package net.dravigen.pack_me_up;

import btw.block.BTWBlocks;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import net.minecraft.src.*;

import java.util.ArrayList;

public class PMUBlocks {
    static ArrayList<String> compressedBlockList = new ArrayList<>();
    static ArrayList<Block> equivalentBlock = new ArrayList<>();
    static CustomPackedBlock customPackedBlock;

    public static void instantiateModBlocks() {
        customPackedBlock = new CustomPackedBlock(1699);
        initBlockItemMeta(1699);
        initLists();
        int id = 1700;
        for (int i = 0; i < compressedBlockList.size(); i++) {
            Block block = new CompressedBlock(id, equivalentBlock.get(i), compressedBlockList.get(i));
            for (int j = 0; j < 5; j++) {
                if (j < 4) {
                    RecipeManager.addShapelessRecipe(new ItemStack(id, 9, j), new ItemStack[]{new ItemStack(id, 1, j + 1)});
                }
                if (j > 0) {
                    RecipeManager.addPistonPackingRecipe(block, j, new ItemStack(id, 9, j - 1));
                }
                if (j == 0) {
                    int outputMeta;
                    outputMeta = i == 2 ? 14 : i == 3 ? 1 : i == 4 ? 7 : i == 5 ? 9 : i == 6 ? 10 : i == 8 ? 6 : i == 10 ? 15 : i == 17 ? 5 : i == 15 ? 4 : i == 16 ? 8 : i == 28 ? 2 : i == 29 ? 1 : i>=22&&i<=25 ? i-21 : i>=32&&i<=38 ? i-27 : i==12 ? 1 : i==13 ? 2 : 0;
                    Block var1 = equivalentBlock.get(i);
                    Block blockOutput = i >= 2 && i <= 6 || i == 10 || i == 17 ? BTWBlocks.aestheticOpaque : (i >= 21 && i <= 25) || (i>=32&&i<=38) ? PMUBlocks.customPackedBlock : var1;
                    RecipeManager.addShapelessRecipe(new ItemStack(blockOutput, 9, outputMeta), new ItemStack[]{new ItemStack(id, 1, 0)});
                    if (!compressedBlockList.get(i).equals("sand")) {
                        RecipeManager.addPistonPackingRecipe(block, new ItemStack(blockOutput, 9, outputMeta));
                    }else {
                        RecipeManager.addRecipe(new ItemStack(block),new Object[]{
                                "AAA",
                                "AAA",
                                "AAA",
                                'A',new ItemStack(Block.sand,1)
                        });
                    }
                }
            }
            initBlockItemMeta(id);
            id++;
        }
        customPackingRecipe();
        customShapelessRecipe();
    }

