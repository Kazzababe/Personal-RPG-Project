package ravioli.gravioli.rpg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public abstract class BaseCommand extends Command {
    public BaseCommand(String name, String... aliases) {
        super(name);
        this.setAliases(Arrays.asList(aliases));
    }
    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return this.onCommand(commandSender, strings);
    }

    public abstract boolean onCommand(CommandSender sender, String[] args);
}