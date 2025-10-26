package net.dravigen.pack_me_up.block;

import btw.block.BTWBlocks;
import btw.item.BTWItems;
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
	
	public static final int NUM_SUBTYPES = 12;
	
	public static final Item[] equivItems = new Item[]{
			Item.appleRed,
			BTWItems.carrot,
			Item.potato,
			BTWItems.brownMushroom,
			BTWItems.redMushroom,
			Item.fermentedSpiderEye,
			BTWItems.batWing,
			BTWItems.nitre,
			BTWItems.witchWart,
			BTWItems.brimstone,
			BTWItems.soulFlux,
			Item.slimeBall
	};
	
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
	
	public CustomPackedBlock(int par1) {
		super(par1, Material.ground);
		this.setHardness(0.8F);
		this.setBuoyancy(1.0F);
		this.setShovelsEffectiveOn(true);
		this.setAxesEffectiveOn(true);
		this.setStepSound(BTWBlocks.cropStepSound);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setUnlocalizedName("CustomPackedBlocks");
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
	
	public int damageDropped(int par1) {
		return par1;
	}
	
	@Environment(EnvType.CLIENT)
	public int idPicked(World world, int i, int j, int k) {
		return this.idDropped(world.getBlockMetadata(i, j, k), world.rand, 0);
	}
	
	@Environment(EnvType.CLIENT)
	public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
		list.add(new ItemStack(blockID, 1, SUBTYPE_APPLE));
		list.add(new ItemStack(blockID, 1, SUBTYPE_CARROT));
		list.add(new ItemStack(blockID, 1, SUBTYPE_POTATO));
		list.add(new ItemStack(blockID, 1, SUBTYPE_BROWN_MUSHROOM));
		list.add(new ItemStack(blockID, 1, SUBTYPE_RED_MUSHROOM));
		list.add(new ItemStack(blockID, 1, SUBTYPE_VENOM_SAC));
		list.add(new ItemStack(blockID, 1, SUBTYPE_BAT_WING));
		list.add(new ItemStack(blockID, 1, SUBTYPE_NITRE));
		list.add(new ItemStack(blockID, 1, SUBTYPE_WITCH_WART));
		list.add(new ItemStack(blockID, 1, SUBTYPE_BRIMSTONE));
		list.add(new ItemStack(blockID, 1, SUBTYPE_SOUL_FLUX));
		list.add(new ItemStack(blockID, 1, SUBTYPE_SLIME));
	}
	
	@Environment(EnvType.CLIENT)
	public void registerIcons(IconRegister register) {
		String base = "pack_me_up:";
		String suffix = "Block";
		
		this.iconAppleBlock = register.registerIcon(base + "apple" + suffix);
		this.iconCarrotBlock = register.registerIcon(base + "carrot" + suffix);
		this.iconPotatoBlock = register.registerIcon(base + "potato" + suffix);
		this.iconBMushroomBlock = register.registerIcon(base + "bshroom" + suffix);
		this.iconRMushroomBlock = register.registerIcon(base + "rshroom" + suffix);
		this.iconVenomSac = register.registerIcon(base + "venom" + suffix);
		this.iconBatWing = register.registerIcon(base + "bat" + suffix);
		this.iconNitre = register.registerIcon(base + "nitre" + suffix);
		this.iconWitchWart = register.registerIcon(base + "wart" + suffix);
		this.iconBrimstone = register.registerIcon(base + "brimstone" + suffix);
		this.iconSoulFlux = register.registerIcon(base + "flux" + suffix);
		this.iconSlime = register.registerIcon(base + "slime" + suffix);
	}
	
	@Override
	public float getMovementModifier(World world, int i, int j, int k) {
		int metadata = world.getBlockMetadata(i, j, k);
		return metadata == SUBTYPE_SLIME || metadata == SUBTYPE_SOUL_FLUX || metadata == SUBTYPE_NITRE ? 0.8F : super.getMovementModifier(world, i, j, k);
	}
	
	@Override
	public StepSound getStepSound(World world, int i, int j, int k) {
		int metadata = world.getBlockMetadata(i, j, k);
		if (metadata == SUBTYPE_NITRE || metadata == SUBTYPE_SOUL_FLUX) {
			return soundSandFootstep;
		}
		if (metadata == SUBTYPE_BRIMSTONE) {
			return soundStoneFootstep;
		}
		if (metadata == SUBTYPE_SLIME) {
			return BTWBlocks.clayStepSound;
		}
		return super.getStepSound(world, i, j, k);
	}
	
	public boolean doesBlockBreakSaw(World world, int i, int j, int k) {
		int metadata = world.getBlockMetadata(i, j, k);
		return metadata == SUBTYPE_BRIMSTONE;
	}
	
	public boolean canBePistonShoveled(World world, int i, int j, int k) {
		int metadata = world.getBlockMetadata(i, j, k);
		return metadata == SUBTYPE_BRIMSTONE;
	}
	
	@Override
	public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
		renderBlocks.renderBlockAsItemVanilla(this, iItemDamage, fBrightness);
	}
}
