package io.github.prospector.routiduct.blockentity;

import io.github.prospector.routiduct.registry.RoutiductBlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;

public class UnpackagerBlockEntity extends BlockEntity implements Tickable {
	public UnpackagerBlockEntity() {
		super(RoutiductBlockEntities.UNPACKAGER);
	}

	@Override
	public void tick() {
	}
}
