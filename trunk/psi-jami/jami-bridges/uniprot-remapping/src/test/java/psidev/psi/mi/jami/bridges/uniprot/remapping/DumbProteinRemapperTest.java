package psidev.psi.mi.jami.bridges.uniprot.remapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.uniprot.remapping.listener.CountingRemapListener;
import psidev.psi.mi.jami.bridges.uniprot.remapping.listener.LoggingRemapListener;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 06/06/13
 * Time: 13:56
 */
public class DumbProteinRemapperTest {

    public static final Log log = LogFactory.getLog(DumbProteinRemapperTest.class);

    public DumbProteinRemapper remap;
    
    LoggingRemapListener logger;
    CountingRemapListener counter;

    public Protein protein;

    public final String test_sequence = "FOOBARBARFOO";
    public final String TESTID = "P42694";

    //public RemapReport remapReport;

    public  Xref MAPPABLE_A;
    public  Xref MAPPABLE_B;
    public  Xref UNMAPPABLE;
    public  Xref CONFLICT;

    // Checking
    // // NO SEQUENCE tests
    // // // ID 1 , SEQ 1   - Always fails when no seq
    // // // ID 1 , SEQ 0
    // // // ID 0 , SEQ 1
    // // // ID 0 , SEQ 0
    // // SEQUENCE tests
    // // // ID 1 , SEQ 1
    // // // ID 1 , SEQ 0
    // // // ID 0 , SEQ 1
    // // // ID 0 , SEQ 0

    // NO checking
    // // NO SEQUENCE tests
    // // // ID 1 , SEQ 1   - Always fails when no seq
    // // // ID 1 , SEQ 0
    // // // ID 0 , SEQ 1
    // // // ID 0 , SEQ 0
    // // SEQUENCE tests
    // // // ID 1 , SEQ 1
    // // // ID 1 , SEQ 0
    // // // ID 0 , SEQ 1
    // // // ID 0 , SEQ 0

    @Before
    public void build_bridge(){
        remap = new DumbProteinRemapper();

        //logger = new LoggingRemapListener();
        counter = new CountingRemapListener();
        remap.setRemapListener(counter);

        MAPPABLE_A = new DefaultXref(new DefaultCvTerm("ensembl"), "ENSP00000351524"); //P42694
        MAPPABLE_B = new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000198265"); //P42694
        UNMAPPABLE = new DefaultXref(new DefaultCvTerm("pfam"), "PF00642"); //P42694
        CONFLICT = new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000197561"); //P08246

        protein = new DefaultProtein("test");
        protein.setOrganism(new DefaultOrganism(Integer.parseInt("9606")));
    }

    @Test
    public void test_conflict_xref_returns_conflicting_identifier(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(CONFLICT);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertNotNull(protein.getUniprotkb());
        assertFalse(protein.getUniprotkb().equalsIgnoreCase(TESTID));
        assertEquals("P08246",protein.getUniprotkb());
    }



    // // Checking

    // // NO SEQUENCE tests

    // ID 1 , SEQ 1

    /**
     * Checking // NO SEQUENCE tests //ID 1 , SEQ 1
     *
     * Attempting to remap with a remappable id but no sequence
     * This should fail as identifiers and sequence are both required.
     * No conflict will be thrown as there are no mappings to compare and cause a conflict.
     */
    @Test
    public void test_mappable_xref_with_no_sequence_UseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Failed"));
        assertNull(protein.getUniprotkb());
        //assertTrue(counter.getConflictCount() > 0);
    }

    // ID 1 , SEQ 0

    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_no_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_no_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_no_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());

    }

    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_no_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Failed"));
        assertNull(protein.getUniprotkb());
        assertTrue(counter.getConflictCount() > 0);
    }

    // ID 0 , SEQ 1

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_no_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_no_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());

    }

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Failed"));
        assertNull(protein.getUniprotkb());
        assertTrue(counter.getConflictCount() > 0);
    }

    // ID 0 , SEQ 0

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_no_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_no_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());

    }

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Failed"));
        assertNull(protein.getUniprotkb());
        assertTrue(counter.getConflictCount() > 0);
    }





    // // Checking

    // // SEQUENCE tests

    // ID 1 , SEQ 1

    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 1
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_sequence_UseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 1
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_sequence_UseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 1
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_sequence_UseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());

    }

    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 1
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_UseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Failed"));
        assertNull(protein.getUniprotkb());
        assertTrue(counter.getConflictCount() > 0);
    }





    // ID 1 , SEQ 0

    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());

    }

    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Failed"));
        assertNull(protein.getUniprotkb());
        assertTrue(counter.getConflictCount() > 0);
    }

    // ID 0 , SEQ 1

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertFalse(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertFalse(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertFalse(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs, (with conflict)
     * Sequence should dbe used over the identifiers.
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertFalse(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());
        
        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }

    // ID 0 , SEQ 0

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());
        
        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());
    }


    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Success"));
        assertTrue(counter.isFromIdentifiers());
        assertTrue(counter.isFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertEquals(0, counter.getConflictCount());

    }

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref - conflicts with sequence
     */
    @Test
    public void test_mappable_xref_which_conflicts_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Failed"));
        assertFalse(counter.isFromIdentifiers());
        assertFalse(counter.isFromSequence());

        assertNull(protein.getUniprotkb());
        assertTrue(counter.getConflictCount() > 0);
    }

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(counter.getStatus().contains("Failed"));
        assertNull(protein.getUniprotkb());
        assertTrue(counter.getConflictCount() > 0);
    }
}
