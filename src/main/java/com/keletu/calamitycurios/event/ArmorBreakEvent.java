package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.cap.HurtCounter;
import com.keletu.calamitycurios.init.CCAttachments;
import com.keletu.calamitycurios.init.CCEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CalamityCurios.MODID)
public final class ArmorBreakEvent {
    private ArmorBreakEvent() {
    }

    @SubscribeEvent
    public static void onCrumblingRemoved(MobEffectEvent.Remove event) {
        if (!event.getEntity().level().isClientSide() && CalamityCuriosConfig.ENABLE_ARMOR_BREAK_TWEAKS.get()
                && event.getEffect().value() == CCEffects.CRUMBLING.get()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerHurt(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !CalamityCuriosConfig.ENABLE_ARMOR_BREAK_TWEAKS.get()) {
            return;
        }
        float amount = event.getAmount();
        if (!Float.isFinite(amount) || amount <= 0 || amount >= Float.MAX_VALUE
                || !(event.getSource().getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        HurtCounter counter = player.getData(CCAttachments.HURT_COUNTER);
        double contribution = attacker.getType().is(Tags.EntityTypes.BOSSES) ? amount : amount / 2.0;
        counter.setHurtCounter(counter.getHurtCounter() + contribution);
        counter.setHurtSinceLastDamage(CalamityCuriosConfig.CONFIG_CRUMBLING.fadeTimer.get());
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || !player.isAlive() || player.tickCount % 20 != 0
                || !CalamityCuriosConfig.ENABLE_ARMOR_BREAK_TWEAKS.get()) {
            return;
        }

        HurtCounter counter = player.getData(CCAttachments.HURT_COUNTER);
        if (counter.getHurtCounter() > 0) {
            if (counter.getHurtSinceLastDamage() == 0) {
                counter.setHurtCounter(counter.getHurtCounter() - 1.0);
            }
            if (counter.getHurtCounter() >= CalamityCuriosConfig.CONFIG_CRUMBLING.level1Damage.get()) {
                player.addEffect(new MobEffectInstance(CCEffects.CRUMBLING, 60, amplifier(counter.getHurtCounter()), false, false, true));
            }
        }
        if (counter.getHurtSinceLastDamage() > 0) {
            counter.setHurtSinceLastDamage(counter.getHurtSinceLastDamage() - 1);
        }
    }

    private static int amplifier(double damage) {
        var config = CalamityCuriosConfig.CONFIG_CRUMBLING;
        if (damage < config.level2Damage.get()) return 0;
        if (damage < config.level3Damage.get()) return 1;
        if (damage < config.level4Damage.get()) return 2;
        return damage < config.level5Damage.get() ? 3 : 4;
    }
}
