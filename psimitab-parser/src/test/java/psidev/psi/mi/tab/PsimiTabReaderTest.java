package psidev.psi.mi.tab;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.ConfidenceImpl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * PsimiTabReader Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>01/09/2007</pre>
 */
public class PsimiTabReaderTest {

	@Test
	public void readStringNoHeader() throws PsimiTabException, IOException {

		PsimiTabReader reader = new PsimiTabReader();
		Collection<BinaryInteraction> interactions = reader.read(TestHelper.TAB_11585365);

		assertEquals(4, interactions.size());
	}

	@Test
	public void readStringWithHeader() throws PsimiTabException, IOException {

		PsimiTabReader reader = new PsimiTabReader();
		Collection<BinaryInteraction> interactions = reader.read(TestHelper.TXT_11585365);

		assertEquals(4, interactions.size());
	}

	@Test
	public void iterateStringWithHeader() throws Exception {

		PsimiTabReader reader = new PsimiTabReader();
		Iterator<BinaryInteraction> iterator = reader.iterate(TestHelper.TXT_11585365);

		int count = 0;

		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}

		assertEquals(4, count);

		PsimiTabIterator iter = (PsimiTabIterator) iterator;
		assertEquals(4, iter.getInteractionsProcessedCount());
	}

	@Test
	public void readMitab25String() throws PsimiTabException, IOException {

		PsimiTabReader mitabReader = new PsimiTabReader();
		Collection<BinaryInteraction> interactions = mitabReader.read(TestHelper.MITAB25_2LINES_HEADER);

		int count = 0;
		for (BinaryInteraction interaction : interactions) {
			assertEquals("P23367", interaction.getInteractorA().getIdentifiers().iterator().next().getIdentifier());
			assertEquals("P06722", interaction.getInteractorB().getIdentifiers().iterator().next().getIdentifier());
			count++;
		}

		assertEquals(2, count);
		assertEquals(2, interactions.size());
	}

	@Test
	public void iterateMitab25String() throws IOException {

		PsimiTabReader mitabReader = new PsimiTabReader();
		Iterator<BinaryInteraction> iterator = mitabReader.iterate(TestHelper.MITAB25_2LINES_HEADER);
		PsimiTabIterator mitabIterator = (PsimiTabIterator) iterator;

		int count = 0;
		while (mitabIterator.hasNext()) {
			BinaryInteraction<?> interaction = mitabIterator.next();
			assertEquals("P23367", interaction.getInteractorA().getIdentifiers().iterator().next().getIdentifier());
			assertEquals("P06722", interaction.getInteractorB().getIdentifiers().iterator().next().getIdentifier());
			count++;
		}

		assertEquals(2, count);
		assertEquals(2,mitabIterator.getInteractionsProcessedCount());
	}

	@Test
	public void readMitab25File() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		File file = TestHelper.getFileByResources("/mitab-samples/14726512.txt", PsimiTabReader.class);
		Collection<BinaryInteraction> interactions = mitabReader.read(file);

		assertEquals(8, interactions.size());

		BinaryInteraction<?> firstBinaryInteraction = interactions.iterator().next();

		// test idB
		assertEquals("uniprotkb", firstBinaryInteraction.getInteractorB().getIdentifiers().get(0).getDatabase());
		assertEquals("Q9Y584", firstBinaryInteraction.getInteractorB().getIdentifiers().get(0).getIdentifier());

		// test AltIdA
		assertEquals("uniprotkb", firstBinaryInteraction.getInteractorA().getAlternativeIdentifiers().get(0).getDatabase());
		assertEquals("TIMM9", firstBinaryInteraction.getInteractorA().getAlternativeIdentifiers().get(0).getIdentifier());

		// test pubauth
		assertTrue(firstBinaryInteraction.getAuthors().isEmpty());

		// test pubid
		assertEquals("pubmed", firstBinaryInteraction.getPublications().get(0).getDatabase());
		assertEquals("14726512", firstBinaryInteraction.getPublications().get(0).getIdentifier());

		// test interaction type
		assertEquals("MI", firstBinaryInteraction.getInteractionTypes().get(0).getDatabase());
		assertEquals("0218", firstBinaryInteraction.getInteractionTypes().get(0).getIdentifier());
		assertEquals("physical interaction", firstBinaryInteraction.getInteractionTypes().get(0).getText());

		// test confidence score
		assertTrue(firstBinaryInteraction.getConfidenceValues().isEmpty());
	}

	@Test
	public void iterateMitab25File() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		File file = TestHelper.getFileByResources("/mitab-samples/14726512.txt", PsimiTabReader.class);

		Iterator<BinaryInteraction> iterator = mitabReader.iterate(file);
		PsimiTabIterator mitabIterator = (PsimiTabIterator) iterator;

		while (mitabIterator.hasNext()) {
			BinaryInteraction<?> interaction = mitabIterator.next();
			assertTrue(interaction.getConfidenceValues().isEmpty());
		}

		assertEquals(8, mitabIterator.getInteractionsProcessedCount());
	}

	@Test
	public void readMitab27String() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		Collection<BinaryInteraction> interactions = mitabReader.read(TestHelper.MITAB27_LINE_HEADER);

		assertEquals(1,interactions.size());

		BinaryInteraction<?> firstBinaryInteraction = interactions.iterator().next();

		// test biological role A
		Assert.assertEquals("psi-mi", firstBinaryInteraction.getInteractorA().getBiologicalRoles().get(0).getDatabase());
		Assert.assertEquals("MI:0499", firstBinaryInteraction.getInteractorA().getBiologicalRoles().get(0).getIdentifier());
		Assert.assertEquals("unspecified role", firstBinaryInteraction.getInteractorA().getBiologicalRoles().get(0).getText());

		// test interactor type B
		Assert.assertEquals("psi-mi", firstBinaryInteraction.getInteractorB().getInteractorTypes().get(0).getDatabase());
		Assert.assertEquals("MI:0326", firstBinaryInteraction.getInteractorB().getInteractorTypes().get(0).getIdentifier());
		Assert.assertEquals("protein", firstBinaryInteraction.getInteractorB().getInteractorTypes().get(0).getText());

		// test xref for interaction
		Assert.assertEquals("go", firstBinaryInteraction.getXrefs().get(0).getDatabase());
		Assert.assertEquals("GO:xxxxx", firstBinaryInteraction.getXrefs().get(0).getIdentifier());

		// test parameters of the interaction
		Assert.assertEquals("kd", firstBinaryInteraction.getParameters().get(0).getType());
		Assert.assertEquals("2.0", firstBinaryInteraction.getParameters().get(0).getValue());

		// test update date
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Assert.assertEquals("2010/03/30", simpleDateFormat.format(firstBinaryInteraction.getUpdateDate().get(0)));

		// test checksumB
		Assert.assertEquals("seguid", firstBinaryInteraction.getInteractorB().getChecksums().get(0).getMethodName());
		Assert.assertEquals("checksumB", firstBinaryInteraction.getInteractorB().getChecksums().get(0).getChecksum());

		// test negative
		Assert.assertFalse(firstBinaryInteraction.isNegativeInteraction());

		// test features
		Assert.assertEquals("tag", firstBinaryInteraction.getInteractorA().getFeatures().get(0).getFeatureType());
		Assert.assertEquals("?-?", firstBinaryInteraction.getInteractorA().getFeatures().get(0).getRanges().get(0));
		Assert.assertTrue(firstBinaryInteraction.getInteractorB().getFeatures().isEmpty());

		// test stoichiometries
		Assert.assertTrue(firstBinaryInteraction.getInteractorA().getStoichiometry().isEmpty());
		Assert.assertTrue(firstBinaryInteraction.getInteractorB().getStoichiometry().isEmpty());

		// test participant identification methods
		Assert.assertEquals("psi-mi", firstBinaryInteraction.getInteractorA().getParticipantIdentificationMethods().get(0).getDatabase());
		Assert.assertEquals("MI:0102", firstBinaryInteraction.getInteractorA().getParticipantIdentificationMethods().get(0).getIdentifier());
		Assert.assertEquals("sequence tag identification", firstBinaryInteraction.getInteractorA().getParticipantIdentificationMethods().get(0).getText());
		Assert.assertEquals("psi-mi", firstBinaryInteraction.getInteractorB().getParticipantIdentificationMethods().get(0).getDatabase());
		Assert.assertEquals("MI:0102", firstBinaryInteraction.getInteractorB().getParticipantIdentificationMethods().get(0).getIdentifier());
		Assert.assertEquals("sequence tag identification", firstBinaryInteraction.getInteractorB().getParticipantIdentificationMethods().get(0).getText());
	}

	@Test
	public void iterateMitab27String() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		Iterator<BinaryInteraction> iterator = mitabReader.iterate(TestHelper.MITAB27_LINE_HEADER);
		PsimiTabIterator mitabIterator = (PsimiTabIterator) iterator;

		while (mitabIterator.hasNext()) {
			BinaryInteraction<?> firstBinaryInteraction = mitabIterator.next();

			// test interactor type B
			Assert.assertEquals("psi-mi", firstBinaryInteraction.getInteractorB().getInteractorTypes().get(0).getDatabase());
			Assert.assertEquals("MI:0326", firstBinaryInteraction.getInteractorB().getInteractorTypes().get(0).getIdentifier());
			Assert.assertEquals("protein", firstBinaryInteraction.getInteractorB().getInteractorTypes().get(0).getText());

			// test checksumB
			Assert.assertEquals("seguid", firstBinaryInteraction.getInteractorB().getChecksums().get(0).getMethodName());
			Assert.assertEquals("checksumB", firstBinaryInteraction.getInteractorB().getChecksums().get(0).getChecksum());

			// test negative
			Assert.assertFalse(firstBinaryInteraction.isNegativeInteraction());

			// test features
			Assert.assertEquals("tag", firstBinaryInteraction.getInteractorA().getFeatures().get(0).getFeatureType());
			Assert.assertEquals("?-?", firstBinaryInteraction.getInteractorA().getFeatures().get(0).getRanges().get(0));
			Assert.assertTrue(firstBinaryInteraction.getInteractorB().getFeatures().isEmpty());
		}

		assertEquals(1, mitabIterator.getInteractionsProcessedCount());
	}

	@Test
	public void readMitab27File() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		File file = TestHelper.getFileByResources("/mitab-samples/mitab_27_example.txt", PsimiTabReader.class);
		Collection<BinaryInteraction> interactions = mitabReader.read(file);

		assertEquals(14, interactions.size());

		BinaryInteraction<?> firstBinaryInteraction = interactions.iterator().next();

		// test IdA
		assertEquals("innatedb", firstBinaryInteraction.getInteractorA().getIdentifiers().get(0).getDatabase());
		assertEquals("IDBG-82738", firstBinaryInteraction.getInteractorA().getIdentifiers().get(0).getIdentifier());

		// test pubauth
		assertEquals("Shimazu et al. (1999)", firstBinaryInteraction.getAuthors().get(0).getName());

		// test confidence scores
		assertEquals("lpr", firstBinaryInteraction.getConfidenceValues().get(0).getType());
		assertEquals("2", firstBinaryInteraction.getConfidenceValues().get(0).getValue());
		assertEquals("hpr", firstBinaryInteraction.getConfidenceValues().get(1).getType());
		assertEquals("2", firstBinaryInteraction.getConfidenceValues().get(1).getValue());
		assertEquals("np", firstBinaryInteraction.getConfidenceValues().get(2).getType());
		assertEquals("1", firstBinaryInteraction.getConfidenceValues().get(2).getValue());

		// test parameters of the interaction
		assertTrue(firstBinaryInteraction.getParameters().isEmpty());

		// test negative
		Assert.assertFalse(firstBinaryInteraction.isNegativeInteraction());

		// test participant identification methods
		assertEquals("psi-mi", firstBinaryInteraction.getInteractorA().getParticipantIdentificationMethods().get(0).getDatabase());
		assertEquals("MI:0363", firstBinaryInteraction.getInteractorA().getParticipantIdentificationMethods().get(0).getIdentifier());
		assertEquals("inferred by author", firstBinaryInteraction.getInteractorA().getParticipantIdentificationMethods().get(0).getText());
		assertEquals("psi-mi", firstBinaryInteraction.getInteractorB().getParticipantIdentificationMethods().get(0).getDatabase());
		assertEquals("MI:0363", firstBinaryInteraction.getInteractorB().getParticipantIdentificationMethods().get(0).getIdentifier());
		assertEquals("inferred by author", firstBinaryInteraction.getInteractorB().getParticipantIdentificationMethods().get(0).getText());
	}

	@Test
	public void iterateMitab27File() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		File file = TestHelper.getFileByResources("/mitab-samples/mitab_27_example.txt", PsimiTabReader.class);

		Iterator<BinaryInteraction> iterator = mitabReader.iterate(file);
		PsimiTabIterator mitabIterator = (PsimiTabIterator) iterator;

		while (mitabIterator.hasNext()) {
			BinaryInteraction<?> interaction = mitabIterator.next();
			assertEquals("psi-mi", interaction.getInteractorA().getParticipantIdentificationMethods().get(0).getDatabase());
			assertEquals("MI:0363", interaction.getInteractorA().getParticipantIdentificationMethods().get(0).getIdentifier());
			assertEquals("inferred by author", interaction.getInteractorA().getParticipantIdentificationMethods().get(0).getText());
		}

		assertEquals(14, mitabIterator.getInteractionsProcessedCount());
	}

	@Test
	public void readMitab28String() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		Collection<BinaryInteraction> interactions = mitabReader.read(TestHelper.MITAB28_LINE_HEADER);

		assertEquals(1,interactions.size());

		BinaryInteraction<?> firstBinaryInteraction = interactions.iterator().next();

		// test biological effects
		assertTrue(firstBinaryInteraction.getInteractorA().getBiologicalEffects().isEmpty());
		assertTrue(firstBinaryInteraction.getInteractorB().getBiologicalEffects().isEmpty());

		// test causal regulatory mechanism
		assertEquals("psi-mi", firstBinaryInteraction.getCausalRegulatoryMechanism().get(0).getDatabase());
		assertEquals("MI:2247", firstBinaryInteraction.getCausalRegulatoryMechanism().get(0).getIdentifier());
		assertEquals("transcriptional regulation", firstBinaryInteraction.getCausalRegulatoryMechanism().get(0).getText());

		// test causal statement
		assertEquals("psi-mi", firstBinaryInteraction.getCausalStatement().get(0).getDatabase());
		assertEquals("MI:2236", firstBinaryInteraction.getCausalStatement().get(0).getIdentifier());
		assertEquals("up-regulates activity", firstBinaryInteraction.getCausalStatement().get(0).getText());
	}

	@Test
	public void iterateMitab28String() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		Iterator<BinaryInteraction> iterator = mitabReader.iterate(TestHelper.MITAB28_LINE_HEADER);
		PsimiTabIterator mitabIterator = (PsimiTabIterator) iterator;

		while (mitabIterator.hasNext()) {
			BinaryInteraction<?> interaction = mitabIterator.next();

			// test biological effects
			assertTrue(interaction.getInteractorA().getBiologicalEffects().isEmpty());
			assertTrue(interaction.getInteractorB().getBiologicalEffects().isEmpty());

			// test causal regulatory mechanism
			assertEquals("psi-mi", interaction.getCausalRegulatoryMechanism().get(0).getDatabase());
			assertEquals("MI:2247", interaction.getCausalRegulatoryMechanism().get(0).getIdentifier());
			assertEquals("transcriptional regulation", interaction.getCausalRegulatoryMechanism().get(0).getText());

			// test causal statement
			assertEquals("psi-mi", interaction.getCausalStatement().get(0).getDatabase());
			assertEquals("MI:2236", interaction.getCausalStatement().get(0).getIdentifier());
			assertEquals("up-regulates activity", interaction.getCausalStatement().get(0).getText());
		}

		assertEquals(1, mitabIterator.getInteractionsProcessedCount());
	}

	@Test
	public void readMitab28File() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		File file = TestHelper.getFileByResources("/mitab-samples/sampleFileMitab28.txt", PsimiTabReader.class);
		Collection<BinaryInteraction> interactions = mitabReader.read(file);

		assertEquals(4, interactions.size());

		BinaryInteraction<?> firstBinaryInteraction = interactions.iterator().next();

		// test biological effects
		assertEquals("go", firstBinaryInteraction.getInteractorA().getBiologicalEffects().get(0).getDatabase());
		assertEquals("GO:0016301", firstBinaryInteraction.getInteractorA().getBiologicalEffects().get(0).getIdentifier());
		assertEquals("kinase activity", firstBinaryInteraction.getInteractorA().getBiologicalEffects().get(0).getText());
		assertEquals("go", firstBinaryInteraction.getInteractorB().getBiologicalEffects().get(0).getDatabase());
		assertEquals("GO:0016301", firstBinaryInteraction.getInteractorB().getBiologicalEffects().get(0).getIdentifier());
		assertEquals("kinase activity", firstBinaryInteraction.getInteractorB().getBiologicalEffects().get(0).getText());

		// test causal regulatory mechanism
		assertEquals("psi-mi", firstBinaryInteraction.getCausalRegulatoryMechanism().get(0).getDatabase());
		assertEquals("MI:2249", firstBinaryInteraction.getCausalRegulatoryMechanism().get(0).getIdentifier());
		assertEquals("post transcriptional regulation", firstBinaryInteraction.getCausalRegulatoryMechanism().get(0).getText());

		// test causal statement
		assertEquals("psi-mi", firstBinaryInteraction.getCausalStatement().get(0).getDatabase());
		assertEquals("MI:2235", firstBinaryInteraction.getCausalStatement().get(0).getIdentifier());
		assertEquals("up regulates", firstBinaryInteraction.getCausalStatement().get(0).getText());
	}

	@Test
	public void iterateMitab28File() throws Exception {

		PsimiTabReader mitabReader = new PsimiTabReader();
		File file = TestHelper.getFileByResources("/mitab-samples/sampleFileMitab28.txt", PsimiTabReader.class);

		Iterator<BinaryInteraction> iterator = mitabReader.iterate(file);
		PsimiTabIterator mitabIterator = (PsimiTabIterator) iterator;

		while (mitabIterator.hasNext()) {
			BinaryInteraction<?> interaction = mitabIterator.next();

			// test causal regulatory mechanism
			assertEquals("psi-mi", interaction.getCausalRegulatoryMechanism().get(0).getDatabase());
			assertEquals("MI:2249", interaction.getCausalRegulatoryMechanism().get(0).getIdentifier());
			assertEquals("post transcriptional regulation", interaction.getCausalRegulatoryMechanism().get(0).getText());
		}

		assertEquals(4, mitabIterator.getInteractionsProcessedCount());
	}

	@Test
	public void emptyInteractionAc() throws PsimiTabException {
		String line = "entrez gene/locuslink:3069\tentrez gene/locuslink:11260\tentrez " +
				"gene/locuslink:HDLBP\tentrez gene/locuslink:XPOT\tentrez " +
				"gene/locuslink:FLJ16432|entrez gene/locuslink:HBP|entrez " +
				"gene/locuslink:PRO2900|entrez gene/locuslink:VGL\tentrez " +
				"gene/locuslink:XPO3\tpsi-mi:\"MI:0401\"(biochemical)\tKruse C (2000)\t" +
				"pubmed:10657246\ttaxid:9606\ttaxid:9606\tpsi-mi:\"MI:0914\"(association)\t" +
				"psi-mi:\"MI:0463\"(GRID)\t-\t-";

		psidev.psi.mi.tab.io.PsimiTabReader mitabReader = new PsimiTabReader();
		final BinaryInteraction binaryInteraction = mitabReader.readLine(line);

		Assert.assertTrue(binaryInteraction.getInteractionAcs().isEmpty());
	}

	@Test
	public void unexpectedFreeTextInConfidences() throws PsimiTabException {
		String line = "uniprotkb:P23367\tuniprotkb:P06722\tinterpro:IPR003594|interpro:IPR002099|go:GO:0005515|intact:EBI-554913\tinterpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:GO:0005515|intact:EBI-545170\tgene name:mutL|locus name:b4170\tgene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831\tadenylate cyclase:MI:0014\t-\tpubmed:11585365\ttaxid:562\ttaxid:562\tphysical interaction:MI:0218\t-\t-\t" +
				"lpr:640|hpr:640|np:1|PSICQUIC entries are truncated here.  Seeirefindex.uio.no";
		psidev.psi.mi.tab.io.PsimiTabReader mitabReader = new PsimiTabReader();
		final BinaryInteraction binaryInteraction = mitabReader.readLine(line);

		Assert.assertEquals(4, binaryInteraction.getConfidenceValues().size());

		ConfidenceImpl confidence = (ConfidenceImpl) binaryInteraction.getConfidenceValues().get(3);
		Assert.assertEquals("not-defined", confidence.getType());
		Assert.assertEquals("PSICQUIC entries are truncated here.  Seeirefindex.uio.no", confidence.getValue());
		Assert.assertEquals("free-text", confidence.getText());
	}

	@Test
	public void readBadFormatLine() throws Exception {
		final String line = "BindingDB_monomerID:18129\tBindingDB_polymerID:50003968\t-\tDisplayName:\"(2S)-2-amino-3-(4-hydroxyphenyl)propanoic acid\"|DisplayName:L-[U-14C]Tyr|DisplayName:Tyrosine\tDisplayName:Tubulin--tyrosine ligase\t-\tBanerjee et al:2010\tpmid:20545322\t-\t-\tpsi-mi:\"MI:0915\"(physical association)\t-\thttp://www.bindingdb.org/jsp/dbsearch/PrimarySearch_ki.jsp?energyterm=kJ/mole&tag=r22&monomerid=18129&polymerid=50003968&column=ki&startPg=0&Increment=50&submit=Search\t-\t-";
		psidev.psi.mi.tab.io.PsimiTabReader mitabReader = new PsimiTabReader();
		final BinaryInteraction binaryInteraction = mitabReader.readLine(line);

		Assert.assertNotNull(binaryInteraction);
	}

	@Test
	public void readAndWriteAnnotationWithControlCharacter() throws Exception {

		final String ANNOTATION_WITH_CONTROL = "uniprotkb:P78537\tuniprotkb:Q9NUP1\tintact:EBI-348630" +
				"\tintact:EBI-465852\t" +
				"psi-mi:bl1s1_human(display_long)|uniprotkb:bl1s1_human(shortlabel)|" +
				"uniprotkb:GCN5-like protein 1(gene name synonym)|psi-mi:GCN5-like protein 1(display_short)|" +
				"uniprotkb:Protein RT14(gene name synonym)|psi-mi:Protein RT14(display_short)|" +
				"uniprotkb:BLOC1S1(gene name)|psi-mi:BLOC1S1(display_short)|uniprotkb:BLOS1(gene name synonym)|" +
				"psi-mi:BLOS1(display_short)|uniprotkb:RT14(gene name synonym)|psi-mi:RT14(display_short)" +
				"|uniprotkb:GCN5L1(gene name synonym)|psi-mi:GCN5L1(display_short)\tpsi-mi:cno_human(display_long)" +
				"|uniprotkb:cno_human(shortlabel)|uniprotkb:CNO(gene name)|psi-mi:CNO(display_short)" +
				"\tpsi-mi:\"MI:0364\"(inferred by curator)\t-\tpubmed:14681455\ttaxid:9606(human)" +
				"|taxid:9606(Homo sapiens)\ttaxid:9606(human)|taxid:9606(Homo sapiens)" +
				"\tpsi-mi:\"MI:0915\"(physical association)" +
				"\tpsi-mi:\"MI:0469\"(IntAct)\t" +
				"intact:EBI-6162293\t-" +
				"\tpsi-mi:\"MI:1060\"(spoke expansion)" +
				"\tpsi-mi:\"MI:0499\"(unspecified role)\tpsi-mi:\"MI:0499" +
				"\"(unspecified role)\tpsi-mi:\"MI:0497\"(neutral component)" +
				"\tpsi-mi:\"MI:0497\"(neutral component)" +
				"\tpsi-mi:\"MI:0326\"(protein)" +
				"\tpsi-mi:\"MI:0326\"(protein)" +
				"\tinterpro:IPR009395(GCN5-like 1)|uniprotkb:Q6NZ45(secondary-ac)|uniprotkb:A1L4Q9(secondary-ac)|go:" +
				"\"GO:0006892\"(post-Golgi vesicle-mediated transport)|go:" +
				"\"GO:0060155\"(platelet dense granule organization)|go:\"GO:0032438\"(melanosome organization)|" +
				"go:\"GO:0016044\"(cellular membrane organization)|go:\"GO:0005765\"(lysosomal membrane)|" +
				"go:\"GO:0031083\"(BLOC-1 complex)|go:\"GO:0018394\"(peptidyl-lysine acetylation)|" +
				"go:\"GO:0009060\"(aerobic respiration)|go:\"GO:0005759\"(mitochondrial matrix)|" +
				"go:\"GO:0005758\"(mitochondrial intermembrane space)|ensembl:ENSG00000135441|" +
				"refseq:NP_001478.2|reactome:REACT_11123|ipi:IPI01015745|ipi:IPI00020319" +
				"\tinterpro:IPR024857|uniprotkb:Q96G84(secondary-ac)|uniprotkb:Q6NVY6(secondary-ac)|" +
				"go:\"GO:0032438\"(melanosome organization)|go:\"GO:0006892\"(post-Golgi vesicle-mediated transport)|" +
				"go:\"GO:0016044\"(cellular membrane organization)|go:\"GO:0005829\"(cytosol)|ensembl:ENSG00000186222|" +
				"refseq:NP_060836.1|reactome:REACT_11123|ipi:IPI00020002\tintact:EBI-6160094(exp-evidence)|" +
				"pubmed:22203680(see-also)|go:\"GO:0031083\"(BLOC-1 complex)|go:\"GO:0032438\"(melanosome organization)|" +
				"reactome:REACT_19646.1(identity)\t-\t-\tdataset:Complexes - Interaction dataset based on curated protein complexes.|" +
				"complex-properties:\"Consists of eight globular domains of approximately equal diameter (30 A) connected in a linear chain, approx." +
				" 300A long and 30A in diameter. The individual domains are flexibly connected such that" +
				" the linear chain undergoes bending by as much as 45 degrees." +
				" The complex is elongated, with the heterotrimeric subcomplexes forming" +
				" separate arms. Two^M stable subcomplexes have been identified, pallidin-Cappuccino-BLOS1" +
				" and dysbindin-Snapin-BLOS2, but no distinct role has been dientifed for these sub-complexes." +
				"\"|complex-synonym:Biogenesis of Lysosome-related Organelles Complex-1; BLOC-1;|curated-complex:" +
				"Critical for melanosome biogenesis and also implicated in neurological function and disease. Implicated" +
				" in the formation and/or maturation of tubular vesicular intermediates between endosomes and lysosome-related organelles. " +
				"Possible role in intracellular protein trafficking.\ttaxid:9606(human)|taxid:9606(Homo sapiens)\t-\t2006/01/01\t2012/06/28" +
				"\trogid:lntB3izez0HoFLPpiAtPBayt+G49606" +
				"\trogid:OGP3Nz9cdPa/BmzSDjUtdbg5XpE9606" +
				"\tintact-crc:4185B91CD6A27DC2|rigid:g2O5LVCenKS94OEpfrEyAh3EM5I\tfalse\t-\t-\t1\t1\t-\t-";

		psidev.psi.mi.tab.io.PsimiTabReader mitabReader = new PsimiTabReader();
		BinaryInteraction interaction = mitabReader.readLine(ANNOTATION_WITH_CONTROL);

		Assert.assertNotNull(interaction);
	}
}