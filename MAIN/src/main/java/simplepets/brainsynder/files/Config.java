package simplepets.brainsynder.files;

import com.google.common.collect.Lists;
import lib.brainsynder.files.YamlFile;
import lib.brainsynder.utils.Utilities;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.utils.RenameType;
import simplepets.brainsynder.utils.debug.Debug;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class Config extends YamlFile {
    public Config(PetCore core) {
        super(core.getDataFolder(), "config.yml");
    }

    @Override
    public void loadDefaults() {
        int configVersion = 5; // The current config version, changing this will force the plugin to make a backup of the existing config
        int version = getInt("version", -1);
        if (getFile().length() == 0) {
            version = configVersion;
            addDefault("version", configVersion, "The version the config file was last majorly updated for\n   NOTE: CHANGING THIS WILL RESET YOUR CONFIG FILE");
        }

        if (version != configVersion) {
            File folder = new File(PetCore.getInstance().getDataFolder() + File.separator+"Config Backups");
            if (!folder.exists()) folder.mkdirs();
            File file = getFile();
            String name = file.getName().replace(".yml", "")+"_backup(v"+version+").yml";
            simplepets.brainsynder.utils.Utilities.makeBackup(this, new File(folder, name));
            addDefault("version", configVersion, "The version the config file was last majorly updated for\n   NOTE: CHANGING THIS WILL RESET YOUR CONFIG FILE");
        }

        addDefault("Reload-Detected", false, "This is used by the plugin to detect if the plugin was previously unloaded (via /reload or by a plugin)\n    CAN NOT BE CUSTOMIZED");
        addDefault("Update-Checking.Enabled", true, "Would you like to check for new jenkins builds?\nDefault: true");
        addDefault("Update-Checking.Message-On-Join", true, "Would you like to be alerted when there is a new update when you log in?\n(MUST HAVE 'pet.update' permission or OP)\nDefault: true");
        addSectionHeader("Update-Checking.unit", Utilities.AlignText.LEFT, "The unit of time for update checking\nTime Units:\n- SECONDS\n- MINUTES\n- HOURS\n- DAYS");
        addDefault("Update-Checking.unit", TimeUnit.HOURS.name());
        addDefault("Update-Checking.time", 12);

        addDefault("Permissions.Ignored-List", Lists.newArrayList(
                ""
        ), "Any permission in this list will be ignored when the plugin checks if the player has permission\n(Will act like the player has the permission, without actually having it)");

        addDefault("Permissions.Needs-Pet-Permission-for-GUI", false, "Enabling this would require players to have access to at least 1 pets permission\nDefault: false");
        addDefault("Permissions.Enabled", true, "Disabling this would grant ALL players access to pets (they wont need permissions)\nDefault: true");
        addDefault("Permissions.Data-Permissions", true, "Disabling this will make it so players do not need to have any data permissions (EG. pet.type.armorstand.data.silent)\nDefault: true"); // TODO: Reformat this value


        addDefault("Remove-Item-If-No-Permission", true, "Disabling this would remove all the pets the player does not have access to from the GUI\nDefault: true");

        addDefault("RemovePetsOnWorldChange", true, "Disabling this will remove a players pet when they change worlds\nDefault true");

        addDefault(ECONOMY_TOGGLE, false,
                "Enabling this would allow players to buy pets via Vault/TokenManager\n" +
                        "NOTE: If 'Needs-Permission' is set to true the players will still need the permission for the pet\n" +
                        "Default: false");
        addDefault(ECONOMY_TYPE, "UNKNOWN",
                "What type of economy do you have?\n" +
                        "Economy Types:\n" +
                        "- UNKNOWN (Will act like all pets are free)\n" +
                        "- EXPERIENCE_LEVEL (Will use the players experience levels as payment)\n" +
                        "- EXPERIENCE_POINTS (Will use the players experience points as payment [level 30 = 1395])\n" +
                        "- VAULT https://www.spigotmc.org/resources/34315/\n" +
                        "- TOKEN_MANAGER https://www.spigotmc.org/resources/8610/\n" +
                        "- GEMS_ECONOMY https://www.spigotmc.org/resources/19655/\n" +
                        "Default: 'UNKNOWN'");

        addDefault(SPAWN_PARTICLE_TOGGLE, true, "Disabling this would make it so there is no particles when a player spawns a pet\nDefault: true");
        addDefault(FAILED_PARTICLE_TOGGLE, true, "Disabling this would make it so there is no particles when a player fails to spawn a pet\nDefault: true");
        addDefault(REMOVE_PARTICLE_TOGGLE, true, "Disabling this would make it so there is no particles when a player removes a pet\nDefault: true");
        addDefault(NAME_PARTICLE_TOGGLE, true, "Disabling this would make it so there is no particles when a player renames a pet\nDefault: true");
        addDefault(TELEPORT_PARTICLE_TOGGLE, true, "Disabling this would make it so there is no particles when a players pet teleports to its owner\nDefault: true");
        addDefault(FAILED_TASK_PARTICLE_TOGGLE, true, "Disabling this would make it so there is no particles when a task for a pet fails\nDefault: true");
        addDefault("Complete-Mobspawning-Deny-Bypass", true, "Disabling this would allow other plugins to deny the pets from spawning\nDefault: true");

        addDefault("PetItemStorage.Enable", true, "Disabling this will remove players access to a GUI that stores items\nDefault: true");
        addDefault("PetItemStorage.Inventory-Size", 27, "What size would you like the inventory to be?\nSizes: 9,18,27,36,45,54\nDefault: 27");

        addDefault("Pathfinding.Distance-to-Player", 1.9, "How far away can the pets stand near the player?\nDefault: 1.9");
        addDefault("Pathfinding.Distance-to-Player_LargePets", 2.9, "How far away can the large pets (Giants/Ghast) stand near the player?\nDefault: 2.9");
        addDefault("Pathfinding.Min-Distance-For-Teleport", 20.0, "How far away from the player does the pet have to be before it teleports closer?\nDefault: 20");
        addDefault("Pathfinding.Stopping-Distance", 3.0, "How far away can the pet be before it will stop walking near the player?\nDefault: 3");
        addDefault("Pathfinding.Stopping-Distance_LargePets", 7.0, "How far away can the large pet (Giant/Ghast) be before it will stop walking near the player?\nDefault: 7");

        addDefault("Worlds.Enabled", false, "Enabling this will make it so pets only work in the worlds that are listed in 'Allowed-Worlds'\nDefault: false");
        addDefault("Worlds.Allowed-Worlds", Collections.singletonList("world"));

        addSectionHeader("WorldGuard", Utilities.AlignText.LEFT, "Recently our code changed to support WorldGuard flags\nFlag Names:\n- allow-pet-spawn\n- allow-pet-enter\n- allow-pet-riding");
        addDefault("WorldGuard.BypassPermission", "region.bypass", "This is the bypass permission for WorldGuard\nDefault: region.bypass");

        addDefault("PlotSquared.BypassPermission", "plots.admin", "This is the bypass permission for PlotSquared\nDefault: plots.admin");
        addDefault("PlotSquared.On-Unclaimed-Plots.Move", true, "Are pets allowed to move on unclaimed plots?\nDefault: true");
        addDefault("PlotSquared.On-Unclaimed-Plots.Spawn", true, "Are pets allowed to be spawned on unclaimed plots?\nDefault: true");
        addDefault("PlotSquared.On-Unclaimed-Plots.Riding", true, "Are players allowed to ride pets on unclaimed plots?\nDefault: true");
        addDefault("PlotSquared.On-Roads.Move", true, "Are pets allowed to move on the roads?\nDefault: true");
        addDefault("PlotSquared.On-Roads.Spawn", true, "Are pets allowed to be spawned while on roads\nDefault: true");
        addDefault("PlotSquared.On-Roads.Riding", true, "Are players allowed to ride pets while on a road\nDefault: true");
        addDefault("PlotSquared.Block-If-Denied.Move", true, "Are pets allowed to move on a plot their owner is blocked on?\nDefault: true");
        addDefault("PlotSquared.Block-If-Denied.Spawn", true, "Are pets allowed to be spawned on plots their owner is blocked on?\n(Is this even needed? Its not like they can get on the plot anyway XD)\n\nDefault: true");
        addDefault("PlotSquared.Block-If-Denied.Riding", true, "Can player ride their pets onto plots they are denied on?\nDefault: true");

        addDefault("WorldBorder.Block-If-Denied.Move", true, "Are pets allowed to move when inside a WorldBorder?\nDefault: true");
        addDefault("WorldBorder.Block-If-Denied.Spawn", true, "Can pets be spawned in a WorldBorder?\nDefault: true");
        addDefault("WorldBorder.Block-If-Denied.Riding", true, "Can a player ride a pet in a WorldBorder?\nDefault: true");

        addDefault("MySQL.Enabled", false, "Would you like to use MySQL to save player/pet data?\nIf it is disabled the plugin will use SQLite\nDefault: false");
        addDefault("MySQL.Table", "simplepets");
        addDefault("MySQL.Host", "host");
        addDefault("MySQL.Port", 3306);
        addDefault("MySQL.DatabaseName", "SimplePets", "Example: SimplePets");
        addDefault("MySQL.Login.Username", "username");
        addDefault("MySQL.Login.Password", "password");
        addDefault("MySQL.Options.UseSSL", false);

        addDefault("Debug.Enabled", true, "Would you like to view Debug information in the console/logs?\nIt can help us see where issues are.\nDefault: true");
        addDefault("Debug.Levels", Arrays.asList(
                "NORMAL", "MODERATE", "ERROR"
        ), "What level of debug info would you like to see?\n" +
                "NORMAL = Normal Info\n" +
                "MODERATE = Moderate Info (Warnings)\n" +
                "ERROR = Critical/Errors (No explanation here i hope...)");

        // TODO: Reformat these value
        addDefault("PetToggles.GlowWhenVanished", true, "When the owner is vanished should the owner see their pet with the glow effect?\nDefault: true");
        addDefault("PetToggles.ShowPetNames", true, "Should pet names be seen?\nDefault: true");
        addDefault("PetToggles.HideNameOnShift", true, "Should pet names be hidden when their owner sneaks?\nDefault: true");
        addDefault("PetToggles.AutoRemove.Enabled", true, "Disabling this will make it so pets wont be automatically removed if the player is afk\nDefault: true");
        addDefault("PetToggles.AutoRemove.TickDelay", 10000, "What should the wait be?\nThis is in ticks (20 ticks = 1 second)\nDefault: 10000");
        addDefault(MOUNTABLE, true, "Are all pets able to be rideable?\nDefault: true");
        addDefault(HATS, true, "Are all pets able to be worn as hats?\nDefault: true");
        addDefault(FLYABLE, false, "Are all pets able to fly when mounted?\nDefault: false");
        addDefault("Respawn-Last-Pet-On-Login", true, "When a player logs back in should their pet be spawned in as well?\nNOTE: If the player removed their pet before logging out then it wont respawn.\nDefault: true");

        addDefault("RenamePet.Enabled", true, "Should players be able to rename pets?\nDefault: true");
        addDefault("RenamePet.Type", RenameType.ANVIL.name(), "How should the player be able to modify their pets name?\n" +
                "Types:\n" +
                "- COMMAND (They have to use the '/pet rename' command to set the name)\n" +
                "- CHAT (They have to type the name in chat)\n" +
                "- ANVIL (The Anvil GUI will open and they can change the name there)\n" +
                "- SIGN [REQUIRES ProtocolLib] (Will open a Sign GUI they input the name on the configured line)\n" +
                "Default: ANVIL");
        addDefault(LIMIT_CHARS_TOGGLE, false, "Should the name have a limited number of characters?\nDefault: false");
        addDefault(LIMIT_CHARS_NUMBER, 10, "What should the character limit be set to?\nDefault: 10");
        addDefault("RenamePet.Blocked-Words", Arrays.asList("jeb_"),
                "Are there words you don't want in a pets name?\n" +
                        "   If you put word in between [] it will check if it contains ANY part of the word\n" +
                        "         Example: [ass] will also flag glass because it contains the word in it\n" +
                        "   If you just have the word it will check for the exact word\n" +
                        "         Example: 'ass' will just be flagged so 'glass' can be said");
        addDefault(MAGIC, false, "Are pet names allowed to have the &k color code?\nDefault: false");
        addDefault(COLOR, true, "Are pet names allowed to be colored?\nDefault: true");
        addDefault(HEX, true, "Are pet names allowed to have HEX colors?\nNOTE: If this is disabled only regular chat color will be used (EG: '&c')\nExample: '&#c986b2'\nDefault: true");
        updateSections();
    }


    public void setEnum(String key, Enum anEnum) {
        set(key, anEnum.name());
    }

    public <E extends Enum>E getEnum (String key, Class<E> type) {
        return getEnum(key, type, null);
    }
    public <E extends Enum>E getEnum (String key, Class<E> type, E fallback) {
        if (!contains(key)) return fallback;
        return (E) E.valueOf(type, getString(key));
    }

    private void updateSections () {
        move("Needs-Pet-Permission-To-Open-GUI", "Permissions.Needs-Pet-Permission-for-GUI", logMove());
        move("Needs-Data-Permissions", "Permissions.Data-Permissions", logMove());
        move("Needs-Permission", "Permissions.Enabled", logMove());
        move("Use&k", MAGIC, logMove());
        move("ShowParticles", SPAWN_PARTICLE_TOGGLE, logMove());
        move("ColorCodes", COLOR, logMove());
        move("RenamePet.Limit-Number-Of-Characters", LIMIT_CHARS_TOGGLE, logMove());
        move("RenamePet.CharacterLimit", LIMIT_CHARS_NUMBER, logMove());
        move("RenamePet.Allow-ColorCodes", COLOR, logMove());
        move("Allow-Pets-Being-Mounts", MOUNTABLE, logMove());
        move("Allow-Pets-Being-Hats", HATS, logMove());
        remove("WorldGuard.Spawning.Always-Allowed");
        remove("WorldGuard.Spawning.Blocked-Regions");
        remove("WorldGuard.Pet-Entering.Always-Allowed");
        remove("WorldGuard.Pet-Entering.Blocked-Regions");
        remove("WorldGuard.Pet-Riding.Always-Allowed");
        remove("WorldGuard.Pet-Riding.Blocked-Regions");
        remove("OldPetRegistering");
        remove("MySQL.Options.Auto_Reconnect"); // Not needed as the new system needs the connection to remain
        remove("PetToggles.Weight.Enabled");
        remove("PetToggles.Weight.Weight_Stacked");
        remove("PetToggles.Weight.Max_Weight");
        move("UseVaultEconomy", ECONOMY_TOGGLE);
    }

    public static final String
            MOUNTABLE = "PetToggles.All-Pets-Mountable",
            HATS = "PetToggles.All-Pets-Hat",
            FLYABLE = "PetToggles.All-Pets-Flyable",
            MAGIC = "RenamePet.Allow-&k",
            COLOR = "RenamePet.ColorCodes.Enabled",
            HEX = "RenamePet.ColorCodes.Allow-HEX-Colors",
            ECONOMY_TOGGLE = "Economy.Enabled",
            ECONOMY_TYPE = "Economy.Type",
            LIMIT_CHARS_TOGGLE = "RenamePet.Character-Limit.Enabled",
            LIMIT_CHARS_NUMBER = "RenamePet.Character-Limit.Limit",
            SPAWN_PARTICLE_TOGGLE = "Particles.Summon",
            FAILED_PARTICLE_TOGGLE = "Particles.Failed-Summon",
            TELEPORT_PARTICLE_TOGGLE = "Particles.Teleport",
            FAILED_TASK_PARTICLE_TOGGLE = "Particles.Failed-Task",
            REMOVE_PARTICLE_TOGGLE = "Particles.Remove",
            NAME_PARTICLE_TOGGLE = "Particles.Name-Change";


    protected BiConsumer<String, String> logMove () {
        return (oldKey, newKey) -> {
            String name = getClass().getSimpleName().replace("File", "");
            Debug.debug("["+name+"] Moving '"+oldKey+"' to '"+newKey+"'");
        };
    }
}