package com.keletu.calamitycurios.packet;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetwork {

    private static final String PROTOCOL_VERSION = "1";

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

        registrar.playToClient(PacketBubbleShield.TYPE, PacketBubbleShield.CODEC, PacketBubbleShield::handle);
        registrar.playToClient(CommunityProgressSyncPayload.TYPE, CommunityProgressSyncPayload.CODEC, CommunityProgressSyncPayload::handle);
        registrar.playToServer(MechanicalHeartActivatePayload.TYPE, MechanicalHeartActivatePayload.CODEC, MechanicalHeartActivatePayload::handle);
    }
}
