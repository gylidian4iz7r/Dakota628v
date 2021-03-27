package net.dasigns.fartingsquid;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class FartingSquid extends JavaPlugin {
    private static FartingSquid instance;

    private ArrayList<Squid> fartingSquids = new ArrayList<Squid>();

    @Override
    public void onEnable() {
        //Set our instance
        instance = this;

        //Setup the command listeners
        getCommand("fartingsquid").setExecutor(new FartingSquidCommands(this));

        //Setup event listener
        getServer().getPluginManager().registerEvents(new FartingSquidEvents(),this);

        this.getLogger().info("FartingSquid Enabled");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("FartingSquid Disabled");
    }

    //Spawn a squid at the players location and mount the player on it
    public void spawnSquid(Player p) {
        Location l = p.getLocation();
        Squid s = (Squid) l.getWorld().spawnEntity(l, EntityType.SQUID);
        addFartingSquid(s);
        s.setMaxHealth(200);
        s.setHealth(200);
        s.setPassenger(p);
    }

    public static FartingSquid getInstance() {
        return instance;
    }

    public ArrayList<Squid> getFartingSquids() {
        return fartingSquids;
    }

    public void addFartingSquid(Squid s) {
        this.fartingSquids.add(s);
    }

    public void removeFartingSquid(Squid s) {
        this.fartingSquids.remove(s);
    }

    public boolean isFartingSquid(Squid s) {
        return this.fartingSquids.contains(s);
    }
}
