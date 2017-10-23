package uk.co.agileworks.parity.demo.util;

@FunctionalInterface
public interface CheckedSupplier<T> {
    
    T get() throws Exception;
    
    static <T> T uncheckException(CheckedSupplier<T> checked) {
        try {
            return checked.get();
        } catch (Exception e) {
            throw new UncheckedException(e);
        }
    }

}
