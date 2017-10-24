package com.jetsen.pack.optram.business;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jetsen.pack.optram.bean.RedisKey;
import com.jetsen.pack.optram.netty.ByteOper;
import com.jetsen.pack.optram.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 与下级机心跳处理业务类（反馈系统状态及包装单版本）
 */
@Component
@Configuration
public class HeartBusiness {
    private static  RestTemplate restTemplate = new RestTemplate();
    private static  StringRedisTemplate redisTemp;
    private static final String noramlTemp = "%s:%s:normal";
    private static final String specialTemp = "%s:%s:special";
    private static Logger logger = LogManager.getLogger(HeartBusiness.class);
    public static  String doBusiness(String request){
        String channelcode="";
        String playdate ="";
        try {
            Document requestdoc = DocumentHelper.parseText(request);
            Element requestRoot = requestdoc.getRootElement();
            channelcode = requestRoot.element("ChannelCode").getTextTrim();
            playdate = requestRoot.element("PlayDate").getTextTrim();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Document document = DocumentHelper.createDocument(); // 创建文档
        document.setXMLEncoding(ByteOper.characterEncoding);
        Element root = document.addElement("SystemStatusReportResponse");
        Element ChannelCode = root.addElement("ChannelCode");
        ChannelCode.addText(channelcode);
        Element PlayDate = root.addElement("PlayDate");
        PlayDate.addText(playdate);
        Element SystemStatus = root.addElement("SystemStatus");
        SystemStatus.addText("0");
        //build request Object
        String taskNormalKey = String.format(noramlTemp,channelcode,playdate);
        String taskSpecialKey = String.format(specialTemp,channelcode,playdate);
        String nextDay = DateUtil.getBeforeOrAfterDay(DateUtil.str2Date(playdate,"yyyy-MM-dd"),1,"yyyy-MM-dd");
        String taskNextDayNormalKey = String.format(noramlTemp,channelcode,nextDay);
        String taskNextDaySpecialKey = String.format(specialTemp,channelcode,nextDay);
        List<RedisKey> redisKeys = new ArrayList<RedisKey>();
        RedisKey normalkey =new RedisKey(taskNormalKey,null);
        RedisKey specialkey =new RedisKey(taskSpecialKey,null);
        RedisKey nextnormalkey =new RedisKey(taskNextDayNormalKey,null);
        RedisKey nextspecialkey =new RedisKey(taskNextDaySpecialKey,null);
        redisKeys.add(normalkey);
        redisKeys.add(specialkey);
        redisKeys.add(nextnormalkey);
        redisKeys.add(nextspecialkey);
        //convert list to string of json
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<RedisKey>>(){}.getType();
        String redisJson = gson.toJson(redisKeys,listType);
        //http request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(redisJson, headers);
        ResponseEntity<String> resoEntity = restTemplate.postForEntity("http://localhost:8080/redis/getForValues",formEntity,String.class);
        String resoBody = resoEntity.getBody();
        //handle response body(json)
        redisKeys = gson.fromJson(resoBody,listType);
        Element NormalPackingTodayVersion = root.addElement("NormalPackingTodayVersion");
        Element SpecialPackingTodayVersion = root.addElement("SpecialPackingTodayVersion");
        if(!StringUtils.isEmpty(redisKeys.get(0).getValue())){
            NormalPackingTodayVersion.addText(redisKeys.get(0).getValue());
        }
        if(!StringUtils.isEmpty(redisKeys.get(1).getValue())){
            SpecialPackingTodayVersion.addText(redisKeys.get(1).getValue());
        }
        Element NormalPackingNextDayVersion = root.addElement("NormalPackingNextDayVersion");
        Element SpecialPackingNextDayVersion = root.addElement("SpecialPackingNextDayVersion");
        if(!StringUtils.isEmpty(redisKeys.get(2).getValue())){
            NormalPackingNextDayVersion.addText(redisKeys.get(2).getValue());
        }
        if(!StringUtils.isEmpty(redisKeys.get(3).getValue())){
            SpecialPackingNextDayVersion.addText(redisKeys.get(3).getValue());
        }
        return document.asXML();
    }

}
