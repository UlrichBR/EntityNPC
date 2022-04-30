package me.ulrich.npc.line;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import me.ulrich.npc.data.EntityEnum;
import me.ulrich.npc.data.EntityEquipData;
import me.ulrich.npc.data.EntityPoseData;
import me.ulrich.npc.data.MaterialManager;
import me.ulrich.npc.data.StandEnum.AgeType;
import me.ulrich.npc.util.VersionUtil;
import me.ulrich.npc.util.VersionUtil.VersionEnum;

public class TextLine extends AbstractLine<String> {


    public TextLine(int entNum, @NotNull Collection<Player> seeingPlayers, @NotNull Plugin plugin, int curr_id, @NotNull String obj, EntityEquipData equipData, EntityPoseData poseData, EntityEnum entityType, AgeType age, Boolean visible) {
        super(entNum, seeingPlayers, plugin, curr_id, obj, equipData, poseData, entityType, age, visible);

    }

    @Override
    public void show(@NotNull Player player) {
        super.show(player);
        /*
         * Entity Metadata
         */

        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, entityID);
        WrappedDataWatcher watcher = new WrappedDataWatcher();

        if(VersionUtil.isCompatible(VersionUtil.VersionEnum.V1_8)) {
            //watcher.setObject(0, (byte) 0x20);
            watcher.setObject(2, (String) this.obj);
            watcher.setObject(3, (byte) 1);
            
          //baseplate|hands
            watcher.setObject(15, (byte) (0x08|0x04));
        }else{
        	
        	if(!this.getVisible()) {
                WrappedDataWatcher.WrappedDataWatcherObject visible = new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class));
                watcher.setObject(visible, (byte) 0x20);
        	}

            byte byte15list = (byte) (0x08|0x04);
            
            if(this.getAge()!=null) {
            	if(this.getEntityType().equals(EntityEnum.armor_stand) && this.getAge().equals(AgeType.BABY)) {
                	byte15list = (byte) (0x08|0x04|0x01);
                } else {
                	byte15list = (byte) (0x08|0x04);
                }
            	
            	if(!this.getEntityType().equals(EntityEnum.armor_stand)) {
                	if(this.getEntityType().equals(EntityEnum.slime)) {
                    	watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(16, WrappedDataWatcher.Registry.get(Integer.class)), ( (this.getAge().equals(AgeType.BABY)?1:4) ));
                	} else {
                    	watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(16, WrappedDataWatcher.Registry.get(Boolean.class)), ( (this.getAge().equals(AgeType.BABY)?true:false) ));
                    	if(this.getEntityType().equals(EntityEnum.villager)) {
                        	//watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(16, WrappedDataWatcher.Registry.get(Integer.class)), 3);
                    	}

                	}
                }
            	
            }

            if(this.getEntityType().equals(EntityEnum.villager)) {
            	
            	//watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(17, WrappedDataWatcher.Registry.get(Integer.class)), 5);

            	/*
            	WrappedDataWatcherObject dataWatcherObj = new WrappedDataWatcherObject(18, WrappedDataWatcher.Registry.get(Integer.class));

            		Type type = Type.valueOf("PLAINS");
					Profession profession = Profession.NONE;
					// Set villager data
            		WrappedVillagerData villagerData = WrappedVillagerData.fromValues(type, profession , 1);
            		watcher.setObject(dataWatcherObj, villagerData.getHandle());
            	*/

            }
            
            watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(15, WrappedDataWatcher.Registry.get(Byte.class)), byte15list );

            
            

            if(this.obj!=null && !this.obj.isEmpty()) {
            	
            	String colored_name = this.obj;

            	if(VersionUtil.isAbove(VersionEnum.V1_13)) {
                	Optional<?> opt = Optional.of(WrappedChatComponent.fromChatMessage(colored_name)[0].getHandle());
                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
                } else {
                	watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.get(String.class)), colored_name);
                }
            	
            	WrappedDataWatcher.WrappedDataWatcherObject nameVisible = new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class));
                watcher.setObject(nameVisible, true);	
            }
            

            
            if(this.getPoseData()!=null && this.getEntityType().equals(EntityEnum.armor_stand)) {

            	if(this.getPoseData().getLeftHand()!=null) {
                    WrappedDataWatcher.WrappedDataWatcherObject leftArmRotation = new WrappedDataWatcher.WrappedDataWatcherObject(18, WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass()));
                    watcher.setObject(leftArmRotation, new Vector3F((float) this.getPoseData().getLeftHand().getX(), (float) this.getPoseData().getLeftHand().getY(), (float) this.getPoseData().getLeftHand().getZ()));
            	}
            	
            	if(this.getPoseData().getRightHand()!=null) {
                    WrappedDataWatcher.WrappedDataWatcherObject rightArmRotation = new WrappedDataWatcher.WrappedDataWatcherObject(19, WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass()));
                    watcher.setObject(rightArmRotation, new Vector3F((float) this.getPoseData().getRightHand().getX(), (float) this.getPoseData().getRightHand().getY(), (float) this.getPoseData().getRightHand().getZ()));	
            	}
            	
            	if(this.getPoseData().getLeftFoot()!=null) {
                    WrappedDataWatcher.WrappedDataWatcherObject leftFootRotation = new WrappedDataWatcher.WrappedDataWatcherObject(20, WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass()));
                    watcher.setObject(leftFootRotation, new Vector3F((float) this.getPoseData().getLeftFoot().getX(), (float) this.getPoseData().getLeftFoot().getY(), (float) this.getPoseData().getLeftFoot().getZ()));
            	}

            	if(this.getPoseData().getRightFoot()!=null) {
                    WrappedDataWatcher.WrappedDataWatcherObject rightFootRotation = new WrappedDataWatcher.WrappedDataWatcherObject(21, WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass()));
                    watcher.setObject(rightFootRotation, new Vector3F((float) this.getPoseData().getRightFoot().getX(), (float) this.getPoseData().getRightFoot().getY(), (float) this.getPoseData().getRightFoot().getZ()));
            	}
            	
            	if(this.getPoseData().getBody()!=null) {
                    WrappedDataWatcher.WrappedDataWatcherObject bodyRotation = new WrappedDataWatcher.WrappedDataWatcherObject(17, WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass()));
                    watcher.setObject(bodyRotation, new Vector3F((float) this.getPoseData().getBody().getX(), (float) this.getPoseData().getBody().getY(), (float) this.getPoseData().getBody().getZ()));
            	}

            	if(this.getPoseData().getHead()!=null) {
            		
                    WrappedDataWatcher.WrappedDataWatcherObject headRotation = new WrappedDataWatcher.WrappedDataWatcherObject(16, WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass()));
                    watcher.setObject(headRotation, new Vector3F((float) this.getPoseData().getHead().getX(), (float) this.getPoseData().getHead().getY(), (float) this.getPoseData().getHead().getZ()));
            	}


                
            }

            
        }

        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }
                
        direction(player);
        update(player);
        
        
    }
    
    @Override
	public void respawn(@NotNull Player player) {

	}
    
    @Override
    public void update(@NotNull Player player) throws NullPointerException {
        /*
         * Entity Equipment
         */
    	    
    	if((this.getEquipData()==null)) {
    		return;
    	}
    	    	
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, this.entityID);
        
        if (VersionUtil.isCompatible(VersionUtil.VersionEnum.V1_8)) {

            // Use legacy form to update the head slot.
            packet.getIntegers().write(1, 4);
            packet.getItemModifier().write(0, new ItemStack(Material.PLAYER_HEAD));
  
        } else {
        	
        	
        	List<Pair<EnumWrappers.ItemSlot, ItemStack>> pairList = new ArrayList<>();

        	
        	
        	
            if(this.getEquipData()!=null) {

            	if((this.getEntityType().equals(EntityEnum.armor_stand) || this.getEntityType().equals(EntityEnum.zombie) || this.getEntityType().equals(EntityEnum.giant) || this.getEntityType().equals(EntityEnum.skeleton))) {
            		
            		if(this.getEquipData().getHead()!=null) {
                    	pairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, this.getEquipData().getHead()));
                    } else {
                    	pairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, new ItemStack(Material.AIR)));
                    }
                    
                    if(this.getEquipData().getChest()!=null) {
                    	pairList.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, this.getEquipData().getChest()));
                    } else {
                    	pairList.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, new ItemStack(Material.AIR)));
                    }
                    
                    if(this.getEquipData().getLegs()!=null) {
                    	pairList.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, this.getEquipData().getLegs()));
                    } else {
                    	pairList.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, new ItemStack(Material.AIR)));
                    }
                    
                    if(this.getEquipData().getBoots()!=null) {
                    	pairList.add(new Pair<>(EnumWrappers.ItemSlot.FEET, this.getEquipData().getBoots()));
                    } else {
                    	pairList.add(new Pair<>(EnumWrappers.ItemSlot.FEET, new ItemStack(Material.AIR)));
                    }
                    
            	}
 
                if(this.getEquipData().getMainHand()!=null) {
                	pairList.add(new Pair<>(EnumWrappers.ItemSlot.MAINHAND, this.getEquipData().getMainHand()));
                } else {
                	pairList.add(new Pair<>(EnumWrappers.ItemSlot.MAINHAND, new ItemStack(Material.AIR)));
                }
                
                if(this.getEquipData().getOffHand()!=null) {
                	pairList.add(new Pair<>(EnumWrappers.ItemSlot.OFFHAND, this.getEquipData().getOffHand()));
                } else {
                	pairList.add(new Pair<>(EnumWrappers.ItemSlot.OFFHAND, new ItemStack(Material.AIR)));
                }
                
                if((this.getEquipData().getHead()==null && this.getEquipData().getChest()==null && this.getEquipData().getLegs()==null && this.getEquipData().getBoots()==null && this.getEquipData().getMainHand()==null && this.getEquipData().getOffHand()==null)){
                	//pairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, new ItemStack(Material.valueOf(MaterialManager.PLAYER_HEAD.getLasted()))));
                }
  
            } else {
            	pairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, new ItemStack(Material.valueOf(MaterialManager.PLAYER_HEAD.getLasted()))));
            }
            
            if(packet.getSlotStackPairLists()==null || pairList.size()==0) {
            	return;
            }
            
            packet.getSlotStackPairLists().write(0, pairList);
            
        }
        
        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void direction(@NotNull Player player) {
		if(this.getPoseData()==null) {
			return;
		}
						
		PacketContainer pc = new PacketContainer(PacketType.Play.Server.ENTITY_LOOK);
		
		if(this.getEntityType().equals(EntityEnum.armor_stand)) {
			pc.getIntegers().write(0, this.entityID);
	        pc.getBytes().write(0, (byte)getCompressedAngle((float) this.getPoseData().getDirection())).write(1, (byte) 0);
	        pc.getBooleans().write(0, true);
		} else {
			pc = new PacketContainer(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
			pc.getIntegers().write(0, this.entityID);
	        pc.getModifier().writeDefaults();
	        pc.getBytes().write(0, (byte)getCompressedAngle((float) this.getPoseData().getDirection()));
	        
		}

        try {
            protocolManager.sendServerPacket(player, pc);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
		
	}
	
	private int getCompressedAngle(float value) {
        return (int)(value * 256.0F / 360.0F);
    }

	

	
    /*
    @Override
    public void update(@NotNull Player player) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, this.entityID);

        WrappedDataWatcher watcher = new WrappedDataWatcher();

        if(VersionUtil.isCompatible(VersionUtil.VersionEnum.V1_8)) {
            watcher.setObject(2, this.obj);
        }else{
            Optional<?> opt = Optional
                    .of(WrappedChatComponent
                            .fromChatMessage(this.obj)[0].getHandle());

            watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
        }

        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        
        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }
        

        
        
    }
    */

}
