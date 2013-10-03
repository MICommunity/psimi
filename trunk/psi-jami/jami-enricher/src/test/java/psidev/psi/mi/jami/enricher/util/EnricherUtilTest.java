package psidev.psi.mi.jami.enricher.util;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 26/07/13
 */
public class EnricherUtilTest {

    @Before
    public void setup(){

    }

    /**
     * Assert that if the current list is empty, all fetched entries are marked to add
     */
    @Test
    public void test_complete_set_of_new_Aliases_is_added_to_emptyList(){
        Collection<Alias> currentAliases = new ArrayList<Alias>();

        Collection<Alias> newAliases = new ArrayList<Alias>();
        CvTerm cvTermA = new DefaultCvTerm("CvTerm A");
        Alias alias1A = new DefaultAlias(cvTermA , "Alias 1,A");
        newAliases.add(alias1A);
        Alias alias2A = new DefaultAlias(cvTermA , "Alias 2,A");
        newAliases.add(alias2A);

        assertEquals( 2 , newAliases.size());
        assertEquals( 0 , currentAliases.size());

        EnricherUtils.mergeAliases(new DefaultCvTerm("test object to enrich"), newAliases, currentAliases, false, null);

        assertEquals(newAliases.size(), currentAliases.size());
        for(Alias alias : newAliases){
            assertTrue(currentAliases.contains(alias));
        }
    }


    /**
     * Assert that if the fetched list is empty, no changes are to be made
     */
    @Test
    public void test_complete_set_of_current_aliases_kept_if_emptyList_added(){
        Collection<Alias> currentAliases = new ArrayList<Alias>();
        CvTerm cvTermA = new DefaultCvTerm("CvTerm A");
        Alias alias1A = new DefaultAlias(cvTermA , "Alias 1,A");
        currentAliases.add(alias1A);
        Alias alias2A = new DefaultAlias(cvTermA , "Alias 2,A");
        currentAliases.add(alias2A);

        Collection<Alias> newAliases = new ArrayList<Alias>();


        assertEquals( 0 , newAliases.size());
        assertEquals( 2 , currentAliases.size());

        EnricherUtils.mergeAliases(new DefaultCvTerm("test object to enrich"), newAliases, currentAliases, false, null);

        assertEquals(2, currentAliases.size());
    }


    /**
     * Assert that if two lists are effectively identical, no change is made.
     */
    @Test
    public void test_identical_aliases_cause_no_change(){
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

        EnricherUtils.mergeAliases(new DefaultCvTerm("test object to enrich"), newAliases, currentAliases, true, null);

        assertTrue(CollectionUtils.isEqualCollection(currentAliases, newAliases));
    }

    /**
     * Assert that if entries without common features are merged, all are kept
     */
    @Test
    public void test_different_aliases_are_all_integrated(){

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

        EnricherUtils.mergeAliases(new DefaultCvTerm("test object to enrich"), newAliases, currentAliases, false, null);

        assertEquals(newAliases.size(), 4);
        for(Alias alias : newAliases){
            assertTrue(currentAliases.contains(alias));
        }
    }

    /**
     * Assert that if aliases form the toEnrich share an effectively identical cvTerm with the fetched,
     * the matching toEnrich are removed.
     */
    @Test
    public void test_aliases_matching_cvTerm_are_removed(){

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

        EnricherUtils.mergeAliases(new DefaultCvTerm("test object to enrich"), newAliases, currentAliases, true, null);

        assertEquals(newAliases.size(), 2);
        for(Alias alias : newAliases){
            assertTrue(currentAliases.contains(alias));
        }
    }

    /**
     * Assert that if the current list is empty, all fetched entries are marked to add
     */
    @Test
    public void test_complete_set_of_new_xrefs_is_added_to_emptyList(){

        Collection<Xref> currentXrefs = new ArrayList<Xref>();

        Collection<Xref> newXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A = new DefaultCvTerm("CvTerm DBA");
        Xref xref_1A = new DefaultXref(cvTermDB_A , "Xref 1,A");
        newXrefs.add(xref_1A);
        Xref xref_2A = new DefaultXref(cvTermDB_A , "Xref 2,A");
        newXrefs.add(xref_2A);

        EnricherUtils.mergeXrefs(new DefaultCvTerm("test object to enrich"), newXrefs, currentXrefs, false, false, null, null);

        assertEquals(newXrefs.size(), 2);
        for(Xref xref : newXrefs){
            assertTrue(currentXrefs.contains(xref));
        }
    }


    /**
     * Assert that if the fetched list is empty, no changes are to be made
     */
    @Test
    public void test_complete_set_of_current_xrefs_kept_if_emptyList_added(){

        Collection<Xref> currentXrefs = new ArrayList<Xref>();
        CvTerm cvTermDB_A = new DefaultCvTerm("CvTerm DBA");
        Xref xref_1A = new DefaultXref(cvTermDB_A , "Xref 1,A");
        currentXrefs.add(xref_1A);
        Xref xref_2A = new DefaultXref(cvTermDB_A , "Xref 2,A");
        currentXrefs.add(xref_2A);

        Collection<Xref> newXrefs = new ArrayList<Xref>();

        EnricherUtils.mergeXrefs(new DefaultCvTerm("test object to enrich"), newXrefs, currentXrefs, false, false, null, null);

        assertEquals(2, currentXrefs.size());
    }


    /**
     * Assert that if two lists are effectively identical, no change is made.
     */
    @Test
    public void test_identical_xrefs_cause_no_change(){
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

        EnricherUtils.mergeXrefs(new DefaultCvTerm("test object to enrich"), newXrefs, currentXrefs, false, false, null, null);

        assertTrue(CollectionUtils.isEqualCollection(newXrefs, currentXrefs));
    }

    /**
     * Assert that if entries without common features are merged, all are kept
     */
    @Test
    public void test_different_xrefs_are_all_integrated(){

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

        EnricherUtils.mergeXrefs(new DefaultCvTerm("test object to enrich"), newXrefs, currentXrefs, false, false, null, null);

        assertEquals(4, currentXrefs.size());
        for(Xref xref : newXrefs){
            assertTrue(currentXrefs.contains(xref));
        }
    }

    /**
     * Assert that if aliases form the toEnrich share an effectively identical cvTerm with the fetched,
     * the matching toEnrich are removed.
     */
    @Test
    public void test_xrefs_matching_cvTerm_are_removed(){

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

        EnricherUtils.mergeXrefs(new DefaultCvTerm("test object to enrich"), newXrefs, currentXrefs, true, false, null, null);

        assertEquals(2, currentXrefs.size());
        for(Xref xref : newXrefs){
            assertTrue(currentXrefs.contains(xref));
        }
    }
}
