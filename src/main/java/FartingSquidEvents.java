import org.bukkit.entity.Entity;
import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.util.ArrayList;
import java.util.logging.Level;

public class FartingSquidEvents implements Listener {
    private ArrayList<EntityDamageEvent.DamageCause> getSquidImmunites() {
        ArrayList<EntityDamageEvent.DamageCause> si = new ArrayList<EntityDamageEvent.DamageCause>();
        si.add(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
        si.add(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
        si.add(EntityDamageEvent.DamageCause.FIRE);
        si.add(EntityDamageEvent.DamageCause.FIRE_TICK);
        si.add(EntityDamageEvent.DamageCause.SUFFOCATION);
        si.add(EntityDamageEvent.DamageCause.SUICIDE);
        return si;
    }

    private boolean isFartingSquid(Entity e) {
        if(!(e instanceof Squid)) return false;
        return FartingSquid.getInstance().isFartingSquid((Squid) e);
    }

    @EventHandler //Don't take damage from explosions, fire, suicide or suffocation if squid or rider
    public void onEntityDamage(EntityDamageEvent e) {
        //Make rider immune
        if(e.getEntity().getVehicle() instanceof Squid) {
            Squid s = (Squid) e.getEntity().getVehicle();
            if(FartingSquid.getInstance().isFartingSquid(s) && getSquidImmunites().contains(e.getCause())) {
                e.setCancelled(true);
            }
        }

        //Make squid immune
        if(e.getEntity() instanceof Squid) {
            Squid s = (Squid) e.getEntity();
            if(FartingSquid.getInstance().isFartingSquid(s) && getSquidImmunites().contains(e.getCause())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void vehicleEnterEvent(VehicleEnterEvent e) {
        if(isFartingSquid(e.getVehicle())) {
            FartingSquid.getInstance().getServer().getLogger().log(Level.INFO, "Now riding farting squid");
            e.getVehicle().getWorld().createExplosion(e.getVehicle().getLocation(),10);
        }
    }
}
