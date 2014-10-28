package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ResultingSequence;

/**
 * Unit tester for ResultingSequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class DefaultResultingSequenceTest {

    @Test
    public void test_create_empty_resultingSequence(){
        ResultingSequence resSeq = new DefaultResultingSequence();

        Assert.assertNull(resSeq.getNewSequence());
        Assert.assertNull(resSeq.getOriginalSequence());
        Assert.assertTrue(resSeq.getXrefs().isEmpty());
    }

    @Test
    public void test_create_resultingSequence(){
        ResultingSequence resSeq = new DefaultResultingSequence("AAG", "AAA");

        Assert.assertNotNull(resSeq.getNewSequence());
        Assert.assertEquals("AAA", resSeq.getNewSequence());
        Assert.assertNotNull(resSeq.getOriginalSequence());
        Assert.assertEquals("AAG", resSeq.getOriginalSequence());
        Assert.assertTrue(resSeq.getXrefs().isEmpty());
    }
}
