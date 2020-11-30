package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.EquipmentSlot;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEquipmentPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPlayerPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import org.meditation.ez4h.bedrock.BedrockUtils;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.converters.ItemConverter;
import org.meditation.ez4h.mcjava.cache.EntityInfo;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

import java.util.ArrayList;

public class AddPlayerPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        AddPlayerPacket packet=(AddPlayerPacket)inPacket;
        ArrayList<PlayerListEntry> playerListEntries=new ArrayList<>();
        playerListEntries.add(new PlayerListEntry(new GameProfile(packet.getUuid(), BedrockUtils.lengthCutter(packet.getMetadata().getString(EntityData.NAMETAG),16)), GameMode.SURVIVAL,0,new TextMessage(packet.getMetadata().getString(EntityData.NAMETAG))));
        PlayerListEntry[] playerListEntriesL=playerListEntries.toArray(new PlayerListEntry[0]);
        client.javaSession.send(new ServerPlayerListEntryPacket(PlayerListEntryAction.ADD_PLAYER, playerListEntriesL));

        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),new EntityInfo(position.getX(), position.getY(), position.getZ(), (int) packet.getRuntimeEntityId(),"player"));
        client.javaSession.send(new ServerSpawnPlayerPacket((int) packet.getRuntimeEntityId(),packet.getUuid(), position.getX(), position.getY(), position.getZ(),rotation.getY(),rotation.getX(),new EntityMetadata[0]));
        client.javaSession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.MAIN_HAND, ItemConverter.convertToJE(packet.getHand())));
    }
}
