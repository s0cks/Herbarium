package herbarium.common.core;

import herbarium.api.IFlower;
import herbarium.common.Herbarium;
import herbarium.common.blocks.BlockHerbariumFlower;
import herbarium.common.items.ItemDust;
import herbarium.common.items.ItemMortarPestle;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public final class DustRecipe
implements IRecipe {
    private static final class CraftDust{
        int mPosX = -1;
        int mPosY = -2;
        int flowerPosX = -1;
        int flowerPosY = -1;
        IFlower flower;
    }

    private CraftDust getDust(InventoryCrafting inv){
        CraftDust dust = new CraftDust();
        for(int x = 0; x < inv.getWidth(); x++){
            for(int y = 0; y < inv.getHeight(); y++){
                ItemStack stack = inv.getStackInRowAndColumn(x, y);
                if(stack != null){
                    if(stack.getItem() instanceof ItemMortarPestle){
                        if(dust.mPosX != -1){
                            return null;
                        }

                        dust.mPosX = x;
                        dust.mPosY = y;
                        continue;
                    }

                    if(stack.getItem() instanceof ItemBlock){
                        Block block = ((ItemBlock) stack.getItem()).getBlock();
                        if(block instanceof BlockHerbariumFlower){
                            if(dust.flowerPosX != -1){
                                return null;
                            }

                            dust.flower = ((BlockHerbariumFlower) block).flower;
                            dust.flowerPosX = x;
                            dust.flowerPosY = y;
                            continue;
                        }
                    }

                    return null;
                }
            }
        }

        if(dust.mPosX == -1 || dust.flowerPosX == -1){
            return null;
        }

        return dust;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return getDust(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        CraftDust dust = getDust(inv);
        if(dust == null) return null;
        ItemStack result = new ItemStack(Herbarium.itemDust, 1);
        ItemDust.setFlower(result, dust.flower);
        return result;
    }

    @Override
    public int getRecipeSize() {
        return 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        ItemStack[] stack = new ItemStack[inv.getSizeInventory()];
        for(int i = 0; i < stack.length; i++){
            ItemStack s = inv.getStackInSlot(i);
            stack[i] = ForgeHooks.getContainerItem(s);
        }
        return stack;
    }
}