package psidev.psi.mi.tab.model.builder;


import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.model.*;

import java.text.DateFormat;
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
public class MitabParserUtilsTest {
	@Test
	public void testQuoteAwareSplit() throws Exception {
		Assert.assertEquals(3, MitabParserUtils.quoteAwareSplit("a:b(c)", new char[]{':', '(', ')'}, true).length);

		Assert.assertEquals(2, MitabParserUtils.quoteAwareSplit("aaaaa|bbbb", new char[]{'|'}, true).length);
		Assert.assertEquals(2, MitabParserUtils.quoteAwareSplit("\"aaa|aaa\"|bbbbb", new char[]{'|'}, true).length);
		Assert.assertEquals(2, MitabParserUtils.quoteAwareSplit("\"a\\aa|aaa\"|bbbbb", new char[]{'|'}, true).length);
		Assert.assertEquals(2, MitabParserUtils.quoteAwareSplit("\"aa\\\"a|a\\\"aa\"|bbbbb", new char[]{'|'}, true).length);
		Assert.assertEquals(2, MitabParserUtils.quoteAwareSplit("\"a(a:a)a\":\"b(b:b)b\"(\"c(c:c)c\")" +
				"|" +
				"\"a(\\\"a:a)a\":\"b(\\\"b:b\\\")b\"(\"c(c:c)\\\"c\")", new char[]{'|'}, true).length);

	}

