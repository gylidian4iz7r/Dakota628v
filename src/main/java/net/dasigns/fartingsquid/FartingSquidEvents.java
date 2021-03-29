package net.dasigns.fartingsquid;

import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class FartingSquidEvents implements Listener {
    private ArrayList<EntityDamageEvent.DamageCause> getSquidImmunites() {
        ArrayList<EntityDamageEvent.DamageCause> si = new ArrayList<EntityDamageEvent.DamageCause>();
        si.add(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
        si.add(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
        si.add(EntityDamageEvent.DamageCause.FIRE);
        si.add(EntityDamageEvent.DamageCause.FIRE_TICK);
        si.add(EntityDamageEvent.DamageCause.SUFFOCATION);
        si.add(EntityDamageEvent.DamageCause.SUICIDE);
        si.add(EntityDamageEvent.DamageCause.CONTACT);
        si.add(EntityDamageEvent.DamageCause.LAVA);
        return si;
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
}
