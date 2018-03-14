package simplepets.brainsynder.nms.entities.v1_9_R1;

import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import simple.brainsynder.nbt.StorageTagCompound;
import simplepets.brainsynder.menu.PetDataMenu;
import simplepets.brainsynder.nms.entities.type.main.IEntityPet;
import simplepets.brainsynder.nms.entities.type.main.IFlyablePet;
import simplepets.brainsynder.pet.IPet;
import simplepets.brainsynder.pet.PetMoveEvent;
import simplepets.brainsynder.wrapper.EntityWrapper;

import java.lang.reflect.Field;

/**
 * @Deprecated Will be removed when MC version 1.13 is released
 */
@Deprecated
public abstract class EntityPet extends EntityCreature implements IAnimal,
        IEntityPet {
    private IPet pet;
    private Field JUMP_FIELD = null;

    public EntityPet(World world, IPet pet) {
        super(world);

        this.pet = pet;
        try {
            JUMP_FIELD = EntityLiving.class.getDeclaredField("bc");
        } catch (NoSuchFieldException | SecurityException e1) {
            e1.printStackTrace();
        }
        JUMP_FIELD.setAccessible(true);

    }

    @Override
    public EntityWrapper getEntityType() {
        return pet.getPetType().getType();
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = new StorageTagCompound();
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {

    }

    public void setPassenger(int pos, org.bukkit.entity.Entity entity, org.bukkit.entity.Entity passenger) {
        ((CraftEntity) entity).getHandle().passengers.add(pos, ((CraftEntity) passenger).getHandle());
        PacketPlayOutMount packet = new PacketPlayOutMount(((CraftEntity) entity).getHandle());
        if (entity instanceof Player) {
            ((CraftPlayer) entity).getHandle().playerConnection.sendPacket(packet);
        } else {
            ((CraftPlayer) getOwner()).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void removePassenger(org.bukkit.entity.Entity entity) {
        ((CraftEntity) entity).getHandle().passengers.clear();
        PacketPlayOutMount packet = new PacketPlayOutMount(((CraftEntity) entity).getHandle());
        if (entity instanceof Player) {
            ((CraftPlayer) entity).getHandle().playerConnection.sendPacket(packet);
        } else {
            ((CraftPlayer) getOwner()).getHandle().playerConnection.sendPacket(packet);
        }
    }


    @Override
    public org.bukkit.entity.Entity getEntity() {
        return super.getBukkitEntity();
    }

    public CraftCreature getBukkitEntity() {
        return (CraftCreature) super.getBukkitEntity();
    }

    @Override
    public Player getOwner() {
        return pet.getOwner();
    }

    @Override
    public IPet getPet() {
        return pet;
    }

    private boolean isOnGround(Entity entity) {
        org.bukkit.block.Block block = entity.getBukkitEntity().getLocation().subtract(0, 0.4, 0).getBlock();
        return block.getType().isSolid();
    }

    public boolean a(EntityHuman human) {
        return this.onInteract((Player) human.getBukkitEntity());
    }

    public void repeatTask() {
        if (this instanceof IFlyablePet) {
            if (getPet().isVehicle()) {
                if (!this.onGround && this.motY < 0.0D) {
                    this.motY *= 0.6D;
                }
            }
        }
        if (pet.getOwner() != null) {
            Player p = pet.getOwner();
            if (((CraftPlayer) p).getHandle().isInvisible() != this.isInvisible()) {
                this.setInvisible(!this.isInvisible());
            }

            if (this.getPet().isHat()) {
                this.lastYaw = this.yaw = p.getLocation().getYaw();
            }
        }
    }

    @Override
    public void m() {
        super.m();
        repeatTask();
    }

    public boolean onInteract(Player p) {
        if (p.getName().equals(pet.getOwner().getName())) {
            PetDataMenu data = new PetDataMenu(pet);
            data.showTo(p);
            return true;
        } else {
            return false;
        }

    }

    public boolean isOwnerRiding() {
        EntityPlayer owner = ((CraftPlayer) getOwner()).getHandle();
        for (Entity passenger : this.passengers) {
            if (passenger == owner) {
                return true;
            }
        }
        return false;
    }

    public void g(float sideMot, float forwMot) {
        if (this.pet == null) {
            super.g(sideMot, forwMot);
            this.P = 0.5F;
            return;
        }
        if (!isOwnerRiding()) {
            super.g(sideMot, forwMot);
            this.P = 0.5F;
            return;
        }
        EntityPlayer owner = ((CraftPlayer) getOwner()).getHandle();
        this.P = 1.0F;
        this.lastYaw = this.yaw = owner.yaw;
        this.pitch = owner.pitch * 0.5F;
        this.setYawPitch(this.yaw, this.pitch);
        this.aM = this.aG = this.yaw;

        sideMot = owner.bd * 0.5F;
        forwMot = owner.be;

        if (forwMot <= 0.0F) {
            forwMot *= 0.25F;
        }
        sideMot *= 0.75F;
        k(0.2F);
        if (!world.isClientSide) {
            PetMoveEvent event = new PetMoveEvent(this, PetMoveEvent.Cause.RIDE);
            Bukkit.getServer().getPluginManager().callEvent(event);
            super.g(sideMot, forwMot);
        }
        if ((this.JUMP_FIELD != null) && (isOnGround(this))) {
            try {
                if (this.JUMP_FIELD.getBoolean(owner)) {
                    this.motY = 0.3D;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if ((!isOnGround(this)) && (this instanceof IFlyablePet) && (pet.getPetType().canFly(getOwner()))) {
            try {
                if (JUMP_FIELD.getBoolean(owner)) {
                    this.motY = 0.5D;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    protected void i() {
        super.i();
        initDataWatcher();
    }

    protected void initDataWatcher() {
    }

    public boolean k_() {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(getBoundingBox().b);
        int k = MathHelper.floor(this.locZ);
        net.minecraft.server.v1_9_R1.Block block = this.world.getType(new BlockPosition(i, j, k)).getBlock();
        return (block == Blocks.LADDER) || (block == Blocks.VINE);
    }

}
