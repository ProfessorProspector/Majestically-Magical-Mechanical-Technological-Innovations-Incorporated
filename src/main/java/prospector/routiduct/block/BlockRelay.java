package prospector.routiduct.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import prospector.routiduct.Routiduct;
import prospector.routiduct.api.EnumProtocol;
import reborncore.modcl.BlockCL;

/**
 * Created by Prospector
 */
public class BlockRelay extends BlockCL {

	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public final EnumProtocol protocol;

	public BlockRelay(EnumProtocol protocol) {
		super(Routiduct.MOD_CL, "relay." + protocol.name.toLowerCase(), Material.IRON);
		setHardness(0.5F);
		setDefaultState(getDefaultState().withProperty(EAST, false).withProperty(WEST, false).withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(UP, false).withProperty(DOWN, false));
		this.protocol = protocol;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, 0);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(getDefaultState());
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		IBlockState actualState = state;
		if (worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockRoutiduct && worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getValue(BlockRoutiduct.AXIS) == BlockRoutiduct.EnumAxis.X || worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockRelay || worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockPackager || worldIn.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockUnpackager)
			actualState = actualState.withProperty(EAST, true);
		if (worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockRoutiduct && worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getValue(BlockRoutiduct.AXIS) == BlockRoutiduct.EnumAxis.X || worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockRelay || worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockPackager || worldIn.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockUnpackager)
			actualState = actualState.withProperty(WEST, true);
		if (worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockRoutiduct && worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getValue(BlockRoutiduct.AXIS) == BlockRoutiduct.EnumAxis.Z || worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockRelay || worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockPackager || worldIn.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockUnpackager)
			actualState = actualState.withProperty(NORTH, true);
		if (worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockRoutiduct && worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getValue(BlockRoutiduct.AXIS) == BlockRoutiduct.EnumAxis.Z || worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockRelay || worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockPackager || worldIn.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockUnpackager)
			actualState = actualState.withProperty(SOUTH, true);
		if (worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() instanceof BlockRoutiduct && worldIn.getBlockState(pos.offset(EnumFacing.UP)).getValue(BlockRoutiduct.AXIS) == BlockRoutiduct.EnumAxis.Y || worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() instanceof BlockRelay || worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() instanceof BlockPackager || worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() instanceof BlockUnpackager)
			actualState = actualState.withProperty(UP, true);
		if (worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() instanceof BlockRoutiduct && worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getValue(BlockRoutiduct.AXIS) == BlockRoutiduct.EnumAxis.Y || worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() instanceof BlockRelay || worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() instanceof BlockPackager || worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() instanceof BlockUnpackager)
			actualState = actualState.withProperty(DOWN, true);
		return actualState;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { EAST, WEST, NORTH, SOUTH, UP, DOWN });
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState();
	}
}
