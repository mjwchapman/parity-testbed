package uk.co.agileworks.parity.demo.core;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.paritytrading.nassau.MessageListener;

import uk.co.agileworks.parity.demo.domain.*;

public class AgentListener implements MessageListener {

    private EventListener<AgentEvent> listener;

    public AgentListener(EventListener<AgentEvent> listener) {
        this.listener = listener;
    }

    @Override
    public void message(ByteBuffer buffer) throws IOException {
        byte event = buffer.get();
        if      (event == 'A') listener.put(AgentEvent.ORDER_ACCEPTED);
        else if (event == 'C') listener.put(AgentEvent.ORDER_CANCELLED);
        else if (event == 'E') listener.put(AgentEvent.ORDER_EXECUTED);
    }

}
