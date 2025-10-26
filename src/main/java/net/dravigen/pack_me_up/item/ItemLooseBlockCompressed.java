package net.dravigen.pack_me_up.item;

import net.dravigen.pack_me_up.block.CompressedLooseBlock;
import net.minecraft.src.Block;
import net.minecraft.src.ItemBlockWithMetadata;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StatCollector;

public class ItemLooseBlockCompressed extends ItemBlockWithMetadata {
	public ItemLooseBlockCompressed(int i, Block block) {
		super(i, block);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return getTranslatedName(par1ItemStack);
	}
	
	private static String getTranslatedName(ItemStack par1ItemStack) {
		int compressionLevel = par1ItemStack.getItemDamage();
		String prefixKey = "compressionLvl." + compressionLevel;
		
		String prefix = StatCollector.translateToLocal(prefixKey);
		
		CompressedLooseBlock compressedBlock = (CompressedLooseBlock) Block.blocksList[par1ItemStack.itemID];
		
		ItemStack equivItem = new ItemStack(Block.blocksList[compressedBlock.equivID], 1, compressedBlock.equivMeta);
		
		String name = equivItem.getDisplayName();
		
		return StatCollector.translateToLocalFormatted("compressedBlock.format", prefix, name);
	}
	
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return getTranslatedName(par1ItemStack);
	}
	
	
	
}
