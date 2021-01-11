package me.liuli.ez4h.translators.bedrockTranslators;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.ModalFormRequestPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.converters.FormConverter;

public class ModalFormRequestPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        ModalFormRequestPacket packet=(ModalFormRequestPacket)inPacket;
        FormConverter.showForm(client,packet.getFormData(),packet.getFormId());
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return ModalFormRequestPacket.class;
    }
}
