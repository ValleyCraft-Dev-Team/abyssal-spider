package net.linkle.abyssal.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.linkle.abyssal.Main;
import net.linkle.abyssal.client.entity.renderer.SpiderEntityRenderer;
import net.linkle.abyssal.entity.AbyssalSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class Entities {
    
    public static final EntityType<AbyssalSpider> ABYSSAL_SPIDER = register("abyssal_spider", 
            FabricEntityTypeBuilder.createMob().entityFactory(AbyssalSpider::new).spawnGroup(SpawnGroup.MONSTER)
            .dimensions(new EntityDimensions(1.4F, 0.9F, false)).fireImmune().trackRangeBlocks(8).build());
    
    public static void init() {
        FabricDefaultAttributeRegistry.register(ABYSSAL_SPIDER, AbyssalSpider.createSpiderAttributes());
    }
    
    @Environment(EnvType.CLIENT)
    public static void initClient() {
        EntityRendererRegistry.register(ABYSSAL_SPIDER, SpiderEntityRenderer.create("abyssal_spider_angry"));
    }
    
    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, Main.makeId(id), entity);
    }
}
