package psidev.psi.mi.tab.model.builder;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 16/07/2012
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class MitabWriterUtilsTest {

	@Test(expected = IllegalArgumentException.class)
    public void testBuildHeaderException() throws Exception {

        MitabWriterUtils.buildHeader(null);

    }


    @Test
    public void testBuildLine() throws Exception {
        String result1 = MitabWriterUtils.buildLine(buildInteraction(), PsimiTabVersion.v2_5);
        Assert.assertEquals(result1, MitabWriterUtils.createMitabLine(line25, PsimiTabVersion.v2_5));

        String result2 = MitabWriterUtils.buildLine(buildInteraction(), PsimiTabVersion.v2_6);
        Assert.assertEquals(result2, MitabWriterUtils.createMitabLine(line26, PsimiTabVersion.v2_6));

        String result3 = MitabWriterUtils.buildLine(buildInteraction(), PsimiTabVersion.v2_7);
        Assert.assertEquals(result3, MitabWriterUtils.createMitabLine(line27, PsimiTabVersion.v2_7));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildLineException() throws Exception {
        MitabWriterUtils.buildLine(buildInteraction(), null);
    }

	@Test
	public void testIntraIterMoleculeInteractionLine() throws Exception {
		String result1 = MitabWriterUtils.buildLine(buildIntraIterMoleculeAInteraction(), PsimiTabVersion.v2_7);
		Assert.assertEquals(result1, MitabWriterUtils.createMitabLine(interactorBNull, PsimiTabVersion.v2_7));

		String result2 = MitabWriterUtils.buildLine(buildIntraIterMoleculeBInteraction(), PsimiTabVersion.v2_7);
		Assert.assertEquals(result2, MitabWriterUtils.createMitabLine(interactorANull, PsimiTabVersion.v2_7));

	}


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

	String[] interactorANull ={
			"-",
			"innatedb:IDBG-4279",
			"-",
			"ensembl:ENSG00000141655",
			"-",
			"uniprotkb:Q9Y6Q6",
			"psi-mi:\"MI:0007\"(anti tag coimmunoprecipitation)",
			"Arron et al. (2001)",
			"pubmed:11406619",
			"-",
			"taxid:9606(Human)",
			"psi-mi:\"MI:0915\"(physical association)",
			"psi-mi:\"MI:0974\"(innatedb)",
			"innatedb:IDB-113260",
			"lpr:2|hpr:2|np:1",
			"-",
			"-",
			"psi-mi:\"MI:0499\"(unspecified role)",
			"-",
			"psi-mi:\"MI:0496\"(bait)",
			"-",
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
			"2",
			"-",
			"psi-mi:\"MI:0363\"(inferred by author)"};

	String[] interactorBNull = {
			"innatedb:IDBG-40102",
			"-",
			"ensembl:ENSG00000175104",
			"-",
			"uniprotkb:Q9Y4K3",
			"-",
			"psi-mi:\"MI:0007\"(anti tag coimmunoprecipitation)",
			"Arron et al. (2001)",
			"pubmed:11406619",
			"taxid:9606(Human)",
			"-",
			"psi-mi:\"MI:0915\"(physical association)",
			"psi-mi:\"MI:0974\"(innatedb)",
			"innatedb:IDB-113260",
			"lpr:2|hpr:2|np:1",
			"-",
			"psi-mi:\"MI:0499\"(unspecified role)",
			"-",
			"psi-mi:\"MI:0498\"(prey)",
			"-",
			"psi-mi:\"MI:0326\"(protein)",
			"-",
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
			"2",
			"-",
			"psi-mi:\"MI:0363\"(inferred by author)",
			"-"};


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

		List<Alias> aliasesA = new ArrayList<Alias>() {{
			add(new AliasImpl("uniprotkb", "Q9Y4K3"));
			add(new AliasImpl("uniprotkb", "TRAF6_HUMAN"));
			add(new AliasImpl("refseq", "NM_145803"));
			add(new AliasImpl("refseq", "NM_004620"));
			add(new AliasImpl("refseq", "NP_004611"));
			add(new AliasImpl("refseq", "NP_665802"));
			add(new AliasImpl("hgnc", "TNFRSF11A", "display_short"));
		}};
		A.setInteractorAliases(aliasesA);

		List<Alias> aliasesB = new ArrayList<Alias>() {{
			add(new AliasImpl("uniprotkb", "Q9Y6Q6"));
			add(new AliasImpl("uniprotkb", "TNR11_HUMAN"));
			add(new AliasImpl("refseq", "NM_003839"));
			add(new AliasImpl("refseq", "NP_003830"));
			add(new AliasImpl("hgnc", "TNFRSF11A", "display_short"));
		}};
		B.setInteractorAliases(aliasesB);

		interactionToCompare.setDetectionMethods(new ArrayList<CrossReference>(
				Collections.singletonList(
						new CrossReferenceImpl("psi-mi", "MI:0007", "anti tag coimmunoprecipitation"))));
		interactionToCompare.setAuthors(new ArrayList<Author>(
				Collections.singletonList(new AuthorImpl("Arron et al. (2001)"))));
		interactionToCompare.setPublications(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("pubmed", "11406619"))));

		Organism organism = new OrganismImpl();
		organism.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("taxid", "9606", "Human"))));

		A.setOrganism(organism);
		B.setOrganism(organism);


		interactionToCompare.setInteractionTypes(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0915", "physical association"))));
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

//        A.setMitabXrefs();
//        B.setMitabXrefs();
//        interactionToCompare.setMitabXrefs();
//        A.setMitabAnnotations();
//        B.setMitabAnnotations();
//        interactionToCompare.setMitabAnnotations();

		Organism hostOrganism = new OrganismImpl();
		hostOrganism.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("taxid", "9606"))));

		interactionToCompare.setHostOrganism(hostOrganism);

