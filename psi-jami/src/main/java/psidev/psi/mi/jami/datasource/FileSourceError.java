package psidev.psi.mi.jami.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * A file source error.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/03/13</pre>
 */

public class FileSourceError extends DataSourceError {

    private List<FileSourceContext> sourceContexts;

    public FileSourceError(String label, String message) {
        super(label, message);
    }

    public List<FileSourceContext> getSourceContexts(){
        if (sourceContexts == null){
            sourceContexts = new ArrayList<FileSourceContext>();
        }
        return sourceContexts;
    }
}
