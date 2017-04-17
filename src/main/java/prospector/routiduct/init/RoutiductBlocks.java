package prospector.routiduct.init;

import net.minecraft.item.ItemStack;
import prospector.routiduct.Routiduct;
import prospector.routiduct.api.EnumProtocol;
import prospector.routiduct.block.BlockPackager;
import prospector.routiduct.block.BlockRelay;
import prospector.routiduct.block.BlockRoutiduct;
import prospector.routiduct.block.BlockUnpackager;
import reborncore.modcl.BlockCL;
import reborncore.modcl.BlockContainerCL;

/**
 * Created by Prospector
 */
public class RoutiductBlocks {

	public static void init() {
		for (EnumProtocol protocol : EnumProtocol.values()) {
			BlockRoutiduct routiduct = new BlockRoutiduct(protocol);
			addToRegistry(routiduct, "blockRoutiduct", "routiduct" + protocol.name);
			BlockPackager packager = new BlockPackager(protocol);
			addToRegistry(packager, "blockPackager", "packager" + protocol.name);
			BlockUnpackager unpackager = new BlockUnpackager(protocol);
			addToRegistry(unpackager, "blockUnpackager", "unpackager" + protocol.name);
			BlockRelay relay = new BlockRelay(protocol);
			addToRegistry(relay, "blockRelay", "relay" + protocol.name);
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

	protected static void addToRegistry(BlockContainerCL block) {
		addToRegistry(block.name.replaceFirst("^routiduct:", ""), block);
	}

	protected static void addToRegistry(String name, BlockContainerCL block) {
		Routiduct.MOD_CL.getRegistry().blockContainerRegistry.put(name, block);
	}

	protected static void addToRegistry(BlockContainerCL block, String... oreDictNames) {
		addToRegistry(block.name.replaceFirst("^routiduct:", ""), block, oreDictNames);
	}

	protected static void addToRegistry(String name, BlockContainerCL block, String... oreDictNames) {
		addToRegistry(name, block);
		for (String oreName : oreDictNames)
			Routiduct.MOD_CL.getRegistry().oreEntries.put(new ItemStack(block), oreName);
	}
}
