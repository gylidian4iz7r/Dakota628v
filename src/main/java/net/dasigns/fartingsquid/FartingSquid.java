package net.dasigns.fartingsquid;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

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
        getServer().getPluginManager().registerEvents(new FartingSquidEvents(), this);

        this.getLogger().info("FartingSquid Enabled");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("FartingSquid Disabled");
    }

    //Spawn a squid at the players location and mount the player on it
    public void spawnSquid(final Player p) {
        Location l = p.getLocation();
        final Squid s = (Squid) l.getWorld().spawnEntity(l, EntityType.SQUID);
        addFartingSquid(s);
        s.setMaxHealth(200);
        s.setHealth(200);
        s.setMaximumAir(Integer.MAX_VALUE);
        s.setRemainingAir(Integer.MAX_VALUE);
        s.setPassenger(p);

        Runnable squidMove = new Runnable() {
            public void run() {
                if (s == null || s.getPassenger() == null || !(s.getPassenger() instanceof Player)) {
                    if(getFartingSquids().contains(s)) removeFartingSquid(s);
                    return;
                }

                //Explode blocks in our way
                int blocksUp = 0;
                Block target = s.getLocation().add(p.getLocation().getDirection()).getBlock();
                while (blocksUp < 3) {
                    if (target != null && target.getType() != Material.AIR) {

                        s.getWorld().createExplosion(
                                s.getLocation().getX(),
                                s.getLocation().getY(),
                                s.getLocation().getZ(),
                                4,
                                true,
                                true
                        );

                        break;
                    }
                    target = target.getRelative(BlockFace.UP);
                    blocksUp++;
                }

                //Clear ground items near us
                for(Item i : s.getWorld().getEntitiesByClass(Item.class)) {
                    if(i.getLocation().distance(s.getLocation()) <= 25) {
                        i.remove();
                    }
                }

                //Light fire to the block behind us...but there is probably nothing there
                Block fire = s.getLocation().subtract(p.getLocation().getDirection()).getBlock();
                fire.getRelative(BlockFace.UP).setType(Material.FIRE);

                //Face the same direction as me squid!!
                s.getLocation().setYaw(p.getLocation().getYaw());

                //Continue moving
                Vector velocity = p.getLocation().getDirection();
                velocity.setY(0);
                s.setVelocity(velocity);
            }
        };
        getServer().getScheduler().runTaskTimer(this, squidMove, 1, 1);
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
