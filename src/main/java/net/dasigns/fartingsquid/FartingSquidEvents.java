/*
    Copyright 2013 Dakota Baber (Dakota628)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

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
        if (e.getEntity().getVehicle() instanceof Squid) {
            Squid s = (Squid) e.getEntity().getVehicle();
            if (FartingSquid.getInstance().isFartingSquid(s) && getSquidImmunites().contains(e.getCause())) {
                e.setCancelled(true);
            }
        }

        //Make squid immune
        if (e.getEntity() instanceof Squid) {
            Squid s = (Squid) e.getEntity();
            if (FartingSquid.getInstance().isFartingSquid(s) && getSquidImmunites().contains(e.getCause())) {
                e.setCancelled(true);
            }
        }
    }
}
