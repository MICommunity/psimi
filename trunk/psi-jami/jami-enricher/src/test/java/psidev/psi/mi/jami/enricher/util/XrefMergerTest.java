package psidev.psi.mi.jami.enricher.util;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests to assure that the XrefMerger has the correct behaviour.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 02/08/13
 */
public class XrefMergerTest {

    private XrefMerger merger;


    @Before
    public void setup(){
        merger = new XrefMerger();
    }

    /**
     * Assert that if the current list is empty, all fetched entries are marked to add
     */
    @Test
    public void test_complete_set_of_new_entries_is_added_to_emptyList(){

        Collection<Xref> currentXrefs = new ArrayList<Xref>();

        Collection<Xref> newXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A = new DefaultCvTerm("CvTerm DBA");
        Xref xref_1A = new DefaultXref(cvTermDB_A , "Xref 1,A");
        newXrefs.add(xref_1A);
        Xref xref_2A = new DefaultXref(cvTermDB_A , "Xref 2,A");
        newXrefs.add(xref_2A);

        merger.merge(newXrefs, currentXrefs , true);

        assertEquals(newXrefs.size(), merger.getToAdd().size());
        for(Xref xref : newXrefs){
            assertTrue(merger.getToAdd().contains(xref));
        }

        assertEquals( 0 , merger.getToRemove().size());
    }


    /**
     * Assert that if the fetched list is empty, no changes are to be made
     */
    @Test
    public void test_complete_set_of_current_entries_kept_if_emptyList_added(){

        Collection<Xref> currentXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A = new DefaultCvTerm("CvTerm DBA");
        Xref xref_1A = new DefaultXref(cvTermDB_A , "Xref 1,A");
        currentXrefs.add(xref_1A);
        Xref xref_2A = new DefaultXref(cvTermDB_A , "Xref 2,A");
        currentXrefs.add(xref_2A);

        Collection<Xref> newXrefs = new ArrayList<Xref>();


        merger.merge(newXrefs, currentXrefs , true);

        assertEquals(0, merger.getToAdd().size());
        assertEquals(0 , merger.getToRemove().size());
    }


    /**
     * Assert that if two lists are effectively identical, no change is made.
     */
    @Test
    public void test_identical_entries_cause_no_change(){
        Collection<Xref> currentXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A_c = new DefaultCvTerm("CvTerm DBA");
        Xref xref_1A_c = new DefaultXref(cvTermDB_A_c , "Xref 1,A");
        currentXrefs.add(xref_1A_c);
        Xref xref_2A_c = new DefaultXref(cvTermDB_A_c , "Xref 2,A");
        currentXrefs.add(xref_2A_c);

        Collection<Xref> newXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A_n = new DefaultCvTerm("CvTerm DBA");
        Xref xref_1A_n = new DefaultXref(cvTermDB_A_n , "Xref 1,A");
        newXrefs.add(xref_1A_n);
        Xref xref_2A_n = new DefaultXref(cvTermDB_A_n , "Xref 2,A");
        newXrefs.add(xref_2A_n);

        merger.merge(newXrefs, currentXrefs , true);

        assertEquals(0, merger.getToAdd().size());
        assertEquals( 0 , merger.getToRemove().size());
    }

    /**
     * Assert that if entries without common features are merged, all are kept
     */
    @Test
    public void test_different_entries_are_all_integrated(){

        Collection<Xref> newXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_B = new DefaultCvTerm("CvTerm DB B");
        Xref xref_1B = new DefaultXref(cvTermDB_B , "Xref 1,A");
        newXrefs.add(xref_1B);
        Xref xref_2B = new DefaultXref(cvTermDB_B , "Xref 2,A");
        newXrefs.add(xref_2B);

        Collection<Xref> currentXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A = new DefaultCvTerm("CvTerm DB A");
        Xref xref_1A = new DefaultXref(cvTermDB_A , "Xref 1,A");
        currentXrefs.add(xref_1A);
        Xref xref_2A = new DefaultXref(cvTermDB_A , "Xref 2,A");
        currentXrefs.add(xref_2A);

        merger.merge(newXrefs, currentXrefs , true);


        assertEquals( 0 , merger.getToRemove().size());

        assertEquals(newXrefs.size(), merger.getToAdd().size());
        for(Xref xref : newXrefs){
            assertTrue(merger.getToAdd().contains(xref));
        }
    }

