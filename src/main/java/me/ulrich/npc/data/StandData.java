package me.ulrich.npc.data;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

import me.ulrich.npc.data.StandEnum.AgeType;

public class StandData {

	private String id;
	private Location location;
	private EntityEquipData equip;
	private int cooldownClick;
	private List<String> commands;
	private String permission;
	private UUID owner;
	private int tempId;
	private String name;
	private EntityPoseData pose;
	private EntityEnum entityType;
	private AgeType age;

	public StandData(String id, UUID owner, String name, Location location, EntityEquipData equip, EntityPoseData pose, int cooldownClick, List<String> commands, String permission, int tempId, EntityEnum entityType, AgeType age) {
		this.setId(id);
		this.setOwner(owner);
		this.setName(name);
		this.setLocation(location);
		this.setEquip(equip);
		this.setCooldownClick(cooldownClick);
		this.setCommands(commands);
		this.setPermission(permission);
		this.setTempId(tempId);
		this.setPose(pose);
		this.setEntityType(entityType);
		this.setAge(age);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public EntityEquipData getEquip() {
		return equip;
	}

	public void setEquip(EntityEquipData equip) {
		this.equip = equip;
	}

	public int getCooldownClick() {
		return cooldownClick;
	}

	public void setCooldownClick(int cooldownClick) {
		this.cooldownClick = cooldownClick;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public int getTempId() {
		return tempId;
	}

	public void setTempId(int tempId) {
		this.tempId = tempId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EntityPoseData getPose() {
		return pose;
	}

	public void setPose(EntityPoseData pose) {
		this.pose = pose;
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

	public void setAge(AgeType age) {
		this.age = age;
	}
}
