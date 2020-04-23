package simplepets.brainsynder.menu.items.list;

import lib.brainsynder.item.ItemBuilder;
import org.bukkit.Material;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.menu.ItemStorageMenu;
import simplepets.brainsynder.menu.inventory.CustomInventory;
import simplepets.brainsynder.menu.items.Item;
import simplepets.brainsynder.player.PetOwner;

import java.io.File;

public class Storage extends Item {
    public Storage(File file) {
        super(file);
    }

    @Override
    public ItemBuilder getDefaultItem() {
        return new ItemBuilder(Material.CHEST).withName("&e&lItem Storage");
    }

    @Override
    public void onClick(PetOwner owner, CustomInventory inventory) {
        if (PetCore.get().getConfiguration().getBoolean("PetItemStorage.Enable")) {
            if (PetCore.hasPerm(owner.getPlayer(), "Pet.itemstorage")) {
                ItemStorageMenu.loadFromPlayer(owner.getPlayer());
            } else {
                owner.getPlayer().sendMessage(PetCore.get().getMessages().getString("No-Permission", true));
            }
        }
    }
}
