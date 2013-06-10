package psidev.psi.mi.jami.exception;

/**
 * Exception thrown by a DataSourceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class DataSourceWriterException extends Exception {

    public DataSourceWriterException() {
        super();
    }

    public DataSourceWriterException(String s) {
        super(s);
    }

    public DataSourceWriterException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataSourceWriterException(Throwable throwable) {
        super(throwable);
    }
}
