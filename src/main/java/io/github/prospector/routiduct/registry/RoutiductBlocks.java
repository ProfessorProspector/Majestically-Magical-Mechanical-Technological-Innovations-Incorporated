package io.github.prospector.routiduct.registry;

import io.github.prospector.routiduct.Routiduct;
import io.github.prospector.routiduct.block.PackagerBlock;
import io.github.prospector.routiduct.block.RoutiductBlock;
import io.github.prospector.routiduct.block.UnpackagerBlock;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;

public class RoutiductBlocks {

	public static final RoutiductBlock ROUTIDUCT = add("routiduct", new RoutiductBlock(FabricBlockSettings.of(Material.METAL).hardness(0.5F).build()), ItemGroup.DECORATIONS);
	public static final PackagerBlock PACKAGER = add("packager", new PackagerBlock(FabricBlockSettings.of(Material.METAL).hardness(0.5F).build()), ItemGroup.DECORATIONS);
	public static final UnpackagerBlock UNPACKAGER = add("unpackager", new UnpackagerBlock(FabricBlockSettings.of(Material.METAL).hardness(0.5F).build()), ItemGroup.DECORATIONS);

	public static <B extends Block> B add(String name, B block, ItemGroup itemGroup) {
		return add(name, block, new BlockItem(block, new Item.Settings().itemGroup(itemGroup)));
	}

	public static <B extends Block> B add(String name, B block, BlockItem item) {
		RoutiductRegistry.BLOCKS.put(new Identifier(Routiduct.MOD_ID, name), block);
		if (item != null) {
			item.registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
			RoutiductItems.add(name, item);
		}
		return block;
	}

	public static void loadClass() {}
}
