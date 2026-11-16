package com.keletu.calamitycurios.baubles;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.init.CCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Consumer;

public class ItemJelly extends Item implements ICurioItem {

    public ItemJelly(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        if (!(entity instanceof Player player)) {
            return false;
        }

        Item item = stack.getItem();

        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(item).isEmpty()).orElse(true);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        CurioAttributeModifiers.Builder modifiers = CurioAttributeModifiers.builder();

        if (stack.is(CCItems.greenJelly.get()) || stack.is(CCItems.grandGelatin.get())) {
            modifiers.addModifier(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.fromNamespaceAndPath(CalamityCurios.MODID, "movement_speed"), CalamityCuriosConfig.CONFIG_CALAMITY.greenJellySpeed.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        return modifiers.build();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext level, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.empty());

        if (stack.is(CCItems.blueJelly.get())) {
            tooltip.accept(Component.translatable("tooltip.calamitycurios.blueJelly1").withStyle(ChatFormatting.AQUA));

            tooltip.accept(Component.translatable("tooltip.calamitycurios.blueJelly2").withStyle(ChatFormatting.AQUA));
        }

        if (stack.is(CCItems.pinkJelly.get())) {
            tooltip.accept(Component.translatable("tooltip.calamitycurios.pinkJelly1").withStyle(ChatFormatting.LIGHT_PURPLE));

            tooltip.accept(Component.translatable("tooltip.calamitycurios.pinkJelly2").withStyle(ChatFormatting.LIGHT_PURPLE));
        }

        if (stack.is(CCItems.greenJelly.get())) {
            tooltip.accept(Component.translatable("tooltip.calamitycurios.greenJelly1", Math.round(CalamityCuriosConfig.CONFIG_CALAMITY.greenJellySpeed.get() * 100)).withStyle(ChatFormatting.GREEN));
        }

        if (stack.is(CCItems.grandGelatin.get())) {
            tooltip.accept(Component.translatable("tooltip.calamitycurios.grandGelatin1", Math.round(CalamityCuriosConfig.CONFIG_CALAMITY.greenJellySpeed.get() * 100)).withStyle(ChatFormatting.DARK_PURPLE));

            tooltip.accept(Component.translatable("tooltip.calamitycurios.grandGelatin2").withStyle(ChatFormatting.DARK_PURPLE));

            tooltip.accept(Component.translatable("tooltip.calamitycurios.grandGelatin3").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }
}
