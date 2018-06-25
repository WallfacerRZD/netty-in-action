package chapter13;

import java.net.InetSocketAddress;

/**
 * @author WallfacerRZD
 * @date 2018/6/25 14:52
 */
public final class LogEvent {
    public static final byte SEPARATOR = (byte) ':';

    private final InetSocketAddress source;

    private final String logfile;

    private final String msg;

    private final long received;

    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }

    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public static byte getSEPARATOR() {
        return SEPARATOR;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceived() {
        return received;
    }
}
