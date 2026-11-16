package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.baubles.soulheart.SoulHeartClientHandler;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = CalamityCurios.MODID, value = Dist.CLIENT)
public final class AccessoryHudEvents {
    private AccessoryHudEvents() {
    }

    @SubscribeEvent
    public static void onGuiLayer(RenderGuiLayerEvent.Post event) {
        var minecraft = Minecraft.getInstance();
        if (event.getName().equals(VanillaGuiLayers.PLAYER_HEALTH) && minecraft.player != null && !minecraft.options.hideGui) {
            SoulHeartClientHandler.renderHUD(event.getGuiGraphics(), minecraft.player, null);
        }
    }
}
