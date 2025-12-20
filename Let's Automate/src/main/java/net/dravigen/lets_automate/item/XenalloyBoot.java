package net.dravigen.lets_automate.item;

import btw.item.items.ArmorItem;
import net.minecraft.src.EnumArmorMaterial;

public class XenalloyBoot extends ArmorItem {
    public XenalloyBoot(int itemID) {
        super(itemID, EnumArmorMaterial.DIAMOND, 3, 3, 2);
        this.setUnlocalizedName("XenalloyBoot");
    }
}
