package com.keletu.calamitycurios.packet;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.init.CCAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketBubbleShield(int entityId, int hearts) implements CustomPacketPayload {

    public static final Type<PacketBubbleShield> TYPE = new Type<>(Identifier.fromNamespaceAndPath(CalamityCurios.MODID, "bubble_shield"));

    public static final StreamCodec<ByteBuf, PacketBubbleShield> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, PacketBubbleShield::entityId,
            ByteBufCodecs.VAR_INT, PacketBubbleShield::hearts,
            PacketBubbleShield::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final PacketBubbleShield packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().level().getEntity(packet.entityId()) instanceof Player player) {
                player.getData(CCAttachments.DATA_SHIELD).heartCount = Math.max(0, packet.hearts());
            }
        });
    }
}
