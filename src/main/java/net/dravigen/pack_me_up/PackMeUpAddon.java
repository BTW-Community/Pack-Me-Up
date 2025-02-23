package net.dravigen.pack_me_up;

import btw.AddonHandler;
import btw.BTWAddon;
import btw.block.BTWBlocks;
import btw.crafting.recipe.RecipeManager;
import net.minecraft.src.*;

import java.util.ArrayList;

public class PackMeUpAddon extends BTWAddon {
    public PackMeUpAddon() {
        super();
    }


    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
        PMUBlocks.instantiateModBlocks();
    }
}