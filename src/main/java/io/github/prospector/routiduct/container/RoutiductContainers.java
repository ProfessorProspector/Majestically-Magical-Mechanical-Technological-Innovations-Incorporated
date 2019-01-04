package io.github.prospector.routiduct.container;

import io.github.prospector.routiduct.blockentity.PackagerBlockEntity;
import io.github.prospector.routiduct.client.gui.PackagerGui;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.gui.GuiProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class RoutiductContainers implements ModInitializer {

	@Override
	public void onInitialize() {
		ContainerProviderRegistry.INSTANCE.registerFactory(PackagerBlockEntity.CONTAINER_ID, (identifier, playerEntity, packetByteBuf) -> {
			BlockPos pos = packetByteBuf.readBlockPos();
			BlockEntity blockEntity = playerEntity.world.getBlockEntity(pos);
			if (blockEntity instanceof PackagerBlockEntity) {
				return ((PackagerBlockEntity) blockEntity).createContainer(playerEntity);
			}
			return null;
		});

		GuiProviderRegistry.INSTANCE.registerFactory(PackagerBlockEntity.CONTAINER_ID, PackagerGui::new);

	}

}
