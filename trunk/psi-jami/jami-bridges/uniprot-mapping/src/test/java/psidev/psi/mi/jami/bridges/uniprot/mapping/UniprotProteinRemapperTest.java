package psidev.psi.mi.jami.bridges.uniprot.mapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.mapper.ProteinMapperListener;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import java.util.Collection;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  06/06/13
 */
public class UniprotProteinRemapperTest {

    public static final Log log = LogFactory.getLog(UniprotProteinRemapperTest.class);

    public UniprotProteinMapper remap;

    public Protein protein;

    public final String test_sequence =
            "MEDRRAEKSCEQACESLKRQDYEMALKHCTEALLSLGQYSMADFTGPCPLEIERIKIESL" +
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

    //public RemapReport remapReport;

    public  Xref MAPPABLE_A; // An xref that maps to the testid
    public  Xref MAPPABLE_B; // An xref that also maps to the testid
    public  Xref UNMAPPABLE; // An xref that maps to no protein
    public  Xref CONFLICT;   // An xref that maps to a protein other than testid

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
        remap = new UniprotProteinMapper();

        MAPPABLE_A = new DefaultXref(new DefaultCvTerm("ensembl"), "ENSP00000351524"); //P42694
        MAPPABLE_B = new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000198265"); //P42694
        UNMAPPABLE = new DefaultXref(new DefaultCvTerm("pfam"), "PF00642"); //P42694
        CONFLICT = new DefaultXref(new DefaultCvTerm("ensembl"), "ENSG00000197561"); //P08246

        protein = new DefaultProtein("test");
        protein.setOrganism(new DefaultOrganism(Integer.parseInt("9606")));
    }

    /**
     * Test that the xref chosen to cause conflicts is mappable and different from the testID
     * @throws BridgeFailedException
     */
    @Test
    public void test_conflict_xref_returns_conflicting_identifier() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getIdentifiers().add(CONFLICT);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
                //log.info("success: "+report.toString());
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);
        assertNotNull(protein.getUniprotkb());
        assertFalse(protein.getUniprotkb().equalsIgnoreCase(TESTID));
        assertEquals("P08246", protein.getUniprotkb());
    }

    /**
     * Test that the xref chosen to cause conflicts is mappable and different from the testID
     * @throws BridgeFailedException
     */
    @Test
    public void test_mappableA_xref_returns_testID_identifier() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
                //log.info("success: "+report.toString());
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);
        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
    }

    /**
     * Test that the xref chosen to cause conflicts is mappable and different from the testID
     * @throws BridgeFailedException
     */
    @Test
    public void test_mappableB_xref_returns_testID_identifier() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getIdentifiers().add(MAPPABLE_B);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
                //log.info("success: "+report.toString());
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);
        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
    }

    @Test
    public void test_sequence_returns_correct_identifier() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.setSequence(test_sequence);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID , protein.getUniprotkb());
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
    public void test_mappable_xref_with_no_sequence_UseIds_UseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);

        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
               fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);
        assertNotNull(protein.getUniprotkb());
    }

    // ID 1 , SEQ 0

    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_no_sequence_UseIds_NOTUseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);
        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_no_sequence_UseIds_NOTUseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getIdentifiers().add(UNMAPPABLE);
        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_no_sequence_UseIds_NOTUseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getIdentifiers().add(MAPPABLE_B);
        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
    }

    /**
     * Checking // NO SEQUENCE tests // ID 1 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_no_sequence_UseIds_NOTUseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(false);

        protein.getIdentifiers().add(CONFLICT);
        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());


        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
            }
        });


        remap.map(protein);
        assertNull(protein.getUniprotkb());
    }

    // ID 0 , SEQ 1

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref - no sequence
     */
    @Test
    public void test_mappable_xref_with_no_sequence_NOTUseIds_UseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 1 mappable xref, 1 unmappable
     */
    @Test
    public void test_mappable_xref_and_unmappable_xref_with_no_sequence_NOTUseIds_UseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getIdentifiers().add(UNMAPPABLE);
        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
    }


    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs (no conflict)
     */
    @Test
    public void test_none_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_UseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getIdentifiers().add(MAPPABLE_B);
        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                assertTrue(p == protein);
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {
                fail();
            }
        });

        remap.map(protein);

        assertNotNull(protein.getUniprotkb());
        assertEquals(TESTID, protein.getUniprotkb());
    }

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 1
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_UseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(true);

        protein.getIdentifiers().add(CONFLICT);
        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());

        remap.setListener(new ProteinMapperListener() {
            public void onSuccessfulMapping(Protein p, Collection<String> report) {
                log.info("SU: "+report);
                fail();
            }

            public void onFailedMapping(Protein p, Collection<String> report) {
                fail();
            }

            public void onToBeReviewedMapping(Protein p, Collection<String> report) {

                assertTrue(p == protein);
            }
        });

        remap.map(protein);
        assertNull(protein.getUniprotkb());
    }

    /**
     * Checking // NO SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_no_sequence_NOTUseIds_NOTUseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);

        protein.getIdentifiers().add(CONFLICT);
        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.map(protein);
        // assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        // assertNotNull(remapReport.getConflictMessage());
    }

    /**
     * Checking // SEQUENCE tests // ID 1 , SEQ 1
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_UseIds_UseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(true);
        remap.setPrioritySequence(true);
        protein.setSequence(test_sequence);

        protein.getIdentifiers().add(CONFLICT);
        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());


        remap.map(protein);
        // assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        // assertNotNull(remapReport.getConflictMessage());
    }


    /**
     * Checking // SEQUENCE tests // ID 0 , SEQ 0
     * 2 mappable xrefs, (with conflict)
     */
    @Test
    public void test_conflicting_mappable_xrefs_with_sequence_NOTUseIds_NOTUseSeq() throws BridgeFailedException {
        remap.setCheckingEnabled(true);
        remap.setPriorityIdentifiers(false);
        remap.setPrioritySequence(false);
        protein.setSequence(test_sequence);

        protein.getIdentifiers().add(CONFLICT);
        protein.getIdentifiers().add(MAPPABLE_A);
        assertNull(protein.getUniprotkb());
        

        remap.map(protein);
        //assertFalse(remapReport.isRemapped());
        assertNull(protein.getUniprotkb());
        //assertNotNull(remapReport.getConflictMessage());
    }

}
