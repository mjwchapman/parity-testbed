package uk.co.agileworks.parity.demo;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import uk.co.agileworks.parity.demo.core.*;
import uk.co.agileworks.parity.demo.domain.*;

@Slf4j
public class DemoApp {

    static final SimpleOrder BUY_ORDER = new SimpleOrder(Side.BUY, 100, "AAPL", 99.75);
    static final SimpleOrder SELL_ORDER = new SimpleOrder(Side.SELL, 100, "AAPL", 99.75);

    private DemoConfig config;
    private SoupBinTCPClientFactory soupBinTCPClientFactory;
    private MoldUDP64ClientFactory moldUDP64ClientFactory;

    public DemoApp() throws IOException {

        config = new DemoConfig("src/main/resources/demo-app.conf");

        soupBinTCPClientFactory = new SoupBinTCPClientFactory(
                config.getOrderEntryAddress());

        moldUDP64ClientFactory = new MoldUDP64ClientFactory(
                config.getMarketDataMulticastInterface(),
                config.getMarketDataMulticastAddress(),
                config.getMarketDataRequestAddress());
    }

    public void run() throws IOException {

        new ParityServer(config.getParityConfig()).start();

        Agent buyer = new Agent(soupBinTCPClientFactory);
        Agent seller = new Agent(soupBinTCPClientFactory);
        MarketData marketData = new MarketData(moldUDP64ClientFactory);

        EventHandler events = new EventHandler();
        events.register(buyer, seller);
        events.register(marketData);
        events.start();

        buyer.login();
        seller.login();

        buyer.send(BUY_ORDER);
        seller.send(SELL_ORDER);

        for (int i = 0; i++ < 3;) {
            log.info("buyer event={}", buyer.getEvent());
            log.info("seller event={}", seller.getEvent());
        }

        log.info("marketData event={}", marketData.getEvent());
        log.info("marketData event={}", marketData.getEvent());
    }

    public static void main(String[] args) throws IOException {
        new DemoApp().run();
        System.exit(0);
    }

}
