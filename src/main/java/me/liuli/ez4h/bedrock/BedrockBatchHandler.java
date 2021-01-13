package me.liuli.ez4h.bedrock;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.handler.BatchHandler;
import io.netty.buffer.ByteBuf;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.Variables;
import me.liuli.ez4h.managers.BedrockTranslatorManager;

import java.util.Collection;

public class BedrockBatchHandler implements BatchHandler {
    private Client client;
    public BedrockBatchHandler(Client client) {
        this.client=client;
    }
    @Override
    public void handle(BedrockSession bedrockSession, ByteBuf byteBuf, Collection<BedrockPacket> collection) {
        for (BedrockPacket packet : collection) {
            if(EZ4H.getConfigManager().getDebugLevel()==2){
                Variables.logger.debug("Bedrock > "+packet.toString());
            }
            try {
                BedrockTranslatorManager.translatePacket(packet,client);
            }catch (Throwable t){
                Variables.logger.throwing(t);
            }
        }
    }
}
