package me.ulrich.npc;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import me.ulrich.npc.data.EntityLine;

public class Builder {

	private final ConcurrentLinkedDeque<EntityLine> lines = new ConcurrentLinkedDeque<>();
    private Location location;
	private String uuid;
	
    @NotNull
    public Builder setLine(@NotNull EntityLine line) {
        Validate.notNull(line, "Line cannot be null");
        this.lines.addFirst(line);
        return this;
    }
    
	public Builder addLines(List<EntityLine> lines2) {
		for(int j=0; j<lines2.size(); j++) {
            @NotNull
			EntityLine line = lines2.get(j);
            Validate.notNull(line, "Line cannot be null");
            this.lines.addFirst(line);
    	}
		return this;
	}

 
    @NotNull
    public Builder setUUID(@NotNull String uuid) {
        Validate.notNull(uuid, "Line cannot be null");
        this.uuid = uuid;
        return this;
    }

    @NotNull
    public Builder location(@NotNull Location location) {
        Validate.notNull(location, "Location cannot be null");
        this.location = location;
        return this;
    }

    @NotNull
    public EntityData build(@NotNull EntityManager pool) {

        if(location==null || lines==null || pool==null) {
            throw new IllegalArgumentException("No location given or not completed!");
        }
        
        EntityData hologram = new EntityData(pool.getPlugin(), this.location, new CopyOnWriteArraySet<>(), this.uuid, this.lines.toArray());
        //hologram.setAnimation(0, Animation.CIRCLE);
        pool.takeCareOf(hologram);
        return hologram;
    }
	
	public ConcurrentLinkedDeque<EntityLine> getLines() {
		return lines;
	}
	public Location getLocation() {
		return location;
	}
	public String getUuid() {
		return uuid;
	}





	
}