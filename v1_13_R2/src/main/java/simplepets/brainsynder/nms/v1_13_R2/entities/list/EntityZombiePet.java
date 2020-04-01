package simplepets.brainsynder.nms.v1_13_R2.entities.list;

import lib.brainsynder.nbt.StorageTagCompound;
import net.minecraft.server.v1_13_R2.DataWatcher;
import net.minecraft.server.v1_13_R2.DataWatcherObject;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.World;
import simplepets.brainsynder.api.Size;
import simplepets.brainsynder.api.entity.hostile.IEntityZombiePet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.v1_13_R2.entities.AgeableEntityPet;
import simplepets.brainsynder.nms.v1_13_R2.utils.DataWatcherWrapper;

/**
 * NMS: {@link net.minecraft.server.v1_13_R2.EntityZombie}
 */
@Size(width = 0.6F, length = 1.8F)
public class EntityZombiePet extends AgeableEntityPet implements IEntityZombiePet {
    private static final DataWatcherObject<Integer> VILLAGER_TYPE;
    private static final DataWatcherObject<Boolean> ARMS_RAISED;

    static {
        VILLAGER_TYPE = DataWatcher.a(EntityZombiePet.class, DataWatcherWrapper.INT);
        ARMS_RAISED = DataWatcher.a(EntityZombiePet.class, DataWatcherWrapper.BOOLEAN);
    }

    public EntityZombiePet(EntityTypes<?> type, World world) {
        super(type, world);
    }
    public EntityZombiePet(EntityTypes<?> type, World world, IPet pet) {
        super(type, world, pet);
    }

    @Override
    protected void registerDatawatchers() {
        super.registerDatawatchers();
        this.getDataWatcher().register(VILLAGER_TYPE, 0);
        this.getDataWatcher().register(ARMS_RAISED, false);
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setBoolean("raised", isArmsRaised());
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        if (object.hasKey("raised"))
            setArmsRaised(object.getBoolean("raised"));
        super.applyCompound(object);
    }

    @Override
    public boolean isArmsRaised() {
        return datawatcher.get(ARMS_RAISED);
    }

    @Override
    public void setArmsRaised(boolean flag) {
        datawatcher.set(ARMS_RAISED, flag);
    }
}