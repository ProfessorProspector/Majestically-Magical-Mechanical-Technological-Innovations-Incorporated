package prospector.routiduct.init;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreDictionary;
import prospector.routiduct.item.ItemRD;
import prospector.routiduct.item.ItemWrench;

public class RoutiductItems {

	public static final ItemRD WRENCH = new ItemWrench();

	public static void init(RegistryEvent.Register<Item> event) {
		register(WRENCH, event, "itemWrench", "wrenchRoutiduct");
	}

	public static void register(Item item, RegistryEvent.Register<Item> event, String... oreNames) {
		event.getRegistry().register(item);
		for (String oreName : oreNames) {
			OreDictionary.registerOre(oreName, item);
		}
	}
}
