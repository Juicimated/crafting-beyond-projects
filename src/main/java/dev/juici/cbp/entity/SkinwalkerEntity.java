package dev.juici.cbp.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SkinwalkerEntity extends PathAwareEntity {
    private UUID copiedPlayer = null;

    public SkinwalkerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSkinwalkerAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EntityData data = super.initialize(world, difficulty, spawnReason, entityData);

        if (world instanceof ServerWorld serverWorld) {
            List<ServerPlayerEntity> players = serverWorld.getServer().getPlayerManager().getPlayerList();
            if (!players.isEmpty()) {
                Random random = serverWorld.random;
                ServerPlayerEntity randomPlayer = players.get(random.nextInt(players.size()));
                this.copiedPlayer = randomPlayer.getUuid();
            }
        }

        return data;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("CopiedPlayer")) {
            this.copiedPlayer = nbt.getUuid("CopiedPlayer");
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.copiedPlayer != null) {
            nbt.putUuid("CopiedPlayer", this.copiedPlayer);
        }
    }

    public UUID getCopiedPlayer() {
        return copiedPlayer;
    }

    public void setCopiedPlayer(UUID uuid) {
        this.copiedPlayer = uuid;
    }
}
