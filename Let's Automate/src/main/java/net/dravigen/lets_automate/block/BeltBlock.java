package net.dravigen.lets_automate.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

public class BeltBlock extends BlockDirectional {
    public BeltBlock(int i) {
        super(i, Material.wood);
        this.textureName="belt";
        this.setUnlocalizedName("beltBlock");
    }
    private Icon side2Icon;
    private Icon baseIcon;
    private Icon sideIcon;

    public boolean isOpaqueCube() {
        return true;
    }

    public int onBlockPlaced(World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata) {
        return this.setFacing(iMetadata, iFacing);
    }

    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        int var7 = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + (double)2.5F) & 3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
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


    public boolean canRotateOnTurntable(IBlockAccess blockAccess, int i, int j, int k) {
        return true;
    }

    public void registerIcons(IconRegister par1IconRegister) {
        this.side2Icon = par1IconRegister.registerIcon("lets_automate:belt_side2");
        this.baseIcon = par1IconRegister.registerIcon("lets_automate:belt_base");
        this.sideIcon = par1IconRegister.registerIcon("lets_automate:belt_side");
    }

    public Icon getIcon(int par1, int par2) {
        Icon icon = this.baseIcon;
        switch (par2){
            case 0 -> icon = par1 == 3 ? this.side2Icon : par1 == 2 ? this.sideIcon : icon;
            case 1 -> icon = par1 == 4 ? this.side2Icon : par1 == 5 ? this.sideIcon : icon;
            case 2 -> icon = par1 == 2 ? this.side2Icon : par1 == 3 ? this.sideIcon : icon;
            case 3 -> icon = par1 == 5 ? this.side2Icon : par1 == 4 ? this.sideIcon : icon;
        }
        return icon;
    }


    @Environment(EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        return this.currentBlockRenderer.shouldSideBeRenderedBasedOnCurrentBounds(iNeighborI, iNeighborJ, iNeighborK, iSide);
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
        super.renderBlockAsItem(renderBlocks, iItemDamage, fBrightness);
    }
}
