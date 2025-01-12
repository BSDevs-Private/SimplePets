package simplepets.brainsynder.versions.v1_18_R1.entity.list;

import net.minecraft.world.entity.EntityType;
import simplepets.brainsynder.api.entity.hostile.IEntityWitherSkeletonPet;
import simplepets.brainsynder.api.pet.PetType;
import simplepets.brainsynder.api.user.PetUser;
import simplepets.brainsynder.versions.v1_18_R1.entity.branch.EntitySkeletonAbstractPet;

/**
 * NMS: {@link net.minecraft.server.v1_16_R3.EntitySkeletonWither}
 */
public class EntityWitherSkeletonPet extends EntitySkeletonAbstractPet implements IEntityWitherSkeletonPet {
    public EntityWitherSkeletonPet(PetType type, PetUser user) {
        super(EntityType.WITHER_SKELETON, type, user);
    }
}
