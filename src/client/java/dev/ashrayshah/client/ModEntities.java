package dev.ashrayshah.client;

import dev.ashrayshah.client.entities.ProphetEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final String MODID = "cultish";

    public static final RegistryKey<EntityType<?>> PROPHET_KEY = RegistryKey.of(
            RegistryKeys.ENTITY_TYPE,
            Identifier.of(MODID, "prophet")
    );

    public static final EntityType<ProphetEntity> PROPHET = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MODID, "prophet"),
            EntityType.Builder.create(ProphetEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.8f) // width, height
                    .build(PROPHET_KEY)
    );

    public static void register() {
        // Registration happens via static initialization (aka just keep this empty)
    }
}