package MultiThread.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import org.apache.log4j.Logger;
import qidong.Tray;

/**
 * Demo class
 * @author ls
 * @date 20-2-27
 */
public class MockClientHandler extends
        SimpleChannelInboundHandler<Object> {

    private final WebSocketClientHandshaker webSocketClientHandshaker;
    private static Logger logger = Logger.getLogger(MockClientHandler.class);

    public MockClientHandler(WebSocketClientHandshaker webSocketClientHandshaker) {
        this.webSocketClientHandshaker = webSocketClientHandshaker;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        ByteBuf buf = (ByteBuf) o;
        byte[] barray = new byte[buf.readableBytes()];
        buf.getBytes(0, barray);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(new String(barray));
        String s;
        if (stringBuilder.length() > 0) {
            s= String.valueOf(stringBuilder.delete(0,2));
            logger.info("服务端传来消息：   "+stringBuilder);
            System.out.flush();
           // s.split(":")[1]
        } else {
            System.out.println("不能读啊");
        }
            buf.release();

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        webSocketClientHandshaker.handshake(ctx.channel());
    }

}