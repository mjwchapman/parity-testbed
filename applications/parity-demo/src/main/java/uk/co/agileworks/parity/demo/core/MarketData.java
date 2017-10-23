package uk.co.agileworks.parity.demo.core;

import static uk.co.agileworks.parity.demo.util.CheckedSupplier.uncheckException;

import com.paritytrading.nassau.moldudp64.MoldUDP64Client;

import uk.co.agileworks.parity.demo.domain.*;

public class MarketData {

    private EventListener<MarketDataEvent> listener = new EventListener<>();
    private MoldUDP64Client transport;

    public MarketData(MoldUDP64ClientFactory factory) {
        transport = uncheckException(
                () -> factory.transport(listener));
    }

    public MarketDataEvent getEvent() {
        return listener.poll();
    }

    public MoldUDP64Client getTransport() {
        return transport;
    }

}
