package me.ulrich.npc.line;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import me.ulrich.npc.animation.Animation;
import me.ulrich.npc.util.VersionUtil;
import me.ulrich.npc.data.EntityEquipData;
import me.ulrich.npc.data.EntityPoseData;
import me.ulrich.npc.data.StandEnum.AgeType;

public abstract class AbstractLine<T> {
    private final Plugin plugin;
    protected final ProtocolManager protocolManager;
    protected final int entityID;
    protected Location location;
    protected T obj;
    protected Optional<Animation> animation = Optional.empty();
    private final Collection<Player> animationPlayers;
    private int taskID = -1;
    private WrappedDataWatcher defaultDataWatcher;
	private EntityEquipData equipData;
	private EntityPoseData poseData;
	private Object entityType;
	private AgeType age;
	private int entNum;
	private Boolean visible;
	private Boolean equipable;

    public AbstractLine(int entNum, @NotNull Collection<Player> seeingPlayers, @NotNull Plugin plugin, int entityID, @NotNull T obj, EntityEquipData equipData, EntityPoseData poseData, Object entityType, AgeType age, Boolean visible, Boolean equipable) {
        this.plugin = plugin;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.entityID = entityID;
        this.obj = obj;
        this.animationPlayers = seeingPlayers; //copy rif
        this.equipData = equipData;
        this.poseData = poseData;
        this.age = age;
        this.entNum = entNum;
        this.visible = visible;
        this.entityType = entityType;
        this.equipable = equipable;

        if(VersionUtil.isCompatible(VersionUtil.VersionEnum.V1_8)) {
            defaultDataWatcher = getDefaultWatcher(Bukkit.getWorlds().get(0));
        }
    }

    public void setLocation(@NotNull Location location) {
        this.location = location;
    }

    public void set(T newObj) {
        this.obj = newObj;
    }

    public abstract void update(@NotNull Player player);
        
    public abstract void direction(@NotNull Player player);

    public abstract void respawn(@NotNull Player player);
    
    public void hide(@NotNull Player player) {

    	
		PacketContainer destroyEntity = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
		try {
			destroyEntity.getIntegerArrays().write(0, new int[] { this.entityID });
		} catch (Exception ex) {

			List<Integer> entityIDList = new ArrayList<Integer>();
			entityIDList.add(this.entityID);
			destroyEntity.getIntLists().write(0, entityIDList);
		}
		try {

			protocolManager.sendServerPacket(player, destroyEntity);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException("Cannot send server packet.", ex);
		}

    }

    @SuppressWarnings("deprecation")
	public void show(@NotNull Player player) {

        final PacketContainer itemPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        String entity_desc = (String) this.entityType;
        
        if(VersionUtil.isCompatible(VersionUtil.VersionEnum.V1_8)) {
        	
            itemPacket.getIntegers().
                    write(0, this.entityID).
                    write(1, (int) EntityType.valueOf(entity_desc.toUpperCase()).getTypeId()).
                    write(2, (int) (this.location.getX() * 32)).
                    write(3, (int) (this.location.getY() * 32)).
                    write(4, (int) (this.location.getZ() * 32));
            itemPacket.getDataWatcherModifier().write(0, this.defaultDataWatcher);
            
        }else{

            final int extraData = 1;
            StructureModifier<Integer> itemInts = itemPacket.getIntegers();
            itemInts.write(0, this.entityID);

            //itemInts.write(1, Integer.valueOf((String) splited[1]));
            //itemInts.write(2, extraData);
                                    
            StructureModifier<UUID> itemIDs = itemPacket.getUUIDs();
            itemIDs.write(0, UUID.randomUUID());

            StructureModifier<Double> itemDoubles = itemPacket.getDoubles();
            itemDoubles.write(0, this.location.getX());
            itemDoubles.write(1, this.location.getY()/*+1.2*/);
            itemDoubles.write(2, this.location.getZ());
            
        	itemPacket.getEntityTypeModifier().write(0, EntityType.valueOf((String) entity_desc.toUpperCase()));

        }

        try {

            protocolManager.sendServerPacket(player, itemPacket);
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }
        
        //rotate(player, this.location.getYaw());

        
    }

    public void setAnimation(@NotNull Animation animation) {
    	
        this.animation = Optional.of(animation);
        animation.setEntityID(this.entityID);
        animation.setProtocolManager(this.protocolManager);

        Runnable taskR = ()-> this.animationPlayers.forEach(animation::nextFrame);
        BukkitTask task;
        if(animation.async()) {
            task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, taskR, animation.delay(), animation.delay());
        } else {
            task = Bukkit.getScheduler().runTaskTimer(plugin, taskR, animation.delay(), animation.delay());
        }
        this.taskID = task.getTaskId();
    }

    public void removeAnimation() {
        if(taskID != -1) {
            Bukkit.getScheduler().cancelTask(taskID);
            taskID = -1;
        }
    }

    private WrappedDataWatcher getDefaultWatcher(@NotNull World world) {
        Entity entity = world.spawnEntity(new Location(world, 0, 256, 0), EntityType.ARMOR_STAND);
        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();
        entity.remove();
        return watcher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractLine<?> that = (AbstractLine<?>) o;
        return entityID == that.entityID && Objects.equals(obj, that.obj);
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

	public void setPoseData(EntityPoseData poseDatas) {
		this.poseData = poseDatas;
	}

	public Object getEntityType() {
		return entityType;
	}

	public void setEntityType(Object entityType) {
		this.entityType = entityType;
	}

	public AgeType getAge() {
		return age;
	}

	public void setAge(AgeType age) {
		this.age = age;
	}

	public int getEntNum() {
		return entNum;
	}

	public Boolean getVisible() {
		return visible;
	}

	public Boolean getEquipable() {
		return equipable;
	}


}
