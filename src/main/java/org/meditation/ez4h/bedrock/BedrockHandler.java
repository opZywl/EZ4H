package org.meditation.ez4h.bedrock;

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;
import org.meditation.ez4h.Variables;

public class BedrockHandler implements BedrockPacketHandler {
    private Client client;
    public BedrockHandler(Client client) {
        this.client=client;
    }

    public boolean handle(AdventureSettingsPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AnimatePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AnvilDamagePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(AvailableEntityIdentifiersPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

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
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(InventorySlotPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
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

    public boolean handle(AvailableCommandsPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

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

    public boolean handle(CraftingDataPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(DisconnectPacket packet) {
        System.out.println("DIS");
        client.JESession.disconnect(packet.getKickMessage());
        return false;
    }

    public boolean handle(ExplodePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LevelChunkPacket packet) {
        //client.JESession.send(new ServerChunkDataPacket(new Column(packet.getChunkX(), packet.getChunkZ(), new Chunk[16], new byte[256], new CompoundTag[0])));
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
        Variables.logger.warning(packet.getClass().getName());
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

    public boolean handle(SetCommandsEnabledPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetDifficultyPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetDisplayObjectivePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetEntityDataPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        Variables.logger.warning(packet.toString());
        return false;
    }

    public boolean handle(SetEntityLinkPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(SetEntityMotionPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        Variables.logger.warning(packet.toString());
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
        Variables.logger.warning(packet.getClass().getName());
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
        System.out.println("READY!");
        String BEGameType=packet.getPlayerGameType().toString();
        if(BEGameType.contains("VIEWER")){
            BEGameType=GameMode.SPECTATOR.name();
        }
        Difficulty difficulty;
        switch (packet.getDifficulty()){
            case 0:{
                difficulty=Difficulty.PEACEFUL;
                break;
            }
            case 1:{
                difficulty=Difficulty.EASY;
                break;
            }
            case 3:{
                difficulty=Difficulty.HARD;
                break;
            }
            default:{
                difficulty=Difficulty.NORMAL;
                break;
            }
        }
        GameMode gamemode=GameMode.valueOf(BEGameType);
        client.JESession.send(new ServerJoinGamePacket(
                (int) packet.getUniqueEntityId(),
                false,
                gamemode,
                packet.getDimensionId(),
                difficulty,
                Ping.ping.getPlayerInfo().getMaxPlayers(),
                WorldType.CUSTOMIZED,
                true
        ));
        Vector3f position=packet.getPlayerPosition();
        client.JESession.send(new ServerPlayerPositionRotationPacket(position.getX(), position.getY(), position.getZ(), 90,90,1));
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
        Variables.logger.warning(packet.getClass().getName());
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

    public boolean handle(BiomeDefinitionListPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(LevelSoundEvent2Packet packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

    public boolean handle(NetworkChunkPublisherUpdatePacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

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

    public boolean handle(CreativeContentPacket packet) {
        Variables.logger.warning(packet.getClass().getName());
        return false;
    }

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

