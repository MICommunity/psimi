package psidev.psi.mi.xml.xmlindex.impl;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.PsimiXmlReaderRuntimeException;
import psidev.psi.mi.xml.converter.impl254.*;
import psidev.psi.mi.xml.dao.lazy.LazyDAOFactory;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml.xmlindex.PsimiXmlNamespaceFilter254;
import psidev.psi.mi.xml.xmlindex.PsimiXmlPullParser;
import psidev.psi.mi.xml254.jaxb.AvailabilityList;
import psidev.psi.mi.xml254.jaxb.EntrySet;

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
public class PsimiXmlPullParser254 implements PsimiXmlPullParser {

    private PsimiXmlNamespaceFilter254 xmlFilter;

    private Unmarshaller um;

    //////////////////
    // Constructors

    public PsimiXmlPullParser254() throws PsimiXmlReaderRuntimeException {

        try {
            // Init JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance( EntrySet.class.getPackage().getName() );
            this.um = jaxbContext.createUnmarshaller();
            UnmarshallerHandler uh = um.getUnmarshallerHandler();

            // Create a new XML parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware( true );
            factory.setValidating( true );
            SAXParser parser = factory.newSAXParser();

            XMLReader xmlReader = parser.getXMLReader();

            // Create a filter to intercept events -- and patch the missing namespace
            xmlFilter = new PsimiXmlNamespaceFilter254( xmlReader );
            xmlFilter.setContentHandler( uh );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderRuntimeException( "An error occured while initialising the parser.", e );
        }
    }

    public PsimiXmlPullParser254( JAXBContext jaxbContext ) throws PsimiXmlReaderRuntimeException {

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
            xmlFilter = new PsimiXmlNamespaceFilter254( xmlReader );
            xmlFilter.setContentHandler( uh );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderRuntimeException( "An error occured while initialising the parser.", e );
        }
    }

    /////////////////////////
    // Parsing

    public Entry parseEntry( InputStream is ) throws PsimiXmlReaderException {

        try {
            psidev.psi.mi.xml254.jaxb.Entry e = um.unmarshal( buildSaxSource( is ), psidev.psi.mi.xml254.jaxb.Entry.class ).getValue();

            EntryConverter ic = new EntryConverter();
            ic.setDAOFactory( new LazyDAOFactory() );

            return ic.fromJaxb( e );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an entry.", e );
        }
    }

    public Source parseSource( InputStream is ) throws PsimiXmlReaderException {

        try {
            psidev.psi.mi.xml254.jaxb.Source s = um.unmarshal( buildSaxSource( is ),
                                               psidev.psi.mi.xml254.jaxb.Source.class ).getValue();
            SourceConverter ic = new SourceConverter();

            return ic.fromJaxb( s );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an source.", e );
        }
    }

    public List<Availability> parseAvailabilityList( InputStream is ) throws PsimiXmlReaderException {

        try {
            AvailabilityList i = um.unmarshal( buildSaxSource( is ),
                                                         AvailabilityList.class ).getValue();
            List<Availability> availabilities = new ArrayList<Availability>();

            AvailabilityConverter ac = new AvailabilityConverter();
            for ( psidev.psi.mi.xml254.jaxb.Availability availabilityType : i.getAvailabilities() ) {
                availabilities.add( ac.fromJaxb( availabilityType ) );
            }

            return availabilities;
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an availability.", e );
        }
    }

    public Interaction parseInteraction( InputStream is ) throws PsimiXmlReaderException {

        try {
            psidev.psi.mi.xml254.jaxb.Interaction i = um.unmarshal( buildSaxSource( is ),
                                                                    psidev.psi.mi.xml254.jaxb.Interaction.class ).getValue();

            InteractionConverter ic = new InteractionConverter();
            ic.setDAOFactory( new LazyDAOFactory() );

            return ic.fromJaxb( i );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an interaction.", e );
        }
    }

    public ExperimentDescription parseExperiment( InputStream is ) throws PsimiXmlReaderException {

        try {
            psidev.psi.mi.xml254.jaxb.ExperimentDescription e = um.unmarshal( buildSaxSource( is ),
                                             psidev.psi.mi.xml254.jaxb.ExperimentDescription.class ).getValue();

            ExperimentDescriptionConverter ec = new ExperimentDescriptionConverter();
            ec.setDAOFactory( new LazyDAOFactory() );

            return ec.fromJaxb( e );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an experiment.", e );
        }
    }

    public Interactor parseInteractor( InputStream is ) throws PsimiXmlReaderException {
        psidev.psi.mi.xml254.jaxb.Interactor i = null;

        try {
            i = um.unmarshal( buildSaxSource( is ), psidev.psi.mi.xml254.jaxb.Interactor.class ).getValue();

            InteractorConverter ic = new InteractorConverter();
            ic.setDAOFactory( new LazyDAOFactory() );

            return ic.fromJaxb( i );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing interactor with id: "+i.getId(), e );
        }
    }

    public Participant parseParticipant( InputStream is ) throws PsimiXmlReaderException {

        try {
            psidev.psi.mi.xml254.jaxb.Participant p = um.unmarshal( buildSaxSource( is ),
                                              psidev.psi.mi.xml254.jaxb.Participant.class ).getValue();

            ParticipantConverter pc = new ParticipantConverter();
            pc.setDAOFactory( new LazyDAOFactory() );

            return pc.fromJaxb( p );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing a participant.", e );
        }
    }

    public Feature parseFeature( InputStream is ) throws PsimiXmlReaderException {

        try {
            psidev.psi.mi.xml254.jaxb.Feature f = um.unmarshal( buildSaxSource( is ),
                                                 psidev.psi.mi.xml254.jaxb.Feature.class ).getValue();

            FeatureConverter fc = new FeatureConverter();
            fc.setDAOFactory( new LazyDAOFactory() );

            return fc.fromJaxb( f );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing a feature.", e );
        }
    }

    public Attribute parseAttribute( InputStream is ) throws PsimiXmlReaderException {
        try {
            psidev.psi.mi.xml254.jaxb.Attribute a = um.unmarshal( buildSaxSource( is ),
                                                          psidev.psi.mi.xml254.jaxb.Attribute.class ).getValue();

            AttributeConverter fc = new AttributeConverter();
            return fc.fromJaxb( a );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderException( "An error occured while parsing an attribute.", e );
        }
    }

    ////////////////////
    // Utility methods

    private SAXSource buildSaxSource( InputStream is ) {
        return new SAXSource( xmlFilter, new InputSource( is ) );
    }
}