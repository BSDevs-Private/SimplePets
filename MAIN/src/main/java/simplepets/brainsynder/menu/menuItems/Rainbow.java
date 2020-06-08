package simplepets.brainsynder.menu.menuItems;

import lib.brainsynder.item.ItemBuilder;
import org.bukkit.Material;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.misc.IRainbow;
import simplepets.brainsynder.menu.menuItems.base.MenuItemAbstract;
import simplepets.brainsynder.pet.PetType;
import simplepets.brainsynder.utils.ValueType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ValueType(type = "boolean", def = "false")
public class Rainbow extends MenuItemAbstract {
    public Rainbow(PetType type, IEntityPet entityPet) {
        super(type, entityPet);
    }
    public Rainbow(PetType type) {
        super(type);
    }

    @Override
    public ItemBuilder getItem() {
        ItemBuilder item = type.getDataItemByName("rainbow", 0);
        if (entityPet instanceof IRainbow) {
            IRainbow var = (IRainbow) entityPet;
            item.withName(item.getName().replace("%value%", String.valueOf(var.isRainbow())));
        }
        return item;
    }

    @Override
    public List<ItemBuilder> getDefaultItems() {
        ItemBuilder item = new ItemBuilder(Material.PLAYER_HEAD).withName("&6Rainbow: &e%value%")
                .setTexture("http://textures.minecraft.net/texture/cffc977cc7e10e564a09638a53bbc4c54c9c8dac7450ba3dfa3c9099d94f5");
        return new ArrayList<>(Collections.singleton(item));
    }

    @Override
    public void onLeftClick() {
        if (entityPet instanceof IRainbow) {
            IRainbow pet = (IRainbow) entityPet;
            pet.setRainbow(!pet.isRainbow());
        }
    }
}
