package uk.co.agileworks.parity.demo;

import static org.awaitility.Awaitility.await;
import static uk.co.agileworks.parity.demo.DemoConfig.getOrderEntryAddress;

import java.lang.reflect.Method;
import java.nio.channels.SocketChannel;

import com.typesafe.config.Config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParityServer {

    private Config config;

    public ParityServer(Config config) {
        this.config = config;
    }

    public void start() {

        new Thread(() -> {
            try {
                log.info("Starting server with config={}", config);
                Method main = Class.forName("com.paritytrading.parity.system.TradingSystem")
                        .getDeclaredMethod("main", Config.class);
                main.setAccessible(true);
                main.invoke(null, config);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }).start();

        await().until(this::serverReady);
    }

    private boolean serverReady() {
        try {
            SocketChannel.open(getOrderEntryAddress(config));
            log.info("Server ready.");
            return true;
        } catch (Exception e) {
            log.debug(e.getMessage() + " - trying again...");
            return false;
        }
    }

}
