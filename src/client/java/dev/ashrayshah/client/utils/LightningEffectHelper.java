package dev.ashrayshah.client.utils;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class LightningEffectHelper {
    public static void strikeLightningEffect(ServerWorld world, BlockPos pos) {
        strikeLightningEffect(world, pos, 1);
    }

    public static void strikeLightningEffect(ServerWorld world, BlockPos pos, int strikes) {
        for (int i = 0; i < strikes; i++) {
            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world, SpawnReason.EVENT);

            if (lightning != null) {
                lightning.refreshPositionAfterTeleport(
                        Vec3d.ofBottomCenter(pos)
                );

                lightning.setCosmetic(true);

                world.spawnEntity(lightning);
            }
        }
    }

    public static void strikeLightningEffectWithOffset(ServerWorld world, BlockPos pos, double offsetY) {
        strikeLightningEffectWithOffset(world, pos, offsetY, 1);
    }

    public static void strikeLightningEffectWithOffset(ServerWorld world, BlockPos pos, double offsetY, int strikes) {
        for (int i = 0; i < strikes; i++) {
            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world, SpawnReason.EVENT);

            if (lightning != null) {
                Vec3d position = Vec3d.ofBottomCenter(pos).add(0, offsetY, 0);
                lightning.refreshPositionAfterTeleport(position);
                lightning.setCosmetic(true);
                world.spawnEntity(lightning);
            }
        }
    }
}