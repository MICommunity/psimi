package psidev.psi.mi.xml.model;

import javax.xml.bind.Unmarshaller;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Iterator;

/**
 * Steamed access to the data of a PSI-MI entry.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public class StreamedEntry {

    private XMLStreamReader xpp;

    private Unmarshaller unmarshaller;

    private Source source;

    private Iterator<Availability> availabilities;

    private Iterator<ExperimentDescription> experimentIterator;

    private Iterator<Interactor> interactorIterator;

    private Iterator<Interaction> interactionIterator;

    private Iterator<Attribute> attributeIterator;

    //////////////////
    // Constructors

    public StreamedEntry( XMLStreamReader xpp, Unmarshaller unmarshaller ) {
        if ( xpp == null ) {
            throw new IllegalArgumentException( "You must give a non null XMLStreamReader." );
        }
        try {
            xpp.require( START_ELEMENT, null, "entry" );
            if ( xpp.getEventType() != START_ELEMENT ) {
                throw new IllegalArgumentException( "The given XMLStreamReader isn't positioned on <entry>." );
            }
        } catch ( XMLStreamException e ) {
            throw new IllegalArgumentException( "The given XMLStreamReader isn't positioned on <entry>." );
        }
        this.xpp = xpp;

        if ( unmarshaller == null ) {
            throw new IllegalArgumentException( "You must give a non null Unmarshaller." );
        }

        this.unmarshaller = unmarshaller;
    }

    /////////////////////////////
    // Getters and Setters

//    public Iterator<ExperimentDescription> getExperimentIterator() {
//
//        return new Iterator<ExperimentDescription>() {
//            public boolean hasNext() {
//                return "experiment".equals( xpp.getLocalName() );
//            }
//
//            public ExperimentDescription next() {
//                try {
//                    return unmarshaller.unmarshal( xpp, ExperimentDescription.class );
//                } catch ( JAXBException e ) {
//                    e.printStackTrace();
//                }
//            }
//
//            public void remove() {
//                throw new UnsupportedOperationException();
//            }
//        };
//    }

    public Iterator<Interactor> getInteractorIterator() {
        return interactorIterator;
    }

    public Iterator<Interaction> getInteractionIterator() {
        return interactionIterator;
    }
}
