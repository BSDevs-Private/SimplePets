package simplepets.brainsynder.nms.v1_16_R2.entities;

import net.minecraft.server.v1_16_R2.EntityInsentient;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.World;
import simplepets.brainsynder.api.entity.misc.IEntityNoClipPet;
import simplepets.brainsynder.api.pet.IPet;

public abstract class EntityNoClipPet extends EntityPet implements IEntityNoClipPet {
    public EntityNoClipPet(EntityTypes<? extends EntityInsentient> type, World world, IPet pet) {
        super(type, world, pet);
    }

    public EntityNoClipPet(EntityTypes<? extends EntityInsentient> type, World world) {
        super(type, world);
    }

    public void noClip(boolean flag) {
        this.noclip = flag;
    }
}
