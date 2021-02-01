package me.liuli.ez4h.translators.converters;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.opennbt.tag.builtin.*;
import com.nukkitx.nbt.NbtList;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;

import java.util.ArrayList;
import java.util.Map;

public class ItemConverter {
    private final JSONObject BedrockItemMap;
    private final JSONObject JavaItemMap;
    private final JSONObject BedrockEnchantMap;
    private final JSONObject JavaEnchantMap;

    public ItemConverter(JSONObject bedrock, JSONObject java, JSONObject enchant) {
        BedrockItemMap = bedrock;
        JavaItemMap = java;
        BedrockEnchantMap = enchant.getJSONObject("bedrock");
        JavaEnchantMap = enchant.getJSONObject("java");
    }

    public int inventoryIndex(int index, boolean isToBedrock) {
        if (isToBedrock) {
            if (index > 35) {
                return index - 36;
            } else {
                return index;
            }
        } else {
            if (index < 9) {
                return index + 36;
            } else {
                return index;
            }
        }
    }

    public Tag nbtTagTranslator(String name, Object value) {
        if (value instanceof NbtMap) {
            return nbtMapTranslator(name, (NbtMap) value, false);
        } else if (value instanceof NbtList) {
            try {
                return nbtListTranslator(name, (NbtList) value);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } else if (value instanceof String) {
            return new StringTag(name, (String) value);
        } else if (value instanceof Byte) {
            return new ByteTag(name, (Byte) value);
        } else if (value instanceof Double) {
            return new DoubleTag(name, (Double) value);
        } else if (value instanceof Float) {
            return new FloatTag(name, (Float) value);
        } else if (value instanceof Integer) {
            return new IntTag(name, (Integer) value);
        } else if (value instanceof Long) {
            return new LongTag(name, (Long) value);
        } else if (value instanceof Short) {
            return new ShortTag(name, (Short) value);
        }
        return null;
    }

    public ListTag nbtListTranslator(String name, NbtList nbtList) {
        ArrayList<Tag> tags = new ArrayList<>();
        for (Object o : nbtList) {
            tags.add(nbtTagTranslator("", o));
        }
        if (tags.size() != 0) {
            return new ListTag(name, tags);
        } else {
            return null;
        }
    }

    public CompoundTag nbtMapTranslator(String name, NbtMap nbtMap, boolean isFirst) {
        CompoundTag compoundTag = new CompoundTag(name);
        if (nbtMap != null) {
            for (Map.Entry<String, Object> e : nbtMap.entrySet()) {
                if (isFirst) {
                    if (e.getKey().equals("ench") && e.getValue() instanceof NbtList) {
                        try {
                            ArrayList<Tag> tags = new ArrayList<>();
                            for (Object o : (NbtList) e.getValue()) {
                                NbtMap enchMap = (NbtMap) o;
                                CompoundTag tag = new CompoundTag("");
                                tag.put(new ShortTag("id", getJavaEnchant(enchMap.getShort("id"))));
                                tag.put(new ShortTag("lvl", enchMap.getShort("lvl")));
                                tags.add(tag);
                            }
                            compoundTag.put(new ListTag("ench", tags));
                            continue;
                        } catch (IllegalArgumentException illegalArgumentException) {
                            illegalArgumentException.printStackTrace();
                        }
                    }
                }
                Tag tag = nbtTagTranslator(e.getKey(), e.getValue());
                if (tag != null) {
                    compoundTag.put(tag);
                }
            }
        }
        return compoundTag;
    }

    public ItemStack convertToJE(ItemData itemData) {
        int id = 1, data = 0;
        String item = (String) JavaItemMap.get(BedrockItemMap.get(itemData.getId() + ":" + itemData.getDamage()));
        if (item != null) {
            String[] splitData = item.split(":");
            id = new Integer(splitData[0]);
            if (item.contains(":")) {
                data = new Integer(splitData[1]);
            } else {
                data = itemData.getDamage();
            }
        }
        CompoundTag tag = nbtMapTranslator("", itemData.getTag(), true);
        if (tag.contains("Damage") && tag.size() == 1) {
            tag = null;
        }
        return new ItemStack(id, itemData.getCount(), data, tag);
    }

    public short getJavaEnchant(short id) {
        String result = BedrockEnchantMap.getString(id + "");
        if (result == null) {
            return id;
        }
        Short javaResult = JavaEnchantMap.getShort(result);
        if (javaResult == null) {
            return id;
        }
        return javaResult;
    }
}
