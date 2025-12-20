package net.dravigen.pack_me_up.block;

import api.block.blocks.FallingFullBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class CompressedLooseBlock extends FallingFullBlock {
	private final String[] compressionLvl = new String[]{"1", "2", "3", "4", "5"};
	public int equivID;
	public int equivMeta;
	float speed;
	boolean pistonShovelable;
	boolean breakSaw;
	int toolLevel;
	private Icon[] blockIcons;
	public Icon[] overlays;
	
	public CompressedLooseBlock(int id, int equivID, int equivMeta, Material material, float hardness, float resistance, boolean shovelEff, boolean pickEff,
			boolean axeEff, StepSound sound, float speed, boolean pistonShovelable, boolean breakSaw, int toolLevel) {
		super(id, material);
		this.setUnlocalizedName(Block.blocksList[equivID].getUnlocalizedName() + ".compressed");
		this.equivID = equivID;
		this.equivMeta = equivMeta;
		this.speed = speed;
		this.setShovelsEffectiveOn(shovelEff);
		this.setPicksEffectiveOn(pickEff);
		this.setAxesEffectiveOn(axeEff);
		this.setHardness(hardness);
		this.blockResistance = resistance;
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(sound);
		this.pistonShovelable = pistonShovelable;
		this.breakSaw = breakSaw;
		this.toolLevel = toolLevel;
	}
	
	@Override
	public float getBlockHardness(World world, int par2, int par3, int par4) {
		int compression = world.getBlockMetadata(par2, par3, par4) + 2;
		return (this.blockHardness * compression / 1.5f * compression / 1.5f);
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int par2, int par3, int par4) {
		int compression = world.getBlockMetadata(par2, par3, par4) + 2;
		return (this.blockHardness * 5 * compression) / 5.0f;
	}
	
	@Override
	public int getHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k) {
		return this.toolLevel;
	}
	
	@Override
	public void getSubBlocks(int itemID, CreativeTabs tab, List list) {
		for (int i = 0; i < compressionLvl.length; i++) {
			list.add(new ItemStack(itemID, 1, i));
		}
	}
	
	@Override
	public boolean doesBlockBreakSaw(World world, int x, int y, int z) {
		return this.breakSaw;
	}
	
	@Override
	public boolean canBePistonShoveled(World world, int i, int j, int k) {
		return this.pistonShovelable;
	}
	
	@Override
	public float getMovementModifier(World world, int i, int j, int k) {
		return this.speed;
	}
	
	public int damageDropped(int par1) {
		return par1;
	}
	
	@Override
	public void onBlockDestroyedWithImproperTool(World world, EntityPlayer player, int i, int j, int k, int iMetadata) {
		if (iMetadata > 0) {
			this.dropItemsIndividually(world, i, j, k, this.blockID, 9, iMetadata - 1, 1);
		}
		else {
			this.dropItemsIndividually(world, i, j, k, this.equivID, 9, this.equivMeta, 1);
		}
	}
	
	@Environment(EnvType.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return this.idDropped(world.getBlockMetadata(i, j, k), world.rand, 0);
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.overlays = new Icon[compressionLvl.length];
		this.blockIcons = new Icon[6];
		
		for (int i = 0; i < compressionLvl.length; i++) {
			this.overlays[i] = iconRegister.registerIcon("pack_me_up:" + "overlay" + compressionLvl[i]);
			
			for (int j = 0; j < 6; j++) {
				this.blockIcons[j] = Block.blocksList[equivID].getIcon(j, equivMeta);
			}
		}
	}
	
	@Environment(EnvType.CLIENT)
	public Icon getIcon(int side, int metaData) {
		return this.blockIcons[side];
	}
	
	@Override
	public int getRenderBlockPass() {
		return 1;
	}
	
	@Override
	public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
		renderer.setRenderBounds(this.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k));
		return renderer.renderStandardBlock(this, i, j, k);
	}
}
