package simplepets.brainsynder.nms.entities.v1_9_R1.list;

import net.minecraft.server.v1_9_R1.DataWatcher;
import net.minecraft.server.v1_9_R1.DataWatcherObject;
import net.minecraft.server.v1_9_R1.DataWatcherRegistry;
import net.minecraft.server.v1_9_R1.World;
import simplepets.brainsynder.nms.entities.type.IEntityZombiePet;
import simplepets.brainsynder.nms.entities.v1_9_R1.AgeableEntityPet;
import simplepets.brainsynder.pet.IPet;

/**
 * @Deprecated Will be removed when MC version 1.13 is released
 */
@Deprecated
public class EntityZombiePet extends AgeableEntityPet implements IEntityZombiePet {
    private static final DataWatcherObject<Integer> by;
    private static final DataWatcherObject<Boolean> bz;
    private static final DataWatcherObject<Boolean> bA;

    static {
        by = DataWatcher.a(EntityZombiePet.class, DataWatcherRegistry.b);
        bz = DataWatcher.a(EntityZombiePet.class, DataWatcherRegistry.h);
        bA = DataWatcher.a(EntityZombiePet.class, DataWatcherRegistry.h);
    }

    public EntityZombiePet(World world, IPet pet) {
        super(world, pet);
    }

    @Override
    protected void initDataWatcher() {
        super.initDataWatcher();
        this.getDataWatcher().register(by, 0);
        this.getDataWatcher().register(bz, false);
        this.getDataWatcher().register(bA, false);
    }

    @Override
    public void setVillager(boolean flag) {

    }
}
