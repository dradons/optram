package com.jetsen.pack.optram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jetsen.pack.optram.bean.MultiKey;
import com.jetsen.pack.optram.bean.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * get and set task version use redis
 * Created by yyf on 2017/10/17.
 */
@RestController
public class TaskVersionController {

    @Autowired
    private StringRedisTemplate template;

    /**
     * 操作Redis
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value="/redis/setForValue",method = RequestMethod.POST)
    @ResponseBody
    String setForValue(@RequestParam String key,@RequestParam String value){
        String result = "ok";
        ValueOperations<String, String> ops = this.template.opsForValue();
        if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value) ){
            ops.set(key, value);//ops.set(key, value,5,TimeUnit.MINUTES);暂时不处理过期
        }else{
            result = "key can not be null ";
        }
        return result;
    }

    @RequestMapping(value = "/redis/getForValue",method = RequestMethod.GET)
    @ResponseBody
    String getForValue(@RequestParam String key){
        String result = null;
        ValueOperations<String, String> ops = this.template.opsForValue();
        if(!StringUtils.isEmpty(key) ){
            result = ops.get(key);
        }
        return result;
    }

    @RequestMapping(value = "/redis/getForValues",method = RequestMethod.POST)
    @ResponseBody
    List<RedisKey> getForValues(@RequestBody String keys){
//        MultiKey multiKey = new MultiKey();
        List<RedisKey> redisKeys = new ArrayList<RedisKey>();
        //解析json
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<RedisKey>>(){}.getType();
        List<RedisKey> redisObjList = gson.fromJson(keys,listType);
        // 构造redis key 请求list
        List<String> keyList = new ArrayList<String>();
        for(RedisKey rediskey : redisObjList){
            keyList.add(rediskey.getKey());
        }
        //集合方式请求redis
        ValueOperations<String, String> ops = this.template.opsForValue();
        List<String> keyValList = ops.multiGet(keyList);
        //处理redis结果
        for(int i = 0;i<keyValList.size(); i++){
            RedisKey redisKey = new RedisKey();
            redisKey.setKey(keyList.get(i));
            redisKey.setValue(keyValList.get(i));
            redisKeys.add(redisKey);
        }
//        multiKey.setKeys(redisKeys);
        return redisKeys;
    }
}
