package com.keletu.calamitycurios.baubles.client;

import com.keletu.calamitycurios.CalamityCurios;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public final class ModelBubbleShield {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CalamityCurios.prefix("bubble_shield"), "main");

    private ModelBubbleShield() {
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        mesh.getRoot().addOrReplaceChild("bubble", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.5F, 6.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(15.0F)), PartPose.ZERO);
        return LayerDefinition.create(mesh, 12, 6);
    }
}
