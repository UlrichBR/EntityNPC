package me.ulrich.npc;

import me.ulrich.npc.animation.*;
import me.ulrich.npc.line.AbstractLine;
import me.ulrich.npc.line.TextLine;
import me.ulrich.npc.data.EntityEnum;
import me.ulrich.npc.data.EntityEquipData;
import me.ulrich.npc.data.EntityLine;
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
	private final String name;
	private final ConcurrentLinkedDeque<Integer> entId;

    public EntityData(@NotNull JavaPlugin plugin, @NotNull Location location, @NotNull Collection<Player> seeingPlayers, @NotNull String uuid, Object... lines) {
        this.plugin = plugin;
        this.location = location;
        this.seeingPlayers = seeingPlayers;
        this.lines = new AbstractLine[lines.length];
        this.uuid = uuid;
        this.entId = new ConcurrentLinkedDeque<>();

        final ThreadLocalRandom random = ThreadLocalRandom.current();
        Location cloned = this.location.clone().subtract(0, 0.28D, 0);
        
        EntityLine linex = (EntityLine) lines[0];
        
        this.name = linex.getName();
        
        EntityLine entity;
        AbstractLine<?> tempLine;
        for(int j=0; j<lines.length; j++) {
        	entity = (EntityLine) lines[j];
            double up = 0.28D;

            try {
            	if(lines[j-1]!=null) {
                	EntityLine lineant = (EntityLine) lines[j-1];
                	if(!lineant.getVisible() && lineant.getEquipData()!=null) {
                		if(lineant.getEquipData().getHead()!=null) {
                			up = 0.0D;
                			
                		}
                	}
                }
            } catch (Exception e) {}
            	
            if(!entity.getVisible() && entity.getEquipData()!=null) {
        		if(entity.getEquipData().getHead()!=null) {
        			entity.setName("");
        			entity.setAge(AgeType.ADULT);
        		}
        	}

        	int curr_id = random.nextInt();
        	this.entId.add(curr_id);
        	
        	
        	if(!entity.getVisible() && entity.getEquipData()!=null && entity.getEquipData().getHead()!=null) {
        		
        		tempLine = new TextLine(0, this.seeingPlayers, plugin, curr_id, (String) entity.getName(), entity.getEquipData(), entity.getPoseData(), entity.getType(), entity.getAge(), entity.getVisible());
                tempLine.setLocation(cloned.add(0.0, (up*(j)), 0).clone());
                if(entity.getAnimation()!=null) {
                	tempLine.setAnimation(entity.getAnimation());
                }
                
                this.lines[j] = tempLine;
        	} else {
        		tempLine = new TextLine(0, this.seeingPlayers, plugin, curr_id, (String) entity.getName(), entity.getEquipData(), entity.getPoseData(), entity.getType(), entity.getAge(), entity.getVisible());
                tempLine.setLocation(cloned.add(0.0, up, 0).clone());
                this.lines[j] = tempLine;
        	}
        	
            
            
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

	public ConcurrentLinkedDeque<Integer> getEntId() {
		return entId;
	}

	public String getName() {
		return name;
	}



    
}
