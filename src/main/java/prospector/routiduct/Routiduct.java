package prospector.routiduct;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import prospector.routiduct.block.tiles.TileRoutiduct;
import prospector.routiduct.gui.RoutiductGuiHandler;
import prospector.routiduct.proxy.RoutiductServer;

@Mod(modid = RoutiductConstants.MOD_ID, name = RoutiductConstants.MOD_NAME, version = RoutiductConstants.MOD_VERSION, acceptedMinecraftVersions = RoutiductConstants.MINECRAFT_VERSIONS)
public class Routiduct {

	@Mod.Instance(RoutiductConstants.MOD_ID)
	public static Routiduct instance;
	@SidedProxy(clientSide = RoutiductConstants.CLIENT_PROXY_CLASS, serverSide = RoutiductConstants.SERVER_PROXY_CLASS)
	public static RoutiductServer proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerTileEntity(TileRoutiduct.class, "TileRoutiduct");
		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new RoutiductGuiHandler());
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
}