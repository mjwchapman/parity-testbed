package uk.co.agileworks.parity.demo.util;

public class UncheckedException extends RuntimeException {
    
    public UncheckedException(Exception cause) {
        super(cause);
    }

}
