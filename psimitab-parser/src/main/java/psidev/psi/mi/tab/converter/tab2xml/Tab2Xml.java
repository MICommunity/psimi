/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.tab.converter.tab2xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.xml2tab.InteractionConverter;
import psidev.psi.mi.tab.converter.xml2tab.InteractorConverter;
import psidev.psi.mi.tab.converter.xml2tab.MitabInteractionConverter;
import psidev.psi.mi.tab.converter.xml2tab.NullCrossReference;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.xml.model.*;

import java.util.*;

/**
 * Utility class allowing to convert MITAB25 data into PSI-MI XML.
 *
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version $Id$
 */

public class Tab2Xml {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( Tab2Xml.class );

    /**
     * Interaction Converter
     */
    private InteractionConverter<?> interactionConverter;

    /**
     * Main entry which will add to EntrySet.
     */
    private Entry entry;

    /**
     * Collection of all Interactions.
     */
    private Collection<Interaction> interactions;

    /**
     * Collection of Interactors.
     */
    private Collection<Interactor> interactors = new ArrayList<Interactor>();

    /**
     * Source Reference.
     */
    private Xref sourceXref = null;

    /**
     * Strategy defining which interactor name used.
     */
    private InteractorNameBuilder interactorNameBuilder;

    protected static final String IREFINDEX = "irefindex";

    public Tab2Xml() {
        this( new MitabInteractionConverter() );
    }

    public Tab2Xml( InteractionConverter<?> interactionConverter ) {
        this.interactionConverter = interactionConverter;
    }

    /**
     * Getter for property 'interactorNameBuilder'
     */
    public InteractorNameBuilder getInteractorNameBuilder() {
        return interactorNameBuilder;
    }

    /**
     * Setter for property 'interactorNameBuilder'
     */
    public void setInteractorNameBuilder( InteractorNameBuilder interactorNameBuilder ) {
        this.interactorNameBuilder = interactorNameBuilder;
    }

    public Collection<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions( Collection<Interaction> interactions ) {
        this.interactions = interactions;
    }

    public InteractionConverter<?> getInteractionConverter() {
        return interactionConverter;
    }

    public void setInteractionConverter( InteractionConverter interactionConverter ) {
        this.interactionConverter = interactionConverter;
    }

    public Collection<Interactor> getInteractors() {
        return interactors;
    }

    public void setInteractors( Collection<Interactor> interactors ) {
        this.interactors = interactors;
    }

    /**
     * This method create a Map which is used to find the ExperimentalRole (bait,prey or unspecified)
     * key = InteractionID and value = tab.model.InteractorPair(s)
     *
     * @param miTabInteractions
     * @return
	 * @throws IllegalAccessException
     */
    public Map<String, Collection<Participant>> createInteractionMap(Collection<BinaryInteraction> miTabInteractions) throws IllegalAccessException, XmlConversionException{
        InteractorConverter<?> interactorConverter = interactionConverter.getInteractorConverter();

        Map<String, Collection<Participant>> interactionMap = new HashMap<String, Collection<Participant>>();
        interactorConverter.setInteractorNameBuilder( getInteractorNameBuilder() );

        for ( BinaryInteraction<?> binaryInteraction : miTabInteractions ) {
            int index = 0;
            final List<CrossReference> crossReferences = binaryInteraction.getInteractionAcs();
            List<CrossReference> filteredCrossReferences = filterCrossReferences(crossReferences);

            if ( filteredCrossReferences.isEmpty() ) {
                filteredCrossReferences.add( new NullCrossReference( binaryInteraction ) );
            }

            for ( CrossReference ref : filteredCrossReferences ) {
                String interactionId = ref.getIdentifier();

                if ( interactionId == null ) {
                    interactionId = binaryInteraction.getInteractorA().getIdentifiers().iterator().next()
                                    + "_" + binaryInteraction.getInteractorB().getIdentifiers().iterator().next();
                }

                Interactor iA = interactorConverter.fromMitab( binaryInteraction.getInteractorA() );
                Interactor iB = interactorConverter.fromMitab( binaryInteraction.getInteractorB() );

                // reusing the interactor in the participants
                iA = checkInteractor(iA);
                iB = checkInteractor(iB);

                Participant pA = interactorConverter.buildParticipantA(iA, binaryInteraction, index);
                Participant pB = interactorConverter.buildParticipantB(iB, binaryInteraction, index);

                index++;

                Collection<Participant> participants = null;
                if ( !interactionMap.containsKey( interactionId ) ) {
                    participants = new ArrayList<Participant>();
                } else {
                    participants = interactionMap.get( interactionId );
                }

                if ( !participants.contains( pA ) ) {
                    participants.add( pA );
                }
                if ( !participants.contains( pB ) ) {
                    participants.add( pB );
                }

                interactionMap.put( interactionId, participants );
            }
        }
        return interactionMap;
    }

