package uk.co.agileworks.parity.demo;

import static uk.co.agileworks.parity.demo.util.CheckedSupplier.uncheckException;

import org.junit.rules.ExternalResource;

public class ParityTestRule extends ExternalResource {

    private final DemoConfig config;

    public ParityTestRule() {
        config = uncheckException(
                () -> new DemoConfig("src/test/resources/demo-test.conf"));
    }

    public DemoConfig getConfig() {
        return config;
    }

    @Override
    protected void before() {
        new ParityServer(config.getParityConfig()).start();
    }

}
