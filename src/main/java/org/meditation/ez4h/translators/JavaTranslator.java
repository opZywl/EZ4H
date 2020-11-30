package org.meditation.ez4h.translators;

import com.github.steveice10.packetlib.packet.Packet;
import org.meditation.ez4h.bedrock.Client;

public interface JavaTranslator {
    void translate(Packet inPacket, Client client);
}
