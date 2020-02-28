package MultiThread.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;

/**
 * Demo class
 *
 * @author ls
 * @date 20-2-27
 */
public class Myclient extends Thread {
    public static EventLoopGroup eventLoopGroup;
    public  void init() {
         eventLoopGroup = new EpollEventLoopGroup();//工作组，实现数据的读写
        try {
            URI uri = new URI("ws://127.0.0.1:8080/websocket/EasyArch");
            Bootstrap bootstrap = new Bootstrap();
             final MockClientHandler webSocketClientHandler = new MockClientHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(uri
                            , WebSocketVersion.V13
                            , null
                            , false
                            , new DefaultHttpHeaders()));
            bootstrap.group(eventLoopGroup).channel(EpollSocketChannel.class).
                    handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HttpClientCodec());
                            socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                            socketChannel.pipeline().addLast(webSocketClientHandler);
                        }
                    });
            Channel channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
    @Override
    public void run(){
           init();
    }

}
