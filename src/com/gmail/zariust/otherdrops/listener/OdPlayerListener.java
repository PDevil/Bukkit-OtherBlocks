// OtherDrops - a Bukkit plugin
// Copyright (C) 2011 Robert Sargant, Zarius Tularial, Celtic Minstrel
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	 See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.	 If not, see <http://www.gnu.org/licenses/>.

package com.gmail.zariust.otherdrops.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.zariust.common.Verbosity;
import com.gmail.zariust.otherdrops.Log;
import com.gmail.zariust.otherdrops.OtherDrops;
import com.gmail.zariust.otherdrops.event.OccurredEvent;

import java.util.Set;

public class OdPlayerListener implements Listener {
    private final OtherDrops parent;

    public OdPlayerListener(OtherDrops instance) {
        parent = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Deliberately processing cancelled events as a click into air
        // is always "cancelled" and we want to catch that event
        if (event.isCancelled() && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Log.logInfo("Cancelled event but not AIR - skipping.", Verbosity.HIGHEST);
            return;
        }
        
        if (event.getPlayer() != null) {
//            if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                // skip for creative mode - TODO: make this configurable?
//            } else {
                Block targetBlock = null;
                if (event.getClickedBlock() == null) {
                    try {
                        targetBlock = event.getPlayer().getTargetBlock((Set<Material>) null, 200);
                    } catch (Exception ex) {
                        // no need to do anything here
                    }
                    if (targetBlock == null)
                        targetBlock = event.getPlayer().getLocation()
                                .getBlock();
                } else {
                    targetBlock = event.getClickedBlock();
                }

                OccurredEvent drop = new OccurredEvent(event, targetBlock);
            parent.sectionManager.performDrop(drop);
 //           }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.isCancelled())
            return;
        if (event.getPlayer() != null)
            if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                // skip drops for creative mode - TODO: make this configurable?
            } else {
                OccurredEvent drop = new OccurredEvent(event);
                parent.sectionManager.performDrop(drop);
            }
    }
}
