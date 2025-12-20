package net.dravigen.lets_automate.item;

import btw.item.items.ArmorItem;
import net.minecraft.src.EnumArmorMaterial;

public class XenalloyLegging extends ArmorItem {
    public XenalloyLegging(int itemID) {
        super(itemID, EnumArmorMaterial.DIAMOND, 3, 2, 4);
        this.setUnlocalizedName("XenalloyLegging");
    }
}
