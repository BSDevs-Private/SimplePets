package simplepets.brainsynder.menu.menuItems;

import lib.brainsynder.item.ItemBuilder;
import lib.brainsynder.utils.DyeColorWrapper;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.passive.IEntityLlamaPet;
import simplepets.brainsynder.menu.menuItems.base.MenuItemAbstract;
import simplepets.brainsynder.pet.PetType;
import simplepets.brainsynder.utils.ValueType;

import java.util.ArrayList;
import java.util.List;

@ValueType(def = "CREAMY", target = "https://github.com/brainsynder-Dev/SimplePets/blob/master/MAIN/src/main/java/simplepets/brainsynder/wrapper/LlamaColor.java")
public class LlamaColor extends MenuItemAbstract {
    public LlamaColor(PetType type, IEntityPet entityPet) {
        super(type, entityPet);
    }
    public LlamaColor(PetType type) {
        super(type);
    }

    @Override
    public ItemBuilder getItem() {
        ItemBuilder item = type.getDataItemByName("llamacolor", 0);
        if (entityPet instanceof IEntityLlamaPet) {
            IEntityLlamaPet var = (IEntityLlamaPet) entityPet;
            simplepets.brainsynder.wrapper.LlamaColor typeID = simplepets.brainsynder.wrapper.LlamaColor.CREAMY;
            if (var.getLlamaColor() != null) {
                typeID = var.getLlamaColor();
            }
            switch (typeID) {
                case CREAMY:
                    item = type.getDataItemByName("llamacolor", 0);
                    break;
                case BROWN:
                    item = type.getDataItemByName("llamacolor", 1);
                    break;
                case GRAY:
                    item = type.getDataItemByName("llamacolor", 2);
                    break;
                case WHITE:
                    item = type.getDataItemByName("llamacolor", 3);
                    break;
            }
        }
        return item;
    }

    @Override
    public List<ItemBuilder> getDefaultItems() {
        List<ItemBuilder> items = new ArrayList<>();
        ItemBuilder item = lib.brainsynder.nms.DataConverter.getColoredMaterial(lib.brainsynder.nms.DataConverter.MaterialType.WOOL, DyeColorWrapper.YELLOW);
        item.withName("&6Creamy");
        items.add(item);
        item = lib.brainsynder.nms.DataConverter.getColoredMaterial(lib.brainsynder.nms.DataConverter.MaterialType.WOOL, DyeColorWrapper.BROWN);
        item.withName("&6Brown");
        items.add(item);
        item = lib.brainsynder.nms.DataConverter.getColoredMaterial(lib.brainsynder.nms.DataConverter.MaterialType.WOOL, DyeColorWrapper.GRAY);
        item.withName("&6Gray");
        items.add(item);
        item = lib.brainsynder.nms.DataConverter.getColoredMaterial(lib.brainsynder.nms.DataConverter.MaterialType.WOOL, DyeColorWrapper.WHITE);
        item.withName("&6White");
        items.add(item);
        return items;
    }

    @Override
    public void onLeftClick() {
        if (entityPet instanceof IEntityLlamaPet) {
            IEntityLlamaPet var = (IEntityLlamaPet) entityPet;
            int typeID = 0;
            if (var.getLlamaColor() != null) {
                typeID = var.getLlamaColor().getId();
            }
            if (typeID == 3) {
                typeID = 0;
            } else {
                typeID++;
            }
            var.setSkinColor(simplepets.brainsynder.wrapper.LlamaColor.getByID(typeID));
        }
    }

    @Override
    public void onRightClick() {
        if (entityPet instanceof IEntityLlamaPet) {
            IEntityLlamaPet var = (IEntityLlamaPet) entityPet;
            int typeID = 0;
            if (var.getLlamaColor() != null) {
                typeID = var.getLlamaColor().getId();
            }
            if (typeID == 0) {
                typeID = 3;
            } else {
                typeID--;
            }
            var.setSkinColor(simplepets.brainsynder.wrapper.LlamaColor.getByID(typeID));
        }
    }
}
