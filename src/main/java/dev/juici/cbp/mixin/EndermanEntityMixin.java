package dev.juici.cbp.mixin;

import dev.juici.cbp.entity.SkinnedEnderman;
import dev.juici.cbp.registry.CBPItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
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

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin extends MobEntity implements SkinnedEnderman {
    @Unique
    private boolean isSkinned = false;

    public EndermanEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isSkinned() {
        return isSkinned;
    }

    @Override
    public void setSkinned(boolean skinned) {
        this.isSkinned = skinned;
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack item = player.getStackInHand(hand);
        if (item.getItem() instanceof ShearsItem && !this.isSkinned) {
            this.setSkinned(true);

            player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR);
            item.damage(1, player, player.getPreferredEquipmentSlot(item));

            int count = player.getRandom().nextInt(2);
            ItemEntity pearls = new ItemEntity(player.getWorld(),
                    this.getX(), this.getY() + 2, this.getZ(),
                    new ItemStack(Items.ENDER_PEARL, count));
            player.getWorld().spawnEntity(pearls);
            ItemEntity rods = new ItemEntity(player.getWorld(),
                    this.getX(), this.getY() + 2, this.getZ(),
                    new ItemStack(CBPItems.BELLE_ROD, count));
            player.getWorld().spawnEntity(rods);

            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }
}
