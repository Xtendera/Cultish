package dev.ashrayshah.client;

import dev.ashrayshah.client.entities.ProphetEntity;
import dev.ashrayshah.client.utils.LightningEffectHelper;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.SpawnReason;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProphetSummoner {
    private static BlockPattern prophetPattern;

    private static boolean shouldCheckPattern = false;
    private static BlockPos pendingSkullPos = null;
    private static ServerWorld pendingWorld = null;

    private static BlockPattern getProphetPattern() {
        if (prophetPattern == null) {
            prophetPattern = BlockPatternBuilder.start()
                    .aisle("NNN", "NPN", "NNN")
                    .aisle("   ", " S ", "   ")
                    .where('N', CachedBlockPosition.matchesBlockState(
                            BlockStatePredicate.forBlock(Blocks.NETHERRACK)))
                    .where('P', CachedBlockPosition.matchesBlockState(
                            BlockStatePredicate.forBlock(Blocks.PUMPKIN)))
                    .where('S', CachedBlockPosition.matchesBlockState(
                            BlockStatePredicate.forBlock(Blocks.WITHER_SKELETON_SKULL)))
                    .where(' ', CachedBlockPosition.matchesBlockState(
                            BlockStatePredicate.ANY))
                    .build();
        }
        return prophetPattern;
    }

    public static void register() {
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (shouldCheckPattern && pendingSkullPos != null && pendingWorld != null) {
                checkForPattern(pendingWorld, pendingSkullPos);
                shouldCheckPattern = false;
                pendingSkullPos = null;
                pendingWorld = null;
            }
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient()) return ActionResult.PASS;

            BlockPos clickedPos = hitResult.getBlockPos();

            if (!player.getStackInHand(hand).isOf(net.minecraft.item.Items.WITHER_SKELETON_SKULL)) {
                return ActionResult.PASS;
            }

            if (world.getBlockState(clickedPos).getBlock() != Blocks.PUMPKIN) {
                return ActionResult.PASS;
            }

            pendingSkullPos = clickedPos.up();
            pendingWorld = (ServerWorld) world;
            shouldCheckPattern = true;


            return ActionResult.PASS;
        });
    }

    private static void checkForPattern(World world, BlockPos skullPos) {
        if (world.isClient()) return;

        if (world.getBlockState(skullPos).getBlock() != Blocks.WITHER_SKELETON_SKULL) {
            return;
        }

        BlockPattern.Result result = findPattern(world, skullPos);

        if (result == null) {
            BlockPos pumpkinPos = skullPos.down();
            result = findPattern(world, pumpkinPos);
        }

        if (result != null) {
            summonProphet((ServerWorld) world, result);
        }
    }

    private static BlockPattern.Result findPattern(World world, BlockPos pos) {
        BlockPattern pattern = getProphetPattern();

        return pattern.searchAround(world, pos);
    }

    private static void summonProphet(ServerWorld world, BlockPattern.Result patternResult) {
        BlockPos centerPos = patternResult.translate(2, 2, 0).getBlockPos();
//        BlockPos pos = centerPos;
//
//        Logger LOGGER = LoggerFactory.getLogger("cultish");
//
//        LOGGER.info("{}", pos);
//
//        int rawId = net.minecraft.block.Block.getRawIdFromState(world.getBlockState(pos));
//        world.removeBlock(pos, false);
//        world.syncWorldEvent(2001, pos, rawId);

        for (int y = 0; y < 2; y++) {
            for (int z = 0; z < 3; z++) {
                for (int x = 0; x < 3; x++) {
                    BlockPos pos = patternResult.translate(x, z, y).getBlockPos();
                    int rawId = net.minecraft.block.Block.getRawIdFromState(world.getBlockState(pos));
                    world.removeBlock(pos, false);
                    world.syncWorldEvent(2001, pos, rawId);
                }
            }
        }

        ProphetEntity prophet = ModEntities.PROPHET.create(world, SpawnReason.EVENT);
        if (prophet != null) {
            prophet.refreshPositionAndAngles(
                    centerPos.getX() + 0.5,
                    centerPos.getY(),
                    centerPos.getZ() + 0.5,
                    0.0F, 0.0F
            );
            prophet.initialize(world, world.getLocalDifficulty(centerPos),
                    SpawnReason.EVENT, null);
            world.spawnEntity(prophet);
            LightningEffectHelper.strikeLightningEffect(world, centerPos, 5);
            world.playSound(null, centerPos,
                    net.minecraft.sound.SoundEvents.ENTITY_WITHER_SPAWN,
                    net.minecraft.sound.SoundCategory.HOSTILE,
                    1.0F, 1.0F);
        }
    }
}
