package herbarium.common.net;

import herbarium.api.brew.BrewmanLevel;
import herbarium.common.core.brew.PlayerBrewLevel;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class PacketSyncBrewmanLevel
        implements IMessageHandler<PacketSyncBrewmanLevel, IMessage>,
                   IMessage {
    private BrewmanLevel level;

    public PacketSyncBrewmanLevel() {
        this(null);
    }

    public PacketSyncBrewmanLevel(BrewmanLevel level){
        this.level = level;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound comp = ByteBufUtils.readTag(buf);
        if(comp.hasKey("LEVEL_TAG")){
            this.level = BrewmanLevel.valueOf(comp.getString("LEVEL_TAG"));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound comp = new NBTTagCompound();
        if(this.level != null) comp.setString("LEVEL_TAG", this.level.name());
        ByteBufUtils.writeTag(buf, comp);
    }

    @Override
    public IMessage onMessage(PacketSyncBrewmanLevel message, MessageContext ctx) {
        PlayerBrewLevel.get(FMLClientHandler.instance().getClient().thePlayer).set(message.level);
        return null;
    }
}