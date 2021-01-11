package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.world.sound.SoundCategory;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerPlayBuiltinSoundPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.PlaySoundPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.converters.SoundConverter;

import java.util.Locale;

public class PlaySoundPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        PlaySoundPacket packet=(PlaySoundPacket)inPacket;
        Vector3f pos=packet.getPosition();
        SoundCategory category;
        try {
            category=SoundCategory.valueOf(packet.getSound().split("\\.")[0].toUpperCase());
        } catch (Exception e) {
            category=SoundCategory.AMBIENT;
        }
        client.sendPacket(new ServerPlayBuiltinSoundPacket(SoundConverter.convert(packet.getSound()), category, pos.getX(), pos.getY(), pos.getZ(), packet.getVolume(), packet.getPitch()));
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return PlaySoundPacket.class;
    }
}
