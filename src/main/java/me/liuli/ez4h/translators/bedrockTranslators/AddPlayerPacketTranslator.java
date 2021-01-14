package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.EquipmentSlot;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEquipmentPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPlayerPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.cache.EntityInfo;
import me.liuli.ez4h.translators.converters.ItemConverter;
import me.liuli.ez4h.translators.converters.MetadataConverter;
import me.liuli.ez4h.utils.BedrockUtils;

import java.util.ArrayList;

public class AddPlayerPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        AddPlayerPacket packet=(AddPlayerPacket)inPacket;
        ArrayList<PlayerListEntry> playerListEntries=new ArrayList<>();
        playerListEntries.add(new PlayerListEntry(new GameProfile(packet.getUuid(), BedrockUtils.lengthCutter(packet.getMetadata().getString(EntityData.NAMETAG),16)), GameMode.SURVIVAL,0,new TextMessage(packet.getMetadata().getString(EntityData.NAMETAG))));
        PlayerListEntry[] playerListEntriesL=playerListEntries.toArray(new PlayerListEntry[0]);
        client.sendPacket(new ServerPlayerListEntryPacket(PlayerListEntryAction.ADD_PLAYER, playerListEntriesL));

        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),new EntityInfo(position.getX(), position.getY(), position.getZ(), (int) packet.getRuntimeEntityId(),"player"));
        client.sendPacket(new ServerSpawnPlayerPacket((int) packet.getRuntimeEntityId(),packet.getUuid(), position.getX(), position.getY(), position.getZ(),rotation.getY(),rotation.getX(),new EntityMetadata[0]));
        client.sendPacket(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.MAIN_HAND, EZ4H.getConverterManager().getItemConverter().convertToJE(packet.getHand())));
        client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(), new EntityMetadata[0]));
        EZ4H.getConverterManager().getMetadataConverter().convert(packet.getMetadata(),client, (int) packet.getRuntimeEntityId());
    }


    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return AddPlayerPacket.class;
    }
}
