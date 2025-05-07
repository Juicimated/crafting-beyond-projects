package dev.juici.cbp.entity.client;

import dev.juici.cbp.entity.SkinwalkerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class SkinwalkerRenderer extends MobEntityRenderer<SkinwalkerEntity, BipedEntityModel<SkinwalkerEntity>> {
    private static final Identifier TEXTURE = Identifier.of("minecraft", "textures/entity/player/wide/steve.png");

    public SkinwalkerRenderer(EntityRendererFactory.Context context) {
        super(context, new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(SkinwalkerEntity entity) {
        return TEXTURE;
    }
}
