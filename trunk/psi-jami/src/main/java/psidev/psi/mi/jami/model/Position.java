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
     * It gives more information about the position.
     * Ex: 'n-terminal', 'certain', 'range', 'greater than', 'less than', 'c-terminal', 'undetermined', ...
     * @return the status
     */
    public CvTerm getStatus();

    /**
     * The start position in the molecule sequence.
     * If the position is an exact sequence position, then start == end. It is possible that a Position represents
     * an interval and in this case, start <= end.
     * @return start position. 0 if the position is undetermined, n-terminal range or c-terminal range
     */
    public int getStart();

    /**
     * The end position in the molecule sequence.
     * If the position is an exact sequence position, then start == end. It is possible that a Position represents
     * an interval and in this case, start <= end.
     * @return end position. 0 if the position is undetermined, n-terminal range or c-terminal range
     */
    public int getEnd();

    /**
     *
     * @return true if the numerical positions exist, false if the range is undetermined, c-terminal range or n-terminal range because
     * we cannot provide numerical positions. In the last case, the methods getStart and getEnd should return 0.
     */
    public boolean isPositionUndetermined();
}
