package me.liuli.ez4h.minecraft.data.entity;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import lombok.Getter;
import lombok.Setter;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;

import java.util.ArrayList;

public class Inventory {
    private final Client client;
    @Getter
    private final ItemStack[] javaInventory=new ItemStack[46];
    @Getter
    private final ItemData[] bedrockInventory=new ItemData[46];
    @Getter
    @Setter
    private int handSlot=0;
    @Getter
    @Setter
    private boolean open=false;

    public ItemData getBedrockItemInHand(){
        return bedrockInventory[36+handSlot];
    }
    public ItemStack getJavaItemInHand(){
        return javaInventory[36+handSlot];
    }
    public void updateHand(int hand){
        this.handSlot=hand;
    }
    public ItemStack getJavaItem(int slot){
        return javaInventory[slot];
    }
    public ItemData getBedrockItem(int slot){
        return bedrockInventory[slot];
    }
    public void updateItem(ItemData itemData, int slot,boolean packet){
        ItemStack itemStack=EZ4H.getConverterManager().getItemConverter().convertToJE(itemData);
        javaInventory[slot]=itemStack;
        bedrockInventory[slot]=itemData;
        if(packet) {
            client.sendPacket(new ServerSetSlotPacket(0, slot, itemStack));
        }
    }

    public Inventory(Client client){
        this.client=client;
    }
}
