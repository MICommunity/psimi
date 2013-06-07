package psidev.psi.mi.jami.bridges.uniprotremapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.uniprotremapping.listener.RemapListener;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import java.util.HashMap;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 06/06/13
 * Time: 13:56
 */
public class DefaultProteinRemapperTest {

    public static final Log log = LogFactory.getLog(DefaultProteinRemapperTest.class);

    public DefaultProteinRemapper remap;
    public DumbRemapperBridge bridge;
    public HashMap<String, Xref> xrefMap = new HashMap<String, Xref>();

    public Protein protein;

    public final String test_sequence = "FOOBARBARFOO";
    public final String TESTID = "P42694";

    public RemapReport remapReport;

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
        bridge = new DumbRemapperBridge();
        remap = new DefaultProteinRemapper(bridge);
        remap.addRemapListener(new RemapListener() {
            public void fireRemapReport(RemapReport report) {
                remapReport = report;
            }
        });

        bridge.setTestXrefs();
        bridge.setSequenceIdentifier();


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
        remap.setUseIdentifiers(true);
        remap.setUseSequence(false);

        protein.getXrefs().add(CONFLICT);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
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
     */
    @Test
    public void test_mappable_xref_with_no_sequence_UseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(true);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        assertNotNull(remapReport.getConflictMessage());
    }

    // ID 1 , SEQ 0

    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_no_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(false);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_no_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(false);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_no_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(false);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());

    }

    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_no_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(false);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        assertNotNull(remapReport.getConflictMessage());
    }

    // ID 0 , SEQ 1

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_no_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(true);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_no_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(true);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(true);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());

    }

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(true);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        assertNotNull(remapReport.getConflictMessage());
    }

    // ID 0 , SEQ 0

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_no_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(false);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_no_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(false);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(false);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());

    }

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(false);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        assertNotNull(remapReport.getConflictMessage());
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
        remap.setUseIdentifiers(true);
        remap.setUseSequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertTrue(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 1
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_sequence_UseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertTrue(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 1
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_sequence_UseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertTrue(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());

    }

    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 1
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_UseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        assertNotNull(remapReport.getConflictMessage());
    }





    // ID 1 , SEQ 0

    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());

    }

    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_UseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(true);
        remap.setUseSequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        assertNotNull(remapReport.getConflictMessage());
    }

    // ID 0 , SEQ 1

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertFalse(remapReport.isIdentifierFromIdentifiers());
        assertTrue(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertFalse(remapReport.isIdentifierFromIdentifiers());
        assertTrue(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertFalse(remapReport.isIdentifierFromIdentifiers());
        assertTrue(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs, (with conflict)
     * Sequence should dbe used over the identifiers.
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_NOTUseIds_UseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromSequence());
        assertFalse(remapReport.isIdentifierFromIdentifiers());
        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }

    // ID 0 , SEQ 0

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertTrue(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertTrue(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isIdentifierFromIdentifiers());
        assertTrue(remapReport.isIdentifierFromSequence());

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
        assertNull(remapReport.getConflictMessage());

    }

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 1 mappable xref - conflicts with sequence
     */
    @Test
    public void test_mappable_xref_which_conflicts_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertFalse(remapReport.isRemapped());
        assertFalse(remapReport.isIdentifierFromIdentifiers());
        assertFalse(remapReport.isIdentifierFromSequence());

        assertNull(protein.getUniprotkb());
        assertNotNull(remapReport.getConflictMessage());
    }

    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_NOTUseIds_NOTUseSeq(){
        remap.setCheckingEnabled(true);
        remap.setUseIdentifiers(false);
        remap.setUseSequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        remap.setProtein(protein);

        remap.remapProtein();
        assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        assertNotNull(remapReport.getConflictMessage());
    }

}
