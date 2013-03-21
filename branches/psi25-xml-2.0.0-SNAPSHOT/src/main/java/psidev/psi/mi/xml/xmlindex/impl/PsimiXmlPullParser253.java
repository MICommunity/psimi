package psidev.psi.mi.xml.xmlindex.impl;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.PsimiXmlReaderRuntimeException;
import psidev.psi.mi.xml.converter.impl253.*;
import psidev.psi.mi.xml.dao.lazy.LazyDAOFactory;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml.xmlindex.PsimiXmlNamespaceFilter253;
import psidev.psi.mi.xml.xmlindex.PsimiXmlPullParser;
import psidev.psi.mi.xml253.jaxb.*;
import psidev.psi.mi.xml253.jaxb.EntrySet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse various PSI-MI element from an InputStream.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlPullParser253 implements PsimiXmlPullParser {

    private PsimiXmlNamespaceFilter253 xmlFilter;

    private Unmarshaller um;

    private List<PsiXml25ParserListener> listeners;

    //////////////////
    // Constructors

    public PsimiXmlPullParser253() throws PsimiXmlReaderRuntimeException {

        try {
            // Init JAXB
        	
        		// This is required for OSGi environments to set correct class loader.
        		final ClassLoader cl = ObjectFactory.class.getClassLoader();
            final JAXBContext jaxbContext = JAXBContext.newInstance( EntrySet.class.getPackage().getName(), cl );
            this.um = jaxbContext.createUnmarshaller();
            UnmarshallerHandler uh = um.getUnmarshallerHandler();

            // Create a new XML parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware( true );
            factory.setValidating( true );
            SAXParser parser = factory.newSAXParser();

            XMLReader xmlReader = parser.getXMLReader();

            // Create a filter to intercept events -- and patch the missing namespace
            xmlFilter = new PsimiXmlNamespaceFilter253( xmlReader );
            xmlFilter.setContentHandler( uh );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderRuntimeException( "An error occured while initialising the parser.", e );
        }
    }

    public PsimiXmlPullParser253( JAXBContext jaxbContext ) throws PsimiXmlReaderRuntimeException {

        try {
            // Init JAXB
            this.um = jaxbContext.createUnmarshaller();
            UnmarshallerHandler uh = um.getUnmarshallerHandler();

            // Create a new XML parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware( true );
            factory.setValidating( true );
            SAXParser parser = factory.newSAXParser();

            XMLReader xmlReader = parser.getXMLReader();

            // Create a filter to intercept events -- and patch the missing namespace
            xmlFilter = new PsimiXmlNamespaceFilter253( xmlReader );
            xmlFilter.setContentHandler( uh );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderRuntimeException( "An error occured while initialising the parser.", e );
        }
    }

    /////////////////////////
    // Parsing

    public Entry parseEntry( InputStream is ) throws PsimiXmlReaderException {

        try {
            EntryType e = um.unmarshal( buildSaxSource( is ), EntryType.class ).getValue();

            EntryConverter ic = new EntryConverter();
            ic.setDAOFactory( new LazyDAOFactory() );

            return ic.fromJaxb( e );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an entry.", e );
        }
    }

    public Source parseSource( InputStream is ) throws PsimiXmlReaderException {

        try {
            EntryType.Source s = um.unmarshal( buildSaxSource( is ),
                                               EntryType.Source.class ).getValue();
            SourceConverter ic = new SourceConverter();

            return ic.fromJaxb( s );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an source.", e );
        }
    }

    public List<Availability> parseAvailabilityList( InputStream is ) throws PsimiXmlReaderException {

        try {
            EntryType.AvailabilityList i = um.unmarshal( buildSaxSource( is ),
                                                         EntryType.AvailabilityList.class ).getValue();
            List<Availability> availabilities = new ArrayList<Availability>();

            AvailabilityConverter ac = new AvailabilityConverter();
            for ( AvailabilityType availabilityType : i.getAvailabilities() ) {
                availabilities.add( ac.fromJaxb( availabilityType ) );
            }

            return availabilities;
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an availability.", e );
        }
    }

    public Interaction parseInteraction( InputStream is ) throws PsimiXmlReaderException {

        try {
            EntryType.InteractionList.Interaction i = um.unmarshal( buildSaxSource( is ),
                                                                    EntryType.InteractionList.Interaction.class ).getValue();

            InteractionConverter ic = new InteractionConverter();
            ic.setDAOFactory( new LazyDAOFactory() );

            return ic.fromJaxb( i );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an interaction.", e );
        }
    }

    public ExperimentDescription parseExperiment( InputStream is ) throws PsimiXmlReaderException {

        try {
            ExperimentType e = um.unmarshal( buildSaxSource( is ),
                                             ExperimentType.class ).getValue();

            ExperimentDescriptionConverter ec = new ExperimentDescriptionConverter();
            ec.setDAOFactory( new LazyDAOFactory() );

            return ec.fromJaxb( e );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an experiment.", e );
        }
    }

    public Interactor parseInteractor( InputStream is ) throws PsimiXmlReaderException {

        InteractorElementType i = null;
        try {
            i = um.unmarshal( buildSaxSource( is ), InteractorElementType.class ).getValue();

            InteractorConverter ic = new InteractorConverter();
            ic.setDAOFactory( new LazyDAOFactory() );

            return ic.fromJaxb( i );
        } catch ( Exception e ) {
            if( i != null ) {
                throw new PsimiXmlReaderException( "An error occured while parsing interactor with id: "+i.getId(), e );
            } else {
                throw new PsimiXmlReaderException( "Could not unmarshall interactor.", e );
            }
        }
    }

    public Participant parseParticipant( InputStream is ) throws PsimiXmlReaderException {

        try {
            ParticipantType p = um.unmarshal( buildSaxSource( is ),
                                              ParticipantType.class ).getValue();

            ParticipantConverter pc = new ParticipantConverter();
            pc.setDAOFactory( new LazyDAOFactory() );

            return pc.fromJaxb( p );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing a participant.", e );
        }
    }

    public Feature parseFeature( InputStream is ) throws PsimiXmlReaderException {

        try {
            FeatureElementType f = um.unmarshal( buildSaxSource( is ),
                                                 FeatureElementType.class ).getValue();

            FeatureConverter fc = new FeatureConverter();
            fc.setDAOFactory( new LazyDAOFactory() );

            return fc.fromJaxb( f );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing a feature.", e );
        }
    }

    public Attribute parseAttribute( InputStream is ) throws PsimiXmlReaderException {
        try {
            AttributeListType.Attribute a = um.unmarshal( buildSaxSource( is ),
                                                          AttributeListType.Attribute.class ).getValue();

            AttributeConverter fc = new AttributeConverter();
            return fc.fromJaxb( a );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an attribute.", e );
        }
    }

    public void registerListener(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
    }

    ////////////////////
    // Utility methods

    private SAXSource buildSaxSource( InputStream is ) {
        return new SAXSource( xmlFilter, new InputSource( is ) );
    }
}