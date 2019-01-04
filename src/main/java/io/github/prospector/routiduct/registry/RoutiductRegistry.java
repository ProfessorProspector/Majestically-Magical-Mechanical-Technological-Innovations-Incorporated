package io.github.prospector.routiduct.registry;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class RoutiductRegistry implements ModInitializer {
	public static final Map<Identifier, Block> BLOCKS = new HashMap<>();
	public static final Map<Identifier, Item> ITEMS = new HashMap<>();
	public static final Map<Identifier, BlockEntityType<? extends BlockEntity>> BLOCK_ENTITIES = new HashMap<>();

	@Override
	public void onInitialize() {
		RoutiductItems.loadClass();
		RoutiductBlocks.loadClass();
		RoutiductBlockEntities.loadClass();

		for (Identifier id : ITEMS.keySet()) {
			Registry.register(Registry.ITEM, id, ITEMS.get(id));
		}
		for (Identifier id : BLOCKS.keySet()) {
			Registry.register(Registry.BLOCK, id, BLOCKS.get(id));
		}
		for (Identifier id : BLOCK_ENTITIES.keySet()) {
			Registry.register(Registry.BLOCK_ENTITY, id, BLOCK_ENTITIES.get(id));
		}
	}
}
