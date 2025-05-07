package dev.juici.cbp.mixin;

import dev.juici.cbp.entity.SkinnedEnderman;
import dev.juici.cbp.registry.CBPItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin extends MobEntity implements SkinnedEnderman {
    @Unique
    private static final TrackedData<Boolean> SKINNED =
            DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public EndermanEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "initDataTracker")
    private void initSkinnedData(DataTracker.Builder builder, CallbackInfo info) {
        builder.add(SKINNED, false);
    }

    @Override
    public boolean isSkinned() {
        return this.dataTracker.get(SKINNED);
    }

    @Override
    public void setSkinned(boolean skinned) {
        this.dataTracker.set(SKINNED, skinned);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack item = player.getStackInHand(hand);
        if (item.getItem() instanceof ShearsItem && !isSkinned()) {
            player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR);
            item.damage(1, player, player.getPreferredEquipmentSlot(item));

            int count = player.getRandom().nextInt(2);
            ItemEntity pearls = new ItemEntity(player.getWorld(),
                    this.getX(), this.getY(), this.getZ(),
                    new ItemStack(Items.ENDER_PEARL, count));
            player.getWorld().spawnEntity(pearls);
            ItemEntity rods = new ItemEntity(player.getWorld(),
                    this.getX(), this.getY(), this.getZ(),
                    new ItemStack(CBPItems.BELLE_ROD, count));
            player.getWorld().spawnEntity(rods);

            this.setSkinned(true);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        if (!isSkinned()) {
            super.dropLoot(source, causedByPlayer);
        } else {
            this.dropStack(null);
        }
    }
}
