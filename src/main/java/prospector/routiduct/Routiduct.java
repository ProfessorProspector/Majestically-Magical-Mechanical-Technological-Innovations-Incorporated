package prospector.routiduct;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import prospector.routiduct.block.tiles.TilePackager;
import prospector.routiduct.gui.RoutiductGuiHandler;
import prospector.routiduct.init.RoutiductItems;
import prospector.routiduct.init.RoutiductRegistry;
import prospector.routiduct.proxy.RoutiductServer;
import reborncore.modcl.ModCL;
import reborncore.modcl.RegistryCL;

/**
 * Created by Prospector
 */
@Mod(modid = Routiduct.MOD_ID, name = Routiduct.MOD_NAME, version = Routiduct.MOD_VERSION, dependencies = Routiduct.MOD_DEPENDENCIES, acceptedMinecraftVersions = Routiduct.MINECRAFT_VERSIONS)
public class Routiduct extends ModCL {
	public static final String MOD_NAME = "Routiduct";
	public static final String MOD_ID = "routiduct";
	public static final String PREFIX = "routiduct:";
	public static final String MOD_VERSION = "%version%";
	public static final String MINECRAFT_VERSIONS = "[1.11.2]";
	public static final String MOD_DEPENDENCIES = "required-after:reborncore;";
	public static final String SERVER_PROXY_CLASS = "prospector.routiduct.proxy.RoutiductServer";
	public static final String CLIENT_PROXY_CLASS = "prospector.routiduct.proxy.RoutiductClient";
	public static RegistryCL REGISTRY = new RoutiductRegistry();

	@Mod.Instance(MOD_ID)
	public static Routiduct MOD_CL;
	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
	public static RoutiductServer PROXY;

	public Routiduct() {
		FluidRegistry.enableUniversalBucket();
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		getRegistry().init(MOD_CL);
		GameRegistry.registerTileEntity(TilePackager.class, "TilePackagerRD");
		PROXY.registerRenders();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(MOD_CL, new RoutiductGuiHandler());
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@Override
	public String getModName() {
		return MOD_NAME;
	}

	@Override
	public String getModID() {
		return MOD_ID;
	}

	@Override
	public String getModVersion() {
		return MOD_VERSION;
	}

	@Override
	public String getModDependencies() {
		return MOD_DEPENDENCIES;
	}

	@Override
	public String getServerProxy() {
		return SERVER_PROXY_CLASS;
	}

	@Override
	public String getClientProxy() {
		return CLIENT_PROXY_CLASS;
	}

	@Override
	public RegistryCL getRegistry() {
		return REGISTRY;
	}

	@Override
	public ItemStack getTabStack() {
		return new ItemStack(RoutiductItems.WRENCH);
	}
}