package me.ulrich.npc.data;

import me.ulrich.npc.util.VersionUtil;
import me.ulrich.npc.util.VersionUtil.VersionEnum;

public enum EntityEnum {

	armor_stand("armor_stand","1", ""),
	axolotl("axolotl","3", ""),
	bat("bat","4", ""),
	blaze("blaze","6", ""),
	boat("boat","7", ""),
	cat("cat","8", ""),
	cave_spider("cave_spider","9", ""),
	chicken("chicken","10", ""),
	cod("cod","11", ""),
	cow("cow","12", ""),
	creeper("creeper","13", ""),
	dolphin("dolphin","14", ""),
	donkey("donkey","15", ""),
	drowned("drowned","17", ""),
	elder_guardian("elder_guardian","18", ""),
	end_crystal("end_crystal","19", ""),
	ender_dragon("ender_dragon","20", ""),
	enderman("enderman","21", ""),
	endermite("endermite","22", ""),
	evoker("evoker","23", ""),
	fox("fox","29", ""),
	ghast("ghast","30", ""),
	giant("giant","31", ""),
	glow_squid("glow_squid","33", ""),
	goat("goat","34", ""),
	guardian("guardian","35", ""),
	hoglin("hoglin","36", ""),
	horse("horse","37", ""),
	husk("husk","38", ""),
	illusioner("illusioner","39", ""),
	iron_golem("iron_golem","40", ""),
	llama("llama","46", ""),
	magma_cube("magma_cube","48", ""),
	mule("mule","57", ""),
	mooshroom("mooshroom","58", ""),
	ocelot("ocelot","59", ""),
	panda("panda","61", ""),
	parrot("parrot","62", ""),
	phantom("phantom","63", ""),
	pig("pig","64", ""),
	piglin("piglin","65", ""),
	piglin_brute("piglin_brute","66", ""),
	pillager("pillager","67", ""),
	polar_bear("polar_bear","68", ""),
	tnt("tnt","69", ""),
	pufferfish("pufferfish","70", ""),
	rabbit("rabbit","71", ""),
	ravager("ravager","72", ""),
	salmon("salmon","73", ""),
	sheep("sheep","74", ""),
	shulker("shulker","75", ""),
	silverfish("silverfish","77", ""),
	skeleton("skeleton","78", ""),
	skeleton_horse("skeleton_horse","79", ""),
	slime("slime","80", ""),
	snow_golem("snow_golem","82", ""),
	spider("spider","85", ""),
	squid("squid","86", ""),
	stray("stray","87", ""),
	strider("strider","88", ""),
	trader_llama("trader_llama","94", ""),
	tropical_fish("tropical_fish","95", ""),
	turtle("turtle","96", ""),
	vex("vex","97", ""),
	villager("villager","98", ""),
	vindicator("vindicator","99", ""),
	wandering_trader("wandering_trader","11", ""),
	witch("witch","101", ""),
	wither("wither","102", ""),
	wither_skeleton("wither_skeleton","103", ""),
	wolf("wolf","105", ""),
	zoglin("zoglin","106", ""),
	zombie("zombie","107", ""),
	zombie_horse("zombie_horse","108", ""),
	zombie_villager("zombie_villager","109", ""),
	zombified_piglin("zombified_piglin","110", ""),
	player("player","111", ""),
	
	;

	private boolean islegacy = ((VersionUtil.isBelow(VersionEnum.V1_12))?true:false);
	
	private String legacy;
	private String lasted;
	private String current;
	
	EntityEnum(String legacy, String lasted, String minversion) {

		
		
		this.setLegacy(legacy);
		this.setLasted(lasted);
		
		if(isIslegacy()) {
			this.setCurrent(legacy);
		} else {
			this.setCurrent(lasted);
		}
		
		
		
	}
	
	
	public String getMaterial(EntityEnum material ) {
		
		try {
			material.getLegacy();
		} catch (Exception e) {}
		
		return "AIR";
	}

	public String getLegacy() {
		return legacy;
	}

	public void setLegacy(String legacy) {
		this.legacy = legacy;
	}

	public String getLasted() {
		return lasted;
	}

	public void setLasted(String lasted) {
		this.lasted = lasted;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public boolean isIslegacy() {
		return islegacy;
	}

	public void setIslegacy(boolean islegacy) {
		this.islegacy = islegacy;
	}



	
}
