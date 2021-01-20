package me.liuli.ez4h.translators.bedrockTranslators.play;

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerAbilitiesPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.AdventureSetting;
import com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class AdventureSettingsPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        AdventureSettingsPacket packet=(AdventureSettingsPacket)inPacket;
        boolean flyable=false;
        if(packet.getSettings().contains(AdventureSetting.MAY_FLY)){
            flyable=true;
        }
        if(client.clientStat.canFly!=flyable){
            client.clientStat.canFly=flyable;
            boolean creativeMode=false;
            if(client.clientStat.gameMode.equals(GameMode.CREATIVE)){
                creativeMode=true;
            }
            client.sendPacket(new ServerPlayerAbilitiesPacket(false,flyable,flyable,creativeMode,client.clientStat.walkSpeed,client.clientStat.walkSpeed));
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return AdventureSettingsPacket.class;
    }
}
