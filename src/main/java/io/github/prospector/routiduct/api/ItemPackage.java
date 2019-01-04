package io.github.prospector.routiduct.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemPackage implements Package {
	public static final ItemPackage EMPTY = new ItemPackage(PackageColor.RED);

	protected List<ItemStack> stacks;
	protected PackageColor color;

	public ItemPackage(PackageColor color, ItemStack... stacks) {
		this.stacks = Arrays.asList(stacks);
	}

	public List<ItemStack> getStacks() {
		return stacks;
	}

	public PackageColor getColor() {
		return color;
	}

	public void setColor(PackageColor color) {
		this.color = color;
	}

	@Override
	public boolean isEmpty() {
		return this != EMPTY && stacks.stream().filter(ItemStack::isEmpty).collect(Collectors.toList()).size() < stacks.size();
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		CompoundTag compoundTag = new CompoundTag();
		int size = stacks.size();
		tag.putInt("Size", size);
		for (ItemStack stack : stacks) {
			compoundTag.put("Stack" + stacks.indexOf(stack), stack.toTag(new CompoundTag()));
		}
		tag.put("Stacks", compoundTag);
		return tag;
	}

	@Override
	public void fromTag(CompoundTag tag) {
		stacks = new ArrayList<>();
		CompoundTag compoundTag = tag.getCompound("Stacks");
		for (int i = 0; i < tag.getInt("Size"); i++) {
			stacks.add(ItemStack.fromTag(compoundTag.getCompound("Stack" + i)));
		}
	}
}
