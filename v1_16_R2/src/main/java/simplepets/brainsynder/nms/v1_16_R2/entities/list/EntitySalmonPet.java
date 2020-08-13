package simplepets.brainsynder.nms.v1_16_R2.entities.list;

import net.minecraft.server.v1_16_R2.EntityCreature;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.World;
import simplepets.brainsynder.api.entity.passive.IEntityCodPet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.v1_16_R2.entities.EntityFishPet;

/**
 * NMS: {@link net.minecraft.server.v1_16_R2.EntitySalmon}
 */
public class EntitySalmonPet extends EntityFishPet implements IEntityCodPet {
    public EntitySalmonPet(EntityTypes<? extends EntityCreature> type, World world, IPet pet) {
        super(type, world, pet);
    }

    public EntitySalmonPet(EntityTypes<? extends EntityCreature> type, World world) {
        super(type, world);
    }
}
