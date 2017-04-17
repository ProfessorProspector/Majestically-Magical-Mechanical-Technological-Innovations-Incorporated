package prospector.routiduct.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import prospector.routiduct.block.tiles.TilePackager;
import prospector.routiduct.container.ContainerPackager;

import javax.annotation.Nullable;

/**
 * Created by Prospector
 */
public class RoutiductGuiHandler implements IGuiHandler {
	@Nullable
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		EnumGui gui = EnumGui.values()[id];
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch (gui) {
			case PACKAGER:
				return new ContainerPackager((TilePackager) tile, player);
			default:
				break;
		}
		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		EnumGui gui = EnumGui.values()[id];
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch (gui) {
			case PACKAGER:
				return new GuiPackager((TilePackager) tile, player);
			default:
				break;
		}
		return null;
	}
}
