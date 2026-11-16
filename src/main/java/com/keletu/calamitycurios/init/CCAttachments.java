package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.cap.CommunityProgress;
import com.keletu.calamitycurios.cap.HurtCounter;
import com.keletu.calamitycurios.cap.ShieldData;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class CCAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CalamityCurios.MODID);
    public static final Supplier<AttachmentType<CommunityProgress>> COMMUNITY_PROGRESS = ATTACHMENT_TYPES.register("community_progress", () -> AttachmentType.builder(CommunityProgress::new).serialize(CommunityProgress.CODEC).copyOnDeath().build());
    public static final Supplier<AttachmentType<Boolean>> COMMUNITY_STARTER_CHECKED = ATTACHMENT_TYPES.register("community_starter_checked", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL.fieldOf("checked")).copyOnDeath().build());
    public static final Supplier<AttachmentType<HurtCounter>> HURT_COUNTER = ATTACHMENT_TYPES.register("hurt_counter", () -> AttachmentType.builder(HurtCounter::new).serialize(HurtCounter.CODEC).copyOnDeath().build());
    public static final Supplier<AttachmentType<ShieldData>> DATA_SHIELD = ATTACHMENT_TYPES.register("shield_counter", () -> AttachmentType.builder(ShieldData::new).build());

    private CCAttachments() {
    }
}
