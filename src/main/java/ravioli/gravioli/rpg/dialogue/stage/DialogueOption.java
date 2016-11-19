package ravioli.gravioli.rpg.dialogue.stage;

import ravioli.gravioli.rpg.dialogue.DialogueAction;

public class DialogueOption {
    protected String message;
    protected DialogueAction action;

    public DialogueOption(String message, DialogueAction action) {
        this.message = message;
        this.action = action;
    }
}
