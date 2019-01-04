package io.github.prospector.routiduct.api;

import io.github.prospector.silk.util.NullableDirection;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ViewableWorld;

public interface RoutiductRelay extends RoutiductConnectable {
	public NullableDirection getNextConnection(ViewableWorld world, BlockPos pos, BlockState state, Direction fromSide);
}
