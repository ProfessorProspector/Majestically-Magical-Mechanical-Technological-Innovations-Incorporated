package prospector.routiduct.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import prospector.routiduct.Routiduct;
import prospector.routiduct.api.EnumProtocol;
import prospector.routiduct.api.IProtocolProvider;
import prospector.routiduct.block.tiles.TilePackager;
import prospector.routiduct.gui.EnumGui;
import reborncore.modcl.BlockContainerCL;

import javax.annotation.Nullable;

/**
 * Created by Prospector
 */
public class BlockPackager extends BlockContainerCL implements IProtocolProvider {
	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);
	public static final PropertyBool CONNECTION = PropertyBool.create("connection");
	private final EnumProtocol protocol;

	public BlockPackager(EnumProtocol protocol) {
		super(Routiduct.MOD_CL, "packager." + protocol.name.toLowerCase(), Material.IRON, TilePackager.class);
		setHardness(0.5F);
		this.protocol = protocol;
	}

	@Override
	public EnumProtocol getProtocol() {
		return protocol;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TilePackager();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
		playerIn.openGui(Routiduct.MOD_CL, EnumGui.PACKAGER.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
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
		if (isEqual(state, EnumFacing.EAST))
			return 0;
		else if (isEqual(state, EnumFacing.WEST))
			return 1;
		else if (isEqual(state, EnumFacing.NORTH))
			return 2;
		else if (isEqual(state, EnumFacing.SOUTH))
			return 3;
		else if (isEqual(state, EnumFacing.UP))
			return 4;
		else if (isEqual(state, EnumFacing.DOWN))
			return 5;
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() instanceof BlockRelay && ((BlockRelay) worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock()).getProtocol() == protocol || worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock() instanceof BlockRoutiduct && ((BlockRoutiduct) worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getBlock()).getProtocol() == protocol && worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite())).getValue(BlockRoutiduct.AXIS) == getAxis(worldIn, pos))
			return state.withProperty(CONNECTION, true);
		return state;
	}

	public BlockRoutiduct.EnumAxis getAxis(IBlockAccess world, BlockPos pos) {
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

	public boolean isEqual(IBlockState state, EnumFacing facingValue) {
		if (state.equals(getDefaultState().withProperty(FACING, facingValue))) {
			return true;
		}
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacingFromMeta(meta));
	}

	public EnumFacing getFacingFromMeta(int meta) {
		if (meta == 0) {
			return EnumFacing.EAST;
		}
		if (meta == 1) {
			return EnumFacing.WEST;
		}
		if (meta == 2) {
			return EnumFacing.NORTH;
		}
		if (meta == 3) {
			return EnumFacing.SOUTH;
		}
		if (meta == 4) {
			return EnumFacing.UP;
		}
		if (meta == 5) {
			return EnumFacing.DOWN;
		}
		return EnumFacing.EAST;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, CONNECTION });
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING, facing.getOpposite()).withProperty(CONNECTION, false);
	}
}
