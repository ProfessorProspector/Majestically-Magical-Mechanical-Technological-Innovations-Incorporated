package prospector.routiduct.container;

import net.minecraft.entity.player.EntityPlayer;
import reborncore.common.container.RebornContainer;

/**
 * Created by Prospector
 */
public class ContainerRoutiductBase extends RebornContainer {
	public ContainerRoutiductBase() {
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return true;
	}
}
