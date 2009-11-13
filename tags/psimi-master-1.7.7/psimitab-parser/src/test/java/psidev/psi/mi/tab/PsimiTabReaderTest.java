package psidev.psi.mi.tab;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

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

        File file = TestHelper.getFileByResources( "/mitab-samples/11585365.tab", PsimiTabReaderTest.class );

        PsimiTabReader reader = new PsimiTabReader( false );
        Collection<BinaryInteraction> interactions = reader.read( file );

        assertEquals( 4, interactions.size() );
    }

    @Test
    public void readFileWithHeader() throws ConverterException, IOException {

        File file = TestHelper.getFileByResources( "/mitab-samples/11585365.txt", PsimiTabReaderTest.class );

        PsimiTabReader reader = new PsimiTabReader( true );
        Collection<BinaryInteraction> interactions = reader.read( file );

        assertEquals( 4, interactions.size() );
    }

    @Test
    public void iterate_withHeader() throws Exception {
        InputStream is = PsimiTabReaderTest.class.getResourceAsStream( "/mitab-samples/11585365.txt" );

        PsimiTabReader reader = new PsimiTabReader( true );
        Iterator<BinaryInteraction> iterator = reader.iterate( is );

        int count = 0;

        while ( iterator.hasNext() ) {
            iterator.next();
            count++;
        }

        assertEquals( 4, count );

        PsimiTabIterator iter = ( PsimiTabIterator ) iterator;
        assertEquals( 4, iter.getInteractionsProcessedCount() );
    }

    @Test
    public void read_file() throws ConverterException, IOException {
        File file = TestHelper.getFileByResources( "/mitab-samples/11585365.txt", PsimiTabReaderTest.class );
        PsimiTabReader mitabReader = new PsimiTabReader( true );
        Collection<BinaryInteraction> interactions = mitabReader.read( file );
        int count = 0;
        for ( BinaryInteraction interaction : interactions ) {
            count++;
        }
        assertEquals( 4, count );
    }

    @Test
    public void iterate_file() throws ConverterException, IOException {
        File file = TestHelper.getFileByResources( "/mitab-samples/11585365.txt", PsimiTabReaderTest.class );
        PsimiTabReader mitabReader = new PsimiTabReader( true );
        Iterator<BinaryInteraction> ii = mitabReader.iterate( file );

        int count = 0;
        while ( ii.hasNext() ) {
            ii.next();
            count++;
        }

        assertEquals( 4, count );
    }

    public static final String MITAB_2_LINE_WITH_HEADER =
            "ID interactor A\tID interactor B\tAlt. ID interactor A\tAlt. ID interactor B\tAliases interactor A\tAliases interactor B\tinteraction detection method(s)\tpublication(s) 1st author(s) surname\tPublication ID\ttaxid interactor A\ttaxid interactor B\tInteraction types\tSource databases and identifiers\tInteraction ID\tConfidenceImpl\n" +
            "uniprotkb:P23367\tuniprotkb:P06722\tinterpro:IPR003594|interpro:IPR002099|go:GO:0005515|intact:EBI-554913\tinterpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:GO:0005515|intact:EBI-545170\tgene name:mutL|locus name:b4170\tgene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831\tadenylate cyclase:MI:0014\t-\tpubmed:11585365\ttaxid:562\ttaxid:562\tphysical interaction:MI:0218\t-\t-\t-\n" +
            "uniprotkb:P23367\tuniprotkb:P06722\t-\t-\t-\t-\t-\t-\t-\ttaxid:562\ttaxid:562\t-\t-\t-\t\t-";

    @Test
    public void read_String() throws ConverterException, IOException {

        PsimiTabReader mitabReader = new PsimiTabReader( true );
        Collection<BinaryInteraction> interactions = mitabReader.read( MITAB_2_LINE_WITH_HEADER );
        int count = 0;
        for ( BinaryInteraction interaction : interactions ) {
            assertEquals( "P23367", interaction.getInteractorA().getIdentifiers().iterator().next().getIdentifier() );
            assertEquals( "P06722", interaction.getInteractorB().getIdentifiers().iterator().next().getIdentifier() );
            count++;
        }
        assertEquals( 2, count );
    }

    @Test
    public void iterate_String() throws ConverterException, IOException {
        PsimiTabReader mitabReader = new PsimiTabReader( true );
        Iterator<BinaryInteraction> ii = mitabReader.iterate( MITAB_2_LINE_WITH_HEADER );

        int count = 0;
        while ( ii.hasNext() ) {
            BinaryInteraction interaction = ii.next();

            assertEquals( "P23367", interaction.getInteractorA().getIdentifiers().iterator().next().getIdentifier() );
            assertEquals( "P06722", interaction.getInteractorB().getIdentifiers().iterator().next().getIdentifier() );

            count++;
        }

        assertEquals( 2, count );
    }

    @Test
    public void emptyInteractionAc() {
        String line = "entrez gene/locuslink:3069\tentrez gene/locuslink:11260\tentrez " +
                "gene/locuslink:HDLBP\tentrez gene/locuslink:XPOT\tentrez " +
                "gene/locuslink:FLJ16432|entrez gene/locuslink:HBP|entrez " +
                "gene/locuslink:PRO2900|entrez gene/locuslink:VGL\tentrez " +
                "gene/locuslink:XPO3\tpsi-mi:\"MI:0401\"(biochemical)\tKruse C (2000)\t" +
                "pubmed:10657246\ttaxid:9606\ttaxid:9606\tpsi-mi:\"MI:0914\"(association)\t" +
                "psi-mi:\"MI:0463\"(GRID)\t-\t-";

        PsimiTabReader mitabReader = new PsimiTabReader( false );
        final BinaryInteraction binaryInteraction = mitabReader.readLine(line);

        Assert.assertTrue(binaryInteraction.getInteractionAcs().isEmpty());
    }
}