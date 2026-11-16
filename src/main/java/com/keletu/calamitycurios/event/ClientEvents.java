package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.baubles.client.ModelAbsorber;
import com.keletu.calamitycurios.baubles.client.ModelBubbleShield;
import com.keletu.calamitycurios.baubles.client.PlayerAccessoryLayer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.renderstate.AvatarRenderStateModifier;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import org.lwjgl.glfw.GLFW;

public final class ClientEvents {
    public static final KeyMapping MECHANICAL_HEART_KEY = new KeyMapping("key.calamitycurios.mechanical_heart", GLFW.GLFW_KEY_H,
            KeyMapping.Category.register(CalamityCurios.prefix("controls")));

    private ClientEvents() {
    }

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(MECHANICAL_HEART_KEY);
    }

    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelBubbleShield.LAYER_LOCATION, ModelBubbleShield::createBodyLayer);
        event.registerLayerDefinition(ModelAbsorber.LAYER_LOCATION, ModelAbsorber::createBodyLayer);
        event.registerLayerDefinition(PlayerAccessoryLayer.HEART_LAYER, PlayerAccessoryLayer::createHeartLayer);
    }

    public static void attachLayers(EntityRenderersEvent.AddLayers event) {
        for (var skin : event.getSkins()) {
            var renderer = event.getPlayerRenderer(skin);
            if (renderer != null) {
                renderer.addLayer(new PlayerAccessoryLayer(renderer, event.getEntityModels()));
            }
        }
    }

    public static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
        event.registerAvatarEntityModifier(new AvatarRenderStateModifier() {
            @Override
            public <T extends Avatar & ClientAvatarEntity> void accept(T avatar, AvatarRenderState state) {
                state.setRenderData(PlayerAccessoryLayer.ACCESSORIES, avatar instanceof Player player ? PlayerAccessoryLayer.extract(player) : null);
            }
        });
    }
}
