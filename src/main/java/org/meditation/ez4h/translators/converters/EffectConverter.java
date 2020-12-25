package org.meditation.ez4h.translators.converters;

import com.github.steveice10.mc.protocol.data.game.entity.Effect;

public class EffectConverter {
    public static Effect converter(int effectId){
        switch (effectId){
            case 1:{
                return Effect.SPEED;
            }
            case 2:{
                return Effect.SLOWNESS;
            }
            case 3:{
                return Effect.DIG_SPEED;
            }
            case 4:{
                return Effect.DIG_SLOWNESS;
            }
            case 6:{
                return Effect.HEAL;
            }
            case 7:{
                return Effect.DAMAGE;
            }
            case 8:{
                return Effect.JUMP_BOOST;
            }
            case 9:{
                return Effect.CONFUSION;
            }
            case 10:{
                return Effect.REGENERATION;
            }
            case 11:{
                return Effect.RESISTANCE;
            }
            case 12:{
                return Effect.FIRE_RESISTANCE;
            }
            case 13:{
                return Effect.WATER_BREATHING;
            }
            case 14:{
                return Effect.INVISIBILITY;
            }
            case 15:{
                return Effect.BLINDNESS;
            }
            case 16:{
                return Effect.NIGHT_VISION;
            }
            case 17:{
                return Effect.HUNGER;
            }
            case 18:{
                return Effect.WEAKNESS;
            }
            case 19:
            case 25: {
                return Effect.POISON;
            }
            case 20:{
                return Effect.WITHER_EFFECT;
            }
            case 21:{
                return Effect.HEALTH_BOOST;
            }
            case 22:{
                return Effect.ABSORPTION;
            }
            case 23:{
                return Effect.SATURATION;
            }
            case 24:{
                return Effect.LEVITATION;
            }
            case 28:{
                return Effect.BAD_LUCK;
            }
            case 29:{
                return Effect.LUCK;
            }
            default:
                return Effect.LUCK;
        }
    }
}
