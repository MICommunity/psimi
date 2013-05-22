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

    public static void copyAndOverrideRangeProperties(Range source, Range target){
        if (source != null && target != null){
            target.setPositions(source.getStart(), source.getEnd());
            target.setLink(source.isLink());
            target.setResultingSequence(source.getResultingSequence());
        }
    }
}
