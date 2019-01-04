package io.github.prospector.routiduct.blockentity;

import io.github.prospector.routiduct.Routiduct;
import io.github.prospector.routiduct.api.RoutiductPackageReciever;
import io.github.prospector.routiduct.api.RoutiductRelay;
import io.github.prospector.routiduct.block.PackagerBlock;
import io.github.prospector.routiduct.container.builder.ContainerBuilder;
import io.github.prospector.routiduct.registry.RoutiductBlockEntities;
import io.github.prospector.silk.blockentity.InventoryBlockEntity;
import io.github.prospector.silk.util.NullableDirection;
import net.minecraft.block.BlockState;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class PackagerBlockEntity extends InventoryBlockEntity implements Tickable {
	public static final Identifier CONTAINER_ID = new Identifier(Routiduct.MOD_ID, "packager");

	public int timer = 0;

	public int travelDistance = 0;
	public BlockPos recieverPos = BlockPos.ORIGIN;
	public boolean hasReciever = false;

	public PackagerBlockEntity() {
		super(RoutiductBlockEntities.PACKAGER);
	}

	@Override
	public void tick() {
		if (!world.isClient) {
//			if (timer % 20 == 0) {
//				BlockState state = world.getBlockState(getPos());
//				if (state.getBlock() instanceof PackagerBlock && state.get(PackagerBlock.CONNECTED)) {
//					if (world.getBlockEntity(pos.offset(state.get(PackagerBlock.FACING))) instanceof Inventory) {
//						BlockPos checkingPos = new BlockPos(this.getPos());
//						Direction checkingDirection = state.get(PackagerBlock.FACING).getOpposite();
//						travelDistance = 0;
//						hasReciever = false;
//						boolean hitEnd = false;
//						while (!hitEnd) {
//							checkingPos = checkingPos.offset(checkingDirection);
//							BlockState checkingState = world.getBlockState(checkingPos);
//							if (checkingState.getBlock() instanceof RoutiductRelay) {
//								travelDistance++;
//								NullableDirection nextConnection = ((RoutiductRelay) checkingState.getBlock()).getNextConnection(world, checkingPos.offset(checkingDirection), checkingState, checkingDirection.getOpposite());
//								if (nextConnection != NullableDirection.NONE) {
//									checkingDirection = nextConnection.getDirection();
//								} else {
//									hitEnd = true;
//								}
//							} else {
//								if (checkingState.getBlock() instanceof RoutiductPackageReciever) {
//									hasReciever = true;
//									recieverPos = checkingPos;
//								}
//								hitEnd = true;
//							}
//						}
//					}
//				}
//			}
//			if (timer % Math.pow(4, 0.032 * (travelDistance + 2)) - 0.5 == 0) {
//				if (hasReciever && world.getBlockEntity(checkingPos.offset(checkingDirection)) instanceof Inventory) {
//					Inventory attachedInventory = ((Inventory) world.getBlockEntity(pos.offset(state.get(PackagerBlock.FACING))));
//					ItemStack stack = attachedInventory.getInvStack(0).copy();
//					if (!stack.isEmpty()) {
//						((Inventory) world.getBlockEntity(checkingPos.offset(checkingDirection))).setInvStack(0, stack);
//						attachedInventory.setInvStack(0, ItemStack.EMPTY);
//					}
//				}
//			}
		}
		timer++;
	}

	@Override
	public void fromTag(CompoundTag compoundTag) {
		super.fromTag(compoundTag);
		timer = compoundTag.getInt("Timer");
	}

	@Override
	public CompoundTag toTag(CompoundTag compoundTag) {
		compoundTag.putInt("Timer", timer);
		return super.toTag(compoundTag);
	}

	public Container createContainer(PlayerEntity playerEntity) {
		return (new ContainerBuilder(CONTAINER_ID))
			.player(playerEntity).inventory().hotbar().addInventory()
			.blockEntity(this).slot(0, 55, 45).slot(0, 75, 45).slot(0, 55, 65).slot(0, 75, 65)
			.addInventory().create(this);
	}

	@Override
	public int getInvSize() {
		return 4;
	}
}
