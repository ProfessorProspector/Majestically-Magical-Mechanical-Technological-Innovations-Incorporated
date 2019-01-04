package io.github.prospector.routiduct.block;

import io.github.prospector.routiduct.api.RoutiductConnectable;
import io.github.prospector.routiduct.api.RoutiductRelay;
import io.github.prospector.routiduct.util.RoutiductProperties;
import io.github.prospector.silk.util.NullableDirection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.EnumProperty;
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

public class RoutiductBlock extends Block implements RoutiductRelay {

	public static final VoxelShape CORE_SHAPE = Block.createCubeShape(4, 4, 4, 16 - 4, 16 - 4, 16 - 4);
	public static final Map<Direction, VoxelShape> CONNECTION_SHAPES = new HashMap<>();
	public final Map<BlockState, VoxelShape> stateShapeCache = new HashMap<>();

	static {
		CONNECTION_SHAPES.put(Direction.NORTH, Block.createCubeShape(4, 4, 0, 12, 12, 4));
		CONNECTION_SHAPES.put(Direction.SOUTH, Block.createCubeShape(4, 4, 16, 12, 12, 4));
		CONNECTION_SHAPES.put(Direction.EAST, Block.createCubeShape(16, 4, 4, 4, 12, 12));
		CONNECTION_SHAPES.put(Direction.WEST, Block.createCubeShape(0, 4, 4, 4, 12, 12));
		CONNECTION_SHAPES.put(Direction.UP, Block.createCubeShape(4, 16, 4, 12, 4, 12));
		CONNECTION_SHAPES.put(Direction.DOWN, Block.createCubeShape(4, 0, 4, 12, 4, 12));
	}

	public static final EnumProperty<NullableDirection> DIRECTION1 = RoutiductProperties.DIRECTION_1;
	public static final EnumProperty<NullableDirection> DIRECTION2 = RoutiductProperties.DIRECTION_2;

	public RoutiductBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(DIRECTION1, NullableDirection.NONE).with(DIRECTION2, NullableDirection.NONE));
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

		VoxelShape shape = CORE_SHAPE;
		NullableDirection d1 = state.get(DIRECTION1);
		if (d1 != NullableDirection.NONE) {
			shape = VoxelShapes.union(shape, CONNECTION_SHAPES.get(d1.getDirection()));
		}
		NullableDirection d2 = state.get(DIRECTION2);
		if (d2 != NullableDirection.NONE) {
			shape = VoxelShapes.union(shape, CONNECTION_SHAPES.get(d2.getDirection()));
		}

		stateShapeCache.put(state, shape);
		return method_9571(state, world, pos);
	}

	@Override
	protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
		super.appendProperties(builder.with(DIRECTION1, DIRECTION2));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(DIRECTION1).getDirection() == direction && !(neighborState.getBlock() instanceof RoutiductConnectable && ((RoutiductConnectable) neighborState.getBlock()).isConnectedTo(world, neighborPos, neighborState, NullableDirection.get(direction.getOpposite())))) {
			world.setBlockState(pos, state.with(DIRECTION1, NullableDirection.NONE), 3);
		} else if (state.get(DIRECTION2).getDirection() == direction && !(neighborState.getBlock() instanceof RoutiductConnectable && ((RoutiductConnectable) neighborState.getBlock()).isConnectedTo(world, neighborPos, neighborState, NullableDirection.get(direction.getOpposite())))) {
			world.setBlockState(pos, state.with(DIRECTION2, NullableDirection.NONE), 3);
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		NullableDirection direction1 = state.get(DIRECTION1);
		if (direction1.hasDirection()) {
			BlockPos offsetPos = pos.offset(direction1.getDirection());
			BlockState offsetState = world.getBlockState(offsetPos);
			((RoutiductConnectable) (offsetState.getBlock())).connectTo(world, offsetPos, offsetState, NullableDirection.get(direction1.getDirection().getOpposite()));
		}
		NullableDirection direction2 = state.get(DIRECTION2);
		if (direction2.hasDirection()) {
			BlockPos offsetPos = pos.offset(direction2.getDirection());
			BlockState offsetState = world.getBlockState(offsetPos);
			((RoutiductConnectable) (offsetState.getBlock())).connectTo(world, offsetPos, offsetState, NullableDirection.get(direction2.getDirection().getOpposite()));
		}
		super.onPlaced(world, pos, state, entity, stack);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		World world = context.getWorld();
		BlockState state = super.getPlacementState(context);
		for (Direction side : Direction.values()) {
			BlockPos offsetPos = context.getPos().offset(side);
			BlockState offsetState = world.getBlockState(offsetPos);
			if (offsetState.getBlock() instanceof RoutiductConnectable && ((RoutiductConnectable) offsetState.getBlock()).canConnectTo(world, offsetPos, offsetState, side.getOpposite())) {
				state = state.with(DIRECTION1, NullableDirection.get(side));
				BlockPos oppositePos = context.getPos().offset(side.getOpposite());
				BlockState oppositeState = world.getBlockState(oppositePos);
				if (oppositeState.getBlock() instanceof RoutiductConnectable && ((RoutiductConnectable) oppositeState.getBlock()).canConnectTo(world, oppositePos, oppositeState, side.getOpposite())) {
					state = state.with(DIRECTION2, NullableDirection.get(side.getOpposite()));
				} else {
					for (Direction side1 : Direction.values()) {
						if (state.get(DIRECTION1).getDirection() != side1) {
							BlockPos offsetPos1 = context.getPos().offset(side1);
							BlockState offsetState1 = world.getBlockState(offsetPos1);
							if (offsetState1.getBlock() instanceof RoutiductConnectable && ((RoutiductConnectable) offsetState1.getBlock()).canConnectTo(world, offsetPos1, offsetState1, side1.getOpposite())) {
								state = state.with(DIRECTION2, NullableDirection.get(side1));
							}
						}
					}
				}
			}
		}
		return state;
	}

	@Override
	public boolean canConnectTo(ViewableWorld world, BlockPos pos, BlockState state, Direction side) {
		return state.get(DIRECTION1) == NullableDirection.NONE || state.get(DIRECTION2) == NullableDirection.NONE;
	}

	@Override
	public boolean isConnectedTo(ViewableWorld world, BlockPos pos, BlockState state, NullableDirection direction) {
		return state.get(DIRECTION1) == direction || state.get(DIRECTION2) == direction;
	}

	@Override
	public void connectTo(IWorld world, BlockPos pos, BlockState state, NullableDirection side) {
		if (state.get(DIRECTION1) == NullableDirection.NONE) {
			world.setBlockState(pos, state.with(DIRECTION1, side), 3);
		} else if (state.get(DIRECTION2) == NullableDirection.NONE) {
			world.setBlockState(pos, state.with(DIRECTION2, side), 3);
		}
	}

	@Override
	public NullableDirection getNextConnection(ViewableWorld world, BlockPos pos, BlockState state, Direction fromSide) {
		NullableDirection direction1 = state.get(DIRECTION1);
		if (direction1.hasDirection() && direction1.getDirection() == fromSide) {
			return state.get(DIRECTION2);
		} else {
			NullableDirection direction2 = state.get(DIRECTION2);
			if (direction2.hasDirection() && direction2.getDirection() == fromSide) {
				return state.get(DIRECTION1);
			}
		}
		return NullableDirection.NONE;
	}
}
