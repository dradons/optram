package com.jetsen.pack.optram.netty;

import com.jetsen.pack.optram.business.HeartBusiness;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 下级机心跳处理
 * Created by lenovo on 2017/10/18.
 */
public class HeartSeverHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LogManager.getLogger(HeartSeverHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
        String body = (String) msg;
        String resp = HeartBusiness.doBusiness(body);
        ByteBuf message =  Unpooled.copiedBuffer(resp.getBytes());
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}
