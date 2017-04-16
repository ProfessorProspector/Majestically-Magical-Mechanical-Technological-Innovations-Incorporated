package prospector.routiduct.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import prospector.routiduct.block.TilePackager;
import prospector.routiduct.container.ContainerPackager;
import reborncore.client.guibuilder.GuiBuilder;

/**
 * Created by Prospector
 */
public class GuiPackager extends GuiContainer {
	TilePackager tile;
	EntityPlayer player;

	public GuiPackager(TilePackager tile, EntityPlayer player) {
		super(new ContainerPackager(tile, player));
		this.tile = tile;
		this.player = player;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {

	}
}
