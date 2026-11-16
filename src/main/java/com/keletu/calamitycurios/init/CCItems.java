package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.baubles.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.BlocksAttacks;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;

public class CCItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CalamityCurios.MODID);
    public static final DeferredItem<Item> BAROCLAW = ITEMS.registerItem("baroclaw", properties -> new ItemBaroClaw(properties.stacksTo(1).rarity(Rarity.RARE)));

    public static final DeferredItem<Item> CRAWCARAPACE = ITEMS.registerItem("craw_carapace", properties -> new ItemCrabClaw(properties.stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> GIANTSHELL = ITEMS.registerItem("giant_shell", properties -> new ItemGiantShell(shieldProperties(properties).stacksTo(1).durability(999).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> giantTurtleShell = ITEMS.registerItem("giant_tortoise_shell", properties -> new ItemGiantTurtleShell(shieldProperties(properties).stacksTo(1).durability(1500).rarity(Rarity.RARE)));

    public static final DeferredItem<Item> blueJelly = ITEMS.registerItem("cleansing_jelly", properties -> new ItemJelly(properties.stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> pinkJelly = ITEMS.registerItem("life_jelly", properties -> new ItemJelly(properties.stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> greenJelly = ITEMS.registerItem("vital_jelly", properties -> new ItemJelly(properties.stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> grandGelatin = ITEMS.registerItem("grand_gelatin", properties -> new ItemJelly(properties.stacksTo(1).rarity(Rarity.RARE)));
    public static final DeferredItem<Item> theAbsorber = ITEMS.registerItem("the_absorber", properties -> new ItemTheAbsorber(properties.stacksTo(1).rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> roverDrive = ITEMS.registerItem("rover_drive", properties -> new ItemRoverDrive(properties));

    public static final DeferredItem<Item> theSponge = ITEMS.registerItem("the_sponge", properties -> new ItemTheSponge(properties));

    public static final DeferredItem<Item> theCommunity = ITEMS.registerItem("the_community", properties -> new ItemTheCommunity(properties));

    public static final DeferredItem<Item> MECHANICAL_HEART = ITEMS.registerItem("mechanical_heart", properties -> new ItemMechanicalHeart(properties.stacksTo(1).rarity(Rarity.RARE)));

    private static Item.Properties shieldProperties(Item.Properties properties) {
        return properties.repairable(Items.OBSIDIAN)
                .delayedComponent(DataComponents.BLOCKS_ATTACKS, context -> new BlocksAttacks(
                        0.25F, 1.0F,
                        List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                        new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                        Optional.of(context.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
                        Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK)))
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK);
    }
}