    /**
     * Assert that if aliases form the toEnrich share an effectively identical cvTerm with the fetched,
     * the matching toEnrich are removed.
     */
    @Test
    public void test_entries_matching_cvTerm_are_removed(){

        Collection<Xref> newXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_B = new DefaultCvTerm("CvTerm DB");
        Xref xref_1B = new DefaultXref(cvTermDB_B , "Xref 1,B");
        newXrefs.add(xref_1B);
        Xref xref_2B = new DefaultXref(cvTermDB_B , "Xref 2,B");
        newXrefs.add(xref_2B);

        Collection<Xref> currentXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A = new DefaultCvTerm("CvTerm DB");
        Xref xref_1A = new DefaultXref(cvTermDB_A , "Xref 1,A");
        currentXrefs.add(xref_1A);
        Xref xref_2A = new DefaultXref(cvTermDB_A , "Xref 2,A");
        currentXrefs.add(xref_2A);

        merger.merge(newXrefs, currentXrefs , true);

        assertEquals(newXrefs.size(), merger.getToAdd().size());
        for(Xref xref : newXrefs){
            assertTrue(merger.getToAdd().contains(xref));
        }

        assertEquals( currentXrefs.size() , merger.getToRemove().size());
        for(Xref xref : currentXrefs){
            assertTrue(merger.getToRemove().contains(xref));
        }
    }

    /**
     * Assert that if aliases form the toEnrich share an effectively identical cvTerm with the fetched,
     * the matching toEnrich are removed.
     */
    @Test
    public void test_entries_matching_cvTerm_are_not_removed_when_having_qualifiers(){

        Collection<Xref> newXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_B = new DefaultCvTerm("CvTerm DB");
        Xref xref_1B = new DefaultXref(cvTermDB_B , "Xref 1,B");
        newXrefs.add(xref_1B);
        Xref xref_2B = new DefaultXref(cvTermDB_B , "Xref 2,B");
        newXrefs.add(xref_2B);

        Collection<Xref> currentXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A = new DefaultCvTerm("CvTerm DB");
        CvTerm cvTermQU_A = new DefaultCvTerm("Qualifier");
        Xref xref_1A = new DefaultXref(cvTermDB_A , "Xref 1,A" , cvTermQU_A);
        currentXrefs.add(xref_1A);
        Xref xref_2A = new DefaultXref(cvTermDB_A , "Xref 2,A" , cvTermQU_A);
        currentXrefs.add(xref_2A);

        merger.merge(newXrefs, currentXrefs , true);

        assertEquals( 0 , merger.getToRemove().size());

        assertEquals( newXrefs.size() , merger.getToAdd().size());
        for(Xref xref : newXrefs){
            assertTrue(merger.getToAdd().contains(xref));
        }
    }

    /**
     * Assert that if aliases form the toEnrich share an effectively identical cvTerm with the fetched,
     * the matching toEnrich are removed.
     */
    @Test
    public void test_entries_matching_cvTerm_are_removed_despite_having_qualifiers_when_boolean_is_false(){

        Collection<Xref> newXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_B = new DefaultCvTerm("CvTerm DB");
        Xref xref_1B = new DefaultXref(cvTermDB_B , "Xref 1,B");
        newXrefs.add(xref_1B);
        Xref xref_2B = new DefaultXref(cvTermDB_B , "Xref 2,B");
        newXrefs.add(xref_2B);

        Collection<Xref> currentXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A = new DefaultCvTerm("CvTerm DB");
        CvTerm cvTermQU_A = new DefaultCvTerm("Qualifier");
        Xref xref_1A = new DefaultXref(cvTermDB_A , "Xref 1,A" , cvTermQU_A);
        currentXrefs.add(xref_1A);
        Xref xref_2A = new DefaultXref(cvTermDB_A , "Xref 2,A" , cvTermQU_A);
        currentXrefs.add(xref_2A);

        merger.merge(newXrefs, currentXrefs , false);

        assertEquals(newXrefs.size(), merger.getToAdd().size());
        for(Xref xref : newXrefs){
            assertTrue(merger.getToAdd().contains(xref));
        }

        assertEquals( currentXrefs.size() , merger.getToRemove().size());
        for(Xref xref : currentXrefs){
            assertTrue(merger.getToRemove().contains(xref));
        }

    }
}
