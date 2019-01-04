package io.github.prospector.routiduct.network;

import io.github.prospector.routiduct.Routiduct;
import io.github.prospector.routiduct.container.builder.IExtendedContainerListener;
import io.github.prospector.silk.util.ObjectBufUtils;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.networking.CustomPayloadPacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ContainerGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.packet.CustomPayloadClientPacket;
import net.minecraft.container.Container;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class RoutiductNetworking implements ModInitializer {

	public static final Identifier CONTAINER_SYNC = new Identifier(Routiduct.MOD_ID, "container_sync");

	@Override
	public void onInitialize() {
		CustomPayloadPacketRegistry.CLIENT.register(CONTAINER_SYNC, (packetContext, packetByteBuf) -> {
			Gui gui = MinecraftClient.getInstance().currentGui;
			if (gui instanceof ContainerGui) {
				Container container = ((ContainerGui) gui).container;
				if (container instanceof IExtendedContainerListener) {
					((IExtendedContainerListener) container).handleObject(packetByteBuf.readInt(), ObjectBufUtils.readObject(packetByteBuf));
				}
			}
		});
	}

	@Environment(EnvType.SERVER)
	public static void syncContainer(ServerPlayerEntity player, int id, Object value) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(id);
		ObjectBufUtils.writeObject(value, buf);
		player.networkHandler.sendPacket(new CustomPayloadClientPacket(CONTAINER_SYNC, buf));
	}
}
