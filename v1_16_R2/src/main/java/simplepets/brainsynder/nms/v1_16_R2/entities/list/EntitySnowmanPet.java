package simplepets.brainsynder.nms.v1_16_R2.entities.list;

import lib.brainsynder.nbt.StorageTagCompound;
import net.minecraft.server.v1_16_R2.DataWatcher;
import net.minecraft.server.v1_16_R2.DataWatcherObject;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.World;
import simplepets.brainsynder.api.Size;
import simplepets.brainsynder.api.entity.passive.IEntitySnowmanPet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.v1_16_R2.entities.EntityPet;
import simplepets.brainsynder.nms.v1_16_R2.utils.DataWatcherWrapper;

/**
 * NMS: {@link net.minecraft.server.v1_16_R2.EntitySnowman}
 */
@Size(width = 0.4F, length = 1.8F)
public class EntitySnowmanPet extends EntityPet implements IEntitySnowmanPet {
    private static final DataWatcherObject<Byte> PUMPKIN;

    static {
        PUMPKIN = DataWatcher.a(EntitySnowmanPet.class, DataWatcherWrapper.BYTE);
    }

    public EntitySnowmanPet(World world) {
        super(EntityTypes.SNOW_GOLEM, world);
    }

    public EntitySnowmanPet(World world, IPet pet) {
        super(EntityTypes.SNOW_GOLEM, world, pet);
    }

    protected void registerDatawatchers() {
        super.registerDatawatchers();
        this.datawatcher.register(PUMPKIN, (byte) 0);
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setBoolean("pumpkin", hasPumpkin());
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        if (object.hasKey("pumpkin")) setHasPumpkin(object.getBoolean("pumpkin"));
        super.applyCompound(object);
    }

    public boolean hasPumpkin() {
        return (this.datawatcher.get(PUMPKIN) & 16) != 0;
    }

    public void setHasPumpkin(boolean flag) {
        byte b0 = this.datawatcher.get(PUMPKIN);
        if (flag) {
            this.datawatcher.set(PUMPKIN, (byte) (b0 | 16));
        } else {
            this.datawatcher.set(PUMPKIN, (byte) (b0 & -17));
        }
    }
}
