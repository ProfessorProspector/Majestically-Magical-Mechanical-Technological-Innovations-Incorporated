package io.github.prospector.routiduct.api;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;

public interface RoutiductPackageReciever extends RoutiductConnectable {
	public boolean canRecievePackage(ViewableWorld world, BlockPos pos, BlockState state, Direction fromSide, Package pkg);

	public void recievePackage(IWorld world, BlockPos pos, BlockState state, Direction fromSide, Package pkg);
}
