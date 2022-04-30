package me.ulrich.npc.data;

import me.ulrich.npc.animation.Animation;
import me.ulrich.npc.data.StandEnum.AgeType;

public class EntityLine {

	private EntityEnum type;
	private String name;
	private Boolean visible;
	private EntityEquipData equipData;
	private EntityPoseData poseData;
	private AgeType age;
	private Animation animation;
	
	public EntityLine(EntityEnum type, String name,EntityEquipData equipData, EntityPoseData poseData, AgeType age, Animation animation , Boolean visible) {
		this.setType(type);
		this.setName(name);
		this.setEquipData(equipData);
		this.setPoseData(poseData);
		this.setAge(age);
		this.setVisible(visible);
		this.setAnimation(animation);
		
	}

	public EntityEnum getType() {
		return type;
	}

	public void setType(EntityEnum type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public EntityEquipData getEquipData() {
		return equipData;
	}

	public void setEquipData(EntityEquipData equipData) {
		this.equipData = equipData;
	}

	public EntityPoseData getPoseData() {
		return poseData;
	}

	public void setPoseData(EntityPoseData poseData) {
		this.poseData = poseData;
	}

	public AgeType getAge() {
		return age;
	}

	public void setAge(AgeType age) {
		this.age = age;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

}
