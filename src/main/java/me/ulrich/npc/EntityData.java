package me.ulrich.npc;

import me.ulrich.npc.animation.*;
import me.ulrich.npc.line.AbstractLine;
import me.ulrich.npc.line.TextLine;
import me.ulrich.npc.data.EntityEnum;
import me.ulrich.npc.data.EntityEquipData;
import me.ulrich.npc.data.EntityPoseData;
import me.ulrich.npc.data.StandEnum.AgeType;
import me.ulrich.npc.events.PlayerHologramHideEvent;
import me.ulrich.npc.events.PlayerHologramShowEvent;

import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.*;

public class EntityData {
    private final JavaPlugin plugin;
    private final Location location;

    protected final AbstractLine<?>[] lines;
    private final Collection<Player> seeingPlayers;
	private final String uuid;
	private final Object type;
	private final int entId;
	private final EntityEquipData equipData;
	private final String name;
	private EntityPoseData poseData;
	private EntityEnum entityType;
	private AgeType age;

    public EntityData(@NotNull JavaPlugin plugin, @NotNull Location location, @NotNull Collection<Player> seeingPlayers, @NotNull String uuid, Object type, @NotNull Object lines, EntityEquipData equipData, EntityPoseData poseData, EntityEnum entityType, AgeType age) {
        this.plugin = plugin;
        this.location = location;
        this.seeingPlayers = seeingPlayers;
        this.lines = new AbstractLine[1];
        this.uuid = uuid;
        this.type = type;
        this.equipData = equipData;
        this.poseData = poseData;
        this.entityType = entityType;
        this.age = age;

        final ThreadLocalRandom random = ThreadLocalRandom.current();
        Location cloned = this.location.clone().subtract(0, 0.28, 0);

        AbstractLine<?> tempLine;
        double up = 0.30D;
        this.entId = random.nextInt();
        this.name = (String) lines;
                
        if(lines instanceof String) {
            tempLine = new TextLine(this.seeingPlayers, plugin, this.entId, (String) lines, equipData, poseData, entityType, age);
            tempLine.setLocation(cloned.add(0.0, up, 0).clone());
            this.lines[0] = tempLine;
        }
		
    }

    public void setPose(EntityPoseData pose) {
        getLine(0).setPoseData(pose);
        this.seeingPlayers.forEach(getLine(0)::update);
    }
    
    public void setAge(AgeType age) {
    	getLine(0).setAge(age);
    	this.seeingPlayers.forEach(getLine(0)::respawn);
    }
    
    public void setType(EntityEnum entityType) {
    	getLine(0).setEntityType(entityType);
    	this.seeingPlayers.forEach(getLine(0)::respawn);
    }
    
    public void setEquip(EntityEquipData data) {
        getLine(0).setEquipData(data);
        this.seeingPlayers.forEach(getLine(0)::update);
    }

    public void setLocation(Location loc) {
        getLine(0).setLocation(loc);
        this.seeingPlayers.forEach(getLine(0)::update);
    }
    
    public void setName(@NotNull String text) {
        Validate.notNull(text, "New line cannot be null");
        AbstractLine<String> line = (TextLine) getLine(0);
        line.set(text);
        this.seeingPlayers.forEach(line::show);
    }
    
    public void setLine(int index, @NotNull String text) {
        Validate.notNull(text, "New line cannot be null");
        AbstractLine<String> line = (TextLine) getLine(index);
        line.set(text);
        this.seeingPlayers.forEach(line::update);
    }

    public void setAnimation(int index, @NotNull Animation animationType) {
        Validate.notNull(animationType, "AnimationType cannot be null");
        getLine(index).setAnimation(animationType.clone());
    }

    public void removeAnimation(int index) {
        getLine(index).removeAnimation();
    }

    public Location getLocation() {
        return location;
    }

    public String getUUID() {
        return uuid;
    }
    
    protected void show(@NotNull Player player) {
        this.seeingPlayers.add(player);
        for(AbstractLine<?> line: this.lines) {
            line.show(player);
        }
        Bukkit.getScheduler().runTask(
                plugin, ()->Bukkit.getPluginManager().callEvent(new PlayerHologramShowEvent(player, this)));
    }

    protected void hide(@NotNull Player player) {
        for(AbstractLine<?> line: this.lines) {
            line.hide(player);
        }
        this.seeingPlayers.remove(player);

        if(plugin.isEnabled()) {
        	 Bukkit.getScheduler().runTask(plugin, ()->Bukkit.getPluginManager().callEvent(new PlayerHologramHideEvent(player, this)));
        }
       
    }

    @NotNull
    protected AbstractLine<?> getLine(int index) {
        return this.lines[Math.abs(index-this.lines.length+1)];
    }

    protected boolean isShownFor(@NotNull Player player) {
        return this.seeingPlayers.contains(player);
    }

    @NotNull
    protected Collection<Player> getSeeingPlayers() {
        return seeingPlayers;
    }

    @NotNull
    public static Builder builder() {

        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityData hologram = (EntityData) o;
        return Objects.equals(location, hologram.location) && Arrays.equals(lines, hologram.lines);
    }

	public Object getType() {
		return type;
	}

	public int getEntId() {
		return entId;
	}

	public EntityEquipData getEquipData() {
		return equipData;
	}

	public String getName() {
		return name;
	}

	public EntityPoseData getPoseData() {
		return poseData;
	}

	public void setPoseData(EntityPoseData poseData) {
		this.poseData = poseData;
	}

	public EntityEnum getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityEnum entityType) {
		this.entityType = entityType;
	}

	public AgeType getAge() {
		return age;
	}


    
}
