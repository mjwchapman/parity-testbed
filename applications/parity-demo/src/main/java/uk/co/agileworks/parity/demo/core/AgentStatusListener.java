package uk.co.agileworks.parity.demo.core;

import java.io.IOException;

import com.paritytrading.nassau.soupbintcp.*;
import com.paritytrading.nassau.soupbintcp.SoupBinTCP.*;

import lombok.extern.slf4j.Slf4j;
import uk.co.agileworks.parity.demo.domain.*;

@Slf4j
public class AgentStatusListener implements SoupBinTCPClientStatusListener {

    private EventListener<AgentEvent> listener;

    public AgentStatusListener(EventListener<AgentEvent> listener) {
        this.listener = listener;
    }
    @Override
    public void endOfSession(SoupBinTCPClient session) throws IOException {
        log.debug("endOfSession");
    }

    @Override
    public void heartbeatTimeout(SoupBinTCPClient session) throws IOException {
        log.debug("heartbeatTimedout");
    }

    @Override
    public void loginAccepted(SoupBinTCPClient session, LoginAccepted payload) throws IOException {
        listener.put(AgentEvent.LOGIN_ACCEPTED);
    }

    @Override
    public void loginRejected(SoupBinTCPClient session, LoginRejected payload) throws IOException {
        listener.put(AgentEvent.LOGIN_REJECTED);
    }

}
