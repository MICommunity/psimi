package psidev.psi.mi.tab.model.builder;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 16/07/2012
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class MitabWriterUtilsTest {

    private String[] line25 = {
            "innatedb:IDBG-40102",
            "innatedb:IDBG-4279",
            "ensembl:ENSG00000175104",
            "ensembl:ENSG00000141655",
            "uniprotkb:Q9Y4K3|uniprotkb:TRAF6_HUMAN|refseq:NM_145803|refseq:NM_004620|refseq:NP_004611|refseq:NP_665802|hgnc:TNFRSF11A(display_short)",
            "uniprotkb:Q9Y6Q6|uniprotkb:TNR11_HUMAN|refseq:NM_003839|refseq:NP_003830|hgnc:TNFRSF11A(display_short)",
            "psi-mi:\"MI:0007\"(anti tag coimmunoprecipitation)",
            "Arron et al. (2001)",
            "pubmed:11406619",
            "taxid:9606(Human)",
            "taxid:9606(Human)",
            "psi-mi:\"MI:0915\"(physical association)",
            "psi-mi:\"MI:0974\"(innatedb)",
            "innatedb:IDB-113260",
            "lpr:2|hpr:2|np:1"
    };

    private String[] line26 = {
            "innatedb:IDBG-40102",
            "innatedb:IDBG-4279",
            "ensembl:ENSG00000175104",
            "ensembl:ENSG00000141655",
            "uniprotkb:Q9Y4K3|uniprotkb:TRAF6_HUMAN|refseq:NM_145803|refseq:NM_004620|refseq:NP_004611|refseq:NP_665802|hgnc:TNFRSF11A(display_short)",
            "uniprotkb:Q9Y6Q6|uniprotkb:TNR11_HUMAN|refseq:NM_003839|refseq:NP_003830|hgnc:TNFRSF11A(display_short)",
            "psi-mi:\"MI:0007\"(anti tag coimmunoprecipitation)",
            "Arron et al. (2001)",
            "pubmed:11406619",
            "taxid:9606(Human)",
            "taxid:9606(Human)",
            "psi-mi:\"MI:0915\"(physical association)",
            "psi-mi:\"MI:0974\"(innatedb)",
            "innatedb:IDB-113260",
            "lpr:2|hpr:2|np:1",
            "-",
            "psi-mi:\"MI:0499\"(unspecified role)",
            "psi-mi:\"MI:0499\"(unspecified role)",
            "psi-mi:\"MI:0498\"(prey)",
            "psi-mi:\"MI:0496\"(bait)",
            "psi-mi:\"MI:0326\"(protein)",
            "psi-mi:\"MI:0326\"(protein)",
            "-",
            "-",
            "-",
            "-",
            "-",
            "-",
            "taxid:9606",
            "-",
            "2008/03/30",
            "2008/03/30",
            "-",
            "-",
            "-",
            "false"
    };

    private String[] line27 = {
            "innatedb:IDBG-40102",
            "innatedb:IDBG-4279",
            "ensembl:ENSG00000175104",
            "ensembl:ENSG00000141655",
            "uniprotkb:Q9Y4K3|uniprotkb:TRAF6_HUMAN|refseq:NM_145803|refseq:NM_004620|refseq:NP_004611|refseq:NP_665802|hgnc:TNFRSF11A(display_short)",
            "uniprotkb:Q9Y6Q6|uniprotkb:TNR11_HUMAN|refseq:NM_003839|refseq:NP_003830|hgnc:TNFRSF11A(display_short)",
            "psi-mi:\"MI:0007\"(anti tag coimmunoprecipitation)",
            "Arron et al. (2001)",
            "pubmed:11406619",
            "taxid:9606(Human)",
            "taxid:9606(Human)",
            "psi-mi:\"MI:0915\"(physical association)",
            "psi-mi:\"MI:0974\"(innatedb)",
            "innatedb:IDB-113260",
            "lpr:2|hpr:2|np:1",
            "-",
            "psi-mi:\"MI:0499\"(unspecified role)",
            "psi-mi:\"MI:0499\"(unspecified role)",
            "psi-mi:\"MI:0498\"(prey)",
            "psi-mi:\"MI:0496\"(bait)",
            "psi-mi:\"MI:0326\"(protein)",
            "psi-mi:\"MI:0326\"(protein)",
            "-",
            "-",
            "-",
            "-",
            "-",
            "-",
            "taxid:9606",
            "-",
            "2008/03/30",
            "2008/03/30",
            "-",
            "-",
            "-",
            "false",
            "-",
            "-",
            "-",
            "-",
            "psi-mi:\"MI:0363\"(inferred by author)",
            "psi-mi:\"MI:0363\"(inferred by author)"};


    BinaryInteraction<Interactor> buildInteraction() {

        Interactor A = new Interactor();
        Interactor B = new Interactor();

        BinaryInteraction<Interactor> interactionToCompare = new BinaryInteractionImpl(A, B);


        A.setIdentifiers(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("innatedb", "IDBG-40102"))));
        B.setIdentifiers(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("innatedb", "IDBG-4279"))));
        A.setAlternativeIdentifiers(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("ensembl", "ENSG00000175104"))));
        B.setAlternativeIdentifiers(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("ensembl", "ENSG00000141655"))));

        Collection<Alias> aliasesA = new ArrayList<Alias>() {{
            add(new AliasImpl("uniprotkb", "Q9Y4K3"));
            add(new AliasImpl("uniprotkb", "TRAF6_HUMAN"));
            add(new AliasImpl("refseq", "NM_145803"));
            add(new AliasImpl("refseq", "NM_004620"));
            add(new AliasImpl("refseq", "NP_004611"));
            add(new AliasImpl("refseq", "NP_665802"));
            add(new AliasImpl("hgnc", "TNFRSF11A", "display_short"));
        }};
        A.setAliases(aliasesA);

        Collection<Alias> aliasesB = new ArrayList<Alias>() {{
            add(new AliasImpl("uniprotkb", "Q9Y6Q6"));
            add(new AliasImpl("uniprotkb", "TNR11_HUMAN"));
            add(new AliasImpl("refseq", "NM_003839"));
            add(new AliasImpl("refseq", "NP_003830"));
            add(new AliasImpl("hgnc", "TNFRSF11A", "display_short"));
        }};
        B.setAliases(aliasesB);

        interactionToCompare.setDetectionMethods(new ArrayList<InteractionDetectionMethod>(
                Collections.singletonList(
                        new InteractionDetectionMethodImpl("psi-mi", "MI:0007", "anti tag coimmunoprecipitation"))));
        interactionToCompare.setAuthors(new ArrayList<Author>(
                Collections.singletonList(new AuthorImpl("Arron et al. (2001)"))));
        interactionToCompare.setPublications(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("pubmed", "11406619"))));

        Organism organism = new OrganismImpl();
        organism.setIdentifiers(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("taxid", "9606", "Human"))));

        A.setOrganism(organism);
        B.setOrganism(organism);


        interactionToCompare.setInteractionTypes(new ArrayList<InteractionType>(
                Collections.singletonList(new InteractionTypeImpl("psi-mi", "MI:0915", "physical association"))));
        interactionToCompare.setSourceDatabases(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0974", "innatedb"))));
        interactionToCompare.setInteractionAcs(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("innatedb", "IDB-113260"))));

        List<Confidence> confidences = new ArrayList<Confidence>() {{
            add(new ConfidenceImpl("lpr", "2"));
            add(new ConfidenceImpl("hpr", "2"));
            add(new ConfidenceImpl("np", "1"));
        }};

        interactionToCompare.setConfidenceValues(confidences);

