package net.linkle.abyssal.client.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.linkle.abyssal.Main;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpiderEntityRenderer extends MobEntityRenderer<PathAwareEntity, SpiderEntityModel<PathAwareEntity>> {

    private final Identifier texture;
    
    public SpiderEntityRenderer(Context context, Identifier texture) {
        super(context, new SpiderEntityModel<PathAwareEntity>(context.getPart(EntityModelLayers.SPIDER)), 0.8f);
        this.texture = texture;
    }

    @Override
    public Identifier getTexture(PathAwareEntity entity) {
        return texture;
    }
    
    @Override
    protected float getLyingAngle(PathAwareEntity spiderEntity) {
        return 180.0f;
    }

    /** @param texture file name of the spider texture. */
    public static EntityRendererFactory<PathAwareEntity> create(String texture) {
        var id = Main.makeId("textures/entity/spider/" + texture + ".png");
        return context -> new SpiderEntityRenderer(context, id);
    }
}
