package psidev.psi.mi.xml.io.impl;

import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;
import psidev.psi.mi.xml.xmlindex.PsimiXmlIndexerTest;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * PsimiXmlLightweightReader Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlLightweightReader253Test {

    ////////////////////////////////
    // Compatibility with JUnit 3

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter( PsimiXmlLightweightReader253Test.class );
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
    public void getIndexedEntries() throws Exception {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.253.xml").getFile() );
        PsimiXmlLightweightReader253 reader = new PsimiXmlLightweightReader253( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 1, indexedEntries.size() );
    }

    @Test
    public void getIndexedEntries_multipleEntries() throws Exception {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.v4.253.xml").getFile() );
        PsimiXmlLightweightReader253 reader = new PsimiXmlLightweightReader253( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 2, indexedEntries.size() );
    }
}
