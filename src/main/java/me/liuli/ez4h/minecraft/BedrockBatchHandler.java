package me.liuli.ez4h.minecraft;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.handler.BatchHandler;
import io.netty.buffer.ByteBuf;
import me.liuli.ez4h.EZ4H;

import java.util.Collection;

public class BedrockBatchHandler implements BatchHandler {
    private final Client client;
    public BedrockBatchHandler(Client client) {
        this.client=client;
    }
    @Override
    public void handle(BedrockSession bedrockSession, ByteBuf byteBuf, Collection<BedrockPacket> collection) {
        for (BedrockPacket packet : collection) {
            client.addPacket(packet);
        }
    }
}
