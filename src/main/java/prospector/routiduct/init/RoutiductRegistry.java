package prospector.routiduct.init;

import net.minecraft.item.ItemBlock;
import prospector.routiduct.Routiduct;
import reborncore.modcl.BlockCL;
import reborncore.modcl.ItemCL;
import reborncore.modcl.ModCL;
import reborncore.modcl.RegistryCL;

/**
 * Created by Prospector
 */
public class RoutiductRegistry extends RegistryCL {

	public void init(ModCL mod) {
		RoutiductItems.init();
		RoutiductBlocks.init();

		for (ItemCL item : Routiduct.MOD_CL.getRegistry().itemRegistry.values()) {
			register(item);
		}

		for (BlockCL block : Routiduct.MOD_CL.getRegistry().blockRegistry.values()) {
			register(block);
		}
	}

}
