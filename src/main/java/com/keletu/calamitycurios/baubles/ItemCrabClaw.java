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

public class ItemCrabClaw extends Item implements ICurioItem {

    public ItemCrabClaw(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        if (!(entity instanceof Player player)) {
            return false;
        }

        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(CCItems.CRAWCARAPACE.get()).isEmpty()).orElse(true);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        CurioAttributeModifiers.Builder modifiers = CurioAttributeModifiers.builder();

        modifiers.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.fromNamespaceAndPath(CalamityCurios.MODID, "attack_bonus"), CalamityCuriosConfig.CONFIG_CALAMITY.crabClawAttackBonus.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers.build();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext level, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.empty());

        tooltip.accept(Component.translatable("tooltip.calamitycurios.crabClaw1", Math.round(CalamityCuriosConfig.CONFIG_CALAMITY.crabClawAttackBonus.get() * 100)).withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.accept(Component.translatable("tooltip.calamitycurios.crabClaw2").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.accept(Component.translatable("tooltip.calamitycurios.crabClaw3").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
