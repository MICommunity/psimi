package psidev.psi.mi.jami.utils;

import org.junit.Test;
import psidev.psi.mi.jami.model.Organism;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 05/09/13
 */
public class OrganismUtilsTest {

    @Test
    public void test_null_organism_returned_for_unreserved_identifier(){
        Organism organism;


        organism = OrganismUtils.createSpecialistOrganism(1);
        assertNull(organism);

        organism = OrganismUtils.createSpecialistOrganism(0);
        assertNull(organism);

        organism = OrganismUtils.createSpecialistOrganism(-6);
        assertNull(organism);
    }

    @Test
    public void test_reserved_identifiers_give_correct_organisms(){
        Organism organism;

        organism = OrganismUtils.createSpecialistOrganism(-1);
        assertNotNull(organism);

        assertEquals("In vitro" , organism.getScientificName());
        assertEquals("In vitro" , organism.getCommonName());

        organism = OrganismUtils.createSpecialistOrganism(-2);
        assertNotNull(organism);

        assertEquals( "Chemical synthesis"  , organism.getScientificName() );
        assertEquals( "Chemical synthesis"  , organism.getCommonName() );

        organism = OrganismUtils.createSpecialistOrganism(-3);
        assertNotNull(organism);

        assertEquals( "Unknown" , organism.getScientificName() );
        assertEquals( "Unknown" , organism.getCommonName() );

        organism = OrganismUtils.createSpecialistOrganism(-4);
        assertNotNull(organism);

        assertEquals("In vivo" , organism.getScientificName() );
        assertEquals("In vivo" , organism.getCommonName() );

        organism = OrganismUtils.createSpecialistOrganism(-5);
        assertNotNull(organism);

        assertEquals("In Silico" , organism.getScientificName() );
        assertEquals("In Silico" , organism.getCommonName() );
    }

    public void test_identifier_for_unknown_gives_correct_entry(){
        Organism organism;

        organism = OrganismUtils.createUnknownOrganism();
        assertNotNull(organism);

        assertEquals( "Unknown" , organism.getScientificName() );
        assertEquals( "Unknown" , organism.getCommonName() );
    }

}
