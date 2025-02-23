package net.dravigen.pack_me_up;

import btw.block.BTWBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class CompressedBlock extends Block {
    String name;
    Block block;
    private final String[] compressionLvl = new String[]{"2", "3", "4", "5","6"};
    private Icon[] blockIcons;
    private Icon[] blockSide;
    private Icon[] blockTop;
    private Icon[] blockBottom;


    protected CompressedBlock(int par1, Block block, String name) {
        super(par1, block.blockMaterial);
        this.setUnlocalizedName(name);
        this.blockHardness=block.blockHardness;
        this.name = name;
        this.block = block;
        this.setShovelsEffectiveOn(block.areShovelsEffectiveOn());
        this.setPicksEffectiveOn(block.arePicksEffectiveOn());
        this.setAxesEffectiveOn(block.areAxesEffectiveOn());
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setStepSound(block.stepSound);
    }
    @Override
    public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
        renderBlocks.renderBlockAsItemVanilla(this,iItemDamage,fBrightness);
    }

    @Override
    public float getMovementModifier(World world, int i, int j, int k) {
        return this.name.equals("slime")||this.name.equals("flux")||this.name.equals("nitre") ? 0.8F : this.name.equals("packed_dirt") ? 1.2F : super.getMovementModifier(world, i, j, k);
    }

    @Override
    public StepSound getStepSound(World world, int i, int j, int k) {
        return this.name.equals("flux")||this.name.equals("nitre") ? soundSandFootstep : this.name.equals("brimstone") ? soundStoneFootstep : this.name.equals("slime") ? BTWBlocks.clayStepSound : super.getStepSound(world, i, j, k);

    }

    @Override
    public float getBlockHardness(World world, int par2, int par3, int par4) {
        int metadata = world.getBlockMetadata(par2,par3,par4)+2;
        return (this.blockHardness*metadata*metadata*2);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcons = new Icon[compressionLvl.length];
        this.blockSide = new Icon[compressionLvl.length];
        this.blockTop = new Icon[compressionLvl.length];
        this.blockBottom = new Icon[compressionLvl.length];
        for (int i = 0; i < compressionLvl.length; i++) {
            this.blockIcons[i] = iconRegister.registerIcon("pack_me_up:" + compressionLvl[i]+name);
            if (textureExists("pack_me_up:" + compressionLvl[i] + name + "_side")){
                this.blockSide[i] = iconRegister.registerIcon("pack_me_up:" + compressionLvl[i] + name + "_side");
            }else this.blockSide[i] = iconRegister.registerIcon("pack_me_up:" + compressionLvl[i]+name);

            if (textureExists("pack_me_up:" + compressionLvl[i] + name + "_top")){
                this.blockTop[i] = iconRegister.registerIcon("pack_me_up:" + compressionLvl[i] + name + "_top");
            }else this.blockTop[i] = iconRegister.registerIcon("pack_me_up:" + compressionLvl[i]+name);

            if (textureExists("pack_me_up:" + compressionLvl[i] + name + "_bottom")){
                this.blockBottom[i] = iconRegister.registerIcon("pack_me_up:" + compressionLvl[i] + name + "_bottom");
            }else this.blockBottom[i] = iconRegister.registerIcon("pack_me_up:" + compressionLvl[i]+name);
        }
    }
    private boolean textureExists(String texture) {
        String path = "/assets/" + texture.replace(":", "/textures/blocks/") + ".png";
        return getClass().getResource(path) != null;
    }

    @Environment(EnvType.CLIENT)
    public Icon getIcon(int side, int metaData) {
        if (side==0) {
            return this.blockBottom[metaData];
        }else if (side==1) {
            return this.blockTop[metaData];
        }else return this.blockSide[metaData];
    }

    @Environment(EnvType.CLIENT)
    public int idPicked(World world, int i, int j, int k) {
        return this.idDropped(world.getBlockMetadata(i, j, k), world.rand, 0);
    }
    @Override
    public int getRenderType() {
        return 0;
    }
    public int damageDropped(int par1) {
        return par1;
    }

    @Override
    public void getSubBlocks(int itemID, CreativeTabs tab, List list) {
        for (int i = 0; i < compressionLvl.length; i++) {
            list.add(new ItemStack(itemID, 1, i));
        }
    }
}
