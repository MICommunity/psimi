package psidev.psi.mi.jami.model;

/**
 * The position of a feature in the interactor sequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Position {

    /**
     * The range status is a controlled vocabulary term which cannot be null.
     * It gives more information about the position e.g. 'n-terminal' or 'certain'
     * @return the status
     */
    public CvTerm getStatus();

    /**
     * Set the range status.
     * @param status
     * @throws IllegalArgumentException when status is null
     */
    public void setStatus(CvTerm status);

    /**
     * The start position in the interactor sequence.
     * If the position is an exact sequence position, then start == end. It is possible that a Position represents
     * an interval and in this case, start <= end.
     * @return start position.
     */
    public int getStart();

    /**
     * set start position
     * @param start
     */
    public void setStart(int start);

    /**
     * The end position in the interactor sequence.
     * If the position is an exact sequence position, then start == end. It is possible that a Position represents
     * an interval and in this case, start <= end.
     * @return end position.
     */
    public int getEnd();

    /**
     * Set end position
     * @param end
     */
    public void setEnd(int end);

}
