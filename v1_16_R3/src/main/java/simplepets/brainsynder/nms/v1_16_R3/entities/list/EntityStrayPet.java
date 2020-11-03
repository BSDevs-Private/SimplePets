package simplepets.brainsynder.nms.v1_16_R3.entities.list;

import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import simplepets.brainsynder.api.Size;
import simplepets.brainsynder.api.entity.hostile.IEntityStrayPet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.v1_16_R3.entities.branch.EntitySkeletonAbstractPet;

/**
 * NMS: {@link net.minecraft.server.v1_16_R2.EntitySkeletonStray}
 */
@Size(width = 0.6F, length = 1.9F)
public class EntityStrayPet extends EntitySkeletonAbstractPet implements IEntityStrayPet {
    public EntityStrayPet(EntityTypes<? extends EntityCreature> type, World world, IPet pet) {
        super(type, world, pet);
    }
    public EntityStrayPet(EntityTypes<? extends EntityCreature> type, World world) {
        super(type, world);
    }
}

