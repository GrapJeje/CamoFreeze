package eu.camonetwork.camofreeze;

import eu.camonetwork.camofreeze.Commands.FreezeCommand;
import eu.camonetwork.camofreeze.Commands.UnFreezeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class CamoFreeze extends JavaPlugin {

    public static CamoFreeze instance;

    @Override
    public void onEnable() {
        instance = this;

        // FreezeManager
        FreezeManager freezeManager = new FreezeManager();
        getServer().getPluginManager().registerEvents(freezeManager, this);

        // Freeze Commands
        this.getCommand("freeze").setExecutor(new FreezeCommand(freezeManager));
        this.getCommand("unfreeze").setExecutor(new UnFreezeCommand(freezeManager));

        getLogger().info("---CamoFreeze loaded---");
    }

    @Override
    public void onDisable() {
        getLogger().info("---CamoFreeze unloaded---");
    }
}
