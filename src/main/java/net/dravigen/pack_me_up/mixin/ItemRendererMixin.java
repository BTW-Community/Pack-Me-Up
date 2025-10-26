package net.dravigen.pack_me_up.mixin;

import net.dravigen.pack_me_up.PackMeUpAddon;
import net.dravigen.pack_me_up.block.CompressedBlock;
import net.dravigen.pack_me_up.block.CompressedLooseBlock;
import net.minecraft.src.Block;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.RenderBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
	
	@Redirect(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderBlocks;renderBlockAsItem(Lnet/minecraft/src/Block;IF)V"))
	private void customRender(RenderBlocks instance, Block block, int iItemDamage, float fBrightness) {
		if (block instanceof CompressedBlock || block instanceof CompressedLooseBlock) {
			instance.renderBlockAsItem(block, iItemDamage, PackMeUpAddon.getColorMultiplier(iItemDamage));
		}
		else {
			instance.renderBlockAsItem(block, iItemDamage, fBrightness);
		}
	}
}
