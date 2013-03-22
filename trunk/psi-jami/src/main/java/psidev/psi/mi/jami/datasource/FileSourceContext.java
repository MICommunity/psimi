package psidev.psi.mi.jami.datasource;

/**
 * Some context about the datasource object when it is located in a file.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public interface FileSourceContext {

    public FileSourceLocator getSourceLocator();
}
