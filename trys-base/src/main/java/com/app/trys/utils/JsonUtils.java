package com.app.trys.utils;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JsonUtils {

    public static JSONObject readFromFile(String path){
        File file = new File(path);
        return Actions.doTry(() -> {
            JSONReader jo = JSONReader.of(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            return JSONObject.parseObject(jo.readString());
        }, "读取文件失败");

    }
}
