package me.liuli.ez4h.translators.bedrock.connect;

import com.alibaba.fastjson.JSONObject;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

import javax.crypto.SecretKey;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;

public class ServerToClientHandshakePacketTranslator  implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        ServerToClientHandshakePacket packet=(ServerToClientHandshakePacket)inPacket;
        try {
            String[] jwtSplit = packet.getJwt().split("\\.");
            String header = new String(Base64.getDecoder().decode(jwtSplit[0]));
            JSONObject headerObject = JSONObject.parseObject(header);

            String payload = new String(Base64.getDecoder().decode(jwtSplit[1]));
            JSONObject payloadObject = JSONObject.parseObject(payload);

            ECPublicKey serverKey = EncryptionUtils.generateKey(headerObject.getString("x5u"));
            SecretKey key = EncryptionUtils.getSecretKey(client.getPrivateKey(),serverKey, Base64.getDecoder().decode(payloadObject.getString("salt")));
            client.getBedrockSession().enableEncryption(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClientToServerHandshakePacket clientToServerHandshake = new ClientToServerHandshakePacket();
        client.sendPacket(clientToServerHandshake);
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return ServerToClientHandshakePacket.class;
    }
}

