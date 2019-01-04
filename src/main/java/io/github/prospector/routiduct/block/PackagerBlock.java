package io.github.prospector.routiduct.block;

import io.github.prospector.routiduct.api.RoutiductConnectable;
import io.github.prospector.routiduct.blockentity.PackagerBlockEntity;
import io.github.prospector.routiduct.util.RoutiductProperties;
import io.github.prospector.silk.block.SilkBlockWithEntity;
import io.github.prospector.silk.util.NullableDirection;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class PackagerBlock extends SilkBlockWithEntity implements RoutiductConnectable {
	public static final Map<Direction, VoxelShape> PACKAGER_SHAPES = new HashMap<>();
	public static final Map<Direction, VoxelShape> CONNECTION_SHAPES = new HashMap<>();
	public final Map<BlockState, VoxelShape> stateShapeCache = new HashMap<>();

	static {
		PACKAGER_SHAPES.put(Direction.NORTH, Block.createCubeShape(0, 0, 0, 16, 16, 8));
		PACKAGER_SHAPES.put(Direction.SOUTH, Block.createCubeShape(0, 0, 8, 16, 16, 16));
		PACKAGER_SHAPES.put(Direction.EAST, Block.createCubeShape(8, 0, 0, 16, 16, 16));
		PACKAGER_SHAPES.put(Direction.WEST, Block.createCubeShape(0, 0, 0, 8, 16, 16));
		PACKAGER_SHAPES.put(Direction.UP, Block.createCubeShape(0, 8, 0, 16, 16, 16));
		PACKAGER_SHAPES.put(Direction.DOWN, Block.createCubeShape(0, 0, 0, 16, 8, 16));

		CONNECTION_SHAPES.put(Direction.NORTH, Block.createCubeShape(4, 4, 16, 12, 12, 8));
		CONNECTION_SHAPES.put(Direction.SOUTH, Block.createCubeShape(4, 4, 0, 12, 12, 8));
		CONNECTION_SHAPES.put(Direction.EAST, Block.createCubeShape(0, 4, 4, 8, 12, 12));
		CONNECTION_SHAPES.put(Direction.WEST, Block.createCubeShape(16, 4, 4, 8, 12, 12));
		CONNECTION_SHAPES.put(Direction.UP, Block.createCubeShape(4, 0, 4, 12, 8, 12));
		CONNECTION_SHAPES.put(Direction.DOWN, Block.createCubeShape(4, 16, 4, 12, 8, 12));
	}

	public static final DirectionProperty FACING = Properties.FACING;
	public static final BooleanProperty CONNECTED = RoutiductProperties.CONNECTED;
	public static final BooleanProperty PUSHING = RoutiductProperties.PUSHING;

	public PackagerBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(CONNECTED, false).with(PUSHING, false));
	}

	@Override
	public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
		if (!world.isClient) {
			ContainerProviderRegistry.INSTANCE.openContainer(PackagerBlockEntity.CONTAINER_ID, (ServerPlayerEntity) player, packetByteBuf -> packetByteBuf.writeBlockPos(pos));
		}
		return super.activate(state, world, pos, player, hand, side, hitX, hitY, hitZ);
	}

	@Override
	public VoxelShape getBoundingShape(BlockState state, BlockView world, BlockPos pos) {
		return method_9571(state, world, pos);
	}

	@Override
	public VoxelShape method_9571(BlockState state, BlockView world, BlockPos pos) {
		if (stateShapeCache.containsKey(state)) {
			return stateShapeCache.get(state);
		}

		Direction facing = state.get(FACING);
		VoxelShape shape = PACKAGER_SHAPES.get(facing);
		if (state.get(CONNECTED)) {
			shape = VoxelShapes.union(shape, CONNECTION_SHAPES.get(facing));
		}
		stateShapeCache.put(state, shape);
		return method_9571(state, world, pos);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView var1) {
		return new PackagerBlockEntity();
	}

	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
		super.appendProperties(builder.with(FACING, CONNECTED, PUSHING));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(FACING).getOpposite() == direction && !(neighborState.getBlock() instanceof RoutiductConnectable && ((RoutiductConnectable) neighborState.getBlock()).isConnectedTo(world, neighborPos, neighborState, NullableDirection.get(direction.getOpposite())))) {
			world.setBlockState(pos, state.with(CONNECTED, false), 3);
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		super.onPlaced(world, pos, state, entity, stack);
		if (state.get(CONNECTED)) {
			BlockPos offsetPos = pos.offset(state.get(FACING).getOpposite());
			BlockState offsetState = world.getBlockState(offsetPos);
			((RoutiductConnectable) (offsetState.getBlock())).connectTo(world, offsetPos, offsetState, NullableDirection.get(state.get(FACING)));
		}
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		BlockState state = getDefaultState().with(FACING, context.getFacing().getOpposite());
		BlockPos behindPos = context.getPos().offset(context.getFacing());
		BlockState behindState = context.getWorld().getBlockState(behindPos);
		state = state.with(CONNECTED, behindState.getBlock() instanceof RoutiductConnectable && ((RoutiductConnectable) behindState.getBlock()).canConnectTo(context.getWorld(), behindPos, behindState, context.getFacing()));
		return state;
	}

	@Override
	public boolean canConnectTo(ViewableWorld world, BlockPos pos, BlockState state, Direction side) {
		return !state.get(CONNECTED) && state.get(FACING) == side.getOpposite();
	}

	@Override
	public boolean isConnectedTo(ViewableWorld world, BlockPos pos, BlockState state, NullableDirection direction) {
		return state.get(CONNECTED) && direction.hasDirection() && direction.getDirection().getOpposite() == state.get(FACING);
	}

	@Override
	public void connectTo(IWorld world, BlockPos pos, BlockState state, NullableDirection direction) {
		if (direction.hasDirection() && direction.getDirection().getOpposite() == state.get(FACING)) {
			world.setBlockState(pos, state.with(CONNECTED, true), 3);
		}
	}
}
