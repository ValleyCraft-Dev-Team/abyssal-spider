package net.linkle.abyssal.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.sound.SoundEvent;

@Mixin(ParrotEntity.class)
public interface ParrotEntityMixin {
    @Accessor("MOB_SOUNDS")
    static Map<EntityType<?>, SoundEvent> getMobSounds() {
        throw new AssertionError();
    }
}
