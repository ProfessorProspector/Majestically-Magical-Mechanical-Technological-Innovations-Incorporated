package prospector.routiduct.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import prospector.routiduct.block.tiles.TilePackager;
import prospector.routiduct.container.ContainerPackager;

/**
 * Created by Prospector
 */
public class GuiPackager extends GuiContainer {
	public int xSize = 176;
	public int ySize = 176;
	RDBuilder builder = new RDBuilder();
	TilePackager tile;
	EntityPlayer player;

	public GuiPackager(TilePackager tile, EntityPlayer player) {
		super(new ContainerPackager(tile, player));
		this.tile = tile;
		this.player = player;
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
		builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
		builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 93, true);

		for (int j = 0; j < tile.protocol.stacks; j++) {
			builder.drawSlot(this, guiLeft + 20 * j + 9, guiTop + 9);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
}
