package dev.ashrayshah.client.renderers;

import dev.ashrayshah.client.entities.ProphetEntity;
import dev.ashrayshah.client.models.ProphetModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ProphetRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<ProphetEntity, R> {
    public ProphetRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ProphetModel());
    }
}
