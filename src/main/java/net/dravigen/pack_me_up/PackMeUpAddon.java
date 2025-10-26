package net.dravigen.pack_me_up;

import btw.AddonHandler;
import btw.BTWAddon;

public class PackMeUpAddon extends BTWAddon {
	public PackMeUpAddon() {
		super();
	}
	
	@Override
	public void initialize() {
		AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
		PMURegistry.instantiateModBlocks();
	}
	
	public static float getColorMultiplier(int compressionLevel) {
		final int MAX_SHADE_LEVEL = 5;
		final float MIN_MULTIPLIER = 0.3f;
		
		float reductionFactor = (float)(1 + compressionLevel) / (MAX_SHADE_LEVEL);
		
		float multiplier = 0.8f - (reductionFactor * (0.8f - MIN_MULTIPLIER));
		
		return Math.max(MIN_MULTIPLIER, multiplier);
	}
}