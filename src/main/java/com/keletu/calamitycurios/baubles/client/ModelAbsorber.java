package com.keletu.calamitycurios.baubles.client;

import com.keletu.calamitycurios.CalamityCurios;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public final class ModelAbsorber {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CalamityCurios.prefix("absorber"), "main");

    private ModelAbsorber() {
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        mesh.getRoot().addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-5.0F, -1.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(-0.75F)), PartPose.ZERO);
        return LayerDefinition.create(mesh, 48, 32);
    }
}
