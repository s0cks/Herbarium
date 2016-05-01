package herbarium.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "herb", name = "Herbarium", version = "0.0.0.0", dependencies = "required-after:Forge@[1.8.9-11.15.1.1722,)")
public final class Herbarium{
    @Mod.Instance("herb")
    public static Herbarium instance;

    @SidedProxy(clientSide = "herbarium.client.ClientProxy", serverSide = "herbarium.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e){

    }
}