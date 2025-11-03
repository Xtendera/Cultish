package dev.ashrayshah.client;

import dev.ashrayshah.client.entities.ProphetEntity;
import dev.ashrayshah.client.renderers.ProphetRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.entity.EntityRendererFactories;

public class cultishClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModEntities.register();
        FabricDefaultAttributeRegistry.register(ModEntities.PROPHET, ProphetEntity.createMobAttributes());
        EntityRendererFactories.register(ModEntities.PROPHET, ProphetRenderer::new);
        ProphetSummoner.register();
    }
}
