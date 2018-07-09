package me.glaremasters.wgrcc;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.glaremasters.wgrcc.events.Teleport;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class WGRCC extends JavaPlugin {

    private static WGRCC i;

    public static WGRCC getI() {
        return i;
    }

    @Override
    public void onEnable() {
        i = this;
        getServer().getPluginManager().registerEvents(new Teleport(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }
}
