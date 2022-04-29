package me.ulrich.npc;

import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import me.ulrich.npc.data.EntityEnum;
import me.ulrich.npc.data.EntityEquipData;
import me.ulrich.npc.data.EntityPoseData;
import me.ulrich.npc.data.StandEnum.AgeType;

public class Builder {

    private Object lines = null;
    private Location location;
	private @NotNull String uuid;
	private Object type;
	private EntityEquipData equipData;
	private EntityPoseData poseData = null;
	private EntityEnum entityType;
	private AgeType age;

    @NotNull
    public Builder setName(@NotNull String line) {
        Validate.notNull(line, "Line cannot be null");
        this.lines=line;
        return this;
    }

    @NotNull
    public Builder setEquip(@NotNull EntityEquipData equipData) {
    	Validate.notNull(equipData, "Equip cannot be null");
    	this.equipData = equipData;
		return this;
	}
    
    @NotNull
    public Builder setPose(@NotNull EntityPoseData pose) {
    	Validate.notNull(pose, "Pose cannot be null");
    	this.poseData = pose;
		return this;
	}
 
    @NotNull
    public Builder setUUID(@NotNull String uuid) {
        Validate.notNull(uuid, "Line cannot be null");
        this.uuid = uuid;
        return this;
    }
    
    @NotNull
    public Builder setEntityType(@NotNull EntityEnum type) {
        this.entityType = type;
        return this;
    }
    
    @NotNull
    public Builder setEntityAge(@NotNull AgeType age) {
        this.age = age;
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
        
        EntityData hologram = new EntityData(pool.getPlugin(), this.location, new CopyOnWriteArraySet<>(), this.uuid, this.type, this.lines, this.equipData, this.poseData, this.entityType, this.age);
        //hologram.setAnimation(0, Animation.CIRCLE);
        pool.takeCareOf(hologram);
        return hologram;
    }

	
}