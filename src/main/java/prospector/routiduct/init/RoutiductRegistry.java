package prospector.routiduct.init;

import prospector.routiduct.Routiduct;
import reborncore.modcl.*;

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

		for (BlockContainerCL block : Routiduct.MOD_CL.getRegistry().blockContainerRegistry.values()) {
			register(block);
		}
	}

}
