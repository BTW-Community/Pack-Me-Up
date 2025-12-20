package net.dravigen.pack_me_up.block;

import btw.BTWMod;
import btw.block.BTWBlockIDs;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.StepSound;

import static net.minecraft.src.Block.*;
import static btw.block.BTWBlocks.*;

public enum EnumCompressedBlock {
	
	NETHERRACK(87),
	CLAY(1038),
	ENDER(BTWBlockIDs.AESTHETIC_OPAQUE_BLOCK_ID, 14, miscMaterial, 2, 10 * 2, false, true, true, soundStoneFootstep, 1.2f),
	DUNG(BTWBlockIDs.AESTHETIC_OPAQUE_EARTH_BLOCK_ID, 7, Material.ground, 0.6f, 3 * 2, true, false, false, dirtStepSound, 0.8f),
	FLINT(BTWBlockIDs.AESTHETIC_OPAQUE_BLOCK_ID, 7, miscMaterial, 2, 10 * 2, false, true, true, soundStoneFootstep, 1.2f),
	WHITESTONE(BTWBlockIDs.AESTHETIC_OPAQUE_BLOCK_ID, 9, miscMaterial, 2, 10 * 2, false, true, true, stoneStepSound, 1.2f),
	WHITECOBBLE(BTWBlockIDs.AESTHETIC_OPAQUE_BLOCK_ID, 10, miscMaterial, 2, 10 * 2, false, true, true, stoneStepSound, 1.2f),
	DIRT( 3),
	PACKED_DIRT(BTWBlockIDs.AESTHETIC_OPAQUE_EARTH_BLOCK_ID, 6, Material.ground, 0.6f, 3 * 2, true, false, false, dirtStepSound, 1.2f),
	SPIDER(BTWBlockIDs.SPIDER_EYE_BLOCK_ID),
	BONE(BTWBlockIDs.AESTHETIC_OPAQUE_BLOCK_ID, 15, miscMaterial, 2, 10 * 2, false, true, true, boneStepSound, 1.2f, true, false),
	FLESH(BTWBlockIDs.ROTTEN_FLESH_BLOCK_ID),
	STONE2(1, 1, Material.rock, 3, 39 * 2, false, true, false, stoneStrata2StepSound, 1.2f),
	STONE3(1, 2, Material.rock, 4.5f, 60 * 2, false, true, false, stoneStrata3StepSound, 1.2f),
	LOOSECOBBLE1(BTWBlockIDs.COBBLESTONE_LOOSE_BLOCK_ID, 0, Material.rock, 1, 15 * 2, false, true, false, cobblestoneStepSound, 1.2f),
	LOOSECOBBLE2(BTWBlockIDs.COBBLESTONE_LOOSE_BLOCK_ID, 4, Material.rock, 1, 15 * 2, false, true, false, cobblestoneStrata2StepSound, 1.2f),
	LOOSECOBBLE3(BTWBlockIDs.COBBLESTONE_LOOSE_BLOCK_ID, 8, Material.rock, 1, 15 * 2, false, true, false, cobblestoneStrata3StepSound, 1.2f),
	SOAP(BTWBlockIDs.AESTHETIC_OPAQUE_BLOCK_ID, 5, miscMaterial, 2, 10 * 2, false, true, true, soundStoneFootstep, 1.2f, false, false),
	SAND(12),
	OYSTER(BTWBlockIDs.CREEPER_OYSTERS_BLOCK_ID),
	GRAVEL(13, 0),
	APPLE(1699, 0, miscMaterial, 0.8f, 3 * 2, true, false, true, cropStepSound, 1, true, false),
	CARROT(1699, 1, miscMaterial, 0.8f, 3 * 2, true, false, true, cropStepSound, 1, true, false),
	POTATO(1699, 2, miscMaterial, 0.8f, 3 * 2, true, false, true, cropStepSound, 1, true, false),
	BSHROOM(1699, 3, miscMaterial, 0.8f, 3 * 2, false, false, true, cropStepSound, 1, true, false),
	RSHROOM(1699, 4, miscMaterial, 0.8f, 3 * 2, false, false, true, cropStepSound, 1, true, false),
	MELON(103),
	SANDSTONE_NORMAL(24, 0),
	SANDSTONE_SMOOTH(24, 1),
	SANDSTONE_CARVED(24, 2),
	PUMPKIN(BTWBlockIDs.PUMPKIN_FRESH_BLOCK_ID, 0),
	HAY(170),
	VENOM(1699, 5, miscMaterial, 0.8f, 3 * 2, true, false, true, cropStepSound, 1, true, false),
	BAT(1699, 6, miscMaterial, 0.8f, 3 * 2, true, false, true, cropStepSound, 1, true, false),
	NITRE(1699, 7, miscMaterial, 0.8f, 3 * 2, true, false, false, soundSandFootstep, 0.8f, true, false),
	WART(1699, 8, miscMaterial, 0.8f, 3 * 2, true, false, true, cropStepSound, 1, true, false),
	BRIMSTONE(1699, 9, miscMaterial, 10, 2 * 2, false, true, false, soundStoneFootstep, 1, false, true),
	FLUX(1699, 10, miscMaterial, 0.8f, 3 * 2, true, false, false, soundSandFootstep, 0.8f, true, false),
	SLIME(1699, 11, miscMaterial, 0.8f, 3 * 2, true, true, true, clayStepSound, 0.8f, true, false),
	STONE1(1),
	ENDSTONE(121),
	OAK_LOG(17),
	SPRUCE_LOG(17, 1),
	BIRCH_LOG(17, 2),
	JUNGLE_LOG(17, 3),
	BLOOD_LOG(BTWBlockIDs.BLOOD_WOOD_BLOCK_ID),
	OAK_PLANK(5),
	SPRUCE_PLANK(5, 1),
	BIRCH_PLANK(5, 2),
	JUNGLE_PLANK(5, 3),
	BLOOD_PLANK(5, 4),
	REDSTONE( 152),
	LAPIS(22),
	GOLD(41),
	IRON(42),
	DIAMOND(57),
	EMERALD(133),
	COAL(173),
	DIAMOND_INGOT(BTWBlockIDs.DIAMOND_INGOT_BLOCK_ID),
	SFS(BTWBlockIDs.SOULFORGED_STEEL_BLOCK_BLOCK_ID),
	ROPE(BTWBlockIDs.AESTHETIC_OPAQUE_BLOCK_ID, 6, miscMaterial, 2, 10 * 2, false, true, true, soundStoneFootstep, 1.2f);
	
