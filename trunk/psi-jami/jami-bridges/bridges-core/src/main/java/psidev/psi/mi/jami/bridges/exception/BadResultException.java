package psidev.psi.mi.jami.bridges.exception;

/**
 * Thrown when an entry is returned which has unexpected values.
 * This may include null fields, empty fields, or type mismatches.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 15/04/13
 */
public class BadResultException extends Exception{

    public BadResultException() {
        super();
    }

    public BadResultException(String s) {
        super(s);
    }

    public BadResultException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BadResultException(Throwable throwable) {
        super(throwable);
    }
}
