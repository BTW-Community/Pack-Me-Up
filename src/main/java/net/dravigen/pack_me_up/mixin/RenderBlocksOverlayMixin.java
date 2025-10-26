package net.dravigen.pack_me_up.mixin;

import net.dravigen.pack_me_up.PackMeUpAddon;
import net.dravigen.pack_me_up.block.CompressedBlock;
import net.dravigen.pack_me_up.block.CompressedLooseBlock;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBlocks.class)
public abstract class RenderBlocksOverlayMixin {
	
	@Shadow public IBlockAccess blockAccess;
	
	@Shadow public abstract boolean hasOverrideBlockTexture();
	
	@Shadow private Icon overrideBlockTexture;
	@Shadow private boolean[] uvOverride;
	@Shadow private double renderMinX;
	@Shadow private double renderMaxX;
	@Shadow private double renderMinZ;
	@Shadow private double renderMaxZ;
	@Shadow private float[] uvOverrideTop;
	@Shadow private int uvRotateTop;
	@Shadow private double renderMaxY;
	@Shadow private boolean enableAO;
	@Shadow public float colorRedTopLeft;
	@Shadow public float colorGreenTopLeft;
	@Shadow public float colorBlueTopLeft;
	@Shadow private int brightnessTopLeft;
	@Shadow public float colorRedBottomLeft;
	@Shadow public float colorGreenBottomLeft;
	@Shadow public float colorBlueBottomLeft;
	@Shadow public float colorBlueBottomRight;
	@Shadow public float colorGreenBottomRight;
	@Shadow public float colorRedBottomRight;
	@Shadow private int brightnessBottomLeft;
	@Shadow private int brightnessBottomRight;
	@Shadow public float colorRedTopRight;
	@Shadow private int brightnessTopRight;
	@Shadow public float colorGreenTopRight;
	@Shadow public float colorBlueTopRight;
	@Shadow private boolean flipTexture;
	@Shadow private double renderMinY;
	@Shadow private float[] uvOverrideSouth;
	@Shadow private float[] uvOverrideNorth;
	@Shadow private int uvRotateNorth;
	@Shadow private float[] uvOverrideBottom;
	@Shadow private int uvRotateBottom;
	@Shadow private float[] uvOverrideWest;
	@Shadow private int uvRotateWest;
	@Shadow private float[] uvOverrideEast;
	@Shadow private int uvRotateEast;
	@Shadow private int uvRotateSouth;
	
	@Shadow public abstract void renderFaceYNeg(Block par1Block, double par2, double par4, double par6, Icon par8Icon);
	
	@Shadow public abstract void renderFaceZNeg(Block par1Block, double par2, double par4, double par6, Icon par8Icon);
	
	@Shadow public abstract void renderFaceXNeg(Block par1Block, double par2, double par4, double par6, Icon par8Icon);
	
	@Shadow public abstract void renderFaceYPos(Block par1Block, double par2, double par4, double par6, Icon par8Icon);
	
	@Shadow public abstract void renderFaceZPos(Block par1Block, double par2, double par4, double par6, Icon par8Icon);
	
