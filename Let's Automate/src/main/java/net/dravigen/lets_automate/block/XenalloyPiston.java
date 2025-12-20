package net.dravigen.lets_automate.block;

import net.minecraft.src.*;

public class XenalloyPiston extends BlockPistonBase implements XenalloyPistonI {
    public XenalloyPiston(int par1) {
        super(par1, false);
        this.setUnlocalizedName("XenalloyPiston");
    }

    @Override
    protected void updatePistonState(World world, int x, int y, int z) {
        super.updatePistonState(world, x, y, z);
    }
    @Override
    public boolean isIndirectlyPowered(World par1World, int par2, int par3, int par4, int par5) {
        return (par5 != 0 && par1World.getIndirectPowerOutput(par2, par3 - 1, par4, 0)) || (par5 != 1 && par1World.getIndirectPowerOutput(par2, par3 + 1, par4, 1)) || (par5 != 2 && par1World.getIndirectPowerOutput(par2, par3, par4 - 1, 2)) || (par5 != 3 && par1World.getIndirectPowerOutput(par2, par3, par4 + 1, 3)) || (par5 != 5 && par1World.getIndirectPowerOutput(par2 + 1, par3, par4, 5)) || (par5 != 4 && par1World.getIndirectPowerOutput(par2 - 1, par3, par4, 4)) || (par1World.getIndirectPowerOutput(par2, par3, par4, 0));
    }
    @Override
    protected boolean canExtend(World world, int i, int j, int k, int iToFacing) {
        int iOffsetI = i + Facing.offsetsXForSide[iToFacing];
        int iOffsetJ = j + Facing.offsetsYForSide[iToFacing];
        int iOffsetK = k + Facing.offsetsZForSide[iToFacing];
        int powerInput = world.getStrongestIndirectPower(i,j,k);
        System.out.println(powerInput);
        if (powerInput>0) {
            for (int iDist = 0; iDist < powerInput; ++iDist) {
                if (iOffsetJ <= 0 || iOffsetJ >= 255) {
                    return false;
                }
                Block tempBlock = blocksList[world.getBlockId(iOffsetI, iOffsetJ, iOffsetK)];
                if (tempBlock != null) {
                    if (!tempBlock.canBlockBePushedByPiston(world, iOffsetI, iOffsetJ, iOffsetK, iToFacing)) {
                        return false;
                    }
                    int iMobility = tempBlock.getMobilityFlag();
                    int iShovelEjectDirection = this.getPistonShovelEjectionDirection(world, iOffsetI, iOffsetJ, iOffsetK, iToFacing);
                    if (iMobility != 1 && iShovelEjectDirection < 0) {
                        if (iDist == powerInput - 1) {
                            return false;
                        }
                        iOffsetI += Facing.offsetsXForSide[iToFacing];
                        iOffsetJ += Facing.offsetsYForSide[iToFacing];
                        iOffsetK += Facing.offsetsZForSide[iToFacing];
                        continue;
                    }
                }
                return true;
            }
        }
        return true;
    }

