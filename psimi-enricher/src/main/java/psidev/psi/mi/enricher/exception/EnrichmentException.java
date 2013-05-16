package psidev.psi.mi.enricher.exception;

/**
 * A general error during enrichment
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:57
 */
public class EnrichmentException extends Exception{
    public EnrichmentException() {
        super();
    }

    public EnrichmentException(String s) {
        super(s);
    }

    public EnrichmentException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public EnrichmentException(Throwable throwable) {
        super(throwable);
    }
}
