package ravioli.gravioli.rpg.dialogue;

import ravioli.gravioli.rpg.dialogue.stage.DialogueOption;
import ravioli.gravioli.rpg.dialogue.stage.DialogueStageItemReward;
import ravioli.gravioli.rpg.dialogue.stage.DialogueStageMultipleChoice;
import ravioli.gravioli.rpg.dialogue.stage.DialogueStageSingleMessage;
import ravioli.gravioli.rpg.item.CustomItem;
import ravioli.gravioli.rpg.util.ItemBuilder;

import java.util.Arrays;

public class DialogueBuilder {
    private Dialogue dialogue;

    public DialogueBuilder() {
        this.dialogue = new Dialogue();
    }

    public DialogueBuilder message(String message) {
        this.dialogue.stages.add(new DialogueStageSingleMessage(this.dialogue, message));
        return this;
    }

    public DialogueBuilder message(String message, DialogueAction action) {
        this.dialogue.stages.add(new DialogueStageSingleMessage(this.dialogue, message, action));
        return this;
    }

    public DialogueBuilder multipleChoice(String message, DialogueOption... options) {
        DialogueStageMultipleChoice stage = new DialogueStageMultipleChoice(this.dialogue, message);
        stage.getOptions().addAll(Arrays.asList(options));
        this.dialogue.stages.add(stage);
        return this;
    }

    public DialogueBuilder itemReward(String message, boolean end, CustomItem... items) {
        DialogueStageItemReward stage = new DialogueStageItemReward(this.dialogue, message, end);
        stage.getItems().addAll(Arrays.asList(items));
        this.dialogue.stages.add(stage);
        return this;
    }

    public DialogueBuilder withPrefix(String prefix) {
        this.dialogue.prefix = prefix;
        return this;
    }

    public Dialogue getDialogue() {
        return this.dialogue;
    }
}
