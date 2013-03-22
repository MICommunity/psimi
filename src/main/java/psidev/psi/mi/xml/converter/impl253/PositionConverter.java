package psidev.psi.mi.xml.converter.impl253;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;

import java.math.BigInteger;


/**
 * Converts a Position.
 *
 * @author Michael Mueller (mmueller@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Position
 * @see psidev.psi.mi.xml253.jaxb.PositionType
 * @since <pre>07-Jun-2006</pre>
 */
public class PositionConverter {

    public PositionConverter() {
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Position fromJaxb( psidev.psi.mi.xml253.jaxb.PositionType jPosition ) {

        if ( jPosition == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB PositionType." );
        }

        psidev.psi.mi.xml.model.Position mPosition = new psidev.psi.mi.xml.model.Position();

        Locator locator = jPosition.sourceLocation();
        mPosition.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));
        //Initialise the model reading the Jaxb object

        //position
        mPosition.setPosition( jPosition.getPosition().longValue() );

        return mPosition;
    }

    public psidev.psi.mi.xml253.jaxb.PositionType toJaxb( psidev.psi.mi.xml.model.Position mPosition ) {

        if ( mPosition == null ) {
            throw new IllegalArgumentException( "You must give a non null model Position." );
        }

        psidev.psi.mi.xml253.jaxb.PositionType jPosition = new psidev.psi.mi.xml253.jaxb.PositionType();

        //Initialise the JAXB object reading the model

        //position
        jPosition.setPosition( BigInteger.valueOf( mPosition.getPosition() ) );

        return jPosition;
    }
}