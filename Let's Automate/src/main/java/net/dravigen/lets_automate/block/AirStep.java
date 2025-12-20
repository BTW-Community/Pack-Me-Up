package net.dravigen.lets_automate.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class AirStep extends Block {
    public AirStep(int par1) {
        super(par1, Material.air);
        this.setUnlocalizedName("AirStep");
        this.setTextureName("lets_automate:air_step");
    }
    boolean entityWithGravityBoot = true;
    boolean entityWithEnoughXp = false;
    private Icon topIcon;


    @Override
    public void updateTick(World par1World, int x, int y, int z, Random par5Random) {
        AxisAlignedBB tempBox = AxisAlignedBB.getAABBPool().getAABB(x, y +1, z, x + 1, y +2 , z + 1);
        List<Entity> entityList = par1World.getEntitiesWithinAABBExcludingEntity((Entity) null, tempBox);
        if (!entityList.isEmpty()) {
            for (Entity entity : entityList) {
                if (entity instanceof EntityPlayer entityP){
                    entityWithGravityBoot = entityP.getCurrentArmor(0) != null && entityP.getCurrentArmor(0).itemID == 1552;
                    entityWithEnoughXp = entityP.experienceTotal >= 1;
                    if (entityWithEnoughXp&&entityWithGravityBoot) break;
                }
            }
        }else entityWithGravityBoot = false;
        if (!entityWithGravityBoot || !entityWithEnoughXp){
            par1World.setBlockToAir(x,y,z);
        }
        par1World.scheduleBlockUpdate(x,y,z,1553,20);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getAABBPool().getAABB(x,y+1, z, x + 1, y+0.99, z + 1);
    }

    @Override
    public boolean isAirBlock() {
        return true;
    }

    public int getRenderBlockPass() {
        return 1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldRenderNeighborFullFaceSide(IBlockAccess blockAccess, int i, int j, int k, int iNeighborSide) {
        return true;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public boolean canCollideCheck(int i, boolean flag) {
        return false;
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("lets_automate:air_step_base");
        this.topIcon = par1IconRegister.registerIcon("lets_automate:air_step");

    }


    public Icon getIcon(int par1, int par2) {
        if (par1 == 1) {
            return this.topIcon;
        }else return this.blockIcon;
    }
    @Override
    @Environment(value= EnvType.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        world.markBlockForRenderUpdate(i,j,k);
    }
}
