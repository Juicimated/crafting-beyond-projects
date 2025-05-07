package dev.juici.cbp.item.staff;

import dev.juici.cbp.item.StaffItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class KindnessStaff extends StaffItem {
    private static final double RADIUS = 16.0;
    private static final int DURATION = 30 * 20;
    private static final int COOLDOWN = 3 * 60 * 20;
    private boolean isActive = false;
    private int activeTicks = 0;
    private BlockPos center = null;
    private final Set<LivingEntity> invEntities = new HashSet<>();

    public KindnessStaff(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getStackInHand(hand);

        if (!world.isClient) {
            if (!isActive && !player.getItemCooldownManager().isCoolingDown(this)) {
                world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE,
                        SoundCategory.PLAYERS, 1.0f, 1.0f);
                activateKindness(world, player);
                player.getItemCooldownManager().set(this, COOLDOWN);
            }
        }

        return TypedActionResult.success(item);
    }

    private void activateKindness(World world, PlayerEntity player) {
        if (!(world instanceof ServerWorld)) return;

        this.isActive = true;
        this.activeTicks = 0;
        this.center = player.getBlockPos();
        this.invEntities.clear();
    }

    private void deactivateKindness() {
        for (LivingEntity entity : invEntities) {
            if (entity.isAlive()) {
                entity.setInvulnerable(false);
            }
        }
        invEntities.clear();
        center = null;
        this.activeTicks = 0;
        isActive = false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient || !(entity instanceof PlayerEntity player)) {
            return;
        }

        if (isActive && center != null && player.getWorld() instanceof ServerWorld serverWorld) {
            activeTicks++;

            for (int i = 0; i < 360; i += 10) {
                double angle = Math.toRadians(i);
                double x = center.getX() + 0.5 + Math.cos(angle) * RADIUS;
                double z = center.getZ() + 0.5 + Math.sin(angle) * RADIUS;
                serverWorld.spawnParticles(
                        ParticleTypes.END_ROD,
                        x, center.getY() + 1, z,
                        1, 0, 0, 0, 0.0
                );
            }

            Box kindBox = new Box(center).expand(RADIUS);

            Set<LivingEntity> currentEntities = new HashSet<>();
            for (Entity e : ((ServerWorld) player.getWorld()).getOtherEntities(null, kindBox)) {
                if (e instanceof LivingEntity livingEntity) {
                    currentEntities.add(livingEntity);
                    if (!invEntities.contains(livingEntity)) {
                        livingEntity.setInvulnerable(true);
                        invEntities.add(livingEntity);
                    }
                }
            }

            invEntities.removeIf(entityToCheck -> {
                if (!currentEntities.contains(entityToCheck)) {
                    entityToCheck.setInvulnerable(false);
                    return true;
                }
                return false;
            });

            if (activeTicks >= DURATION) {
                deactivateKindness();
            }
        }
    }
}
