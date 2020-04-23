package simplepets.brainsynder.menu.inventory.listeners;

import lib.brainsynder.storage.IStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.event.pet.PetDataChangeEvent;
import simplepets.brainsynder.menu.holders.PetDataHolder;
import simplepets.brainsynder.menu.holders.SelectionHolder;
import simplepets.brainsynder.menu.inventory.list.DataMenu;
import simplepets.brainsynder.menu.items.Item;
import simplepets.brainsynder.menu.menuItems.base.MenuItem;
import simplepets.brainsynder.menu.menuItems.base.MenuItemAbstract;
import simplepets.brainsynder.player.PetOwner;

public class DataListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() == null) return;
        if (!(e.getInventory().getHolder() instanceof PetDataHolder)) return;
        DataMenu menu = PetCore.get().getInvLoaders().PET_DATA;
        if ((e.getWhoClicked() instanceof Player)) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            final Player p = (Player) e.getWhoClicked();
            PetOwner owner = PetOwner.getPetOwner(p);
            if (e.getCurrentItem() == null) return;
            if (e.getClick().isShiftClick()) {
                e.setCancelled(true);
                return;
            }
            Item item = PetCore.get().getItemLoaders().getLoader(e.getCurrentItem());
            if (item != null) {
                item.onClick(owner, menu);
                return;
            }
            IStorage<MenuItem> menuItems = owner.getPet().getItems();
            if (!menuItems.isEmpty()) {
                while (menuItems.hasNext()) {
                    MenuItemAbstract menuItem = (MenuItemAbstract) menuItems.next();
                    PetDataChangeEvent.ClickType type = PetDataChangeEvent.ClickType.LEFT_CLICK;
                    if (e.getClick().toString().toLowerCase().contains("right"))
                        type = PetDataChangeEvent.ClickType.RIGHT_CLICK;
                    if (menuItem.getItem().isSimilar(e.getCurrentItem())) {
                        PetDataChangeEvent event = new PetDataChangeEvent(menuItem, type);
                        Bukkit.getServer().getPluginManager().callEvent(event);
                        if (!event.isCancelled()) {
                            if (type == PetDataChangeEvent.ClickType.LEFT_CLICK) {
                                menuItem.onLeftClick();
                                e.getInventory().setItem(e.getSlot(), menuItem.getItem().build());
                            } else {
                                menuItem.onRightClick();
                                e.getInventory().setItem(e.getSlot(), menuItem.getItem().build());
                            }
                            e.getInventory().setItem(e.getSlot(), menuItem.getItem().build());
                        }
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose (InventoryCloseEvent e) {
        if (e.getInventory().getHolder() == null) return;
        if (!(e.getInventory().getHolder() instanceof SelectionHolder)) return;
        DataMenu menu = PetCore.get().getInvLoaders().PET_DATA;
        menu.reset(PetOwner.getPetOwner((Player) e.getPlayer()));
    }
}
