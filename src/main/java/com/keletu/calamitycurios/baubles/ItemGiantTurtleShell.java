package com.keletu.calamitycurios.baubles;

import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.init.CCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Consumer;

public class ItemGiantTurtleShell extends ShieldItem implements ICurioItem {

    public ItemGiantTurtleShell(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        if (!(entity instanceof Player player)) {
            return false;
        }

        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(CCItems.giantTurtleShell.get()).isEmpty()).orElse(true);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.calamitycurios.giant_tortoise_shell");
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext level, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.empty());

        tooltip.accept(Component.translatable("tooltip.calamitycurios.giantTortoiseShell1", Math.round(CalamityCuriosConfig.CONFIG_CALAMITY.giantTurtleShellArmor.get())).withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.accept(Component.translatable("tooltip.calamitycurios.giantTortoiseShell2").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.accept(Component.translatable("tooltip.calamitycurios.giantTortoiseShell3", Math.round(CalamityCuriosConfig.CONFIG_CALAMITY.giantTurtleShellDashSlowness.get() * 100)).withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.accept(Component.translatable("tooltip.calamitycurios.giantTortoiseShell4", Math.round(CalamityCuriosConfig.CONFIG_CALAMITY.giantTurtleShellArmor.get() - CalamityCuriosConfig.CONFIG_CALAMITY.giantTurtleShellWeaknessArmor.get())).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
