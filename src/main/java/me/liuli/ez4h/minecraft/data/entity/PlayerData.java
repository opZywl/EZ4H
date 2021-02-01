package me.liuli.ez4h.minecraft.data.entity;

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import lombok.Getter;
import lombok.Setter;
import me.liuli.ez4h.minecraft.data.world.Location;

import java.util.UUID;

public class PlayerData extends Location {
    @Getter
    @Setter
    private long entityId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private UUID uuid;
    @Getter
    @Setter
    private String xuid;
    @Getter
    @Setter
    private int dimension;
    @Getter
    @Setter
    private Difficulty difficulty;
    @Getter
    @Setter
    private GameMode gameMode;
    @Getter
    @Setter
    private float health=20;
    @Getter
    @Setter
    private float exp=0;
    @Getter
    @Setter
    private float expLevel=0;
    @Getter
    @Setter
    private int food=20;
    @Getter
    @Setter
    private boolean flyable=false;
    @Getter
    @Setter
    private float walkSpeed=0.7F;

    public PlayerData() {
        super();
    }
}
