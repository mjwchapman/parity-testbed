package uk.co.agileworks.parity.demo.core;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.*;

import uk.co.agileworks.parity.demo.domain.*;

public class DemoIT {

    static final SimpleOrder BUY_ORDER = new SimpleOrder(Side.BUY, 100, "AAPL", 99.75);
    static final SimpleOrder SELL_ORDER = new SimpleOrder(Side.SELL, 100, "AAPL", 99.75);

    @ClassRule
    public static ParityTestRule parity = new ParityTestRule();

    SoupBinTCPClientFactory soupBinTCPClientFactory = new SoupBinTCPClientFactory(
            parity.getOrderEntryAddress());

    MoldUDP64ClientFactory moldUDP64ClientFactory = new MoldUDP64ClientFactory(
            parity.getMarketDataMulticastInterface(),
            parity.getMarketDataMulticastAddress(),
            parity.getMarketDataRequestAddress());

    Agent buyer = new Agent(soupBinTCPClientFactory);
    Agent seller = new Agent(soupBinTCPClientFactory);

    MarketData marketData = new MarketData(moldUDP64ClientFactory);

    @Test
    public void executeOrder() {
        login(buyer);
        login(seller);

        send(buyer, BUY_ORDER);
        send(seller, SELL_ORDER);

        assertThat(buyer.getEvent(), equalTo(AgentEvent.ORDER_EXECUTED));
        assertThat(seller.getEvent(), equalTo(AgentEvent.ORDER_EXECUTED));

        assertThat(marketData.getEvent(), equalTo(MarketDataEvent.ORDER_ADDED));
        assertThat(marketData.getEvent(), equalTo(MarketDataEvent.ORDER_EXECUTED));
    }

    @Test
    public void loginAgent() {
        login(buyer);
    }

    @Test
    public void sendOrder() {
        login(buyer);
        send(buyer, BUY_ORDER);
        assertThat(marketData.getEvent(), equalTo(MarketDataEvent.ORDER_ADDED));
    }

    @Before
    public void setUp() throws IOException {
        EventHandler events = new EventHandler();
        events.register(buyer, seller);
        events.register(marketData);
        events.start();
    }

    void login(Agent agent) {
        agent.login();
        assertThat(agent.getEvent(), equalTo(AgentEvent.LOGIN_ACCEPTED));
    }

    void send(Agent agent, SimpleOrder order) {
        agent.send(order);
        assertThat(agent.getEvent(), equalTo(AgentEvent.ORDER_ACCEPTED));
    }

}
