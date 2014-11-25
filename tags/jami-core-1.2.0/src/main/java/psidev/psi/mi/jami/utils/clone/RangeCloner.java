package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.Range;

/**
 * Utility class for cloning ranges
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/02/13</pre>
 */

public class RangeCloner {

    /**
     * All properties are copied from source to target excepted the participant
     * @param source
     * @param target
     */
    public static void copyAndOverrideRangeProperties(Range source, Range target){
        if (source != null && target != null){
            target.setPositions(source.getStart(), source.getEnd());
            target.setLink(source.isLink());
            target.setResultingSequence(source.getResultingSequence());
        }
    }

    /**
     * All properties are copied from source to target
     * @param source
     * @param target
     */
    public static void copyAndOverrideRangePropertiesWithParticipant(Range source, Range target){
        copyAndOverrideRangeProperties(source, target);
        target.setParticipant(source.getParticipant());
    }
}
