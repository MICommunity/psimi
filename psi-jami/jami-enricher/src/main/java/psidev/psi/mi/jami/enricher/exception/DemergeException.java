package psidev.psi.mi.jami.enricher.exception;

/**
 * Thrown if the fetcher can not be found
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:05
 */
@Deprecated
public class DemergeException extends EnrichmentException{
    public DemergeException() {
        super();
    }

    public DemergeException(String s) {
        super(s);
    }

    public DemergeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DemergeException(Throwable throwable) {
        super(throwable);
    }
}