    private static void customPackingRecipe() {
        RecipeManager.addPistonPackingRecipe(customPackedBlock,0,new ItemStack(Item.appleRed,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,1,new ItemStack(BTWItems.carrot,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,2,new ItemStack(Item.potato,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,3,new ItemStack(BTWItems.brownMushroom,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,4,new ItemStack(BTWItems.redMushroom,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,5,new ItemStack(Item.fermentedSpiderEye,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,6,new ItemStack(BTWItems.batWing,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,7,new ItemStack(BTWItems.nitre,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,8,new ItemStack(BTWItems.witchWart,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,9,new ItemStack(BTWItems.brimstone,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,10,new ItemStack(BTWItems.soulFlux,9));
        RecipeManager.addPistonPackingRecipe(customPackedBlock,11,new ItemStack(Item.slimeBall,9));
    }

    private static void customShapelessRecipe(){
        RecipeManager.addShapelessRecipe(new ItemStack(Item.appleRed,9),new ItemStack[]{new ItemStack(1699,1,0)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.carrot,9),new ItemStack[]{new ItemStack(1699,1,1)});
        RecipeManager.addShapelessRecipe(new ItemStack(Item.potato,9),new ItemStack[]{new ItemStack(1699,1,2)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.brownMushroom,9),new ItemStack[]{new ItemStack(1699,1,3)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.redMushroom,9),new ItemStack[]{new ItemStack(1699,1,4)});
        RecipeManager.addShapelessRecipe(new ItemStack(Item.fermentedSpiderEye,9),new ItemStack[]{new ItemStack(1699,1,5)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.batWing,9),new ItemStack[]{new ItemStack(1699,1,6)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.nitre,9),new ItemStack[]{new ItemStack(1699,1,7)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.witchWart,9),new ItemStack[]{new ItemStack(1699,1,8)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.brimstone,9),new ItemStack[]{new ItemStack(1699,1,9)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.soulFlux,9),new ItemStack[]{new ItemStack(1699,1,10)});
        RecipeManager.addShapelessRecipe(new ItemStack(Item.slimeBall,9),new ItemStack[]{new ItemStack(1699,1,11)});
    }

    private static void initBlockItemMeta(int blockID) {
        if (Item.itemsList[blockID] == null) {
            Item.itemsList[blockID] = new ItemBlockWithMetadata(blockID - 256);
        } else {
            throw new RuntimeException("Item ID " + blockID + " is already occupied by " + Item.itemsList[blockID]);
        }
    }

    private static void initLists() {
        compressedBlockList.add("netherrack"); //0
        compressedBlockList.add("clay"); //1
        compressedBlockList.add("ender"); //2
        compressedBlockList.add("dung"); //3
        compressedBlockList.add("flint"); //4
        compressedBlockList.add("whitestone"); //5
        compressedBlockList.add("whitecobble"); //6
        compressedBlockList.add("dirt"); //7
        compressedBlockList.add("packed_dirt"); //8
        compressedBlockList.add("spider"); //9
        compressedBlockList.add("bone"); //10
        compressedBlockList.add("flesh"); //11
        compressedBlockList.add("stone2"); //12
        compressedBlockList.add("stone3"); //13
        compressedBlockList.add("loosecobble1"); //14
        compressedBlockList.add("loosecobble2"); //15
        compressedBlockList.add("loosecobble3"); //16
        compressedBlockList.add("soap"); //17
        compressedBlockList.add("sand"); //18
        compressedBlockList.add("oyster"); //19
        compressedBlockList.add("gravel"); //20
        compressedBlockList.add("apple"); //21
        compressedBlockList.add("carrot"); //22
        compressedBlockList.add("potato"); //23
        compressedBlockList.add("bshroom"); //24
        compressedBlockList.add("rshroom"); //25
        compressedBlockList.add("melon"); //26
        compressedBlockList.add("sandstone_normal"); //27
        compressedBlockList.add("sandstone_smooth"); //28
        compressedBlockList.add("sandstone_carved"); //29
        compressedBlockList.add("pumpkin"); //30
        compressedBlockList.add("hay"); //31
        compressedBlockList.add("venom"); //32
        compressedBlockList.add("bat"); //33
        compressedBlockList.add("nitre"); //34
        compressedBlockList.add("wart"); //35
        compressedBlockList.add("brimstone"); //36
        compressedBlockList.add("flux"); //37
        compressedBlockList.add("slime"); //38
        compressedBlockList.add("stone1"); //39


        equivalentBlock.add(Block.netherrack);  //0
        equivalentBlock.add(Block.blockClay);  //1
        equivalentBlock.add(BTWBlocks.aestheticOpaque);  //2
        equivalentBlock.add(BTWBlocks.aestheticEarth);  //3
        equivalentBlock.add(BTWBlocks.aestheticOpaque);  //4
        equivalentBlock.add(BTWBlocks.aestheticOpaque);  //5
        equivalentBlock.add(BTWBlocks.aestheticOpaque);  //6
        equivalentBlock.add(Block.dirt);  //7
        equivalentBlock.add(BTWBlocks.aestheticEarth);  //8
        equivalentBlock.add(BTWBlocks.spiderEyeBlock); //9
        equivalentBlock.add(BTWBlocks.aestheticOpaque); //10
        equivalentBlock.add(BTWBlocks.rottenFleshBlock); //11
        equivalentBlock.add(Block.stone); //12
        equivalentBlock.add(Block.stone); //13
        equivalentBlock.add(BTWBlocks.looseCobblestone); //14
        equivalentBlock.add(BTWBlocks.looseCobblestone); //15
        equivalentBlock.add(BTWBlocks.looseCobblestone); //16
        equivalentBlock.add(BTWBlocks.aestheticOpaque); //17
        equivalentBlock.add(Block.sand); //18
        equivalentBlock.add(BTWBlocks.creeperOysterBlock); //19
        equivalentBlock.add(Block.gravel); //20
        equivalentBlock.add(PMUBlocks.customPackedBlock); //21
        equivalentBlock.add(PMUBlocks.customPackedBlock); //22
        equivalentBlock.add(PMUBlocks.customPackedBlock); //23
        equivalentBlock.add(PMUBlocks.customPackedBlock); //24
        equivalentBlock.add(PMUBlocks.customPackedBlock); //25
        equivalentBlock.add(Block.melon); //26
        equivalentBlock.add(Block.sandStone); //27
        equivalentBlock.add(Block.sandStone); //28
        equivalentBlock.add(Block.sandStone); //29
        equivalentBlock.add(BTWBlocks.freshPumpkin); //30
        equivalentBlock.add(Block.hay); //31
        equivalentBlock.add(PMUBlocks.customPackedBlock); //32
        equivalentBlock.add(PMUBlocks.customPackedBlock); //33
        equivalentBlock.add(PMUBlocks.customPackedBlock); //34
        equivalentBlock.add(PMUBlocks.customPackedBlock); //35
        equivalentBlock.add(PMUBlocks.customPackedBlock); //36
        equivalentBlock.add(PMUBlocks.customPackedBlock); //37
        equivalentBlock.add(PMUBlocks.customPackedBlock); //38
        equivalentBlock.add(Block.stone); //39
    }
}

