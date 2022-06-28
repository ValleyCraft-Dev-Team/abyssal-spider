package net.linkle.abyssal.entity;

import net.linkle.abyssal.init.Entities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class AbyssalSpider extends SpiderEntity {

    public AbyssalSpider(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }
    
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(4, new AttackGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (world.isClient) {
            return itemStack.isOf(Items.BONE) ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (itemStack.isOf(Items.BONE)) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                if (this.random.nextInt(3) == 0) {
                    var spider = convertTo(Entities.TAMED_ABYSSAL_SPIDER, false);
                    spider.setOwner(player);
                    //world.sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                } else {
                    //world.sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }

                return ActionResult.SUCCESS;
            }

            return super.interactMob(player, hand);
        }
    }
    
    public static DefaultAttributeContainer.Builder createSpiderAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F);
    }
    
    @Override
    public boolean occludeVibrationSignals() {
        return true;
    }
    
    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(SpiderEntity spider) {
            super(spider, 1.0, true);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.mob.hasPassengers();
        }

        @Override
        public boolean shouldContinue() {
            float f = this.mob.getBrightnessAtEyes();
            if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
                this.mob.setTarget(null);
                return false;
            } else {
                return super.shouldContinue();
            }
        }

        @Override
        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double)(4.0F + entity.getWidth());
        }
    }
}