    @Override
    protected boolean tryExtend(World world, int x, int y, int z, int facingTo) {
        int movingY;
        int movingX;
        int offsetX = x + Facing.offsetsXForSide[facingTo];
        int offsetY = y + Facing.offsetsYForSide[facingTo];
        int offsetZ = z + Facing.offsetsZForSide[facingTo];
        int powerInput = world.getStrongestIndirectPower(x, y, z);
        if (powerInput > 0) {
            for (int distance = 0; distance < powerInput; ++distance) {
                if (offsetY <= 0 || offsetY >= 255) {
                    return false;
                }
                int movingBlockID = world.getBlockId(offsetX, offsetY, offsetZ);
                Block movingBlock = blocksList[movingBlockID];
                if (movingBlock == null) break;
                if (!movingBlock.canBlockBePushedByPiston(world, offsetX, offsetY, offsetZ, facingTo)) {
                    return false;
                }
                int mobilityFlag = movingBlock.getMobilityFlag();
                int shovelEjectDirection = this.getPistonShovelEjectionDirection(world, offsetX, offsetY, offsetZ, facingTo);
                if (mobilityFlag != 1 && shovelEjectDirection < 0) {
                    if (distance == powerInput - 1) {
                        return false;
                    }
                    offsetX += Facing.offsetsXForSide[facingTo];
                    offsetY += Facing.offsetsYForSide[facingTo];
                    offsetZ += Facing.offsetsZForSide[facingTo];
                    continue;
                }
                int movingBlockMetadata = world.getBlockMetadata(offsetX, offsetY, offsetZ);
                if (shovelEjectDirection >= 0) {
                    movingBlockMetadata = movingBlock.adjustMetadataForPistonMove(movingBlockMetadata);
                    int ejectX = offsetX + Facing.offsetsXForSide[shovelEjectDirection];
                    int ejectY = offsetY + Facing.offsetsYForSide[shovelEjectDirection];
                    int ejectZ = offsetZ + Facing.offsetsZForSide[shovelEjectDirection];
                    this.onShovelEjectIntoBlock(world, ejectX, ejectY, ejectZ);
                    world.setBlock(ejectX, ejectY, ejectZ, Block.pistonMoving.blockID, movingBlockMetadata, 4);
                    world.setBlockTileEntity(ejectX, ejectY, ejectZ, BlockPistonMoving.getShoveledTileEntity(movingBlockID, movingBlockMetadata, shovelEjectDirection));
                } else {
                    movingBlock.onBrokenByPistonPush(world, offsetX, offsetY, offsetZ, movingBlockMetadata);
                }
                world.setBlockToAir(offsetX, offsetY, offsetZ);
                break;
            }
            int previousOffsetX = offsetX;
            int previousOffsetY = offsetY;
            int previousOffsetZ = offsetZ;
            int blockCounter = 0;
            int movingZ = 0;
            int[] blockIDList = new int[powerInput];
            while (offsetX != x || offsetY != y || offsetZ != z) {
                movingX = offsetX - Facing.offsetsXForSide[facingTo];
                movingY = offsetY - Facing.offsetsYForSide[facingTo];
                movingZ = offsetZ - Facing.offsetsZForSide[facingTo];
                int movingBlockID = world.getBlockId(movingX, movingY, movingZ);
                int movingBlockMetadata = world.getBlockMetadata(movingX, movingY, movingZ);
                NBTTagCompound tileEntityData = BlockPistonBase.getBlockTileEntityData(world, movingX, movingY, movingZ);
                world.removeBlockTileEntity(movingX, movingY, movingZ);
                if (movingBlockID == this.blockID && movingX == x && movingY == y && movingZ == z) {
                    world.setBlock(offsetX, offsetY, offsetZ, Block.pistonMoving.blockID, facingTo | (this.isSticky ? 8 : 0), 4);
                    world.setBlockTileEntity(offsetX, offsetY, offsetZ, BlockPistonMoving.getTileEntity(Block.pistonExtension.blockID, facingTo | (this.isSticky ? 8 : 0), facingTo, true, false));
                } else {
                    if (Block.blocksList[movingBlockID] != null) {
                        movingBlockMetadata = Block.blocksList[movingBlockID].adjustMetadataForPistonMove(movingBlockMetadata);
                    }
                    world.setBlock(offsetX, offsetY, offsetZ, Block.pistonMoving.blockID, movingBlockMetadata, 4);
                    world.setBlockTileEntity(offsetX, offsetY, offsetZ, BlockPistonMoving.getTileEntity(movingBlockID, movingBlockMetadata, facingTo, true, false));
                    if (tileEntityData != null) {
                        ((TileEntityPiston) world.getBlockTileEntity(offsetX, offsetY, offsetZ)).storeTileEntity(tileEntityData);
                    }
                }
                blockIDList[blockCounter++] = movingBlockID;
                offsetX = movingX;
                offsetY = movingY;
                offsetZ = movingZ;
            }
            offsetX = previousOffsetX;
            offsetY = previousOffsetY;
            offsetZ = previousOffsetZ;
            blockCounter = 0;
            while (offsetX != x || offsetY != y || offsetZ != z) {
                movingX = offsetX - Facing.offsetsXForSide[facingTo];
                movingY = offsetY - Facing.offsetsYForSide[facingTo];
                movingZ = offsetZ - Facing.offsetsZForSide[facingTo];
                world.notifyBlocksOfNeighborChange(movingX, movingY, movingZ, blockIDList[blockCounter++]);
                offsetX = movingX;
                offsetY = movingY;
                offsetZ = movingZ;
            }
        }
        return true;
    }
}