//        interactionToCompare.setComplexExpansion();

        A.setBiologicalRoles(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0499", "unspecified role"))));
        B.setBiologicalRoles(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0499", "unspecified role"))));
        A.setExperimentalRoles(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0498", "prey"))));
        B.setExperimentalRoles(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0496", "bait"))));
        A.setInteractorTypes(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0326", "protein"))));
        B.setInteractorTypes(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0326", "protein"))));

//        A.setXrefs();
//        B.setXrefs();
//        interactionToCompare.setXrefs();
//        A.setAnnotations();
//        B.setAnnotations();
//        interactionToCompare.setAnnotations();

        Organism hostOrganism = new OrganismImpl();
        hostOrganism.setIdentifiers(new ArrayList<CrossReference>(
                Collections.singletonList(new CrossReferenceImpl("taxid", "9606"))));

        interactionToCompare.setHostOrganism(hostOrganism);

//        interactionToCompare.setParameters();

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = formatter.parse("2008/03/30");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        interactionToCompare.setCreationDate(new ArrayList<Date>(
                Collections.singletonList(date)));
        interactionToCompare.setUpdateDate(new ArrayList<Date>(
                Collections.singletonList(date)));

//        A.setChecksums();
//        B.setChecksums();
//        interactionToCompare.setChecksums();

        interactionToCompare.setNegativeInteraction(false);

//        A.setFeatures();
//        B.setFeatures();
//        A.setStoichiometry();
//        B.setStoichiometry();


        A.setParticipantIdentificationMethods(new ArrayList<ParticipantIdentificationMethod>(
                Collections.singletonList(new ParticipantIdentificationMethodImpl("psi-mi", "MI:0363", "inferred by author"))));
        B.setParticipantIdentificationMethods(new ArrayList<ParticipantIdentificationMethod>(
                Collections.singletonList(new ParticipantIdentificationMethodImpl("psi-mi", "MI:0363", "inferred by author"))));

        return interactionToCompare;
    }

    @Test
    public void testBuildHeader() throws Exception {

        String[] header1 = MitabWriterUtils.buildHeader(PsimiTab.VERSION_2_5);
        Assert.assertEquals(PsimiTab.VERSION_2_5,header1.length);

        String[] header2 = MitabWriterUtils.buildHeader(PsimiTab.VERSION_2_6);
        Assert.assertEquals(PsimiTab.VERSION_2_6,header2.length);

        String [] header3 = MitabWriterUtils.buildHeader(PsimiTab.VERSION_2_7);
        Assert.assertEquals(PsimiTab.VERSION_2_7,header3.length);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildHeaderException() throws Exception {

        final int WRONG_VERSION = 40;
        MitabWriterUtils.buildHeader(WRONG_VERSION);

    }


    @Test
    public void testBuildLine() throws Exception {
        String[] result1 = MitabWriterUtils.buildLine(buildInteraction(),PsimiTab.VERSION_2_5);
        Assert.assertEquals(Arrays.toString(result1), Arrays.toString(line25));

        String[] result2 = MitabWriterUtils.buildLine(buildInteraction(),PsimiTab.VERSION_2_6);
        Assert.assertEquals(Arrays.toString(result2), Arrays.toString(line26));

        String[] result3 = MitabWriterUtils.buildLine(buildInteraction(),PsimiTab.VERSION_2_7);
        Assert.assertEquals(Arrays.toString(result3), Arrays.toString(line27));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildLineException() throws Exception {
        final int WRONG_VERSION = 40;
        MitabWriterUtils.buildLine(buildInteraction(),WRONG_VERSION);
    }
}
