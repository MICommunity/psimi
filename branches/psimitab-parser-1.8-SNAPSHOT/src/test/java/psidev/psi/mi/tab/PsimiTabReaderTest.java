package psidev.psi.mi.tab;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.ConfidenceImpl;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * PsimiTabReader Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>01/09/2007</pre>
 */
public class PsimiTabReaderTest {

	@Test
	public void readFileNoHeader() throws PsimiTabException, IOException {

		psidev.psi.mi.tab.io.PsimiTabReader reader = new PsimiTabReader();
		Collection<BinaryInteraction> interactions = reader.read(TestHelper.TAB_11585365);

		assertEquals(4, interactions.size());
	}

	@Test
	public void readFileWithHeader() throws PsimiTabException, IOException {

		psidev.psi.mi.tab.io.PsimiTabReader reader = new PsimiTabReader();
		Collection<BinaryInteraction> interactions = reader.read(TestHelper.TXT_11585365);

		assertEquals(4, interactions.size());
	}

	@Test
	public void iterate_withHeader() throws Exception {
		psidev.psi.mi.tab.io.PsimiTabReader reader = new PsimiTabReader();
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
	public void read_file() throws PsimiTabException, IOException {
		psidev.psi.mi.tab.io.PsimiTabReader mitabReader = new PsimiTabReader();
		Collection<BinaryInteraction> interactions = mitabReader.read(TestHelper.TXT_11585365);
		int count = 0;
		for (BinaryInteraction interaction : interactions) {
			count++;
		}
		assertEquals(4, count);
	}

	@Test
	public void iterate_file() throws PsimiTabException, IOException {
		psidev.psi.mi.tab.io.PsimiTabReader mitabReader = new PsimiTabReader();
		Iterator<BinaryInteraction> ii = mitabReader.iterate(TestHelper.TXT_11585365);

		int count = 0;
		while (ii.hasNext()) {
			ii.next();
			count++;
		}

		assertEquals(4, count);
	}

	public static final String MITAB_2_LINE_WITH_HEADER =
			"#ID interactor A\tID interactor B\tAlt. ID interactor A\tAlt. ID interactor B\tAliases interactor A\tAliases interactor B\tinteraction detection method(s)\tpublication(s) 1st author(s) surname\tPublication ID\ttaxid interactor A\ttaxid interactor B\tInteraction types\tSource databases and identifiers\tInteraction ID\tConfidenceImpl\n" +
					"uniprotkb:P23367\tuniprotkb:P06722\tinterpro:IPR003594|interpro:IPR002099|go:GO:0005515|intact:EBI-554913\tinterpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:GO:0005515|intact:EBI-545170\tgene name:mutL|locus name:b4170\tgene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831\tadenylate cyclase:MI:0014\t-\tpubmed:11585365\ttaxid:562\ttaxid:562\tphysical interaction:MI:0218\t-\t-\t-\n" +
					"uniprotkb:P23367\tuniprotkb:P06722\t-\t-\t-\t-\t-\t-\t-\ttaxid:562\ttaxid:562\t-\t-\t-\t-\t-";

	@Test
	public void read_String() throws PsimiTabException, IOException {

		psidev.psi.mi.tab.io.PsimiTabReader mitabReader = new PsimiTabReader();
		Collection<BinaryInteraction> interactions = mitabReader.read(MITAB_2_LINE_WITH_HEADER);
		int count = 0;
		for (BinaryInteraction interaction : interactions) {
			assertEquals("P23367", interaction.getInteractorA().getIdentifiers().iterator().next().getIdentifier());
			assertEquals("P06722", interaction.getInteractorB().getIdentifiers().iterator().next().getIdentifier());
			count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void iterate_String() throws PsimiTabException, IOException {
		psidev.psi.mi.tab.io.PsimiTabReader mitabReader = new PsimiTabReader();
		Iterator<BinaryInteraction> ii = mitabReader.iterate(MITAB_2_LINE_WITH_HEADER);

		int count = 0;
		while (ii.hasNext()) {
			BinaryInteraction interaction = ii.next();

			assertEquals("P23367", interaction.getInteractorA().getIdentifiers().iterator().next().getIdentifier());
			assertEquals("P06722", interaction.getInteractorB().getIdentifiers().iterator().next().getIdentifier());

			count++;
		}

		assertEquals(2, count);
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
		BinaryInteraction interactions = mitabReader.readLine(ANNOTATION_WITH_CONTROL);

	}
}