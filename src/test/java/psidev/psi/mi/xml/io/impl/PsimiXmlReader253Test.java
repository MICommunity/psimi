package psidev.psi.mi.xml.io.impl;

import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.Interactor;

import java.io.*;

/**
 * PsimiXmlReader Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlReader253Test {

    ////////////////////////////////
    // Compatibility with JUnit 3

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter( PsimiXmlReader253Test.class );
    }

    ////////////////////
    // Tests

    @Test
    public void checkInteractorSequence() throws Exception {
        File file = new File( PsimiXmlReader253Test.class.getResource("/sample-xml/intact/10320477.v2.253.xml").getFile() );
        PsimiXmlReader253 reader = new PsimiXmlReader253();

        EntrySet es = reader.read( file );
        Assert.assertNotNull( es );

        final Entry entry = es.getEntries().iterator().next();
        Assert.assertNotNull( entry );

        Assert.assertEquals( 3, entry.getInteractors().size() );

        for ( Interactor interactor : entry.getInteractors() ) {
            switch ( interactor.getId() ) {
                case 4:
                    Assert.assertEquals( "ABCD", interactor.getSequence() );
                    break;
                case 7:
                    Assert.assertEquals( "EFGHI", interactor.getSequence() );
                    break;
                case 11:
                    Assert.assertEquals( "KLMNOPQRSTUVWxYZ", interactor.getSequence() );
                    break;
                default:
                    Assert.fail();
            }
        }
    }

    @Test
    public void read_file() throws Exception {
        File file = new File( PsimiXmlReader253Test.class.getResource("/sample-xml/intact/10320477.253.xml").getFile() );
        PsimiXmlReader253 reader = new PsimiXmlReader253();
        EntrySet es = reader.read( file );
        Assert.assertNotNull( es );
    }

    @Test
    public void read_url() throws Exception {
        File file = new File( PsimiXmlReader253Test.class.getResource("/sample-xml/intact/10320477.253.xml").getFile() );
        PsimiXmlReader253 reader = new PsimiXmlReader253();
        EntrySet es = reader.read( file.toURL() );
        Assert.assertNotNull( es );
    }

    @Test
    public void read_input_stream() throws Exception {
        File file = new File( PsimiXmlReader253Test.class.getResource("/sample-xml/intact/10320477.253.xml").getFile() );
        InputStream is = new FileInputStream( file );
        PsimiXmlReader253 reader = new PsimiXmlReader253();
        EntrySet es = reader.read( is );
        Assert.assertNotNull( es );
    }

    @Test
    public void read_string() throws Exception {
        File file = new File( PsimiXmlReader253Test.class.getResource("/sample-xml/intact/10320477.253.xml").getFile() );

        // cache file in a buffer
        BufferedReader in = new BufferedReader( new FileReader( file ) );
        String str;
        StringBuilder sb = new StringBuilder();
        while ( ( str = in.readLine() ) != null ) {
            sb.append( str );
        }
        in.close();

        // read string
        PsimiXmlReader253 reader = new PsimiXmlReader253();
        EntrySet es = reader.read( sb.toString() );
        Assert.assertNotNull( es );
    }
}
