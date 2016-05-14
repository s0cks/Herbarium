package herbarium.common.net;

import herbarium.api.HerbariumApi;
import herbarium.common.Herbarium;
import herbarium.common.core.brew.effects.EffectTracker;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class PacketSyncEffects
    implements IMessageHandler<PacketSyncEffects, IMessage>,
               IMessage {
  private EffectTracker.PlayerEffectData data;

  public PacketSyncEffects() {
    this(null);
  }

  public PacketSyncEffects(EffectTracker.PlayerEffectData data) {
    this.data = data;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    if (this.data == null) this.data = new EffectTracker.PlayerEffectData();
    this.data.readFromNBT(ByteBufUtils.readTag(buf));
  }

  @Override
  public void toBytes(ByteBuf buf) {
    if (this.data != null) {
      NBTTagCompound comp = new NBTTagCompound();
      this.data.writeToNBT(comp);
      ByteBufUtils.writeTag(buf, comp);
    }
  }

  @Override
  public IMessage onMessage(PacketSyncEffects message, MessageContext ctx) {
    ((EffectTracker) HerbariumApi.EFFECT_TRACKER).setData(Herbarium.proxy.getClient().thePlayer, message.data);
    return null;
  }
}