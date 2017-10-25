package com.jetsen.pack.optram;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jetsen.pack.optram.bean.RedisKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * http test
 * Created by lenovo on 2017/10/24.
 */
public class HttpTest {
    private static Logger logger = LogManager.getLogger("HttpTest");

    static void testPostJson(){
        String json = "[{\"key\":\"first\"},{\"key\":\"second\",\"value\":\"cc\"}]";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> s = restTemplate.postForEntity("http://localhost:8080/redis/getForValues",formEntity,String.class);
        s.getBody();
        logger.info(s.getBody());
    }

    static void testPostVal(){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("key","0080:2017-10-18:normal");
        params.set("value","1");
        logger.info(restTemplate.postForEntity("http://localhost:8080/redis/setForValue",params,String.class));
        params.set("key","0080:2017-10-18:special");
        params.set("value","1");
        logger.info(restTemplate.postForEntity("http://localhost:8080/redis/setForValue",params,String.class));
        params.set("key","0080:2017-10-19:normal");
        params.set("value","1");
        logger.info(restTemplate.postForEntity("http://localhost:8080/redis/setForValue",params,String.class));
        params.set("key","0080:2017-10-19:special");
        params.set("value","1");
        logger.info(restTemplate.postForEntity("http://localhost:8080/redis/setForValue",params,String.class));

    }

    void testHttpgetVal(){
        RestTemplate restTemplate = new RestTemplate();
        logger.info(restTemplate.getForEntity("http://localhost:8080/redis/getForValue?key={key}",String.class,"hello"));
    }

    static void testSocketHeartServer(){
        for(int i=0;i<1;i++){
            OperatorTest op = new OperatorTest();
            Thread t = new Thread(op);
            t.start();
        }
    }

    static void json2Obj(){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<RedisKey>>(){}.getType();
        List<RedisKey> redisObjList = gson.fromJson("[{'key':'first','value':null},{'key':'second','value':'cc'}]",listType);
        logger.debug(StringUtils.isEmpty(redisObjList.get(0).getValue()));
        logger.debug(redisObjList.get(1).getKey());
        logger.debug(gson.toJson(redisObjList,listType));
    }

    public static void main(String arg[]) throws UnknownHostException, IOException, InterruptedException{
        testPostVal();
    }

}
