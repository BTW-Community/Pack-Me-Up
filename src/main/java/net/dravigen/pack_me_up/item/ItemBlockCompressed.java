package net.dravigen.pack_me_up.item;

import net.dravigen.pack_me_up.block.CompressedBlock;
import net.minecraft.src.*;
import net.minecraft.src.ItemBlockWithMetadata;

public class ItemBlockCompressed extends ItemBlockWithMetadata {
	public ItemBlockCompressed(int i, Block block) {
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
		
		CompressedBlock compressedBlock = (CompressedBlock) Block.blocksList[par1ItemStack.itemID];
		
		ItemStack equivItem = new ItemStack(Block.blocksList[compressedBlock.equivID], 1, compressedBlock.equivMeta);
		
		String name = equivItem.getDisplayName();
		
		return StatCollector.translateToLocalFormatted("compressedBlock.format", prefix, name);
	}
	
	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return getTranslatedName(par1ItemStack);
	}
}
