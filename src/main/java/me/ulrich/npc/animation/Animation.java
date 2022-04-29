package me.ulrich.npc.animation;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class Animation {

    public static final Animation CIRCLE = new CircleAnimation();

    protected ProtocolManager protocolManager;
    protected int entityID;

    public abstract long delay();

    public abstract void nextFrame(@NotNull Player player);

    public abstract boolean async();

    public abstract Animation clone();

    public void setProtocolManager(@NotNull ProtocolManager protocolManager) {
        this.protocolManager = protocolManager;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }
}
