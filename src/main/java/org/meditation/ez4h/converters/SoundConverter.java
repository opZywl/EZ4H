package org.meditation.ez4h.converters;

import com.github.steveice10.mc.protocol.data.game.world.sound.BuiltinSound;

public class SoundConverter {
    public static BuiltinSound convert(String name){
        switch (name){
            case "ambient.cave":{
                return BuiltinSound.AMBIENT_CAVE;
            }
            case "ambient.weather.lightning.impact":{
                return BuiltinSound.ENTITY_LIGHTNING_IMPACT;
            }
            case "ambient.weather.rain":{
                return BuiltinSound.WEATHER_RAIN;
            }
            case "ambient.weather.thunder":{
                return BuiltinSound.ENTITY_LIGHTNING_THUNDER;
            }
            case "armor.equip_netherite":
            case "armor.equip_diamond": {
                return BuiltinSound.ITEM_ARMOR_EQUIP_DIAMOND;
            }
            case "armor.equip_chain":{
                return BuiltinSound.ITEM_ARMOR_EQUIP_CHAIN;
            }
            case "armor.equip_generic":{
                return BuiltinSound.ITEM_ARMOR_EQUIP_GENERIC;
            }
            case "armor.equip_gold":{
                return BuiltinSound.ITEM_ARMOR_EQUIP_GOLD;
            }
            case "armor.equip_iron":{
                return BuiltinSound.ITEM_ARMOR_EQUIP_IRON;
            }
            case "armor.equip_leather":{
                return BuiltinSound.ITEM_ARMOR_EQUIP_LEATHER;
            }
            case "block.bell.hit":{
                return BuiltinSound.BLOCK_NOTE_BELL;
            }
            case "block.blastfurnace.fire_crackle":{
                return BuiltinSound.BLOCK_FURNACE_FIRE_CRACKLE;
            }
            case "block.chorusflower.death":{
                return BuiltinSound.BLOCK_CHORUS_FLOWER_DEATH;
            }
            case "block.chorusflower.grow":{
                return BuiltinSound.BLOCK_CHORUS_FLOWER_GROW;
            }
            case "block.composter.empty":
            case "block.composter.fill":
            case "block.composter.fill_success":
            case "block.composter.ready": {
                return BuiltinSound.BLOCK_COMPARATOR_CLICK;
            }
            case "block.end_portal.spawn":{
                return BuiltinSound.BLOCK_END_PORTAL_SPAWN;
            }
            case "block.end_portal_frame.fill":{
                return BuiltinSound.BLOCK_END_PORTAL_FRAME_FILL;
            }
            case "block.itemframe.add_item":{
                return BuiltinSound.ENTITY_ITEMFRAME_ADD_ITEM;
            }
            case "block.itemframe.break":{
                return BuiltinSound.ENTITY_ITEMFRAME_BREAK;
            }
            case "block.itemframe.place":{
                return BuiltinSound.ENTITY_ITEMFRAME_PLACE;
            }
            case "block.itemframe.remove_item":{
                return BuiltinSound.ENTITY_ITEMFRAME_REMOVE_ITEM;
            }
            case "block.itemframe.rotate_item":{
                return BuiltinSound.ENTITY_ITEMFRAME_ROTATE_ITEM;
            }
            case "bottle.dragonbreath":{
                return BuiltinSound.ITEM_BOTTLE_FILL_DRAGONBREATH;
            }
            case "bucket.empty_fish":{
                return BuiltinSound.ITEM_BUCKET_EMPTY;
            }
            case "bucket.empty_lava":{
                return BuiltinSound.ITEM_BUCKET_EMPTY_LAVA;
            }
            case "bucket.empty_water":{
                return BuiltinSound.ITEM_BUCKET_EMPTY;
            }
            case "bucket.fill_fish":{
                return BuiltinSound.ITEM_BUCKET_FILL;
            }
            case "bucket.fill_lava":{
                return BuiltinSound.ITEM_BUCKET_FILL_LAVA;
            }
            case "bucket.fill_water":{
                return BuiltinSound.ITEM_BUCKET_FILL;
            }
            case "damage.fallbig":{
                return BuiltinSound.ENTITY_GENERIC_BIG_FALL;
            }
            case "damage.fallsmall":{
                return BuiltinSound.ENTITY_GENERIC_SMALL_FALL;
            }
            case "fire.fire":{
                return BuiltinSound.BLOCK_FIRE_AMBIENT;
            }
            case "fire.ignite":{
                return BuiltinSound.BLOCK_FIRE_EXTINGUISH;
            }
            case "firework.blast":{
                return BuiltinSound.ENTITY_FIREWORK_BLAST;
            }
            case "firework.large_blast":{
                return BuiltinSound.ENTITY_FIREWORK_BLAST_FAR;
            }
            case "firework.launch":{
                return BuiltinSound.ENTITY_FIREWORK_LAUNCH;
            }
            case "firework.shoot":{
                return BuiltinSound.ENTITY_FIREWORK_SHOOT;
            }
            case "firework.twinkle":{
                return BuiltinSound.ENTITY_FIREWORK_TWINKLE;
            }
            case "game.player.attack.nodamage":{
                return BuiltinSound.ENTITY_PLAYER_ATTACK_NODAMAGE;
            }
            case "game.player.attack.strong":{
                return BuiltinSound.ENTITY_PLAYER_ATTACK_STRONG;
            }
            case "game.player.die":{
                return BuiltinSound.ENTITY_PLAYER_DEATH;
            }
            case "game.player.hurt":{
                return BuiltinSound.ENTITY_PLAYER_HURT;
            }
            case "hit.anvil":{
                return BuiltinSound.BLOCK_ANVIL_HIT;
            }
            case "item.shield.block":{
                return BuiltinSound.ITEM_SHIELD_BLOCK;
            }
            case "mob.armor_stand.break":{
                return BuiltinSound.ENTITY_ARMORSTAND_BREAK;
            }
            case "mob.armor_stand.hit":{
                return BuiltinSound.ENTITY_ARMORSTAND_HIT;
            }
            case "mob.armor_stand.land":{
                return BuiltinSound.ENTITY_ARMORSTAND_FALL;
            }
            case "mob.armor_stand.place":{
                return BuiltinSound.ENTITY_ARMORSTAND_PLACE;
            }
            case "mob.bat.death":{
                return BuiltinSound.ENTITY_BAT_DEATH;
            }
            case "mob.bat.hurt":{
                return BuiltinSound.ENTITY_BAT_HURT;
            }
            case "mob.bat.idle":{
                return BuiltinSound.ENTITY_BAT_LOOP;
            }
            case "mob.bat.takeoff":{
                return BuiltinSound.ENTITY_BAT_TAKEOFF;
            }
            case "mob.blaze.breathe":{
                return BuiltinSound.ENTITY_BLAZE_BURN;
            }
            case "mob.blaze.death":{
                return BuiltinSound.ENTITY_BLAZE_DEATH;
            }
            case "mob.blaze.hit":{
                return BuiltinSound.ENTITY_BLAZE_HURT;
            }
            case "mob.blaze.shoot":{
                return BuiltinSound.ENTITY_BLAZE_SHOOT;
            }
            case "mob.cat.hiss":{
                return BuiltinSound.ENTITY_CAT_HISS;
            }
            case "mob.cat.hit":{
                return BuiltinSound.ENTITY_CAT_HURT;
            }
            case "mob.cat.meow":{
                return BuiltinSound.ENTITY_CAT_AMBIENT;
            }
            case "mob.cat.purr":{
                return BuiltinSound.ENTITY_CAT_PURR;
            }
            case "mob.cat.purreow":{
                return BuiltinSound.ENTITY_CAT_PURREOW;
            }
            case "mob.chicken.hurt":{
                return BuiltinSound.ENTITY_CHICKEN_HURT;
            }
            case "mob.chicken.plop":{
                return BuiltinSound.ENTITY_CHICKEN_EGG;
            }
            case "mob.chicken.say":{
                return BuiltinSound.ENTITY_CHICKEN_AMBIENT;
            }
            case "mob.chicken.step":{
                return BuiltinSound.ENTITY_CHICKEN_STEP;
            }
            case "mob.cow.hurt":{
                return BuiltinSound.ENTITY_COW_HURT;
            }
            case "mob.cow.milk":{
                return BuiltinSound.ENTITY_COW_MILK;
            }
            case "mob.cow.say":{
                return BuiltinSound.ENTITY_COW_AMBIENT;
            }
            case "mob.cow.step":{
                return BuiltinSound.ENTITY_COW_STEP;
            }
            case "mob.creeper.death":{
                return BuiltinSound.ENTITY_CREEPER_DEATH;
            }
            case "mob.creeper.say":{
                return BuiltinSound.ENTITY_CREEPER_PRIMED;
            }
            case "mob.elderguardian.curse":{
                return BuiltinSound.ENTITY_ELDER_GUARDIAN_CURSE;
            }
            case "mob.elderguardian.death":{
                return BuiltinSound.ENTITY_ELDER_GUARDIAN_DEATH;
            }
            case "mob.elderguardian.hit":{
                return BuiltinSound.ENTITY_ELDER_GUARDIAN_HURT;
            }
            case "mob.elderguardian.idle":{
                return BuiltinSound.ENTITY_ELDER_GUARDIAN_AMBIENT;
            }
            case "mob.enderdragon.death":{
                return BuiltinSound.ENTITY_ENDERDRAGON_DEATH;
            }
            case "mob.enderdragon.flap":{
                return BuiltinSound.ENTITY_ENDERDRAGON_FLAP;
            }
            case "mob.enderdragon.growl":{
                return BuiltinSound.ENTITY_ENDERDRAGON_GROWL;
            }
            case "mob.enderdragon.hit":{
                return BuiltinSound.ENTITY_ENDERDRAGON_HURT;
            }
            case "mob.endermen.death":{
                return BuiltinSound.ENTITY_ENDERMEN_DEATH;
            }
            case "mob.endermen.hit":{
                return BuiltinSound.ENTITY_ENDERMEN_HURT;
            }
            case "mob.endermen.idle":{
                return BuiltinSound.ENTITY_ENDERMEN_AMBIENT;
            }
            case "mob.endermen.portal":{
                return BuiltinSound.ENTITY_ENDERMEN_TELEPORT;
            }
            case "mob.endermen.scream":{
                return BuiltinSound.ENTITY_ENDERMEN_SCREAM;
            }
            case "mob.endermen.stare":{
                return BuiltinSound.ENTITY_ENDERMEN_STARE;
            }
            case "mob.endermite.hit":{
                return BuiltinSound.ENTITY_ENDERMITE_HURT;
            }
            case "mob.endermite.kill":{
                return BuiltinSound.ENTITY_ENDERMITE_DEATH;
            }
            case "mob.endermite.say":{
                return BuiltinSound.ENTITY_ENDERMITE_AMBIENT;
            }
            case "mob.endermite.step":{
                return BuiltinSound.ENTITY_ENDERMITE_STEP;
            }
            case "mob.evocation_fangs.attack":{
                return BuiltinSound.ENTITY_EVOCATION_FANGS_ATTACK;
            }
            case "mob.evocation_illager.ambient":{
                return BuiltinSound.ENTITY_EVOCATION_ILLAGER_AMBIENT;
            }
            case "mob.evocation_illager.cast_spell":{
                return BuiltinSound.ENTITY_EVOCATION_ILLAGER_CAST_SPELL;
            }
            case "mob.evocation_illager.celebrate":{
                return BuiltinSound.ENTITY_EVOCATION_ILLAGER_AMBIENT;
            }
            case "mob.evocation_illager.death":{
                return BuiltinSound.ENTITY_EVOCATION_ILLAGER_DEATH;
            }
            case "mob.evocation_illager.hurt":{
                return BuiltinSound.ENTITY_EVOCATION_ILLAGER_HURT;
            }
            case "mob.evocation_illager.prepare_attack":{
                return BuiltinSound.ENTITY_EVOCATION_ILLAGER_PREPARE_ATTACK;
            }
            case "mob.evocation_illager.prepare_summon":{
                return BuiltinSound.ENTITY_EVOCATION_ILLAGER_PREPARE_SUMMON;
            }
            case "mob.evocation_illager.prepare_wololo":{
                return BuiltinSound.ENTITY_EVOCATION_ILLAGER_PREPARE_WOLOLO;
            }
            case "mob.ghast.affectionate_scream":{
                return BuiltinSound.ENTITY_GHAST_SCREAM;
            }
            case "mob.ghast.charge":{
                return BuiltinSound.ENTITY_GHAST_WARN;
            }
            case "mob.ghast.death":{
                return BuiltinSound.ENTITY_GHAST_DEATH;
            }
            case "mob.ghast.fireball":{
                return BuiltinSound.ENTITY_GHAST_SHOOT;
            }
            case "mob.ghast.moan":{
                return BuiltinSound.ENTITY_GHAST_AMBIENT;
            }
            case "mob.ghast.scream":{
                return BuiltinSound.ENTITY_GHAST_SCREAM;
            }
            case "mob.guardian.ambient":{
                return BuiltinSound.ENTITY_GUARDIAN_AMBIENT;
            }
            case "mob.guardian.death":{
                return BuiltinSound.ENTITY_GUARDIAN_DEATH;
            }
            case "mob.guardian.flop":{
                return BuiltinSound.ENTITY_GUARDIAN_FLOP;
            }
            case "mob.guardian.hit":{
                return BuiltinSound.ENTITY_GUARDIAN_HURT;
            }
            case "mob.guardian.land_death":{
                return BuiltinSound.ENTITY_GUARDIAN_DEATH_LAND;
            }
            case "mob.guardian.land_hit":{
                return BuiltinSound.ENTITY_GUARDIAN_HURT_LAND;
            }
            case "mob.guardian.land_idle":{
                return BuiltinSound.ENTITY_GUARDIAN_AMBIENT_LAND;
            }
            case "mob.horse.angry":{
                return BuiltinSound.ENTITY_HORSE_ANGRY;
            }
            case "mob.horse.armor":{
                return BuiltinSound.ENTITY_HORSE_ARMOR;
            }
            case "mob.horse.breathe":{
                return BuiltinSound.ENTITY_HORSE_BREATHE;
            }
            case "mob.horse.death":{
                return BuiltinSound.ENTITY_HORSE_DEATH;
            }
            case "mob.horse.donkey.angry":{
                return BuiltinSound.ENTITY_DONKEY_ANGRY;
            }
            case "mob.horse.donkey.death":{
                return BuiltinSound.ENTITY_DONKEY_DEATH;
            }
            case "mob.horse.donkey.hit":{
                return BuiltinSound.ENTITY_DONKEY_HURT;
            }
            case "mob.horse.donkey.idle":{
                return BuiltinSound.ENTITY_DONKEY_AMBIENT;
            }
            case "mob.horse.eat":{
                return BuiltinSound.ENTITY_HORSE_EAT;
            }
            case "mob.horse.gallop":{
                return BuiltinSound.ENTITY_HORSE_GALLOP;
            }
            case "mob.horse.hit":{
                return BuiltinSound.ENTITY_HORSE_HURT;
            }
            case "mob.horse.idle":{
                return BuiltinSound.ENTITY_HORSE_AMBIENT;
            }
            case "mob.horse.jump":{
                return BuiltinSound.ENTITY_HORSE_JUMP;
            }
            case "mob.horse.land":{
                return BuiltinSound.ENTITY_HORSE_LAND;
            }
            case "mob.horse.leather":{
                return BuiltinSound.ENTITY_HORSE_SADDLE;
            }
            case "mob.horse.skeleton.death":{
                return BuiltinSound.ENTITY_SKELETON_HORSE_DEATH;
            }
            case "mob.horse.skeleton.hit":{
                return BuiltinSound.ENTITY_SKELETON_HORSE_HURT;
            }
            case "mob.horse.skeleton.idle":{
                return BuiltinSound.ENTITY_SKELETON_HORSE_AMBIENT;
            }
            case "mob.horse.soft":{
                return BuiltinSound.ENTITY_HORSE_STEP;
            }
            case "mob.horse.wood":{
                return BuiltinSound.ENTITY_HORSE_STEP_WOOD;
            }
            case "mob.horse.zombie.death":{
                return BuiltinSound.ENTITY_ZOMBIE_HORSE_DEATH;
            }
            case "mob.horse.zombie.hit":{
                return BuiltinSound.ENTITY_ZOMBIE_HORSE_HURT;
            }
            case "mob.horse.zombie.idle":{
                return BuiltinSound.ENTITY_ZOMBIE_HORSE_AMBIENT;
            }
            case "mob.husk.ambient":{
                return BuiltinSound.ENTITY_HUSK_AMBIENT;
            }
            case "mob.husk.death":{
                return BuiltinSound.ENTITY_HUSK_DEATH;
            }
            case "mob.husk.hurt":{
                return BuiltinSound.ENTITY_HUSK_HURT;
            }
            case "mob.husk.step":{
                return BuiltinSound.ENTITY_HUSK_STEP;
            }
            case "mob.irongolem.death":{
                return BuiltinSound.ENTITY_IRONGOLEM_DEATH;
            }
            case "mob.irongolem.hit":{
                return BuiltinSound.ENTITY_IRONGOLEM_HURT;
            }
            case "mob.irongolem.throw":{
                return BuiltinSound.ENTITY_IRONGOLEM_ATTACK;
            }
            case "mob.irongolem.walk":{
                return BuiltinSound.ENTITY_IRONGOLEM_STEP;
            }
            case "mob.llama.angry":{
                return BuiltinSound.ENTITY_LLAMA_ANGRY;
            }
            case "mob.llama.death":{
                return BuiltinSound.ENTITY_LLAMA_DEATH;
            }
            case "mob.llama.eat":{
                return BuiltinSound.ENTITY_LLAMA_EAT;
            }
            case "mob.llama.hurt":{
                return BuiltinSound.ENTITY_LLAMA_HURT;
            }
            case "mob.llama.idle":{
                return BuiltinSound.ENTITY_LLAMA_AMBIENT;
            }
            case "mob.llama.spit":{
                return BuiltinSound.ENTITY_LLAMA_SPIT;
            }
            case "mob.llama.step":{
                return BuiltinSound.ENTITY_LLAMA_STEP;
            }
            case "mob.llama.swag":{
                return BuiltinSound.ENTITY_LLAMA_SWAG;
            }
            case "mob.magmacube.jump":{
                return BuiltinSound.ENTITY_MAGMACUBE_JUMP;
            }
            case "mob.mooshroom.suspicious_milk":{
                return BuiltinSound.ENTITY_MOOSHROOM_SHEAR;
            }
            case "mob.parrot.death":{
                return BuiltinSound.ENTITY_PARROT_DEATH;
            }
            case "mob.parrot.eat":{
                return BuiltinSound.ENTITY_PARROT_EAT;
            }
            case "mob.parrot.fly":{
                return BuiltinSound.ENTITY_PARROT_FLY;
            }
            case "mob.parrot.hurt":{
                return BuiltinSound.ENTITY_PARROT_HURT;
            }
            case "mob.parrot.idle":{
                return BuiltinSound.ENTITY_PARROT_AMBIENT;
            }
            case "mob.parrot.step":{
                return BuiltinSound.ENTITY_PARROT_STEP;
            }
            case "mob.pig.boost":{
                return BuiltinSound.ENTITY_PIG_SADDLE;
            }
            case "mob.pig.death":{
                return BuiltinSound.ENTITY_PIG_DEATH;
            }
            case "mob.pig.say":{
                return BuiltinSound.ENTITY_PIG_AMBIENT;
            }
            case "mob.pig.step":{
                return BuiltinSound.ENTITY_PIG_STEP;
            }
            case "mob.piglin.ambient":{
                return BuiltinSound.ENTITY_ZOMBIE_PIG_AMBIENT;
            }
            case "mob.piglin.angry":{
                return BuiltinSound.ENTITY_ZOMBIE_PIG_ANGRY;
            }
            case "mob.piglin.death":{
                return BuiltinSound.ENTITY_ZOMBIE_PIG_DEATH;
            }
            case "mob.piglin.hurt":{
                return BuiltinSound.ENTITY_ZOMBIE_PIG_HURT;
            }
            case "mob.polarbear.death":{
                return BuiltinSound.ENTITY_POLAR_BEAR_DEATH;
            }
            case "mob.polarbear.hurt":{
                return BuiltinSound.ENTITY_POLAR_BEAR_HURT;
            }
            case "mob.polarbear.idle":{
                return BuiltinSound.ENTITY_POLAR_BEAR_AMBIENT;
            }
            case "mob.polarbear.step":{
                return BuiltinSound.ENTITY_POLAR_BEAR_STEP;
            }
            case "mob.polarbear.warning":{
                return BuiltinSound.ENTITY_POLAR_BEAR_WARNING;
            }
            case "mob.polarbear_baby.idle":{
                return BuiltinSound.ENTITY_POLAR_BEAR_BABY_AMBIENT;
            }
            case "mob.rabbit.death":{
                return BuiltinSound.ENTITY_RABBIT_DEATH;
            }
            case "mob.rabbit.hop":{
                return BuiltinSound.ENTITY_RABBIT_JUMP;
            }
            case "mob.rabbit.hurt":{
                return BuiltinSound.ENTITY_RABBIT_HURT;
            }
            case "mob.rabbit.idle":{
                return BuiltinSound.ENTITY_RABBIT_AMBIENT;
            }
            case "mob.sheep.shear":{
                return BuiltinSound.ENTITY_SHEEP_SHEAR;
            }
            case "mob.sheep.step":{
                return BuiltinSound.ENTITY_SHEEP_STEP;
            }
            case "mob.shulker.ambient":{
                return BuiltinSound.ENTITY_SHULKER_AMBIENT;
            }
            case "mob.shulker.bullet.hit":{
                return BuiltinSound.ENTITY_SHULKER_BULLET_HIT;
            }
            case "mob.shulker.close":{
                return BuiltinSound.ENTITY_SHULKER_CLOSE;
            }
            case "mob.shulker.close.hurt":{
                return BuiltinSound.ENTITY_SHULKER_HURT_CLOSED;
            }
            case "mob.shulker.death":{
                return BuiltinSound.ENTITY_SHULKER_DEATH;
            }
            case "mob.shulker.hurt":{
                return BuiltinSound.ENTITY_SHULKER_HURT;
            }
            case "mob.shulker.open":{
                return BuiltinSound.ENTITY_SHULKER_OPEN;
            }
            case "mob.shulker.shoot":{
                return BuiltinSound.ENTITY_SHULKER_SHOOT;
            }
            case "mob.shulker.teleport":{
                return BuiltinSound.ENTITY_SHULKER_TELEPORT;
            }
            case "mob.silverfish.hit":{
                return BuiltinSound.ENTITY_SILVERFISH_HURT;
            }
            case "mob.silverfish.kill":{
                return BuiltinSound.ENTITY_SILVERFISH_DEATH;
            }
            case "mob.silverfish.say":{
                return BuiltinSound.ENTITY_SILVERFISH_AMBIENT;
            }
            case "mob.silverfish.step":{
                return BuiltinSound.ENTITY_SILVERFISH_STEP;
            }
            case "mob.skeleton.death":{
                return BuiltinSound.ENTITY_SKELETON_DEATH;
            }
            case "mob.skeleton.hurt":{
                return BuiltinSound.ENTITY_SKELETON_HURT;
            }
            case "mob.skeleton.say":{
                return BuiltinSound.ENTITY_SKELETON_AMBIENT;
            }
            case "mob.skeleton.step":{
                return BuiltinSound.ENTITY_SKELETON_STEP;
            }
            case "mob.slime.attack":{
                return BuiltinSound.ENTITY_SLIME_ATTACK;
            }
            case "mob.slime.death":{
                return BuiltinSound.ENTITY_SLIME_DEATH;
            }
            case "mob.slime.hurt":{
                return BuiltinSound.ENTITY_SLIME_HURT;
            }
            case "mob.slime.jump":{
                return BuiltinSound.ENTITY_SLIME_JUMP;
            }
            case "mob.slime.squish":{
                return BuiltinSound.ENTITY_SLIME_SQUISH;
            }
            case "mob.snowgolem.death":{
                return BuiltinSound.ENTITY_SNOWMAN_DEATH;
            }
            case "mob.snowgolem.hurt":{
                return BuiltinSound.ENTITY_SNOWMAN_HURT;
            }
            case "mob.snowgolem.shoot":{
                return BuiltinSound.ENTITY_SNOWMAN_SHOOT;
            }
            case "mob.spider.death":{
                return BuiltinSound.ENTITY_SPIDER_DEATH;
            }
            case "mob.spider.say":{
                return BuiltinSound.ENTITY_SPIDER_AMBIENT;
            }
            case "mob.spider.step":{
                return BuiltinSound.ENTITY_SPIDER_STEP;
            }
            case "mob.squid.ambient":{
                return BuiltinSound.ENTITY_SQUID_AMBIENT;
            }
            case "mob.squid.death":{
                return BuiltinSound.ENTITY_SQUID_DEATH;
            }
            case "mob.squid.hurt":{
                return BuiltinSound.ENTITY_SQUID_HURT;
            }
            case "mob.stray.ambient":{
                return BuiltinSound.ENTITY_STRAY_AMBIENT;
            }
            case "mob.stray.death":{
                return BuiltinSound.ENTITY_STRAY_DEATH;
            }
            case "mob.stray.hurt":{
                return BuiltinSound.ENTITY_STRAY_HURT;
            }
            case "mob.stray.step":{
                return BuiltinSound.ENTITY_STRAY_STEP;
            }
            case "mob.vex.ambient":{
                return BuiltinSound.ENTITY_VEX_AMBIENT;
            }
            case "mob.vex.charge":{
                return BuiltinSound.ENTITY_VEX_CHARGE;
            }
            case "mob.vex.death":{
                return BuiltinSound.ENTITY_VEX_DEATH;
            }
            case "mob.vex.hurt":{
                return BuiltinSound.ENTITY_VEX_HURT;
            }
            case "mob.villager.death":{
                return BuiltinSound.ENTITY_VILLAGER_DEATH;
            }
            case "mob.villager.hit":{
                return BuiltinSound.ENTITY_VILLAGER_HURT;
            }
            case "mob.villager.idle":{
                return BuiltinSound.ENTITY_VILLAGER_AMBIENT;
            }
            case "mob.villager.no":{
                return BuiltinSound.ENTITY_VILLAGER_NO;
            }
            case "mob.villager.yes":{
                return BuiltinSound.ENTITY_VILLAGER_YES;
            }
            case "mob.vindicator.death":{
                return BuiltinSound.ENTITY_VINDICATION_ILLAGER_DEATH;
            }
            case "mob.vindicator.hurt":{
                return BuiltinSound.ENTITY_VINDICATION_ILLAGER_HURT;
            }
            case "mob.vindicator.idle":{
                return BuiltinSound.ENTITY_VINDICATION_ILLAGER_AMBIENT;
            }
            case "mob.witch.ambient":{
                return BuiltinSound.ENTITY_WITCH_AMBIENT;
            }
            case "mob.witch.death":{
                return BuiltinSound.ENTITY_WITCH_DEATH;
            }
            case "mob.witch.drink":{
                return BuiltinSound.ENTITY_WITCH_DRINK;
            }
            case "mob.witch.hurt":{
                return BuiltinSound.ENTITY_WITCH_HURT;
            }
            case "mob.witch.throw":{
                return BuiltinSound.ENTITY_WITCH_THROW;
            }
            case "mob.wither.ambient":{
                return BuiltinSound.ENTITY_WITHER_AMBIENT;
            }
            case "mob.wither.break_block":{
                return BuiltinSound.ENTITY_WITHER_BREAK_BLOCK;
            }
            case "mob.wither.death":{
                return BuiltinSound.ENTITY_WITHER_DEATH;
            }
            case "mob.wither.hurt":{
                return BuiltinSound.ENTITY_WITHER_HURT;
            }
            case "mob.wither.shoot":{
                return BuiltinSound.ENTITY_WITHER_SHOOT;
            }
            case "mob.wither.spawn":{
                return BuiltinSound.ENTITY_WITHER_SPAWN;
            }
            case "mob.wolf.bark":{
                return BuiltinSound.ENTITY_WOLF_HOWL;
            }
            case "mob.wolf.death":{
                return BuiltinSound.ENTITY_WOLF_DEATH;
            }
            case "mob.wolf.growl":{
                return BuiltinSound.ENTITY_WOLF_GROWL;
            }
            case "mob.wolf.hurt":{
                return BuiltinSound.ENTITY_WOLF_HURT;
            }
            case "mob.wolf.panting":{
                return BuiltinSound.ENTITY_WOLF_PANT;
            }
            case "mob.wolf.shake":{
                return BuiltinSound.ENTITY_WOLF_SHAKE;
            }
            case "mob.wolf.step":{
                return BuiltinSound.ENTITY_WOLF_STEP;
            }
            case "mob.wolf.whine":{
                return BuiltinSound.ENTITY_WOLF_WHINE;
            }
            case "mob.zombie.death":{
                return BuiltinSound.ENTITY_ZOMBIE_DEATH;
            }
            case "mob.zombie.hurt":{
                return BuiltinSound.ENTITY_ZOMBIE_HURT;
            }
            case "mob.zombie.remedy":{
                return BuiltinSound.ENTITY_ZOMBIE_INFECT;
            }
            case "mob.zombie.say":{
                return BuiltinSound.ENTITY_ZOMBIE_AMBIENT;
            }
            case "mob.zombie.step":{
                return BuiltinSound.ENTITY_ZOMBIE_STEP;
            }
            case "mob.zombie.wood":{
                return BuiltinSound.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD;
            }
            case "mob.zombie.woodbreak":{
                return BuiltinSound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD;
            }
            case "mob.zombie_villager.death":{
                return BuiltinSound.ENTITY_ZOMBIE_VILLAGER_DEATH;
            }
            case "mob.zombie_villager.hurt":{
                return BuiltinSound.ENTITY_ZOMBIE_VILLAGER_HURT;
            }
            case "mob.zombie_villager.say":{
                return BuiltinSound.ENTITY_ZOMBIE_VILLAGER_AMBIENT;
            }
            case "mob.zombiepig.zpig":{
                return BuiltinSound.ENTITY_ZOMBIE_PIG_AMBIENT;
            }
            case "mob.zombiepig.zpigangry":{
                return BuiltinSound.ENTITY_ZOMBIE_PIG_ANGRY;
            }
            case "mob.zombiepig.zpigdeath":{
                return BuiltinSound.ENTITY_ZOMBIE_PIG_DEATH;
            }
            case "mob.zombiepig.zpighurt":{
                return BuiltinSound.ENTITY_ZOMBIE_PIG_HURT;
            }
            case "music.game":{
                return BuiltinSound.MUSIC_GAME;
            }
            case "music.game.creative":{
                return BuiltinSound.MUSIC_CREATIVE;
            }
            case "music.game.credits":{
                return BuiltinSound.MUSIC_CREDITS;
            }
            case "music.game.end":{
                return BuiltinSound.MUSIC_END;
            }
            case "music.game.endboss":{
                return BuiltinSound.MUSIC_DRAGON;
            }
            case "music.game.nether":{
                return BuiltinSound.MUSIC_NETHER;
            }
            case "music.menu":{
                return BuiltinSound.MUSIC_MENU;
            }
            case "note.bass":{
                return BuiltinSound.BLOCK_NOTE_BASS;
            }
            case "note.bassattack":{
                return BuiltinSound.BLOCK_NOTE_BASEDRUM;
            }
            case "note.bd":{
                return BuiltinSound.BLOCK_NOTE_CHIME;
            }
            case "note.bell":{
                return BuiltinSound.BLOCK_NOTE_BELL;
            }
            case "note.chime":{
                return BuiltinSound.BLOCK_NOTE_CHIME;
            }
            case "note.flute":{
                return BuiltinSound.BLOCK_NOTE_FLUTE;
            }
            case "note.guitar":{
                return BuiltinSound.BLOCK_NOTE_GUITAR;
            }
            case "note.harp":{
                return BuiltinSound.BLOCK_NOTE_HARP;
            }
            case "note.hat":{
                return BuiltinSound.BLOCK_NOTE_HAT;
            }
            case "note.iron_xylophone":{
                return BuiltinSound.BLOCK_NOTE_XYLOPHONE;
            }
            case "note.pling":{
                return BuiltinSound.BLOCK_NOTE_PLING;
            }
            case "note.snare":{
                return BuiltinSound.BLOCK_NOTE_SNARE;
            }
            case "note.xylophone":{
                return BuiltinSound.BLOCK_NOTE_XYLOPHONE;
            }
            case "portal.portal":{
                return BuiltinSound.BLOCK_PORTAL_AMBIENT;
            }
            case "portal.travel":{
                return BuiltinSound.BLOCK_PORTAL_TRAVEL;
            }
            case "portal.trigger":{
                return BuiltinSound.BLOCK_PORTAL_TRIGGER;
            }
            case "random.anvil_break":{
                return BuiltinSound.BLOCK_ANVIL_BREAK;
            }
            case "random.anvil_land":{
                return BuiltinSound.BLOCK_ANVIL_LAND;
            }
            case "random.anvil_use":{
                return BuiltinSound.BLOCK_ANVIL_USE;
            }
            case "random.bow":{
                return BuiltinSound.ENTITY_ARROW_SHOOT;
            }
            case "random.break":{
                return BuiltinSound.ENTITY_ITEM_BREAK;
            }
            case "random.burp":{
                return BuiltinSound.ENTITY_PLAYER_BURP;
            }
            case "random.chestclosed":{
                return BuiltinSound.BLOCK_CHEST_CLOSE;
            }
            case "random.chestopen":{
                return BuiltinSound.BLOCK_CHEST_OPEN;
            }
            case "random.click":{
                return BuiltinSound.UI_BUTTON_CLICK;
            }
            case "random.door_close":{
                return BuiltinSound.BLOCK_WOODEN_DOOR_CLOSE;
            }
            case "random.door_open":{
                return BuiltinSound.BLOCK_WOODEN_DOOR_OPEN;
            }
            case "random.drink":{
                return BuiltinSound.ENTITY_GENERIC_DRINK;
            }
            case "random.drink_honey":{
                return BuiltinSound.ENTITY_GENERIC_DRINK;
            }
            case "random.eat":{
                return BuiltinSound.ENTITY_GENERIC_EAT;
            }
            case "random.enderchestclosed":{
                return BuiltinSound.BLOCK_ENDERCHEST_CLOSE;
            }
            case "random.enderchestopen":{
                return BuiltinSound.BLOCK_ENDERCHEST_OPEN;
            }
            case "random.explode":{
                return BuiltinSound.ENTITY_GENERIC_EXPLODE;
            }
            case "random.glass":{
                return BuiltinSound.BLOCK_GLASS_STEP;
            }
            case "random.hurt":{
                return BuiltinSound.ENTITY_PLAYER_HURT;
            }
            case "random.levelup":{
                return BuiltinSound.ENTITY_PLAYER_LEVELUP;
            }
            case "random.orb":{
                return BuiltinSound.ENTITY_EXPERIENCE_ORB_PICKUP;
            }
            case "random.pop":{
                return BuiltinSound.BLOCK_LAVA_POP;
            }
            case "random.pop2":{
                return BuiltinSound.BLOCK_LAVA_POP;
            }
            case "random.potion.brewed":{
                return BuiltinSound.BLOCK_BREWING_STAND_BREW;
            }
            case "random.shulkerboxclosed":{
                return BuiltinSound.BLOCK_SHULKER_BOX_CLOSE;
            }
            case "random.shulkerboxopen":{
                return BuiltinSound.BLOCK_SHULKER_BOX_OPEN;
            }
            case "random.splash":{
                return BuiltinSound.ENTITY_GENERIC_SPLASH;
            }
            case "random.swim":{
                return BuiltinSound.ENTITY_PLAYER_SWIM;
            }
            case "random.toast":{
                return BuiltinSound.UI_TOAST_CHALLENGE_COMPLETE;
            }
            case "random.totem":{
                return BuiltinSound.ITEM_TOTEM_USE;
            }
            case "record.11":{
                return BuiltinSound.RECORD_11;
            }
            case "record.13":{
                return BuiltinSound.RECORD_13;
            }
            case "record.blocks":{
                return BuiltinSound.RECORD_BLOCKS;
            }
            case "record.cat":{
                return BuiltinSound.RECORD_CAT;
            }
            case "record.chirp":{
                return BuiltinSound.RECORD_CHIRP;
            }
            case "record.far":{
                return BuiltinSound.RECORD_FAR;
            }
            case "record.mall":{
                return BuiltinSound.RECORD_MALL;
            }
            case "record.mellohi":{
                return BuiltinSound.RECORD_MELLOHI;
            }
            case "record.stal":{
                return BuiltinSound.RECORD_STAL;
            }
            case "record.strad":{
                return BuiltinSound.RECORD_STRAD;
            }
            case "record.wait":{
                return BuiltinSound.RECORD_WAIT;
            }
            case "record.ward":{
                return BuiltinSound.RECORD_WARD;
            }
            case "tile.piston.in":{
                return BuiltinSound.BLOCK_PISTON_CONTRACT;
            }
            case "tile.piston.out":{
                return BuiltinSound.BLOCK_PISTON_EXTEND;
            }
            default:{
                return BuiltinSound.ITEM_SHIELD_BLOCK;
            }
        }
    }
}
