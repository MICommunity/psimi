package psidev.psi.mi.jami.bridges.uniprot.remapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.uniprot.remapping.listener.LoggingRemapListener;
import psidev.psi.mi.jami.bridges.uniprot.remapping.listener.RemapListener;
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
public class IntactProteinRemapperTest {

    public static final Log log = LogFactory.getLog(IntactProteinRemapperTest.class);

    public IntactProteinRemapper remap;

    public Protein protein;

    public final String test_sequence = "MEDRRAEKSCEQACESLKRQDYEMALKHCTEALLSLGQYSMADFTGPCPLEIERIKIESL" +
            "LYRIASFLQLKNYVQADEDCRHVLGEGLAKGEDAFRAVLCCMQLKGKLQPVSTILAKSLT" +
            "GESLNGMVTKDLTRLKTLLSETETATSNALSGYHVEDLDEGSCNGWHFRPPPRGITSSEE" +
            "YTLCKRFLEQGICRYGAQCTSAHSQEELAEWQKRYASRLIKLKQQNENKQLSGSYMETLI" +
            "EKWMNSLSPEKVLSECIEGVKVEHNPDLSVTVSTKKSHQTWTFALTCKPARMLYRVALLY" +
            "DAHRPHFSIIAISAGDSTTQVSQEVPENCQEWIGGKMAQNGLDHYVYKVGIAFNTEIFGT" +
            "FRQTIVFDFGLEPVLMQRVMIDAASTEDLEYLMHAKQQLVTTAKRWDSSSKTIIDFEPNE" +
            "TTDLEKSLLIRYQIPLSADQLFTQSVLDKSLTKSNYQSRLHDLLYIEEIAQYKEISKFNL" +
            "KVQLQILASFMLTGVSGGAKYAQNGQLFGRFKLTETLSEDTLAGRLVMTKVNAVYLLPVP" +
            "KQKLVQTQGTKEKVYEATIEEKTKEYIFLRLSRECCEELNLRPDCDTQVELQFQLNRLPL" +
            "CEMHYALDRIKDNGVLFPDISMTPTIPWSPNRQWDEQLDPRLNAKQKEAVLAITTPLAIQ" +
            "LPPVLIIGPYGTGKTFTLAQAVKHILQQQETRILICTHSNSAADLYIKDYLHPYVEAGNP" +
            "QARPLRVYFRNRWVKTVHPVVHQYCLISSAHSTFQMPQKEDILKHRVVVVTLNTSQYLCQ" +
            "LDLEPGFFTHILLDEAAQAMECETIMPLALATQNTRIVLAGDHMQLSPFVYSEFARERNL" +
            "HVSLLDRLYEHYPAEFPCRILLCENYRSHEAIINYTSELFYEGKLMASGKQPAHKDFYPL" +
            "TFFTARGEDVQEKNSTAFYNNAEVFEVVERVEELRRKWPVAWGKLDDGSIGVVTPYADQV" +
            "FRIRAELRKKRLSDVNVERVLNVQGKQFRVLFLSTVRTRHTCKHKQTPIKKKEQLLEDST" +
            "EDLDYGFLSNYKLLNTAITRAQSLVAVVGDPIALCSIGRCRKFWERFIALCHENSSLHGI" +
            "TFEQIKAQLEALELKKTYVLNPLAPEFIPRALRLQHSGSTNKQQQSPPKGKSLHHTQNDH" +
            "FQNDGIVQPNPSVLIGNPIRAYTPPPPLGPHPNLGKSPSPVQRIDPHTGTSILYVPAVYG" +
            "GNVVMSVPLPVPWTGYQGRFAVDPRIITHQAAMAYNMNLLQTHGRGSPIPYGLGHHPPVT" +
            "IGQPQNQHQEKDQHEQNRNGKSDTNNSGPEINKIRTPEKKPTEPKQVDLESNPQNRSPES" +
            "RPSVVYPSTKFPRKDNLNPRHINLPLPAPHAQYAIPNRHFHPLPQLPRPPFPIPQQHTLL" +
            "NQQQNNLPEQPNQIPPQPNQVVQQQSQLNQQPQQPPPQLSPAYQAGPNNAFFNSAVAHRP" +
            "QSPPAEAVIPEQQPPPMLQEGHSPLRAIAQPGPILPSHLNSFIDENPSGLPIGEALDRIH" +
            "GSVALETLRQQQARFQQWSEHHAFLSQGSAPYPHHHHPHLQHLPQPPLGLHQPPVRADWK" +
            "LTSSAEDEVETTYSRFQDLIRELSHRDQSETRELAEMPPPQSRLLQYRQVQSRSPPAVPS" +
            "PPSSTDHSSHFSNFNDNSRDIEVASNPAFPQRLPPQIFNSPFSLPSEHLAPPPLKYLAPD" +
            "GAWTFANLQQNHLMGPGFPYGLPPLPHRPPQNPFVQIQNHQHAIGQEPFHPLSSRTVSSS" +
            "SLPSLEEYEPRGPGRPLYQRRISSSSVQPCSEEVSTPQDSLAQCKELQDHSNQSSFNFSS" +
            "PESWVNTTSSTPYQNIPCNGSSRTAQPRELIAPPKTVKPPEDQLKSENLEVSSSFNYSVL" +
            "QHLGQFPPLMPNKQIAESANSSSPQSSAGGKPAMSYASALRAPPKPRPPPEQAKKSSDPL" +
            "SLFQELSLGSSSGSNGFYSYFK";

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
        remap = new IntactProteinRemapper();
        remap.addRemapListener(new RemapListener() {
            public void fireRemapReport(RemapReport report) {
                remapReport = report;
            }
        });
        remap.addRemapListener(new LoggingRemapListener());

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
        assertTrue(remapReport.isRemapped());
        assertNotNull(protein.getUniprotkb());
        assertFalse(protein.getUniprotkb().equalsIgnoreCase(TESTID));
        assertEquals("P08246",protein.getUniprotkb());
    }

    @Test
    public void test_sequence_returns_correct_identifier(){
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.setSequence(test_sequence);
        assertNull(protein.getUniprotkb());

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID,protein.getUniprotkb());
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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertTrue(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertTrue(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertTrue(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertFalse(remapReport.isMappingFromIdentifiers());
        assertTrue(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertFalse(remapReport.isMappingFromIdentifiers());
        assertTrue(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertFalse(remapReport.isMappingFromIdentifiers());
        assertTrue(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromSequence());
        assertFalse(remapReport.isMappingFromIdentifiers());
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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertTrue(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(UNMAPPABLE);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertTrue(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(MAPPABLE_B);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertTrue(remapReport.isRemapped());
        assertTrue(remapReport.isMappingFromIdentifiers());
        assertTrue(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertFalse(remapReport.isRemapped());
        assertFalse(remapReport.isMappingFromIdentifiers());
        assertFalse(remapReport.isMappingFromSequence());

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
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getXrefs().add(CONFLICT);
        protein.getXrefs().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.remapProtein(protein);
        assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        assertNotNull(remapReport.getConflictMessage());
    }

}
