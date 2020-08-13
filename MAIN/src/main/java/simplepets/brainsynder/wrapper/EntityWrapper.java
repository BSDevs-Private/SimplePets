package simplepets.brainsynder.wrapper;

import org.bukkit.entity.EntityType;

// This class is not used for registering mobs only for internal use
public enum EntityWrapper {
    UNKNOWN("null"),
    ELDER_GUARDIAN,
    WITHER_SKELETON,
    STRAY,
    HUSK,
    ZOMBIE_VILLAGER,
    SKELETON_HORSE,
    ZOMBIE_HORSE,
    ARMOR_STAND,
    DONKEY,
    MULE,
    EVOKER("evocation_illager", "evoker"),
    VEX,
    VINDICATOR("vindication_illager", "vindicator"),
    ILLUSIONER("illusion_illager", "illusioner"),
    CREEPER,
    SKELETON,
    SPIDER,
    GIANT,
    ZOMBIE,
    SLIME,
    GHAST,
    PIG_ZOMBIE("zombie_pigman"),
    ENDERMAN,
    CAVE_SPIDER,
    SILVERFISH,
    BLAZE,
    MAGMA_CUBE,
    ENDER_DRAGON,
    WITHER,
    BAT,
    WITCH,
    ENDERMITE,
    GUARDIAN,
    SHULKER,
    PIG,
    SHEEP,
    COW,
    CHICKEN,
    SQUID,
    WOLF,
    MUSHROOM_COW("mooshroom"),
    SNOWMAN("snowman", "snow_golem"),
    OCELOT,
    IRON_GOLEM("villager_golem", "iron_golem"),
    HORSE,
    RABBIT,
    POLAR_BEAR,
    LLAMA,
    PARROT,
    VILLAGER,

    // 1.13 Mobs
    COD,
    SALMON,
    TURTLE,
    PHANTOM,
    DROWNED,
    DOLPHIN,
    PUFFER_FISH("puffer_fish", "pufferfish"),
    TROPICAL_FISH,

    // 1.14 Mobs
    TRADER_LLAMA,
    PANDA,
    CAT,
    PILLAGER,
    RAVAGER,
    WANDERING_TRADER,
    FOX,

    // 1.15 Mobs
    BEE,

    // 1.16 Mobs
    PIGLIN,
    HOGLIN,
    STRIDER,
    ZOGLIN,
    ZOMBIFIED_PIGLIN,

    // 1.16.2 Mobs
    PIGLIN_BRUTE
    ;

    private final String name;
    private final String typeName;

    EntityWrapper(String name) {
        this.name = name;
        typeName = name;
    }
    EntityWrapper(String name, String typeName) {
        this.name = name;
        this.typeName = typeName;
    }
    EntityWrapper() {
        this.name = name().toLowerCase();
        typeName = name;
    }

    public static EntityWrapper getByName(String name) {
        for (EntityWrapper entityWrapper : values()) {
            if (entityWrapper.name.equalsIgnoreCase(name)) {
                return entityWrapper;
            }
        }
        return null;
    }

    public EntityType toEntityType() {
        try {
            return EntityType.valueOf(name());
        } catch (Exception ignored) {
        }
        return EntityType.UNKNOWN;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getName() {
        return this.name;
    }
}
