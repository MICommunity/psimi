package psidev.psi.mi.validator.extension;

import psidev.psi.tools.validator.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * A context which cluster the different contexts for a same error message
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/04/11</pre>
 */

public class Mi25ClusteredContext extends Context {

    List<Context> contexts = new ArrayList<Context>();

    public Mi25ClusteredContext(String context) {
        super(context);
    }

    public Mi25ClusteredContext() {
        super(null);
    }

    public List<Context> getContexts() {
        return contexts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append( "Clustered contexts(" );

        for (Context context : contexts){

            sb.append( context.toString() );
            sb.append("\n");
        }

        sb.append( ")" );
        return sb.toString();
    }
}
