package net.linkle.abyssal.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.linkle.abyssal.init.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;

@Mixin(WardenEntity.class)
public class WardenEntityMixin {
    @Inject(at = @At("HEAD"), method = "isValidTarget()Z", cancellable = true)
    public void isValidTarget(@Nullable Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (entity != null) {
            if (entity.getType() == Entities.ABYSSAL_SPIDER ||
                entity.getType() == Entities.TAMED_ABYSSAL_SPIDER) {
                info.setReturnValue(false);
            }
        }
    }
}
