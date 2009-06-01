package psidev.psi.mi.xml.xmlindex;

import psidev.psi.mi.xml.PsimiXmlReaderRuntimeException;
import psidev.psi.mi.xml.model.Interactor;

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
public class InteractorIterator implements Iterator<Interactor> {

    /**
     * The source file from which we read the data.
     */
    private File file;

    /**
     * Iterator on the locations of all interactors in the given file.
     */
    private Iterator<InputStreamRange> iterator;

    private PsimiXmlPullParser psimiXmlPullParser;

    //////////////////
    // Constructors

    public InteractorIterator( List<InputStreamRange> ranges,
                               File file, PsimiXmlPullParser psimiXmlPullParser ) {
        if ( ranges == null ) {
            throw new IllegalArgumentException( "You must give a non null List<InputStreamRange>" );
        }
        if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null XML File" );
        }
        if ( psimiXmlPullParser == null ) {
            throw new IllegalArgumentException( "You must give a non null psimiXmlPullParser" );
        }
        this.file = file;
        iterator = ranges.iterator();
        this.psimiXmlPullParser = psimiXmlPullParser;
    }

    /////////////////////
    // Parsing

    /**
     * Extracts an interaction from the XML file and resolve references recursively.
     *
     * @param range the range of the interaction we want to get.
     * @return an interaction with resolved references.
     */
    private Interactor getInteractorByRange( InputStreamRange range ) {
        Interactor interactor;
        try {
            FileInputStream fis = new FileInputStream( file );
            final InputStream snippetStream = PsimiXmlExtractor.extractXmlSnippet( fis, range );
            interactor = psimiXmlPullParser.parseInteractor( snippetStream );
        } catch ( Exception e ) {
            throw new PsimiXmlReaderRuntimeException( "An error occured while parsing interactor", e );
        }

        return interactor;
    }

    /////////////////////
    // Iterator

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Interactor next() {
        final InputStreamRange range = iterator.next();
        return getInteractorByRange( range );
    }

    public void remove() {
        throw new UnsupportedOperationException( "Interactor iterator is read only, you cannot remove elements." );
    }
}