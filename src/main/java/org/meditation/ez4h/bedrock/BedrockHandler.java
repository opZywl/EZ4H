package org.meditation.ez4h.bedrock;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListDataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityVelocityPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerSetExperiencePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUpdateTimePacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.mcjava.BroadcastPacket;

import java.util.ArrayList;
import java.util.List;

public class BedrockHandler implements BedrockPacketHandler {
    private Client client;
    public BedrockHandler(Client client) {
        this.client=client;
    }

//    public boolean handle(AdventureSettingsPacket packet) {
//        Variables.logger.warning(packet.getClass().getName());
//        return false;
//    }

    public boolean handle(AnimatePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
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
                    ItemData itemData1=itemData[i];
                    client.clientStat.inventory[Variables.JEInventory_0.getInteger(i+"")]=new ItemStack(itemData1.getId(),itemData1.getCount(),itemData1.getDamage());
                }
                break;
            }
            case 119:{
                ItemData itemData1=itemData[0];
                client.clientStat.inventory[45]=new ItemStack(itemData1.getId(),itemData1.getCount(),itemData1.getDamage());
                break;
            }
            case 120:{
                for(int i=0;i<4;i++){
                    ItemData itemData1=itemData[i];
                    client.clientStat.inventory[i+5]=new ItemStack(itemData1.getId(),itemData1.getCount(),itemData1.getDamage());
                }
                break;
            }
        }
        client.JESession.send(new ServerWindowItemsPacket(0,client.clientStat.inventory));
        return false;
    }

    public boolean handle(InventorySlotPacket packet) {
        ItemData itemData=packet.getItem();
        ItemStack itemStack=new ItemStack(itemData.getId(),itemData.getCount(), itemData.getDamage());
        ServerSetSlotPacket serverSetSlotPacket;
        switch (packet.getContainerId()){
            case 0:{
                client.clientStat.inventory[Variables.JEInventory_0.getInteger(packet.getSlot()+"")]=itemStack;
                serverSetSlotPacket=new ServerSetSlotPacket(0,Variables.JEInventory_0.getInteger(packet.getSlot()+""),itemStack);
                break;
            }
            case 119:{
                client.clientStat.inventory[45]=itemStack;
                serverSetSlotPacket=new ServerSetSlotPacket(0,45,itemStack);
                break;
            }
            case 120:{
                client.clientStat.inventory[packet.getSlot()+5]=itemStack;
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

    public boolean handle(LevelSoundEventPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LoginPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MapInfoRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MobArmorEquipmentPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MobEquipmentPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ModalFormResponsePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MoveEntityAbsolutePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MovePlayerPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AddHangingEntityPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AddItemEntityPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AddPaintingPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AddPlayerPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
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

//    public boolean handle(CraftingDataPacket packet) {
//        Variables.logger.warning(packet.getClass().getName());
//        return false;
//    }

    public boolean handle(DisconnectPacket packet) {
        client.JESession.disconnect(packet.getKickMessage());
        return false;
    }

    public boolean handle(ExplodePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LevelChunkPacket packet) {
        //System.out.println(packet.toString());
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
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MapCreateLockedCopyPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(MobEffectPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(ModalFormRequestPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(RemoveObjectivePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
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
        String BEGameType=packet.getPlayerGameType().toString();
        if(BEGameType.contains("VIEWER")){
            BEGameType=GameMode.SPECTATOR.name();
        }
        GameMode gamemode=GameMode.valueOf(BEGameType);
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
        ServerPlayerListDataPacket serverPlayerListDataPacket=new ServerPlayerListDataPacket(Ping.ping.getDescription(),new TextMessage(""));
        Vector3f position=packet.getPlayerPosition();
        ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket=new ServerPlayerPositionRotationPacket(position.getX(), position.getY(), position.getZ(), 90,90,1);
        if(client.clientStat.jLogined){
            client.JESession.send(serverJoinGamePacket);
            client.JESession.send(serverPlayerPositionRotationPacket);
            client.JESession.send(serverPlayerListDataPacket);
        }else {
            client.clientStat.jPacketMap.put("ServerPlayerPositionRotation",serverPlayerPositionRotationPacket);
            client.clientStat.jPacketMap.put("ServerJoinGame",serverJoinGamePacket);
            client.clientStat.jPacketMap.put("ServerPlayerListData",serverPlayerListDataPacket);
        }
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
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(TextPacket packet) {
        client.JESession.send(new ServerChatPacket(packet.getMessage()));
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
        List<AttributeData> attributes=packet.getAttributes();
        for(AttributeData attribute:attributes){
            switch (attribute.getName()){
                case "minecraft:health":{
                    client.clientStat.health=attribute.getValue();
                    break;
                }
                case "minecraft:player.experience":{
                    client.clientStat.exp=attribute.getValue()/attribute.getMaximum();
                    break;
                }
                case "minecraft:player.hunger":{
                    client.clientStat.food= (int) attribute.getValue();
                    break;
                }
                case "minecraft:player.level":{
                    client.clientStat.expLevel=attribute.getValue();
                    break;
                }
            }
        }
        client.JESession.send(new ServerPlayerHealthPacket(client.clientStat.health,client.clientStat.food,0));
        client.JESession.send(new ServerPlayerSetExperiencePacket(client.clientStat.exp, (int) client.clientStat.expLevel, 0));
        return false;
    }

    public boolean handle(UpdateBlockPacket packet) {
        //Variables.logger.warning(packet.getClass().getName());
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
        Variables.logger.warning(packet.getClass().getName());
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

