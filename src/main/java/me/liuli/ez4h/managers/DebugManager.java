package me.liuli.ez4h.managers;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

public class DebugManager {
    @Getter
    private final boolean allPackets;
    @Getter
    private final boolean unknownPackets;
    @Getter
    private final boolean outPackets;
    @Getter
    private final boolean other;

    public DebugManager(JSONObject data) {
        this.allPackets = data.getBoolean("all-packet");
        this.unknownPackets = data.getBoolean("unknown-packet");
        this.outPackets = data.getBoolean("out-packet");
        this.other = data.getBoolean("other");
    }

    public boolean enableDebug() {
        return allPackets || unknownPackets || outPackets || other;
    }
}
