package io.github.prospector.routiduct.block;

import io.github.prospector.routiduct.api.Package;
import io.github.prospector.routiduct.api.RoutiductConnectable;
import io.github.prospector.routiduct.api.RoutiductPackageReciever;
import io.github.prospector.routiduct.blockentity.PackagerBlockEntity;
import io.github.prospector.routiduct.util.RoutiductProperties;
import io.github.prospector.silk.block.SilkBlockWithEntity;
import io.github.prospector.silk.util.NullableDirection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
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

public class UnpackagerBlock extends SilkBlockWithEntity implements RoutiductPackageReciever {
	public static final Map<Direction, VoxelShape> UNPACKAGER_SHAPES = new HashMap<>();
	public static final Map<Direction, VoxelShape> UNPACKAGER_EXTENSION_SHAPES = new HashMap<>();
	public static final Map<Direction, VoxelShape> CONNECTION_SHAPES = new HashMap<>();
	public final Map<BlockState, VoxelShape> stateShapeCache = new HashMap<>();

	static {
		UNPACKAGER_SHAPES.put(Direction.NORTH, Block.createCubeShape(1, 1, 1, 15, 15, 7));
		UNPACKAGER_SHAPES.put(Direction.SOUTH, Block.createCubeShape(1, 1, 9, 15, 15, 15));
		UNPACKAGER_SHAPES.put(Direction.EAST, Block.createCubeShape(9, 1, 1, 15, 15, 15));
		UNPACKAGER_SHAPES.put(Direction.WEST, Block.createCubeShape(1, 1, 1, 7, 15, 15));
		UNPACKAGER_SHAPES.put(Direction.UP, Block.createCubeShape(1, 9, 1, 15, 15, 15));
		UNPACKAGER_SHAPES.put(Direction.DOWN, Block.createCubeShape(1, 1, 1, 15, 7, 15));

		UNPACKAGER_EXTENSION_SHAPES.put(Direction.NORTH, Block.createCubeShape(3, 3, 3, 13, 13, 8));
		UNPACKAGER_EXTENSION_SHAPES.put(Direction.SOUTH, Block.createCubeShape(3, 3, 8, 13, 13, 13));
		UNPACKAGER_EXTENSION_SHAPES.put(Direction.EAST, Block.createCubeShape(8, 3, 3, 13, 13, 13));
		UNPACKAGER_EXTENSION_SHAPES.put(Direction.WEST, Block.createCubeShape(3, 3, 3, 8, 13, 13));
		UNPACKAGER_EXTENSION_SHAPES.put(Direction.UP, Block.createCubeShape(3, 8, 3, 13, 13, 13));
		UNPACKAGER_EXTENSION_SHAPES.put(Direction.DOWN, Block.createCubeShape(3, 3, 3, 13, 8, 13));

		CONNECTION_SHAPES.put(Direction.NORTH, Block.createCubeShape(4, 4, 16, 12, 12, 8));
		CONNECTION_SHAPES.put(Direction.SOUTH, Block.createCubeShape(4, 4, 0, 12, 12, 8));
		CONNECTION_SHAPES.put(Direction.EAST, Block.createCubeShape(0, 4, 4, 8, 12, 12));
		CONNECTION_SHAPES.put(Direction.WEST, Block.createCubeShape(16, 4, 4, 8, 12, 12));
		CONNECTION_SHAPES.put(Direction.UP, Block.createCubeShape(4, 0, 4, 12, 8, 12));
		CONNECTION_SHAPES.put(Direction.DOWN, Block.createCubeShape(4, 16, 4, 12, 8, 12));
	}

	public static final DirectionProperty FACING = Properties.FACING;
	public static final BooleanProperty CONNECTED = RoutiductProperties.CONNECTED;

	public UnpackagerBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(CONNECTED, false));
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
		VoxelShape shape = VoxelShapes.union(UNPACKAGER_SHAPES.get(facing), UNPACKAGER_EXTENSION_SHAPES.get(facing));
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
		super.appendProperties(builder.with(FACING, CONNECTED));
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

	@Override
	public boolean canRecievePackage(ViewableWorld world, BlockPos pos, BlockState state, Direction fromSide, Package pkg) {
		BlockEntity blockEntity = world.getBlockEntity(pos.offset(state.get(FACING)));
		return blockEntity instanceof Inventory;
	}

	@Override
	public void recievePackage(IWorld world, BlockPos pos, BlockState state, Direction fromSide, Package pkg) {

	}
}
