package com.mytomcat;

import com.mytomcat.connector.NettyServer;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-20 12:59
 **/
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();

    }
}



