package psidev.psi.mi.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 * Time: 11:20
 */
@Deprecated
public class UnresolvableIDException extends Exception {

    public UnresolvableIDException() {
        super();
    }

    public UnresolvableIDException(String s) {
        super(s);
    }

    public UnresolvableIDException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UnresolvableIDException(Throwable throwable) {
        super(throwable);
    }
}
