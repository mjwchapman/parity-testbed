package uk.co.agileworks.parity.demo.domain;

import lombok.Value;

@Value
public class SimpleOrder {

    private final Side side;
    private final long quantity;
    private final String instrument;
    private final double price;
}
