package net.dravigen.lets_automate.mixin;

import net.dravigen.lets_automate.LetsAutomateAddon;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@Mixin(NetServerHandler.class)
public abstract class NetServerHandlerMixin {

    @Shadow
    public EntityPlayerMP playerEntity;

    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true) // Adjust method and At
    public void onHandleCustomPayload(Packet250CustomPayload par1Packet250CustomPayload, CallbackInfo ci) {
        if (par1Packet250CustomPayload.channel.equals(LetsAutomateAddon.JUMP_KEY_CHANNEL)) {
            ByteArrayInputStream bis = new ByteArrayInputStream(par1Packet250CustomPayload.data);
            DataInputStream dis = new DataInputStream(bis);

            try {
                boolean isJumpKeyPressed = dis.readBoolean();
                LetsAutomateAddon.playerJumpKeyStates.put(playerEntity.getUniqueID(), isJumpKeyPressed);
                ci.cancel();
            } catch (IOException e) {
                System.err.println("[SERVER] Error reading jump key packet from " + playerEntity.getUniqueID() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