	@Test(expected = NullPointerException.class)
	public void testQuoteAwareSplit_no_str() throws Exception {
		MitabParserUtils.quoteAwareSplit(null, new char[]{':', '(', ')'}, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testQuoteAwareSplit_empty_str() throws Exception {
		MitabParserUtils.quoteAwareSplit("", new char[]{':', '(', ')'}, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testQuoteAwareSplit_empty_delimiter() throws Exception {
		MitabParserUtils.quoteAwareSplit("lala", new char[]{}, true);
	}

	@Test(expected = NullPointerException.class)
	public void testQuoteAwareSplit_null_delimiter() throws Exception {
		MitabParserUtils.quoteAwareSplit("lala", null, true);
	}

	@Test
	public void quoteAwareSplit1() throws Exception {
		String str = "a:b(c)";
		final String[] splitted = MitabParserUtils.quoteAwareSplit(str, new char[]{':', '(', ')'}, true);
		Assert.assertEquals("a", splitted[0]);
		Assert.assertEquals("b", splitted[1]);
		Assert.assertEquals("c", splitted[2]);
	}

	@Test
	public void quoteAwareSplit2() throws Exception {
		String str = "a:\"(+)b\"(c)";
		final String[] splitted = MitabParserUtils.quoteAwareSplit(str, new char[]{':', '(', ')'}, true);
		Assert.assertEquals("a", splitted[0]);
		Assert.assertEquals("(+)b", splitted[1]);
		Assert.assertEquals("c", splitted[2]);
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
				"psi-mi:\"MI:0974\"(innatedb)",
				"innatedb:IDB-113260",
				"lpr:2|",
				"psi-mi:\"MI:1060\"(spoke expansion)",
				"psi-mi:\"MI:0499\"(unspecified role)",
				"psi-mi:\"MI:0499\"(unspecified role)",
				"psi-mi:\"MI:0498\"(\"prey\")",
				"psi-mi:\"MI:0496\"(\"bait\")",
				"psi-mi:\"MI:0326\"(protein)",
				"psi-mi:\"MI:0326\"(protein)",
				"uniprotkb:D3DRX9(secondary-ac)",
				"refseq:NP_001447.2",
				"intact:EBI-5627041(see-also)|imex:IM-17229-3(imex-primary)",
				"comment:\"sequence not available in uniprotKb\"",
				"anti-bacterial",
				"curation depth:imex curation",
				"taxid:9606",
				"kd:1.36x10^-6(molar)",
				"2008/03/30",
				"2008/03/30",
				"crc64:6C1A07041DF50142",
				"crc64:2F6FEFCDF2C80457",
				"intact-crc:08C4486B755C70C0",
				"false",
				"necessary binding region:2171-2647",
				"necessary binding region:757-800",
				"2",
				"2",
				"psi-mi:\"MI:0363\"(inferred by author)",
				"psi-mi:\"MI:0363\"(inferred by author)",
				"go:\"GO:0016301\"(kinase activity)",
				"go:\"GO:0016301\"(kinase activity)",
				"psi-mi:\"MI:2249\"(post transcriptional regulation)",
				"psi-mi:\"MI:2240\"(down regulates)"};

		BinaryInteraction interactionBuilt = MitabParserUtils.buildBinaryInteraction(line);

		Interactor A = new Interactor();
		Interactor B = new Interactor();

		BinaryInteraction interactionToCompare = new BinaryInteractionImpl(A, B);

		// MITAB 2.5
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
		A.setAliases(aliasesA);

		List<Alias> aliasesB = new ArrayList<Alias>() {{
			add(new AliasImpl("uniprotkb", "Q9Y6Q6"));
			add(new AliasImpl("uniprotkb", "TNR11_HUMAN"));
			add(new AliasImpl("refseq", "NM_003839"));
			add(new AliasImpl("refseq", "NP_003830"));
			add(new AliasImpl("hgnc", "TNFRSF11A", "display_short"));
		}};
		B.setAliases(aliasesB);

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
		}};

		interactionToCompare.setConfidenceValues(confidences);

		// MITAB 2.6
		interactionToCompare.setComplexExpansion(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:1060", "spoke expansion"))));

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

        A.setXrefs(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("uniprotkb", "D3DRX9", "secondary-ac"))));
        B.setXrefs(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("refseq", "NP_001447.2"))));
		ArrayList<CrossReference> xrefs = new ArrayList<CrossReference>();
		xrefs.add(new CrossReferenceImpl("intact", "EBI-5627041", "see-also"));
		xrefs.add(new CrossReferenceImpl("imex", "IM-17229-3", "imex-primary"));
        interactionToCompare.setXrefs(xrefs);

		List<Annotation> annotationsA = new ArrayList<Annotation>() {{
			add(new AnnotationImpl("comment", "sequence not available in uniprotKb"));
		}};
		A.setAnnotations(annotationsA);

		List<Annotation> annotationsB = new ArrayList<Annotation>() {{
			add(new AnnotationImpl("anti-bacterial"));
		}};
		B.setAnnotations(annotationsB);

		List<Annotation> annotations = new ArrayList<Annotation>() {{
			add(new AnnotationImpl("curation depth", "imex curation"));
		}};
		interactionToCompare.setAnnotations(annotations);

		Organism hostOrganism = new OrganismImpl();
		hostOrganism.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("taxid", "9606"))));

		interactionToCompare.setHostOrganism(hostOrganism);

		List<Parameter> parameters = new ArrayList<Parameter>() {{
			add(new ParameterImpl("kd", "1.36x10^-6", "molar"));
		}};
		interactionToCompare.setParameters(parameters);

		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date date = formatter.parse("2008/03/30");

		interactionToCompare.setCreationDate(new ArrayList<Date>(
				Collections.singletonList(date)));
		interactionToCompare.setUpdateDate(new ArrayList<Date>(
				Collections.singletonList(date)));

		A.setChecksums(new ArrayList<Checksum>(
				Collections.singletonList(new ChecksumImpl("crc64", "6C1A07041DF50142"))));
		B.setChecksums(new ArrayList<Checksum>(
				Collections.singletonList(new ChecksumImpl("crc64", "2F6FEFCDF2C80457"))));
		interactionToCompare.setChecksums(new ArrayList<Checksum>(
				Collections.singletonList(new ChecksumImpl("intact-crc", "08C4486B755C70C0"))));

		interactionToCompare.setNegativeInteraction(false);

		// MITAB 2.7
		ArrayList<String> rangesA = new ArrayList<String>();
		rangesA.add("2171-2647");
		ArrayList<String> rangesB = new ArrayList<String>();
		rangesB.add("757-800");
		A.setFeatures(new ArrayList<Feature>(
				Collections.singletonList(new FeatureImpl("necessary binding region", rangesA))));
		B.setFeatures(new ArrayList<Feature>(
				Collections.singletonList(new FeatureImpl("necessary binding region", rangesB))));

		A.setStoichiometry(new ArrayList<Integer>(
				Collections.singletonList(new Integer("2"))));
		B.setStoichiometry(new ArrayList<Integer>(
				Collections.singletonList(new Integer("2"))));

		A.setParticipantIdentificationMethods(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0363", "inferred by author"))));
		B.setParticipantIdentificationMethods(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0363", "inferred by author"))));

		// MITAB 2.8
		A.setBiologicalEffects(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("go", "GO:0016301", "kinase activity"))));
		B.setBiologicalEffects(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("go", "GO:0016301", "kinase activity"))));

		interactionToCompare.setCausalRegulatoryMechanism(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:2249", "post transcriptional regulation"))));
		interactionToCompare.setCausalStatement(new ArrayList<CrossReference>(
						Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:2240", "down regulates"))));

		Assert.assertEquals(A, A);
		Assert.assertEquals(B, B);
		Assert.assertEquals(A, interactionBuilt.getInteractorA());
		Assert.assertEquals(B, interactionBuilt.getInteractorB());
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
				"lpr:2|hpr:2|np:1|"
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

		String[] line28 = {
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
				"lpr:2|hpr:2|np:1|",
				"psi-mi:\"MI:1060\"(spoke expansion)",
				"psi-mi:\"MI:0499\"(unspecified role)",
				"psi-mi:\"MI:0499\"(unspecified role)",
				"psi-mi:\"MI:0498\"(\"prey\")",
				"psi-mi:\"MI:0496\"(\"bait\")",
				"psi-mi:\"MI:0326\"(protein)",
				"psi-mi:\"MI:0326\"(protein)",
				"uniprotkb:D3DRX9(secondary-ac)",
				"refseq:NP_001447.2",
				"intact:EBI-5627041(see-also)|imex:IM-17229-3(imex-primary)",
				"-",
				"-",
				"curation depth:imex curation",
				"taxid:9606",
				"kd:1.36x10^-6(molar)",
				"2008/03/30",
				"2008/03/30",
				"crc64:6C1A07041DF50142",
				"crc64:2F6FEFCDF2C80457",
				"intact-crc:08C4486B755C70C0",
				"false",
				"necessary binding region:2171-2647",
				"necessary binding region:757-800",
				"-",
				"-",
				"psi-mi:\"MI:0363\"(inferred by author)",
				"psi-mi:\"MI:0363\"(inferred by author)",
				"go:\"GO:0016301\"(kinase activity)",
				"go:\"GO:0016301\"(kinase activity)",
				"psi-mi:\"MI:2249\"(post transcriptional regulation)",
				"psi-mi:\"MI:2240\"(down regulates)"};

		int numColumns2_5 = PsimiTabVersion.v2_5.getNumberOfColumns();
		int numColumns2_6 = PsimiTabVersion.v2_6.getNumberOfColumns();
		int numColumns2_7 = PsimiTabVersion.v2_7.getNumberOfColumns();
		int numColumns2_8 = PsimiTabVersion.v2_8.getNumberOfColumns();

		String[] result1 = MitabParserUtils.extendFormat(line28, numColumns2_5);
		Assert.assertEquals(numColumns2_8, result1.length);

		String[] result2 = MitabParserUtils.extendFormat(line27, numColumns2_5);
		Assert.assertEquals(numColumns2_7, result2.length);

		String[] result3 = MitabParserUtils.extendFormat(line26, numColumns2_5);
		Assert.assertEquals(numColumns2_6, result3.length);

		String[] result4 = MitabParserUtils.extendFormat(line25, numColumns2_5);
		Assert.assertEquals(numColumns2_5, result4.length);

		String[] result5 = MitabParserUtils.extendFormat(line28, numColumns2_6);
		Assert.assertEquals(numColumns2_8, result5.length);

		String[] result6 = MitabParserUtils.extendFormat(line27, numColumns2_6);
		Assert.assertEquals(numColumns2_7, result6.length);

		String[] result7 = MitabParserUtils.extendFormat(line26, numColumns2_6);
		Assert.assertEquals(numColumns2_6, result7.length);

		String[] result8 = MitabParserUtils.extendFormat(line25, numColumns2_6);
		Assert.assertEquals(numColumns2_6, result8.length);

		String[] result9 = MitabParserUtils.extendFormat(line28, numColumns2_7);
		Assert.assertEquals(numColumns2_8, result9.length);

		String[] result10 = MitabParserUtils.extendFormat(line27, numColumns2_7);
		Assert.assertEquals(numColumns2_7, result10.length);

		String[] result11 = MitabParserUtils.extendFormat(line26, numColumns2_7);
		Assert.assertEquals(numColumns2_7, result11.length);

		String[] result12 = MitabParserUtils.extendFormat(line25, numColumns2_7);
		Assert.assertEquals(numColumns2_7, result12.length);

		String[] result13 = MitabParserUtils.extendFormat(line28, numColumns2_8);
		Assert.assertEquals(numColumns2_8, result13.length);

		String[] result14 = MitabParserUtils.extendFormat(line27, numColumns2_8);
		Assert.assertEquals(numColumns2_8, result14.length);

		String[] result15 = MitabParserUtils.extendFormat(line26, numColumns2_8);
		Assert.assertEquals(numColumns2_8, result15.length);

		String[] result16 = MitabParserUtils.extendFormat(line25, numColumns2_8);
		Assert.assertEquals(numColumns2_8, result16.length);
	}

	@Test
	public void testIntraInterMoleculeInteraction() throws Exception {

		String[] interactorANull ={
				"-",
				"innatedb:IDBG-4279",
				"-",
				"ensembl:ENSG00000141655",
				"-",
				"uniprotkb:Q9Y6Q6",
				"psi-mi:\"MI:0007\"(\"anti tag coimmunoprecipitation\")",
				"Arron et al. (2001)",
				"pubmed:11406619",
				"-",
				"taxid:9606(Human)",
				"psi-mi:\"MI:0915\"(physical association)",
				"psi-mi:\"MI:0974\"(innatedb)",
				"innatedb:IDB-113260",
				"lpr:2|hpr:2|np:1|",
			     "-",
				"-",
				"psi-mi:\"MI:0499\"(unspecified role)",
				"-",
				"psi-mi:\"MI:0496\"(\"bait\")",
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
				"psi-mi:\"MI:0363\"(inferred by author)",
				"-",
				"go:\"GO:0016301\"(kinase activity)",
				"psi-mi:\"MI:2249\"(post transcriptional regulation)",
				"psi-mi:\"MI:2240\"(down regulates)"};

		String[] interactorBNull = {
					"innatedb:IDBG-40102",
					"-",
					"ensembl:ENSG00000175104",
					"-",
					"uniprotkb:Q9Y4K3",
					"-",
					"psi-mi:\"MI:0007\"(\"anti tag coimmunoprecipitation\")",
					"Arron et al. (2001)",
					"pubmed:11406619",
					"taxid:9606(Human)",
					"-",
					"psi-mi:\"MI:0915\"(physical association)",
					"psi-mi:\"MI:0974\"(innatedb)",
					"innatedb:IDB-113260",
					"lpr:2|hpr:2|np:1|",
					"-",
					"psi-mi:\"MI:0499\"(unspecified role)",
					"-",
					"psi-mi:\"MI:0498\"(\"prey\")",
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
					"-",
					"go:\"GO:0016301\"(kinase activity)",
					"-",
					"psi-mi:\"MI:2249\"(post transcriptional regulation)",
					"psi-mi:\"MI:2240\"(down regulates)"};

		BinaryInteraction interactionABuilt = MitabParserUtils.buildBinaryInteraction(interactorBNull);
		BinaryInteraction interactionBBuilt = MitabParserUtils.buildBinaryInteraction(interactorANull);

		Interactor ANull = null;
		Interactor BOK = interactionBBuilt.getInteractorB();

		BinaryInteraction interactionANullToCompare = new BinaryInteractionImpl(ANull, BOK);


		interactionANullToCompare.setDetectionMethods(new ArrayList<CrossReference>(
				Collections.singletonList(
						new CrossReferenceImpl("psi-mi", "MI:0007", "anti tag coimmunoprecipitation"))));
		interactionANullToCompare.setAuthors(new ArrayList<Author>(
				Collections.singletonList(new AuthorImpl("Arron et al. (2001)"))));
		interactionANullToCompare.setPublications(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("pubmed", "11406619"))));
		interactionANullToCompare.setInteractionTypes(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0915", "physical association"))));
		interactionANullToCompare.setSourceDatabases(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0974", "innatedb"))));
		interactionANullToCompare.setInteractionAcs(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("innatedb", "IDB-113260"))));
		List<Confidence> confidences = new ArrayList<Confidence>() {{
			add(new ConfidenceImpl("lpr", "2"));
            add(new ConfidenceImpl("hpr", "2"));
            add(new ConfidenceImpl("np", "1"));
		}};

		interactionANullToCompare.setConfidenceValues(confidences);

		Organism hostOrganism = new OrganismImpl();
		hostOrganism.setIdentifiers(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("taxid", "9606"))));

		interactionANullToCompare.setHostOrganism(hostOrganism);

		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date date = formatter.parse("2008/03/30");

		interactionANullToCompare.setCreationDate(new ArrayList<Date>(
				Collections.singletonList(date)));
		interactionANullToCompare.setUpdateDate(new ArrayList<Date>(
				Collections.singletonList(date)));

		interactionANullToCompare.setNegativeInteraction(false);

		interactionANullToCompare.setCausalRegulatoryMechanism(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi","MI:2249", "post transcriptional regulation"))));
		interactionANullToCompare.setCausalStatement(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi","MI:2240", "down regulates"))));

		Interactor AOK = interactionABuilt.getInteractorA();
		Interactor BNull = null;

		BinaryInteraction interactionBNullToCompare = new BinaryInteractionImpl(AOK, BNull);

		interactionBNullToCompare.setDetectionMethods(new ArrayList<CrossReference>(
				Collections.singletonList(
						new CrossReferenceImpl("psi-mi", "MI:0007", "anti tag coimmunoprecipitation"))));
		interactionBNullToCompare.setAuthors(new ArrayList<Author>(
				Collections.singletonList(new AuthorImpl("Arron et al. (2001)"))));
		interactionBNullToCompare.setPublications(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("pubmed", "11406619"))));
		interactionBNullToCompare.setInteractionTypes(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0915", "physical association"))));
		interactionBNullToCompare.setSourceDatabases(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi", "MI:0974", "innatedb"))));
		interactionBNullToCompare.setInteractionAcs(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("innatedb", "IDB-113260"))));

		interactionBNullToCompare.setConfidenceValues(confidences);

		interactionBNullToCompare.setHostOrganism(hostOrganism);


		interactionBNullToCompare.setCreationDate(new ArrayList<Date>(
				Collections.singletonList(date)));
		interactionBNullToCompare.setUpdateDate(new ArrayList<Date>(
				Collections.singletonList(date)));

		interactionBNullToCompare.setNegativeInteraction(false);

		interactionBNullToCompare.setCausalRegulatoryMechanism(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi","MI:2249", "post transcriptional regulation"))));
		interactionBNullToCompare.setCausalStatement(new ArrayList<CrossReference>(
				Collections.singletonList(new CrossReferenceImpl("psi-mi","MI:2240", "down regulates"))));

		Interactor AEmpty = new Interactor();

		BinaryInteraction interactionAEmptyToCompare = new BinaryInteractionImpl(AEmpty, BOK);

		Interactor BEmpty = new Interactor();

		BinaryInteraction interactionBEmptyToCompare = new BinaryInteractionImpl(AOK, BEmpty);

		Assert.assertNotSame(interactionANullToCompare, interactionAEmptyToCompare);
		Assert.assertNotSame(interactionBNullToCompare, interactionBEmptyToCompare);

		//It is not the same when you have an empty interaction than a null interactor
		Assert.assertNotSame(interactionABuilt,interactionAEmptyToCompare);
		Assert.assertEquals(interactionABuilt,interactionBNullToCompare);

		//It is not the same when you have an empty interaction than a null interactor
		Assert.assertNotSame(interactionBBuilt,interactionBEmptyToCompare);
		Assert.assertEquals(interactionBBuilt,interactionANullToCompare);
	}
}
