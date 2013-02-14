package psidev.psi.mi.xml.xmlindex;

import psidev.psi.mi.xml.PsimiXmlReaderRuntimeException;
import psidev.psi.mi.xml.model.ExperimentDescription;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * PSI-MI Interactor Iterator.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.5.0
 */
public class ExperimentIterator implements Iterator<ExperimentDescription> {

    /**
     * The source file from which we read the data.
     */
    private File file;

    private PsimiXmlPullParser parser;

    /**
     * Iterator on the locations of all interactors in the given file.
     */
    private Iterator<InputStreamRange> iterator;

    //////////////////
    // Constructors

    public ExperimentIterator( List<InputStreamRange> ranges,
                               File file, PsimiXmlPullParser parser ) {
        if ( ranges == null ) {
            throw new IllegalArgumentException( "You must give a non null List<InputStreamRange>" );
        }
        if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null XML File" );
        }
        if ( parser == null ) {
            throw new IllegalArgumentException( "You must give a non null parser" );
        }
        this.file = file;
        iterator = ranges.iterator();
        this.parser = parser;
    }

    /////////////////////
    // Parsing

    /**
     * Extracts an interaction from the XML file and resolve references recursively.
     *
     * @param range the range of the interaction we want to get.
     * @return an interaction with resolved references.
     */
    private ExperimentDescription getExperimentByRange( InputStreamRange range ) {
        ExperimentDescription interactor;
        try {
            FileInputStream fis = new FileInputStream( file );
            final InputStream snippetStream = PsimiXmlExtractor.extractXmlSnippet( file, range );
            interactor = parser.parseExperiment( snippetStream );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderRuntimeException( "An error occured while parsing experiment", e );
        }

        return interactor;
    }

    /////////////////////
    // Iterator

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public ExperimentDescription next() {
        final InputStreamRange range = iterator.next();
        return getExperimentByRange( range );
    }

    public void remove() {
        throw new UnsupportedOperationException( "ExperimentDescription iterator is read only, you cannot remove elements." );
    }
}