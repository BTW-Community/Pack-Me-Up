package net.dravigen.pack_me_up;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemBlockWithMetadata extends ItemBlock {
    public ItemBlockWithMetadata(int id) {
        super(id);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