//        interactionToCompare.setMitabParameters();

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

//        A.setMitabChecksums();
//        B.setMitabChecksums();
//        interactionToCompare.setMitabChecksums();

		interactionToCompare.setNegativeInteraction(false);

//        A.setFeatures();
//        B.setFeatures();
//        A.setStoichiometry();
//        B.setStoichiometry();


		A.setParticipantIdentificationMethods(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0363", "inferred by author"))));
		B.setParticipantIdentificationMethods(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0363", "inferred by author"))));

		return interactionToCompare;
	}

	BinaryInteraction<Interactor> buildIntraIterMoleculeAInteraction() {

		Interactor A = new Interactor();

		BinaryInteraction<Interactor> interactionToCompare = new BinaryInteractionImpl(A, null);


		A.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("innatedb", "IDBG-40102"))));
		A.setAlternativeIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("ensembl", "ENSG00000175104"))));

		List<Alias> aliasesA = new ArrayList<Alias>() {{
			add(new AliasImpl("uniprotkb", "Q9Y4K3"));
		}};
		A.setInteractorAliases(aliasesA);

		interactionToCompare.setDetectionMethods(new ArrayList<CrossReference>(
				Collections.singletonList(
						new CrossReferenceImpl("psi-mi", "MI:0007", "anti tag coimmunoprecipitation"))));
		interactionToCompare.setAuthors(new ArrayList<Author>(
				Collections.singletonList(new AuthorImpl("Arron et al. (2001)"))));
		interactionToCompare.setPublications(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("pubmed", "11406619"))));

		Organism organism = new OrganismImpl();
		organism.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("taxid", "9606", "Human"))));

		A.setOrganism(organism);

		interactionToCompare.setInteractionTypes(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0915", "physical association"))));
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
		A.setExperimentalRoles(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0498", "prey"))));
		A.setInteractorTypes(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0326", "protein"))));

//        A.setMitabXrefs();
//        interactionToCompare.setMitabXrefs();
//        A.setMitabAnnotations();
//        interactionToCompare.setMitabAnnotations();

		Organism hostOrganism = new OrganismImpl();
		hostOrganism.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("taxid", "9606"))));

		interactionToCompare.setHostOrganism(hostOrganism);

//        interactionToCompare.setMitabParameters();

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

//        A.setMitabChecksums();
//        interactionToCompare.setMitabChecksums();

		interactionToCompare.setNegativeInteraction(false);

//        A.setFeatures();

		A.setInteractorStoichiometry(new ArrayList<Integer>(
				Collections.singletonList(new Integer("2"))));

		A.setParticipantIdentificationMethods(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0363", "inferred by author"))));

		return interactionToCompare;
	}

	BinaryInteraction<Interactor> buildIntraIterMoleculeBInteraction() {

		Interactor B = new Interactor();

		BinaryInteraction<Interactor> interactionToCompare = new BinaryInteractionImpl(null, B);

		B.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("innatedb", "IDBG-4279"))));
		B.setAlternativeIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("ensembl", "ENSG00000141655"))));

		List<Alias> aliasesB = new ArrayList<Alias>() {{
			add(new AliasImpl("uniprotkb", "Q9Y6Q6"));
		}};
		B.setInteractorAliases(aliasesB);

		interactionToCompare.setDetectionMethods(new ArrayList<CrossReference>(
				Collections.singletonList(
						new CrossReferenceImpl("psi-mi", "MI:0007", "anti tag coimmunoprecipitation"))));
		interactionToCompare.setAuthors(new ArrayList<Author>(
				Collections.singletonList(new AuthorImpl("Arron et al. (2001)"))));
		interactionToCompare.setPublications(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("pubmed", "11406619"))));

		Organism organism = new OrganismImpl();
		organism.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("taxid", "9606", "Human"))));

		B.setOrganism(organism);


		interactionToCompare.setInteractionTypes(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0915", "physical association"))));
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

		B.setBiologicalRoles(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0499", "unspecified role"))));
		B.setExperimentalRoles(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0496", "bait"))));
		B.setInteractorTypes(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0326", "protein"))));


//        B.setMitabXrefs();
//        interactionToCompare.setMitabXrefs();
//        B.setMitabAnnotations();
//        interactionToCompare.setMitabAnnotations();

		Organism hostOrganism = new OrganismImpl();
		hostOrganism.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("taxid", "9606"))));

		interactionToCompare.setHostOrganism(hostOrganism);

//        interactionToCompare.setMitabParameters();

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

//        B.setMitabChecksums();
//        interactionToCompare.setMitabChecksums();

		interactionToCompare.setNegativeInteraction(false);


//        B.setFeatures();

        B.setInteractorStoichiometry(new ArrayList<Integer>(
				Collections.singletonList(new Integer("2"))));

		B.setParticipantIdentificationMethods(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0363", "inferred by author"))));

		return interactionToCompare;
	}

}
