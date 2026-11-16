package com.keletu.calamitycurios.baubles;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Consumer;

public final class ItemMechanicalHeart extends Item implements ICurioItem {
    public static final int EXPERIENCE_LEVEL_COST = 5;

    public ItemMechanicalHeart(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.empty());
        tooltip.accept(Component.translatable("tooltip.calamitycurios.mechanical_heart1").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.accept(Component.translatable("tooltip.calamitycurios.mechanical_heart.activate", Component.keybind("key.calamitycurios.mechanical_heart"), EXPERIENCE_LEVEL_COST).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
