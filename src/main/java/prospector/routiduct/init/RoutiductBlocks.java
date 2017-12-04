package prospector.routiduct.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import prospector.routiduct.RoutiductConstants;
import prospector.routiduct.api.EnumProtocol;
import prospector.routiduct.block.BlockPackager;
import prospector.routiduct.block.BlockRelay;
import prospector.routiduct.block.BlockRoutiduct;
import prospector.routiduct.block.BlockUnpackager;

public class RoutiductBlocks {

	public static void init(RegistryEvent.Register<Block> event) {
		for (EnumProtocol protocol : EnumProtocol.values()) {
			BlockRoutiduct routiduct = new BlockRoutiduct(protocol);
			register(routiduct, event, "blockRoutiduct", "routiduct" + protocol.name);
			BlockPackager packager = new BlockPackager(protocol);
			register(packager, event, "blockPackager", "packager" + protocol.name);
			BlockUnpackager unpackager = new BlockUnpackager(protocol);
			register(unpackager, event, "blockUnpackager", "unpackager" + protocol.name);
			BlockRelay relay = new BlockRelay(protocol);
			register(relay, event, "blockRelay", "relay" + protocol.name);
		}
	}

	public static void register(Block block, RegistryEvent.Register<Block> event, String... oreNames) {
		event.getRegistry().register(block);
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);
		for (String oreName : oreNames) {
			OreDictionary.registerOre(oreName, block);
		}
	}

	public Block getBlock(String name, EnumProtocol protocol) {
		return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(RoutiductConstants.PREFIX, name + "." + protocol.name.toLowerCase()));
	}

	public Item getItem(String name, EnumProtocol protocol) {
		return Item.getItemFromBlock(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(RoutiductConstants.PREFIX, name + "." + protocol.name.toLowerCase())));
	}
}
