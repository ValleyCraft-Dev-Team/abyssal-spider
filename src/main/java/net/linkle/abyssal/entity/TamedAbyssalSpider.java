package net.linkle.abyssal.entity;

import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.linkle.abyssal.Main;
import net.linkle.abyssal.ai.goal.AttackWithOwnerGoal;
import net.linkle.abyssal.ai.goal.FollowOwnerGoal;
import net.linkle.abyssal.ai.goal.SitGoal;
import net.linkle.abyssal.ai.goal.TrackOwnerAttackerGoal;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TamedAbyssalSpider extends PathAwareEntity implements Tameable, Tamed, Sittable {
    
    protected static final TrackedData<Byte> SPIDER_FLAGS = DataTracker.registerData(TamedAbyssalSpider.class, TrackedDataHandlerRegistry.BYTE);
    protected static final TrackedData<Byte> SITTING = DataTracker.registerData(TamedAbyssalSpider.class, TrackedDataHandlerRegistry.BYTE);
    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(TamedAbyssalSpider.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    protected boolean sitting;
    
    public TamedAbyssalSpider(EntityType<? extends TamedAbyssalSpider> entityType, World world) {
        super(entityType, world);
    }
    
    @Override
    protected void initGoals() {
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(2, new SitGoal(this));
        goalSelector.add(3, new PounceAtTargetGoal(this, 0.4F));
        goalSelector.add(4, new MeleeAttackGoal(this, 1.0, true));
        goalSelector.add(5, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        goalSelector.add(6, new WanderAroundFarGoal(this, 0.8));
        goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.add(7, new LookAroundGoal(this));
        targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        targetSelector.add(2, new AttackWithOwnerGoal(this));
        targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
    }
    
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(SPIDER_FLAGS, (byte)0);
        dataTracker.startTracking(SITTING, (byte)0);
        dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }
    
    @Override
    public boolean occludeVibrationSignals() {
        return true;
    }
    
    @Override
    public double getMountedHeightOffset() {
        return (double)(this.getHeight() * 0.5F);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }
    
    @Override
    public boolean shouldRender(double distance) {
        return super.shouldRender(distance / 3.0);
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }
    
    @Override
    public void slowMovement(BlockState state, Vec3d multiplier) {
        if (!state.isOf(Blocks.COBWEB)) {
            super.slowMovement(state, multiplier);
        }
    }
    
    @Override
    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }
    
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return effect.getEffectType() == StatusEffects.POISON ? false : super.canHaveStatusEffect(effect);
    }
    
    @Override
    public boolean isClimbing() {
        return isClimbingWall();
    }
    
    public boolean isClimbingWall() {
        return (this.dataTracker.get(SPIDER_FLAGS) & 1) != 0;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = this.dataTracker.get(SPIDER_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b = (byte)(b & -2);
        }

        this.dataTracker.set(SPIDER_FLAGS, b);
    }
    
    @Override
    public void setInSittingPose(boolean inSittingPose) {
        dataTracker.set(SITTING, (byte)(inSittingPose ? 1 : 0));
    }
    
    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }
    
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }

        nbt.putBoolean("Sitting", this.sitting);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        UUID uUID = null;
        if (nbt.containsUuid("Owner")) {
            uUID = nbt.getUuid("Owner");
        }

        if (uUID != null) {
            setOwnerUuid(uUID);
        }

        this.sitting = nbt.getBoolean("Sitting");
        this.setInSittingPose(this.sitting);
    }

    @Override
    public void tickMovement() {
        tickHandSwing();
        super.tickMovement();
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return isOwner(target) ? false : super.canTarget(target);
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }
    
    @Override
    public AbstractTeam getScoreboardTeam() {
        var owner = getOwner();
        if (owner != null) {
            return owner.getScoreboardTeam();
        }

        return super.getScoreboardTeam();
    }

    @Override
    public boolean isTeammate(Entity other) {
        var owner = getOwner();
        if (other == owner) {
            return true;
        }

        if (owner != null) {
            return owner.isTeammate(other);
        }

        return super.isTeammate(other);
    }

    @Override
    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (target instanceof CreeperEntity || target instanceof GhastEntity) {
            return false;
        } else if (target instanceof Tameable tameable) {
            return tameable.getOwner() != owner;
        } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).shouldDamagePlayer((PlayerEntity)target)) {
            return false;
        } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTame()) {
            return false;
        } else {
            return !(target instanceof TameableEntity) || !((TameableEntity)target).isTamed();
        }
    }
    
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.65F;
    }
    
    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isLeashed();
    }
    
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (world.isClient) {
            return isOwner(player) ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (itemStack.isOf(Items.BONE) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                this.heal((float)item.getFoodComponent().getHunger());
                return ActionResult.SUCCESS;
            }

            ActionResult actionResult = super.interactMob(player, hand);
            if ((!actionResult.isAccepted() || isBaby()) && isOwner(player)) {
                Main.LOGGER.info("Toggle spider sit");
                setSitting(!isSitting());
                jumping = false;
                navigation.stop();
                getNavigation().stop();
                setTarget(null);
                return ActionResult.SUCCESS;
            }

            return actionResult;
        }
    }
    
    @Nullable
    @Override
    public UUID getOwnerUuid() {
        return dataTracker.get(OWNER_UUID).orElse(null);
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        try {
            UUID uUID = getOwnerUuid();
            return uUID == null ? null : world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }
    
    public void setOwner(PlayerEntity player) {
        setOwnerUuid(player.getUuid());
    }
    
    @Override
    public boolean isSitting() {
        return sitting;
    }

    @Override
    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }
    
    @Override
    public boolean isInSittingPose() {
        return dataTracker.get(SITTING) == 1;
    }
    
    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
}
