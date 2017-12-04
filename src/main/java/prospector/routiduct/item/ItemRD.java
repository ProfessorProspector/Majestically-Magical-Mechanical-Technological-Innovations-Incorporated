package prospector.routiduct.item;

import net.minecraft.item.Item;
import prospector.routiduct.RoutiductConstants;

/**
 * Created by Prospector
 */
public class ItemRD extends Item {

	public ItemRD(String name) {
		super();
		this.setRegistryName(RoutiductConstants.MOD_ID, name);
		this.setUnlocalizedName(RoutiductConstants.MOD_ID + "." + name);
	}
}
