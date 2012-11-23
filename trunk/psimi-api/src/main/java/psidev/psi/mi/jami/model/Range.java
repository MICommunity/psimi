package psidev.psi.mi.jami.model;

/**
 * A feature range has a start position and end position, a feature sequence an isLink boolean
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Range {

    public Position getStart();
    public void setStart(Position start);

    public Position getEnd();
    public void setEnd(Position end);

    public boolean isLink();
    public void setLink(boolean link);
}
