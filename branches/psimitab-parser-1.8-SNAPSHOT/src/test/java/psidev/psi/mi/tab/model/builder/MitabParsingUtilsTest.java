package psidev.psi.mi.tab.model.builder;


import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.BinaryInteractionImpl;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 16/07/2012
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class MitabParsingUtilsTest {
    @Test
    public void testQuoteAwareSplit() throws Exception {
        Assert.assertEquals(3, MitabParsingUtils.quoteAwareSplit("a:b(c)", new char[]{':', '(', ')'}, true).length);

        Assert.assertEquals(2, MitabParsingUtils.quoteAwareSplit("aaaaa|bbbb", new char[]{'|'}, true).length);
        Assert.assertEquals(2, MitabParsingUtils.quoteAwareSplit("\"aaa|aaa\"|bbbbb", new char[]{'|'}, true).length);
        Assert.assertEquals(2, MitabParsingUtils.quoteAwareSplit("\"a\\aa|aaa\"|bbbbb", new char[]{'|'}, true).length);
        Assert.assertEquals(2, MitabParsingUtils.quoteAwareSplit("\"aa\\\"a|a\\\"aa\"|bbbbb", new char[]{'|'}, true).length);
        Assert.assertEquals(2, MitabParsingUtils.quoteAwareSplit("\"a(a:a)a\":\"b(b:b)b\"(\"c(c:c)c\")" +
                "|" +
                "\"a(\\\"a:a)a\":\"b(\\\"b:b\\\")b\"(\"c(c:c)\\\"c\")", new char[]{'|'}, true).length);

    }

    @Test(expected = NullPointerException.class)
    public void testQuoteAwareSplit_no_str() throws Exception {
        MitabParsingUtils.quoteAwareSplit(null, new char[]{':', '(', ')'}, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuoteAwareSplit_empty_str() throws Exception {
        MitabParsingUtils.quoteAwareSplit("", new char[]{':', '(', ')'}, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuoteAwareSplit_empty_delimiter() throws Exception {
        MitabParsingUtils.quoteAwareSplit("lala", new char[]{}, true);
    }

    @Test(expected = NullPointerException.class)
    public void testQuoteAwareSplit_null_delimiter() throws Exception {
        MitabParsingUtils.quoteAwareSplit("lala", null, true);
    }


    @Test
    public void testBuildBinaryInteraction() throws Exception {
        String[] line = {
                "innatedb:IDBG-40102",
                "innatedb:IDBG-4279",
                "ensembl:ENSG00000175104",
                "ensembl:ENSG00000141655",
                "uniprotkb:Q9Y4K3|uniprotkb:TRAF6_HUMAN|refseq:NM_145803|refseq:NM_004620|refseq:NP_004611|refseq:NP_665802|hgnc:TNFRSF11A(display_short)",
                "uniprotkb:Q9Y6Q6|uniprotkb:TNR11_HUMAN|refseq:NM_003839|refseq:NP_003830|hgnc:TNFRSF11A(display_short)",
                "psi-mi:\"MI:0007\"(\"anti tag coimmunoprecipitation\")",
                "Arron et al. (2001)",
                "pubmed:11406619",
                "taxid:9606(Human)",
                "taxid:9606(Human)",
                "psi-mi:\"MI:0915\"(physical association)",
//                "psi-mi:\"MI:0974(innatedb)\"(innatedb)", TODO Report this problem
                "psi-mi:\"MI:0974\"(innatedb)",
                "innatedb:IDB-113260",
                "lpr:2|hpr:2|np:1|",
                "-",
                "psi-mi:\"MI:0499\"(unspecified role)",
                "psi-mi:\"MI:0499\"(unspecified role)",
                "psi-mi:\"MI:0498\"(\"prey\")",
                "psi-mi:\"MI:0496\"(\"bait\")",
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

        BinaryInteraction interactionBuilt = MitabParsingUtils.buildBinaryInteraction(line);

        Interactor A = new Interactor();
        Interactor B = new Interactor();

        BinaryInteraction interactionToCompare = new BinaryInteractionImpl(A, B);


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
        Date date = formatter.parse("2008/03/30");

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

        Assert.assertEquals(A,A);
        Assert.assertEquals(B,B);
        Assert.assertEquals(A,interactionBuilt.getInteractorA());
        Assert.assertEquals(B,interactionBuilt.getInteractorB());
        Assert.assertEquals(interactionBuilt, interactionToCompare);
    }

    @Test
    public void testExtendFormat() throws Exception {

        String[] line25 = {
                "innatedb:IDBG-40102",
                "innatedb:IDBG-4279",
                "ensembl:ENSG00000175104",
                "ensembl:ENSG00000141655",
                "uniprotkb:Q9Y4K3|uniprotkb:TRAF6_HUMAN|refseq:NM_145803|refseq:NM_004620|refseq:NP_004611|refseq:NP_665802|hgnc:TNFRSF11A(display_short)",
                "uniprotkb:Q9Y6Q6|uniprotkb:TNR11_HUMAN|refseq:NM_003839|refseq:NP_003830|hgnc:TNFRSF11A(display_short)",
                "psi-mi:\"MI:0007\"(\"anti tag coimmunoprecipitation\")",
                "Arron et al. (2001)",
                "pubmed:11406619",
                "taxid:9606(Human)",
                "taxid:9606(Human)",
                "psi-mi:\"MI:0915\"(physical association)",
                "psi-mi:\"MI:0974\"(innatedb)",
                "innatedb:IDB-113260",
                "lpr:2|hpr:2|np:1"
        };

        String[] line26 = {
                "innatedb:IDBG-40102",
                "innatedb:IDBG-4279",
                "ensembl:ENSG00000175104",
                "ensembl:ENSG00000141655",
                "uniprotkb:Q9Y4K3|uniprotkb:TRAF6_HUMAN|refseq:NM_145803|refseq:NM_004620|refseq:NP_004611|refseq:NP_665802|hgnc:TNFRSF11A(display_short)",
                "uniprotkb:Q9Y6Q6|uniprotkb:TNR11_HUMAN|refseq:NM_003839|refseq:NP_003830|hgnc:TNFRSF11A(display_short)",
                "psi-mi:\"MI:0007\"(\"anti tag coimmunoprecipitation\")",
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
                "psi-mi:\"MI:0498\"(\"prey\")",
                "psi-mi:\"MI:0496\"(\"bait\")",
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

        String[] line27 = {
                "innatedb:IDBG-40102",
                "innatedb:IDBG-4279",
                "ensembl:ENSG00000175104",
                "ensembl:ENSG00000141655",
                "uniprotkb:Q9Y4K3|uniprotkb:TRAF6_HUMAN|refseq:NM_145803|refseq:NM_004620|refseq:NP_004611|refseq:NP_665802|hgnc:TNFRSF11A(display_short)",
                "uniprotkb:Q9Y6Q6|uniprotkb:TNR11_HUMAN|refseq:NM_003839|refseq:NP_003830|hgnc:TNFRSF11A(display_short)",
                "psi-mi:\"MI:0007\"(\"anti tag coimmunoprecipitation\")",
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
                "psi-mi:\"MI:0498\"(\"prey\")",
                "psi-mi:\"MI:0496\"(\"bait\")",
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


        String[] result1 = MitabParsingUtils.extendFormat(line27,PsimiTab.VERSION_2_5);
        Assert.assertEquals(PsimiTab.VERSION_2_7, result1.length);

        String[] result2 = MitabParsingUtils.extendFormat(line26,PsimiTab.VERSION_2_5);
        Assert.assertEquals(PsimiTab.VERSION_2_6,result2.length);

        String[] result3 = MitabParsingUtils.extendFormat(line25,PsimiTab.VERSION_2_5);
        Assert.assertEquals(PsimiTab.VERSION_2_5,result3.length);

        String[] result4 = MitabParsingUtils.extendFormat(line25,PsimiTab.VERSION_2_6);
        Assert.assertEquals(PsimiTab.VERSION_2_6,result4.length);

        String[] result5 = MitabParsingUtils.extendFormat(line25,PsimiTab.VERSION_2_7);
        Assert.assertEquals(PsimiTab.VERSION_2_7,result5.length);

        String[] result6 = MitabParsingUtils.extendFormat(line26,PsimiTab.VERSION_2_7);
        Assert.assertEquals(PsimiTab.VERSION_2_7,result6.length);

    }
}
