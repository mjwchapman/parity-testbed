package uk.co.agileworks.parity.demo.domain;

import java.util.concurrent.*;

import uk.co.agileworks.parity.demo.util.*;

public class EventListener<T> {

    private BlockingQueue<T> events = new LinkedBlockingQueue<>();

    public void put(T event) {
        CheckedRunnable.uncheckException(
                () -> events.put(event));
    }

    public T poll() {
        return CheckedSupplier.uncheckException(
                () -> events.poll(1, TimeUnit.SECONDS));
    }

}
