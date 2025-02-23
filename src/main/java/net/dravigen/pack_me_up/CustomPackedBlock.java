package net.dravigen.pack_me_up;

import btw.block.BTWBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class CustomPackedBlock extends Block {

    public static final int SUBTYPE_APPLE = 0;
    public static final int SUBTYPE_CARROT = 1;
    public static final int SUBTYPE_POTATO = 2;
    public static final int SUBTYPE_BROWN_MUSHROOM = 3;
    public static final int SUBTYPE_RED_MUSHROOM = 4;
    public static final int SUBTYPE_VENOM_SAC = 5;
    public static final int SUBTYPE_BAT_WING = 6;
    public static final int SUBTYPE_NITRE = 7;
    public static final int SUBTYPE_WITCH_WART = 8;
    public static final int SUBTYPE_BRIMSTONE = 9;
    public static final int SUBTYPE_SOUL_FLUX = 10;
    public static final int SUBTYPE_SLIME = 11;
    @Environment(EnvType.CLIENT)
    private Icon iconAppleBlock;
    @Environment(EnvType.CLIENT)
    private Icon iconCarrotBlock;
    @Environment(EnvType.CLIENT)
    private Icon iconPotatoBlock;
    @Environment(EnvType.CLIENT)
    private Icon iconBMushroomBlock;
    @Environment(EnvType.CLIENT)
    private Icon iconRMushroomBlock;
    @Environment(EnvType.CLIENT)
    private Icon iconVenomSac;
    @Environment(EnvType.CLIENT)
    private Icon iconBatWing;
    @Environment(EnvType.CLIENT)
    private Icon iconNitre;
    @Environment(EnvType.CLIENT)
    private Icon iconWitchWart;
    @Environment(EnvType.CLIENT)
    private Icon iconBrimstone;
    @Environment(EnvType.CLIENT)
    private Icon iconSoulFlux;
    @Environment(EnvType.CLIENT)
    private Icon iconSlime;

    protected CustomPackedBlock(int par1) {
        super(par1, Material.ground);
        this.setHardness(0.8F);
        this.setBuoyancy(1.0F);
        this.setShovelsEffectiveOn(true);
        this.setAxesEffectiveOn(true);
        this.setStepSound(BTWBlocks.cropStepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setUnlocalizedName("CustomPackedBlocks");
    }

    public boolean doesBlockBreakSaw(World world, int i, int j, int k) {
        int metadata = world.getBlockMetadata(i,j,k);
        return metadata==9;
    }

    @Override
    public float getMovementModifier(World world, int i, int j, int k) {
        int metadata = world.getBlockMetadata(i,j,k);
        return metadata==11||metadata==10||metadata==7 ? 0.8F : super.getMovementModifier(world, i, j, k);
    }

    public boolean canBePistonShoveled(World world, int i, int j, int k) {
        int metadata = world.getBlockMetadata(i,j,k);
        return metadata==9;
    }
    @Override
    public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
        renderBlocks.renderBlockAsItemVanilla(this,iItemDamage,fBrightness);
    }

    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        String base = "pack_me_up:1";
        this.iconAppleBlock = register.registerIcon(base+"apple");
        this.iconCarrotBlock = register.registerIcon(base+"carrot");
        this.iconPotatoBlock = register.registerIcon(base+"potato");
        this.iconBMushroomBlock = register.registerIcon(base+"bshroom");
        this.iconRMushroomBlock = register.registerIcon(base+"rshroom");
        this.iconVenomSac = register.registerIcon(base+"venom");
        this.iconBatWing = register.registerIcon(base+"bat");
        this.iconNitre = register.registerIcon(base+"nitre");
        this.iconWitchWart = register.registerIcon(base+"wart");
        this.iconBrimstone = register.registerIcon(base+"brimstone");
        this.iconSoulFlux = register.registerIcon(base+"flux");
        this.iconSlime = register.registerIcon(base+"slime");
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        return switch (metadata) {
            case SUBTYPE_APPLE -> this.iconAppleBlock;
            case SUBTYPE_CARROT -> this.iconCarrotBlock;
            case SUBTYPE_POTATO -> this.iconPotatoBlock;
            case SUBTYPE_BROWN_MUSHROOM -> this.iconBMushroomBlock;
            case SUBTYPE_RED_MUSHROOM -> this.iconRMushroomBlock;
            case SUBTYPE_VENOM_SAC -> this.iconVenomSac;
            case SUBTYPE_BAT_WING -> this.iconBatWing;
            case SUBTYPE_NITRE -> this.iconNitre;
            case SUBTYPE_WITCH_WART -> this.iconWitchWart;
            case SUBTYPE_BRIMSTONE -> this.iconBrimstone;
            case SUBTYPE_SOUL_FLUX -> this.iconSoulFlux;
            case SUBTYPE_SLIME -> this.iconSlime;
            default -> null;
        };
    }

    @Environment(EnvType.CLIENT)
    public int idPicked(World world, int i, int j, int k) {
        return this.idDropped(world.getBlockMetadata(i, j, k), world.rand, 0);
    }
    @Override
    public int getRenderType() {
        return 0;
    }


    @Override
    public StepSound getStepSound(World world, int i, int j, int k) {
        int metadata = world.getBlockMetadata(i,j,k);
        if (metadata == 7 || metadata==10){
            return soundSandFootstep;
        }
        if (metadata==9){
            return soundStoneFootstep;
        }
        if (metadata==11){
            return BTWBlocks.clayStepSound;
        }
        return super.getStepSound(world, i, j, k);
    }

    public int damageDropped(int par1) {
        return par1;
    }
    @Environment(EnvType.CLIENT)
    public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(blockID, 1, 0));
        list.add(new ItemStack(blockID, 1, 1));
        list.add(new ItemStack(blockID, 1, 2));
        list.add(new ItemStack(blockID, 1, 3));
        list.add(new ItemStack(blockID, 1, 4));
        list.add(new ItemStack(blockID, 1, 5));
        list.add(new ItemStack(blockID, 1, 6));
        list.add(new ItemStack(blockID, 1, 7));
        list.add(new ItemStack(blockID, 1, 8));
        list.add(new ItemStack(blockID, 1, 9));
        list.add(new ItemStack(blockID, 1, 10));
        list.add(new ItemStack(blockID, 1, 11));
    }
}
