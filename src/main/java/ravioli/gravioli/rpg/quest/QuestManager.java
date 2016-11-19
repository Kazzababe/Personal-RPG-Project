package ravioli.gravioli.rpg.quest;

import ravioli.gravioli.rpg.player.RPGPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class QuestManager {
    private static HashMap<Integer, Quest> allQuests = new HashMap();

    public static void registerQuest(Class<? extends Quest> questClass) {
        try {
            Quest quest = questClass.getConstructor(RPGPlayer.class).newInstance(new Object[] {null});
            allQuests.put(quest.getId(), quest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QuestManager() {
        registerQuest(Quest001PigKiller.class);
    }

    public static Class<? extends Quest> getQuestById(int id) {
        return allQuests.get(id).getClass();
    }

    public static Quest getNewQuestById(RPGPlayer player, int id) {
        try {
            return getQuestById(id).getConstructor(RPGPlayer.class).newInstance(new Object[] {player});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}