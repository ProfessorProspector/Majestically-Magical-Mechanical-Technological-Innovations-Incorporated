package prospector.routiduct.api;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prospector
 */
public class Package {
	public static final Package EMPTY = new Package(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);

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

	public boolean isEmpty() {
		if (this != EMPTY) {
			return true;
		}
		return false;
	}

	public enum EnumColour {
		TEAL("colour.routiduct:teal.name", 0xFF00FFFA, 0xFF00D4D0),
		SKY("colour.routiduct:sky.name", 0xFF009EFF, 0xFF0083D4),
		BLUE("colour.routiduct:blue.name", 0xFF2100FF, 0xFF1C00D4),
		PURPLE("colour.routiduct:purple.name", 0xFF9000FF, 0xFF7800D4),
		PINK("colour.routiduct:pink.name", 0xFFF200FF, 0xFFC900D4),
		RED("colour.routiduct:red.name", 0xFFFF0800, 0xFFD40700),
		ORANGE("colour.routiduct:orange.name", 0xFFFF6A00, 0xFFD45800),
		YELLOW("colour.routiduct:yellow.name", 0xFFFFE100, 0xFFD4BB00),
		LIME("colour.routiduct:lime.name", 0xFF80FF00, 0xFF6AD400),
		GREEN("colour.routiduct:green.name", 0xFF21A943, 0xFF0B7C27),
		BLACK("colour.routiduct:black.name", 0xFF191919, 0xFF000000),
		GREY("colour.routiduct:grey.name", 0xFF666666, 0xFF484848),
		SILVER("colour.routiduct:silver.name", 0xFFB2B2B2, 0xFF949494),
		WHITE("colour.routiduct:white.name", 0xFFFFFFFF, 0xFFDEDEDE);

		public final int colour;
		public final int altColour;
		public String unlocalisedName;

		EnumColour(String unlocalisedName, int colour, int altColour) {
			this.unlocalisedName = unlocalisedName;
			this.colour = colour;
			this.altColour = altColour;
		}
	}
}
