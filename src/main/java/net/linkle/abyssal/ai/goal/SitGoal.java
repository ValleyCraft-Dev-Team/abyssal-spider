package net.linkle.abyssal.ai.goal;

import java.util.EnumSet;

import net.linkle.abyssal.entity.Sittable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

public class SitGoal extends Goal {
    private final MobEntity entity;
    private final Sittable sittable;
    private final Tameable tameable;

    public SitGoal(MobEntity entity) {
        this.entity = entity;
        this.sittable = (Sittable)entity;
        this.tameable = (Tameable)entity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    @Override
    public boolean shouldContinue() {
        return sittable.isSitting();
    }

    @Override
    public boolean canStart() {
        if (entity.isInsideWaterOrBubbleColumn()) {
            return false;
        } else if (!entity.isOnGround()) {
            return false;
        } else {
            var owner = (LivingEntity)tameable.getOwner();
            if (owner == null) {
                return true;
            } else {
                return entity.squaredDistanceTo(owner) < 144.0 && owner.getAttacker() != null ? false : sittable.isSitting();
            }
        }
    }

    @Override
    public void start() {
        entity.getNavigation().stop();
        sittable.setInSittingPose(true);
    }

    @Override
    public void stop() {
        sittable.setInSittingPose(false);
    }
}
