package me.ulrich.npc.events;

import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.*;

import me.ulrich.npc.EntityData;

public abstract class PlayerHologramEvent extends PlayerEvent {

    private final EntityData hologram;

    public PlayerHologramEvent(@NotNull Player player, @NotNull EntityData hologram) {
        super(player);
        this.hologram = hologram;
    }

    @NotNull
    public EntityData getHologram() {
        return hologram;
    }

}
