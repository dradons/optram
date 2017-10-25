package com.jetsen.pack.optram;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jetsen.pack.optram.bean.RedisKey;
import com.jetsen.pack.optram.netty.ByteOper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class OperatorTest implements Runnable{
	private static Logger logger = LogManager.getLogger(OperatorTest.class);
	private Socket client;
	public void run() {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String reqStr = null;
		try {
			client = new Socket("127.0.0.1",20001);
			outputStream = client.getOutputStream();
			// 接收数据后业务处理
			StringBuilder sb = new StringBuilder();
			/*for(int i=0;i<2;i++){
				sb.append("<>helloeveryone"+i);
			}*/
//			String resultString = "QUERY TIME ORDER";//
			String resultString = "";
			resultString+="<root>";
			resultString+="<ChannelCode>0080</ChannelCode><PlayDate>2017-10-18</PlayDate><PlayListType>1</PlayListType>";
			resultString+="</root>";
			String code = "gbk";
//			String resultString = sb.toString();
			logger.info(Thread.currentThread().getId()+"-请求体-"+resultString);
			logger.info(Thread.currentThread().getId()+"-请求长度-"+resultString.getBytes(code).length);
//			byte[] head1 = ByteOper.intToByte4(resultString.getBytes(code).length);
			byte[] head = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(resultString.getBytes(code).length).array();
			byte[] body = resultString.getBytes(code);
//			outputStream.write(ByteOper.bytesMerger(head, body));//体
			outputStream.write(head);
			outputStream.write(body);
			inputStream = client.getInputStream();
			int byteLength =4;
			byte[] Lengthbuffer = new byte[byteLength];
			inputStream.read(Lengthbuffer);//读取 总长度
			int byteSize = ByteOper.byte4ToInt(Lengthbuffer, 0);//传输总长度
			byte[] buffer = new byte[byteSize];
			inputStream.read(buffer);//读取内容
			reqStr = new String(buffer,ByteOper.characterEncoding);
			logger.info(Thread.currentThread().getId()+"-"+"length-"+byteSize+"-结果-"+reqStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(inputStream!=null){

				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(this.client != null && !(this.client.isConnected())){
				try {
					this.client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String arg[]) throws UnknownHostException, IOException, InterruptedException{
        testSocketHeartServer();
//        json2Obj();
	}


    static void testSocketHeartServer(){
        for(int i=0;i<1;i++){
            OperatorTest op = new OperatorTest();
            Thread t = new Thread(op);
            t.start();
        }
    }

}
