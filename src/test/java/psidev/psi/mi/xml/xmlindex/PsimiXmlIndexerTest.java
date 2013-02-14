package psidev.psi.mi.xml.xmlindex;

import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import psidev.psi.mi.xml.converter.impl253.ExperimentDescriptionConverter;
import psidev.psi.mi.xml.dao.lazy.LazyDAOFactory;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml253.jaxb.EntrySet;
import psidev.psi.mi.xml253.jaxb.ExperimentType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.sax.SAXSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * PsimiXmlIndexer Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlIndexerTest {

    ////////////////////////////////
    // Compatibility with JUnit 3

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter( PsimiXmlIndexerTest.class );
    }

    ////////////////////
    // Tests

    @Test
    /**
     * This is in a nutshell what the pull parser framework does.
     */
    public void buildExperiment_using_sax_filter() throws Exception {

        // http://www.genender.net/blog/2006/03/07/fixing-up-namespaces-with-jaxb-20-and-sax/

        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.253.xml").getFile() );

        // index the XML file
        PsimiXmlIndexer indexer = new PsimiXmlIndexer();
        PsimiXmlFileIndex index = indexer.build( file );

        // init jaxb
        JAXBContext jaxbContext = JAXBContext.newInstance( EntrySet.class.getPackage().getName() );
        Unmarshaller um = jaxbContext.createUnmarshaller();
        UnmarshallerHandler uh = um.getUnmarshallerHandler();

        // create a new XML parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware( true );
        factory.setValidating( true );
        SAXParser parser = factory.newSAXParser();

        XMLReader xmlReader = parser.getXMLReader();

        // get the input stream to parse
        FileInputStream fis = new FileInputStream( file );
        InputStreamRange position = index.getExperimentPosition( 2 );
        Assert.assertTrue( 1016L == position.getFromPosition() || 1042L == position.getFromPosition()); // windows / linux

        InputStream is = PsimiXmlExtractor.extractXmlSnippet( file, position );

        // Create a filter to intercept events -- and patch the missing namespace
        PsimiXmlNamespaceFilter253 xmlFilter = new PsimiXmlNamespaceFilter253( xmlReader );
        xmlFilter.setContentHandler( uh );
        SAXSource source = new SAXSource( xmlFilter, new InputSource( is ) );

        ExperimentType e = um.unmarshal( source, ExperimentType.class ).getValue();
        Assert.assertNotNull( e );
        Assert.assertEquals( 2, e.getId() );

        ExperimentDescriptionConverter ec = new ExperimentDescriptionConverter();
        ec.setDAOFactory( new LazyDAOFactory() );

        ExperimentDescription experimentDescription = ec.fromJaxb( e );
        Assert.assertNotNull( experimentDescription );
        Assert.assertEquals( 2, experimentDescription.getId() );
    }

    @Test
    public void build() throws IOException, JAXBException, XMLStreamException {
        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.253.xml").getFile() );

        // index the XML file
        PsimiXmlIndexer indexer = new PsimiXmlIndexer();
        PsimiXmlFileIndex index = indexer.build( file );

        Assert.assertEquals( 1, index.getExperimentCount() );
        Assert.assertEquals( 3, index.getInteractorCount() );
        Assert.assertEquals( 2, index.getInteractionCount() );
        Assert.assertEquals( 4, index.getParticipantCount() );
        Assert.assertEquals( 4, index.getFeatureCount() );
    }
}