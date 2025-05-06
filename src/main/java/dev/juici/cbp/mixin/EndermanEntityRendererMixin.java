package dev.juici.cbp.mixin;

import dev.juici.cbp.CraftingBeyondProjects;
import dev.juici.cbp.entity.SkinnedEnderman;
import net.minecraft.client.render.entity.EndermanEntityRenderer;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntityRenderer.class)
public abstract class EndermanEntityRendererMixin {
    @Unique
    private static final Identifier SKINNED_TEX = Identifier.of(CraftingBeyondProjects.MOD_ID,
            "textures/entity/enderman/skinned.png");

    @Inject(at = @At("HEAD"), method = "getTexture", cancellable = true)
    private void getTexture(EndermanEntity enderman, CallbackInfoReturnable<Identifier> info) {
        if (((SkinnedEnderman) enderman).isSkinned()) {
            info.setReturnValue(SKINNED_TEX);
        }
    }
}
