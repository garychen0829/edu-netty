package com.itgary.netty.pro4.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gary.chen on 2016/9/9.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private final ByteBuf firstMesg;

    public TimeClientHandler(){
        byte[] req = "QUERY TIME ORDER".getBytes();
        firstMesg = Unpooled.buffer(req.length);
        firstMesg.writeBytes(req);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        System.out.println("连接.."+remoteAddress);
        System.out.println("哈哈.."+localAddress);
        System.out.println("呵呵.."+promise);

        super.connect(ctx, remoteAddress, localAddress, promise);


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMesg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8");

        System.out.println("OutPut:{}" + body);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Now is : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
