package psidev.psi.mi.tab.filter;

import static org.junit.Assert.*;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * DisjonctiveFilterSet Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0-beta4
 */
public class DisjonctiveFilterSetTest extends AbstractFilterTest {

    @Test
    public void Evaluate_errors() {

        try {
            new DisjonctiveFilterSet( null );
            fail();
        } catch ( Exception e ) {
            // ok
        }

        Set<BinaryInteractionFilter> filters = new HashSet<BinaryInteractionFilter>();
        try {
            new DisjonctiveFilterSet( filters );
            fail();
        } catch ( Exception e ) {
            // ok
        }
    }

    @Test
    public void Evaluate_singleFilter() {

        Set<BinaryInteractionFilter> filters = new HashSet<BinaryInteractionFilter>();

        filters.add( buildFilterOrganism( 9606 ) );
        DisjonctiveFilterSet set = new DisjonctiveFilterSet( filters );

        // 11 - 4896 & 8 - 9606

        assertNotNull( getInteractions() );
        assertEquals( 19, getInteractions().size() );

        Collection<BinaryInteraction> filtered = BinaryInteractionCollectionFilter.filter( getInteractions(), set );

        assertEquals( 8, filtered.size() );
    }

    @Test
    public void Evaluate_manyFilters() {

        Set<BinaryInteractionFilter> filters = new HashSet<BinaryInteractionFilter>();

        filters.add( buildFilterOrganism( 9606 ) );
        filters.add( buildFilterInteractionDetection( "MI:0030" ) ); // crosslink

        DisjonctiveFilterSet set = new DisjonctiveFilterSet( filters );

        // 11 - 4896 & 8 - 9606

        assertNotNull( getInteractions() );
        assertEquals( 19, getInteractions().size() );

        set = new DisjonctiveFilterSet( filters );

        Collection<BinaryInteraction> filtered = BinaryInteractionCollectionFilter.filter( getInteractions(), set );

        assertEquals( 8, filtered.size() );
    }
}
