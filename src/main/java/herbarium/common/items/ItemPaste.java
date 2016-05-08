package herbarium.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public final class ItemPaste
extends Item {
    public static final String[] names = new String[]{
        "alstromeria",
        "anemone",
        "belladonna",
        "blueberry_blossom",
        "buttercup",
        "cavern_bloom",
        "igneous_spear",
        "lancet_root",
        "spring_lotus",
        "tail_iris",
        "tropical_berries",
        "winter_lily"
    };

    public ItemPaste(){
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item.herbarium.paste." + names[stack.getItemDamage()];
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for(int i = 0; i < names.length; i++){
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }
}