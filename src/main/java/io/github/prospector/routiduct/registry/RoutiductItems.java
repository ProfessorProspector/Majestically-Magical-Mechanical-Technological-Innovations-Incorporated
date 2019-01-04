package io.github.prospector.routiduct.registry;

import io.github.prospector.routiduct.Routiduct;
import io.github.prospector.routiduct.item.WrenchItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class RoutiductItems {

	public static final WrenchItem WRENCH = add("wrench", new WrenchItem(new Item.Settings().stackSize(1).itemGroup(ItemGroup.TOOLS)));

	public static <I extends Item> I add(String name, I item) {
		RoutiductRegistry.ITEMS.put(new Identifier(Routiduct.MOD_ID, name), item);
		return item;
	}

	public static void loadClass() {}
}
