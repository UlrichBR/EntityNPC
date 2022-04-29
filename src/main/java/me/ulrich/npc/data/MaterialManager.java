package me.ulrich.npc.data;

import org.bukkit.Bukkit;

import me.ulrich.npc.util.VersionUtil;
import me.ulrich.npc.util.VersionUtil.VersionEnum;


public enum MaterialManager {
 

	SUNFLOWER("DOUBLE_PLANT", "SUNFLOWER"),
	CLOCK("WATCH", "CLOCK"),
	EXPERIENCE_BOTTLE("EXP_BOTTLE","EXPERIENCE_BOTTLE"),
	CRAFTING_TABLE("WORKBENCH","CRAFTING_TABLE"),
	GOLDEN_BOOTS("GOLD_BOOTS","GOLDEN_BOOTS"),
	
	RED_STAINED_GLASS_PANE("STAINED_GLASS_PANE:14","RED_STAINED_GLASS_PANE"),
	PURPLE_STAINED_GLASS_PANE("STAINED_GLASS_PANE:10","PURPLE_STAINED_GLASS_PANE"),
	MAGENTA_STAINED_GLASS_PANE("STAINED_GLASS_PANE:2","MAGENTA_STAINED_GLASS_PANE"),
	LIME_STAINED_GLASS_PANE("STAINED_GLASS_PANE:5","LIME_STAINED_GLASS_PANE"),
	GRAY_STAINED_GLASS_PANE("STAINED_GLASS_PANE:7","GRAY_STAINED_GLASS_PANE"),
	ORANGE_STAINED_GLASS_PANE("STAINED_GLASS_PANE:1","ORANGE_STAINED_GLASS_PANE"),
	YELLOW_STAINED_GLASS_PANE("STAINED_GLASS_PANE:4","YELLOW_STAINED_GLASS_PANE"),
	GREEN_STAINED_GLASS_PANE("STAINED_GLASS_PANE:13","GREEN_STAINED_GLASS_PANE"),
	PINK_STAINED_GLASS_PANE("STAINED_GLASS_PANE:6","PINK_STAINED_GLASS_PANE"),
	LIGHT_BLUE_STAINED_GLASS_PANE("STAINED_GLASS_PANE:3","LIGHT_BLUE_STAINED_GLASS_PANE"),
	BLUE_STAINED_GLASS_PANE("STAINED_GLASS_PANE:11","BLUE_STAINED_GLASS_PANE"),

	ENDER_EYE("EYE_OF_ENDER","ENDER_EYE"),
	CHEST_MINECART("STORAGE_MINECART","CHEST_MINECART"),
	FURNACE_MINECART("HOPPER_MINECART","FURNACE_MINECART"),
	JUNGLE_SAPLING("SAPLING:3","JUNGLE_SAPLING"),
	OAK_SAPLING("SAPLING:0","OAK_SAPLING"),
	JUNGLE_LEAVES("LEAVES:3","JUNGLE_LEAVES"),
	NETHER_QUARTZ_ORE("QUARTZ_ORE","NETHER_QUARTZ_ORE"),
	SPAWNER("MOB_SPAWNER","SPAWNER"),
	ZOMBIE_HEAD("SKULL_ITEM:2","ZOMBIE_HEAD"),
	PIG_SPAWN_EGG("MONSTER_EGG:90","PIG_SPAWN_EGG"),
	PISTON("PISTON_BASE","PISTON"),
	PLAYER_HEAD("SKULL_ITEM:3", "PLAYER_HEAD"),
	RED_BED("BED","RED_BED"),
	LIME_DYE("INK_SACK:10","LIME_DYE"),
	RED_DYE("INK_SACK:1","RED_DYE"),
	LIGHT_BLUE_DYE("INK_SACK:12","LIGHT_BLUE_DYE"),
	GRAY_DYE("INK_SACK:8","GRAY_DYE"),
	END_STONE("ENDER_STONE","END_STONE"),
	ENCHANTING_TABLE("ENCHANTMENT_TABLE","ENCHANTING_TABLE"),
	OAK_TRAPDOOR("TRAP_DOOR","OAK_TRAPDOOR"),
	COMPARATOR("REDSTONE_COMPARATOR","COMPARATOR"),
	MAP("EMPTY_MAP","MAP"),
	GOLDEN_HELMET("GOLD_HELMET","GOLDEN_HELMET"),
	OAK_FENCE_GATE("FENCE_GATE","OAK_FENCE_GATE"),
	
	CHISELED_STONE_BRICKS("SMOOTH_BRICK:3","CHISELED_STONE_BRICKS"),
	END_CRYSTAL("ENDER_EYE","END_CRYSTAL"),
	LEGACY("0","1"),
	;

	
	
	private boolean islegacy = ((VersionUtil.isBelow(VersionEnum.V1_12))?true:false);
	
	private String legacy;
	private String lasted;
	private String current;
	
	MaterialManager(String legacy, String lasted) {

		
		
		this.setLegacy(legacy);
		this.setLasted(lasted);
		
		if(isIslegacy()) {
			this.setCurrent(legacy);
		} else {
			this.setCurrent(lasted);
		}
		
		
		
	}
	
	public static String getNetherBiome() {
		if (Bukkit.getServer().getVersion().contains("1.13") || Bukkit.getServer().getVersion().contains("1.14") || Bukkit.getServer().getVersion().contains("1.15")) {
			return ("NETHER");
		} else if (Bukkit.getServer().getVersion().contains("1.16") || Bukkit.getServer().getVersion().contains("1.17")) {
			return ("NETHER_WASTES");
		} else {
			return ("HELL");
		}
	}
	
	public String getMaterial(MaterialManager material ) {
		
		try {
			material.getLegacy();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
