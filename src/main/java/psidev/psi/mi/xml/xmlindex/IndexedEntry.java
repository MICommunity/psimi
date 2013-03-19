/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.xml.xmlindex;

import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.xxindex.index.IndexElement;
import psidev.psi.tools.xxindex.index.StandardXpathIndex;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * PSIMI indexed Entry that gives access to sub element of an entry without loading everything in memory.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class IndexedEntry {

    /**
     * File index in which we identify the position of interactions, experiemnts, interactors, participants and features
     * but only for this entry.
     */
    private final PsimiXmlFileIndex idIndex;

    private final StandardXpathIndex xpathIndex;

    /**
     * The byte range of the entry represented by this object.
     */
    private final IndexElement entryIndexElement;

    private final File xmlDataFile;

    private PsimiXmlPullParser psimiXmlPullParser;

    //////////////////
    // Constructors

    public IndexedEntry( PsimiXmlFileIndex idIndex,
                         StandardXpathIndex xpathIndex,
                         IndexElement indexElement,
                         File xmlDataFile,
                         PsimiXmlPullParser psimiXmlPullParser) {
        if ( idIndex == null ) {
            throw new IllegalArgumentException( "You must give a non null identifier index" );
        }
        if ( xpathIndex == null ) {
            throw new IllegalArgumentException( "You must give a non null xpath index" );
        }
        if ( indexElement == null ) {
            throw new IllegalArgumentException( "You must give a non null byte Range" );
        }
        if ( xmlDataFile == null ) {
            throw new IllegalArgumentException( "You must give a non null xml data file" );
        }
        if ( psimiXmlPullParser == null ) {
            throw new IllegalArgumentException( "You must give a non null psimiXmlPullParser" );
        }
        this.idIndex = idIndex;
        this.xpathIndex = xpathIndex;
        this.entryIndexElement = indexElement;
        this.xmlDataFile = xmlDataFile;
        this.psimiXmlPullParser = psimiXmlPullParser;
    }

    //////////////////////////
    // Unmarshalling methods

    /**
     * Returns an Entry that is the current entry.
     *
     * @return an non null entry.
     * @throws PsimiXmlReaderException
     */
    public Entry unmarshalledEntry() throws PsimiXmlReaderException {
        return psimiXmlPullParser.parseEntry( getXmlSnippet( entryIndexElement ) );
    }

    /**
     * Returns a non null List of Availability collected from /entryset/entry/availabilityList in the current entry.
     *
     * @return a non null List of Availability
     * @throws PsimiXmlReaderException
     */
    public List<Availability> unmarshallAvailabilityList() throws PsimiXmlReaderException {
        List<IndexElement> ranges = xpathIndex.getElements( PsiMi25ElementXpath.AVAILABILITY_LIST );
        ranges = IndexElementUtils.filterRanges( entryIndexElement, ranges );
        switch ( ranges.size() ) {
            case 0:
                return Collections.EMPTY_LIST;
            case 1:
                IndexElement range = ranges.iterator().next();
                return psimiXmlPullParser.parseAvailabilityList( getXmlSnippet( range ) );
            default:
                throw new IllegalStateException( "We were expecting none or a single <availibility> element, found " + ranges.size() );
        }
    }

    /**
     * Returns a Source collected from /entryset/entry/source in the current entry.
     *
     * @return a non null List of Source
     * @throws PsimiXmlReaderException
     */
    public Source unmarshallSource() throws PsimiXmlReaderException {
        List<IndexElement> ranges = xpathIndex.getElements( PsiMi25ElementXpath.SOURCE );
        ranges = IndexElementUtils.filterRanges( entryIndexElement, ranges );
        switch ( ranges.size() ) {
            case 1:
                IndexElement range = ranges.iterator().next();
                return psimiXmlPullParser.parseSource( getXmlSnippet( range ) );
            default:
                throw new IllegalStateException( "We were expecting a single <source> element, found " + ranges.size() );
        }
    }

    // Experiment specific methods

    public long getExperimentLineNumber( int id ) {
        InputStreamRange range = idIndex.getExperimentPosition( id );
        return range.getLineNumber();
    }

    /**
     * Returns a non null List of ExperimentDescription collected from /entryset/entry/experimentList in the current entry.
     *
     * @return a non null List of ExperimentDescription
     * @throws PsimiXmlReaderException
     */
    public List<ExperimentDescription> unmarshallExperimentList() throws PsimiXmlReaderException {
        List<IndexElement> ranges = xpathIndex.getElements( PsiMi25ElementXpath.EXPERIMENT );
        ranges = IndexElementUtils.filterRanges( entryIndexElement, ranges );

        List<ExperimentDescription> experiments = new ArrayList<ExperimentDescription>( ranges.size() );
        final PsimiXmlPullParser parser = psimiXmlPullParser;
        for ( IndexElement range : ranges ) {
            experiments.add( parser.parseExperiment( getXmlSnippet( range ) ) );
        }
        return experiments;
    }

    /**
     * Returns a non null List of ExperimentDescription collected from /entryset/entry/experimentList in the current entry.
     *
     * @return a non null List of ExperimentDescription
     * @throws PsimiXmlReaderException
     */
    public Iterator<ExperimentDescription> unmarshallExperimentIterator() throws PsimiXmlReaderException {
        List<IndexElement> ranges = xpathIndex.getElements( PsiMi25ElementXpath.EXPERIMENT );
        ranges = IndexElementUtils.filterRanges( entryIndexElement, ranges );
        return new ExperimentIterator( buildInputStreamRanges( ranges ),
                                       xmlDataFile, psimiXmlPullParser );
    }

    /**
     * Returns an ExperimentDescription collected from /entryset/entry/experimentList in the current entry.
     *
     * @return an ExperimentDescription, null if not found
     * @throws PsimiXmlReaderException
     */
    public ExperimentDescription unmarshallExperimentById( int id ) throws PsimiXmlReaderException {
        ExperimentDescription experiment = null;
        final InputStreamRange isr = idIndex.getExperimentPosition( id );
        if ( isr != null ) {
            final IndexElementAdapter IndexElement = new IndexElementAdapter( isr );
            final PsimiXmlPullParser parser = psimiXmlPullParser;
            experiment = parser.parseExperiment( getXmlSnippet( IndexElement ) );
        }
        return experiment;
    }

    // Interactor specific methods

    public long getInteractorLineNumber( int id ) {
        InputStreamRange range = idIndex.getInteractorPosition( id );
        return range.getLineNumber();
    }

    /**
     * Returns a non null List of Interactor collected from /entryset/entry/interactorList in the current entry.
     *
     * @return a non null List of Interactor
     * @throws PsimiXmlReaderException
     */
    public List<Interactor> unmarshallInteractorList() throws PsimiXmlReaderException {
        List<IndexElement> ranges = xpathIndex.getElements( PsiMi25ElementXpath.INTERACTOR );
        ranges = IndexElementUtils.filterRanges( entryIndexElement, ranges );

        List<Interactor> interactors = new ArrayList<Interactor>( ranges.size() );
        final PsimiXmlPullParser parser = psimiXmlPullParser;
        for ( IndexElement range : ranges ) {
            interactors.add( parser.parseInteractor( getXmlSnippet( range ) ) );
        }
        return interactors;
    }

    /**
     * Returns a non null iterator over Interactor collected from /entryset/entry/interactorList in the current entry.
     *
     * @return a non null iterator of Interactor
     * @throws PsimiXmlReaderException
     */
    public Iterator<Interactor> unmarshallInteractorIterator() throws PsimiXmlReaderException {
        List<IndexElement> ranges = xpathIndex.getElements( PsiMi25ElementXpath.INTERACTOR );
        ranges = IndexElementUtils.filterRanges( entryIndexElement, ranges );
        return new InteractorIterator( buildInputStreamRanges( ranges ),
                                       xmlDataFile, psimiXmlPullParser );
    }

    /**
     * Returns an interactor collected from /entryset/entry/interactorList in the current entry by id.
     *
     * @return an Interactor, null if not found.
     * @throws PsimiXmlReaderException
     */
    public Interactor unmarshallInteractorById( int id ) throws PsimiXmlReaderException {
        Interactor interactor = null;
        final InputStreamRange isr = idIndex.getInteractorPosition( id );
        if ( isr != null ) {
            final IndexElementAdapter IndexElement = new IndexElementAdapter( isr );
            final PsimiXmlPullParser parser = psimiXmlPullParser;
            interactor = parser.parseInteractor( getXmlSnippet( IndexElement ) );
        }
        return interactor;
    }

    // Interaction specific methods

    public long getInteractionLineNumber( int id ) {
        InputStreamRange range = idIndex.getInteractionPosition( id );
        return range.getLineNumber();
    }

    /**
     * Returns a non null List of Interaction collected from /entryset/entry/interactionList in the current entry.
     *
     * @return a non null List of Interaction
     * @throws PsimiXmlReaderException
     */
    public List<Interaction> unmarshallInteractionList() throws PsimiXmlReaderException {
        List<IndexElement> ranges = xpathIndex.getElements( PsiMi25ElementXpath.INTERACTION );
        ranges = IndexElementUtils.filterRanges( entryIndexElement, ranges );

        List<Interaction> interactions = new ArrayList<Interaction>( ranges.size() );
        final PsimiXmlPullParser parser = psimiXmlPullParser;
        for ( IndexElement range : ranges ) {
            Interaction inter = parser.parseInteraction( getXmlSnippet( range ) );
            interactions.add( inter );

            for ( Participant participant : inter.getParticipants() ) {
                if (participant.getParticipantIdentificationMethods().isEmpty()){
                    if (!inter.getExperiments().isEmpty()){
                        for (ExperimentDescription desc : inter.getExperiments()){
                            participant.getParticipantIdentificationMethods().add(desc.getParticipantIdentificationMethod());

                            if (desc.getFeatureDetectionMethod() != null){
                                for (Feature f : participant.getFeatures()){
                                    if (f.getFeatureDetectionMethod() == null){
                                        f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                    }
                                }
                            }
                        }
                    }
                    else if (!inter.getExperimentRefs().isEmpty()){

                        for (ExperimentRef ref : inter.getExperimentRefs()){
                            ExperimentDescription desc = unmarshallExperimentById( ref.getRef() );
                            if (desc != null){
                                participant.getParticipantIdentificationMethods().add(desc.getParticipantIdentificationMethod());

                                if (desc.getFeatureDetectionMethod() != null){
                                    for (Feature f : participant.getFeatures()){
                                        if (f.getFeatureDetectionMethod() == null){
                                            f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    if (!inter.getExperiments().isEmpty()){
                        for (ExperimentDescription desc : inter.getExperiments()){
                            if (desc.getFeatureDetectionMethod() != null){
                                for (Feature f : participant.getFeatures()){
                                    if (f.getFeatureDetectionMethod() == null){
                                        f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                    }
                                }
                            }
                        }
                    }
                    else if (!inter.getExperimentRefs().isEmpty()){

                        for (ExperimentRef ref : inter.getExperimentRefs()){
                            ExperimentDescription desc = unmarshallExperimentById( ref.getRef() );

                            if (desc != null && desc.getFeatureDetectionMethod() != null){
                                for (Feature f : participant.getFeatures()){
                                    if (f.getFeatureDetectionMethod() == null){
                                        f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return interactions;
    }

    /**
     * Returns an Interaction collected from /entryset/entry/interactionList in the current entry by id.
     *
     * @return an Interaction
     * @throws PsimiXmlReaderException
     */
    public Interaction unmarshallInteractionById( int id ) throws PsimiXmlReaderException {
        Interaction interaction = null;
        final InputStreamRange isr = idIndex.getInteractionPosition( id );
        if ( isr != null ) {
            final IndexElementAdapter indexElement = new IndexElementAdapter( isr );
            final PsimiXmlPullParser parser = psimiXmlPullParser;
            interaction = parser.parseInteraction( getXmlSnippet( indexElement ) );

            for ( Participant participant : interaction.getParticipants() ) {
                if (participant.getParticipantIdentificationMethods().isEmpty()){
                    if (!interaction.getExperiments().isEmpty()){
                        for (ExperimentDescription desc : interaction.getExperiments()){
                            participant.getParticipantIdentificationMethods().add(desc.getParticipantIdentificationMethod());

                            if (desc.getFeatureDetectionMethod() != null){
                                for (Feature f : participant.getFeatures()){
                                    if (f.getFeatureDetectionMethod() == null){
                                        f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                    }
                                }
                            }
                        }
                    }
                    else if (!interaction.getExperimentRefs().isEmpty()){

                        for (ExperimentRef ref : interaction.getExperimentRefs()){
                            ExperimentDescription desc = unmarshallExperimentById( ref.getRef() );
                            if (desc != null){
                                participant.getParticipantIdentificationMethods().add(desc.getParticipantIdentificationMethod());

                                if (desc.getFeatureDetectionMethod() != null){
                                    for (Feature f : participant.getFeatures()){
                                        if (f.getFeatureDetectionMethod() == null){
                                            f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    if (!interaction.getExperiments().isEmpty()){
                        for (ExperimentDescription desc : interaction.getExperiments()){
                            if (desc.getFeatureDetectionMethod() != null){
                                for (Feature f : participant.getFeatures()){
                                    if (f.getFeatureDetectionMethod() == null){
                                        f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                    }
                                }
                            }
                        }
                    }
                    else if (!interaction.getExperimentRefs().isEmpty()){

                        for (ExperimentRef ref : interaction.getExperimentRefs()){
                            ExperimentDescription desc = unmarshallExperimentById( ref.getRef() );

                            if (desc != null && desc.getFeatureDetectionMethod() != null){
                                for (Feature f : participant.getFeatures()){
                                    if (f.getFeatureDetectionMethod() == null){
                                        f.setFeatureDetectionMethod(desc.getFeatureDetectionMethod());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return interaction;
    }

    /**
     * Returns a non iterator over the Interactions of the current entry.
     *
     * @return a non null Iterator of Interaction
     * @throws PsimiXmlReaderException
     */
    public Iterator<Interaction> unmarshallInteractionIterator() throws PsimiXmlReaderException {
        List<IndexElement> ranges = xpathIndex.getElements( PsiMi25ElementXpath.INTERACTION );
        ranges = IndexElementUtils.filterRanges( entryIndexElement, ranges );
        return new InteractionIterator( idIndex,
                                        buildInputStreamRanges( ranges ),
                                        xmlDataFile, psimiXmlPullParser );
    }

    /**
     * Returns a non null List of Attribute collected from /entryset/entry/attributeList in the current entry.
     *
     * @return a non null List of Attribute
     * @throws PsimiXmlReaderException
     */
    public List<Attribute> unmarshallAttributeList() throws PsimiXmlReaderException {
        List<IndexElement> ranges = xpathIndex.getElements( PsiMi25ElementXpath.ATTRIBUTE );
        ranges = IndexElementUtils.filterRanges( entryIndexElement, ranges );

        List<Attribute> attributes = new ArrayList<Attribute>( ranges.size() );
        final PsimiXmlPullParser parser = psimiXmlPullParser;
        for ( IndexElement range : ranges ) {
            attributes.add( parser.parseAttribute( getXmlSnippet( range ) ) );
        }
        return attributes;
    }

    public long getParticipantLineNumber( int id ) {
        InputStreamRange range = idIndex.getParticipantPosition( id );
        return range.getLineNumber();
    }

    public long getFeatureLineNumber( int id ) {
        InputStreamRange range = idIndex.getFeaturePosition( id );
        return range.getLineNumber();
    }

    //////////////////
    // Utilities

    private FileInputStream getFileInputStream() throws PsimiXmlReaderException {
        try {
            return new FileInputStream( xmlDataFile );
        } catch ( FileNotFoundException e ) {
            throw new PsimiXmlReaderException( e );
        }
    }

    /**
     * Given a range, extract the correcponding XML snippet.
     *
     * @param indexElement the range we want to convert into an InputStream
     * @return the input stream corresponding to the range given.
     * @throws PsimiXmlReaderException
     */
    private InputStream getXmlSnippet( IndexElement indexElement ) throws PsimiXmlReaderException {
        final InputStreamRange isr = new InputStreamRange();
        isr.setFromPosition( indexElement.getStart() );
        isr.setToPosition( indexElement.getStop() );
        try {
            return PsimiXmlExtractor.extractXmlSnippet( xmlDataFile, isr );
        } catch (IOException e) {
            throw new PsimiXmlReaderException("Error while extract XML snippet: " + indexElement, e);
        }
    }

    private List<InputStreamRange> buildInputStreamRanges( List<IndexElement> ranges ) {
        List<InputStreamRange> inputStreamRanges = new ArrayList<InputStreamRange>( ranges.size() );
        for ( IndexElement range : ranges ) {
            inputStreamRanges.add( new InputStreamRangeAdapter( range ) );
        }
        return inputStreamRanges;
    }

    public IndexElement getEntryIndexElement() {
        return entryIndexElement;
    }
}
