package ravioli.gravioli.rpg.world.instance;

import java.util.ArrayList;

public class InstanceManager {
    private static ArrayList<Instance> instances = new ArrayList();

    public static void addInstance(Instance instance) {
        instances.add(instance);
    }

    public static void removeInstance(Instance instance) {
        instances.remove(instance);
    }
}
