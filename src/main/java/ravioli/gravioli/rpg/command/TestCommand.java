package ravioli.gravioli.rpg.command;

import net.citizensnpcs.api.CitizensAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.dialogue.DialogueBuilder;
import ravioli.gravioli.rpg.dialogue.stage.DialogueOption;
import ravioli.gravioli.rpg.item.CustomItem;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.quest.Quest;
import ravioli.gravioli.rpg.quest.QuestManager;
import ravioli.gravioli.rpg.util.CommonUtil;
import ravioli.gravioli.rpg.util.ItemBuilder;
import ravioli.gravioli.rpg.world.instance.Instance;
import ravioli.gravioli.rpg.world.instance.util.InstanceUtil;

import java.io.File;

public class TestCommand extends BaseCommand {
    private static final Dialogue TEST_DIALOGUE = new DialogueBuilder()
            .multipleChoice("What would you look to do today, you fat mother fucker?",
                    new DialogueOption("Eat ass", (player, dialogue) -> {
                        dialogue.skipTo(player, 2);
                    }),
                    new DialogueOption("Give me the good kush", (player, dialogue) -> {
                        dialogue.skipTo(player, 1);
                    }))
            .itemReward("Here you go you ungrateful monkey",
                    true,
                    new CustomItem(
                            new ItemBuilder(Material.DIAMOND_SWORD)
                                    .setName(ChatColor.RED + "Flesh Tearer")
                    )
            )
            .message("Eat my ass ron jeremy")
            .getDialogue();

    public TestCommand() {
        super("test");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        RPGPlayer player = RPG.getPlayer((Player) sender);

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("sd")) {
                player.startDialogue(TEST_DIALOGUE);
            } else if (args[0].equalsIgnoreCase("name")) {
                for (Entity entity : player.getWorld().getNearbyEntities(player.getPlayer().getLocation(), 3, 3, 3)) {
                    if (CitizensAPI.getNPCRegistry().isNPC(entity)) {

                        break;
                    }
                }
            } else if (args[0].equalsIgnoreCase("ei")) {
                if (player.getInstance() != null) {
                    player.getInstance().destroy();
                }
            } else if (args[0].equalsIgnoreCase("ci")) {
                player.getPlayer().getInventory().addItem(new CustomItemStack(Material.GOLD_AXE, 1, (short) 0));
            } else if (args[0].equalsIgnoreCase("ct")) {
                player.sendMessage("CustomItemStack " + (player.getPlayer().getInventory().getItemInMainHand() instanceof CustomItemStack));
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("sq")) {
                if (CommonUtil.isInteger(args[1])) {
                    Quest quest = QuestManager.getNewQuestById(player, 0);
                    if (quest != null) {
                        player.startQuest(quest);
                    }
                }
            } else if (args[0].equalsIgnoreCase("tp")) {
                if (Bukkit.getWorld(args[1]) != null) {
                    player.getPlayer().teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                }
            } else if (args[0].equalsIgnoreCase("ci")) {
                Instance instance = new Instance(new File(InstanceUtil.getInstanceWorldContainer(), "lol"), "testing");
                instance.addPlayer(player);
                instance.create();

                player.getPlayer().teleport(instance.getWorld().getSpawnLocation());
                player.setInstance(instance);
            }
        }

        return true;
    }
}
