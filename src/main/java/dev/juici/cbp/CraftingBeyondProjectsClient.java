package dev.juici.cbp;

import dev.juici.cbp.entity.client.SkinwalkerRenderer;
import dev.juici.cbp.registry.CBPEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CraftingBeyondProjectsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(CBPEntities.SKINWALKER, SkinwalkerRenderer::new);
    }
}
