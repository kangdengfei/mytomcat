package com.mytomcat.connector;

import com.mytomcat.Router.RouterContext;
import com.mytomcat.Router.imp.DefaultRouterContext;
import com.mytomcat.bean.imp.DefaultBeanContext;
import com.mytomcat.common.CommonConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-18 23:05
 **/
public class NettyServer implements Server {
    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Override
    public void preStart() {
        DefaultBeanContext defaultBeanContext = (DefaultBeanContext)DefaultBeanContext.getInstance();
        DefaultRouterContext routerContext =(DefaultRouterContext) DefaultRouterContext.getInstance();
        defaultBeanContext.init();
        routerContext.init();


    }

    @Override
    public void start()  {
        preStart();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            long start = System.currentTimeMillis();
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyHttpServerInitializer());
            ChannelFuture future = b.bind(CommonConstants.SERVER_PORT).sync();
            long cost = System.currentTimeMillis()-start;
            logger.info("[NettyHttpServer] Startup at port:{} cost:{}[ms]",CommonConstants.SERVER_PORT,cost);
            //等待服务端Socket关闭
            future.channel().closeFuture().sync();
        }catch (Exception e){
            logger.error("[NettyServer] InterruptedException",e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}



