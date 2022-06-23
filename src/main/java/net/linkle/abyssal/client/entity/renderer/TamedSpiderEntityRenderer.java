package net.linkle.abyssal.client.entity.renderer;

import net.linkle.abyssal.Main;
import net.linkle.abyssal.client.entity.model.TamedSpiderEntityModel;
import net.linkle.abyssal.entity.TamedAbyssalSpider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class TamedSpiderEntityRenderer extends MobEntityRenderer<TamedAbyssalSpider, TamedSpiderEntityModel> {
    
    public static final Identifier TEXTURE = Main.makeId("textures/entity/spider/abyssal_spider_tamed.png");

    public TamedSpiderEntityRenderer(Context context) {
        super(context, new TamedSpiderEntityModel(context.getPart(EntityModelLayers.SPIDER)), 0.8f);
    }
    
    @Override
    protected float getLyingAngle(TamedAbyssalSpider spiderEntity) {
        return 180.0f;
    }

    @Override
    public Identifier getTexture(TamedAbyssalSpider entity) {
        return TEXTURE;
    }
}
