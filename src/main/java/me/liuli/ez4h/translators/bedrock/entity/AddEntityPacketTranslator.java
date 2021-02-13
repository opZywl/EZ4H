package me.liuli.ez4h.translators.bedrock.entity;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.entity.type.GlobalEntityType;
import com.github.steveice10.mc.protocol.data.game.entity.type.MobType;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ObjectType;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.*;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.AddEntityPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.minecraft.data.entity.Entity;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtil;
import me.liuli.ez4h.utils.FileUtil;

import java.util.ArrayList;
import java.util.UUID;

public class AddEntityPacketTranslator implements BedrockTranslator {
    private final JSONObject entityMap;

    public AddEntityPacketTranslator() {
        entityMap = JSONObject.parseObject(FileUtil.getTextFromResource("resources/entity.json"));
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        AddEntityPacket packet = (AddEntityPacket) inPacket;
        Vector3f position = packet.getPosition();
        Entity entity = new Entity(position.getX(), position.getY(), position.getZ(), (int) packet.getRuntimeEntityId(), Entity.Type.ENTITY);
        client.getData().addEntity((int) packet.getRuntimeEntityId(), entity);

        JSONObject entityData = entityMap.getJSONObject(prepareEntityName(packet.getIdentifier()));
        if (entityData != null && !entityData.getString("type").equals("disabled")) {
            String name = entityData.getString("name").toUpperCase();
            switch (entityData.getString("type")) {
                case "mob": {
                    Vector3f rotation = packet.getRotation(),
                             motion = packet.getMotion();
                    client.sendPacket(new ServerSpawnMobPacket((int) packet.getRuntimeEntityId(), BedrockUtil.getUUID(packet.getRuntimeEntityId()), MobType.valueOf(name), position.getX(), position.getY() + 1.62, position.getZ(), rotation.getY(), rotation.getX(), rotation.getZ(), motion.getX(), motion.getY(), motion.getZ(), new EntityMetadata[0]));
                    EZ4H.getConverterManager().getMetadataConverter().convert(packet.getMetadata(), client, (int) packet.getRuntimeEntityId());
                    break;
                }
                case "object": {
                    Vector3f rotation = packet.getRotation(), motion = packet.getMotion();
                    client.sendPacket(new ServerSpawnObjectPacket((int) packet.getRuntimeEntityId(), BedrockUtil.getUUID(packet.getRuntimeEntityId()), ObjectType.valueOf(name), position.getX(), position.getY() + 1.62, position.getZ(), rotation.getY(), rotation.getX(), motion.getX(), motion.getY(), motion.getZ()));
                    EZ4H.getConverterManager().getMetadataConverter().convert(packet.getMetadata(), client, (int) packet.getRuntimeEntityId());
                    break;
                }
                case "global": {
                    client.sendPacket(new ServerSpawnGlobalEntityPacket((int) packet.getRuntimeEntityId(), GlobalEntityType.valueOf(name), position.getX(), position.getY() + 1.62, position.getZ()));
                    break;
                }
                case "xp_orb": {
                    client.sendPacket(new ServerSpawnExpOrbPacket((int) packet.getRuntimeEntityId(), position.getX(), position.getY() + 1.62, position.getZ(), 1));
                    break;
                }
            }
        } else {
            //warn at console
            EZ4H.getLogger().warn("Can't translate entity with name " + prepareEntityName(packet.getIdentifier()) + " for player " + client.getPlayer().getName());

            //add a fakeplayer to replace this unknown entity
            UUID uuid = BedrockUtil.getUUID(packet.getRuntimeEntityId());
            ArrayList<PlayerListEntry> playerListEntries = new ArrayList<>();
            playerListEntries.add(new PlayerListEntry(new GameProfile(uuid, BedrockUtil.lengthCutter(prepareEntityName(packet.getIdentifier()), 16)), GameMode.SURVIVAL, 0, new TextMessage(packet.getIdentifier())));
            PlayerListEntry[] playerListEntriesL = playerListEntries.toArray(new PlayerListEntry[0]);
            client.sendPacket(new ServerPlayerListEntryPacket(PlayerListEntryAction.ADD_PLAYER, playerListEntriesL));

            Vector3f rotation = packet.getRotation();
            client.sendPacket(new ServerSpawnPlayerPacket((int) packet.getRuntimeEntityId(), uuid, position.getX(), position.getY() + 1.62, position.getZ(), rotation.getY(), rotation.getX(), new EntityMetadata[0]));
            EZ4H.getConverterManager().getMetadataConverter().convert(packet.getMetadata(), client, (int) packet.getRuntimeEntityId());

            client.sendPacket(new ServerPlayerListEntryPacket(PlayerListEntryAction.REMOVE_PLAYER, playerListEntriesL));
        }
    }

    private String prepareEntityName(String name) {
        if (name.contains("minecraft:")) {
            return name.substring(10);
        }
        return name;
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return AddEntityPacket.class;
    }
}
