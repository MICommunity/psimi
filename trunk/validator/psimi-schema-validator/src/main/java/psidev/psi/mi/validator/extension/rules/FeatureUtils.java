package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Names;
import psidev.psi.mi.xml.model.Range;
import psidev.psi.mi.xml.model.RangeStatus;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.Rule;

import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25-Aug-2010</pre>
 */

public class FeatureUtils {

    /**
     * The constant for undetermined.
     */
    public static final String UNDETERMINED = "undetermined";
    public static final String FULL_UNDETERMINED_NAME = "undetermined sequence position";
    public static final String UNDETERMINED_MI_REF = "MI:0339";

    /**
     * The constant for c-terminal.
     */
    public static final String C_TERMINAL = "c-terminal";
    public static final String FULL_C_TERMINAL_NAME = "c-terminal position";
    public static final String C_TERMINAL_MI_REF = "MI:0334";

    /**
     * The constant for n-terminal.
     */
    public static final String N_TERMINAL = "n-terminal";
    public static final String FULL_N_TERMINAL_NAME = "n-terminal position";
    public static final String N_TERMINAL_MI_REF = "MI:0340";

    /**
     *
     * @param fromIntervalStart
     * @param fromIntervalEnd
     * @param toIntervalStart
     * @param toIntervalEnd
     * @param sequence
     * @return
     */
    public static boolean isRangeWithinSequence(long fromIntervalStart, long fromIntervalEnd, long toIntervalStart, long toIntervalEnd, String sequence){

        if (sequence == null){
            return true;
        }

        int sequenceLength = sequence.length();

        if (fromIntervalEnd > sequenceLength || toIntervalEnd > sequenceLength || fromIntervalStart > sequenceLength || toIntervalStart > sequenceLength){
            return false;
        }
        else if (fromIntervalEnd <= 0 || toIntervalEnd <= 0 || fromIntervalStart <= 0 || toIntervalStart <= 0){
            return false;
        }

        return true;
    }

    /**
     *
     * @param fromIntervalStart
     * @param fromIntervalEnd
     * @param toIntervalStart
     * @param toIntervalEnd
     * @return true if the range is overlapping (start > end)
     */
    public static boolean isOverlappingRange(long fromIntervalStart, long fromIntervalEnd, long toIntervalStart, long toIntervalEnd){

        if (fromIntervalStart > fromIntervalEnd || toIntervalStart > toIntervalEnd || fromIntervalStart > toIntervalStart || fromIntervalEnd > toIntervalEnd || fromIntervalStart > toIntervalEnd){
            return true;
        }

        return false;
    }

