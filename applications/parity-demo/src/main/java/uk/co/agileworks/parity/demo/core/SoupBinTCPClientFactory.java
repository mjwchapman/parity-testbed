package uk.co.agileworks.parity.demo.core;

import java.io.IOException;
import java.net.*;
import java.nio.channels.SocketChannel;

import com.paritytrading.nassau.soupbintcp.SoupBinTCPClient;
import com.paritytrading.parity.net.poe.POE;

import uk.co.agileworks.parity.demo.domain.*;

public class SoupBinTCPClientFactory {
    
    private InetSocketAddress address;

    public SoupBinTCPClientFactory(InetSocketAddress address) {
        this.address = address;
    }

    public SoupBinTCPClient transport(EventListener<AgentEvent> listener) throws IOException {

        SocketChannel channel = SocketChannel.open();
        channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        channel.connect(address);
        channel.configureBlocking(false);

        return new SoupBinTCPClient(
                channel,
                POE.MAX_OUTBOUND_MESSAGE_LENGTH,
                new AgentListener(listener),
                new AgentStatusListener(listener));
    }

}
