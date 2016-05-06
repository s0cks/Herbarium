package herbarium.common.tiles;

import herbarium.api.brew.piping.IPipeConnector;
import herbarium.common.core.PipeController;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public final class TileEntityPipe
extends TileEntity{
    private final PipeController controller = new PipeController(this, false);

    public IPipeConnector connector(){
        return this.controller;
    }

    @Override
    public void writeToNBT(NBTTagCompound comp){
        NBTTagCompound ctrlComp = new NBTTagCompound();
        this.controller.writeToNBT(ctrlComp);
        comp.setTag("Controller", ctrlComp);
    }

    @Override
    public void readFromNBT(NBTTagCompound comp) {
        if (comp.hasKey("Controller")) {
            this.controller.readFromNBT(comp.getCompoundTag("Controller"));
        }
    }
}