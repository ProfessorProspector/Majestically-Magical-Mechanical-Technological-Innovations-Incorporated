package prospector.routiduct.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import prospector.routiduct.Routiduct;
import prospector.routiduct.api.EnumProtocol;
import prospector.routiduct.item.ItemWrench;
import reborncore.modcl.BlockCL;

/**
 * Created by Prospector
 */
public class BlockRoutiduct extends BlockCL {

	public static final PropertyEnum<EnumAxis> AXIS = PropertyEnum.<EnumAxis>create("axis", EnumAxis.class);

	public BlockRoutiduct(EnumProtocol protocol) {
		super(Routiduct.MOD_CL, "routiduct." + protocol.name.toLowerCase(), Material.IRON);
		setHardness(0.5F);
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		super.onBlockClicked(worldIn, pos, playerIn);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
		if (playerIn.getHeldItem(hand).getItem() instanceof ItemWrench && playerIn.isSneaking()) {
			worldIn.destroyBlock(pos, true);
			return true;
		}
		return false;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, 0);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (pos.equals(getDefaultState().withProperty(AXIS, EnumAxis.NEUTRAL))) {
			handleConnection(worldIn, pos, pos.east(), EnumAxis.X);
			handleConnection(worldIn, pos, pos.west(), EnumAxis.X);
			handleConnection(worldIn, pos, pos.north(), EnumAxis.Z);
			handleConnection(worldIn, pos, pos.south(), EnumAxis.Z);
			handleConnection(worldIn, pos, pos.up(), EnumAxis.Y);
			handleConnection(worldIn, pos, pos.down(), EnumAxis.Y);
		}
	}

	private void handleConnection(World world, BlockPos pos, BlockPos adjacentPos, EnumAxis axis) {
		if (world.getBlockState(adjacentPos).equals(getDefaultState().withProperty(AXIS, EnumAxis.NEUTRAL))) {
			world.setBlockState(pos, getDefaultState().withProperty(AXIS, axis));
			world.setBlockState(adjacentPos, getDefaultState().withProperty(AXIS, axis));
		}
		if (world.getBlockState(adjacentPos).equals(getDefaultState().withProperty(AXIS, axis))) {
			world.setBlockState(pos, getDefaultState().withProperty(AXIS, axis));
			world.setBlockState(adjacentPos, getDefaultState().withProperty(AXIS, axis));
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(AXIS).getMetadata();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AXIS).getMetadata();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AXIS, EnumAxis.getFromMetadata(meta));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AXIS });
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(AXIS, EnumAxis.NEUTRAL);
	}

	public enum EnumAxis implements IStringSerializable {
		X("x"), Y("y"), Z("z"), NEUTRAL("neutral");

		private String name;

		EnumAxis(String name) {
			this.name = name;
		}

		public static int getMetadata(EnumAxis axis) {
			if (axis == X) {
				return 1;
			} else if (axis == Y) {
				return 2;
			} else if (axis == Z) {
				return 3;
			} else
				return 0;
		}

		public static EnumAxis getFromMetadata(int meta) {
			if (meta == 1) {
				return X;
			} else if (meta == 2) {
				return Y;
			} else if (meta == 3) {
				return Z;
			} else
				return NEUTRAL;
		}

		public int getMetadata() {
			return getMetadata(this);
		}

		@Override
		public String getName() {
			return name;
		}
	}
}
