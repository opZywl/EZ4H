package me.liuli.ez4h.minecraft.data.world;

import com.github.steveice10.mc.protocol.data.game.window.WindowType;

public class ChestData {
    public String name;
    public WindowType type;
    public int id;

    public ChestData(int id, String name, WindowType type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }
}
