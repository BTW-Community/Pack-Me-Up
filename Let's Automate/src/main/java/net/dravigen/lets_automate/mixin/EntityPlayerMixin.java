package net.dravigen.lets_automate.mixin;

import btw.world.util.difficulty.Difficulty;
import net.dravigen.lets_automate.LetsAutomateAddon;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends Entity {
    public EntityPlayerMixin(World par1World) {
        super(par1World);
    }

    @Shadow public abstract ItemStack getCurrentArmor(int par1);
    @Shadow public int experienceTotal;
    @Shadow public abstract void addExperience(int par1);
    @Shadow public abstract void addExperienceLevel(int par1);
    @Shadow public float experience;
    @Shadow public int experienceLevel;
    @Shadow protected abstract void fall(float par1);


    @Shadow protected abstract void onBlockedDamage(DamageSource source, int iDamage);

    @Unique
    private boolean jumpPressed = false;
    @Unique
    private DamageSource damageSource = null;

    @Inject(method = "onLivingUpdate",at = @At("HEAD"))
    private void gravityStepLogic(CallbackInfo ci) {
        double x = this.posX;
        double y = this.boundingBox.minY;
        double z = this.posZ;
        if (this.getCurrentArmor(0) != null && this.getCurrentArmor(0).itemID == 1552) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            if (this.worldObj.isRemote&&gameSettings.keyBindJump.pressed){
                if (!jumpPressed) {
                    sendJumpKeyPacket(true);
                    jumpPressed = true;
                }
            }else {
                if (jumpPressed) {
                    sendJumpKeyPacket(false);
                    jumpPressed=false;
                }
            }
            this.stepHeight = 1;
            int id = this.worldObj.getBlockId(MathHelper.floor_double(x), MathHelper.floor_double(y - 0.95), MathHelper.floor_double(z));
            UUID username = ((EntityPlayer)(Object)this).getUniqueID();
            if (this.experienceTotal>0) {
                if (this.experienceTotal >= 5) {
                    if (this.fallDistance > 0 && id == 0 && (LetsAutomateAddon.playerJumpKeyStates.getOrDefault(username, false))) {
                        spawnAirStep(x, y, z);
                    } else if (this.isSneaking() && id == 0) {
                        spawnAirStep(x, y + 0.05, z);
                    }
                }
                if (id == 1553 && this.ticksExisted % 4 == 0) {
                    handleDecreaseExp(-1);
                }
            }
        }else this.stepHeight = 0.5f;
    }

    @Redirect(method = "damageEntity",at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;onBlockedDamage(Lnet/minecraft/src/DamageSource;I)V"))
    private void getDamageSource(EntityPlayer instance, DamageSource source, int iDamage){
        damageSource = source;
        this.onBlockedDamage(source, iDamage);
    }

    @Redirect(method = "damageEntity",at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"))
    private float xenalloyChestplateLogic(float damage, float b){
        if (this.getCurrentArmor(2) != null && this.getCurrentArmor(2).itemID == 1556 && damageSource!=null && damageSource instanceof EntityDamageSource) {
            float damageReduction = 0;
            int cost = 0;
            if (damage > 0) {
                for (int i = 0; i < damage; i++) {
                    if (this.experienceTotal > i * 50 + 1) {
                        cost -= 50;
                        damageReduction++;
                    } else break;
                }
            }
            if (cost<0){
                handleDecreaseExp(MathHelper.ceiling_float_int(cost));
            }
            float finalDamage = damage-damageReduction;
            if (finalDamage<1&&damage>1){
                finalDamage=1;
            }
            return Math.max(finalDamage,0);
        }else return Math.max(damage,0);
    }

    @Redirect(method = "fall",at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityLivingBase;fall(F)V"))
    private void xenalloyBootFallCostLogic(EntityLivingBase instance, float par1){
        if (this.getCurrentArmor(0) != null && this.getCurrentArmor(0).itemID == 1552) {
            int dist = MathHelper.ceiling_float_int(par1 - 3);
            int cost = 0;
            int decreasedFall = 0;
            if (dist > 0) {
                for (int i = 0; i < dist; i++) {
                    if (this.experienceTotal > i * 10 + 1) {
                        cost -= 10;
                        decreasedFall++;
                    } else break;
                }
            }
            if (cost < 0) {
                handleDecreaseExp(cost);
            }
            super.fall(par1 - decreasedFall);
        }else super.fall(par1);
    }

    @Unique
    private void spawnAirStep(double x, double y, double z) {
        this.worldObj.setBlock(MathHelper.floor_double(x), MathHelper.floor_double(y - 1), MathHelper.floor_double(z), 1553);
        this.worldObj.scheduleBlockUpdate(MathHelper.floor_double(x), MathHelper.floor_double(y - 1), MathHelper.floor_double(z),1553,10);
        handleDecreaseExp(-5);
    }



    @Unique
    private void handleDecreaseExp(int decreasedXp) {
        this.addExperience(decreasedXp);
        boolean var1 = true;
        while (var1) {
            if (this.experienceLevel>0) {
                int expLvl = this.experienceLevel - 1;
                int var2 = 17;
                for (int i = 0; i <= expLvl; i++) {
                    var2 += i >= 30 ? 62 + (i - 30) * 7 : (i >= 15 ? 17 + (i - 15) * 3 : 17);
                }
                if (this.experienceTotal < var2) {
                    this.addExperienceLevel(-1);
                    this.experience = (float) this.experienceTotal / var2;
                } else {
                    var1 = false;
                }
            }else var1 = false;
        }
    }

    @Unique
    private void sendJumpKeyPacket(boolean isPressed) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        if (this.worldObj.isRemote){
            LetsAutomateAddon.playerJumpKeyStates.put(this.getUniqueID(),isPressed);
        }
        try {
            dos.writeBoolean(isPressed);
            Packet250CustomPayload packet = new Packet250CustomPayload(LetsAutomateAddon.JUMP_KEY_CHANNEL, bos.toByteArray());
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
        } catch (IOException e) {
            System.err.println("Failed to send jump key packet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Redirect(method = "canPlayerEdit",at = @At(value = "INVOKE", target = "Lbtw/world/util/difficulty/Difficulty;allowsPlacingBlocksInAir()Z"), remap = false)
    private boolean xenalloyLeggingLogic(Difficulty instance){
        if (this.getCurrentArmor(1)!=null && this.getCurrentArmor(1).itemID==1554) {
            return true;
        }else return instance.allowsPlacingBlocksInAir();
    }

}
