package psidev.psi.mi.jami.bridges.ols;

/**
 * Exception thrown when a Lazy cv term cannot load the lazy data from OLS
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/09/13</pre>
 */

public class LazyTermLoadingException extends RuntimeException {

    public LazyTermLoadingException() {
        super();
    }

    public LazyTermLoadingException(String s) {
        super(s);
    }

    public LazyTermLoadingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public LazyTermLoadingException(Throwable throwable) {
        super(throwable);
    }
}
