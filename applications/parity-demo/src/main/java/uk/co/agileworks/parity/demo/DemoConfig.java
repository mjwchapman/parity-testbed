package uk.co.agileworks.parity.demo;

import java.io.FileNotFoundException;
import java.net.*;

import org.jvirtanen.config.Configs;
import org.jvirtanen.util.Applications;

import com.typesafe.config.Config;

public class DemoConfig {

    private final Config config;

    public DemoConfig(String filepath) throws FileNotFoundException {
        config = Applications.config(filepath);
    }

    public InetSocketAddress getMarketDataMulticastAddress() {
        return new InetSocketAddress(
                Configs.getInetAddress(config, "market-data.multicast-group"),
                Configs.getPort(config, "market-data.multicast-port"));
    }

    public NetworkInterface getMarketDataMulticastInterface() {
        return Configs.getNetworkInterface(config, "market-data.multicast-interface");
    }

    public InetSocketAddress getMarketDataRequestAddress() {
        return new InetSocketAddress(
                Configs.getInetAddress(config, "market-data.request-address"),
                Configs.getPort(config, "market-data.request-port"));
    }

    public InetSocketAddress getOrderEntryAddress() {
        return DemoConfig.getOrderEntryAddress(config);
    }

    public Config getParityConfig() {
        return config;
    }

    public static InetSocketAddress getOrderEntryAddress(Config config) {
        return new InetSocketAddress(
                Configs.getInetAddress(config, "order-entry.address"),
                Configs.getPort(config, "order-entry.port"));
    }

}
