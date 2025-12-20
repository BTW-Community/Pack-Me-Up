package net.dravigen.lets_automate;

import btw.AddonHandler;
import btw.BTWAddon;
import net.dravigen.lets_automate.block.*;
import net.dravigen.lets_automate.item.XenalloyBoot;
import net.dravigen.lets_automate.item.XenalloyChestplate;
import net.dravigen.lets_automate.item.XenalloyLegging;
import net.minecraft.src.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LetsAutomateAddon extends BTWAddon {
    private static LetsAutomateAddon instance;
    public static Block beltBlock;
    public static Block testDirection;
    public static Item xenalloyBoot;
    public static Item xenalloyLegging;
    public static Block airStep;
    public static Block xenalloyPiston;
    public static Item xenalloyChestplate;
    public static Block selectionBlock;


    public static int letsAutomateID = 1550;

    public static final String JUMP_KEY_CHANNEL = "my_mod_jump_place";
    public static final Map<UUID, Boolean> playerJumpKeyStates = new HashMap<>();


    public LetsAutomateAddon() {
        super();
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");

        beltBlock = new BeltBlock(letsAutomateID).setCreativeTab(CreativeTabs.tabRedstone);
        initBlockItem(letsAutomateID);
        testDirection = new TestDirection(letsAutomateID+1).setCreativeTab(CreativeTabs.tabRedstone);
        initBlockItem(letsAutomateID+1);
        xenalloyBoot = new XenalloyBoot(letsAutomateID+2-256).setCreativeTab(CreativeTabs.tabCombat);
        airStep = new AirStep(letsAutomateID+3);
        initBlockItem(letsAutomateID+3);
        xenalloyLegging = new XenalloyLegging(letsAutomateID+4-256).setCreativeTab(CreativeTabs.tabCombat);
        xenalloyPiston = new XenalloyPiston(letsAutomateID+5).setCreativeTab(CreativeTabs.tabRedstone);
        initBlockItem(letsAutomateID+5);
        xenalloyChestplate = new XenalloyChestplate(letsAutomateID+6-256).setCreativeTab(CreativeTabs.tabCombat);
        selectionBlock = new SelectionBlock(letsAutomateID+7).setCreativeTab(CreativeTabs.tabTools);
        initBlockItem(letsAutomateID+7);
    }
    private static void initBlockItem(int blockID) {
        if (Item.itemsList[blockID] == null) {
            Item.itemsList[blockID] = new ItemBlock(blockID - 256);
        } else {
            throw new RuntimeException("Item ID " + blockID + " is already occupied by " + Item.itemsList[blockID]);
        }
    }


}