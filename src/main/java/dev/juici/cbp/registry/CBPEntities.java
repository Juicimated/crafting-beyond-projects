package dev.juici.cbp.registry;

import dev.juici.cbp.CraftingBeyondProjects;
import dev.juici.cbp.entity.SkinwalkerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CBPEntities {
    public static final EntityType<SkinwalkerEntity> SKINWALKER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(CraftingBeyondProjects.MOD_ID, "skinwalker"),
            EntityType.Builder.create(SkinwalkerEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6f, 1.5f)
                    .maxTrackingRange(80).build());

    public static void register() {
        CraftingBeyondProjects.LOGGER.info("[CBP] Registering entities");

        FabricDefaultAttributeRegistry.register(SKINWALKER, SkinwalkerEntity.createSkinwalkerAttributes());
    }
}
