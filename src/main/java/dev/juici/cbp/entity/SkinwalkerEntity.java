package dev.juici.cbp.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SkinwalkerEntity extends PathAwareEntity {
    private static final TrackedData<Optional<UUID>> COPIED_PLAYER = DataTracker.registerData(SkinwalkerEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<String> COPIED_SKIN = DataTracker.registerData(SkinwalkerEntity.class, TrackedDataHandlerRegistry.STRING);

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
                this.setCopiedPlayer(randomPlayer.getUuid());
                this.setCustomName(Text.literal(randomPlayer.getName().getString()));
                this.setCustomNameVisible(true);
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
        builder.add(COPIED_PLAYER, Optional.empty());
        builder.add(COPIED_SKIN, "minecraft:textures/entity/player/wide/steve.png");
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("CopiedPlayer")) {
            this.setCopiedPlayer(nbt.getUuid("CopiedPlayer"));
        }
        if (nbt.contains("CopiedSkin")) {
            Identifier skin = Identifier.tryParse(nbt.getString("CopiedSkin"));
            if (skin != null) {
                this.setCopiedSkin(skin);
            }
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        UUID player = this.getCopiedPlayer();
        if (player != null) {
            nbt.putUuid("CopiedPlayer", player);
        }
        Identifier skin = this.getCopiedSkin();
        if (skin != null) {
            nbt.putString("CopiedSkin", skin.toString());
        }
    }

    public UUID getCopiedPlayer() {
        return this.dataTracker.get(COPIED_PLAYER).orElse(null);
    }

    public void setCopiedPlayer(UUID player) {
        this.dataTracker.set(COPIED_PLAYER, Optional.ofNullable(player));
    }

    public Identifier getCopiedSkin() {
        return Identifier.tryParse(this.dataTracker.get(COPIED_SKIN));
    }

    public void setCopiedSkin(Identifier skin) {
        if (skin != null) {
            this.dataTracker.set(COPIED_SKIN, skin.toString());
        }
    }
}
