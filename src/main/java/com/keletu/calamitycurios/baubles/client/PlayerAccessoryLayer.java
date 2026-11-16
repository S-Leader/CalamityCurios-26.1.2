package com.keletu.calamitycurios.baubles.client;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.init.CCAttachments;
import com.keletu.calamitycurios.init.CCItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public final class PlayerAccessoryLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    public static final ContextKey<Accessories> ACCESSORIES = new ContextKey<>(CalamityCurios.prefix("accessories"));
    public static final ModelLayerLocation HEART_LAYER = new ModelLayerLocation(CalamityCurios.prefix("mechanical_heart"), "main");
    private static final Identifier HEART_TEXTURE = CalamityCurios.prefix("textures/layer/mechanical_heart_outlayer.png");
    private static final Identifier ABSORBER_TEXTURE = CalamityCurios.prefix("textures/layer/the_absorber_model.png");
    private static final Identifier SHIELD_TEXTURE = CalamityCurios.prefix("textures/layer/shield.png");
    private final ModelPart backpack;
    private final ModelPart bubble;
    private final HumanoidModel<AvatarRenderState> heart;

    public PlayerAccessoryLayer(RenderLayerParent<AvatarRenderState, PlayerModel> parent, EntityModelSet models) {
        super(parent);
        backpack = models.bakeLayer(ModelAbsorber.LAYER_LOCATION).getChild("backpack");
        bubble = models.bakeLayer(ModelBubbleShield.LAYER_LOCATION).getChild("bubble");
        heart = new HumanoidModel<>(models.bakeLayer(HEART_LAYER));
        heart.hat.visible = false;
    }

    public static LayerDefinition createHeartLayer() {
        return LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.35F), 0.0F), 64, 64);
    }

    public static Accessories extract(Player player) {
        ItemStack crab = visibleStack(player, CCItems.CRAWCARAPACE.get());
        ItemStack baro = visibleStack(player, CCItems.BAROCLAW.get());
        ItemStack right = baro.isEmpty() ? crab : baro;
        ItemStack left = baro.isEmpty() ? ItemStack.EMPTY : crab;
        ItemStack back = firstVisible(player, CCItems.giantTurtleShell.get(), CCItems.GIANTSHELL.get());
        ItemStack head = firstVisible(player, CCItems.blueJelly.get(), CCItems.greenJelly.get(), CCItems.pinkJelly.get());
        boolean shield = player.getData(CCAttachments.DATA_SHIELD).heartCount > 0
                && !firstVisible(player, CCItems.roverDrive.get(), CCItems.theSponge.get()).isEmpty();
        return new Accessories(itemState(player, right, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND),
                itemState(player, left, ItemDisplayContext.THIRD_PERSON_LEFT_HAND),
                itemState(player, back, ItemDisplayContext.HEAD), itemState(player, head, ItemDisplayContext.HEAD),
                !visibleStack(player, CCItems.theAbsorber.get()).isEmpty(),
                !visibleStack(player, CCItems.MECHANICAL_HEART.get()).isEmpty(), shield,
                !player.getItemBySlot(EquipmentSlot.CHEST).isEmpty());
    }

    private static ItemStack visibleStack(Player player, Item item) {
        return CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findCurios(item).stream()
                .filter(result -> result.slotContext().visible()).findFirst()).map(SlotResult::stack).orElse(ItemStack.EMPTY);
    }

    private static ItemStack firstVisible(Player player, Item... items) {
        for (Item item : items) {
            ItemStack stack = visibleStack(player, item);
            if (!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStackRenderState itemState(Player player, ItemStack stack, ItemDisplayContext context) {
        ItemStackRenderState state = new ItemStackRenderState();
        if (!stack.isEmpty()) {
            Minecraft.getInstance().getItemModelResolver().updateForLiving(state, stack, context, player);
        }
        return state;
    }

    @Override
    public void submit(PoseStack pose, SubmitNodeCollector collector, int light, AvatarRenderState state, float yRot, float xRot) {
        Accessories accessories = state.getRenderData(ACCESSORIES);
        if (accessories == null || state.isInvisible || state.isSpectator) {
            return;
        }
        submitGlove(pose, collector, light, state, accessories.right(), HumanoidArm.RIGHT);
        submitGlove(pose, collector, light, state, accessories.left(), HumanoidArm.LEFT);
        if (!accessories.head().isEmpty()) {
            pose.pushPose();
            getParentModel().root().translateAndRotate(pose);
            getParentModel().head.translateAndRotate(pose);
            accessories.head().submit(pose, collector, light, OverlayTexture.NO_OVERLAY, state.outlineColor);
            pose.popPose();
        }
        if (!accessories.back().isEmpty()) {
            pose.pushPose();
            translateToBody(pose);
            pose.scale(0.4F, 0.4F, 0.4F);
            pose.mulPose(Axis.ZP.rotationDegrees(180.0F));
            pose.translate(0.0F, -0.8F, accessories.chestArmor() ? 0.03F : -0.02F);
            accessories.back().submit(pose, collector, light, OverlayTexture.NO_OVERLAY, state.outlineColor);
            pose.popPose();
        }
        if (accessories.absorber()) {
            pose.pushPose();
            translateToBody(pose);
            if (accessories.chestArmor()) {
                pose.translate(0.0F, 0.0F, 0.1F);
            }
            collector.submitModelPart(backpack, pose, RenderTypes.entityCutout(ABSORBER_TEXTURE), light,
                    OverlayTexture.NO_OVERLAY, null, false, false, -1, null, state.outlineColor);
            pose.popPose();
        }
        if (accessories.heart()) {
            collector.submitModel(heart, state, pose, RenderTypes.entityCutout(HEART_TEXTURE), light,
                    OverlayTexture.NO_OVERLAY, -1, null, state.outlineColor, null);
        }
        if (accessories.shield()) {
            pose.pushPose();
            getParentModel().root().translateAndRotate(pose);
            ModelPart body = getParentModel().body;
            pose.translate(body.x / 16.0F, body.y / 16.0F, body.z / 16.0F);
            collector.submitModelPart(bubble, pose, RenderTypes.entityTranslucentEmissive(SHIELD_TEXTURE), light,
                    OverlayTexture.NO_OVERLAY, null, 0x80FFFFFF, null);
            pose.popPose();
        }
    }

    private void translateToBody(PoseStack pose) {
        getParentModel().root().translateAndRotate(pose);
        getParentModel().body.translateAndRotate(pose);
    }

    private void submitGlove(PoseStack pose, SubmitNodeCollector collector, int light, AvatarRenderState state, ItemStackRenderState item, HumanoidArm arm) {
        if (item.isEmpty()) {
            return;
        }
        pose.pushPose();
        getParentModel().translateToHand(state, arm, pose);
        pose.mulPose(Axis.XP.rotationDegrees(-90.0F));
        pose.mulPose(Axis.YP.rotationDegrees(180.0F));
        pose.translate((arm == HumanoidArm.LEFT ? -1.0F : 1.0F) / 16.0F, 0.125F, -0.625F);
        item.submit(pose, collector, light, OverlayTexture.NO_OVERLAY, state.outlineColor);
        pose.popPose();
    }

    public record Accessories(ItemStackRenderState right, ItemStackRenderState left, ItemStackRenderState back,
                              ItemStackRenderState head, boolean absorber, boolean heart, boolean shield,
                              boolean chestArmor) {
    }
}
