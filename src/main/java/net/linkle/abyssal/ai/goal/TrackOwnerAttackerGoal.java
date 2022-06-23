package net.linkle.abyssal.ai.goal;

import java.util.EnumSet;

import net.linkle.abyssal.entity.Sittable;
import net.linkle.abyssal.entity.Tamed;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;

public class TrackOwnerAttackerGoal extends TrackTargetGoal {
    private final Sittable sittable;
    private final Tameable tameable;
    private final Tamed tamed;
    private LivingEntity attacker;
    private int lastAttackedTime;

    public TrackOwnerAttackerGoal(MobEntity entity) {
        super(entity, false);
        this.sittable = (Sittable)entity;
        this.tameable = (Tameable)entity;
        this.tamed = (Tamed)entity;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (sittable.isSitting()) {
            var owner = (LivingEntity)tameable.getOwner();
            if (owner == null) {
                return false;
            } else {
                this.attacker = owner.getAttacker();
                int i = owner.getLastAttackedTime();
                return i != this.lastAttackedTime
                    && this.canTrack(this.attacker, TargetPredicate.DEFAULT)
                    && this.tamed.canAttackWithOwner(this.attacker, owner);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacker);
        var owner = (LivingEntity)tameable.getOwner();
        if (owner != null) {
            this.lastAttackedTime = owner.getLastAttackedTime();
        }

        super.start();
    }
}
