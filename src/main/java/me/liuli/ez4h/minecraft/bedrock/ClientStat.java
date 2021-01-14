package me.liuli.ez4h.minecraft.bedrock;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import me.liuli.ez4h.translators.cache.EntityInfo;
import me.liuli.ez4h.translators.cache.Form;

import java.util.HashMap;
import java.util.Map;

public class ClientStat {
    public long entityId;
    public int dimension;
    public float x,y,z,yaw,pitch;
    public int slot;
    public Difficulty difficulty;
    public GameMode gameMode;
    public boolean onLogin=false;
    public boolean jLogined=false;
    public boolean rain=false,thunder=false;
    public Map<String, Packet> jPacketMap=new HashMap<>();
    public Map<String, String> scoreboards=new HashMap<>();
    public Map<Long, ScoreInfo> scoreboardBars=new HashMap<>();
    public int scoreboardOrder;
    public float health=0;
    public float exp=0;
    public float expLevel=0;
    public int food=20;
    public ItemStack[] inventory=new ItemStack[46];
    public ItemData[] bedrockInventory=new ItemData[46];
    public Map<Integer, EntityInfo> entityInfoMap=new HashMap<>();
    public Form formData=null;
}
