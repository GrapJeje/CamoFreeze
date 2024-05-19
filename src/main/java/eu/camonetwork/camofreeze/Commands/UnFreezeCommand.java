package eu.camonetwork.camofreeze.Commands;

import eu.camonetwork.camofreeze.FreezeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnFreezeCommand implements CommandExecutor {

    private final FreezeManager freezeManager;

    public UnFreezeCommand(FreezeManager freezeManager) {
        this.freezeManager = freezeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("camo.unfreezeplayer")) {
            sender.sendMessage(ChatColor.RED + "Je hebt geen toegang tot dit commando");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Gebruik: /unfreeze <speler>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Speler niet gevonden");
            return true;
        }

        if (!freezeManager.isPlayerFrozen(target)) {
            sender.sendMessage(ChatColor.AQUA + "Speler " + target.getName() + " is niet bevroren.");
        } else {
            freezeManager.unfreezePlayer(target);
            sender.sendMessage(ChatColor.AQUA + "Speler " + target.getName() + " is niet meer bevroren.");
            target.sendMessage(ChatColor.AQUA + "Je bent niet meer bevroren.");
        }

        return true;
    }
}
