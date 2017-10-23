package uk.co.agileworks.parity.demo.util;

@FunctionalInterface
public interface CheckedRunnable {
    
    void run() throws Exception;
    
    static void uncheckException(CheckedRunnable checked) {
        try {
            checked.run();
        } catch (Exception e) {
            throw new UncheckedException(e);
        }
    }

}
