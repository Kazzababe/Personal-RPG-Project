package ravioli.gravioli.rpg.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.util.CommonUtil;

public class DialogueCommand extends BaseCommand {
    public DialogueCommand() {
        super("dialogue");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        RPGPlayer player = RPG.getPlayer((Player) sender);

        if (args.length == 4) {
            if (args[0].equals("next")) {
                if (CommonUtil.isInteger(args[1]) && CommonUtil.isInteger(args[2]) && CommonUtil.isInteger(args[3])) {
                    if (player.isDialogueOpen()) {
                        Dialogue dialogue = player.getDialogue();
                        if (Long.parseLong(args[3]) == dialogue.getStartTime()) {
                            dialogue.onChatClick(player, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                        }
                    }
                }
            }
        } else if (args.length == 1) {
            if (args[0].equals("end")) {
                if (player.isDialogueOpen()) {
                    player.stopDialogue();
                }
            }
        }

        return true;
    }
}
