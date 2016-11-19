package ravioli.gravioli.rpg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ravioli.gravioli.rpg.actor.BaseActor;
import ravioli.gravioli.rpg.command.BaseCommand;
import ravioli.gravioli.rpg.command.DialogueCommand;
import ravioli.gravioli.rpg.command.TestCommand;
import ravioli.gravioli.rpg.listener.PlayerListeners;
import ravioli.gravioli.rpg.listener.ServerListeners;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.quest.QuestManager;
import ravioli.gravioli.rpg.util.WorldUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class RPG extends JavaPlugin {
    private static HashMap<UUID, RPGPlayer> players = new HashMap();
    private static RPG plugin;

    private QuestManager questManager;

    @Override
    public void onLoad() {
        plugin = this;

        Iterator<File> files = new ArrayList<File>(Arrays.asList(this.getServer().getWorldContainer().listFiles())).iterator();
        while (files.hasNext()) {
            File file = files.next();
            if (file.isDirectory()) {
                if (file.getName().contains("_dungeon_")) {
                    WorldUtil.deleteWorld(file);
                }
            }
        }
    }

    @Override
    public void onEnable() {
        this.questManager = new QuestManager();

        this.registerListener(new ServerListeners());
        this.registerListener(new PlayerListeners());

        this.registerCommand(new DialogueCommand());
        this.registerCommand(new TestCommand());

        World world = this.getServer().getWorld("world");
        BaseActor.CLASS_SELECTION.spawn(new Location(world, 156, 66, 145, 180, 0));
        BaseActor.TUTORIAL_GUIDE.spawn(new Location(world, 152, 66, 111, 46, 0));

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            //System.out.println("INTERVAL");
        }, 5L, 5L);
    }

    @Override
    public void onDisable() {

    }

    public static RPG getPlugin() {
        return plugin;
    }

    /**
     * Register all events in all the specified listener classes
     *
     * @param listeners Listeners to register
     */
    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * Dynamically register a command
     *
     * @param command The command to register
     */
    public void registerCommand(BaseCommand command) {
        CommandMap commandMap = null;
        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(this.getServer().getPluginManager());
            commandMap.register(this.getName(), command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static RPGPlayer getPlayer(UUID uniqueId) {
        return players.get(uniqueId);
    }

    public static RPGPlayer getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static RPGPlayer addPlayer(UUID uniqueId) {
        RPGPlayer player = new RPGPlayer(uniqueId);
        players.put(uniqueId, player);
        return player;
    }

    public static void removePlayer(UUID uniqueId) {
        players.remove(uniqueId);
    }
}
