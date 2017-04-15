package prospector.routiduct.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import prospector.routiduct.Routiduct;
import prospector.routiduct.item.ItemWrench;
import reborncore.modcl.ItemCL;

/**
 * Created by Prospector
 */
public class RoutiductItems {

	public static ItemCL WRENCH;


	public static void init() {
		WRENCH = new ItemWrench();

		addToRegistry(WRENCH, "itemWrench", "wrenchRoutiduct");
	}

	protected static void addToRegistry(ItemCL item) {
		addToRegistry(item.name.replaceFirst("^routiduct:", ""), item);
	}

	protected static void addToRegistry(String name, ItemCL item) {
		Routiduct.MOD_CL.getRegistry().itemRegistry.put(name, item);
	}

	protected static void addToRegistry(ItemCL item, String... oreDictNames) {
		addToRegistry(item.name.replaceFirst("^routiduct:", ""), item, oreDictNames);
	}

	protected static void addToRegistry(String name, ItemCL item, String... oreDictNames) {
		addToRegistry(name, item);
		for (String oreName : oreDictNames)
			Routiduct.MOD_CL.getRegistry().oreEntries.put(new ItemStack(item), oreName);
	}
}
