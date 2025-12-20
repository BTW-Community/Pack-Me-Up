package net.dravigen.lets_automate.block;

import net.minecraft.src.*;

public class TestDirection extends BlockDirectional {
    public TestDirection(int par1) {
        super(par1, Material.wood);
        this.setUnlocalizedName("testBlock");
    }

    private Icon baseIcon;
    private Icon facingIcon;

    public void registerIcons(IconRegister par1IconRegister) {
        this.facingIcon = par1IconRegister.registerIcon("lets_automate:facing_test");
        this.baseIcon = par1IconRegister.registerIcon("lets_automate:base_test");
    }

    public Icon getIcon(int par1, int par2) {
        Icon icon = this.baseIcon;
        switch (par2){
            case 0 -> icon = par1 == 3 ? this.facingIcon : icon;
            case 1 -> icon = par1 == 4 ? this.facingIcon : icon;
            case 2 -> icon = par1 == 2 ? this.facingIcon : icon;
            case 3 -> icon = par1 == 5 ? this.facingIcon : icon;
            case 4 -> icon = par1 == 0 ? this.facingIcon : icon;
        }
        return icon;
    }

    public boolean canRotateOnTurntable(IBlockAccess blockAccess, int i, int j, int k) {
        return true;
    }

    public int rotateMetadataAroundYAxis(int iMetadata, boolean bReverse) {
        int iDirection = iMetadata & 3;
        if (bReverse) {
            ++iDirection;
            if (iDirection > 3) {
                iDirection = 0;
            }
        } else {
            --iDirection;
            if (iDirection < 0) {
                iDirection = 3;
            }
        }

        return iMetadata & -4 | iDirection;
    }
}
