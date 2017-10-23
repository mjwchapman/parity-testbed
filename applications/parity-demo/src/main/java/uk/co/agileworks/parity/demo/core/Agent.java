package uk.co.agileworks.parity.demo.core;

import java.nio.ByteBuffer;

import com.paritytrading.nassau.soupbintcp.*;
import com.paritytrading.parity.net.poe.POE;

import uk.co.agileworks.parity.demo.domain.*;
import uk.co.agileworks.parity.demo.util.*;

public class Agent {

    private ByteBuffer buffer = ByteBuffer.allocateDirect(POE.MAX_INBOUND_MESSAGE_LENGTH);
    private EventListener<AgentEvent> listener = new EventListener<>();
    private SoupBinTCPClient transport;

    public Agent(SoupBinTCPClientFactory factory) {
        transport = CheckedSupplier.uncheckException(
                () -> factory.transport(listener));
    }

    public AgentEvent getEvent() {
        return listener.poll();
    }

    public SoupBinTCPClient getTransport() {
        return transport;
    }

    public void login() {
        CheckedRunnable.uncheckException(
                () -> transport.login(new SoupBinTCP.LoginRequest()));
    }

    public void send(SimpleOrder order) {
        buffer.clear();
        new ParityOrder(order).put(buffer);
        buffer.flip();

        CheckedRunnable.uncheckException(
                () -> transport.send(buffer));
    }
}
