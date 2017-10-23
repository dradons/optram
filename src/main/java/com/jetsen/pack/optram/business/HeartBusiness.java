package com.jetsen.pack.optram.business;

import com.jetsen.pack.optram.bean.SystemStatusReportRequest;
import com.jetsen.pack.optram.bean.SystemStatusReportResponse;
import com.jetsen.pack.optram.netty.ByteOper;
import com.jetsen.pack.optram.util.XMLUtil;
import io.netty.util.internal.StringUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;


/**
 * 与下级机心跳处理业务类（反馈系统状态及包装单版本）
 */
public class HeartBusiness {
    private static Logger logger = Logger.getLogger(HeartBusiness.class);
    public static String doBusiness(String request){
        /*SystemStatusReportRequest req = (SystemStatusReportRequest)XMLUtil.convertXmlStrToObject(SystemStatusReportRequest.class,msg);
        String template = "%s:%s:%s";
        SystemStatusReportResponse response = new SystemStatusReportResponse();
        response.setChannelCode("001");
        response.setPlayDate("2017-12-12");
        response.setNormalPackingTodayVersion("1");
        response.setNormalPackingNextDayVersion("2");
        response.setSpecialPackingNextDayVersion("1");
        response.setSpecialPackingTodayVersion("");
        response.setSystemStatus("1");*/

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
        logger.debug("channelcode--"+channelcode+"--playdate"+playdate);
        String nextday = getNextday(playdate);
        Document document = DocumentHelper.createDocument(); // 创建文档
        document.setXMLEncoding(ByteOper.characterEncoding);
        Element root = document.addElement("SystemStatusReportResponse");
        Element ChannelCode = root.addElement("ChannelCode");
        ChannelCode.addText(channelcode);
        Element PlayDate = root.addElement("PlayDate");
        PlayDate.addText(playdate);
        Element SystemStatus = root.addElement("SystemStatus");
        SystemStatus.addText("0");

        TaskInfo normaltaskinfo = TaskInfo.getTaskInfo(channelcode+playdate);
        Element NormalPackingTodayVersion = root.addElement("NormalPackingTodayVersion");
        Element SpecialPackingTodayVersion = root.addElement("SpecialPackingTodayVersion");
        if(normaltaskinfo!=null){
            if(!StringUtils.isEmpty(normaltaskinfo.getSpecialPackingTodayVersion())){
                SpecialPackingTodayVersion.addText(normaltaskinfo.getSpecialPackingTodayVersion());
            }
            if(!StringUtils.isEmpty(normaltaskinfo.getNormalPackingTodayVersion())){//&&normaltaskinfo.getNormalPackingTodayVersion()!="0"
                NormalPackingTodayVersion.addText(normaltaskinfo.getNormalPackingTodayVersion());
            }

        }
        TaskInfo normalnexttaskinfo = TaskInfo.getTaskInfo(channelcode+nextday);
        Element NormalPackingNextDayVersion = root.addElement("NormalPackingNextDayVersion");
        Element SpecialPackingNextDayVersion = root.addElement("SpecialPackingNextDayVersion");
        if(normalnexttaskinfo!=null){
            if(!StringUtils.isEmpty(normalnexttaskinfo.getSpecialPackingTodayVersion())){
                SpecialPackingNextDayVersion.addText(normalnexttaskinfo.getSpecialPackingTodayVersion());
            }
            if(!StringUtils.isEmpty(normalnexttaskinfo.getNormalPackingTodayVersion())){//&&normalnexttaskinfo.getNormalPackingTodayVersion()!="0"
                NormalPackingNextDayVersion.addText(normalnexttaskinfo.getNormalPackingTodayVersion());
            }
        }
        return document.asXML();
    }
}
