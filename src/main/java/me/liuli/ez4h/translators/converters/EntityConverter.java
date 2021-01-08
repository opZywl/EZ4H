package me.liuli.ez4h.translators.converters;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.entity.type.GlobalEntityType;
import com.github.steveice10.mc.protocol.data.game.entity.type.MobType;
import com.github.steveice10.mc.protocol.data.game.entity.type.PaintingType;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.HangingDirection;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ObjectType;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.*;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.AddEntityPacket;
import me.liuli.ez4h.Variables;
import me.liuli.ez4h.bedrock.BedrockUtils;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.cache.EntityInfo;

import java.util.ArrayList;
import java.util.UUID;

public class EntityConverter {
    public static void convert(AddEntityPacket packet, Client client, EntityInfo entityInfo){
        Object entityType=convertEntityType(packet,client);
        if(entityType==null){
            Variables.logger.warning("FAILED TO CONVERT ENTITY:"+packet.getIdentifier());
            entityInfo.type="unknown_entity";
            addUnknownEntity(packet,client);
        }
        if(entityType instanceof MobType){
            Vector3f position=packet.getPosition(),rotation=packet.getRotation(),motion=packet.getMotion();
            client.sendPacket(new ServerSpawnMobPacket((int) packet.getRuntimeEntityId(), BedrockUtils.getUUID(packet.getRuntimeEntityId()),(MobType)entityType, position.getX(), position.getY()+1.62, position.getZ(), rotation.getY(),rotation.getX(),rotation.getZ(), motion.getX(), motion.getY(), motion.getZ(), MetadataConverter.convert(packet.getMetadata(),client, (int) packet.getRuntimeEntityId())));
        }else if(entityType instanceof ObjectType){
            Vector3f position=packet.getPosition(),rotation=packet.getRotation(),motion=packet.getMotion();
            client.sendPacket(new ServerSpawnObjectPacket((int) packet.getRuntimeEntityId(), BedrockUtils.getUUID(packet.getRuntimeEntityId()),(ObjectType)entityType, position.getX(), position.getY()+1.62, position.getZ(), rotation.getY(),rotation.getX(), motion.getX(), motion.getY(), motion.getZ()));
            client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(), MetadataConverter.convert(packet.getMetadata(),client, (int) packet.getRuntimeEntityId())));
        }else if(entityType instanceof GlobalEntityType){
            Vector3f position=packet.getPosition();
            client.sendPacket(new ServerSpawnGlobalEntityPacket((int) packet.getRuntimeEntityId(),(GlobalEntityType)entityType, position.getX(), position.getY()+1.62, position.getZ()));
        }
    }
    public static void addUnknownEntity(AddEntityPacket packet, Client client){
        UUID uuid= BedrockUtils.getUUID(packet.getRuntimeEntityId());
        ArrayList<PlayerListEntry> playerListEntries=new ArrayList<>();
        playerListEntries.add(new PlayerListEntry(new GameProfile(uuid, BedrockUtils.lengthCutter(prepareEntityName(packet.getIdentifier()),16)), GameMode.SURVIVAL,0,new TextMessage(packet.getIdentifier())));
        PlayerListEntry[] playerListEntriesL=playerListEntries.toArray(new PlayerListEntry[0]);
        client.sendPacket(new ServerPlayerListEntryPacket(PlayerListEntryAction.ADD_PLAYER, playerListEntriesL));

        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),new EntityInfo(position.getX(), position.getY(), position.getZ(), (int) packet.getRuntimeEntityId(),"player"));
        client.sendPacket(new ServerSpawnPlayerPacket((int) packet.getRuntimeEntityId(),uuid, position.getX(), position.getY()+1.62, position.getZ(),rotation.getY(),rotation.getX(), MetadataConverter.convert(packet.getMetadata(),client, (int) packet.getRuntimeEntityId())));

        client.sendPacket(new ServerPlayerListEntryPacket(PlayerListEntryAction.REMOVE_PLAYER, playerListEntriesL));
    }
    public static Object convertEntityType(AddEntityPacket packet, Client client){
        Object entityType=null;
        switch (prepareEntityName(packet.getIdentifier())){
            case "wither_skeleton":{
                entityType=MobType.WITHER_SKELETON;
                break;
            }
            case "husk":{
                entityType=MobType.HUSK;
                break;
            }
            case "stray":{
                entityType=MobType.STRAY;
                break;
            }
            case "witch":{
                entityType=MobType.WITCH;
                break;
            }
            case "zombie_villager":
            case "zomber_villager_v2": {
                entityType=MobType.ZOMBIE_VILLAGER;
                break;
            }
            case "blaze":{
                entityType=MobType.BLAZE;
                break;
            }
            case "magma_cube":{
                entityType=MobType.MAGMA_CUBE;
                break;
            }
            case "ghast":{
                entityType=MobType.GHAST;
                break;
            }
            case "cave_spider":{
                entityType=MobType.CAVE_SPIDER;
                break;
            }
            case "silverfish":{
                entityType=MobType.SILVERFISH;
                break;
            }
            case "enderman":{
                entityType=MobType.ENDERMAN;
                break;
            }
            case "slime":{
                entityType=MobType.SLIME;
                break;
            }
            case "zombie_pigman":
            case "piglin":{
                entityType=MobType.ZOMBIE_PIGMAN;
                break;
            }
            case "spider":{
                entityType=MobType.SPIDER;
                break;
            }
            case "skeleton":{
                entityType=MobType.SKELETON;
                break;
            }
            case "creeper":{
                entityType=MobType.CREEPER;
                break;
            }
            case "zombie":
            case "drowned": {
                entityType=MobType.ZOMBIE;
                break;
            }
            case "skeleton_horse":{
                entityType=MobType.SKELETON_HORSE;
                break;
            }
            case "mule":{
                entityType=MobType.MULE;
                break;
            }
            case "donkey":{
                entityType=MobType.DONKEY;
                break;
            }
            case "wolf":
            case "fox": {
                entityType=MobType.WOLF;
                break;
            }
            case "squid":{
                entityType=MobType.SQUID;
                break;
            }
            case "sheep":{
                entityType=MobType.SHEEP;
                break;
            }
            case "mooshroom":{
                entityType=MobType.MOOSHROOM;
                break;
            }
            case "panda":
            case "polar_bear":
            case "ravager": {
                entityType=MobType.POLAR_BEAR;
                break;
            }
            case "pig":{
                entityType=MobType.PIG;
                break;
            }
            case "villager":
            case "villager_v2":
            case "wandering_trader": {
                entityType=MobType.VILLAGER;
                break;
            }
            case "cow":{
                entityType=MobType.COW;
                break;
            }
            case "chicken":{
                entityType=MobType.CHICKEN;
                break;
            }
            case "llama":{
                entityType=MobType.LLAMA;
                break;
            }
            case "rabbit":{
                entityType=MobType.RABBIT;
                break;
            }
            case "snow_golem":{
                entityType=MobType.SNOWMAN;
                break;
            }
            case "bat":{
                entityType=MobType.BAT;
                break;
            }
            case "iron_golem":{
                entityType=MobType.IRON_GOLEM;
                break;
            }
            case "ocelot":
            case "cat": {
                entityType=MobType.OCELOT;
                break;
            }
            case "horse":{
                entityType=MobType.HORSE;
                break;
            }
            case "zombie_horse":{
                entityType=MobType.ZOMBIE_HORSE;
                break;
            }
            case "parrot":{
                entityType=MobType.PARROT;
                break;
            }
            case "guardian":{
                entityType=MobType.GUARDIAN;
                break;
            }
            case "elder_guardian":{
                entityType=MobType.ELDER_GUARDIAN;
                break;
            }
            case "vindicator":{
                entityType=MobType.VINDICATION_ILLAGER;
                break;
            }
            case "wither":{
                entityType=MobType.WITHER;
                break;
            }
            case "ender_dragon":{
                entityType=MobType.ENDER_DRAGON;
                break;
            }
            case "shulker":{
                entityType=MobType.SHULKER;
                break;
            }
            case "endermite":{
                entityType=MobType.ENDERMITE;
                break;
            }
            case "minecart":
            case "command_block_minecart":
            case "chest_minecart":
            case "tnt_minecart":
            case "hopper_minecart": {
                entityType=ObjectType.MINECART;
                break;
            }
            case "armor_stand":{
                entityType=ObjectType.ARMOR_STAND;
                break;
            }
            case "tnt":{
                entityType=ObjectType.PRIMED_TNT;
                break;
            }
            case "xp_bottle":{
                entityType=ObjectType.EXP_BOTTLE;
                break;
            }
            case "xp_orb":{
                entityType="xp_orb";
                Vector3f position=packet.getPosition();
                client.sendPacket(new ServerSpawnExpOrbPacket((int) packet.getRuntimeEntityId(),position.getX(), position.getY()+1.62, position.getZ(), 1));
                break;
            }
            case "eye_of_ender_signal":{
                entityType=ObjectType.EYE_OF_ENDER;
                break;
            }
            case "ender_crystal":{
                entityType=ObjectType.ENDER_CRYSTAL;
                break;
            }
            case "shulker_bullet":{
                entityType=ObjectType.SHULKER_BULLET;
                break;
            }
            case "fishing_hook":{
                entityType=ObjectType.FISH_HOOK;
                break;
            }
            case "dragon_fireball":{
                entityType=ObjectType.DRAGON_FIREBALL;
                break;
            }
            case "arrow":
            case "thrown_trident": {
                entityType=ObjectType.SPECTRAL_ARROW;
                break;
            }
            case "snowball":{
                entityType=ObjectType.SNOWBALL;
                break;
            }
            case "egg":{
                entityType=ObjectType.EGG;
                break;
            }
            case "painting":{
                entityType="painting";
                Vector3f position=packet.getPosition();
                Position j_position=new Position(position.getFloorX(), position.getFloorY()+2, position.getFloorZ());
                client.sendPacket(new ServerSpawnPaintingPacket((int) packet.getRuntimeEntityId(), BedrockUtils.getUUID(packet.getRuntimeEntityId()), PaintingType.ALBAN,j_position, HangingDirection.EAST));
                break;
            }
            case "fireball":{
                entityType=ObjectType.GHAST_FIREBALL;
                break;
            }
            case "splash_potion":
            case "lingering_potion": {
                entityType=ObjectType.POTION;
                break;
            }
            case "ender_pearl":{
                entityType=ObjectType.ENDER_PEARL;
                break;
            }
            case "wither_skull":
            case "wither_skull_dangerous": {
                entityType=ObjectType.WITHER_HEAD_PROJECTILE;
                break;
            }
            case "boat":{
                entityType=ObjectType.BOAT;
                break;
            }
            case "lightning_bolt":{
                entityType=GlobalEntityType.LIGHTNING_BOLT;
                break;
            }
            case "small_fireball":{
                entityType=ObjectType.BLAZE_FIREBALL;
                break;
            }
            case "llama_spit":{
                entityType=ObjectType.LLAMA_SPIT;
                break;
            }
            case "area_effect_cloud":{
                entityType=ObjectType.AREA_EFFECT_CLOUD;
                break;
            }
            case "firework_rocket":{
                entityType=ObjectType.FIREWORK_ROCKET;
                break;
            }
            case "evocation_fang":
            case "evocation_illager": {
                entityType=ObjectType.EVOCATION_FANGS;
                break;
            }
        }
        return entityType;
    }
    private static String prepareEntityName(String name){
        if(name.contains("minecraft:")){
            return name.replaceAll("minecraft:","");
        }
        return name;
    }
}
