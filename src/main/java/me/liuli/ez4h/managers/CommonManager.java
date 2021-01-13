package me.liuli.ez4h.managers;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.v422.Bedrock_v422;
import lombok.Getter;

public class CommonManager {
    @Getter
    private final int bedrockProtocolVersion=422;
    @Getter
    private final BedrockPacketCodec bedrockCodec=Bedrock_v422.V422_CODEC;
}
