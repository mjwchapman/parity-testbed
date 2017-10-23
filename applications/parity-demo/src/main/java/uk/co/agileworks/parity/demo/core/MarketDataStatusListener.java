package uk.co.agileworks.parity.demo.core;

import com.paritytrading.nassau.moldudp64.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MarketDataStatusListener implements MoldUDP64ClientStatusListener {

    @Override
    public void downstream(MoldUDP64Client session, long sequenceNumber, int messageCount) {
        log.debug("downstream");
    }
    
    @Override
    public void endOfSession(MoldUDP64Client session) {
        log.debug("endOfSession");
    }
    
    @Override
    public void request(MoldUDP64Client session, long sequenceNumber, int requestedMessageCount) {
        log.debug("request");
    }
    
    @Override
    public void state(MoldUDP64Client session, MoldUDP64ClientState next) {
        log.debug("state");
    }

}
