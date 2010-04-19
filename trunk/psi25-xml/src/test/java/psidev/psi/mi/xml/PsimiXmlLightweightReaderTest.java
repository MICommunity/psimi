package psidev.psi.mi.xml;

import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;
import psidev.psi.mi.xml.xmlindex.PsimiXmlIndexerTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * PsimiXmlLightweightReader Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlLightweightReaderTest {

    ////////////////////////////////
    // Compatibility with JUnit 3

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter( PsimiXmlLightweightReaderTest.class );
    }

    ////////////////////
    // Tests

    @Test( expected = NullPointerException.class )
    public void psimiXmlPullReader_file() throws PsimiXmlReaderException {
        new PsimiXmlLightweightReader( (File) null );
    }

    @Test( expected = NullPointerException.class )
    public void psimiXmlPullReader_url() throws PsimiXmlReaderException {
        new PsimiXmlLightweightReader( ( URL ) null );
    }

    @Test( expected = NullPointerException.class )
    public void psimiXmlPullReader_inputStream() throws PsimiXmlReaderException {
        new PsimiXmlLightweightReader( ( InputStream ) null );
    }

    @Test
    public void getIndexedEntries_253_file() throws Exception {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.253.xml").getFile() );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 1, indexedEntries.size() );
    }

    @Test
    public void getIndexedEntries_254_file() throws Exception {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.254.xml").getFile() );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 1, indexedEntries.size() );
    }

    @Test
    public void getIndexedEntries_254_parameter_with_expRef() throws Exception {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/16141327.254.xml").getFile() );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 1, indexedEntries.size() );

        for ( IndexedEntry entry : indexedEntries ) {
            final Iterator<Interaction> iteractor = entry.unmarshallInteractionIterator();
            while ( iteractor.hasNext() ) {
                iteractor.next();
            }
        }
    }

    @Test
    public void getIndexedEntries_254_participantIdentification_with_expRef() throws Exception {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/16141327_participantIdMethod.xml").getFile() );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 1, indexedEntries.size() );

        for ( IndexedEntry entry : indexedEntries ) {
            final Iterator<Interaction> iteractor = entry.unmarshallInteractionIterator();
            while ( iteractor.hasNext() ) {
                iteractor.next();
            }
        }
    }

    @Test
    public void getIndexedEntries_253_inputStream() throws Exception {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.253.xml").getFile() );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( new FileInputStream( file ) );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 1, indexedEntries.size() );
    }

    @Test
    public void getIndexedEntries_254_inputStream() throws Exception {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.254.xml").getFile() );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( new FileInputStream( file ) );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 1, indexedEntries.size() );
    }

    @Test
    public void getIndexedEntries_multipleEntries() throws Exception {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.v4.253.xml").getFile() );
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 2, indexedEntries.size() );
    }
}