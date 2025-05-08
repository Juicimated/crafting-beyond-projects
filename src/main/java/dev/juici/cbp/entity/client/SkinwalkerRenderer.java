package dev.juici.cbp.entity.client;

import dev.juici.cbp.entity.SkinwalkerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class SkinwalkerRenderer extends MobEntityRenderer<SkinwalkerEntity, BipedEntityModel<SkinwalkerEntity>> {
    private static final Identifier DEFAULT = Identifier.of("minecraft", "textures/entity/player/wide/steve.png");

    public SkinwalkerRenderer(EntityRendererFactory.Context context) {
        super(context, new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(SkinwalkerEntity entity) {
        UUID copiedUuid = entity.getCopiedPlayer();
        MinecraftClient client = MinecraftClient.getInstance();
        if (copiedUuid != null && client != null && client.world != null) {
            for (AbstractClientPlayerEntity player : client.world.getPlayers()) {
                if (player.getUuid().equals(copiedUuid)) {
                    Identifier playerSkin = player.getSkinTextures().texture();
                    entity.setCopiedSkin(playerSkin);
                    return playerSkin;
                }
            }
        }

        Identifier cachedSkin = entity.getCopiedSkin();
        return cachedSkin != null ? cachedSkin : DEFAULT;
    }
}
