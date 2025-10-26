package net.dravigen.pack_me_up;

import btw.crafting.recipe.RecipeManager;
import net.dravigen.pack_me_up.block.CompressedBlock;
import net.dravigen.pack_me_up.block.CompressedLooseBlock;
import net.dravigen.pack_me_up.block.CustomPackedBlock;
import net.dravigen.pack_me_up.block.EnumCompressedBlock;
import net.dravigen.pack_me_up.item.ItemBlockCompressed;
import net.dravigen.pack_me_up.item.ItemBlockWithMetadata;
import net.dravigen.pack_me_up.item.ItemLooseBlockCompressed;
import net.minecraft.src.*;
import org.jetbrains.annotations.NotNull;

public class PMURegistry {
	static CustomPackedBlock customPackedBlock;
	
	public static int id = 1700;
	
	public static void instantiateModBlocks() {
		customPackedBlock = new CustomPackedBlock(1699);
		initBlockItemMeta(1699);
		
		for (EnumCompressedBlock enumB : EnumCompressedBlock.values()) {
			Block block = createCompressedBlock(enumB, id);
			
			for (int j = 0; j < 5; j++) {
				if (j < 4) {
					RecipeManager.addShapelessRecipe(new ItemStack(id, 9, j),
													 new ItemStack[]{new ItemStack(id, 1, j + 1)});
				}
				if (j > 0) {
					RecipeManager.addPistonPackingRecipe(block, j, new ItemStack(id, 9, j - 1));
				}
				if (j == 0) {
					int outputMeta = enumB.equivMeta;
					Block blockOutput = Block.blocksList[enumB.equivID];
					
					if (blockOutput != null) {
						RecipeManager.addShapelessRecipe(new ItemStack(blockOutput, 9, outputMeta),
														 new ItemStack[]{new ItemStack(id, 1, 0)});
						
						if (blockOutput.blockID == Block.sand.blockID) {
							RecipeManager.addRecipe(new ItemStack(block), new Object[]{
									"AAA", "AAA", "AAA", 'A', new ItemStack(Block.sand, 1)
							});
						}
						else {
							RecipeManager.addPistonPackingRecipe(block, new ItemStack(blockOutput, 9, outputMeta));
						}
					}
				}
			}
			
			if (Item.itemsList[id] == null) {
				if (block.isFallingBlock()) {
					Item.itemsList[id] = new ItemLooseBlockCompressed(id - 256, block);
				}
				else {
					Item.itemsList[id] = new ItemBlockCompressed(id - 256, block);
				}
			}
			else {
				throw new RuntimeException("Item ID " + id + " is already occupied by " + Item.itemsList[id]);
			}
			
			id++;
		}
		
		for (int i = 0; i < CustomPackedBlock.NUM_SUBTYPES; i++) {
			RecipeManager.addPistonPackingRecipe(customPackedBlock,
												 i,
												 new ItemStack(CustomPackedBlock.equivItems[i], 9));
			RecipeManager.addShapelessRecipe(new ItemStack(CustomPackedBlock.equivItems[i], 9),
											 new ItemStack[]{new ItemStack(1699, 1, i)});
		}
	}
	
	private static @NotNull Block createCompressedBlock(EnumCompressedBlock enumB, int id) {
		Block block;
		if (Block.blocksList[enumB.equivID].isFallingBlock()) {
			block = new CompressedLooseBlock(id,
											 enumB.equivID,
											 enumB.equivMeta,
											 enumB.material,
											 enumB.hardness,
											 enumB.resistance,
											 enumB.shovelEff,
											 enumB.pickEff,
											 enumB.axeEff,
											 enumB.sound,
											 enumB.speed,
											 enumB.pistonShovelable,
											 enumB.breakSaw,
											 enumB.toolLevel);
		}
		else {
			block = new CompressedBlock(id,
										enumB.equivID,
										enumB.equivMeta,
										enumB.material,
										enumB.hardness,
										enumB.resistance,
										enumB.shovelEff,
										enumB.pickEff,
										enumB.axeEff,
										enumB.sound,
										enumB.speed,
										enumB.pistonShovelable,
										enumB.breakSaw,
										enumB.toolLevel);
		}
		
		return block;
	}
	
	private static void initBlockItemMeta(int blockID) {
		if (Item.itemsList[blockID] == null) {
			Item.itemsList[blockID] = new ItemBlockWithMetadata(blockID - 256);
		}
		else {
			throw new RuntimeException("Item ID " + blockID + " is already occupied by " + Item.itemsList[blockID]);
		}
	}
}

