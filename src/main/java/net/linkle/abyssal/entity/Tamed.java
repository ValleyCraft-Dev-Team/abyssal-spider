package net.linkle.abyssal.entity;

import net.minecraft.entity.LivingEntity;

public interface Tamed {
    boolean canAttackWithOwner(LivingEntity target, LivingEntity owner);
}
