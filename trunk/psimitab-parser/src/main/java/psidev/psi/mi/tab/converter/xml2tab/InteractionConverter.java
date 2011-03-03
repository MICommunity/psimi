/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.IdentifierGenerator;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.tab.model.InteractionDetectionMethod;
import psidev.psi.mi.tab.model.InteractionType;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml.model.Confidence;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts an PSIMI XML Binary Interaction into PSIMITAB interaction.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public abstract class InteractionConverter<T extends BinaryInteraction<?>> {

    private static final Log log = LogFactory.getLog( InteractionConverter.class );

    /**
     * Interaction type converter.
     */
    private InteractionTypeConverter itConverter = new InteractionTypeConverter();

    /**
     * Interaction detection method converter.
     */
    private InteractionDetectionMethodConverter idmConverter = new InteractionDetectionMethodConverter();

    /**
     * Class converting an XML source into a Publication.
     */
    private PublicationConverter pubConverter = new PublicationConverter();

    /**
     * Override the alias source database (column 3/4 & 5/6).
     */
    private CrossReference overrideAliasSourceDatabase;

    /**
     * Source database for the Interaction Id (column 14).
     */
    private Collection<CrossReference> sourceDatabases;

    // identifies patterns like "AuthorName B (2002)", capturing the authorName and the year
    private Pattern FIRST_AUTHOR_REGEX = Pattern.compile("(\\w+(?:\\P{Ps}+)?)(?:\\((\\d{4})\\))?");

    protected static final String IREFINDEX = "irefindex";

    ////////////////////////
    // Getters and Setters

    /**
     * Getter for property 'sourceDatabase'.
     *
     * @return Value for property 'sourceDatabase'.
     */
    public Collection<CrossReference> getSourceDatabase() {
        if ( sourceDatabases == null ) {
            sourceDatabases = new ArrayList<CrossReference>();
        }
        return sourceDatabases;
    }

    public void addSourceDatabase( CrossReference sourceDatabase ) {
        if ( sourceDatabase == null ) {
            throw new IllegalArgumentException( "You must give a non null source database." );
        }
        getSourceDatabase().add( sourceDatabase );
    }

    /**
     * Setter for property 'overrideAliasSourceDatabase'.
     *
     * @param overrideAliasSourceDatabase Value to set for property 'overrideAliasSourceDatabase'.
     */
    public void setOverrideAliasSourceDatabase( CrossReference overrideAliasSourceDatabase ) {
        this.overrideAliasSourceDatabase = overrideAliasSourceDatabase;
    }

    //////////////////////
    // Convertion

    public BinaryInteraction toMitab( Interaction interaction ) throws TabConversionException {

        if( interaction.isNegative() ) {
            log.warn( "interaction (id:" + interaction.getId() + ") could not be converted to MITAB25 as it is negative." );
            return null;
        }

        if ( interaction.getParticipants().size() != 2 ) {
            log.warn( "interaction (id:" + interaction.getId() + ") could not be converted to MITAB25 as it does not have exactly 2 participants." );
            return null;
        }

        Iterator<Participant> pi = interaction.getParticipants().iterator();
        Participant pA = pi.next();
        Participant pB = pi.next();

        final Interactor interactorA = getInteractorConverter().toMitab(pA.getInteractor());
        final Interactor interactorB = getInteractorConverter().toMitab(pB.getInteractor());

        BinaryInteraction<?> bi = newBinaryInteraction( interactorA, interactorB );

        // first thing off, set the source. It can come from two places: the source-reference cross ref, and if not present, from the entry source
        if (interaction.hasXref()) {
            Collection<DbReference> sourceRefs = XrefUtils.searchByType( interaction.getXref(), "source reference", "MI:0685" );

            String text = null;

            for (Attribute attr : interaction.getAttributes()) {
                if ("source reference:label".equals(attr.getName())) {
                    text = attr.getValue();
                    break;
                }
            }

            for (DbReference sourceRef : sourceRefs) {
                String db = "unknown";

                if (sourceRef.getId() != null && sourceRef.getId().startsWith("MI:")) {
                    db = "psi-mi";
                }

                CrossReference xref = new CrossReferenceImpl(db, sourceRef.getId(), text);
                bi.getSourceDatabases().add(xref);
            }
        }

        if ( bi.getSourceDatabases().isEmpty() && sourceDatabases != null ) {
            bi.getSourceDatabases().addAll( sourceDatabases );    
        }

        // Interaction AC
        if ( interaction.hasXref() ) {

            // First, try to search by source-reference type.
            // If nothing is found: navigate through the source databases and,
            // if the refType or refTypeAc not available using searchByDatabase
            // but in some cases you will get more than one interactionAcs
            Collection<DbReference> interactionAcs = XrefUtils.searchByType( interaction.getXref(), "identity", "MI:0356" );

            // filter the identities by the source databases
            Iterator<DbReference> interactionAcsIter = interactionAcs.iterator();

            while (interactionAcsIter.hasNext()) {
                DbReference identityRef = interactionAcsIter.next();

                for ( CrossReference sourceDatabase : bi.getSourceDatabases() ) {
                    if (!sourceDatabase.getIdentifier().equals(identityRef.getDb()) &&
                            !sourceDatabase.getIdentifier().equals(identityRef.getDbAc())) {
                        interactionAcsIter.remove();
                        break;
                    }
                }
            }

            if (interactionAcs.isEmpty() && interaction.hasXref()) {
                interactionAcs.add(interaction.getXref().getPrimaryRef());
            }

            List<CrossReference> refs = new ArrayList<CrossReference>();

            for ( DbReference interactionAc : interactionAcs ) {
                refs.add( CrossReferenceFactory.getInstance().build( interactionAc.getDb(), interactionAc.getId() ) );
            }

            bi.getInteractionAcs().addAll( refs );
        }

        // interaction type
        if ( interaction.hasInteractionTypes() ) {
            List<InteractionType> types = new ArrayList<InteractionType>( interaction.getInteractionTypes().size() );

            for ( psidev.psi.mi.xml.model.InteractionType interactionType : interaction.getInteractionTypes() ) {

                psidev.psi.mi.tab.model.InteractionType type = itConverter.toMitab( interactionType );

                if ( type != null ) {
                    types.add( type );
                } else {
                    log.warn( "Failed to convert interaction type: " + interactionType );
                }
            }

            if ( !types.isEmpty() ) {
                bi.setInteractionTypes( types );
            }
        }

        // publication and interaction detection
        int expCount = interaction.getExperiments().size();
        if ( expCount > 0 ) {

            // init the interaction
            List<CrossReference> publications = new ArrayList<CrossReference>( expCount );
            bi.setPublications( publications );

            List<psidev.psi.mi.tab.model.InteractionDetectionMethod> detections =
                    new ArrayList<psidev.psi.mi.tab.model.InteractionDetectionMethod>( expCount );
            bi.setDetectionMethods( detections );

            for ( ExperimentDescription experiment : interaction.getExperiments() ) {

                // detection method
                InteractionDetectionMethod detection = idmConverter.toMitab( experiment.getInteractionDetectionMethod() );

                if ( detection != null ) {
                    bi.getDetectionMethods().add( detection );
                }

                // publication
                if ( experiment.getBibref() != null ) {
                    CrossReference pub = pubConverter.toMitab( experiment.getBibref() );
                    if ( pub != null ) {
                        bi.getPublications().add( pub );
                    }
                }

                // Note: authors are not stored in PSI-MI. skip this.
                if ( experiment.getAttributes() != null ) {
                    String authorName = "-";
                    String pubYear = "";

                    for ( Attribute attribute : experiment.getAttributes() ) {
                        if ( "author-list".equals(attribute.getName())) {
                            authorName = attribute.getValue();

                            if (authorName.contains(",")) {
                                authorName = authorName.split( " " )[0].concat(" et al");
                            }
                        } else if ("publication year".equals(attribute.getName())) {
                            pubYear = "("+attribute.getValue()+")";
                        }
                    }

                    String authorNameYear = authorName+" "+pubYear;

                    Author author = new AuthorImpl( authorNameYear.trim() );
                    bi.getAuthors().add( author );
                }
            }
        }

        // confidence scores
        for (Confidence confidence : interaction.getConfidences()) {
            final Unit unit = confidence.getUnit();

            String type = null;
            String value = confidence.getValue();
            String text = null;

            if (unit != null) {
                if (unit.getNames() != null) {
                    type = unit.getNames().getShortLabel();
                    text = unit.getNames().getFullName();
                }
            } else {
                type = "unknown";
            }

            //confidence.
            psidev.psi.mi.tab.model.Confidence tabConfidence = new ConfidenceImpl(type, value, text);
            bi.getConfidenceValues().add(tabConfidence);
        }

        return bi;
    }

    /**
     * Collection of all experiments for all binaryInteractions.
     */
    private Collection<ExperimentDescription> experimentList = new ArrayList<ExperimentDescription>();


    /**
     * Collection of all interaction of one binaryInteraction.
     */
    private Collection<Interaction> interactions = null;

    /////////////////////////////////////
    // Getters & Setters

    /**
     * @return a ExperimentDescription collection of all BinaryInteractions.
     */
    public Collection<ExperimentDescription> getExperimentList() {
        return experimentList;
    }

    /**
     * TODO comment this
     *
     * @param interactionAc
     * @param sourceReference
     * @return primaryReference of these interaction.
     */
    private DbReference getPrimaryRef( CrossReference interactionAc, CrossReference sourceReference ) {
        DbReference primaryRef = null;

        // set references
        if ( interactionAc != null ) {

            String id = interactionAc.getIdentifier();
            String db = interactionAc.getDatabase();

            primaryRef = new DbReference( id, db );

            if ( sourceReference != null ) {
                primaryRef.setDbAc( sourceReference.getIdentifier() );
            }

            primaryRef.setRefType( "identity" );
            primaryRef.setRefTypeAc( "MI:0356" );
        }
        return primaryRef;
    }

    /**
     * @param interaction
     * @return names of these interaction.
     */
    private Names getInteractionName( Interaction interaction ) {
        Names interactionName = null;
        Collection<Participant> participants = interaction.getParticipants();
        for ( Participant participant : participants ) {
            if ( interactionName != null ) {
                String shortLabel = interactionName.getShortLabel().concat( "-" );
                shortLabel = shortLabel.concat( participant.getInteractor().getNames().getShortLabel().split( "_" )[0] );
                interactionName.setShortLabel( shortLabel );
            }
            if ( interactionName == null ) {
                interactionName = new Names();
                interactionName.setShortLabel( participant.getInteractor().getNames().getShortLabel().split( "_" )[0] );
            }
        }
        if ( interactionName == null ) {
            log.warn( "Interaction don't have a name" );
        }
        return interactionName;
    }

    /**
     * @param binaryInteraction
     * @param index
     * @return experimentDescription of these Interaction.
     */
    private Collection<psidev.psi.mi.xml.model.ExperimentDescription> getExperimentDescriptions( T binaryInteraction, int index ) throws XmlConversionException {

        Collection<ExperimentDescription> experimentDescriptions = new ArrayList<ExperimentDescription>();

        ExperimentDescription experimentDescription = null;

        psidev.psi.mi.xml.model.Bibref bibref = null;
        if ( binaryInteraction.getPublications().size() <= index ) {
            log.warn( "Size of InteractionAcs is " + binaryInteraction.getInteractionAcs().size() + " but we have only " +
                      binaryInteraction.getPublications().size() + " publication(s)! -> We could not know which publication dependents on which InteractionAcs" );
        } else {
            CrossReference binaryPublication = binaryInteraction.getPublications().get( index );
            bibref = pubConverter.fromMitab( binaryPublication );
        }

        psidev.psi.mi.xml.model.InteractionDetectionMethod detectionMethod = null;
        if ( binaryInteraction.getDetectionMethods().size() <= index ) {
            log.warn( "Size of InteractionAcs is " + binaryInteraction.getInteractionAcs().size() + " but we have only " +
                      binaryInteraction.getDetectionMethods().size() + " detectionMethod(s)! -> We could not know which detectionMethode dependents on which InteractionAcs" );
        } else {
            InteractionDetectionMethod binaryDetectionMethod = binaryInteraction.getDetectionMethods().get( index );
            detectionMethod = idmConverter.fromMitab( binaryDetectionMethod, psidev.psi.mi.xml.model.InteractionDetectionMethod.class );
        }

        if ( bibref != null && detectionMethod != null ) {
            experimentDescription = new ExperimentDescription( bibref, detectionMethod );
            experimentDescription.setId(IdentifierGenerator.getInstance().nextId());
        }

        if ( index < binaryInteraction.getAuthors().size() ) {
            String firstAuthor = binaryInteraction.getAuthors().get( index ).getName();
            if( ! firstAuthor.equals( "-" ) ) {

                Names names = new Names();
                String shortLabel;

                Matcher matcher = FIRST_AUTHOR_REGEX.matcher(firstAuthor);

                if (matcher.matches()) {
                    firstAuthor = matcher.group(1).trim();

                    shortLabel = firstAuthor.split(" ")[0];

                    if (matcher.groupCount() > 1) {
                        String year = matcher.group(2);
                        shortLabel = shortLabel+"-"+year;

                        Attribute pubYear = new Attribute("publication year", year);
                        experimentDescription.getAttributes().add(pubYear);

                    }
                } else {
                    shortLabel = firstAuthor.split(" ")[0];
                }

                shortLabel = shortLabel.toLowerCase();

                names.setShortLabel( shortLabel );
                experimentDescription.setNames( names );
                Attribute authorList = new Attribute( "author-list", firstAuthor );

                if ( !experimentDescription.getAttributes().contains( authorList ) ) {
                    experimentDescription.getAttributes().add( authorList );
                }
            }
        }

        if ( !experimentDescriptions.contains( experimentDescription ) && experimentDescription != null ) {
            experimentDescriptions.add( experimentDescription );
        }

        return experimentDescriptions;
    }

    protected abstract BinaryInteraction newBinaryInteraction(Interactor interactorA, Interactor interactorB);

    public abstract InteractorConverter<?> getInteractorConverter();

    /**
     * TODO comment this
     *
     * @param binaryInteraction
     * @return a collection of InteractionTypes
     */
    private Collection<psidev.psi.mi.xml.model.InteractionType> getInteractionTypes( BinaryInteraction binaryInteraction ) {
        List<psidev.psi.mi.xml.model.InteractionType> types = null;

        if ( binaryInteraction.getInteractionTypes() != null ) {

            types = new ArrayList<psidev.psi.mi.xml.model.InteractionType>( binaryInteraction.getInteractionTypes().size() );
            List<InteractionType> tabInteractionTypes = binaryInteraction.getInteractionTypes();
                                
            for ( InteractionType intactType : tabInteractionTypes ) {
                psidev.psi.mi.xml.model.InteractionType type = null;
                try {
                    type = itConverter.fromMitab( intactType, psidev.psi.mi.xml.model.InteractionType.class );
                } catch ( XmlConversionException e ) {
                    e.printStackTrace();
                }

                if ( type != null ) {
                    if ( !types.contains( type ) ) {
                        types.add( type );
                    }
                } else {
                    log.warn( "Failed to convert interaction type: " + type );
                }
            }
        }
        return types;
    }

    public Collection<Interaction> fromMitab( BinaryInteraction<?> binaryInteraction, Map<String, Collection<Participant>> interactionMap ) throws IllegalAccessException, XmlConversionException {

        interactions = new ArrayList<Interaction>();

        Set<String> interactionAcs = new HashSet<String>();

        int numberOfInteraction = 1;
//        int index = 0;

        final List<CrossReference> acs = binaryInteraction.getInteractionAcs();

        if ( acs.isEmpty() ) {
            acs.add( new NullCrossReference( binaryInteraction ) );
        }

//        for ( int i = 0; i < acs.size(); i++ ) {
            CrossReference interactionAc = acs.get( 0 );
            String interactionId = interactionAc.getIdentifier();
            interactionId = interactionId + "_" + binaryInteraction.getInteractorA().getIdentifiers().iterator().next().getIdentifier()
                    + "_" + binaryInteraction.getInteractorB().getIdentifiers().iterator().next().getIdentifier();

            if ( !interactionAcs.contains( interactionId ) ) {
                interactionAcs.add( interactionId );

                CrossReference source = binaryInteraction.getSourceDatabases().get( 0 );

                DbReference primaryReference = getPrimaryRef( interactionAc, source );
                Interaction interaction = new psidev.psi.mi.xml.model.Interaction();
                interaction.setId( IdentifierGenerator.getInstance().nextId() );
                interaction.setXref( new Xref( primaryReference ) );

                // database source
                for (CrossReference sourceXref : binaryInteraction.getSourceDatabases()) {
                    String id = sourceXref.getIdentifier();

                    String refDbMi = null;
                    String refDb = null;

                    if (id.startsWith("MI:")) {
                        refDbMi = "MI:0488";
                        refDb = "psi-mi";
                    }

                    DbReference sourceReference = new DbReference(refDb, refDbMi, id, "source reference", "MI:0685");
                    interaction.getXref().getSecondaryRef().add(sourceReference);

                    if (sourceXref.hasText()) {
                        interaction.getAttributes().add(new Attribute("source reference:label", sourceXref.getText()));
                    }

                }

                // set participants
                if ( interactionMap.get( interactionId ).size() > 1 ) {
                    for ( Participant participant : interactionMap.get( interactionId ) ) {
                        interaction.getParticipants().add( participant );
                    }
                }

                // set interaction name
                if ( getInteractionName( interaction ) != null ) {
                    Names interactionName = getInteractionName( interaction );
                    String shortLabel = interactionName.getShortLabel().concat( "-".concat( String.valueOf( numberOfInteraction ) ) );
                    numberOfInteraction++;
                    interactionName.setShortLabel( shortLabel );
                    interaction.setNames( interactionName );
                }

                // set interaction Type
                if ( !getInteractionTypes( binaryInteraction ).isEmpty() ) {
                    interaction.getInteractionTypes().addAll( getInteractionTypes( binaryInteraction ) );
                }

                // set experiment List
                Collection<ExperimentDescription> experiments = getExperimentDescriptions( (T) binaryInteraction, 0 );
                for ( ExperimentDescription experimentDescription : experiments ) {
                    interaction.getExperiments().add( experimentDescription );
                }

                // set confidences
                for (psidev.psi.mi.tab.model.Confidence tabConfidence : binaryInteraction.getConfidenceValues()) {
                    Unit unit = new Unit();
                    unit.setNames(new Names());
                    unit.getNames().setShortLabel(tabConfidence.getType());
                    if (tabConfidence.getText() != null) unit.getNames().setFullName(tabConfidence.getText());

                    Confidence confidence = new Confidence(unit, tabConfidence.getValue());
                    interaction.getConfidences().add(confidence);
                }

                populateInteractionFromMitab(interaction, binaryInteraction, 0);

                interactions.add( interaction );
            }

//            index++;
//        }

        return interactions;
    }

    protected abstract void populateInteractionFromMitab(Interaction interaction, BinaryInteraction<?> binaryInteraction, int index);
}