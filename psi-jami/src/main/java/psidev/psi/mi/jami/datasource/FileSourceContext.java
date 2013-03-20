package psidev.psi.mi.jami.datasource;

/**
 * Some context about the datasource object when it is located in a file.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public interface FileSourceContext {

    /**
     * The line number in the file where the object is located
     * @return
     */
    public int getLineNumber();

    /**
     * The column number in the file where the object is located.
     * For tab delimited files, the column number would be the actual tab column number. For all other files,
     * The column number should indicate the character in the line.
     * @return
     */
    public int getColumnNumber();

    /**
     * The id of the object if it has one
     * @return
     */
    public String getId();
}
