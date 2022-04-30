package me.ulrich.npc.events;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import me.ulrich.npc.EntityData;

public class PlayerHologramShowEvent extends PlayerHologramEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public PlayerHologramShowEvent(@NotNull Player player, @NotNull EntityData hologram) {
        super(player, hologram);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
