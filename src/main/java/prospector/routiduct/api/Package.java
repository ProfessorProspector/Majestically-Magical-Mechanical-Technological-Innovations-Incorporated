package prospector.routiduct.api;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prospector
 */
public class Package {
	public ItemStack stack1;
	public ItemStack stack2;
	public ItemStack stack3;
	public ItemStack stack4;

	public Package(ItemStack stack1, ItemStack stack2, ItemStack stack3, ItemStack stack4) {
		this.stack1 = stack1;
		this.stack2 = stack2;
		this.stack3 = stack3;
		this.stack4 = stack4;
	}

	public List<ItemStack> getStacks() {
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(stack1);
		list.add(stack2);
		list.add(stack3);
		list.add(stack4);
		return list;
	}

	public ItemStack getStack1() {
		return stack1;
	}

	public ItemStack getStack2() {
		return stack2;
	}

	public ItemStack getStack3() {
		return stack3;
	}

	public ItemStack getStack4() {
		return stack4;
	}

	public enum EnumColour {

	}
}
