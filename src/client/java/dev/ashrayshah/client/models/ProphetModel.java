package dev.ashrayshah.client.models;

import dev.ashrayshah.client.entities.ProphetEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class ProphetModel extends DefaultedEntityGeoModel<ProphetEntity> {
    public ProphetModel() {
        // First arg is modid and second is entity filename
        super(Identifier.of("cultish", "prophet"));
    }
}
