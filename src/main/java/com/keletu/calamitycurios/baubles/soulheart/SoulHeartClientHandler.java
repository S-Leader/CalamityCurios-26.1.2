package com.keletu.calamitycurios.baubles.soulheart;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.init.CCAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class SoulHeartClientHandler {

    private static final Identifier HEARTS_RESOURCE = Identifier.fromNamespaceAndPath(CalamityCurios.MODID, "textures/misc/soul_hearts.png");

    private SoulHeartClientHandler() {
    }

    public static void renderHUD(GuiGraphicsExtractor guiGraphics, Player player, ItemStack stack) {
        var data = player.getData(CCAttachments.DATA_SHIELD).heartCount;
        int maxHearts = Math.min(data, 22);

        Minecraft mc = Minecraft.getInstance();

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int x = screenWidth / 2 - 91;
        int y = screenHeight - CalamityCuriosConfig.CONFIG_CALAMITY.bubbleHeartHeight.get();

        int it = 0;

        for (int i = 0; i < maxHearts; i++) {
            boolean half = i == maxHearts - 1 && maxHearts % 2 != 0;

            if (half || i % 2 == 0) {
                renderHeart(guiGraphics, x + it * 8, y, !half);
                it++;
            }
        }
    }

    private static void renderHeart(GuiGraphicsExtractor guiGraphics, int x, int y, boolean full) {
        if (full) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, HEARTS_RESOURCE, x, y, 0, 0, 9, 9, 16, 16);
        } else {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, HEARTS_RESOURCE, x, y, 9, 0, 7, 9, 16, 16);
        }
    }
}
