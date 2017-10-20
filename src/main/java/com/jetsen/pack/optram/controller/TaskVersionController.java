package com.jetsen.pack.optram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2017/10/17.
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
    String setForValue(String key,String value){
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
    String getForValue(String key){
        String result = null;
        ValueOperations<String, String> ops = this.template.opsForValue();
        if(!StringUtils.isEmpty(key) ){
            result = ops.get(key);
        }else{
            result = "key can not be null ";
        }
        return result;
    }
}
