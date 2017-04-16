package prospector.routiduct.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import prospector.routiduct.api.EnumProtocol;
import reborncore.common.util.Inventory;
import reborncore.common.util.InventoryHelper;

/**
 * Created by Prospector
 */
public class TilePackager extends TileRoutiductBase implements ITickable {

	public EnumProtocol protocol = EnumProtocol.RD1;
	public Inventory inventory = new Inventory(16, "TilePackager", 64, this);

	public TilePackager() {
		super();
	}

	@Override
	public void update() {
		System.out.println(protocol);
		if (protocol != ((BlockPackager) world.getBlockState(pos).getBlock()).protocol) {
			protocol = ((BlockPackager) world.getBlockState(pos).getBlock()).protocol;
		}
		if (inventory.getStacks().length == protocol.stacks) {
			return;
		}
		EnumFacing facing = getWorld().getBlockState(pos).getValue(BlockPackager.FACING);
		TileEntity tileEntity = getWorld().getTileEntity(getPos().offset(facing));
		if (tileEntity != null && tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
			IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
			for (int i = 0; i < itemHandler.getSlots(); i++) {
				ItemStack stack1 = itemHandler.getStackInSlot(i);
				ItemStack extractedStack = itemHandler.extractItem(i, 1, true);
				for (int j = 0; j < protocol.stacks; j++)
					if (inventory.isItemValidForSlot(j, extractedStack)) {
						int amount = InventoryHelper.testInventoryInsertion(inventory, extractedStack, null);
						if (amount > 0) {
							extractedStack = itemHandler.extractItem(i, 1, false);
							extractedStack.setCount(1);
							InventoryHelper.insertItemIntoInventory(inventory, extractedStack);
						}
					}
			}
		}
	}

	public void updateState() {
		IBlockState state = world.getBlockState(pos);
		if (state.getValue(BlockPackager.CONNECTION) != validConnection())
			world.setBlockState(pos, state.withProperty(BlockPackager.CONNECTION, true));

	}

	public boolean validConnection() {
		IBlockState state = world.getBlockState(pos);
		IBlockState behind = world.getBlockState(pos.offset(state.getValue(BlockPackager.FACING).getOpposite()));
		if (behind.getBlock() instanceof BlockRoutiduct && (behind.getValue(BlockRoutiduct.AXIS) == getAxis() || behind.getValue(BlockRoutiduct.AXIS) == BlockRoutiduct.EnumAxis.NEUTRAL)) {
			if (behind.getValue(BlockRoutiduct.AXIS) == BlockRoutiduct.EnumAxis.NEUTRAL) {
				getWorld().setBlockState(pos.offset(state.getValue(BlockPackager.FACING).getOpposite()), behind.withProperty(BlockRoutiduct.AXIS, getAxis()));
			}
			return true;
		}
		return false;
	}

	public BlockRoutiduct.EnumAxis getAxis() {
		IBlockState state = world.getBlockState(pos);
		EnumFacing facing = state.getValue(BlockPackager.FACING);
		if (facing == EnumFacing.EAST || facing == EnumFacing.EAST) {
			return BlockRoutiduct.EnumAxis.X;
		} else if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
			return BlockRoutiduct.EnumAxis.Z;
		} else {
			return BlockRoutiduct.EnumAxis.Y;
		}
	}
}
