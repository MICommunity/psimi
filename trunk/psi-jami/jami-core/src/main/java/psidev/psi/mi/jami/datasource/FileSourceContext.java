package psidev.psi.mi.jami.datasource;

/**
 * Some context where an object is located in a file.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public interface FileSourceContext {

    /**
     * The locator of a position in a file.
     * It can be null
     * @return
     */
    public FileSourceLocator getSourceLocator();
}
