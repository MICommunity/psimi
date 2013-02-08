package psidev.psi.mi.jami.model;

/**
 * The position of a feature in the interactor sequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Position {
    public static final String UNDETERMINED = "undetermined";
    public static final String UNDETERMINED_FULL = "undetermined sequence position";
    public static final String UNDETERMINED_MI = "MI:0339";

    public static final String N_TERMINAL_RANGE = "n-term range";
    public static final String N_TERMINAL_RANGE_FULL = "n-terminal range";
    public static final String N_TERMINAL_RANGE_MI = "MI:1040";

    public static final String C_TERMINAL_RANGE = "c-term range";
    public static final String C_TERMINAL_RANGE_FULL = "c-terminal range";
    public static final String C_TERMINAL_RANGE_MI = "MI:1039";

    public static final String RAGGED_N_TERMINAL = "ragged n-terminus";
    public static final String RAGGED_N_TERMINAL_MI = "MI:0341";

    public static final String N_TERMINAL = "n-terminal";
    public static final String N_TERMINAL_FULL = "n-terminal position";
    public static final String N_TERMINAL_MI = "MI:0340";

    public static final String C_TERMINAL = "c-terminal";
    public static final String C_TERMINAL_FULL = "c-terminal position";
    public static final String C_TERMINAL_MI = "MI:0334";

    public static final String CERTAIN = "certain";
    public static final String CERTAIN_FULL = "certain sequence position";
    public static final String CERTAIN_MI = "MI:0335";

    public static final String RANGE = "range";
    public static final String RANGE_FULL = "range";
    public static final String RANGE_MI = "MI:0338";

    public static final String GREATER_THAN = "greater-than";
    public static final String GREATER_THAN_FULL = "greater-than";
    public static final String GREATER_THAN_MI = "MI:0336";

    public static final String LESS_THAN = "less-than";
    public static final String LESS_THAN_FULL = "less-than";
    public static final String LESS_THAN_MI = "MI:0337";


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
