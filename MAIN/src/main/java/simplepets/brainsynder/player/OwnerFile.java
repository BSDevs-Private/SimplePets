package simplepets.brainsynder.player;

import lib.brainsynder.json.Json;
import lib.brainsynder.json.JsonArray;
import lib.brainsynder.nbt.*;
import lib.brainsynder.utils.Base64Wrapper;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.database.MySQL;
import simplepets.brainsynder.pet.PetType;
import simplepets.brainsynder.pet.TypeManager;
import simplepets.brainsynder.storage.files.PlayerStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class OwnerFile {
    private MySQL sql = null;
    private static final String SELECT_PETS = "SELECT * FROM `SimplePets` WHERE `UUID`=?";
    private static final String UPDATE = "UPDATE `SimplePets` SET `UnlockedPets`=?, `PetName`=?, `NeedsRespawn`=?, `SavedPets`=? WHERE `UUID`=?";
    private static final String INSERT = "INSERT INTO `SimplePets` (`UUID`, `name`, `UnlockedPets`, `PetName`, `NeedsRespawn`, `SavedPets`) VALUES(?,?,?,?,?,?)";
    private final PetOwner owner;

    OwnerFile(PetOwner owner) {
        this.owner = owner;
        sql = PetCore.get().getMySQL();
    }

    public void save() {
        save(true);
    }

    public void save(boolean savePet) {
        final Player p = owner.getPlayer();
        if (p == null) return;
        if (p.hasMetadata("npc") || p.hasMetadata("NPC")) return;

        if (savePet) {
            String needsRespawn = "null";
            try {
                needsRespawn = (((!owner.hasPet()) && (!savePet)) ? "null" : Base64Wrapper.encodeString(owner.pet.getEntity().asCompound().toString()));
            } catch (Exception ignored) {
            }

            if (PetCore.get().getConfiguration().isSet("MySQL.Enabled") && PetCore.get().getConfiguration().getBoolean("MySQL.Enabled")) {
                if (PetCore.get().isDisabling()) {
                    PetCore.get().debug("Could not save " + p.getName() + "'s Pet information because they did not log out before the plugin disabled...");
                    return;
                }
                if (sql == null) {
                    PetCore.get().debug(2, "Unable to save data to SQL, sql variable seems to be missing...");
                    return;
                }

                try {
                    String name = p.getName(),
                            uuid = p.getUniqueId().toString(),
                            petName = owner.getPetName();

                    if (petName == null || petName.isEmpty()) {
                        petName = "empty";
                    }

                    JSONObject obj = new JSONObject();
                    obj.put("StoredPets", owner.getOwnedPets());
                    String finalPetName = petName;

                    // The CompletableFuture.class is a a class that allows async operations (according to the docs)
                    String finalNeedsRespawn = needsRespawn;
                    CompletableFuture.runAsync(() -> {
                        try (Connection connection = sql.getSource().getConnection()) {
                            PreparedStatement select = connection.prepareStatement(SELECT_PETS);
                            select.setString(1, p.getUniqueId().toString());
                            ResultSet result = select.executeQuery();
                            if (result.next()) {
                                PreparedStatement update = connection.prepareStatement(UPDATE);
                                update.setString(1, Base64Wrapper.encodeString(obj.toJSONString()));
                                update.setString(2, Base64Wrapper.encodeString(finalPetName));
                                update.setString(3, finalNeedsRespawn);
                                update.setString(4, Base64Wrapper.encodeString(owner.getSavedPetsArray().toJSONString()));
                                update.setString(5, uuid);
                                update.execute();
                                update.close();
                            } else {
                                PreparedStatement insert = connection.prepareStatement(INSERT);
                                insert.setString(1, uuid);
                                insert.setString(2, name);
                                insert.setString(3, Base64Wrapper.encodeString(obj.toJSONString()));
                                insert.setString(4, Base64Wrapper.encodeString(finalPetName));
                                insert.setString(5, finalNeedsRespawn);
                                insert.setString(6, Base64Wrapper.encodeString(owner.getSavedPetsArray().toJSONString()));
                                insert.execute();
                                insert.close();
                            }
                            select.close();
                            result.close();
                        } catch (Exception e) {
                            PetCore.get().debug("Unable to save " + name + "'s Pet data.");
                            PetCore.get().debug("Data that failed to save: ");
                            PetCore.get().debug("- Name:" + name);
                            PetCore.get().debug("- UUID:" + uuid);
                            PetCore.get().debug("- PetName (Base64):" + Base64Wrapper.encodeString(finalPetName));
                            PetCore.get().debug("- PetData:" + finalNeedsRespawn);
                            PetCore.get().debug("- PurchasedPets (Base64):" + Base64Wrapper.encodeString(obj.toJSONString()));
                            PetCore.get().debug("- Error:");
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    PetCore.get().debug("Unable to save " + p.getName() + "'s Pet data.");
                    PetCore.get().debug("- Error:");
                    e.printStackTrace();
                }
                return;
            }
        }

        PetCore.get().getPlayerStorage(p, file -> {
            boolean canSave = false;
            if (!owner.getOwnedPets().isEmpty()) {
                StorageTagList list = new StorageTagList();
                owner.getOwnedPets().forEach(pet -> list.appendTag(new StorageTagString(pet.getName())));
                file.setTag("PurchasedPets", list);
                canSave = true;
            }
            if ((!savePet) && !owner.getSavedPetsArray().isEmpty()) {
                file.setJSONArray("SavedPets", owner.getSavedPetsArray());
                canSave = true;
            }
            if ((owner.getPetName() != null)
                    && (!owner.getPetName().equals("null"))
                    && (!owner.getPetName().equals("PetName"))) {
                file.setString("PetName", owner.getPetName());
                canSave = true;
            }

            // This will only save if it is not saving pet data
            if ((!savePet) && (owner.getStoredInventory() != null)) {
                file.setJSONObject("ItemStorage", owner.getStoredInventory());
                canSave = true;
            }
            // This will only save if it is saving pet data
            if (savePet && owner.hasPet()) {
                file.setTag("NeedsRespawn", owner.getPet().getEntity().asCompound());
                canSave = true;
            }
            if (canSave) file.save();
        });

    }

    void reload() {
        final Player p = owner.getPlayer();
        if (p == null) return;
        if (p.hasMetadata("npc") || p.hasMetadata("NPC")) return;
        if (PetCore.get().getConfiguration().isSet("MySQL.Enabled") && PetCore.get().getConfiguration().getBoolean("MySQL.Enabled")) {
            if (PetCore.get().isDisabling()) {
                PetCore.get().debug("Could not reload " + p.getName() + "'s Pet information because the plugin is disabling...");
                return;
            }
            if (sql == null) {
                PetCore.get().debug(2, "Unable to load data from SQL, sql variable seems to be missing...");
                return;
            }
            // The CompletableFuture.class is a a class that allows async operations (according to the docs)
            CompletableFuture.runAsync(() -> {
                final JSONObject data = new JSONObject();
                try (Connection connection = sql.getSource().getConnection()) {
                    PreparedStatement select = connection.prepareStatement(SELECT_PETS);
                    select.setString(1, p.getUniqueId().toString());
                    ResultSet result = select.executeQuery();
                    if (result.next()) {
                        data.put("UnlockedPets", parse(result.getString("UnlockedPets")));
                        data.put("PetName", result.getString("PetName"));
                        data.put("NeedsRespawn", result.getString("NeedsRespawn"));
                        data.put("SavedPets", result.getString("SavedPets"));
                    }
                    select.close();
                    result.close();
                } catch (Exception e) {
                    PetCore.get().debug("Unable to load " + p.getName() + "'s Pet data.");
                    e.printStackTrace();
                }
                new BukkitRunnable() { // run task to make it sync
                    @Override
                    public void run() {
                        try {
                            if ((data.containsKey("UnlockedPets"))
                                    && (data.get("UnlockedPets") != null)
                                    && (!String.valueOf(data.get("UnlockedPets")).isEmpty())) {
                                try {
                                    StorageTagList list = JsonToNBT.parse("").toList();
                                }catch (NBTException e){
                                    //TODO
                                }

                                JSONArray array = (JSONArray) ((JSONObject) data.get("UnlockedPets")).get("StoredPets");
                                owner.setRawOwned(array);
                            }
                            if ((data.containsKey("PetName"))
                                    && (data.get("PetName") != null)
                                    && (!String.valueOf(data.get("PetName")).isEmpty())) {
                                String name = Base64Wrapper.decodeString(String.valueOf(data.get("PetName")));
                                if (!name.equals("empty")) owner.setRawPetName(name);
                            }
                            if ((data.containsKey("SavedPets"))
                                    && (data.get("SavedPets") != null)
                                    && (!String.valueOf(data.get("SavedPets")).isEmpty()))
                                owner.updateSavedPets((JSONArray) JSONValue.parseWithException(Base64Wrapper.decodeString(String.valueOf(data.get("SavedPets")))));

                            if ((data.containsKey("NeedsRespawn"))
                                    && (data.get("NeedsRespawn") != null)
                                    && (!String.valueOf(data.get("NeedsRespawn")).isEmpty()))
                                handle(String.valueOf(data.get("NeedsRespawn")));
                        } catch (Exception e) {
                            PetCore.get().debug("Could not retrieve " + p.getName() + "'s Pet data");
                            e.printStackTrace();
                        }
                    }
                }.runTask(PetCore.get());
            });
            return;
        }
        PetCore.get().getPlayerStorage(p, new PetCore.Call<PlayerStorage>() {
            @Override
            public void call(PlayerStorage file) {
                TypeManager manager = PetCore.get().getTypeManager();
                List<PetType> types = new ArrayList<>();
                if (file.getTag("PurchasedPets") instanceof StorageTagList) {
                    // Was saved as StorageTagList
                    StorageTagList stored = (StorageTagList) file.getTag("PurchasedPets");
                    stored.getList().forEach(base -> types.add(manager.getType(((StorageTagString)base).getString())));
                }else{
                    // Was saved in the old format
                    String raw = file.getString("PurchasedPets");
                    String decoded = Base64Wrapper.decodeString(raw);
                    JsonArray array = (JsonArray) Json.parse(decoded);
                    array.values().forEach(jsonValue -> {
                        String name = jsonValue.asString();
                        types.add(manager.getType(name));
                    });
                }

                owner.setRawOwned(types);
                if (file.hasKey("ItemStorage")) {
                    JSONObject storage = file.getJSONObject("ItemStorage");
                    if (!storage.isEmpty()) owner.setStoredInventory(storage, false);
                }
                if (file.hasKey("SavedPets")) {
                    JSONArray storage = file.getJSONArray("SavedPets");
                    if (!storage.isEmpty()) owner.updateSavedPets(storage);
                }
                if (file.hasKey("NeedsRespawn")) {
                    StorageTagCompound compound = file.getCompoundTag("NeedsRespawn");
                    file.remove("NeedsRespawn");
                    file.save();
                    if (compound.hasKey("PetType")) owner.setPetToRespawn(compound);
                }
                owner.setRawPetName(file.getString("PetName"));
            }

            @Override
            public void onFail() {
                owner.setRawOwned(new ArrayList<>());
            }
        });

    }

    private JSONObject parse(String string) {
        if (string.equals("null")) return new JSONObject();
        try {
            return (JSONObject) JSONValue.parseWithException(Base64Wrapper.decodeString(string));
        } catch (ParseException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    private void handle(String json) {
        if ((!json.isEmpty()) && (!json.equals("null"))) {
            StorageTagCompound compound;
            try {
                compound = JsonToNBT.getTagFromJson(Base64Wrapper.decodeString(json));
            } catch (NBTException e) {
                compound = new StorageTagCompound();
                e.printStackTrace();
            }
            owner.setPetToRespawn(compound);
        }
    }
}
