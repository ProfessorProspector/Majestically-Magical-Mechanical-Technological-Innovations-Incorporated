package prospector.routiduct.block;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.inventory.Slot;
import reborncore.common.logic.LogicController;

import java.util.List;

/**
 * Created by Prospector
 */
public class TileRoutiductBase extends LogicController implements ITickable {
	public TileRoutiductBase() {
	}

	@Override
	public void tick() {

	}

	@Override
	public int getInvSize() {
		return 0;
	}

	@Override
	public List<Slot> getSlots() {
		return null;
	}
}
