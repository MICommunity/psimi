package psidev.psi.mi.jami.enricher.util;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Tests to confirm that the AliasMergeUtils behaves as expected.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 02/08/13
 */
public class AliasMergerTest {


    private AliasMergeUtils merger;


    @Before
    public void setup(){
        merger = new AliasMergeUtils();
    }

    /**
     * Assert that if the current list is empty, all fetched entries are marked to add
     */
    @Test
    public void test_complete_set_of_new_entries_is_added_to_emptyList(){
        Collection<Alias> currentAliases = new ArrayList<Alias>();

        Collection<Alias> newAliases = new ArrayList<Alias>();
        CvTerm cvTermA = new DefaultCvTerm("CvTerm A");
        Alias alias1A = new DefaultAlias(cvTermA , "Alias 1,A");
        newAliases.add(alias1A);
        Alias alias2A = new DefaultAlias(cvTermA , "Alias 2,A");
        newAliases.add(alias2A);

        assertEquals( 2 , newAliases.size());
        assertEquals( 0 , currentAliases.size());

        merger.merge(newAliases, currentAliases);

        assertEquals(newAliases.size(), merger.getToAdd().size());
        for(Alias alias : newAliases){
            assertTrue(merger.getToAdd().contains(alias));
        }

        assertEquals( 0 , merger.getToRemove().size());
    }


    /**
     * Assert that if the fetched list is empty, no changes are to be made
     */
    @Test
    public void test_complete_set_of_current_entries_kept_if_emptyList_added(){
        Collection<Alias> currentAliases = new ArrayList<Alias>();
        CvTerm cvTermA = new DefaultCvTerm("CvTerm A");
        Alias alias1A = new DefaultAlias(cvTermA , "Alias 1,A");
        currentAliases.add(alias1A);
        Alias alias2A = new DefaultAlias(cvTermA , "Alias 2,A");
        currentAliases.add(alias2A);

        Collection<Alias> newAliases = new ArrayList<Alias>();


        assertEquals( 0 , newAliases.size());
        assertEquals( 2 , currentAliases.size());


        merger.merge(newAliases, currentAliases);

        assertEquals(0, merger.getToAdd().size());
        assertEquals(0 , merger.getToRemove().size());
    }


    /**
     * Assert that if two lists are effectively identical, no change is made.
     */
    @Test
    public void test_identical_entries_cause_no_change(){
        Collection<Alias> currentAliases = new ArrayList<Alias>();
        CvTerm cvTermA_c = new DefaultCvTerm("CvTerm A");
        Alias alias1A_c = new DefaultAlias(cvTermA_c , "Alias 1,A");
        currentAliases.add(alias1A_c);

        Collection<Alias> newAliases = new ArrayList<Alias>();
        CvTerm cvTermA_n = new DefaultCvTerm("CvTerm A");
        Alias alias1A_n = new DefaultAlias(cvTermA_n , "Alias 1,A");
        newAliases.add(alias1A_n);

        assertEquals( 1 , newAliases.size());
        assertEquals( 1 , currentAliases.size());

        merger.merge(newAliases, currentAliases);

        assertEquals( 0 , merger.getToAdd().size());
        assertEquals( 0 , merger.getToRemove().size());
    }

    /**
     * Assert that if entries without common features are merged, all are kept
     */
    @Test
    public void test_different_entries_are_all_integrated(){

        Collection<Alias> newAliases = new ArrayList<Alias>();
        CvTerm cvTermA = new DefaultCvTerm("CvTerm A");
        Alias alias1A = new DefaultAlias(cvTermA , "Alias 1,A");
        newAliases.add(alias1A);
        Alias alias2A = new DefaultAlias(cvTermA , "Alias 2,A");
        newAliases.add(alias2A);

        Collection<Alias> currentAliases = new ArrayList<Alias>();
        CvTerm cvTermB = new DefaultCvTerm("CvTerm B");
        Alias alias1B = new DefaultAlias(cvTermB , "Alias 1,B");
        currentAliases.add(alias1B);
        Alias alias2B = new DefaultAlias(cvTermB , "Alias 2,B");
        currentAliases.add(alias2B);

        assertEquals( 2 , newAliases.size());
        assertEquals( 2 , currentAliases.size());

        merger.merge(newAliases, currentAliases);


        assertEquals( 0 , merger.getToRemove().size());

        assertEquals(newAliases.size(), merger.getToAdd().size());
        for(Alias alias : newAliases){
            assertTrue(merger.getToAdd().contains(alias));
        }
    }

    /**
     * Assert that if aliases form the toEnrich share an effectively identical cvTerm with the fetched,
     * the matching toEnrich are removed.
     */
    @Test
    public void test_entries_matching_cvTerm_are_removed(){

        Collection<Alias> newAliases = new ArrayList<Alias>();
        CvTerm cvTermA = new DefaultCvTerm("CvTerm A");
        Alias alias1A = new DefaultAlias(cvTermA , "Alias 1,A");
        newAliases.add(alias1A);
        Alias alias2A = new DefaultAlias(cvTermA , "Alias 2,A");
        newAliases.add(alias2A);

        Collection<Alias> currentAliases = new ArrayList<Alias>();
        CvTerm cvTermB = new DefaultCvTerm("CvTerm A");
        Alias alias1B = new DefaultAlias(cvTermB , "Alias 1,B");
        currentAliases.add(alias1B);
        Alias alias2B = new DefaultAlias(cvTermB , "Alias 2,B");
        currentAliases.add(alias2B);

        assertEquals( 2 , newAliases.size());
        assertEquals( 2 , currentAliases.size());

        merger.merge(newAliases, currentAliases);

        assertEquals(newAliases.size(), merger.getToAdd().size());
        for(Alias alias : newAliases){
            assertTrue(merger.getToAdd().contains(alias));
        }

        assertEquals( currentAliases.size() , merger.getToRemove().size());
        for(Alias alias : currentAliases){
            assertTrue(merger.getToRemove().contains(alias));
        }
    }
}
