package simplepets.brainsynder.nms.v1_16_R1.entities.list;

import lib.brainsynder.nbt.StorageTagCompound;
import net.minecraft.server.v1_16_R1.*;
import simplepets.brainsynder.api.Size;
import simplepets.brainsynder.api.entity.passive.IEntityPigPet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.v1_16_R1.entities.AgeableEntityPet;
import simplepets.brainsynder.nms.v1_16_R1.utils.DataWatcherWrapper;

/**
 * NMS: {@link net.minecraft.server.v1_16_R1.EntityPig}
 */
@Size(width = 0.9F, length = 0.9F)
public class EntityPigPet extends AgeableEntityPet implements IEntityPigPet {
    private static final DataWatcherObject<Boolean> SADDLE;

    static {
        SADDLE = DataWatcher.a(EntityPigPet.class, DataWatcherWrapper.BOOLEAN);
    }

    public EntityPigPet(EntityTypes<? extends EntityCreature> type, World world) {
        super(type, world);
    }

    public EntityPigPet(EntityTypes<? extends EntityCreature> type, World world, IPet pet) {
        super(type, world, pet);
    }

    @Override
    protected void registerDatawatchers() {
        datawatcher.register(SADDLE, false);
        super.registerDatawatchers();
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setBoolean("saddled", hasSaddle());
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        if (object.hasKey("saddled")) setSaddled(object.getBoolean("saddled"));
        super.applyCompound(object);
    }

    public boolean hasSaddle() {
        return datawatcher.get(SADDLE);
    }

    public void setSaddled(boolean flag) {
        datawatcher.set(SADDLE, flag);
    }
}