    public static boolean isUndefinedRangeStatus(RangeStatus status){
        if (status != null){

            if (status.getXref() != null){
                Collection<DbReference> CTerminal = null;
                Collection<DbReference> NTerminal = null;
                Collection<DbReference> undetermined = null;

                CTerminal = RuleUtils.searchReferences(status.getXref().getAllDbReferences(), RuleUtils.IDENTITY_MI_REF, RuleUtils.PSI_MI_REF, C_TERMINAL_MI_REF );
                NTerminal = RuleUtils.searchReferences(status.getXref().getAllDbReferences(), RuleUtils.IDENTITY_MI_REF, RuleUtils.PSI_MI_REF, N_TERMINAL_MI_REF );
                undetermined = RuleUtils.searchReferences(status.getXref().getAllDbReferences(), RuleUtils.IDENTITY_MI_REF, RuleUtils.PSI_MI_REF, UNDETERMINED_MI_REF );

                if (!CTerminal.isEmpty() || !NTerminal.isEmpty() || !undetermined.isEmpty()){
                    return true;
                }
            }
            else if (status.getNames() != null){
                Names names = status.getNames();

                if (names.getShortLabel() != null && (names.getShortLabel().equals(C_TERMINAL) || names.getShortLabel().equals(N_TERMINAL) || names.getShortLabel().equals(UNDETERMINED))){
                    return true;
                }
                else if (names.getFullName() != null && (names.getFullName().equals(FULL_C_TERMINAL_NAME) || names.getFullName().equals(FULL_N_TERMINAL_NAME) || names.getFullName().equals(FULL_UNDETERMINED_NAME))){
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public static boolean hasUndefinedRangeStatus(Range range){

        return (isUndefinedRangeStatus(range.getStartStatus()) || isUndefinedRangeStatus(range.getEndStatus()));
    }

    /**
     *
     * @param range
     * @return true if the Cvfuzzy types are coherent with each other : a N, C terminal and undetermined start fuzzy type should always
     * be associated with the same end fuzzy type.
     */
    public static boolean areFrom_ToCvFuzzyTypeConsistent(Range range){

        if (range.getEndStatus() != null && range.getStartStatus() != null){

            if ((hasUndefinedRangeStatus(range)) && !range.getStartStatus().equals(range.getEndStatus())){
                return false;
            }
        }
        else if (range.getEndStatus() != null && range.getStartStatus() == null){
            if (isUndefinedRangeStatus(range.getEndStatus())){
                return false;
            }
        }
        else if (range.getEndStatus() == null && range.getStartStatus() != null){
            if (isUndefinedRangeStatus(range.getStartStatus())){
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param range : the range to check
     * @param sequence : the sequence of the protein
     * @return true if the range is within the sequence, coherent with its fuzzy type and not overlapping
     */
    public static void checkBadRange(Range range, String sequence, Mi25Context context,
                                     Collection<ValidatorMessage> messages,
                                     Rule rule){

        if (range == null){
            messages.add( new ValidatorMessage( "The range of this feature is null and a range is mandatory for each feature.",
                    MessageLevel.ERROR,
                    context,
                    rule ) );
        }
        else if (sequence != null){
            long fromIntervalStart = 0;
            long fromIntervalEnd = 0;
            long toIntervalStart = 0;
            long toIntervalEnd = 0;

            if (range.getBegin() != null){
                fromIntervalStart = range.getBegin().getPosition();
                fromIntervalEnd = range.getBegin().getPosition();
            }
            else if (range.getBeginInterval() != null){
                fromIntervalStart = range.getBeginInterval().getBegin();
                fromIntervalEnd = range.getBeginInterval().getEnd();
            }

            if (range.getEnd() != null){
                toIntervalStart = range.getEnd().getPosition();
                toIntervalEnd = range.getEnd().getPosition();
            }
            else if (range.getEndInterval() != null){
                toIntervalStart = range.getEndInterval().getBegin();
                toIntervalEnd = range.getEndInterval().getEnd();
            }

            boolean isRangeWithinSequence = isRangeWithinSequence(fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd, sequence);

            if (!isOverlappingRange(fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd)){
                if (areFrom_ToCvFuzzyTypeConsistent(range)){
                    if ((range.getEndStatus() == null || range.getStartStatus() == null) && !isRangeWithinSequence){
                        messages.add( new ValidatorMessage( "The range of this feature is not within the sequence of the interactor.",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                    else if (hasUndefinedRangeStatus(range)) {
                        if (fromIntervalStart != 0 || fromIntervalEnd != 0 || toIntervalStart != 0 || toIntervalEnd != 0){
                            messages.add( new ValidatorMessage( "The range of this feature is n-terminal, c-terminal or undetermined. The range positions should consequently be 0.",
                                    MessageLevel.WARN,
                                    context,
                                    rule ) );
                        }
                    }
                    else {
                        if (!isRangeWithinSequence){
                            messages.add( new ValidatorMessage( "The range of this feature is not within the sequence of the interactor.",
                                    MessageLevel.ERROR,
                                    context,
                                    rule ) );
                        }
                    }
                }
                else {
                    messages.add( new ValidatorMessage( "The feature range start status " + range.getStartStatus().toString() + " cannot be associated with the feature range end status " + range.getEndStatus().toString(),
                            MessageLevel.WARN,
                            context,
                            rule ) );
                }
            }
            else {
                 messages.add( new ValidatorMessage( "The feature range start is superior to the feature range end ",
                            MessageLevel.WARN,
                            context,
                            rule ) );
            }
        }
    }
}
