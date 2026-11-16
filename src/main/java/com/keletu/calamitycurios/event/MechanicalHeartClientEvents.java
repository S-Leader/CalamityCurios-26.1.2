package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.packet.MechanicalHeartActivatePayload;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@EventBusSubscriber(modid = CalamityCurios.MODID, value = Dist.CLIENT)
public final class MechanicalHeartClientEvents {
    private MechanicalHeartClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        while (ClientEvents.MECHANICAL_HEART_KEY.consumeClick()) {
            if (minecraft.screen == null && minecraft.player != null && minecraft.player.isAlive()
                    && !MechanicalHeartEvents.findEquipped(minecraft.player).isEmpty()) {
                ClientPacketDistributor.sendToServer(new MechanicalHeartActivatePayload());
            }
        }
    }
}
