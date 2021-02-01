package me.liuli.ez4h.minecraft.data.play;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityTeleportPacket;
import com.nukkitx.math.vector.Vector3f;
import lombok.Getter;
import lombok.Setter;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.minecraft.data.entity.Entity;
import me.liuli.ez4h.minecraft.data.entity.Inventory;
import me.liuli.ez4h.minecraft.data.world.ChestData;
import me.liuli.ez4h.minecraft.data.world.Weather;
import me.liuli.ez4h.utils.BedrockUtils;

import java.util.HashMap;
import java.util.Map;

public class ClientData {
    private final Client client;

    @Getter
    private final Weather weather;
    @Getter
    private final Inventory inventory;
    private final Map<Integer, Entity> entitys = new HashMap<>();
    @Getter
    @Setter
    private int scoreSortorder = 0;
    @Getter
    @Setter
    private Form form = null;
    @Getter
    @Setter
    private int itemInHand = 0;
    @Getter
    @Setter
    private ChestData queueChest = null;
    @Getter
    @Setter
    private boolean jumpTiming = true;

    public ClientData(Client client) {
        this.client = client;
        this.weather = new Weather(client);
        this.inventory = new Inventory(client);
    }

    public void addEntity(int id, Entity entity) {
        entitys.put(id, entity);
    }

    public Entity removeEntity(int id) {
        return entitys.remove(id);
    }

    public Entity getEntity(int id) {
        return entitys.get(id);
    }

    public Entity getEntity(long id) {
        return getEntity((int) id);
    }

    public void moveEntity(Entity entity, Vector3f position, Vector3f rotation, boolean onGround) {
        if (entity == null) return;
        double moveX = position.getX() - entity.getX(), moveY = (position.getY() - 1.62) - entity.getY(), moveZ = position.getZ() - entity.getZ();
        if (BedrockUtils.calcDistance(position.getX(), position.getY() - 1.62, position.getZ(), entity.getX(), entity.getY(), entity.getZ()) < 8) {
            client.sendPacket(new ServerEntityPositionRotationPacket(entity.getId(), moveX, moveY, moveZ, rotation.getY(), rotation.getX(), onGround));
        } else {
            client.sendPacket(new ServerEntityTeleportPacket(entity.getId(), position.getX(), position.getY(), position.getZ(), rotation.getY(), rotation.getX(), onGround));
        }
        entity.setX(position.getX());
        entity.setY(position.getY() - 1.62F);
        entity.setZ(position.getZ());
    }
}