	public final int equivID;
	public final int equivMeta;
	public Material material;
	public float hardness;
	public float resistance;
	public boolean shovelEff;
	public boolean pickEff;
	public boolean axeEff;
	public StepSound sound;
	public float speed;
	public boolean pistonShovelable;
	public boolean breakSaw;
	public final int toolLevel = 3;
	
	
	EnumCompressedBlock(int equivID, int equivMeta) {
		this.equivID = equivID;
		this.equivMeta = equivMeta;
		
		Block block = Block.blocksList[equivID];
		
		if (block != null) {
			this.material = block.blockMaterial;
			this.hardness = block.blockHardness;
			this.resistance = block.blockResistance * 2;
			this.shovelEff = block.areShovelsEffectiveOn();
			this.pickEff = block.arePicksEffectiveOn();
			this.axeEff = block.areAxesEffectiveOn();
			this.sound = block.stepSound;
			this.pistonShovelable = this.shovelEff;
			this.breakSaw = this.material.isSolid() && this.material.breaksSaw();
			
			float fModifier = 1.0f;
			
			if (this.material != Material.ground && this.material != Material.grass) {
				fModifier *= 1.2f;
			}
			
			this.speed = fModifier;
		}
	}
	
	EnumCompressedBlock(int equivID) {
		this.equivID = equivID;
		this.equivMeta = 0;
		
		Block block = Block.blocksList[equivID];
		
		if (block != null) {
			this.material = block.blockMaterial;
			this.hardness = block.blockHardness;
			this.resistance = block.blockResistance * 2;
			this.shovelEff = block.areShovelsEffectiveOn();
			this.pickEff = block.arePicksEffectiveOn();
			this.axeEff = block.areAxesEffectiveOn();
			this.sound = block.stepSound;
			this.pistonShovelable = this.shovelEff;
			this.breakSaw = this.material.isSolid() && this.material.breaksSaw();
			
			float fModifier = 1.0f;
			
			if (this.material != Material.ground && this.material != Material.grass) {
				fModifier *= 1.2f;
			}
			
			this.speed = fModifier;
		}
	}
	
	
	EnumCompressedBlock(int equivID, int equivMeta, Material material, float hardness, float resistance, boolean shovelEff, boolean pickEff,
			boolean axeEff, StepSound sound, float speed) {
		this.equivID = equivID;
		this.equivMeta = equivMeta;
		this.material = material;
		this.hardness = hardness;
		this.resistance = resistance;
		this.shovelEff = shovelEff;
		this.pickEff = pickEff;
		this.axeEff = axeEff;
		this.sound = sound;
		this.speed = speed;
		this.pistonShovelable = this.shovelEff;
		this.breakSaw = this.material.isSolid() && this.material.breaksSaw();
	}
	
	EnumCompressedBlock(int equivID, int equivMeta, Material material, float hardness, float resistance, boolean shovelEff, boolean pickEff,
			boolean axeEff, StepSound sound, float speed, boolean pistonShovelable, boolean breakSaw) {
		this.equivID = equivID;
		this.equivMeta = equivMeta;
		this.material = material;
		this.hardness = hardness;
		this.resistance = resistance;
		this.shovelEff = shovelEff;
		this.pickEff = pickEff;
		this.axeEff = axeEff;
		this.sound = sound;
		this.speed = speed;
		this.pistonShovelable = pistonShovelable;
		this.breakSaw = breakSaw;
	}
}