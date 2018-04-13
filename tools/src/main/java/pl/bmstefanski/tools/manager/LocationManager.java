package pl.bmstefanski.tools.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LocationManager {

    private static final Map<Player, Location> LOCATION_MAP = new HashMap<>();

    public static Location getLastLocation(Player player) {
        if (LOCATION_MAP.get(player) == null) {
            return player.getLocation();
        }

        return LOCATION_MAP.get(player);
    }

    public static void setLastLocation(Player player) {
        LOCATION_MAP.put(player, player.getLocation());
    }

    private LocationManager() {}

}
