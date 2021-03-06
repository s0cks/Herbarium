package herbarium.common.net;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class HerbariumNetwork {
  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("herb");

  public static void init() {
    INSTANCE.registerMessage(PacketSyncPageData.class, PacketSyncPageData.class, 0x00, Side.CLIENT);
    INSTANCE.registerMessage(PacketSyncEffects.class, PacketSyncEffects.class, 0x01, Side.CLIENT);
  }
}