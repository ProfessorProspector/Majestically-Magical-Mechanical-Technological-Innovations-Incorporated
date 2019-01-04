package io.github.prospector.routiduct.registry;

import io.github.prospector.routiduct.Routiduct;
import io.github.prospector.routiduct.blockentity.PackagerBlockEntity;
import io.github.prospector.routiduct.blockentity.UnpackagerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class RoutiductBlockEntities {

	public static final BlockEntityType<PackagerBlockEntity> PACKAGER = add("packager", PackagerBlockEntity::new);
	public static final BlockEntityType<UnpackagerBlockEntity> UNPACKAGER = add("unpackager", UnpackagerBlockEntity::new);

	public static <T extends BlockEntity> BlockEntityType<T> add(String name, Supplier<? extends T> supplier) {
		return add(name, BlockEntityType.Builder.create(supplier));
	}

	public static <T extends BlockEntity> BlockEntityType<T> add(String name, BlockEntityType.Builder<T> builder) {
		return add(name, builder.method_11034(null));
	}

	public static <T extends BlockEntity> BlockEntityType<T> add(String name, BlockEntityType<T> blockEntityType) {
		RoutiductRegistry.BLOCK_ENTITIES.put(new Identifier(Routiduct.MOD_ID, name), blockEntityType);
		return blockEntityType;
	}

	public static void loadClass() {}
}
