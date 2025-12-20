package net.dravigen.lets_automate.block;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class SelectionBlock extends Block {
    public SelectionBlock(int par1) {
        super(par1, Material.air);
        this.setUnlocalizedName("selection block");
    }
}
