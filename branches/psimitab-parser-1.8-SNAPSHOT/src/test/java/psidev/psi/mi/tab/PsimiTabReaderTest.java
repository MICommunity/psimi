package psidev.psi.mi.tab;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.ConfidenceImpl;
import psidev.psi.mi.xml.converter.ConverterException;

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
    public void readFileNoHeader() throws ConverterException, IOException {

        psidev.psi.mi.tab.io.PsimiTabReader reader = new PsimiTabReader();
        Collection<BinaryInteraction> interactions = reader.read(TestHelper.TAB_11585365);

        assertEquals(4, interactions.size());
    }

    @Test
    public void readFileWithHeader() throws ConverterException, IOException {

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
    public void read_file() throws ConverterException, IOException {
        psidev.psi.mi.tab.io.PsimiTabReader mitabReader = new PsimiTabReader();
        Collection<BinaryInteraction> interactions = mitabReader.read(TestHelper.TXT_11585365);
        int count = 0;
        for (BinaryInteraction interaction : interactions) {
            count++;
        }
        assertEquals(4, count);
    }

    @Test
    public void iterate_file() throws ConverterException, IOException {
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
    public void read_String() throws ConverterException, IOException {

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
    public void iterate_String() throws ConverterException, IOException {
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
    public void emptyInteractionAc() throws ConverterException {
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
    public void unexpectedFreeTextInConfidences() throws ConverterException {
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


}