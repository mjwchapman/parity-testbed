package uk.co.agileworks.parity.demo.core;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Set;
import java.util.concurrent.*;

public class EventHandler {
    
    private Selector selector;
    private SelectionKey marketDataKey;

    public EventHandler() throws IOException {
        selector = Selector.open();
    }

    public void register(Agent... agents) throws ClosedChannelException {
        for (Agent agent : agents)
            agent.getTransport().getChannel().register(selector, SelectionKey.OP_READ, agent);
    }

    public void register(MarketData marketData) throws ClosedChannelException {
        marketDataKey = marketData.getTransport().getChannel().register(selector, SelectionKey.OP_READ, marketData);
    }

    public void start() {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                selector.select(100);
                Set<SelectionKey> keys = selector.selectedKeys();
                if (keys.contains(marketDataKey)) {
                    ((MarketData) marketDataKey.attachment()).getTransport().receive();
                    keys.remove(marketDataKey);
                }
                for (SelectionKey agentKey : keys) {
                    ((Agent) agentKey.attachment()).getTransport().receive();
                }
                keys.clear();
            }
        });
    }

}
