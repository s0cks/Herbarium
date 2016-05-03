package herbarium.common.net;

import herbarium.api.HerbariumApi;
import herbarium.api.commentarium.IPage;
import herbarium.common.Herbarium;
import herbarium.common.core.commentarium.PageTracker;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashSet;
import java.util.Set;

public final class PacketSyncPageData
        implements IMessageHandler<PacketSyncPageData, IMessage>,
                   IMessage{
    private Set<IPage> pages;

    public PacketSyncPageData(){
        this(null);
    }

    public PacketSyncPageData(Set<IPage> pages){
        this.pages = pages;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound comp = ByteBufUtils.readTag(buf);
        if(comp.hasKey("Pages")){
            NBTTagList list = comp.getTagList("Pages", 8);
            this.pages = new HashSet<IPage>();
            for(int i = 0; i < list.tagCount(); i++){
                this.pages.add(HerbariumApi.PAGE_MANAGER.get(list.getStringTagAt(i)));
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound comp = new NBTTagCompound();
        if(this.pages != null){
            NBTTagList list = new NBTTagList();
            for(IPage page : this.pages){
                list.appendTag(new NBTTagString(page.uuid()));
            }
            comp.setTag("Pages", list);
        }
        ByteBufUtils.writeTag(buf, comp);
    }

    @Override
    public IMessage onMessage(PacketSyncPageData message, MessageContext ctx) {
        PageTracker.set(Herbarium.proxy.getClient().thePlayer, message.pages);
        return null;
    }
}