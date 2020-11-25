package org.meditation.ez4h.bedrock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.*;
import com.github.steveice10.mc.protocol.data.game.entity.Effect;
import com.github.steveice10.mc.protocol.data.game.entity.EquipmentSlot;
import com.github.steveice10.mc.protocol.data.game.entity.attribute.Attribute;
import com.github.steveice10.mc.protocol.data.game.entity.attribute.AttributeType;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.MetadataType;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.Animation;
import com.github.steveice10.mc.protocol.data.game.entity.player.BlockBreakStage;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.entity.type.MobType;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.FallingBlockData;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.HangingDirection;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ObjectType;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ProjectileData;
import com.github.steveice10.mc.protocol.data.game.scoreboard.ObjectiveAction;
import com.github.steveice10.mc.protocol.data.game.scoreboard.ScoreType;
import com.github.steveice10.mc.protocol.data.game.scoreboard.ScoreboardPosition;
import com.github.steveice10.mc.protocol.data.game.world.Particle;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockChangeRecord;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import com.github.steveice10.mc.protocol.data.game.world.block.UpdatedTileType;
import com.github.steveice10.mc.protocol.data.game.world.notify.ClientNotification;
import com.github.steveice10.mc.protocol.data.game.world.notify.ClientNotificationValue;
import com.github.steveice10.mc.protocol.data.game.world.notify.RainStrengthValue;
import com.github.steveice10.mc.protocol.data.game.world.notify.ThunderStrengthValue;
import com.github.steveice10.mc.protocol.data.game.world.sound.Sound;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.*;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.*;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerSetExperiencePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.*;
import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerDisplayScoreboardPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerScoreboardObjectivePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerUpdateScorePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.*;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.bedrock.converters.*;
import org.meditation.ez4h.mcjava.SmoothWeather;
import org.meditation.ez4h.mcjava.cache.EntityInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BedrockHandler implements BedrockPacketHandler {
    private Client client;
    public BedrockHandler(Client client) {
        this.client=client;
    }

    public boolean handle(BedrockPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

//    public boolean handle(AdventureSettingsPacket packet) {
//        Variables.logger.warning(packet.getClass().getName());
//        return false;
//    }

    public boolean handle(AnimatePacket packet) {
        switch (packet.getAction()){
            case SWING_ARM:{
                client.JESession.send(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(),Animation.SWING_ARM));
                break;
            }
            case WAKE_UP:{
                client.JESession.send(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(),Animation.LEAVE_BED));
                break;
            }
            case CRITICAL_HIT:{
                client.JESession.send(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(),Animation.CRITICAL_HIT));
                break;
            }
            case MAGIC_CRITICAL_HIT:{
                client.JESession.send(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(),Animation.ENCHANTMENT_CRITICAL_HIT));
                break;
            }
        }
        return false;
    }

    public boolean handle(AnvilDamagePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

//    public boolean handle(AvailableEntityIdentifiersPacket packet) {
//        Variables.logger.warning(packet.getClass().getName());
//        return false;
//    }

    public boolean handle(BlockEntityDataPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(BlockPickRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(BookEditPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ClientCacheBlobStatusPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ClientCacheMissResponsePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ClientCacheStatusPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ClientToServerHandshakePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(CommandBlockUpdatePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(CommandRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(CompletedUsingItemPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ContainerClosePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(CraftingEventPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(EducationSettingsPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(EmotePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(EntityEventPacket packet) {
        switch (packet.getType()){
            case HURT:{
                client.JESession.send(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(),Animation.DAMAGE));
                break;
            }
        }
        return false;
    }

    public boolean handle(EntityFallPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(EntityPickRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(EventPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(InteractPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(InventoryContentPacket packet) {
        ItemData[] itemData=packet.getContents();
        switch (packet.getContainerId()){
            case 0:{
                for(int i=0;i<36;i++){
                    client.clientStat.inventory[ItemConverter.inventoryIndex(i,false)]=ItemConverter.convertToJE(itemData[i]);
                    client.clientStat.bedrockInventory[ItemConverter.inventoryIndex(i,false)]=itemData[i];
                }
                break;
            }
            case 119:{
                client.clientStat.inventory[45]=ItemConverter.convertToJE(itemData[0]);
                client.clientStat.bedrockInventory[45]=itemData[0];
                break;
            }
            case 120:{
                for(int i=0;i<4;i++){
                    client.clientStat.inventory[i+5]=ItemConverter.convertToJE(itemData[i]);
                    client.clientStat.bedrockInventory[i+5]=itemData[i];
                }
                break;
            }
        }
        client.JESession.send(new ServerWindowItemsPacket(0,client.clientStat.inventory));
        return false;
    }

    public boolean handle(InventorySlotPacket packet) {
        ItemStack itemStack=ItemConverter.convertToJE(packet.getItem());
        ServerSetSlotPacket serverSetSlotPacket;
        switch (packet.getContainerId()){
            case 0:{
                client.clientStat.inventory[ItemConverter.inventoryIndex(packet.getSlot(),false)]=itemStack;
                client.clientStat.bedrockInventory[ItemConverter.inventoryIndex(packet.getSlot(),false)]=packet.getItem();
                serverSetSlotPacket=new ServerSetSlotPacket(0,ItemConverter.inventoryIndex(packet.getSlot(),false),itemStack);
                break;
            }
            case 119:{
                client.clientStat.inventory[45]=itemStack;
                client.clientStat.bedrockInventory[45]=packet.getItem();
                serverSetSlotPacket=new ServerSetSlotPacket(0,45,itemStack);
                break;
            }
            case 120:{
                client.clientStat.inventory[packet.getSlot()+5]=itemStack;
                client.clientStat.bedrockInventory[packet.getSlot()+5]=packet.getItem();
                serverSetSlotPacket=new ServerSetSlotPacket(0,packet.getSlot()+5,itemStack);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + packet.getContainerId());
        }
        client.JESession.send(serverSetSlotPacket);
        return false;
    }

    public boolean handle(InventoryTransactionPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ItemFrameDropItemPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LabTablePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LecternUpdatePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LevelEventGenericPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LevelSoundEvent1Packet packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

//    public boolean handle(LevelSoundEventPacket packet) {
//        Variables.logger.warning(packet.toString());
//        return false;
//    }

    public boolean handle(LoginPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MapInfoRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MobArmorEquipmentPacket packet) {
        client.JESession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.HELMET,ItemConverter.convertToJE(packet.getHelmet())));
        client.JESession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.CHESTPLATE,ItemConverter.convertToJE(packet.getChestplate())));
        client.JESession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.LEGGINGS,ItemConverter.convertToJE(packet.getLeggings())));
        client.JESession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.BOOTS,ItemConverter.convertToJE(packet.getBoots())));
        return false;
    }

    public boolean handle(MobEquipmentPacket packet) {
        switch (packet.getContainerId()){
            case 0:{
                client.JESession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.MAIN_HAND,ItemConverter.convertToJE(packet.getItem())));
                break;
            }
            case 119:{
                client.JESession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.OFF_HAND,ItemConverter.convertToJE(packet.getItem())));
                break;
            }
        }
        return false;
    }

    public boolean handle(ModalFormResponsePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MoveEntityAbsolutePacket packet) {
        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        EntityInfo entityInfo=client.clientStat.entityInfoMap.get((int)packet.getRuntimeEntityId());
        if(entityInfo!=null) {
            double moveX = position.getX() - entityInfo.x, moveY = (position.getY() - 1.62) - entityInfo.y, moveZ = position.getZ() - entityInfo.z;
            if (BedrockUtils.calcDistance(moveX, moveY, moveZ) < 8) {
                client.JESession.send(new ServerEntityPositionRotationPacket((int) packet.getRuntimeEntityId(), moveX, moveY, moveZ, rotation.getY(), rotation.getX(), packet.isOnGround()));
            } else {
                client.JESession.send(new ServerEntityTeleportPacket((int) packet.getRuntimeEntityId(), position.getX(), position.getY() - 1.62, position.getZ(), rotation.getY(), rotation.getX(), packet.isOnGround()));
            }
            entityInfo.x = position.getX();
            entityInfo.y = (float) (position.getY() - 1.62);
            entityInfo.z = position.getZ();
            client.JESession.send(new ServerEntityHeadLookPacket((int)packet.getRuntimeEntityId(),rotation.getZ()));
        }
        return false;
    }

    public boolean handle(MovePlayerPacket packet) {
        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        if(packet.getRuntimeEntityId()==client.clientStat.entityId){
            if(client.clientStat.x!=position.getX()&&client.clientStat.y!=position.getY()&&client.clientStat.z!=position.getZ()) {
                client.clientStat.yaw=rotation.getX();
                client.clientStat.pitch=rotation.getY();
                client.clientStat.x=position.getX();
                client.clientStat.y=position.getY();
                client.clientStat.z=position.getZ();
                ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket = new ServerPlayerPositionRotationPacket(position.getX(), position.getY() - 1.61, position.getZ(), rotation.getY(),rotation.getX(), 1);
                client.JESession.send(serverPlayerPositionRotationPacket);
            }
        }else{
            EntityInfo entityInfo=client.clientStat.entityInfoMap.get((int)packet.getRuntimeEntityId());
            double moveX=position.getX()-entityInfo.x,moveY=(position.getY()-1.62)-entityInfo.y,moveZ=position.getZ()-entityInfo.z;
            if(BedrockUtils.calcDistance(moveX,moveY,moveZ)<8){
                client.JESession.send(new ServerEntityPositionRotationPacket((int) packet.getRuntimeEntityId(), moveX,moveY,moveZ,rotation.getY(),rotation.getX(), packet.isOnGround()));
            }else{
                client.JESession.send(new ServerEntityTeleportPacket((int) packet.getRuntimeEntityId(), position.getX(),position.getY()-1.62, position.getZ(),rotation.getY(),rotation.getX(), packet.isOnGround()));
            }
            entityInfo.x=position.getX();
            entityInfo.y= (float) (position.getY()-1.62);
            entityInfo.z=position.getZ();
            client.JESession.send(new ServerEntityHeadLookPacket((int)packet.getRuntimeEntityId(),rotation.getZ()));
        }
        return false;
    }

    public boolean handle(MultiplayerSettingsPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(NetworkStackLatencyPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PhotoTransferPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayerActionPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayerAuthInputPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayerHotbarPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayerInputPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayerSkinPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PurchaseReceiptPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(RequestChunkRadiusPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ResourcePackChunkRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ResourcePackClientResponsePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(RiderJumpPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ServerSettingsRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetDefaultGameTypePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetLocalPlayerAsInitializedPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetPlayerGameTypePacket packet) {
        client.JESession.send(new ServerNotifyClientPacket(ClientNotification.CHANGE_GAMEMODE,BedrockUtils.convertGameModeToJE(GameType.from(packet.getGamemode()))));
        return false;
    }

    public boolean handle(SubClientLoginPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AddBehaviorTreePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AddEntityPacket packet) {
        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),new EntityInfo(position.getX(), position.getY(), position.getZ(), (int) packet.getRuntimeEntityId()));
        System.out.println(packet);
        EntityConverter.convert(packet,client);
        return false;
    }

    public boolean handle(AddHangingEntityPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AddItemEntityPacket packet) {
        Vector3f position=packet.getPosition(),motion=packet.getMotion();
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),new EntityInfo(position.getX(), (float) (position.getY()+1.62), position.getZ(), (int) packet.getRuntimeEntityId()));
        EntityMetadata[] metadata=new EntityMetadata[1];
        metadata[0]=new EntityMetadata(6, MetadataType.ITEM, ItemConverter.convertToJE(packet.getItemInHand()));
        client.JESession.send(new ServerSpawnObjectPacket((int) packet.getRuntimeEntityId(),UUID.nameUUIDFromBytes(String.valueOf(packet.getRuntimeEntityId()).getBytes()), ObjectType.ITEM, position.getX(), position.getY(), position.getZ(),0,0, motion.getX(), motion.getY(), motion.getZ()));
        client.JESession.send(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(),metadata));
        return false;
    }

    public boolean handle(AddPaintingPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AddPlayerPacket packet) {
        ArrayList<PlayerListEntry> playerListEntries=new ArrayList<>();
        playerListEntries.add(new PlayerListEntry(new GameProfile(packet.getUuid(),BedrockUtils.lengthCutter(packet.getMetadata().getString(EntityData.NAMETAG),16)),GameMode.SURVIVAL,0,new TextMessage(packet.getMetadata().getString(EntityData.NAMETAG))));
        PlayerListEntry[] playerListEntriesL=playerListEntries.toArray(new PlayerListEntry[0]);
        client.JESession.send(new ServerPlayerListEntryPacket(PlayerListEntryAction.ADD_PLAYER, playerListEntriesL));

        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),new EntityInfo(position.getX(), position.getY(), position.getZ(), (int) packet.getRuntimeEntityId()));
        client.JESession.send(new ServerSpawnPlayerPacket((int) packet.getRuntimeEntityId(),packet.getUuid(), position.getX(), position.getY(), position.getZ(),rotation.getY(),rotation.getX(),new EntityMetadata[0]));
        client.JESession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.MAIN_HAND,ItemConverter.convertToJE(packet.getHand())));
        return false;
    }

