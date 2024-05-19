package eu.camonetwork.camofreeze;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FreezeManager implements Listener {
    private final Set<UUID> frozenPlayers = new HashSet<>();

    public void freezePlayer(@NotNull Player player) {
        UUID playerId = player.getUniqueId();
        if (frozenPlayers.contains(playerId)) {
            return;
        }

        frozenPlayers.add(playerId);

        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
        player.setInvulnerable(true);
    }

    public void unfreezePlayer(@NotNull Player player) {
        UUID playerId = player.getUniqueId();
        if (!frozenPlayers.contains(playerId)) {
            return;
        }

        frozenPlayers.remove(playerId);

        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.setInvulnerable(false);
    }

    public boolean isPlayerFrozen(@NotNull Player player) {
        return frozenPlayers.contains(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (isPlayerFrozen(player)) {
            if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
                event.setTo(event.getFrom());
            }
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isPlayerFrozen(player)) {
                event.setCancelled(true);
                if (event.getDamager() instanceof Player) {
                    Player damager = (Player) event.getDamager();
                    damager.sendMessage(ChatColor.AQUA + "Deze speler is gefreezed");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (isPlayerFrozen(player)) {
            unfreezePlayer(player);
        }
    }
}
