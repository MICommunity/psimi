package psidev.psi.mi.jami.datasource;

/**
 * Some context in a file giving information about object location, etc..
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public interface FileSourceContext {

    /**
     * The locator of a position in a file.
     * It can be null
     * @return the file locator
     */
    public FileSourceLocator getSourceLocator();

    /**
     * Sets the source locator
     * @param locator : the file locator
     */
    public void setSourceLocator(FileSourceLocator locator);

    /**
     *
     * @return the file source context as a String. If the source locator is not null, it should give the source locator properties
     */
    public String toString();
}
