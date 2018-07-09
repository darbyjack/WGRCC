package me.glaremasters.wgrcc.events;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.glaremasters.wgrcc.WGRCC;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.Set;

public class Teleport implements Listener {

    private HashSet<String> list = new HashSet<>();

    private WGRCC i;

    public Teleport(WGRCC i) {
        this.i = i;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage();
        if (command.contains("back")) {
            list.add(player.getName());
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        try {
            for (ProtectedRegion r : i.getWorldGuard().getRegionManager(to.getWorld()).getApplicableRegions(to)) {
                if (list.contains(player.getName())) {
                    Set<String> commands = r.getFlag(DefaultFlag.BLOCKED_CMDS);
                    try {
                        if (commands.contains("back")) {
                            event.setCancelled(true);
                        }
                    } catch (NullPointerException e) {
                        return;
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
