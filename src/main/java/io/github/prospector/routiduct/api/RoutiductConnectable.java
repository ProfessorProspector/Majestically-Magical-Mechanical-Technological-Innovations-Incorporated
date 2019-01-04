package io.github.prospector.routiduct.api;

import io.github.prospector.silk.util.NullableDirection;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;

public interface RoutiductConnectable {
	public boolean canConnectTo(ViewableWorld world, BlockPos pos, BlockState state, Direction side);

	public boolean isConnectedTo(ViewableWorld world, BlockPos pos, BlockState state, NullableDirection direction);

	public void connectTo(IWorld world, BlockPos pos, BlockState state, NullableDirection direction);
}
