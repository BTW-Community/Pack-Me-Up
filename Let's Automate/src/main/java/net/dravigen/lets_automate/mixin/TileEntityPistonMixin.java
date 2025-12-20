package net.dravigen.lets_automate.mixin;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.TileEntityPiston;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(TileEntityPiston.class)
public abstract class TileEntityPistonMixin {

    @Shadow
    public abstract void restoreStoredBlock();

    @Redirect(method = "placeBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/TileEntityPiston;restoreStoredBlock()V"))
    private void moveBlockIfBeltBelow(TileEntityPiston instance) {
        this.restoreStoredBlock();
        int x = instance.xCoord;
        int y = instance.yCoord;
        int z = instance.zCoord;
        int pistonShouldFace=-1;
        int xOff=0;
        int zOff=0;
        if (instance.getStoredBlockID() == 1550) {
            switch (instance.getBlockMetadata()) {
                case 0 -> { /// facing east
                pistonShouldFace = 5;
                xOff = -1;
                }
                case 1 -> { /// facing south
                pistonShouldFace = 3;
                zOff = -1;
                }
                case 2 -> { /// facing west
                    pistonShouldFace = 4;
                    xOff = 1;
                }
                case 3 -> { /// facing north
                    pistonShouldFace = 2;
                    zOff = 1;
                }
            }
            if (pistonShouldFace!=-1) {
                moveBlockEntityAboveBelt(instance, x, y, z, pistonShouldFace, xOff, zOff);
            }
        }
    }

    @Unique
    private static void moveBlockEntityAboveBelt(TileEntityPiston instance, int x, int y, int z, int pistonShouldFace, int xOff, int zOff) {
        if (instance.getPistonOrientation() == pistonShouldFace) {
            Block block = Block.blocksList[instance.worldObj.getBlockId(x +xOff, y +1, z+zOff)];
            if (instance.worldObj.isAirBlock(x, y + 1, z)) {
                if (block!=null && block.canBlockBePushedByPiston(instance.worldObj, x, y +1, z, instance.getPistonOrientation())) {
                    instance.worldObj.setBlockAndMetadataWithNotify(x, y + 1, z, instance.worldObj.getBlockId(x + xOff, y + 1, z + zOff), instance.worldObj.getBlockMetadata(x + xOff, y + 1, z + zOff));
                    instance.worldObj.setBlockToAir(x + xOff, y + 1, z + zOff);
                }
                AxisAlignedBB tempBox = AxisAlignedBB.getAABBPool().getAABB( x + xOff, y +1, z + zOff, x + xOff + 1, y +2 , z + zOff + 1);
                List<Entity> entityList = instance.worldObj.getEntitiesWithinAABBExcludingEntity((Entity) null, tempBox);
                if (!entityList.isEmpty()) {
                    for (Entity entity : entityList) {
                        entity.moveEntity(-xOff, 0, -zOff);
                    }
                }
            }
        }
    }
}