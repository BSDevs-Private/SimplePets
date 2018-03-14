package simplepets.brainsynder.nms.entities.v1_11_R1.list;

import net.minecraft.server.v1_11_R1.World;
import simplepets.brainsynder.nms.entities.type.IEntitySkeletonPet;
import simplepets.brainsynder.nms.entities.v1_11_R1.branch.EntitySkeletonAbstractPet;
import simplepets.brainsynder.pet.IPet;

public class EntitySkeletonPet extends EntitySkeletonAbstractPet implements IEntitySkeletonPet {

    public EntitySkeletonPet(World world) {
        super(world);
    }

    public EntitySkeletonPet(World world, IPet pet) {
        super(world, pet);
    }
}
