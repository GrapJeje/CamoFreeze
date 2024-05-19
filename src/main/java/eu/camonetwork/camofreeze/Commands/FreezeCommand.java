package eu.camonetwork.camofreeze.Commands;

import eu.camonetwork.camofreeze.FreezeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FreezeCommand implements CommandExecutor {

    private final FreezeManager freezeManager;

    public FreezeCommand(FreezeManager freezeManager) {
        this.freezeManager = freezeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("camo.freezeplayer")) {
            sender.sendMessage(ChatColor.RED + "Je hebt geen toegang tot dit commando");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Gebruik: /freeze <speler>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Speler niet gevonden");
            return true;
        }

        if (freezeManager.isPlayerFrozen(target)) {
            sender.sendMessage(ChatColor.AQUA + "Speler " + target.getName() + " is al bevroren.");
        } else {
            freezeManager.freezePlayer(target);
            sender.sendMessage(ChatColor.AQUA + "Speler " + target.getName() + " is nu bevroren.");
            target.sendMessage(ChatColor.AQUA + "Je bent nu bevroren.");
        }

        return true;
    }
}
