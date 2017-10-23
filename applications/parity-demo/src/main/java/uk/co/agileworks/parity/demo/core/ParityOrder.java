package uk.co.agileworks.parity.demo.core;

import java.nio.ByteBuffer;

import com.paritytrading.foundation.ASCII;
import com.paritytrading.parity.net.poe.POE;
import com.paritytrading.parity.util.OrderIDGenerator;

import uk.co.agileworks.parity.demo.domain.*;

public class ParityOrder {

    static final double PRICE_FACTOR = 100;

    static OrderIDGenerator orderId = new OrderIDGenerator();

    private POE.EnterOrder enterOrder = new POE.EnterOrder();

    public ParityOrder(SimpleOrder order) {
        ASCII.putLeft(enterOrder.orderId, orderId.next());
        enterOrder.side = Side.BUY == order.getSide() ? POE.BUY : POE.SELL;
        enterOrder.quantity = order.getQuantity();
        enterOrder.instrument = ASCII.packLong(order.getInstrument());
        enterOrder.price = (long) (PRICE_FACTOR * order.getPrice());
    }

    public void put(ByteBuffer buffer) {
        enterOrder.put(buffer);
    }

}
