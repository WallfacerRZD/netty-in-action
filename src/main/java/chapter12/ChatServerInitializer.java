package chapter12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author WallfacerRZD
 * @date 2018/6/25 13:51
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {
    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                new HttpServerCodec(),
                new ChunkedWriteHandler(),
                new HttpObjectAggregator(64 * 1024),
                new HttpRequestHandler("/ws"),
                new WebSocketServerProtocolHandler("/ws"),
                new TextWebSocketFrameHandler(group)
        );
    }
}
