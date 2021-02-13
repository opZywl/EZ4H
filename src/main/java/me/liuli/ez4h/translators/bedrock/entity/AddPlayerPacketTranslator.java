package me.liuli.ez4h.translators.bedrock.entity;

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
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.minecraft.data.entity.Entity;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtil;

public class AddPlayerPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        AddPlayerPacket packet = (AddPlayerPacket) inPacket;

        GameProfile gameProfile = new GameProfile(packet.getUuid(), BedrockUtil.lengthCutter(packet.getUsername(), 16));
        client.sendPacket(new ServerPlayerListEntryPacket(PlayerListEntryAction.ADD_PLAYER, new PlayerListEntry[]{new PlayerListEntry(gameProfile, GameMode.SURVIVAL, 0, new TextMessage(BedrockUtil.lengthCutter(packet.getMetadata().getString(EntityData.NAMETAG), 16)))}));

        Vector3f position = packet.getPosition(), rotation = packet.getRotation();
        client.getData().addEntity((int) packet.getRuntimeEntityId(), new Entity(position.getX(), position.getY(), position.getZ(), (int) packet.getRuntimeEntityId(), Entity.Type.PLAYER));
        client.sendPacket(new ServerSpawnPlayerPacket((int) packet.getRuntimeEntityId(), packet.getUuid(), position.getX(), position.getY(), position.getZ(), rotation.getY(), rotation.getX(), new EntityMetadata[]{}));
        client.sendPacket(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.MAIN_HAND, EZ4H.getConverterManager().getItemConverter().convertToJE(packet.getHand())));
        EZ4H.getConverterManager().getMetadataConverter().convert(packet.getMetadata(), client, (int) packet.getRuntimeEntityId());
    }


    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return AddPlayerPacket.class;
    }
}
