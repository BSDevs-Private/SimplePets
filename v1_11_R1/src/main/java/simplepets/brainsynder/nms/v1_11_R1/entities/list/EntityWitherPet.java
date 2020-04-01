package simplepets.brainsynder.nms.v1_11_R1.entities.list;

import lib.brainsynder.nbt.StorageTagCompound;
import net.minecraft.server.v1_11_R1.DataWatcher;
import net.minecraft.server.v1_11_R1.DataWatcherObject;
import net.minecraft.server.v1_11_R1.DataWatcherRegistry;
import net.minecraft.server.v1_11_R1.World;
import simplepets.brainsynder.api.entity.hostile.IEntityWitherPet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.v1_11_R1.entities.EntityPet;

public class EntityWitherPet extends EntityPet implements IEntityWitherPet {
    private static final DataWatcherObject<Integer> a;
    private static final DataWatcherObject<Integer> b;
    private static final DataWatcherObject<Integer> c;
    private static final DataWatcherObject<Integer> SHIELDED;

    static {
        a = DataWatcher.a(EntityWitherPet.class, DataWatcherRegistry.b);
        b = DataWatcher.a(EntityWitherPet.class, DataWatcherRegistry.b);
        c = DataWatcher.a(EntityWitherPet.class, DataWatcherRegistry.b);
        SHIELDED = DataWatcher.a(EntityWitherPet.class, DataWatcherRegistry.b);
    }


    public EntityWitherPet(World world) {
        super(world);
    }

    public EntityWitherPet(World world, IPet pet) {
        super(world, pet);
    }

    @Override
    protected void registerDatawatchers() {
        super.registerDatawatchers();
        this.datawatcher.register(a, 0);
        this.datawatcher.register(b, 0);
        this.datawatcher.register(c, 0);
        this.datawatcher.register(SHIELDED, 0);
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setBoolean("shielded", isShielded());
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        if (object.hasKey("shielded")) {
            setShielded(object.getBoolean("shielded"));
        }
        super.applyCompound(object);
    }

    @Override
    public boolean isShielded() {
        return this.datawatcher.get(SHIELDED) == 1;
    }

    @Override
    public void setShielded(boolean flag) {
        this.datawatcher.set(SHIELDED, flag ? 1 : 0);
        this.setHealth((float) (flag ? 150 : 300));
    }
}
