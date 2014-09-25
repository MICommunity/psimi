package psidev.psi.mi.tab.converter.tab2graphml;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceImpl;

import java.util.Collections;
import java.util.List;

/**
 * IdentifierByDatabaseComparator tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.2
 */
public class IdentifierByDatabaseComparatorTest {

    @Test
    public void sort_matched() throws Exception {

        List<CrossReference> ref = Lists.newArrayList();
        ref.add( new CrossReferenceImpl("chebi", "CHEBI:11111") );
        ref.add( new CrossReferenceImpl("uniprotkb", "Q99999") );
        ref.add( new CrossReferenceImpl("lala", "1") );
        ref.add( new CrossReferenceImpl("uniprotkb", "Q99999") );

        final IdentifierByDatabaseComparator comparator = new IdentifierByDatabaseComparator();
        Collections.sort(ref, comparator);

        Assert.assertTrue(comparator.hasMatchedAny());
        Assert.assertEquals( "uniprotkb", ref.get( 0 ).getDatabase() );
        Assert.assertEquals( "uniprotkb", ref.get( 1 ).getDatabase() );
        Assert.assertEquals( "chebi", ref.get( 2 ).getDatabase() );
        Assert.assertEquals( "lala", ref.get( 3 ).getDatabase() );
    }

    @Test
    public void sort_notMatched() throws Exception {

        List<CrossReference> ref = Lists.newArrayList();
        ref.add( new CrossReferenceImpl("lolo", "Q99999") );
        ref.add( new CrossReferenceImpl("lala", "1") );
        ref.add( new CrossReferenceImpl("lili", "Q99999") );

        final CrossReferenceComparator comparator = new IdentifierByDatabaseComparator();
        Collections.sort(ref, comparator);

        Assert.assertFalse(comparator.hasMatchedAny());
    }
}
