package psidev.psi.mi.xml.xmlindex;

import psidev.psi.mi.xml.PsimiXmlReaderRuntimeException;
import psidev.psi.mi.xml.model.Interaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * PSI-MI Interaction Iterator.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0
 */
public class InteractionIterator implements Iterator<Interaction> {

    /**
     * The source file from which we read the data.
     */
    private File file;

    /**
     * Iterator on the locations of all interaction in the given file.
     */
    private Iterator<InputStreamRange> iterator;

    private PsimiXmlExtractor psimiXmlExtractor;

    private PsimiXmlPullParser psimiXmlPullParser;

    //////////////////
    // Constructors

    public InteractionIterator( PsimiXmlFileIndex index,
                                List<InputStreamRange> ranges,
                                File file, PsimiXmlPullParser psimiXmlPullParser ) {

        if ( index == null ) {
            throw new IllegalArgumentException( "You must give a non null PsimiXmlFileIndex" );
        }
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
        psimiXmlExtractor = new PsimiXmlExtractor( index, psimiXmlPullParser );
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
    private Interaction getInteractionByRange( InputStreamRange range ) {
        Interaction interaction;
        try {
            FileInputStream fis = new FileInputStream( file );
            final InputStream snippetStream = PsimiXmlExtractor.extractXmlSnippet( fis, range );
            interaction = psimiXmlPullParser.parseInteraction( snippetStream );

            // Resolve various references
            psimiXmlExtractor.resolveReferences( fis, interaction );

        } catch ( Exception e ) {
            throw new PsimiXmlReaderRuntimeException( "An error occured while parsing interaction", e );
        }

        return interaction;
    }

    /////////////////////
    // Iterator

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Interaction next() {
        final InputStreamRange range = iterator.next();

        Interaction interaction = getInteractionByRange( range );
        psimiXmlExtractor.clearExperimentCache(); // we only cache experiment in the scope of one interaction
        return interaction;
    }

    public void remove() {
        throw new UnsupportedOperationException( "Interaction iterator is read only, you cannot remove elements." );
    }
}