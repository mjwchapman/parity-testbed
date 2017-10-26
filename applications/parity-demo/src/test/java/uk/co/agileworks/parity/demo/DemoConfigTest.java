package uk.co.agileworks.parity.demo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.net.*;

import org.junit.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoConfigTest {

    DemoConfig config;

    @Test
    public void getMarketDataMulticastAddress() {
        InetSocketAddress addr = config.getMarketDataMulticastAddress();
        log.trace("addr={}", addr);
        assertThat(addr.getHostString(), equalTo("224.0.0.1"));
        assertThat(addr.getPort(), equalTo(5000));
    }

    @Test
    public void getMarketDataMulticastInterface() {
        NetworkInterface intf = config.getMarketDataMulticastInterface();
        log.trace("intf={}", intf);
        assertThat(intf.getName(), equalTo("lo"));
    }

    @Test
    public void getMarketDataRequestAddress() {
        InetSocketAddress addr = config.getMarketDataRequestAddress();
        log.trace("addr={}", addr);
        assertThat(addr.getHostString(), equalTo("127.0.0.1"));
        assertThat(addr.getPort(), equalTo(5001));
    }

    @Test
    public void getOrderEntryAddress() {
        InetSocketAddress addr = config.getOrderEntryAddress();
        log.trace("addr={}", addr);
        assertThat(addr.getHostString(), equalTo("127.0.0.1"));
        assertThat(addr.getPort(), equalTo(4000));
    }

    @Before
    public void setup() throws FileNotFoundException {
        config = new DemoConfig("src/test/resources/demo-test.conf");
    }

}
