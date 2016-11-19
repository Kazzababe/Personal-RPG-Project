package ravioli.gravioli.rpg.actor;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.dialogue.DialogueBuilder;
import ravioli.gravioli.rpg.dialogue.stage.DialogueOption;
import ravioli.gravioli.rpg.event.ClassSelectEvent;
import ravioli.gravioli.rpg.gui.Container;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.player.profession.BaseClass;
import ravioli.gravioli.rpg.quest.Quest002SelectClass;
import ravioli.gravioli.rpg.util.ItemBuilder;

public class Actor001ClassSelection extends BaseActor {
    private static final Dialogue CLASS_SELECTION_DIALOGUE;
    private static final Container CLASS_SELECTION_GUI;

    static {
        CLASS_SELECTION_GUI = new Container(ChatColor.GREEN + "Select your class!", BaseClass.classes.size());
        for (Integer i = 0; i < BaseClass.classes.size(); i++) {
            BaseClass kit = BaseClass.classes.get(i);
            CLASS_SELECTION_GUI.setItem(i, kit.getItemBuilder(), (player, container, item) -> {
                player.setPlayerClass(kit);
                container.close(player);
                Bukkit.getPluginManager().callEvent(new ClassSelectEvent(kit, player));
            });
        }

        CLASS_SELECTION_DIALOGUE = new DialogueBuilder()
            .withPrefix("Class Master")
            .multipleChoice("Have you come to select your class? We've got a few choices so take your time!",
                    new DialogueOption("Open class selector", (player, dialogue) -> {
                        CLASS_SELECTION_GUI.open(player);
                        dialogue.end(player);
                    }))
            .getDialogue();
    }

    public Actor001ClassSelection() {
        super(0);
    }

    @Override
    public String getName() {
        return "Karaden";
    }

    @Override
    public void onInteract(RPGPlayer player) {
        if (player.getPlayerClass() == null) {
            if (!player.isQuestActive(1)) {
                player.startQuest(new Quest002SelectClass(player));
                player.sendMessage("START2");
            }
            player.startDialogue(CLASS_SELECTION_DIALOGUE);
        } else {
            // You already picked a class, can't change it
            player.sendMessage("Can't change your class fucko");
        }
    }
}
