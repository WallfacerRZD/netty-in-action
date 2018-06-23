package chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author WallfacerRZD
 * @date 2018/6/23 23:33
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler
        <ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
