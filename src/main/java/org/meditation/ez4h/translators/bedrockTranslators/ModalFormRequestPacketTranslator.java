package org.meditation.ez4h.translators.bedrockTranslators;

import com.nukkitx.protocol.bedrock.packet.ModalFormRequestPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.converters.FormConverter;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class ModalFormRequestPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        ModalFormRequestPacket packet=(ModalFormRequestPacket)inPacket;
        FormConverter.showForm(client,packet.getFormData(),packet.getFormId());
    }
}
