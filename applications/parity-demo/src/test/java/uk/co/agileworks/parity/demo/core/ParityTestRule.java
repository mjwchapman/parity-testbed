package uk.co.agileworks.parity.demo.core;

import static org.awaitility.Awaitility.await;
import static uk.co.agileworks.parity.demo.util.CheckedSupplier.uncheckException;

import java.lang.reflect.Method;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;

import org.junit.rules.ExternalResource;
import org.jvirtanen.config.Configs;
import org.jvirtanen.util.Applications;

import com.typesafe.config.Config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParityTestRule extends ExternalResource {

    private final Config config;

    public ParityTestRule() {
        config = uncheckException(
                () -> Applications.config("src/test/resources/parity-test.conf"));
    }

    public InetSocketAddress getMarketDataRequestAddress() {
        return new InetSocketAddress(
                Configs.getInetAddress(config, "market-data.request-address"),
                Configs.getPort(config, "market-data.request-port"));
    }

    public InetSocketAddress getMarketDataMulticastAddress() {
        return new InetSocketAddress(
                Configs.getInetAddress(config, "market-data.multicast-group"),
                Configs.getPort(config, "market-data.multicast-port"));
    }

    public NetworkInterface getMarketDataMulticastInterface() {
        return Configs.getNetworkInterface(config, "market-data.multicast-interface");
    }

    public InetSocketAddress getOrderEntryAddress() {
        return new InetSocketAddress(
                Configs.getInetAddress(config, "order-entry.address"),
                Configs.getPort(config, "order-entry.port"));
    }

    @Override
    protected void before() {

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Method main = Class.forName("com.paritytrading.parity.system.TradingSystem")
                        .getDeclaredMethod("main", Config.class);
                main.setAccessible(true);
                main.invoke(null, config);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });

        await().until(() -> serverReady());
    }

    private boolean serverReady() {
        try {
            SocketChannel.open(new InetSocketAddress("localhost", 4000));
            log.info("Connection OK.");
            return true;
        } catch (Exception e) {
            log.debug(e.getMessage() + " - trying again...");
            return false;
        }
    }

}
