package me.liuli.ez4h.managers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import me.liuli.ez4h.translators.converters.BlockConverter;
import me.liuli.ez4h.translators.converters.FormConverter;
import me.liuli.ez4h.translators.converters.ItemConverter;
import me.liuli.ez4h.translators.converters.MetadataConverter;
import me.liuli.ez4h.utils.FileUtils;

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
        blockConverter = new BlockConverter(JSONArray.parseArray(FileUtils.getTextFromResource("resources/blocks.json")), JSONObject.parseObject(FileUtils.getTextFromResource("resources/block_runtime.json")));
        formConverter = new FormConverter();
        itemConverter = new ItemConverter(JSONObject.parseObject(FileUtils.getTextFromResource("resources/bedrock_items.json")), JSONObject.parseObject(FileUtils.getTextFromResource("resources/java_items.json")), JSONObject.parseObject(FileUtils.getTextFromResource("resources/enchant.json")));
        metadataConverter = new MetadataConverter();
    }
}
