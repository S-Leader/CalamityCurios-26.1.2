package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.baubles.ItemMechanicalHeart;
import com.keletu.calamitycurios.init.CCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.CuriosApi;

public final class MechanicalHeartEvents {
    private MechanicalHeartEvents() {
    }

    public static ItemStack findEquipped(Player player) {
        return CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(CCItems.MECHANICAL_HEART.get())).map(result -> result.stack()).orElse(ItemStack.EMPTY);
    }

    public static void activate(ServerPlayer player) {
        if (!player.isAlive() || player.isSpectator() || findEquipped(player).isEmpty()) {
            return;
        }
        int cost = ItemMechanicalHeart.EXPERIENCE_LEVEL_COST;
        if (player.experienceLevel < cost) {
            player.sendOverlayMessage(Component.translatable("message.calamitycurios.mechanical_heart.insufficient", cost).withStyle(ChatFormatting.DARK_RED));
            return;
        }
        player.giveExperienceLevels(-cost);
        ItemStack apple = new ItemStack(Items.GOLDEN_APPLE);
        apple.get(DataComponents.CONSUMABLE).onConsume(player.level(), player, apple);
    }
}
