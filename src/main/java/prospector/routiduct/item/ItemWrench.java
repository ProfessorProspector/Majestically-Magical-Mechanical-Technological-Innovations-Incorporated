package prospector.routiduct.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Prospector
 */
public class ItemWrench extends ItemRoutiductBase {

	public ItemWrench() {
		super("wrench");
		setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && player.isSneaking()) {
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}
