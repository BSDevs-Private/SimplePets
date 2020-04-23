package simplepets.brainsynder.pet.types;

import lib.brainsynder.item.ItemBuilder;
import lib.brainsynder.sounds.SoundMaker;
import org.bukkit.Material;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.hostile.IEntityGhastPet;
import simplepets.brainsynder.pet.PetData;
import simplepets.brainsynder.pet.PetDefault;
import simplepets.brainsynder.wrapper.EntityWrapper;

public class GhastDefault extends PetDefault {
    public GhastDefault(PetCore plugin) {
        super(plugin, "ghast", SoundMaker.ENTITY_GHAST_AMBIENT, EntityWrapper.GHAST);
    }

    @Override
    public PetData getPetData() {
        return PetData.GHAST_SCREAM;
    }

    @Override
    public ItemBuilder getDefaultItem() {
        return new ItemBuilder(Material.GHAST_TEAR).withName("&f&lGhast Pet");
    }

    @Override
    public Class<? extends IEntityPet> getEntityClass() {
        return IEntityGhastPet.class;
    }
}
