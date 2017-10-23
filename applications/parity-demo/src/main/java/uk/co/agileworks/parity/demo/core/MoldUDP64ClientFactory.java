package uk.co.agileworks.parity.demo.core;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;

import com.paritytrading.nassau.moldudp64.MoldUDP64Client;
import com.paritytrading.parity.net.pmd.PMDParser;

import uk.co.agileworks.parity.demo.domain.*;

public class MoldUDP64ClientFactory {

    private NetworkInterface  multicastInterface;
    private InetSocketAddress multicastAddress;
    private InetSocketAddress requestAddress;

    public MoldUDP64ClientFactory(NetworkInterface multicastInterface,
            InetSocketAddress multicastAddress, InetSocketAddress requestAddress) {

        this.multicastInterface = multicastInterface;
        this.multicastAddress = multicastAddress;
        this.requestAddress = requestAddress;
    }

    public MoldUDP64Client transport(EventListener<MarketDataEvent> listener) throws IOException {

        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        channel.bind(new InetSocketAddress(multicastAddress.getPort()));
        channel.join(multicastAddress.getAddress(), multicastInterface);
        channel.configureBlocking(false);

        return new MoldUDP64Client(
                channel,
                requestAddress,
                new PMDParser(new MarketDataListener(listener)),
                new MarketDataStatusListener());
    }

}
