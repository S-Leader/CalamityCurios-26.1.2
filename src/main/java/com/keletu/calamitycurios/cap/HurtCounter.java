package com.keletu.calamitycurios.cap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public final class HurtCounter {
    public static final MapCodec<HurtCounter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.DOUBLE.optionalFieldOf("hurtCounter", 0.0).forGetter(HurtCounter::getHurtCounter),
            Codec.INT.optionalFieldOf("damageCounter", 0).forGetter(HurtCounter::getHurtSinceLastDamage)
    ).apply(instance, HurtCounter::new));

    private double hurtCounter;
    private int hurtSinceLastDamage;

    public HurtCounter() {
    }

    private HurtCounter(double hurtCounter, int hurtSinceLastDamage) {
        setHurtCounter(hurtCounter);
        setHurtSinceLastDamage(hurtSinceLastDamage);
    }

    public double getHurtCounter() {
        return hurtCounter;
    }

    public void setHurtCounter(double value) {
        hurtCounter = Double.isFinite(value) ? Math.clamp(value, 0.0, 1000.0) : 0.0;
    }

    public int getHurtSinceLastDamage() {
        return hurtSinceLastDamage;
    }

    public void setHurtSinceLastDamage(int value) {
        hurtSinceLastDamage = Math.max(0, value);
    }
}
