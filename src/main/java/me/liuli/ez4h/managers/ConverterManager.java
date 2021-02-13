package me.liuli.ez4h.managers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import me.liuli.ez4h.translators.converters.BlockConverter;
import me.liuli.ez4h.translators.converters.FormConverter;
import me.liuli.ez4h.translators.converters.ItemConverter;
import me.liuli.ez4h.translators.converters.MetadataConverter;
import me.liuli.ez4h.utils.FileUtil;

public class ConverterManager {
    @Getter
    private final BlockConverter blockConverter;
    @Getter
    private final FormConverter formConverter;
    @Getter
    private final ItemConverter itemConverter;
    @Getter
    private final MetadataConverter metadataConverter;

    public ConverterManager() {
        blockConverter = new BlockConverter(JSONArray.parseArray(FileUtil.getTextFromResource("resources/blocks.json")), JSONObject.parseObject(FileUtil.getTextFromResource("resources/block_runtime.json")));
        formConverter = new FormConverter();
        itemConverter = new ItemConverter(JSONObject.parseObject(FileUtil.getTextFromResource("resources/bedrock_items.json")), JSONObject.parseObject(FileUtil.getTextFromResource("resources/java_items.json")), JSONObject.parseObject(FileUtil.getTextFromResource("resources/enchant.json")));
        metadataConverter = new MetadataConverter();
    }
}
