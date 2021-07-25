package com.github.platymemo.alaskanativecraft.client.renderer.entity.feature;

import com.github.platymemo.alaskanativecraft.AlaskaNativeCraft;
import com.github.platymemo.alaskanativecraft.client.model.entity.AlaskaModels;
import com.github.platymemo.alaskanativecraft.client.model.entity.feature.SnowshoeModel;
import com.github.platymemo.alaskanativecraft.item.AlaskaItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class SnowshoeFeatureRenderer<E extends LivingEntity, M extends BipedEntityModel<E>> extends FeatureRenderer<E, M> {
    private final SnowshoeModel<E> model;
    private final Identifier TEXTURE = new Identifier(AlaskaNativeCraft.MOD_ID, "textures/entity/feature/snowshoe.png");

    public SnowshoeFeatureRenderer(FeatureRendererContext<E, M> context, EntityModelLoader loader) {
        super(context);
        this.model = new SnowshoeModel<>(loader.getModelPart(AlaskaModels.SNOWSHOES));
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light, E entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack armorItemStack = entity.getEquippedStack(EquipmentSlot.FEET);
        if (armorItemStack.isOf(AlaskaItems.SNOWSHOES)) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(TEXTURE), false, armorItemStack.hasGlint());
            matrices.push(); {
                this.translateToRightLeg(matrices, this.getContextModel(), entity);
                this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
                this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            } matrices.pop();
            matrices.push(); {
                this.translateToLeftLeg(matrices, this.getContextModel(), entity);
                this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
                this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            } matrices.pop();
        }
    }

    private void translateToRightLeg(MatrixStack matrices, M model, E entity) {
        if (entity.isInSneakingPose() && !model.riding && !entity.isSwimming()) {
            matrices.translate(0.0F, 0.0F, 0.25F);
        }
        matrices.translate(-0.1F, 0.75F, 0.0F);
        matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(model.rightLeg.roll));
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(model.rightLeg.yaw));
        matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(model.rightLeg.pitch));
        // This sets it just above the armor stand base.
        float bottomOfFoot = entity instanceof ArmorStandEntity ? 0.65F : 0.7F;
        matrices.translate(0.0F, bottomOfFoot, 0.0F);
        matrices.scale(2.0F, 2.0F, 2.0F);
    }

    private void translateToLeftLeg(MatrixStack matrices, M model, E entity) {
        if (entity.isInSneakingPose() && !model.riding && !entity.isSwimming()) {
            matrices.translate(0.0F, 0.0F, 0.25F);
        }
        matrices.translate(0.1F, 0.75F, 0.0F);
        matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(model.leftLeg.roll));
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(model.leftLeg.yaw));
        matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(model.leftLeg.pitch));
        // This sets it just above the armor stand base.
        float bottomOfFoot = entity instanceof ArmorStandEntity ? 0.65F : 0.7F;
        matrices.translate(0.0F, bottomOfFoot, 0.0F);
        matrices.scale(2.0F, 2.0F, 2.0F);
    }
}
