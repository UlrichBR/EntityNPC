package me.ulrich.npc.data;

public class EntityPoseData {

	private EntityPoseData_values head;
	private EntityPoseData_values leftHand;
	private EntityPoseData_values rightHand;
	private EntityPoseData_values leftFoot;
	private EntityPoseData_values rightFoot;
	private double direction;
	private EntityPoseData_values body;

	public EntityPoseData(double direction2, EntityPoseData_values head, EntityPoseData_values leftHand, EntityPoseData_values rightHand, EntityPoseData_values leftFoot, EntityPoseData_values rightFoot, EntityPoseData_values body) {
		this.setHead(head);
		this.setLeftHand(leftHand);
		this.setRightHand(rightHand);
		this.setLeftFoot(leftFoot);
		this.setRightFoot(rightFoot);
		this.setBody(body);
		this.setDirection(direction2);
	}

	public EntityPoseData_values getHead() {
		return head;
	}

	public void setHead(EntityPoseData_values head) {
		this.head = head;
	}

	public EntityPoseData_values getLeftHand() {
		return leftHand;
	}

	public void setLeftHand(EntityPoseData_values leftHand) {
		this.leftHand = leftHand;
	}

	public EntityPoseData_values getRightHand() {
		return rightHand;
	}

	public void setRightHand(EntityPoseData_values rightHand) {
		this.rightHand = rightHand;
	}

	public EntityPoseData_values getLeftFoot() {
		return leftFoot;
	}

	public void setLeftFoot(EntityPoseData_values leftFoot) {
		this.leftFoot = leftFoot;
	}

	public EntityPoseData_values getRightFoot() {
		return rightFoot;
	}

	public void setRightFoot(EntityPoseData_values rightFoot) {
		this.rightFoot = rightFoot;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public EntityPoseData_values getBody() {
		return body;
	}

	public void setBody(EntityPoseData_values body) {
		this.body = body;
	}
}
