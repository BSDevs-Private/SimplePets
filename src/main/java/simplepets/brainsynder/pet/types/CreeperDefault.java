package simplepets.brainsynder.pet.types;

import org.bukkit.Material;
import simple.brainsynder.sound.SoundMaker;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.hostile.IEntityCreeperPet;
import simplepets.brainsynder.pet.PetData;
import simplepets.brainsynder.pet.PetDefault;
import simplepets.brainsynder.utils.ItemBuilder;
import simplepets.brainsynder.wrapper.EntityWrapper;

public class CreeperDefault extends PetDefault {
    public CreeperDefault(PetCore plugin) {
        super(plugin, "creeper", SoundMaker.ENTITY_CREEPER_HURT, EntityWrapper.CREEPER);
    }

    @Override
    public ItemBuilder getDefaultItem() {
        return new ItemBuilder(Material.GUNPOWDER).withName("&f&lCreeper Pet");
    }

    @Override
    public Class<? extends IEntityPet> getEntityClass() {
        return IEntityCreeperPet.class;
    }

    @Override
    public PetData getPetData() {
        return PetData.POWERED;
    }
}
