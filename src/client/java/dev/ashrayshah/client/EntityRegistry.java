package dev.ashrayshah.client;

import dev.ashrayshah.client.entities.ProphetEntity;
import dev.ashrayshah.client.models.ProphetModel;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;

public final class EntityRegistry implements ModInitializer {
    public static final String MODID = "cultish";

    public static EntityModelLayer PROPHET;

    public static void init() {
        PROPHET = new EntityModelLayer(Identifier.of(MODID, "prophet"), "main");
    }


    @Override
    public void onInitialize() {
//        FabricDefaultAttributeRegistry.register(PROPHET, ProphetEntity.createMobAttributes());
        EntityModelLayerRegistry.registerModelLayer(PROPHET, ProphetModel::getTextureResource);
    }
}
