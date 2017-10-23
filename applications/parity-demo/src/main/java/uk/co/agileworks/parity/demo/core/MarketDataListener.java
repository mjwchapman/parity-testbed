package uk.co.agileworks.parity.demo.core;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import java.io.IOException;

import com.paritytrading.parity.net.pmd.PMD.*;
import com.paritytrading.parity.net.pmd.PMDListener;

import lombok.extern.slf4j.Slf4j;
import uk.co.agileworks.parity.demo.domain.*;

@Slf4j
public class MarketDataListener implements PMDListener {
    
    private EventListener<MarketDataEvent> listener;
    
    public MarketDataListener(EventListener<MarketDataEvent> listener) {
        this.listener = listener;
    }

    @Override
    public void orderAdded(OrderAdded message) throws IOException {
        log(message);
        listener.put(MarketDataEvent.ORDER_ADDED);
    }

    @Override
    public void orderCanceled(OrderCanceled message) throws IOException {
        log(message);
        listener.put(MarketDataEvent.ORDER_CANCELLED);
    }

    @Override
    public void orderExecuted(OrderExecuted message) throws IOException {
        log(message);
        listener.put(MarketDataEvent.ORDER_EXECUTED);
    }

    @Override
    public void version(Version message) throws IOException {
        log(message);
    }

    void log(Message message) {
        log.debug("{}={}", message.getClass().getSimpleName(), reflectionToString(message, JSON_STYLE));
    }

}
