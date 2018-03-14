package simplepets.brainsynder.nms.entities.v1_12_R1.list;

import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.DataWatcherRegistry;
import net.minecraft.server.v1_12_R1.World;
import simple.brainsynder.nbt.StorageTagCompound;
import simplepets.brainsynder.nms.entities.type.IEntityBlazePet;
import simplepets.brainsynder.nms.entities.v1_12_R1.EntityPet;
import simplepets.brainsynder.pet.IPet;
import simplepets.brainsynder.utils.Size;

@Size(width = 0.6f, length = 1.7f)
public class EntityBlazePet extends EntityPet implements IEntityBlazePet {
    private static final DataWatcherObject<Byte> ANGERED;

    static {
        ANGERED = DataWatcher.a(EntityBlazePet.class, DataWatcherRegistry.a);
    }

    public EntityBlazePet(World world) {
        super(world);
    }

    public EntityBlazePet(World world, IPet pet) {
        super(world, pet);
    }

    @Override
    protected void registerDatawatchers() {
        super.registerDatawatchers();
        this.datawatcher.register(ANGERED, (byte) 0);
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setBoolean("burning", isBurning());
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        if (object.hasKey("burning")) setBurning(object.getBoolean("burning"));
        super.applyCompound(object);
    }

    @Override
    public boolean isBurning() {
        return (this.datawatcher.get(ANGERED) & 1) != 0;
    }

    @Override
    public void setBurning(boolean var1) {
        byte b1 = this.datawatcher.get(ANGERED);
        if (var1) {
            b1 = (byte) (b1 | 1);
        } else {
            b1 &= -2;
        }

        this.datawatcher.set(ANGERED, b1);
    }
}
