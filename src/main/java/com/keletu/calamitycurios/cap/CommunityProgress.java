package com.keletu.calamitycurios.cap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class CommunityProgress {
    public static final MapCodec<CommunityProgress> CODEC = Codec.STRING.listOf().optionalFieldOf("DefeatedBosses", List.of()).xmap(CommunityProgress::load, progress -> List.copyOf(progress.defeatedBosses));

    private static CommunityProgress load(List<String> bosses) {
        CommunityProgress progress = new CommunityProgress();
        for (String boss : bosses) {
            Identifier id = Identifier.tryParse(boss);
            if (id != null) {
                progress.recordKill(id);
            }
        }
        return progress;
    }

    private final Set<String> defeatedBosses = new LinkedHashSet<>();
    private int clientBossCount;
    private int clientMaximum;
    private boolean synced;
    private int lastSentBossCount = -1;
    private int lastSentMaximum = -1;

    public boolean recordKill(Identifier entityId) {
        return defeatedBosses.add(entityId.toString());
    }

    public Set<String> defeatedBosses() {
        return Collections.unmodifiableSet(defeatedBosses);
    }

    public boolean needsSync(int bossCount, int maximum) {
        return bossCount != lastSentBossCount || maximum != lastSentMaximum;
    }

    public void markSent(int bossCount, int maximum) {
        lastSentBossCount = bossCount;
        lastSentMaximum = maximum;
    }

    public void receiveSync(int bossCount, int maximum) {
        clientBossCount = Math.max(0, bossCount);
        clientMaximum = Math.max(0, maximum);
        synced = true;
    }

    public boolean hasClientSummary() {
        return synced;
    }

    public int clientBossCount() {
        return clientBossCount;
    }

    public int clientMaximum() {
        return clientMaximum;
    }

}
