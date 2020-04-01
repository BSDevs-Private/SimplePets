package simplepets.brainsynder.nms.v1_11_R1.entities.list;

import com.google.common.base.Optional;
import lib.brainsynder.nbt.StorageTagCompound;
import net.minecraft.server.v1_11_R1.*;
import simplepets.brainsynder.api.entity.hostile.IEntityEndermanPet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.v1_11_R1.entities.EntityPet;

public class EntityEndermanPet extends EntityPet implements IEntityEndermanPet {


    private static final DataWatcherObject<Optional<IBlockData>> BLOCK;
    private static final DataWatcherObject<Boolean> SCREAMING;

    static {
        BLOCK = DataWatcher.a(EntityEndermanPet.class, DataWatcherRegistry.g);
        SCREAMING = DataWatcher.a(EntityEndermanPet.class, DataWatcherRegistry.h);
    }
    public EntityEndermanPet(World world) {
        super(world);
    }

    public EntityEndermanPet(World world, IPet pet) {
        super(world, pet);
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setBoolean("screaming", isScreaming());
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        if (object.hasKey("screaming")) {
            setScreaming(object.getBoolean("screaming"));
        }
        super.applyCompound(object);
    }

    @Override
    protected void registerDatawatchers() {
        super.registerDatawatchers();
        this.datawatcher.register(BLOCK, Optional.absent());
        this.datawatcher.register(SCREAMING, Boolean.FALSE);
    }

    public boolean isScreaming() {
        return this.datawatcher.get(SCREAMING);
    }

    public void setScreaming(boolean flag) {
        this.datawatcher.set(SCREAMING, flag);
    }

    public IBlockData getCarried() {
        return (IBlockData) ((Optional) this.datawatcher.get(BLOCK)).orNull();
    }

    public void setCarried(IBlockData iblockdata) {
        this.datawatcher.set(BLOCK, Optional.fromNullable(iblockdata));
    }
}
