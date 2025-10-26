package net.dravigen.pack_me_up.mixin;

import net.dravigen.pack_me_up.PackMeUpAddon;
import net.dravigen.pack_me_up.block.CompressedBlock;
import net.dravigen.pack_me_up.block.CompressedLooseBlock;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderItem.class)
public class RenderItemMixin {
	
	@Redirect(method = "renderItemIntoGUI", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderBlocks;renderBlockAsItem(Lnet/minecraft/src/Block;IF)V"))
	private void renderInGUIItem(RenderBlocks instance, Block block, int iItemDamage, float fBrightness) {
		if (block instanceof CompressedBlock || block instanceof CompressedLooseBlock) {
			instance.renderBlockAsItem(block, iItemDamage, PackMeUpAddon.getColorMultiplier(iItemDamage));
		}
		else {
			instance.renderBlockAsItem(block, iItemDamage, fBrightness);
		}
	}
	
	@Redirect(method = "doRenderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderBlocks;renderBlockAsItem(Lnet/minecraft/src/Block;IF)V"))
	private void renderDroppedItem(RenderBlocks instance, Block block, int iItemDamage, float fBrightness) {
		if (block instanceof CompressedBlock || block instanceof CompressedLooseBlock) {
			instance.renderBlockAsItem(block, iItemDamage, PackMeUpAddon.getColorMultiplier(iItemDamage));
		}
		else {
			instance.renderBlockAsItem(block, iItemDamage, fBrightness);
		}
	}
}
