package psidev.psi.mi.tab.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import java.util.List;

/**
 * Interactor Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since <pre>01/12/2007</pre>
 * @version 1.0
 */
public class InteractorTest {
    
    @Test public void setGetIdentifiers() throws Exception {
        // TODO: Test goes here...
    }

    @Test public void setGetAlternativeIdentifiers() throws Exception {
        // TODO: Test goes here...
    }

    @Test public void setGetAliases() throws Exception {
        // TODO: Test goes here...
    }

    @Test public void setGetOrganism() throws Exception {
        // TODO: Test goes here...
    }

    private Interactor buildSimpleInteractor( String ac, int taxid ) {
        List<CrossReference> refs = new ArrayList<CrossReference>( );
        refs.add( new CrossReferenceImpl( "uniprotkb", ac ) );
        Interactor i = new Interactor( refs );
        i.setOrganism( new OrganismImpl( taxid ) );
        return i;
    }
    
    @Test
    public void Equals() {
        Interactor i1 = buildSimpleInteractor( "P12345", 9606 );
        Interactor i2 = buildSimpleInteractor( "P12345", 9606 );
        Interactor i3 = buildSimpleInteractor( "Q98765", 9606 );
        Interactor i4 = buildSimpleInteractor( "P12345", 11 );

        assertEquals( i1, i2 );

        assertNotSame( i1, i3 );
        assertNotSame( i1, i4 );
        assertNotSame( i3, i4 );
    }
}