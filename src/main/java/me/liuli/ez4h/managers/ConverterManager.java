package me.liuli.ez4h.managers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import me.liuli.ez4h.EZ4H;
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

    public ConverterManager(){
        blockConverter=new BlockConverter(JSONArray.parseArray(FileUtils.readJarText("resources/blocks.json", EZ4H.getJarDir())), JSONObject.parseObject(FileUtils.readJarText("resources/block_runtime.json",EZ4H.getJarDir())));
        formConverter=new FormConverter();
        itemConverter=new ItemConverter(JSONObject.parseObject(FileUtils.readJarText("resources/bedrock_items.json",EZ4H.getJarDir())),JSONObject.parseObject(FileUtils.readJarText("resources/java_items.json",EZ4H.getJarDir())),JSONObject.parseObject(FileUtils.readJarText("resources/enchant.json",EZ4H.getJarDir())));
        metadataConverter=new MetadataConverter();
    }
}
