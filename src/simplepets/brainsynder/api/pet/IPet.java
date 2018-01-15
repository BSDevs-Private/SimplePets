package simplepets.brainsynder.api.pet;

import org.bukkit.entity.Player;
import simple.brainsynder.storage.IStorage;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.menu.menuItems.base.MenuItem;
import simplepets.brainsynder.pet.PetType;
import simplepets.brainsynder.wrapper.EntityWrapper;

public interface IPet {
    Player getOwner();

    void removePet(boolean var1);

    PetType getPetType();

    EntityWrapper getEntityType();

    IEntityPet getEntity();

    IEntityPet getVisableEntity();

    IStorage<MenuItem> getItems();

    boolean isHat();

    void setHat(boolean isHat);

    boolean isVehicle();

    void setVehicle(boolean vehicle);

    void toggleRiding();

    void toggleHat();
}
