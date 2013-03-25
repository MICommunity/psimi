package psidev.psi.mi.xml.converter.impl253;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.xml.listeners.PsiXml25ParserListener;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Michael Mueller (mmueller@ebi.ac.uk)
 * @version $Id$
 * @see psidev.psi.mi.xml.model.Interval
 * @see psidev.psi.mi.xml253.jaxb.IntervalType
 * @since <pre>07-Jun-2006</pre>
 */
public class IntervalConverter {

    private List<PsiXml25ParserListener> listeners;

    public IntervalConverter() {
    }

    public void setListeners(List<PsiXml25ParserListener> listeners) {
        this.listeners = listeners;
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.Interval fromJaxb( psidev.psi.mi.xml253.jaxb.IntervalType jInterval ) {

        if ( jInterval == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB Interval." );
        }

        psidev.psi.mi.xml.model.Interval mInterval = new psidev.psi.mi.xml.model.Interval();
        Locator locator = jInterval.sourceLocation();
        mInterval.setSourceLocator(new FileSourceLocator(locator.getLineNumber(), locator.getColumnNumber()));

        //Initialise the model reading the Jaxb object

        // 1. set attributes
        mInterval.setBegin( jInterval.getBegin().longValue() );
        mInterval.setEnd( jInterval.getEnd().longValue() );

        return mInterval;
    }

    public psidev.psi.mi.xml253.jaxb.IntervalType toJaxb( psidev.psi.mi.xml.model.Interval mInterval ) {

        if ( mInterval == null ) {
            throw new IllegalArgumentException( "You must give a non null model Interval." );
        }

        psidev.psi.mi.xml253.jaxb.IntervalType jInterval = new psidev.psi.mi.xml253.jaxb.IntervalType();

        //Initialise the JAXB object reading the model

        // 1. set attributes
        jInterval.setBegin( BigInteger.valueOf( mInterval.getBegin() ) );
        jInterval.setEnd( BigInteger.valueOf( mInterval.getEnd() ) );

        return jInterval;
    }
}