package io.github.prospector.routiduct.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/**
 * Created by Prospector
 */
public class WrenchItem extends Item {

	public WrenchItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!context.getWorld().isClient && context.getPlayer() != null && context.getPlayer().isSneaking()) {
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}
}
