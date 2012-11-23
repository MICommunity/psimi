package psidev.psi.mi.jami.model;

/**
 * The position of a feature in the interactor sequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Position {

    public CvTerm getStatus();
    public void setStatus(CvTerm status);

    public int getStart();
    public void setStart(int start);

    public int getEnd();
    public void setEnd(int end);

}