//    public boolean handle(AvailableCommandsPacket packet) {
//        Variables.logger.warning(packet.getClass().getName());
//        return false;
//    }

    public boolean handle(BlockEventPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(BossEventPacket packet) {
        UUID uuid=UUID.nameUUIDFromBytes(String.valueOf(packet.getBossUniqueEntityId()).getBytes());
        switch (packet.getAction()){
            case CREATE:{
                client.JESession.send(new ServerBossBarPacket(uuid, BossBarAction.ADD,new TextMessage(packet.getTitle()),packet.getHealthPercentage(),BossBarColor.PURPLE,BossBarDivision.NONE,false,false));
                break;
            }
            case REMOVE:{
                client.JESession.send(new ServerBossBarPacket(uuid));
                break;
            }
            case UPDATE_PERCENTAGE:{
                client.JESession.send(new ServerBossBarPacket(uuid, BossBarAction.UPDATE_HEALTH,packet.getHealthPercentage()));
                break;
            }
            case UPDATE_NAME:{
                client.JESession.send(new ServerBossBarPacket(uuid, BossBarAction.UPDATE_HEALTH,new TextMessage(packet.getTitle())));
                break;
            }
        }
        return false;
    }

    public boolean handle(CameraPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ChangeDimensionPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ChunkRadiusUpdatedPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ClientboundMapItemDataPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(CommandOutputPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ContainerOpenPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ContainerSetDataPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(CraftingDataPacket packet) {
        List<Integer> recipes=new ArrayList<>();
        for(int i=1;i<1000;i++){
            recipes.add(i);
        }
        client.JESession.send(new ServerUnlockRecipesPacket(false,true,recipes,new ArrayList<>()));
        client.JESession.send(new ServerUnlockRecipesPacket(false,true,recipes, UnlockRecipesAction.ADD));
        return false;
    }

    public boolean handle(DisconnectPacket packet) {
        client.JESession.disconnect(packet.getKickMessage());
        return false;
    }

    public boolean handle(ExplodePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LevelChunkPacket packet) {
        try {
            client.JESession.send(LevelChunkPacketConverter.translate(packet));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean handle(GameRulesChangedPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(GuiDataPickItemPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(HurtArmorPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AutomationClientConnectPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LevelEventPacket packet) {
        //TODO:PARTICLE
        switch (packet.getType()){
            case BLOCK_START_BREAK:{
                Vector3f pos=packet.getPosition();
                client.JESession.send(new ServerBlockBreakAnimPacket(0,new Position((int)pos.getX(),(int)pos.getY(),(int)pos.getZ()), BlockBreakStage.STAGE_1));
                break;
            }
            case BLOCK_STOP_BREAK:{
                Vector3f pos=packet.getPosition();
                client.JESession.send(new ServerBlockBreakAnimPacket(0,new Position((int)pos.getX(),(int)pos.getY(),(int)pos.getZ()), BlockBreakStage.RESET));
            }
            case STOP_RAINING:{
                if(client.clientStat.rain){
                    SmoothWeather.changeWeather(0,false,client);
                    client.clientStat.rain=false;
                }
                break;
            }
            case START_RAINING:{
                if(!client.clientStat.rain){
                    SmoothWeather.changeWeather(1,false,client);
                    client.clientStat.rain=true;
                }
                if(client.clientStat.thunder){
                    SmoothWeather.changeWeather(0,true,client);
                    client.clientStat.thunder=false;
                }
                break;
            }
            case START_THUNDERSTORM:{
                if(!client.clientStat.thunder){
                    SmoothWeather.changeWeather(1,true,client);
                    client.clientStat.thunder=true;
                }
                break;
            }
            case STOP_THUNDERSTORM:{
                if(client.clientStat.thunder){
                    SmoothWeather.changeWeather(0,true,client);
                    client.clientStat.thunder=false;
                }
                break;
            }
        }
        return false;
    }

    public boolean handle(MapCreateLockedCopyPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MobEffectPacket packet) {
        Effect effect=BedrockUtils.effectConverter(packet.getEffectId());
        switch (packet.getEvent()){
            case ADD:
            case MODIFY: {
                switch (effect){
                    case SPEED:{
                        List<Attribute> attributes1=new ArrayList<>();
                        attributes1.add(new Attribute(AttributeType.GENERIC_MOVEMENT_SPEED,0.1+packet.getAmplifier()*0.02));
                        client.JESession.send(new ServerEntityPropertiesPacket((int) client.clientStat.entityId,attributes1));
                    }
                }
                client.JESession.send(new ServerEntityEffectPacket((int) packet.getRuntimeEntityId(),effect,packet.getAmplifier(),packet.getDuration(),true,packet.isParticles()));
                break;
            }
            case REMOVE:{
                switch (effect){
                    case SPEED:{
                        List<Attribute> attributes1=new ArrayList<>();
                        attributes1.add(new Attribute(AttributeType.GENERIC_MOVEMENT_SPEED,0.1));
                        client.JESession.send(new ServerEntityPropertiesPacket((int) client.clientStat.entityId,attributes1));
                    }
                }
                client.JESession.send(new ServerEntityRemoveEffectPacket((int) packet.getRuntimeEntityId(),effect));
                break;
            }
        }
        return false;
    }
    public boolean handle(ModalFormRequestPacket packet) {
        FormConverter.showForm(client,packet.getFormData(),packet.getFormId());
        return false;
    }

    public boolean handle(MoveEntityDeltaPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(NetworkSettingsPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(NpcRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(OnScreenTextureAnimationPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayerListPacket packet) {
        ArrayList<PlayerListEntry> playerListEntries=new ArrayList<>();
        for(PlayerListPacket.Entry entry:packet.getEntries()){
            playerListEntries.add(new PlayerListEntry(new GameProfile(entry.getUuid(),entry.getName()),GameMode.SURVIVAL,0,new TextMessage(entry.getName())));
        }
        PlayerListEntry[] playerListEntriesL=playerListEntries.toArray(new PlayerListEntry[0]);
        switch (packet.getAction()){
            case ADD:{
                client.JESession.send(new ServerPlayerListEntryPacket(PlayerListEntryAction.ADD_PLAYER, playerListEntriesL));
                break;
            }
            case REMOVE:{
                client.JESession.send(new ServerPlayerListEntryPacket(PlayerListEntryAction.REMOVE_PLAYER, playerListEntriesL));
                break;
            }
        }
        return false;
    }

    public boolean handle(PlaySoundPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayStatusPacket packet) {
        if(!packet.getStatus().equals(PlayStatusPacket.Status.LOGIN_SUCCESS)&&client.clientStat.onLogin){
            client.JESession.disconnect("Cannot Connect to the target server!\n"+packet.getStatus().toString());
        }
        return false;
    }

    public boolean handle(RemoveEntityPacket packet) {
        int[] entityIds=new int[1];
        entityIds[0]= (int) packet.getUniqueEntityId();
        client.clientStat.entityInfoMap.remove( (int) packet.getUniqueEntityId());
        client.JESession.send(new ServerEntityDestroyPacket(entityIds));
        return false;
    }

    public boolean handle(RemoveObjectivePacket packet) {
        String displayName=client.clientStat.scoreboards.get(packet.getObjectiveId());
        if(displayName!=null){
            client.JESession.send(new ServerScoreboardObjectivePacket(packet.getObjectiveId()));
        }
        return false;
    }

    public boolean handle(ResourcePackChunkDataPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ResourcePackDataInfoPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ResourcePacksInfoPacket packet) {
        ResourcePackClientResponsePacket resourcePackClientResponsePacket=new ResourcePackClientResponsePacket();
        resourcePackClientResponsePacket.setStatus(ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS);
        client.session.sendPacket(resourcePackClientResponsePacket);
        return false;
    }

    public boolean handle(ResourcePackStackPacket packet) {
        ResourcePackClientResponsePacket resourcePackClientResponsePacket=new ResourcePackClientResponsePacket();
        resourcePackClientResponsePacket.setStatus(ResourcePackClientResponsePacket.Status.COMPLETED);
        client.session.sendPacket(resourcePackClientResponsePacket);
        client.clientStat.onLogin=false;
        return false;
    }

    public boolean handle(RespawnPacket packet) {
        switch (packet.getState()){
            case SERVER_SEARCHING:{
                client.JESession.send(new ServerPlayerHealthPacket(0,client.clientStat.food,0));
                break;
            }
            case SERVER_READY:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.RESPAWN);
                playerActionPacket.setFace(-1);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setBlockPosition(Vector3i.from(0,0,0));
                client.session.sendPacket(playerActionPacket);
                client.JESession.send(new ServerRespawnPacket(client.clientStat.dimension,client.clientStat.difficulty,client.clientStat.gameMode,WorldType.CUSTOMIZED));
                MovePlayerPacket movePlayerPacket=new MovePlayerPacket();
                movePlayerPacket.setMode(MovePlayerPacket.Mode.RESPAWN);
                movePlayerPacket.setOnGround(true);
                movePlayerPacket.setRuntimeEntityId(client.clientStat.entityId);
                movePlayerPacket.setRidingRuntimeEntityId(0);
                Vector3f position=packet.getPosition();
                movePlayerPacket.setPosition(Vector3f.from(position.getX(),position.getY(),position.getZ()));
                movePlayerPacket.setRotation(Vector3f.from(0, 0, 0));
                client.session.sendPacket(movePlayerPacket);
                break;
            }
        }
        return false;
    }

    public boolean handle(ScriptCustomEventPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ServerSettingsResponsePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ServerToClientHandshakePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

//    public boolean handle(SetCommandsEnabledPacket packet) {
//        Variables.logger.warning(packet.getClass().getName());
//        return false;
//    }

    public boolean handle(SetDifficultyPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetDisplayObjectivePacket packet) {
        switch (packet.getDisplaySlot()){
            case "sidebar":{
                String name=BedrockUtils.lengthCutter(packet.getDisplayName(),32);
                client.clientStat.scoreboardOrder=packet.getSortOrder();
                client.clientStat.scoreboards.put(packet.getObjectiveId(),name);
                client.JESession.send(new ServerScoreboardObjectivePacket(packet.getObjectiveId(), ObjectiveAction.ADD,name, ScoreType.HEARTS));
                client.JESession.send(new ServerDisplayScoreboardPacket(ScoreboardPosition.SIDEBAR, packet.getObjectiveId()));
                break;
            }
        }
        return false;
    }

//    public boolean handle(SetEntityDataPacket packet) {
//        return false;
//    }

    public boolean handle(SetEntityLinkPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetEntityMotionPacket packet) {
        Vector3f motion=packet.getMotion();
        client.JESession.send(new ServerEntityVelocityPacket((int) packet.getRuntimeEntityId(), motion.getX(), motion.getY(), motion.getZ()));
        return false;
    }

    public boolean handle(SetHealthPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetLastHurtByPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetScoreboardIdentityPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetScorePacket packet) {
        List<ScoreInfo> infos=packet.getInfos();
        for(ScoreInfo scoreInfo:infos){
            switch (scoreInfo.getType()){
                case INVALID:{
                    ScoreInfo scoreInfo1=client.clientStat.scoreboardBars.get(scoreInfo.getScoreboardId());
                    client.JESession.send(new ServerUpdateScorePacket(scoreInfo1.getName(),scoreInfo1.getObjectiveId()));
                    break;
                }
                default:{
                    client.clientStat.scoreboardBars.put(scoreInfo.getScoreboardId(),scoreInfo);
                    int score;
                    if(client.clientStat.scoreboardOrder==0){
                        score=(int) (infos.size()-scoreInfo.getScoreboardId());
                    }else{
                        score=(int) scoreInfo.getScoreboardId();
                    }
                    client.JESession.send(new ServerUpdateScorePacket(scoreInfo.getName(),scoreInfo.getObjectiveId(), score));
                    break;
                }
            }
        }
        return false;
    }

    public boolean handle(SetSpawnPositionPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetTimePacket packet) {
        client.JESession.send(new ServerUpdateTimePacket(0,packet.getTime()));
        return false;
    }

    public boolean handle(SettingsCommandPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetTitlePacket packet) {
        switch (packet.getType()){
            case TITLE:{
                client.JESession.send(new ServerTitlePacket(packet.getText(),false));
                break;
            }
            case SUBTITLE:{
                client.JESession.send(new ServerTitlePacket(packet.getText(),true));
            }
            case RESET:{
                client.JESession.send(new ServerTitlePacket("",true));
                client.JESession.send(new ServerTitlePacket("",false));
            }
        }
        return false;
    }

    public boolean handle(ShowCreditsPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ShowProfilePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ShowStoreOfferPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SimpleEventPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SpawnExperienceOrbPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SpawnParticleEffectPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(StartGamePacket packet) {
        BlockConverter.loadRuntime(packet.getBlockPalette());
        GameMode gamemode=BedrockUtils.convertGameModeToJE(packet.getPlayerGameType());
        ServerJoinGamePacket serverJoinGamePacket=new ServerJoinGamePacket(
                (int) packet.getUniqueEntityId(),
                false,
                gamemode,
                packet.getDimensionId(),
                BedrockUtils.convertDifficultyToJE(packet.getDifficulty()),
                Ping.ping.getPlayerInfo().getMaxPlayers(),
                WorldType.CUSTOMIZED,
                true
        );
        List<Attribute> attributes1=new ArrayList<>();
        attributes1.add(new Attribute(AttributeType.GENERIC_ATTACK_SPEED,0));
        ServerEntityPropertiesPacket serverEntityPropertiesPacket=new ServerEntityPropertiesPacket((int) client.clientStat.entityId,attributes1);
        ServerPlayerListDataPacket serverPlayerListDataPacket=new ServerPlayerListDataPacket(Ping.ping.getDescription(),new TextMessage(""));
        Vector3f position=packet.getPlayerPosition();
        ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket=new ServerPlayerPositionRotationPacket(position.getX(), position.getY(), position.getZ(), 90,90,1);
        if(client.clientStat.jLogined){
            client.JESession.send(serverJoinGamePacket);
            client.JESession.send(serverPlayerPositionRotationPacket);
            client.JESession.send(serverPlayerListDataPacket);
            client.JESession.send(serverEntityPropertiesPacket);
        }else {
            client.clientStat.jPacketMap.put("ServerPlayerPositionRotation",serverPlayerPositionRotationPacket);
            client.clientStat.jPacketMap.put("ServerJoinGame",serverJoinGamePacket);
            client.clientStat.jPacketMap.put("ServerEntityProperties",serverEntityPropertiesPacket);
            client.clientStat.jPacketMap.put("ServerPlayerListData",serverPlayerListDataPacket);
        }
        client.clientStat.entityId=packet.getRuntimeEntityId();
        client.clientStat.dimension=packet.getDimensionId();
        client.clientStat.difficulty=BedrockUtils.convertDifficultyToJE(packet.getDifficulty());
        client.clientStat.gameMode=gamemode;
        return false;
    }

    public boolean handle(StopSoundPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(StructureBlockUpdatePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(StructureTemplateDataRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(StructureTemplateDataResponsePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(TakeItemEntityPacket packet) {
        int[] entityIds=new int[1];
        entityIds[0]= (int) packet.getItemRuntimeEntityId();
        client.clientStat.entityInfoMap.remove( (int) packet.getItemRuntimeEntityId());
        client.JESession.send(new ServerEntityDestroyPacket(entityIds));
        return false;
    }

    public boolean handle(TextPacket packet) {
        switch (packet.getType()){
            case TIP:
            case POPUP: {
                client.JESession.send(new ServerChatPacket(packet.getMessage(), MessageType.NOTIFICATION));
                break;
            }
            case SYSTEM:{
                client.JESession.send(new ServerChatPacket(packet.getMessage(), MessageType.SYSTEM));
                break;
            }
            default:{
                client.sendMessage(packet.getMessage());
                break;
            }
        }
        return false;
    }

    public boolean handle(TickSyncPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(TransferPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(UpdateAttributesPacket packet) {
        if(packet.getRuntimeEntityId()==client.clientStat.entityId) {
            List<AttributeData> attributes = packet.getAttributes();
            for (AttributeData attribute : attributes) {
                switch (attribute.getName()) {
                    case "minecraft:health": {
                        List<Attribute> attributes1 = new ArrayList<>();
                        attributes1.add(new Attribute(AttributeType.GENERIC_MAX_HEALTH, attribute.getMaximum()));
                        client.JESession.send(new ServerEntityPropertiesPacket((int) client.clientStat.entityId, attributes1));
                        client.clientStat.health = attribute.getValue();
                        break;
                    }
                    case "minecraft:player.experience": {
                        client.clientStat.exp = attribute.getValue() / attribute.getMaximum();
                        break;
                    }
                    case "minecraft:player.hunger": {
                        client.clientStat.food = (int) attribute.getValue();
                        break;
                    }
                    case "minecraft:player.level": {
                        client.clientStat.expLevel = attribute.getValue();
                        break;
                    }
                }
            }
            client.JESession.send(new ServerPlayerHealthPacket(client.clientStat.health, client.clientStat.food, 0));
            client.JESession.send(new ServerPlayerSetExperiencePacket(client.clientStat.exp, (int) client.clientStat.expLevel, 0));
        }
        return false;
    }

    public boolean handle(UpdateBlockPacket packet) {
        if (packet.getDataLayer() == 0) {//TunnelMC says we cant use dataLayer 1
            Vector3i pos=packet.getBlockPosition();
            BlockState blockState = new BlockState(BlockConverter.getJavaIdByJavaName(BlockConverter.getJavaNameByBedrockName(BlockConverter.getBedrockNameByRuntime(packet.getRuntimeId()))),0);
            client.JESession.send(new ServerBlockChangePacket(new BlockChangeRecord(new Position(pos.getX(), pos.getY(), pos.getZ()),blockState)));
        }
        return false;
    }

    public boolean handle(UpdateBlockPropertiesPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(UpdateBlockSyncedPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(UpdateEquipPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(UpdateSoftEnumPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(UpdateTradePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

//    public boolean handle(BiomeDefinitionListPacket packet) {
//        Variables.logger.warning(packet.getClass().getName());
//        return false;
//    }

    public boolean handle(LevelSoundEvent2Packet packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

//    public boolean handle(NetworkChunkPublisherUpdatePacket packet) {
//        Variables.logger.warning(packet.getClass().getName());
//        return false;
//    }

    public boolean handle(VideoStreamConnectPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(CodeBuilderPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(EmoteListPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ItemStackRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ItemStackResponsePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayerArmorDamagePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayerEnchantOptionsPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

//    public boolean handle(CreativeContentPacket packet) {
//        Variables.logger.warning(packet.getClass().getName());
//        return false;
//    }

    public boolean handle(UpdatePlayerGameTypePacket packet) {
        client.JESession.send(new ServerNotifyClientPacket(ClientNotification.CHANGE_GAMEMODE,BedrockUtils.convertGameModeToJE(packet.getGameType())));
        return false;
    }

    public boolean handle(PositionTrackingDBServerBroadcastPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PositionTrackingDBClientRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PacketViolationWarningPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(DebugInfoPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AnimateEntityPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(CameraShakePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(CorrectPlayerMovePredictionPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(PlayerFogPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }
}

