package psidev.psi.mi.jami.enricher.exception;

/**
 * Thrown if the fetcher can not be found
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:05
 */
public class FetchingException extends EnrichmentException{
    public FetchingException() {
        super();
    }

    public FetchingException(String s) {
        super(s);
    }

    public FetchingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FetchingException(Throwable throwable) {
        super(throwable);
    }
}
