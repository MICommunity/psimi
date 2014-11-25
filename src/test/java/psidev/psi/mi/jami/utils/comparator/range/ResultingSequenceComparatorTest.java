package psidev.psi.mi.jami.utils.comparator.range;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.model.impl.DefaultResultingSequence;

/**
 * Unit tester for ResultingSequenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class ResultingSequenceComparatorTest {

    private ResultingSequenceComparator comparator = new ResultingSequenceComparator();

    @Test
    public void test_resulting_sequence_null_after(){
        ResultingSequence seq1 = new DefaultResultingSequence("AAGL", "AAAL");
        ResultingSequence seq2 = null;

        Assert.assertTrue(comparator.compare(seq1, seq2) < 0);
        Assert.assertTrue(comparator.compare(seq2, seq1) > 0);

        Assert.assertFalse(ResultingSequenceComparator.areEquals(seq1, seq2));
        Assert.assertTrue(ResultingSequenceComparator.hashCode(seq1) != ResultingSequenceComparator.hashCode(seq2));
    }

    @Test
    public void test_original_sequence_null_after(){
        ResultingSequence seq1 = new DefaultResultingSequence("AAGL", "AAAL");
        ResultingSequence seq2 = new DefaultResultingSequence(null, "AAAL");

        Assert.assertTrue(comparator.compare(seq1, seq2) < 0);
        Assert.assertTrue(comparator.compare(seq2, seq1) > 0);

        Assert.assertFalse(ResultingSequenceComparator.areEquals(seq1, seq2));
        Assert.assertTrue(ResultingSequenceComparator.hashCode(seq1) != ResultingSequenceComparator.hashCode(seq2));
    }

    @Test
    public void test_original_sequence_different(){
        ResultingSequence seq1 = new DefaultResultingSequence("AAGL", "AAAL");
        ResultingSequence seq2 = new DefaultResultingSequence("AGGL", "AAAL");

        Assert.assertTrue(comparator.compare(seq1, seq2) < 0);
        Assert.assertTrue(comparator.compare(seq2, seq1) > 0);

        Assert.assertFalse(ResultingSequenceComparator.areEquals(seq1, seq2));
        Assert.assertTrue(ResultingSequenceComparator.hashCode(seq1) != ResultingSequenceComparator.hashCode(seq2));
    }

    @Test
    public void test_new_sequence_null_after(){
        ResultingSequence seq1 = new DefaultResultingSequence("AAGL", "AAAL");
        ResultingSequence seq2 = new DefaultResultingSequence("AAGL", null);

        Assert.assertTrue(comparator.compare(seq1, seq2) < 0);
        Assert.assertTrue(comparator.compare(seq2, seq1) > 0);

        Assert.assertFalse(ResultingSequenceComparator.areEquals(seq1, seq2));
        Assert.assertTrue(ResultingSequenceComparator.hashCode(seq1) != ResultingSequenceComparator.hashCode(seq2));
    }

    @Test
    public void test_new_sequence_different(){
        ResultingSequence seq1 = new DefaultResultingSequence("AAGL", "AAAL");
        ResultingSequence seq2 = new DefaultResultingSequence("AAGL", "AGAL");

        Assert.assertTrue(comparator.compare(seq1, seq2) < 0);
        Assert.assertTrue(comparator.compare(seq2, seq1) > 0);

        Assert.assertFalse(ResultingSequenceComparator.areEquals(seq1, seq2));
        Assert.assertTrue(ResultingSequenceComparator.hashCode(seq1) != ResultingSequenceComparator.hashCode(seq2));
    }

    @Test
    public void test_same_resulting_sequence(){
        ResultingSequence seq1 = new DefaultResultingSequence("AAGL", "AAAL");
        ResultingSequence seq2 = new DefaultResultingSequence("AAGL", "AAAL");

        Assert.assertTrue(comparator.compare(seq1, seq2) == 0);
        Assert.assertTrue(comparator.compare(seq2, seq1) == 0);

        Assert.assertTrue(ResultingSequenceComparator.areEquals(seq1, seq2));
        Assert.assertTrue(ResultingSequenceComparator.hashCode(seq1) == ResultingSequenceComparator.hashCode(seq2));
    }

    @Test
    public void test_same_original_sequence_case_insensitive(){
        ResultingSequence seq1 = new DefaultResultingSequence("AAGL", "AAAL");
        ResultingSequence seq2 = new DefaultResultingSequence("aagl ", "AAAL");

        Assert.assertTrue(comparator.compare(seq1, seq2) == 0);
        Assert.assertTrue(comparator.compare(seq2, seq1) == 0);

        Assert.assertTrue(ResultingSequenceComparator.areEquals(seq1, seq2));
        Assert.assertTrue(ResultingSequenceComparator.hashCode(seq1) == ResultingSequenceComparator.hashCode(seq2));
    }

    @Test
    public void test_same_new_sequence_case_insensitive(){
        ResultingSequence seq1 = new DefaultResultingSequence("AAGL", "AAAL");
        ResultingSequence seq2 = new DefaultResultingSequence("AAGL", " aAal ");

        Assert.assertTrue(comparator.compare(seq1, seq2) == 0);
        Assert.assertTrue(comparator.compare(seq2, seq1) == 0);

        Assert.assertTrue(ResultingSequenceComparator.areEquals(seq1, seq2));
        Assert.assertTrue(ResultingSequenceComparator.hashCode(seq1) == ResultingSequenceComparator.hashCode(seq2));
    }
}