	@Inject(method = "renderFaceXPos", at = @At("RETURN"))
	private void renderCompressionOverlay_XPos(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceXPosCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Unique
	public void renderFaceXPosCopy(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
		double var18;
		double var16;
		double var14;
		double var12;
		double var10;
		boolean override;
		Tessellator var9 = Tessellator.instance;
		if (this.hasOverrideBlockTexture()) {
			par8Icon = this.overrideBlockTexture;
		}
		if (!(override = this.uvOverride[2])) {
			var10 = par8Icon.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
			var12 = par8Icon.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
			var14 = par8Icon.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
			var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinY * 16.0);
		} else {
			var10 = par8Icon.getInterpolatedU((double)this.uvOverrideSouth[0] * 16.0);
			var12 = par8Icon.getInterpolatedU((double)this.uvOverrideSouth[2] * 16.0);
			var14 = par8Icon.getInterpolatedV((double)this.uvOverrideSouth[1] * 16.0);
			var16 = par8Icon.getInterpolatedV((double)this.uvOverrideSouth[3] * 16.0);
		}
		if (this.flipTexture) {
			var18 = var10;
			var10 = var12;
			var12 = var18;
		}
		if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if (this.uvRotateSouth == 2) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(this.renderMinY * 16.0);
				var12 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0);
				var14 = par8Icon.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
				var16 = par8Icon.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideSouth[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideSouth[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideSouth[1] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideSouth[3] * 16.0);
			}
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if (this.uvRotateSouth == 1) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMinY * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideSouth[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideSouth[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideSouth[1] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideSouth[3] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if (this.uvRotateSouth == 3) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMinY * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideSouth[2] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideSouth[0] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideSouth[1] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideSouth[3] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + this.renderMaxX;
		double var28 = par4 + this.renderMinY;
		double var30 = par4 + this.renderMaxY;
		double var32 = par6 + this.renderMinZ;
		double var34 = par6 + this.renderMaxZ;
		if (this.enableAO) {
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var26, var28, var34, var20, var24);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var26, var28, var32, var12, var16);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var26, var30, var32, var18, var22);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var26, var30, var34, var10, var14);
		} else {
			var9.addVertexWithUV(var26, var28, var34, var20, var24);
			var9.addVertexWithUV(var26, var28, var32, var12, var16);
			var9.addVertexWithUV(var26, var30, var32, var18, var22);
			var9.addVertexWithUV(var26, var30, var34, var10, var14);
		}
	}
	
	
	@Inject(method = "renderFaceXNeg", at = @At("RETURN"))
	private void renderCompressionOverlay_XNeg(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceXNegCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Unique
	public void renderFaceXNegCopy(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
		double var18;
		double var16;
		double var14;
		double var12;
		double var10;
		boolean override;
		Tessellator var9 = Tessellator.instance;
		if (this.hasOverrideBlockTexture()) {
			par8Icon = this.overrideBlockTexture;
		}
		if (!(override = this.uvOverride[3])) {
			var10 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0);
			var12 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0);
			var14 = par8Icon.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
			var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinY * 16.0);
		} else {
			var10 = par8Icon.getInterpolatedU((double)this.uvOverrideNorth[0] * 16.0);
			var12 = par8Icon.getInterpolatedU((double)this.uvOverrideNorth[2] * 16.0);
			var14 = par8Icon.getInterpolatedV((double)this.uvOverrideNorth[1] * 16.0);
			var16 = par8Icon.getInterpolatedV((double)this.uvOverrideNorth[3] * 16.0);
		}
		if (this.flipTexture) {
			var18 = var10;
			var10 = var12;
			var12 = var18;
		}
		if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if (this.uvRotateNorth == 1) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(this.renderMinY * 16.0);
				var12 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0);
				var14 = par8Icon.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
				var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideNorth[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideNorth[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideNorth[1] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideNorth[3] * 16.0);
			}
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if (this.uvRotateNorth == 2) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMinY * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideNorth[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideNorth[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideNorth[1] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideNorth[3] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if (this.uvRotateNorth == 3) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMinY * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideNorth[2] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideNorth[0] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideNorth[1] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideNorth[3] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + this.renderMinX;
		double var28 = par4 + this.renderMinY;
		double var30 = par4 + this.renderMaxY;
		double var32 = par6 + this.renderMinZ;
		double var34 = par6 + this.renderMaxZ;
		if (this.enableAO) {
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var26, var30, var34, var18, var22);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var26, var28, var32, var20, var24);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var26, var28, var34, var12, var16);
		} else {
			var9.addVertexWithUV(var26, var30, var34, var18, var22);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.addVertexWithUV(var26, var28, var32, var20, var24);
			var9.addVertexWithUV(var26, var28, var34, var12, var16);
		}
	}
	
	
	@Inject(method = "renderFaceYPos", at = @At("TAIL"))
	private void renderCompressionOverlay_YPos(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceYPosCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Unique
	public void renderFaceYPosCopy(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
		double var16;
		double var14;
		double var12;
		double var10;
		boolean override;
		Tessellator var9 = Tessellator.instance;
		if (this.hasOverrideBlockTexture()) {
			par8Icon = this.overrideBlockTexture;
		}
		if (!(override = this.uvOverride[4])) {
			var10 = par8Icon.getInterpolatedU(this.renderMinX * 16.0);
			var12 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0);
			var14 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0);
			var16 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0);
		} else {
			var10 = par8Icon.getInterpolatedU((double)this.uvOverrideTop[0] * 16.0);
			var12 = par8Icon.getInterpolatedU((double)this.uvOverrideTop[2] * 16.0);
			var14 = par8Icon.getInterpolatedV((double)this.uvOverrideTop[1] * 16.0);
			var16 = par8Icon.getInterpolatedV((double)this.uvOverrideTop[3] * 16.0);
		}
		if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		double var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if (this.uvRotateTop == 1) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0);
				var12 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0);
				var14 = par8Icon.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
				var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinX * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideTop[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideTop[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideTop[3] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideTop[1] * 16.0);
			}
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if (this.uvRotateTop == 2) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMinX * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideTop[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideTop[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideTop[3] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideTop[1] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if (this.uvRotateTop == 3) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMinX * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
				var14 = par8Icon.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
				var16 = par8Icon.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideTop[2] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideTop[0] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideTop[3] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideTop[1] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + this.renderMinX;
		double var28 = par2 + this.renderMaxX;
		double var30 = par4 + this.renderMaxY;
		double var32 = par6 + this.renderMinZ;
		double var34 = par6 + this.renderMaxZ;
		if (this.enableAO) {
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var28, var30, var32, var18, var22);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
		} else {
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
			var9.addVertexWithUV(var28, var30, var32, var18, var22);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
		}
	}
	
	
	@Inject(method = "renderFaceYNeg", at = @At("RETURN"))
	private void renderCompressionOverlay_YNeg(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceYNegCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Unique
	public void renderFaceYNegCopy(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
		double var16;
		double var14;
		double var12;
		double var10;
		boolean override;
		Tessellator var9 = Tessellator.instance;
		if (this.hasOverrideBlockTexture()) {
			par8Icon = this.overrideBlockTexture;
		}
		if (!(override = this.uvOverride[5])) {
			var10 = par8Icon.getInterpolatedU(this.renderMinX * 16.0);
			var12 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0);
			var14 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0);
			var16 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0);
		} else {
			var10 = par8Icon.getInterpolatedU((double)this.uvOverrideBottom[0] * 16.0);
			var12 = par8Icon.getInterpolatedU((double)this.uvOverrideBottom[2] * 16.0);
			var14 = par8Icon.getInterpolatedV((double)this.uvOverrideBottom[1] * 16.0);
			var16 = par8Icon.getInterpolatedV((double)this.uvOverrideBottom[3] * 16.0);
		}
		if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		double var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if (this.uvRotateBottom == 2) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0);
				var12 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0);
				var14 = par8Icon.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
				var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinX * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideBottom[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideBottom[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideBottom[3] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideBottom[1] * 16.0);
			}
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if (this.uvRotateBottom == 1) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMinX * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideBottom[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideBottom[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideBottom[3] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideBottom[1] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if (this.uvRotateBottom == 3) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMinX * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
				var14 = par8Icon.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
				var16 = par8Icon.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideBottom[2] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideBottom[0] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideBottom[3] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideBottom[1] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + this.renderMinX;
		double var28 = par2 + this.renderMaxX;
		double var30 = par4 + this.renderMinY;
		double var32 = par6 + this.renderMinZ;
		double var34 = par6 + this.renderMaxZ;
		if (this.enableAO) {
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var28, var30, var32, var18, var22);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
		} else {
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.addVertexWithUV(var28, var30, var32, var18, var22);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
		}
	}
	
	
	@Inject(method = "renderFaceZPos", at = @At("RETURN"))
	private void renderCompressionOverlay_ZPos(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceZPosCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Unique
	public void renderFaceZPosCopy(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
		double var18;
		double var16;
		double var14;
		double var12;
		double var10;
		boolean override;
		Tessellator var9 = Tessellator.instance;
		if (this.hasOverrideBlockTexture()) {
			par8Icon = this.overrideBlockTexture;
		}
		if (!(override = this.uvOverride[1])) {
			var10 = par8Icon.getInterpolatedU(this.renderMinX * 16.0);
			var12 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0);
			var14 = par8Icon.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
			var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinY * 16.0);
		} else {
			var10 = par8Icon.getInterpolatedU((double)this.uvOverrideWest[0] * 16.0);
			var12 = par8Icon.getInterpolatedU((double)this.uvOverrideWest[2] * 16.0);
			var14 = par8Icon.getInterpolatedV((double)this.uvOverrideWest[1] * 16.0);
			var16 = par8Icon.getInterpolatedV((double)this.uvOverrideWest[3] * 16.0);
		}
		if (this.flipTexture) {
			var18 = var10;
			var10 = var12;
			var12 = var18;
		}
		if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if (this.uvRotateWest == 1) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(this.renderMinY * 16.0);
				var12 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0);
				var14 = par8Icon.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
				var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinX * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideWest[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideWest[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideWest[1] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideWest[3] * 16.0);
			}
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if (this.uvRotateWest == 2) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMinY * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMinX * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideWest[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideWest[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideWest[1] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideWest[3] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if (this.uvRotateWest == 3) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMinX * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMinY * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideWest[2] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideWest[0] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideWest[1] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideWest[3] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + this.renderMinX;
		double var28 = par2 + this.renderMaxX;
		double var30 = par4 + this.renderMinY;
		double var32 = par4 + this.renderMaxY;
		double var34 = par6 + this.renderMaxZ;
		if (this.enableAO) {
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var26, var32, var34, var10, var14);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var28, var32, var34, var18, var22);
		} else {
			var9.addVertexWithUV(var26, var32, var34, var10, var14);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
			var9.addVertexWithUV(var28, var32, var34, var18, var22);
		}
	}
	
	
	@Inject(method = "renderFaceZNeg", at = @At("RETURN"))
	private void renderCompressionOverlay_ZNeg(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceZNegCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Unique
	public void renderFaceZNegCopy(Block par1Block, double par2, double par4, double par6, Icon par8Icon) {
		double var18;
		double var16;
		double var14;
		double var12;
		double var10;
		boolean override;
		Tessellator var9 = Tessellator.instance;
		if (this.hasOverrideBlockTexture()) {
			par8Icon = this.overrideBlockTexture;
		}
		if (!(override = this.uvOverride[0])) {
			var10 = par8Icon.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
			var12 = par8Icon.getInterpolatedU(16.0 - this.renderMinX * 16.0);
			var14 = par8Icon.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
			var16 = par8Icon.getInterpolatedV(16.0 - this.renderMinY * 16.0);
		} else {
			var10 = par8Icon.getInterpolatedU((double)this.uvOverrideEast[0] * 16.0);
			var12 = par8Icon.getInterpolatedU((double)this.uvOverrideEast[2] * 16.0);
			var14 = par8Icon.getInterpolatedV((double)this.uvOverrideEast[1] * 16.0);
			var16 = par8Icon.getInterpolatedV((double)this.uvOverrideEast[3] * 16.0);
		}
		if (this.flipTexture) {
			var18 = var10;
			var10 = var12;
			var12 = var18;
		}
		if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if (this.uvRotateEast == 2) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(this.renderMinY * 16.0);
				var12 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0);
				var14 = par8Icon.getInterpolatedV(16.0 - this.renderMinX * 16.0);
				var16 = par8Icon.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideEast[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideEast[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideEast[3] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideEast[1] * 16.0);
			}
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if (this.uvRotateEast == 1) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMinY * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMinX * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideEast[0] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideEast[2] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideEast[3] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideEast[1] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if (this.uvRotateEast == 3) {
			if (!override) {
				var10 = par8Icon.getInterpolatedU(16.0 - this.renderMinX * 16.0);
				var12 = par8Icon.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
				var14 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0);
				var16 = par8Icon.getInterpolatedV(this.renderMinY * 16.0);
			} else {
				var10 = par8Icon.getInterpolatedU((double)this.uvOverrideEast[2] * 16.0);
				var12 = par8Icon.getInterpolatedU((double)this.uvOverrideEast[0] * 16.0);
				var14 = par8Icon.getInterpolatedV((double)this.uvOverrideEast[3] * 16.0);
				var16 = par8Icon.getInterpolatedV((double)this.uvOverrideEast[1] * 16.0);
			}
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + this.renderMinX;
		double var28 = par2 + this.renderMaxX;
		double var30 = par4 + this.renderMinY;
		double var32 = par4 + this.renderMaxY;
		double var34 = par6 + this.renderMinZ;
		if (this.enableAO) {
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var26, var32, var34, var18, var22);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var28, var32, var34, var10, var14);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var28, var30, var34, var20, var24);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var26, var30, var34, var12, var16);
		} else {
			var9.addVertexWithUV(var26, var32, var34, var18, var22);
			var9.addVertexWithUV(var28, var32, var34, var10, var14);
			var9.addVertexWithUV(var28, var30, var34, var20, var24);
			var9.addVertexWithUV(var26, var30, var34, var12, var16);
		}
	}
	
	
	
	@Inject(method = "renderFullEastFace", at = @At("RETURN"), remap = false)
	private void renderCompressionOverlay_East(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceXPosCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Inject(method = "renderFullWestFace", at = @At("RETURN"), remap = false)
	private void renderCompressionOverlay_West(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceXNegCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Inject(method = "renderFullTopFace", at = @At("RETURN"), remap = false)
	private void renderCompressionOverlay_Top(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceYPosCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Inject(method = "renderFullBottomFace", at = @At("RETURN"), remap = false)
	private void renderCompressionOverlay_Bottom(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceYNegCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Inject(method = "renderFullSouthFace", at = @At("RETURN"), remap = false)
	private void renderCompressionOverlay_South(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceZPosCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	@Inject(method = "renderFullNorthFace", at = @At("RETURN"), remap = false)
	private void renderCompressionOverlay_North(Block block, double x, double y, double z, Icon icon,
			CallbackInfo ci) {
		if (isUnvalid(block)) {
			return;
		}
		
		Icon overlayIcon = getOverlayIcon(block, x, y, z);
		
		if (isIconUnvalid(icon, overlayIcon)) return;
		
		commonStart();
		
		renderFaceZNegCopy(block, x, y, z, overlayIcon);
		
		commonEnd();
	}
	
	@Unique
	private boolean isUnvalid(Block block) {
		return blockAccess == null || !(block instanceof CompressedBlock) && !(block instanceof CompressedLooseBlock);
	}
	
	@Unique
	private static boolean isIconUnvalid(Icon icon, Icon overlayIcon) {
		return icon == overlayIcon || icon == null || overlayIcon == null;
	}
	
	@Unique
	private Icon getOverlayIcon(Block block, double x, double y, double z) {
		int level = blockAccess.getBlockMetadata(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
		
		if (blockAccess.getBlockId(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z)) != block.blockID || level > CompressedBlock.compressionLvl.length) return null;
		
		return block instanceof CompressedBlock ? ((CompressedBlock) block).overlays[level] : block instanceof CompressedLooseBlock ? ((CompressedLooseBlock) block).overlays[level] : null;
	}
	
	@Unique
	private static void commonStart() {
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	@Unique
	private static void commonEnd() {
		GL11.glPopAttrib();
	}
	
	
	@Unique
	int meta = 0;
	@Inject(method = "renderStandardFallingBlock", at = @At("HEAD"))
	private void getMetaFalling(Block block, int i, int j, int k, int iMetadata, CallbackInfo ci) {
		meta = iMetadata;
	}
	
	@Redirect(method = "renderStandardFallingBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderBlocks;renderFaceYNeg(Lnet/minecraft/src/Block;DDDLnet/minecraft/src/Icon;)V"))
	private void fallingYN(RenderBlocks instance, Block block, double i, double j, double k, Icon icon) {
		if (block instanceof CompressedLooseBlock) setCustomColor(0.5f, 0.5f, 0.5f);
		this.renderFaceYNegCopy(block, i, j, k, icon);
	}
	@Redirect(method = "renderStandardFallingBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderBlocks;renderFaceYPos(Lnet/minecraft/src/Block;DDDLnet/minecraft/src/Icon;)V"))
	private void fallingYP(RenderBlocks instance, Block block, double i, double j, double k, Icon icon) {
		if (block instanceof CompressedLooseBlock) setCustomColor(1.0f, 1.0f, 1.0f);
		this.renderFaceYPosCopy(block, i, j, k, icon);
	}
	@Redirect(method = "renderStandardFallingBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderBlocks;renderFaceZNeg(Lnet/minecraft/src/Block;DDDLnet/minecraft/src/Icon;)V"))
	private void fallingZN(RenderBlocks instance, Block block, double i, double j, double k, Icon icon) {
		if (block instanceof CompressedLooseBlock) setCustomColor(0.8f, 0.8f, 0.8f);
		this.renderFaceZNeg(block, i, j, k, icon);
	}
	@Redirect(method = "renderStandardFallingBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderBlocks;renderFaceZPos(Lnet/minecraft/src/Block;DDDLnet/minecraft/src/Icon;)V"))
	private void fallingZP(RenderBlocks instance, Block block, double i, double j, double k, Icon icon) {
		if (block instanceof CompressedLooseBlock) setCustomColor(0.8f, 0.8f, 0.8f);
		this.renderFaceZPosCopy(block, i, j, k, icon);
	}
	@Redirect(method = "renderStandardFallingBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderBlocks;renderFaceXNeg(Lnet/minecraft/src/Block;DDDLnet/minecraft/src/Icon;)V"))
	private void fallingXN(RenderBlocks instance, Block block, double i, double j, double k, Icon icon) {
		if (block instanceof CompressedLooseBlock) setCustomColor(0.6f, 0.6f, 0.6f);
		this.renderFaceXNegCopy(block, i, j, k, icon);
	}
	@Redirect(method = "renderStandardFallingBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderBlocks;renderFaceXPos(Lnet/minecraft/src/Block;DDDLnet/minecraft/src/Icon;)V"))
	private void fallingXP(RenderBlocks instance, Block block, double i, double j, double k, Icon icon) {
		if (block instanceof CompressedLooseBlock) setCustomColor(0.6f, 0.6f, 0.6f);
		this.renderFaceXPosCopy(block, i, j, k, icon);
	}
	
	@Unique
	private void setCustomColor(float i, float j, float k) {
		Tessellator tess = Tessellator.instance;
		
		float shade = PackMeUpAddon.getColorMultiplier(meta);
		
		int baseColor = 0xFFFFFF;
		
		int r = (int) (((baseColor >> 16) & 0xFF) * shade);
		int g = (int) (((baseColor >> 8) & 0xFF) * shade);
		int b = (int) ((baseColor & 0xFF) * shade);
		
		int multiplier = (r << 16) | (g << 8) | b;
		
		float fRedMul = (float) (multiplier >> 16 & 0xFF) / 255.0f;
		float fGreenMul = (float) (multiplier >> 8 & 0xFF) / 255.0f;
		float fBlueMul = (float) (multiplier & 0xFF) / 255.0f;
		
		tess.setColorOpaque_F(fRedMul * i, fGreenMul * j, fBlueMul * k);
	}
}
