package psidev.psi.mi.jami.utils.clone;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultResultingSequence;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.RangeUtils;

/**
 * Unit tester for RangeCloner
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class RangeClonerTest {

    @Test
    public void test_copy_basic_range_properties(){

        Range sourceRange = RangeUtils.createCertainRange(2, 5);
        sourceRange.setResultingSequence(new DefaultResultingSequence());
        sourceRange.setParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));

        Range targetRange = RangeUtils.createUndeterminedRange();
        targetRange.setLink(true);
        RangeCloner.copyAndOverrideRangeProperties(sourceRange, targetRange);

        Assert.assertTrue(sourceRange.getResultingSequence() == targetRange.getResultingSequence());
        Assert.assertTrue(sourceRange.getStart() == targetRange.getStart());
        Assert.assertTrue(sourceRange.getEnd() == targetRange.getEnd());
        Assert.assertFalse(targetRange.isLink());
        Assert.assertNull(targetRange.getParticipant());
    }
}
