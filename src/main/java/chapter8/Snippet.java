package chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioDatagramChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;

/**
 * @author WallfacerRZD
 * @date 2018/6/22 23:10
 */
public class Snippet {
    public static void listing8_1() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx,
                                                ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        ChannelFuture future = bootstrap.connect(
                new InetSocketAddress("www.manning.com", 80)
        );
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                System.out.println("Connection established");
            } else {
                System.err.println("Connection attempt failed");
                future1.cause().printStackTrace();
            }
        });
    }

    public static void listing8_4() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx,
                                                ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Server bind");
                } else {
                    System.out.println("Bound attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    public static void listing8_5() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture connectFuture;

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.channel(NioSocketChannel.class).handler(
                                new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx,
                                                                ByteBuf msg) throws Exception {
                                        System.out.println("Received data");
                                    }
                                }
                        );
                        bootstrap.group(ctx.channel().eventLoop());
                        connectFuture = bootstrap.connect(
                                new InetSocketAddress("www.manning.com", 80)
                        );
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx,
                                                ByteBuf msg) throws Exception {
                        if (connectFuture.isDone()) {
                            // do something with the data
                        }
                    }
                });
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    public static void listing8_6() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new HttpClientCodec())
                                .addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
                    }
                });
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.sync();
    }

    public static void listing8_7() {
        final AttributeKey<Integer> id = AttributeKey.newInstance("ID");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        Integer idValue = ctx.channel().attr(id).get();
                        // do something with the idValue
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx,
                                                ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        bootstrap.attr(id, 123456);
        ChannelFuture future = bootstrap.connect(
                new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();
    }

    public static void listing8_8() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new OioEventLoopGroup())
                .channel(OioDatagramChannel.class)
                .handler(
                        new SimpleChannelInboundHandler<DatagramPacket>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                                // Do something with the packet
                            }
                        }
                );
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(0));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Channel bound");
                } else {
                    System.err.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    public static void listing8_9() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class);
        Future<?> future = group.shutdownGracefully();
        future.syncUninterruptibly();
    }
}
