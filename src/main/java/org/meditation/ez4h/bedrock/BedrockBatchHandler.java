package org.meditation.ez4h.bedrock;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.handler.BatchHandler;
import io.netty.buffer.ByteBuf;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.translators.BedrockTranslatorManager;

import java.util.Collection;

public class BedrockBatchHandler implements BatchHandler {
    private Client client;
    public BedrockBatchHandler(Client client) {
        this.client=client;
    }
    @Override
    public void handle(BedrockSession bedrockSession, ByteBuf byteBuf, Collection<BedrockPacket> collection) {
        for (BedrockPacket packet : collection) {
            if(Variables.debug==2){
                Variables.logger.warning("Bedrock > "+packet.toString());
            }
            try {
                BedrockTranslatorManager.translatePacket(packet,client);
            }catch (Throwable t){
                Variables.logger.throwing("ERROR","TRANSLATE BEDROCK PACKET ERROR:"+packet.toString(),t);
            }
        }
    }
}
