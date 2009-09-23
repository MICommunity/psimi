package psidev.psi.mi.xml.io.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.io.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml.xmlindex.*;
import psidev.psi.mi.xml.xmlindex.impl.PsimiXmlPullParser254;
import psidev.psi.tools.xxindex.index.IndexElement;
import psidev.psi.tools.xxindex.index.StandardXpathIndex;
import psidev.psi.tools.xxindex.index.XmlXpathIndexer;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides for parsing of PSI-MI XML 2.5 content using indexing and random file access technology.
 * <p/>
 * This feature aims at providing user with high performance parsing of large data file keeping a small memory
 * footprint.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlLightweightReader254 implements PsimiXmlLightweightReader {

    private static final Log log = LogFactory.getLog( PsimiXmlLightweightReader254.class );

    /**
     * The XML data to be worked on.
     */
    private File xmlDataFile;

    /**
     * Index of the given xmlDataFile by Xpath expression.
     */
    private StandardXpathIndex xpathIndex;

    private static final Set<String> psimixmlXpathIncludes = new HashSet<String>();

    static {
        // we add the list of tags to be indexed, this should keep the memory usage as low as possible.
        psimixmlXpathIncludes.add( "/entrySet" );
        psimixmlXpathIncludes.add( "/entrySet/entry" );
        psimixmlXpathIncludes.add( "/entrySet/entry/source" );
        psimixmlXpathIncludes.add( "/entrySet/entry/availabilityList" );
        psimixmlXpathIncludes.add( "/entrySet/entry/availabilityList/availability" );
        psimixmlXpathIncludes.add( "/entrySet/entry/experimentList/experimentDescription" );
        psimixmlXpathIncludes.add( "/entrySet/entry/interactorList/interactor" );
        psimixmlXpathIncludes.add( "/entrySet/entry/interactionList/interaction" );
        psimixmlXpathIncludes.add( "/entrySet/entry/interactionList/interaction/participantList/participant" );
        psimixmlXpathIncludes.add( "/entrySet/entry/interactionList/interaction/participantList/participant/featureList/feature" );
        psimixmlXpathIncludes.add( "/entrySet/entry/attributeList" );
        psimixmlXpathIncludes.add( "/entrySet/entry/attributeList/attribute" );
    }

    private PsimiXmlPullParser parser;

    //////////////////
    // Constructors

    public PsimiXmlLightweightReader254( File xmlDataFile ) throws PsimiXmlReaderException {
        init( xmlDataFile );
    }

    public PsimiXmlLightweightReader254( URL url ) throws PsimiXmlReaderException {
        if ( url == null ) {
            throw new IllegalArgumentException( "You must give a non null url" );
        }
        try {
            File f = saveOnDisk( url.openStream() );
            init( f );
        } catch ( IOException e ) {
            throw new PsimiXmlReaderException( "An error occured while reading the content of the URL: " + url, e );
        }
    }

    public PsimiXmlLightweightReader254( InputStream is ) throws PsimiXmlReaderException {
        if ( is == null ) {
            throw new IllegalArgumentException( "You must give a non null data stream" );
        }
        try {
            File f = saveOnDisk( is );
            init( f );
        } catch ( IOException e ) {
            throw new PsimiXmlReaderException( "An error occured while reading the content of the given input stream,", e );
        }
    }

    ////////////////////
    // Private methods

    private void init( File file ) throws PsimiXmlReaderException {
        if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null xmlDataFile" );
        }

        if ( !file.exists() ) {
            throw new IllegalArgumentException( "You must give an existing xmlDataFile: " + file.getAbsolutePath() );
        }

        if ( !file.canRead() ) {
            throw new IllegalArgumentException( "You must give a readable xmlDataFile: " + file.getAbsolutePath() );
        }

        this.xmlDataFile = file;

        try {
            if ( log.isDebugEnabled() ) {
                log.debug( "Building XPath index from xmlDataFile: " + file.getAbsolutePath() );
            }

            xpathIndex = XmlXpathIndexer.buildIndex( new FileInputStream( file ), psimixmlXpathIncludes, true );

            if ( log.isDebugEnabled() ) {
                log.debug( "Content of the Xpath index:" );
                log.debug( "--------------------------" );
                Set<String> keys = xpathIndex.getKeys();
                for ( String key : keys ) {
                    log.debug( key + " -> " + xpathIndex.getElements( key ).size() );
                }
            }

        } catch ( IOException e ) {
            throw new PsimiXmlReaderException( "An error occured while indexing the xmlDataFile: " + file.getAbsolutePath(), e );
        }
    }

    /**
     * Builds a PsimiXmlFileIndex based on an Xpath index. iterate over all objects to index, read their XML
     * representation, unmarshal it and store their id and range in PsimiXmlFileIndex. Any element of which the
     * range is not bound within the given entryRange is filtered out.
     *
     * @param index      xpath index
     * @param file       the original source xmlDataFile
     * @param entryRange the range of an entry upon which all elements get filtered.
     * @return a newly build index by object id
     * @throws psidev.psi.mi.xml.PsimiXmlReaderException
     */
    private PsimiXmlFileIndex buildPsimiIndex( StandardXpathIndex index, File file, final IndexElement entryRange ) throws PsimiXmlReaderException {

        if ( log.isDebugEnabled() ) {
            log.debug( "Building index by id from xmlDataFile: " + file.getAbsolutePath() );
        }

        PsimiXmlFileIndex psiIndex = new PsimiXmlFileIndex( file );

        final FileInputStream fis;
        try {
            fis = new FileInputStream( file );
        } catch ( FileNotFoundException e ) {
            throw new PsimiXmlReaderException( "Error while creating a FileInputStream for: " +
                                               file.getAbsolutePath(), e );
        }

        // XML snippet parser
        parser = new PsimiXmlPullParser254();

        // process experiments
        List<IndexElement> experimentRanges = index.getElements( PsiMi25ElementXpath.EXPERIMENT );
        experimentRanges = IndexElementUtils.filterRanges( entryRange, experimentRanges );
        if ( log.isDebugEnabled() ) {
            log.debug( "Now converting " + experimentRanges.size() + " interaction(s)" );
        }
        for ( IndexElement range : experimentRanges ) {
            final InputStreamRange isr = new InputStreamRangeAdapter( range );

            final InputStream snippetStream = PsimiXmlExtractor.extractXmlSnippet( fis, isr );
            final ExperimentDescription ed = parser.parseExperiment( snippetStream );
            psiIndex.addExperiment( ed.getId(), isr );
        }

        // process interactors
        List<IndexElement> interactorRanges = index.getElements( PsiMi25ElementXpath.INTERACTOR );
        interactorRanges = IndexElementUtils.filterRanges( entryRange, interactorRanges );
        if ( log.isDebugEnabled() ) {
            log.debug( "Now converting " + interactorRanges.size() + " interaction(s)" );
        }
        for ( IndexElement range : interactorRanges ) {
            final InputStreamRange isr = new InputStreamRangeAdapter( range );

            final InputStream snippetStream = PsimiXmlExtractor.extractXmlSnippet( fis, isr );
            final Interactor interactor = parser.parseInteractor( snippetStream );
            psiIndex.addInteractor( interactor.getId(), isr );
        }

        // process interactions
        List<IndexElement> interactionRanges = index.getElements( PsiMi25ElementXpath.INTERACTION );
        interactionRanges = IndexElementUtils.filterRanges( entryRange, interactionRanges );
        if ( log.isDebugEnabled() ) {
            log.debug( "Now converting " + interactionRanges.size() + " interaction(s)" );
        }
        for ( IndexElement range : interactionRanges ) {
            final InputStreamRange isr = new InputStreamRangeAdapter( range );

            final InputStream snippetStream = PsimiXmlExtractor.extractXmlSnippet( fis, isr );
            final Interaction interaction = parser.parseInteraction( snippetStream );
            psiIndex.addInteraction( interaction.getId(), isr );
        }

        // process participants
        List<IndexElement> participantRanges = index.getElements( PsiMi25ElementXpath.PARTICIPANT );
        participantRanges = IndexElementUtils.filterRanges( entryRange, participantRanges );
        if ( log.isDebugEnabled() ) {
            log.debug( "Now converting " + participantRanges.size() + " participant(s)" );
        }
        for ( IndexElement range : participantRanges ) {
            final InputStreamRange isr = new InputStreamRangeAdapter( range );

            final InputStream snippetStream = PsimiXmlExtractor.extractXmlSnippet( fis, isr );
            final Participant participant = parser.parseParticipant( snippetStream );
            psiIndex.addParticipant( participant.getId(), isr );
        }

        // process features
        List<IndexElement> featureRanges = index.getElements( PsiMi25ElementXpath.FEATURE );
        featureRanges = IndexElementUtils.filterRanges( entryRange, featureRanges );
        if ( log.isDebugEnabled() ) {
            log.debug( "Now converting " + featureRanges.size() + " feature(s)" );
        }
        for ( IndexElement range : featureRanges ) {
            final InputStreamRange isr = new InputStreamRangeAdapter( range );

            final InputStream snippetStream = PsimiXmlExtractor.extractXmlSnippet( fis, isr );
            final Feature feature = parser.parseFeature( snippetStream );
            psiIndex.addFeature( feature.getId(), isr );
        }

        if ( log.isDebugEnabled() ) {
            log.debug( "Experiment: " + psiIndex.getExperimentCount());
            log.debug( "Interaction: " + psiIndex.getInteractionCount());
            log.debug( "Interactor: " + psiIndex.getInteractorCount());
            log.debug( "Participant: " + psiIndex.getParticipantCount());
            log.debug( "Feature: " + psiIndex.getFeatureCount());
        }

        return psiIndex;
    }

    private File saveOnDisk( InputStream is ) throws IOException {

        // TODO if 'is' is already an FileInputStream, we are already reading from the local filesystem ... no need to save again.

        File tmpFile = File.createTempFile( "psi25-xml.", ".xml" );
        tmpFile.deleteOnExit();

        BufferedWriter out = new BufferedWriter( new FileWriter( tmpFile ) );
        BufferedReader in = new BufferedReader( new InputStreamReader( is ) );

        char[] buf = new char[8192];
        int read = -2;
        while ( ( read = in.read( buf, 0, 8192 ) ) != -1 ) {
            out.write( buf, 0, read );
        }

        in.close();
        out.flush();
        out.close();

        return tmpFile;
    }

    //////////////////////////
    //  Public methods

    /**
     * Provides an entry point to the underlying XML data.
     *
     * @return a non null List of indexed entry.
     * @throws psidev.psi.mi.xml.PsimiXmlReaderException
     * @since 1.5.0
     */
    public List<IndexedEntry> getIndexedEntries() throws PsimiXmlReaderException {
        List<IndexedEntry> entries = new ArrayList<IndexedEntry>();

        if (parser == null) {
            parser = new PsimiXmlPullParser254();
        }

        List<IndexElement> ranges = xpathIndex.getElements( "/entrySet/entry" );
        for ( IndexElement range : ranges ) {
            // build an index for that specific entry
            final PsimiXmlFileIndex myIndex = buildPsimiIndex( xpathIndex, xmlDataFile, range );
            entries.add( new IndexedEntry( myIndex, xpathIndex, range, xmlDataFile, parser ) );
        }

        return entries;
    }
}