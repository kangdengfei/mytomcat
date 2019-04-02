import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;
import io.netty.util.HashedWheelTimer;

import java.nio.charset.Charset;

/**
 * @program: mytomcat
 * @author: KDF
 * @create: 2019-02-20 14:34
 **/
public class NettyClientHandler extends SimpleChannelInboundHandler<HttpRequest> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}



