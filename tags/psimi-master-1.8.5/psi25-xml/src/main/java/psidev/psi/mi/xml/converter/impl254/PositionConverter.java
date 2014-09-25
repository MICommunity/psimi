package psidev.psi.mi.xml.converter.impl254;

import java.math.BigInteger;


/**
 * Converts a Position.
 *
 * @author Michael Mueller (mmueller@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Position
 * @see psidev.psi.mi.xml254.jaxb.Position
 * @since <pre>07-Jun-2006</pre>
 */
public class PositionConverter {

    public PositionConverter() {
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Position fromJaxb( psidev.psi.mi.xml254.jaxb.Position jPosition ) {

        if ( jPosition == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Position." );
        }

        psidev.psi.mi.xml.model.Position mPosition = new psidev.psi.mi.xml.model.Position();

        //Initialise the model reading the Jaxb object

        //position
        mPosition.setPosition( jPosition.getPosition().longValue() );

        return mPosition;
    }

    public psidev.psi.mi.xml254.jaxb.Position toJaxb( psidev.psi.mi.xml.model.Position mPosition ) {

        if ( mPosition == null ) {
            throw new IllegalArgumentException( "You must give a non null model Position." );
        }

        psidev.psi.mi.xml254.jaxb.Position jPosition = new psidev.psi.mi.xml254.jaxb.Position();

        //Initialise the JAXB object reading the model

        //position
        jPosition.setPosition( BigInteger.valueOf( mPosition.getPosition() ) );

        return jPosition;
    }
}