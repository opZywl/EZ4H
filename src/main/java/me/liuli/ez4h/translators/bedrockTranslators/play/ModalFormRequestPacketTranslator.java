package me.liuli.ez4h.translators.bedrockTranslators.play;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.ModalFormRequestPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.converters.FormConverter;

public class ModalFormRequestPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        ModalFormRequestPacket packet=(ModalFormRequestPacket)inPacket;
        EZ4H.getConverterManager().getFormConverter().showForm(client,packet.getFormData(),packet.getFormId());
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return ModalFormRequestPacket.class;
    }
}
