package prospector.routiduct.init;

import net.minecraft.item.ItemStack;
import prospector.routiduct.Routiduct;
import prospector.routiduct.api.EnumProtocol;
import prospector.routiduct.block.BlockRoutiduct;
import reborncore.modcl.BlockCL;

/**
 * Created by Prospector
 */
public class RoutiductBlocks {
	public static BlockRoutiduct routiduct = new BlockRoutiduct(EnumProtocol.RD1);

	public static void init() {
		for (EnumProtocol protocol : EnumProtocol.values()) {
			BlockRoutiduct routiduct = new BlockRoutiduct(protocol);
			addToRegistry(routiduct, "blockRoutiduct", "routiduct" + protocol.name);
		}
	}

	protected static void addToRegistry(BlockCL block) {
		addToRegistry(block.name.replaceFirst("^routiduct:", ""), block);
	}

	protected static void addToRegistry(String name, BlockCL block) {
		Routiduct.MOD_CL.getRegistry().blockRegistry.put(name, block);
	}

	protected static void addToRegistry(BlockCL block, String... oreDictNames) {
		addToRegistry(block.name.replaceFirst("^routiduct:", ""), block, oreDictNames);
	}

	protected static void addToRegistry(String name, BlockCL block, String... oreDictNames) {
		addToRegistry(name, block);
		for (String oreName : oreDictNames)
			Routiduct.MOD_CL.getRegistry().oreEntries.put(new ItemStack(block), oreName);
	}
}
