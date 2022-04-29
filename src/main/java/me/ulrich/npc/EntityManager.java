package me.ulrich.npc;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class EntityManager implements Listener {

    private final JavaPlugin plugin;
    private final double spawnDistance;

    private  Collection<EntityData> holograms = new CopyOnWriteArraySet<>();

    public EntityManager(@NotNull JavaPlugin plugin, double spawnDistance) {
        Validate.notNull(plugin, "Plugin cannot be null");
        
        this.plugin = plugin;
        this.spawnDistance = spawnDistance*spawnDistance;

        Bukkit.getPluginManager().registerEvents(this, plugin);

        hologramTick();
    }

    public void removeAll() {
    	holograms.stream().forEach(e->{
    		remove(e);
    	});
    }
    
    @EventHandler
    public void handleRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        holograms.stream()
                .filter(h->h.isShownFor(player))
                .forEach(h->h.hide(player));
    }

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        holograms.stream()
                .filter(h->h.isShownFor(player))
                .forEach(h->h.hide(player));
    }

    protected @NotNull JavaPlugin getPlugin() {
        return plugin;
    }

    protected void takeCareOf(@NotNull EntityData hologram) {
        this.holograms.add(hologram);
    }

    protected void hologramTick() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, () -> {
        	        	
            for (Player player : ImmutableList.copyOf(Bukkit.getOnlinePlayers())) {
                for (EntityData hologram : this.holograms) {
                    Location holoLoc = hologram.getLocation();
                    Location playerLoc = player.getLocation();
                    boolean isShown = hologram.isShownFor(player);

                    if (!holoLoc.getWorld().equals(playerLoc.getWorld()) && isShown) {
                        hologram.hide(player);
                        continue;
                    } else if (!holoLoc.getWorld().isChunkLoaded(holoLoc.getBlockX() >> 4, holoLoc.getBlockZ() >> 4) && isShown) {
                        hologram.hide(player);
                        continue;
                    }
                    if(holoLoc.getWorld().equals(playerLoc.getWorld())) {
                    	boolean inRange = holoLoc.distanceSquared(playerLoc) <= this.spawnDistance;

                        if (!inRange && isShown) {
                            hologram.hide(player);
                        } else if (inRange && !isShown) {
                            hologram.show(player);
                        }
                    }
                    
                }
            }
        }, 20L, 10L);
    }

    public void remove(@NotNull EntityData hologram) {
        Validate.notNull(hologram, "Npc to remove cannot be null");
        if(this.holograms.contains(hologram)) {
            this.holograms.remove(hologram);
            hologram.getSeeingPlayers()
                    .forEach(hologram::hide);
        }
    }
    
    public void respawn(@NotNull EntityData hologram) {
        Validate.notNull(hologram, "Npc to remove cannot be null");
        if(this.holograms.contains(hologram)) {
        
            hologram.getSeeingPlayers().forEach(hologram::hide);
            hologram.getSeeingPlayers().forEach(hologram::show);
        }
    }
    
	public Collection<EntityData> getHolograms() {
		return holograms;
	}

	public void setHolograms(Collection<EntityData> holograms) {
		this.holograms = holograms;
	}

    
}
