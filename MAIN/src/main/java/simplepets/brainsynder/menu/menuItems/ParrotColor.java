package simplepets.brainsynder.menu.menuItems;

import lib.brainsynder.item.ItemBuilder;
import lib.brainsynder.utils.DyeColorWrapper;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.passive.IEntityParrotPet;
import simplepets.brainsynder.menu.menuItems.base.MenuItemAbstract;
import simplepets.brainsynder.pet.PetType;
import simplepets.brainsynder.utils.ValueType;
import simplepets.brainsynder.wrapper.ParrotVariant;

import java.util.ArrayList;
import java.util.List;

@ValueType(def = "RED", target = "https://github.com/brainsynder-Dev/SimplePets/blob/master/MAIN/src/main/java/simplepets/brainsynder/wrapper/ParrotVariant.java")
public class ParrotColor extends MenuItemAbstract {

    public ParrotColor(PetType type, IEntityPet entityPet) {
        super(type, entityPet);
    }
    public ParrotColor(PetType type) {
        super(type);
    }

    @Override
    public ItemBuilder getItem() {
        ItemBuilder item = null;
        if (entityPet instanceof IEntityParrotPet) {
            IEntityParrotPet var = (IEntityParrotPet) entityPet;
            ParrotVariant typeID = var.getVariant();
            switch (typeID) {
                case BLUE:
                    item = type.getDataItemByName("parrotcolor", 0);
                    break;
                case CYAN:
                    item = type.getDataItemByName("parrotcolor", 1);
                    break;
                case GRAY:
                    item = type.getDataItemByName("parrotcolor", 2);
                    break;
                case GREEN:
                    item = type.getDataItemByName("parrotcolor", 3);
                    break;
                case RED:
                    item = type.getDataItemByName("parrotcolor", 4);
                    break;
            }
        }
        return item;
    }

    @Override
    public List<ItemBuilder> getDefaultItems() {
        List<ItemBuilder> items = new ArrayList<>();
        ItemBuilder item = lib.brainsynder.nms.DataConverter.getColoredMaterial(lib.brainsynder.nms.DataConverter.MaterialType.WOOL, DyeColorWrapper.BLUE);
        item.withName("&9Blue");
        items.add(item);
        item = lib.brainsynder.nms.DataConverter.getColoredMaterial(lib.brainsynder.nms.DataConverter.MaterialType.WOOL, DyeColorWrapper.CYAN);
        item.withName("&3Cyan");
        items.add(item);
        item = lib.brainsynder.nms.DataConverter.getColoredMaterial(lib.brainsynder.nms.DataConverter.MaterialType.WOOL, DyeColorWrapper.LIGHT_GRAY);
        item.withName("&7Gray");
        items.add(item);
        item = lib.brainsynder.nms.DataConverter.getColoredMaterial(lib.brainsynder.nms.DataConverter.MaterialType.WOOL, DyeColorWrapper.GREEN);
        item.withName("&2Green");
        items.add(item);
        item = lib.brainsynder.nms.DataConverter.getColoredMaterial(lib.brainsynder.nms.DataConverter.MaterialType.WOOL, DyeColorWrapper.RED);
        item.withName("&cRed");
        items.add(item);

        return items;
    }

    @Override
    public void onLeftClick() {
        if (entityPet instanceof IEntityParrotPet) {
            IEntityParrotPet var = (IEntityParrotPet) entityPet;

            ParrotVariant wrapper = ParrotVariant.RED;
            if (var.getVariant() != null)
                wrapper = var.getVariant();
            var.setVariant(ParrotVariant.getNext(wrapper));
        }
    }

    @Override
    public void onRightClick() {
        if (entityPet instanceof IEntityParrotPet) {
            IEntityParrotPet var = (IEntityParrotPet) entityPet;
            ParrotVariant wrapper = ParrotVariant.RED;
            if (var.getVariant() != null)
                wrapper = var.getVariant();
            var.setVariant(ParrotVariant.getPrevious(wrapper));
        }
    }
}
