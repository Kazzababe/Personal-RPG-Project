package ravioli.gravioli.rpg.actor;

import net.citizensnpcs.api.npc.NPC;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.dialogue.DialogueBuilder;
import ravioli.gravioli.rpg.dialogue.stage.DialogueOption;
import ravioli.gravioli.rpg.event.ClassSelectEvent;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.quest.Quest002SelectClass;

public class Actor002TutorialGuide extends BaseActor {
    private static final Dialogue MAIN_DIALOGUE;

    static {
        MAIN_DIALOGUE = new DialogueBuilder()
                .withPrefix("Jeremy")
                .message("Ahh, another kid who thinks they can come to our great city and become a hero. Well, I can't say I have high hopes by the looks a you, but I'll at the very least give your first quest.")
                .message("Go talk to that old man in the building over on the left there and select your class.", (player, dialogue) -> {
                    player.startQuest(new Quest002SelectClass(player));
                })
                .getDialogue();
    }

    public Actor002TutorialGuide() {
        super(1);
        this.entity.data().set(NPC.PLAYER_SKIN_TEXTURE_PROPERTIES_METADATA, "eyJ0aW1lc3RhbXAiOjE0NzkyMTAwMDI5NzgsInByb2ZpbGVJZCI6ImRhNzQ2NWVkMjljYjRkZTA5MzRkOTIwMTc0NDkxMzU1IiwicHJvZmlsZU5hbWUiOiJJc2F5bGEiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzllYmIyMTA3MmFkYmRiNGIwNDcxODIwYzc3ZWE1NTM1YjkxOWUyZGE0ODdiNmU1NmM2ZmE3MmRiZWQ0YTYifX19");
        this.entity.data().set(NPC.PLAYER_SKIN_TEXTURE_PROPERTIES_SIGN_METADATA, "wIENy5A/vuIKNS86xptwW+OGf0E/id8AEIqkeAxs2ir2GWh7dY0I8vqLqSI/pZwP+9YyXN8G6kV3Xceuc2upr9OclO52HtE9BDg+WxYxVc0wQPAysnGP5o4G48a/58HGmsR6B6TziMGmU6FuAmWkmVaZR78jpAgpK+G2HTwm6ioie2BYZK+C4mh+kK5D8MwSmKUJ53S5JM/oJ1yYJTVh5pc9VFY+Pp97/LPQ2wBgg83TS1nFxRUebQTHqabJJAmvOj2CDLKySCiGd7s3DWdmqLxZ2IAhPeusYEQsEXXGVv21NRU3rYLSNQO8JWZRZ/wVOghPHzkynjle+uFb4a6uol8fs4FoXtAvlz6rhJLxgfLmqLlFjfz9uQUaeTeaBVF/hWNPA/B3SfxvRufhF3OW2bA5W0AvgrF5mao0DRyUbZ3D1UO/HheD4RoXX12kOkmAWQAhUMCcwDmmsoV4vKVoW1vq5+9TjJCrFLJ9ppcKuj0YjhbUA8UNtX9qhlpXQ50HB7rSrlszZpQi+vLXp2e/dP/r6zsCJdmparJzG5dClJ0nG2DP+sIlWnAr5TCL2FN6cgxZzIxa3QTniAUsTaA9Os2QfzAHQrcTPvpEY15R3tS+GTh1x91gikeOSn9u5fvXBZd8MpfkHksS/St/MD7kuHP9ka71ZZI6Ih/4J2zUE7s=");
    }

    @Override
    public String getName() {
        return "Jeremy";
    }

    @Override
    public void onInteract(RPGPlayer player) {
        if (!player.hasCompletedQuest(1)) {
            if (player.isQuestActive(1)) {
                // Say something about the quest
                player.sendMessage("Go select your class dummy");
            } else {
                player.startDialogue(MAIN_DIALOGUE);
            }
        } else {
            // Say some random nonsense
            player.sendMessage("Weather's looking nice tonight, isn't it?");
        }
    }
}
