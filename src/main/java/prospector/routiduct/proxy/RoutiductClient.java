package prospector.routiduct.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import prospector.routiduct.RoutiductConstants;
import prospector.routiduct.gui.GuiAssembler;
import prospector.routiduct.gui.GuiAssemblerS;
import prospector.shootingstar.ShootingStar;

/**
 * Created by Prospector
 */
@SideOnly(Side.CLIENT)
public class RoutiductClient extends RoutiductServer {

	public void preInit() {
		ShootingStar.registerModels(RoutiductConstants.MOD_ID);
	}

	public void init() {

	}

	public void postInit() {

	}

	public GuiAssemblerS getGuiAssembler() {
		return new GuiAssembler();
	}
}
