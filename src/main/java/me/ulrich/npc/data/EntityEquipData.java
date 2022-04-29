package me.ulrich.npc.data;

import org.bukkit.inventory.ItemStack;

public class EntityEquipData {

	private ItemStack head;
	private ItemStack chest;
	private ItemStack legs;
	private ItemStack boots;
	private ItemStack mainHand;
	private ItemStack offHand;

	public EntityEquipData(ItemStack head, ItemStack chest, ItemStack legs, ItemStack boots, ItemStack mainHand, ItemStack offHand) {
		this.setHead(head);
		this.setChest(chest);
		this.setLegs(legs);
		this.setBoots(boots);
		this.setMainHand(mainHand);
		this.setOffHand(offHand);
		
	}

	public ItemStack getHead() {
		return head;
	}

	public void setHead(ItemStack head) {
		this.head = head;
	}

	public ItemStack getChest() {
		return chest;
	}

	public void setChest(ItemStack chest) {
		this.chest = chest;
	}

	public ItemStack getLegs() {
		return legs;
	}

	public void setLegs(ItemStack legs) {
		this.legs = legs;
	}

	public ItemStack getBoots() {
		return boots;
	}

	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}

	public ItemStack getMainHand() {
		return mainHand;
	}

	public void setMainHand(ItemStack mainHand) {
		this.mainHand = mainHand;
	}

	public ItemStack getOffHand() {
		return offHand;
	}

	public void setOffHand(ItemStack offHand) {
		this.offHand = offHand;
	}
}
