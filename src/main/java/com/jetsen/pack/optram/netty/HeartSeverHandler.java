package com.jetsen.pack.optram.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * 下级机心跳处理
 * Created by lenovo on 2017/10/18.
 */
public class HeartSeverHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = Logger.getLogger(HeartSeverHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
        String body = (String) msg;
        logger.debug(body);
        logger.debug(body.length());
        ByteBuf message =  Unpooled.copiedBuffer(body.getBytes());
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
