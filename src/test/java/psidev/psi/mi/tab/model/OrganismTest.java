package psidev.psi.mi.tab.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;


/**
 * Organism Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>01/12/2007</pre>
 */
public class OrganismTest {

    @Test
    public void setGetDatabase() throws Exception {
        Organism o = OrganismFactory.getInstance().build( 9606 );
        assertEquals( 1, o.getIdentifiers().size() );
        assertEquals( Organism.DEFAULT_DATABASE, o.getIdentifiers().iterator().next().getDatabase() );
    }

    @Test
    public void setGetName() throws Exception {
        Organism o = OrganismFactory.getInstance().build( 9606, "human" );
        assertEquals( 1, o.getIdentifiers().size() );
        assertEquals( "human", o.getIdentifiers().iterator().next().getText() );
    }

    @Test
    public void setGetTaxid() throws Exception {
        Organism o = OrganismFactory.getInstance().build( 9606 );
        assertEquals( 1, o.getIdentifiers().size() );
        assertEquals( "9606", o.getIdentifiers().iterator().next().getIdentifier() );
    }

    @Test
    public void equals() {
        Organism o = OrganismFactory.getInstance().build( 9606 );
        Organism o2 = OrganismFactory.getInstance().build( 9606 );
        Organism o3 = OrganismFactory.getInstance().build( 1234 );

        assertEquals( o, o2 );
        assertEquals( o, o2 );
        assertNotSame( o, o3 );
        assertNotSame( o2, o3 );
    }
}