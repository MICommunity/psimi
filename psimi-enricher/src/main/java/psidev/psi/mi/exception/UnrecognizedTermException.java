package psidev.psi.mi.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 * Time: 11:20
 */
public class UnrecognizedTermException extends Exception {

    public UnrecognizedTermException() {
        super();
    }

    public UnrecognizedTermException(String s) {
        super(s);
    }

    public UnrecognizedTermException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UnrecognizedTermException(Throwable throwable) {
        super(throwable);
    }
}
