package com.keletu.calamitycurios;

import com.keletu.calamitycurios.event.ClientEvents;
import com.keletu.calamitycurios.init.*;
import com.keletu.calamitycurios.packet.ModNetwork;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;

@Mod(CalamityCurios.MODID)
public final class CalamityCurios {
    public static final String MODID = "calamitycurios";

    public CalamityCurios(IEventBus bus, ModContainer container) {
        CCItems.ITEMS.register(bus);
        CCEffects.EFFECTS.register(bus);
        CCLoots.LOOTS.register(bus);
        CCTabs.TABS.register(bus);
        CCAttachments.ATTACHMENT_TYPES.register(bus);
        CCDataComponents.COMPONENTS.register(bus);
        bus.addListener(ModNetwork::register);
        container.registerConfig(ModConfig.Type.COMMON, CalamityCuriosConfig.COMMON_SPEC);
        if (FMLLoader.getCurrent().getDist() == Dist.CLIENT) {
            bus.addListener(ClientEvents::registerKeys);
            bus.addListener(ClientEvents::registerLayers);
            bus.addListener(ClientEvents::attachLayers);
            bus.addListener(ClientEvents::registerRenderStateModifiers);
        }
    }

    public static Identifier prefix(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
