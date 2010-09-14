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

    public static final String CERTAIN_MI_REF = "MI:0335";
    public static final String MORE_THAN_MI_REF = "MI:0336";
    public static final String RANGE_MI_REF = "MI:0338";
    public static final String LESS_THAN_MI_REF = "MI:0337";
    public static final String UNDETERMINED_MI_REF = "MI:0339";
    public static final String CTERMINAL_MI_REF = "MI:0334";
    public static final String NTERMINAL_MI_REF = "MI:0340";
    public static final String RAGGED_NTERMINUS_MI_REF = "MI:0341";

    public static final String CERTAIN = "certain";
    public static final String MORE_THAN = "greater-than";
    public static final String RANGE = "range";
    public static final String LESS_THAN = "less-than";
    public static final String UNDETERMINED = "undetermined";
    public static final String CTERMINAL = "c-terminal";
    public static final String NTERMINAL = "n-terminal";
    public static final String RAGGED_NTERMINUS = "ragged n-terminus";

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
        else {
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

            // get the start and end status of the range
            final RangeStatus startStatus = range.getStartStatus();
            final RangeStatus endStatus = range.getEndStatus();


            checkRangePositionsAccordingToRangeTypeOk(startStatus, fromIntervalStart, fromIntervalEnd, sequence, context, messages, rule);

            checkRangePositionsAccordingToRangeTypeOk(endStatus, toIntervalStart, toIntervalEnd, sequence, context, messages, rule);

            if (areRangeStatusInconsistent(startStatus, endStatus)){
                messages.add( new ValidatorMessage( "The start status "+(startStatus != null? (startStatus.getNames() != null ? startStatus.getNames().getShortLabel() : "null") : "null")+" and end status "+(endStatus != null? (endStatus.getNames() != null ? endStatus.getNames().getShortLabel() : "null") : "null")+" are inconsistent",
                        MessageLevel.WARN,
                        context,
                        rule ) );
            }

            // in case of a sequence null, it is not possible to check that the range is overlapping the end of the protein sequence
            if (sequence == null){
                // both start/end status are not null
                if (startStatus != null && endStatus != null){
                    // We can only check if the start interval is not overlapping with the end interval if the status is not C-terminal
                    // because as the sequence is null, the range C-terminal position is 0
                    if (!(isCTerminal(startStatus) || isUndetermined(startStatus)) && !(isCTerminal(endStatus) || isUndetermined(endStatus)) && areRangePositionsOverlapping(range, fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd)){
                        messages.add( new ValidatorMessage( "The range positions overlap : ("+fromIntervalStart+"-"+fromIntervalEnd+") - ("+fromIntervalStart+"-"+fromIntervalEnd+")",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                    }
                }
                // only the start status is null, which means that only the start status can be a C-terminal and the position 0
                else if (startStatus != null){
                    // We can only check if the start interval is not overlapping with the end interval if the status is not C-terminal
                    // because as the sequence is null, the range C-terminal position is 0
                    if (!(isCTerminal(startStatus) || isUndetermined(startStatus)) && areRangePositionsOverlapping(range, fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd)){
                        messages.add( new ValidatorMessage( "The range positions overlap : ("+fromIntervalStart+"-"+fromIntervalEnd+") - ("+fromIntervalStart+"-"+fromIntervalEnd+")",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                    }
                }
                // only the end status is null, which means that only the end status can be a C-terminal
                else if (endStatus != null){
                    // We can only check if the start interval is not overlapping with the end interval if the status is not C-terminal
                    // because as the sequence is null, the range C-terminal position is 0
                    if (!(isCTerminal(endStatus) || isUndetermined(endStatus)) && areRangePositionsOverlapping(range, fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd)){
                        messages.add( new ValidatorMessage( "The range positions overlap : ("+fromIntervalStart+"-"+fromIntervalEnd+") - ("+fromIntervalStart+"-"+fromIntervalEnd+")",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                    }
                }
                else {
                    // we need to check that the start interval and end interval are not overlapping
                    if (areRangePositionsOverlapping(range, fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd)){
                        messages.add( new ValidatorMessage( "The range positions overlap : ("+fromIntervalStart+"-"+fromIntervalEnd+") - ("+fromIntervalStart+"-"+fromIntervalEnd+")",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                    }
                }
            }
            else {
                // we need to check that the start interval and end interval are not overlapping
                // both start/end status are not null
                if (startStatus != null && endStatus != null){
                    // We can only check if the start interval is not overlapping with the end interval if the status is not C-terminal
                    // because as the sequence is null, the range C-terminal position is 0
                    if (!isUndetermined(startStatus) && !isUndetermined(endStatus) && areRangePositionsOverlapping(range, fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd)){
                        messages.add( new ValidatorMessage("The range positions overlap : ("+fromIntervalStart+"-"+fromIntervalEnd+") - ("+fromIntervalStart+"-"+fromIntervalEnd+")",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                    }
                }
                // only the start status is null, which means that only the start status can be a C-terminal and the position 0
                else if (startStatus != null){
                    // We can only check if the start interval is not overlapping with the end interval if the status is not C-terminal
                    // because as the sequence is null, the range C-terminal position is 0
                    if (!isUndetermined(startStatus) && areRangePositionsOverlapping(range, fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd)){
                        messages.add( new ValidatorMessage( "The range positions overlap : ("+fromIntervalStart+"-"+fromIntervalEnd+") - ("+fromIntervalStart+"-"+fromIntervalEnd+")",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                    }
                }
                // only the end status is null, which means that only the end status can be a C-terminal
                else if (endStatus != null){
                    // We can only check if the start interval is not overlapping with the end interval if the status is not C-terminal
                    // because as the sequence is null, the range C-terminal position is 0
                    if (!isUndetermined(endStatus) && areRangePositionsOverlapping(range, fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd)){
                        messages.add( new ValidatorMessage( "The range positions overlap : ("+fromIntervalStart+"-"+fromIntervalEnd+") - ("+fromIntervalStart+"-"+fromIntervalEnd+")",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                    }
                }
                else {
                    // we need to check that the start interval and end interval are not overlapping
                    if (areRangePositionsOverlapping(range, fromIntervalStart, fromIntervalEnd, toIntervalStart, toIntervalEnd)){
                        messages.add( new ValidatorMessage( "The range positions overlap : ("+fromIntervalStart+"-"+fromIntervalEnd+") - ("+fromIntervalStart+"-"+fromIntervalEnd+")",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                    }
                }
            }
        }
    }

    /**
     * A position is out of bound if inferior or equal to 0 or superior to the sequence length.
     * @param start : the start position of the interval
     * @param end  : the end position of the interval
     * @param sequenceLength : the length of the sequence, 0 if the sequence is null
     * @return true if the start or the end is inferior or equal to 0 and if the start or the end is superior to the sequence length
     */
    public static boolean areRangePositionsOutOfBounds(long start, long end, int sequenceLength){
        return start <= 0 || end <= 0 || start > sequenceLength || end > sequenceLength;
    }

    /**
     * A range interval is invalid if the start is after the end
     * @param start : the start position of the interval
     * @param end : the end position of the interval
     * @return true if the start is after the end
     */
    public static boolean areRangePositionsInvalid(long start, long end){

        if (start > end){
            return true;
        }
        return false;
    }

    /**
     *
     * @param rangeType : the status of the position
     * @param start : the start of the position
     * @param end : the end of the position (equal to start if the range position is a single position and not an interval)
     * @param sequence : the sequence of the protein
     * @return true if the range positions and the position status are consistent
     */
    public static void checkRangePositionsAccordingToRangeTypeOk(RangeStatus rangeType, long start, long end, String sequence, Mi25Context context, Collection<ValidatorMessage> messages, Rule rule){
        // the sequence length is 0 if the sequence is null
        int sequenceLength = 0;

        if (sequence != null){
            sequenceLength = sequence.length();
        }

        // the position status is null, we can only check that the numerical values of the positions are valid
        if (rangeType == null){
            // start not superior to end
            if (!areRangePositionsInvalid(start, end)){
                // the sequence is null, we can only check if the range positions are positive
                if (sequenceLength == 0 ){
                    if (!(start > 0 && end > 0)){
                        messages.add( new ValidatorMessage( "The range position doesn't have a range status and should be superior to 0. Actual position : (" + start + "-"+end+")",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
                // the sequence is not null, we can check if the range are out of bound
                else {
                    if (areRangePositionsOutOfBounds(start, end, sequenceLength)){
                        messages.add( new ValidatorMessage( "The range interval is out of bounds. Actual position : (" + start + "-"+end+")",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
            }
        }
        // the position status is defined
        else {
            // undetermined position, we expect to have a position equal to 0 for both the start and the end
            if (isUndetermined(rangeType)){
                if (start != 0 || end != 0){
                    messages.add( new ValidatorMessage( "It is not consistent to have a range position with a " +
                            "range status 'undetermined' and a value different from null or 0. Actual position : (" + start + "-"+end+")",
                            MessageLevel.WARN,
                            context,
                            rule ) );
                }
            }
            // n-terminal position : we expect to have a position equal to 1 for both the start and the end
            else if (isNTerminal(rangeType)){
                if (start != 1 || end != 1){
                    messages.add( new ValidatorMessage( "It is not consistent to have a range position with a " +
                            "range status 'n-terminal' and a value different from 1 because 'n-terminal' means the first amino acid of the participant. Actual position : (" + start + "-"+end+")",
                            MessageLevel.WARN,
                            context,
                            rule ) );
                }
            }
            // c-terminal position : we expect to have a position equal to the sequence length (0 if the sequence is null) for both the start and the end
            else if (isCTerminal(rangeType)){
                if (sequenceLength == 0 && (start < 0 ||end < 0 || start != end)){
                    messages.add( new ValidatorMessage( "The range position cannot be negative. Actual position : "+ start,
                            MessageLevel.ERROR,
                            context,
                            rule ) );
                }
                else if ((start != sequenceLength || end != sequenceLength) && sequenceLength > 0){
                    messages.add( new ValidatorMessage( "It is not consistent to have a range position with a " +
                            "range status 'c-terminal' and a value different from the sequence length because 'c-terminal' means the last amino acid of the participant." +
                            "However, we can accept a position null or 0 if the participant sequence is not known. Actual position : (" + start + "-"+end+")",
                            MessageLevel.WARN,
                            context,
                            rule ) );
                }
            }
            // greater than position : we don't expect an interval for this position so the start should be equal to the end
            else if (isMoreThan(rangeType)){
                if (start != end) {
                    messages.add( new ValidatorMessage( "It is not consistent to give the range status 'greater-than'" +
                            " to a range position which is an interval ("+start+"-"+end+")",
                            MessageLevel.ERROR,
                            context,
                            rule ) );
                }

                // The sequence is null, all we can expect is at least a start superior to 0.
                if (sequenceLength == 0){
                    if (start <= 0 ){
                        messages.add( new ValidatorMessage( "A range position with a status 'greater-than' cannot be negative or equal to 0. Actual position : (" + start + "-"+end+")",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
                // The sequence is not null, we expect to have positions superior to 0 and STRICTLY inferior to the sequence length
                else {
                    if (start >= sequenceLength || start <= 0 ){
                        messages.add( new ValidatorMessage( "A range position 'greater-than' must be strictly " +
                                "superior to 0 and strictly inferior to the interactor sequence length. Actual position : (" + start + "-"+end+")",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
            }
            // less than position : we don't expect an interval for this position so the start should be equal to the end
            else if (isLessThan(rangeType)){
                if (start != end) {
                    messages.add( new ValidatorMessage( "A range position 'greater-than' must be strictly " +
                            "superior to 0 and strictly inferior to the interactor sequence length. Actual position : (" + start + "-"+end+")",
                            MessageLevel.ERROR,
                            context,
                            rule ) );
                }
                // The sequence is null, all we can expect is at least a start STRICTLY superior to 1.
                if (sequenceLength == 0){
                    if (start <= 1){
                        messages.add( new ValidatorMessage( "A range position with a status 'less-than' cannot inferior or equal to 1. Actual position : (" + start + "-"+end+")",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
                // The sequence is not null, we expect to have positions STRICTLY superior to 1 and inferior or equal to the sequence length
                else {
                    if (start <= 1 || start > sequenceLength) {
                        messages.add( new ValidatorMessage( "A range position 'less-than' must be strictly " +
                                "superior to 1 and inferior or equal to the interactor sequence length. Actual position : (" + start + "-"+end+")",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
            }
            // if the range position is certain or ragged-n-terminus, we expect to have the positions superior to 0 and inferior or
            // equal to the sequence length (only possible to check if the sequence is not null)
            // We don't expect any interval for this position so the start should be equal to the end
            else if (isCertain(rangeType) || isRaggedNTerminal(rangeType)){
                if (start != end) {
                    messages.add( new ValidatorMessage( "It is not consistent to give a range status different from 'range'" +
                            " to a range position which is an interval ("+start+"-"+end+")",
                            MessageLevel.ERROR,
                            context,
                            rule ) );
                }

                if (sequenceLength == 0){
                    if (start <= 0){
                        messages.add( new ValidatorMessage( "The range with a status 'certain' or 'ragged-n-terminus' must be strictly superior to 0. Actual position : (" + start + "-"+end+")",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
                else {
                    if (areRangePositionsOutOfBounds(start, end, sequenceLength)){
                        messages.add( new ValidatorMessage( "This range is out of bounds. Actual position : (" + start + "-"+end+")",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
            }
            // the range status is not well known, so we allow the position to be an interval, we just check that the start and end are superior to 0 and inferior to the sequence
            // length (only possible to check if the sequence is not null)
            else {
                if (sequenceLength == 0){
                    if (areRangePositionsInvalid(start, end) || start <= 0 || end <= 0){
                        messages.add( new ValidatorMessage( "The range position is invalid : "+start+"-"+end+". It cannot be inferior or equal to 0 and the start should be inferior or equal to the end.",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
                else {
                    if (areRangePositionsInvalid(start, end)) {
                        messages.add( new ValidatorMessage( "The range position is invalid : " + start + "-" + end + ". The start cannot be superior to the end",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    } else if (areRangePositionsOutOfBounds(start, end, sequenceLength)){
                        messages.add( new ValidatorMessage( "This range position is out of bounds. Actual position : (" + start + "-"+end+")",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
            }
        }
    }

    /**
     * Checks if the interval positions are overlapping
     * @param fromStart
     * @param fromEnd
     * @param toStart
     * @param toEnd
     * @return true if the range intervals are overlapping
     */
    public static boolean arePositionsOverlapping(long fromStart, long fromEnd, long toStart, long toEnd){

        if (fromStart > toStart || fromEnd > toStart || fromStart > toEnd || fromEnd > toEnd){
            return true;
        }
        return false;
    }

    /**
     * Checks if the interval positions of the range are overlapping
     * @param range
     * @return true if the range intervals are overlapping
     */
    public static boolean areRangePositionsOverlapping(Range range, long fromStart, long fromEnd, long toStart, long toEnd){
        // get the range status
        RangeStatus startStatus = range.getStartStatus();
        RangeStatus endStatus = range.getEndStatus();

        // both the end and the start have a specific status
        if (startStatus != null && endStatus != null){
            // in the specific case where the start is superior to a position and the end is inferior to another position, we need to check that the
            // range is not invalid because 'greater than' and 'less than' are both exclusive
            if (isMoreThan(startStatus) && isLessThan(endStatus) && toEnd - fromStart < 2){
                return true;
            }
            // we have a greater than start position and the end position is equal to the start position
            else if (isMoreThan(startStatus) && !isMoreThan(endStatus) && fromStart == toEnd){
                return true;
            }
            // we have a less than end position and the start position is equal to the start position
            else if (!isLessThan(startStatus) && isLessThan(endStatus) && fromStart == toEnd){
                return true;
            }
            // As the range positions are 0 when the status is undetermined, we can only check if the ranges are not overlapping when both start and end are not undetermined
            else if (!isUndetermined(startStatus) && !isUndetermined(endStatus)){
                return arePositionsOverlapping(fromStart, fromEnd, toStart, toEnd);
            }
        }
        // only the end position has a status
        else if (endStatus != null){
            // in the specific case where the end is inferior to another position, we need to check that the
            // start is not equal to the end because 'less than' is exclusive
            if (isLessThan(endStatus) && fromStart == toEnd){
                return true;
            }
            // As the range positions are 0 when the status is undetermined, we can only check if the ranges are not overlapping when the end is not undetermined
            else if (!isUndetermined(endStatus)){
                return arePositionsOverlapping(fromStart, fromEnd, toStart, toEnd);
            }
        }
        // only the start position has a status
        else if (startStatus != null){
            // in the specific case where the start is superior to another position, we need to check that the
            // end is not equal to the start because 'greater than' is exclusive
            if (isMoreThan(startStatus) && fromStart == toEnd){
                return true;
            }
            // As the range positions are 0 when the status is undetermined, we can only check if the ranges are not overlapping when the start is not undetermined
            else if (!isUndetermined(startStatus)){
                return arePositionsOverlapping(fromStart, fromEnd, toStart, toEnd);
            }
        }
        // both start and end positions don't have any status, all we can do is checking that the start interval is not overlapping with the end interval
        else {
            return arePositionsOverlapping(fromStart, fromEnd, toStart, toEnd);
        }

        return false;
    }

    /**
     *
     * @param startStatus : the status of the start position
     * @param endStatus : the status of the end position
     * @return  true if the range status are inconsistent (n-terminal is the end, c-terminal is the beginning)
     */
    public static boolean areRangeStatusInconsistent(RangeStatus startStatus, RangeStatus endStatus){

        // both status are not null
        if (startStatus != null && endStatus != null){
            // the start position is C-terminal but the end position is different from C-terminal
            if (isCTerminal(startStatus) && !isCTerminal(endStatus)){
                return true;
            }
            // the end position is N-terminal but the start position is different from N-terminal
            else if (isNTerminal(endStatus) && !isNTerminal(startStatus)){
                return true;
            }
        }
        // only the start position has a status
        else if (startStatus != null){
            // it cannot be C-terminal
            if (isCTerminal(startStatus)){
                return true;
            }
        }
        // only the end position has a status
        else if (endStatus != null){
            // it cannot be N-terminal
            if (isNTerminal(endStatus)){
                return true;
            }
        }
        return false;
    }

    private static boolean isRange( RangeStatus rangeStatus ) {
        if (rangeStatus != null){
            if ( isStatusOfType( rangeStatus, RANGE, RANGE_MI_REF ) ) {
                return true;
            }
        }

        return false;
    }

    private static boolean isLessThan( RangeStatus rangeStatus ) {

        if ( isStatusOfType( rangeStatus, LESS_THAN, LESS_THAN_MI_REF ) ) {
            return true;
        }
        return false;
    }

    private static boolean isMoreThan( RangeStatus rangeStatus ) {
        if ( isStatusOfType( rangeStatus, MORE_THAN, MORE_THAN_MI_REF ) ) {
            return true;
        }
        return false;
    }

    private static boolean isCertain( RangeStatus rangeStatus ) {
        if ( isStatusOfType( rangeStatus, CERTAIN, CERTAIN_MI_REF ) ) {
            return true;
        }
        return false;
    }

    private static boolean isUndetermined( RangeStatus rangeStatus ) {
        if ( isStatusOfType( rangeStatus, UNDETERMINED, UNDETERMINED_MI_REF ) ) {
            return true;
        }
        return false;
    }

    private static boolean isCTerminal( RangeStatus rangeStatus ) {
        if ( isStatusOfType( rangeStatus, CTERMINAL, CTERMINAL_MI_REF ) ) {
            return true;
        }
        return false;
    }

    private static boolean isNTerminal( RangeStatus rangeStatus ) {
        if ( isStatusOfType( rangeStatus, NTERMINAL, NTERMINAL_MI_REF ) ) {
            return true;
        }
        return false;
    }

    private static boolean isRaggedNTerminal( RangeStatus rangeStatus ) {
        if ( isStatusOfType( rangeStatus, RAGGED_NTERMINUS, RAGGED_NTERMINUS ) ) {
            return true;
        }
        return false;
    }

    private static boolean isStatusOfType( RangeStatus status, String psimiName, String psimiIdentifier ) {
        if ( status.getXref() != null ) {
            final DbReference ref = status.getXref().getPrimaryRef();
            return psimiIdentifier.equalsIgnoreCase( ref.getId() );
        }
        else if (status.getNames() != null){
            final Names names = status.getNames();

            return psimiName.equalsIgnoreCase(names.getShortLabel()) || psimiName.equalsIgnoreCase(names.getFullName());
        }
        return false;
    }
}
