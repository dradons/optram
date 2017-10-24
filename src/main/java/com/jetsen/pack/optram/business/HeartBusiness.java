package com.jetsen.pack.optram.business;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;


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

        //通过http获取任务版本信息
        String taskNormalKey = String.format(noramlTemp,channelcode,playdate);
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:8080/redis/getForValue?key={key}",String.class,taskNormalKey);
        String noramlVersion = (String) response.getBody();

        //通过http获取特殊任务版本信息
        String taskSpecialKey = String.format(specialTemp,channelcode,playdate);
        //get special version
        ResponseEntity<?> resp = restTemplate.getForEntity("http://localhost:8080/redis/getForValue?key={key}",String.class,taskSpecialKey);
        String specialVersion = (String) resp.getBody();

        Element NormalPackingTodayVersion = root.addElement("NormalPackingTodayVersion");
        Element SpecialPackingTodayVersion = root.addElement("SpecialPackingTodayVersion");
        if(!StringUtils.isEmpty(noramlVersion)){
            NormalPackingTodayVersion.addText(noramlVersion);
        }
        if(!StringUtils.isEmpty(specialVersion)){
            SpecialPackingTodayVersion.addText(specialVersion);
        }
        String nextDay = DateUtil.getBeforeOrAfterDay(new Date(),1,"yyyy-MM-dd");

//        Element NormalPackingNextDayVersion = root.addElement("NormalPackingNextDayVersion");
//        Element SpecialPackingNextDayVersion = root.addElement("SpecialPackingNextDayVersion");

        return document.asXML();
    }

}
