package psidev.psi.mi.jami.datasource;

/**
 * A file source error is a DataSourceError with a fileSourceContext.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/03/13</pre>
 */

public class FileSourceError extends DataSourceError {

    private FileSourceContext sourceContext;

    public FileSourceError(String label, String message, FileSourceContext context) {
        super(label, message);
        this.sourceContext = context;
    }

    public FileSourceContext getSourceContext(){
        return sourceContext;
    }
}