     //quick fix for tomorrows presentation...remove later
     private List<CrossReference> filterCrossReferences( List<CrossReference> crossReferences ) {

        List<CrossReference> filteredXrefs = new ArrayList<CrossReference>();
        for ( CrossReference crossReference : crossReferences ) {
            if (! IREFINDEX.equals( crossReference.getDatabase() ) ) {
                filteredXrefs.add( crossReference );
            }
        }
        return filteredXrefs;
    }

    private psidev.psi.mi.xml.model.Interactor checkInteractor( psidev.psi.mi.xml.model.Interactor interactor1 ) {
        psidev.psi.mi.xml.model.Interactor interactor = interactor1;

        for ( psidev.psi.mi.xml.model.Interactor interactor2 : interactors ) {

            if ( interactor1.getNames().equals( interactor2.getNames() ) &&
                 interactor1.getXref().equals( interactor2.getXref() ) ) {
                interactor = interactor2;
                break;
            }

        }
        if ( interactor.equals( interactor1 ) ) {
            if ( !interactors.contains( interactor1 ) ) {
                interactors.add( interactor );
            }
        }

        return interactor;
    }

    /**
     * This method create a new Source of the binaryInteraction data.
     *
     * @return source
     */
    private Source getSource() {

        Source source = null;

        if ( sourceXref != null ) {
            source = new Source();

            // set default release Date
            Date date = new Date();
            source.setReleaseDate( date );

            // set Source name
            Names names = new Names();
            names.setShortLabel( "European Bioinformatics Institute" );
            source.setNames( names );

            // set Source AttributeList
            Collection<Attribute> attributes = new ArrayList<Attribute>();
            attributes.add( new Attribute( "postalAddress", "Wellcome Trust Genome Campus, Hinxton, Cambridge, CB10 1SD, United Kingdom" ) );
            attributes.add( new Attribute( "url", "http://www.ebi.ac.uk" ) );
            source.getAttributes().addAll( attributes );

            // set Xref
            DbReference sourcePrimaryRef = sourceXref.getPrimaryRef();
            if ( sourcePrimaryRef != null ) {
                if ( "psi-mi".equals( sourcePrimaryRef.getDb() ) ) sourcePrimaryRef.setDbAc( "MI:0488" );
                sourcePrimaryRef.setRefType( "primary-reference" );
                sourcePrimaryRef.setRefTypeAc( "MI:0358" );
                sourceXref.setPrimaryRef( sourcePrimaryRef );
                source.setXref( sourceXref );
            }
        }

        return source;
    }

    /**
     * This method convert a Collection of BinaryInteractions to a EntrySet.
     *
     * @param miTabInteractions
     * @return EntrySet
     * @throws IllegalAccessException
     * @throws XmlConversionException
     */
    public EntrySet convert(Collection<BinaryInteraction> miTabInteractions) throws IllegalAccessException, XmlConversionException {

        if ( miTabInteractions.isEmpty() ) {
            throw new IllegalAccessException( "No miTabInteractions found." );
        }

        entry = new Entry();

        interactions = new ArrayList<Interaction>();

        Map<String, Collection<Participant>> interactionMap = createInteractionMap( miTabInteractions );

        for ( BinaryInteraction<?> binaryInteraction : miTabInteractions ) {

            interactions = interactionConverter.fromMitab( binaryInteraction, interactionMap );

            entry.getInteractions().addAll( interactions );

            // sourceXref is used to create the source of the entry
            if ( sourceXref == null ) {
                Collection<CrossReference> sourceReferences = binaryInteraction.getSourceDatabases();
                Iterator<CrossReference> iterator = sourceReferences.iterator();
                boolean first = true;
                while ( iterator.hasNext() ) {
                    CrossReference sourceReference = iterator.next();
                    sourceXref = new Xref();
                    String identifier = sourceReference.getDatabase().concat( ":".concat( sourceReference.getIdentifier() ) );
                    String database = null;
                    if ( identifier.equals( "MI:0469" ) ) {
                        database = "psi-mi";
                    }
                    if ( first == true && database != null ) {
                        sourceXref.setPrimaryRef( new DbReference( identifier, database ) );
                        first = false;
                    } else {
                        sourceXref.getSecondaryRef().add( new DbReference( identifier, database ) );
                    }
                }
            }
        }

        if ( getSource() != null ) {
            entry.setSource( getSource() );
        }

        Collection<Entry> entries = new ArrayList<Entry>();
        entries.add( entry );
        EntrySet entrySet = new EntrySet( entries, 2, 5, 3 );

        return entrySet;
    }
}