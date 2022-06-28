package net.linkle.abyssal.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.linkle.abyssal.Main;
import net.linkle.abyssal.client.entity.renderer.SpiderEntityRenderer;
import net.linkle.abyssal.client.entity.renderer.TamedSpiderEntityRenderer;
import net.linkle.abyssal.entity.AbyssalSpider;
import net.linkle.abyssal.entity.TamedAbyssalSpider;
import net.linkle.abyssal.mixin.ParrotEntityMixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction.Location;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class Entities {
    
    public static final EntityType<AbyssalSpider> ABYSSAL_SPIDER = register("abyssal_spider", 
            FabricEntityTypeBuilder.createMob().entityFactory(AbyssalSpider::new).spawnGroup(SpawnGroup.MONSTER)
            .dimensions(new EntityDimensions(1.4F, 0.9F, true)).fireImmune().trackRangeChunks(8)
            .spawnRestriction(Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark).build());
    
    public static final EntityType<TamedAbyssalSpider> TAMED_ABYSSAL_SPIDER = register("tamed_abyssal_spider", 
            FabricEntityTypeBuilder.createMob().entityFactory(TamedAbyssalSpider::new).spawnGroup(SpawnGroup.CREATURE)
            .dimensions(new EntityDimensions(1.4F, 0.9F, true)).fireImmune().trackRangeChunks(8).build());
    
    public static void init() {
        initContents();
        
        FabricDefaultAttributeRegistry.register(ABYSSAL_SPIDER, AbyssalSpider.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(TAMED_ABYSSAL_SPIDER, TamedAbyssalSpider.createAttributes());
        
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_DARK), SpawnGroup.MONSTER, ABYSSAL_SPIDER, 20, 2, 3);
    }
    
    @Environment(EnvType.CLIENT)
    public static void initClient() {
        EntityRendererRegistry.register(ABYSSAL_SPIDER, SpiderEntityRenderer.create("abyssal_spider_angry"));
        EntityRendererRegistry.register(TAMED_ABYSSAL_SPIDER, TamedSpiderEntityRenderer::new);
    }
    
    private static void initContents()  {
        var parrotSounds = ParrotEntityMixin.getMobSounds();
        parrotSounds.put(ABYSSAL_SPIDER, SoundEvents.ENTITY_PARROT_IMITATE_SPIDER);
        parrotSounds.put(TAMED_ABYSSAL_SPIDER, SoundEvents.ENTITY_PARROT_IMITATE_SPIDER);
    }
    
    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, Main.makeId(id), entity);
    }
}
