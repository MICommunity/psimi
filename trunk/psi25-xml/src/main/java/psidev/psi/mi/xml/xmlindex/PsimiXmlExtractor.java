package psidev.psi.mi.xml.xmlindex;

import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.xxindex.StandardXmlElementExtractor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Utility class allowing to retreive an element by id as well as resolving its references.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlExtractor {

    /**
     * PSI-MI XML pull parser.
     */
    PsimiXmlPullParser ppp;

    /**
     * Index indicating the location of referenceable PSI-MI element.
     */
    private PsimiXmlFileIndex index;

    //////////////////
    // Constructors

    public PsimiXmlExtractor( PsimiXmlFileIndex index, PsimiXmlPullParser psimiXmlPullParser ) {
        if ( index == null ) {
            throw new IllegalArgumentException( "You must give a non null index." );
        }
        this.index = index;
        ppp = psimiXmlPullParser;
    }

    //////////////////////
    // Object extractor

    /**
     * Experiment cache.
     */
    private Map<Integer, ExperimentDescription> experimentCache = new HashMap<Integer, ExperimentDescription>();

    /**
     * Convenience method to retreive an experiment. Experiments are cached as long as no-one clears the map.
     *
     * @param fis the data stream.
     * @param id  the identifier of the experiment we want.
     * @return an experiment.
     * @throws PsimiXmlReaderException
     */
    public ExperimentDescription getExperimentById( File fis, int id ) throws PsimiXmlReaderException {

        ExperimentDescription ed = experimentCache.get( id );
        if ( ed == null ) {
            // load it from file
            InputStreamRange range = index.getExperimentPosition( id );
            if ( range == null ) {
                throw new PsimiXmlReaderException( "Could not find a range in the index for experiment id:" + id );
            }
            InputStream eis = null;
            try {
                eis = extractXmlSnippet( fis, range );
            } catch (IOException e) {
                throw new PsimiXmlReaderException("Error while extracting XML snippet for experiment", e);
            }
            ed = ppp.parseExperiment( eis );

            // store in cache
            experimentCache.put( id, ed );
        }

        return ed;
    }

    public void clearExperimentCache() {
        experimentCache.clear();
    }

    /**
     * Convenience method to retreive an interactor.
     *
     * @param fis the data stream.
     * @param id  the identifier of the interactor we want.
     * @return an interactor.
     * @throws PsimiXmlReaderException
     */
    public Interactor getInteractorById( File fis, int id ) throws PsimiXmlReaderException {
        // load it from file
        InputStreamRange range = index.getInteractorPosition( id );
        if ( range == null ) {
            throw new PsimiXmlReaderException( "Could not find a range in the index for interactor id:" + id );
        }
        InputStream is = null;
        try {
            is = extractXmlSnippet( fis, range );
        } catch (IOException e) {
            throw new PsimiXmlReaderException("Error while extracting XML snippet for interactor", e);
        }
        return ppp.parseInteractor( is );
    }

    /**
     * Convenience method to retreive an feature.
     *
     * @param fis the data stream.
     * @param id  the identifier of the feature we want.
     * @return an feature.
     * @throws PsimiXmlReaderException
     */
    public Feature getFeatureById( File fis, int id ) throws PsimiXmlReaderException {
        // load it from file
        InputStreamRange range = index.getFeaturePosition( id );
        if ( range == null ) {
            throw new PsimiXmlReaderException( "Could not find a range in the index for feature id:" + id );
        }
        InputStream is = null;
        try {
            is = extractXmlSnippet( fis, range );
        } catch (IOException e) {
            throw new PsimiXmlReaderException("Error while extracting XML snippet for feature", e);
        }
        return ppp.parseFeature( is );
    }

    /**
     * Convenience method to retreive an participant.
     *
     * @param fis the data stream.
     * @param id  the identifier of the participant we want.
     * @return an participant.
     * @throws PsimiXmlReaderException
     */
    public Participant getParticipantById( File fis, int id ) throws PsimiXmlReaderException {
        // load it from file
        InputStreamRange range = index.getParticipantPosition( id );
        if ( range == null ) {
            throw new PsimiXmlReaderException( "Could not find a range in the index for participant id:" + id );
        }
        InputStream is = null;
        try {
            is = extractXmlSnippet( fis, range );
        } catch (IOException e) {
            throw new PsimiXmlReaderException("Error while extracting XML snippet for participant", e);
        }
        return ppp.parseParticipant( is );
    }

    /**
     * Convenience method to retreive an interaction.
     *
     * @param fis the data stream.
     * @param id  the identifier of the interaction we want.
     * @return an interaction.
     * @throws PsimiXmlReaderException
     */
    public Interaction getInteractionById( File fis, int id ) throws PsimiXmlReaderException {
        // load it from file
        InputStreamRange range = index.getInteractionPosition( id );
        if ( range == null ) {
            throw new PsimiXmlReaderException( "Could not find a range in the index for interaction id:" + id );
        }
        InputStream is = null;
        try {
            is = extractXmlSnippet( fis, range );
        } catch (IOException e) {
            throw new PsimiXmlReaderException("Error while getting interaction by id: " + id, e);
        }
        return ppp.parseInteraction( is );
    }

    ////////////////////////
    // Reference resolver

    /**
     * Browse the given interaction and replaces all reference by the respective object.
     *
     * @param file         the file to read objects from.
     * @param interaction the interaction to update.
     * @throws PsimiXmlReaderException
     */
    public void resolveReferences( File file, Interaction interaction ) throws PsimiXmlReaderException {

        // TODO in order to resolve the references of an interaction we have to take into account the scope that is the current entry

        // experiments
        if ( interaction.hasExperimentRefs() ) {
            for ( Iterator<ExperimentRef> itex = interaction.getExperimentRefs().iterator(); itex.hasNext(); ) {
                ExperimentRef eref = itex.next();
                // retreive
                ExperimentDescription ed = getExperimentById( file, eref.getRef() );

                if (ed == null){
                    throw new PsimiXmlReaderException("The interaction "+interaction.getId()+" refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                            " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                            " all the interactions then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                }
                itex.remove();
                interaction.getExperiments().add( ed );
            }
        }

        for ( Participant p : interaction.getParticipants() ) {
            resolveReferences( file, p , interaction);
        }

        // inferred interaction's experiments
        if ( interaction.hasInferredInteractions() ) {
            for ( InferredInteraction ii : interaction.getInferredInteractions() ) {
                if ( ii.hasExperimentRefs() ) {
                    for ( Iterator<ExperimentRef> itex = ii.getExperimentRefs().iterator(); itex.hasNext(); ) {
                        ExperimentRef eref = itex.next();
                        if( ! interaction.getExperiments().isEmpty() ) {
                            ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, interaction);

                            if( ed != null ) {
                                ii.getExperiments().add(ed);
                            }else {
                                throw new PsimiXmlReaderException( "An interaction (id="+
                                        interaction.getId()+") contains an inferredInteraction which refers to experiment ref "+ eref.getRef() +"," +
                                        "however, this experiment isn't defined in this interaction." +
                                        " This is not a supported use of the PSI-MI XML format." );
                            }

                        } else {
                            ExperimentDescription ed = getExperimentById( file, eref.getRef() );
                            if (ed == null){
                                throw new PsimiXmlReaderException("The interaction "+interaction.getId()+" has an inferred interaction which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                        " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                        " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                            }

                            ii.getExperiments().add(ed);
                        }
                    }
                    ii.getExperimentRefs().clear();
                }

                for ( InferredInteractionParticipant iip : ii.getParticipant() ) {
                    if ( iip.hasFeatureRef() ) {
                        InputStreamRange r = index.getFeaturePosition( iip.getFeatureRef().getRef() );
                        InputStream pfis = null;
                        try {
                            pfis = extractXmlSnippet( file, r );
                        } catch (IOException e) {
                            throw new PsimiXmlReaderException("Error while extracting XML snippet for feature", e);
                        }
                        Feature ft = ppp.parseFeature( pfis );

                        if (ft == null){
                            throw new PsimiXmlReaderException("The interaction "+interaction.getId()+" has an inferred interaction participant which refers to the feature " + iip.getFeatureRef().getRef() + " but this feature cannot be found by the parser." +
                                    " Please check that you have described this feature previously in this interaction.");
                        }

                        iip.setFeatureRef( null );
                        iip.setFeature( ft );
                    }

                    if ( iip.hasParticipantRef() ) {
                        InputStreamRange r = index.getParticipantPosition( iip.getParticipantRef().getRef() );
                        InputStream pis = null;
                        try {
                            pis = extractXmlSnippet( file, r );
                        } catch (IOException e) {
                            throw new PsimiXmlReaderException("Error while extracting XML snippet for participant", e);
                        }
                        Participant p = ppp.parseParticipant( pis );

                        if (p == null){
                            throw new PsimiXmlReaderException("The interaction "+interaction.getId()+" has an inferred interaction which refers to the participant " + iip.getParticipantRef().getRef() + " but this participant cannot be found by the parser." +
                                    " Please check that you have described the participant in this interaction.");
                        }

                        iip.setParticipantRef( null );
                        iip.setParticipant( p );
                    }
                }
            }
        } // inferred interaction

        //confidence
        if (interaction.hasConfidences()){
            for (Confidence conf : interaction.getConfidences()){
                if (conf.hasExperimentRefs()){
                    for (ExperimentRef eref : conf.getExperimentRefs()){
                        if( !interaction.getExperiments().isEmpty() ) {
                            ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, interaction);

                            if( ed != null) {
                                conf.getExperiments().add( ed );
                            }
                            else {
                                throw new PsimiXmlReaderException( "The interaction (id="+
                                        interaction.getId()+") has a confidence which refers to experiment ref "+ eref.getRef() +"," +
                                        "however, this experiment isn't defined in this interaction." +
                                        " This is not a supported use of the PSI-MI XML format." );
                            }
                        }
                        else {
                            ExperimentDescription ed = getExperimentById( file, eref.getRef() );

                            if (ed == null){
                                throw new PsimiXmlReaderException("The interaction "+interaction.getId()+" has a confidence which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                        " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                        " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                            }
                            conf.getExperiments().add( ed );
                        }
                    }
                    conf.getExperimentRefs().clear();
                }
            }
        }

        // parameters
        if ( interaction.hasParameters() ) {
            for ( Parameter pm : interaction.getParameters() ) {
                if ( pm.hasExperimentRef() ) {
                    ExperimentRef eref = pm.getExperimentRef();

                    if( ! interaction.getExperiments().isEmpty() ) {
                        ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, interaction);

                        if( ed != null ) {
                            pm.setExperimentRef( null );
                            pm.setExperiment( ed );
                        }else {
                            throw new PsimiXmlReaderException( "A parameter ("+ pm.getTerm() +") defined in interaction (id="+
                                    interaction.getId()+") refers to experiment ref "+ eref.getRef() +"," +
                                    "however, this experiment isn't defined in this interaction." +
                                    " This is not a supported use of the PSI-MI XML format." );
                        }

                    } else {
                        ExperimentDescription ed = getExperimentById( file, eref.getRef() );

                        if (ed == null){
                            throw new PsimiXmlReaderException("The interaction "+interaction.getId()+" has a parameter which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                    " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                    " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                        }

                        pm.setExperimentRef( null );
                        pm.setExperiment( ed );
                    }
                }
            }
        }
    }

    /**
     * Retrieve an experiment description in an interaction using an experiment ref
     * @param eref : the experiment reference
     * @param i  : the interaction to look into
     * @return the experiment which has been found
     */
    private ExperimentDescription findExperimentDescriptionInInteraction(ExperimentRef eref, Interaction i){
        for ( ExperimentDescription ed : i.getExperiments() ) {
            if( ed.getId() == eref.getRef() ) {
                return ed;
            }
        }
        return null;
    }

    /**
     * Browse the given participant and replaces all reference by the respective object.
     *
     * @param fis         fis the file to read objects from.
     * @param participant the participant to update.
     * @throws PsimiXmlReaderException
     */
    public void resolveReferences( File fis, Participant participant, Interaction parentInteraction ) throws PsimiXmlReaderException {

        // interactors
        if ( participant.hasInteractionRef() ) {
            InteractionRef ref = participant.getInteractionRef();
            participant.setInteractionRef( null );

            if (parentInteraction != null){
                if (ref.getRef() == parentInteraction.getId()){
                    throw new PsimiXmlReaderException("The interaction " + ref.getRef() + " has a participant ("+participant.getId()+") which is an interaction and this interaction is referring to itself." +
                            " It is not a valid participant.");
                }

                Interaction i = getInteractionById(fis, ref.getRef());

                if (i == null){
                    throw new PsimiXmlReaderException("The participant "+participant.getId()+" refers to the interaction " + ref.getRef() + " but this interaction cannot be found by the parser. Please check that this interaction is described somewhere in the list of interactions.");
                }

                resolveReferences(fis, i);

                participant.setInteraction(i); // recursive call !!
            }
            else {
                throw new PsimiXmlReaderException("The  participant ("+participant.getId()+") doesn't have any interactions attached to it.");
            }
        }

        boolean hasInteractionExperimentDescription = false;

        if( parentInteraction != null && !parentInteraction.getExperiments().isEmpty() ) {
            hasInteractionExperimentDescription = true;
        }

        if ( participant.hasInteractorRef() ) {
            InteractorRef ref = participant.getInteractorRef();
            InputStreamRange irange = index.getInteractorPosition( ref.getRef() );
            InputStream iis = null;
            try {
                iis = extractXmlSnippet( fis, irange );
            } catch (IOException e) {
                throw new PsimiXmlReaderException("Error while extracting XML snippet for interactor", e);
            }
            Interactor interactor = ppp.parseInteractor( iis );

            if (interactor == null){
                throw new PsimiXmlReaderException("The participant "+participant.getId()+" refers to the interactor " + ref.getRef() + " but this interactior cannot be found by the parser. " +
                        "Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the interactors are described at the beginning of the file and" +
                        " all the participants then use references to pre-declared interactors or all the interactors are only described at the level of the participants and no interactor reference can be used.");
            }

            participant.setInteractorRef( null );
            participant.setInteractor( interactor );
        }

        if ( participant.hasParticipantIdentificationMethods() ) {

            for ( ParticipantIdentificationMethod pim : participant.getParticipantIdentificationMethods() ) {
                if ( pim.hasExperimentRefs() ) {

                    if( hasInteractionExperimentDescription && !parentInteraction.getExperiments().isEmpty() ) {
                        for ( ExperimentRef eref : pim.getExperimentRefs()) {

                            ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, parentInteraction);
                            if( ed != null) {
                                pim.getExperiments().add( ed );
                            }
                            else {
                                throw new PsimiXmlReaderException( "The participant ("+ participant.getId() +") defined in interaction (id="+
                                        parentInteraction.getId()+") has a participant identification method which refers to experiment ref "+ eref.getRef() +"," +
                                        "however, this experiment isn't defined in this interaction." +
                                        " This is not a supported use of the PSI-MI XML format." );
                            }
                        }
                        pim.getExperimentRefs().clear();
                    }
                    else {
                        for ( ExperimentRef eref : pim.getExperimentRefs()) {

                            ExperimentDescription ed = getExperimentById( fis, eref.getRef() );

                            if (ed == null){
                                throw new PsimiXmlReaderException("The participant "+participant.getId()+" has a participant identification method which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                        " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                        " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                            }
                            pim.getExperiments().add( ed );
                        }
                        pim.getExperimentRefs().clear();
                    }
                }
            }
        }

        if ( participant.hasExperimentalRoles() ) {
            for ( ExperimentalRole er : participant.getExperimentalRoles() ) {
                if ( er.hasExperimentRefs() ) {

                    if( hasInteractionExperimentDescription  && !parentInteraction.getExperiments().isEmpty() ) {
                        for ( ExperimentRef eref : er.getExperimentRefs()) {
                            ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, parentInteraction);
                            if( ed != null ) {
                                er.getExperiments().add( ed );
                            }
                            else {
                                throw new PsimiXmlReaderException( "The participant ("+ participant.getId() +") defined in interaction (id="+
                                        parentInteraction.getId()+") has an experimental role which refers to experiment ref "+ eref.getRef() +"," +
                                        "however, this experiment isn't defined in this interaction." +
                                        " This is not a supported use of the PSI-MI XML format." );
                            }
                        }
                        er.getExperimentRefs().clear();
                    }
                    else {
                        for ( ExperimentRef eref : er.getExperimentRefs()) {

                            ExperimentDescription ed = getExperimentById( fis, eref.getRef() );

                            if (ed == null){
                                throw new PsimiXmlReaderException("The participant "+participant.getId()+" has an experimental role which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                        " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                        " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                            }

                            er.getExperiments().add( ed );
                        }
                        er.getExperimentRefs().clear();
                    }
                }
            }
        }

        if ( participant.hasExperimentalPreparations() ) {
            for ( ExperimentalPreparation ep : participant.getExperimentalPreparations() ) {
                if ( ep.hasExperimentRefs() ) {

                    if( hasInteractionExperimentDescription  && !parentInteraction.getExperiments().isEmpty() ) {
                        for ( ExperimentRef eref : ep.getExperimentRefs()) {
                            ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, parentInteraction);

                            if( ed != null ) {
                                ep.getExperiments().add( ed );
                            }
                            else {
                                throw new PsimiXmlReaderException( "The participant ("+ participant.getId() +") defined in interaction (id="+
                                        parentInteraction.getId()+") has an experimental preparation which refers to experiment ref "+ eref.getRef() +"," +
                                        "however, this experiment isn't defined in this interaction." +
                                        " This is not a supported use of the PSI-MI XML format." );
                            }
                        }
                        ep.getExperimentRefs().clear();
                    }
                    else {
                        for ( ExperimentRef eref : ep.getExperimentRefs()) {

                            ExperimentDescription ed = getExperimentById( fis, eref.getRef() );

                            if (ed == null){
                                throw new PsimiXmlReaderException("The participant "+participant.getId()+" has an experimental preparation which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                        " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                        " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                            }
                            ep.getExperiments().add( ed );
                        }
                        ep.getExperimentRefs().clear();
                    }
                }
            }
        }

        if ( participant.hasExperimentalInteractors() ) {
            for ( ExperimentalInteractor ei : participant.getExperimentalInteractors() ) {
                if ( ei.hasExperimentRefs() ) {

                    if( hasInteractionExperimentDescription  && !parentInteraction.getExperiments().isEmpty() ) {
                        for ( ExperimentRef eref : ei.getExperimentRefs()) {
                            ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, parentInteraction);

                            if( ed != null ) {
                                ei.getExperiments().add( ed );
                            }
                            else {
                                throw new PsimiXmlReaderException( "The participant ("+ participant.getId() +") defined in interaction (id="+
                                        parentInteraction.getId()+") has an experimental interactor which refers to experiment ref "+ eref.getRef() +"," +
                                        "however, this experiment isn't defined in this interaction." +
                                        " This is not a supported use of the PSI-MI XML format." );
                            }
                        }
                        ei.getExperimentRefs().clear();
                    }
                    else {
                        for ( ExperimentRef eref : ei.getExperimentRefs()) {

                            ExperimentDescription ed = getExperimentById( fis, eref.getRef() );

                            if (ed == null){
                                throw new PsimiXmlReaderException("The participant "+participant.getId()+" has an experimental interactor which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                        " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                        " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                            }
                            ei.getExperiments().add( ed );
                        }
                        ei.getExperimentRefs().clear();
                    }
                }

                if ( ei.hasInteractorRef() ) {
                    InteractorRef ref = ei.getInteractorRef();
                    InputStreamRange irange = index.getInteractorPosition( ref.getRef() );
                    InputStream iis = null;
                    try {
                        iis = extractXmlSnippet( fis, irange );
                    } catch (IOException e) {
                        throw new PsimiXmlReaderException("Error while extracting XML snippet for interactor", e);
                    }
                    Interactor interactor = ppp.parseInteractor( iis );

                    if (interactor == null){
                        throw new PsimiXmlReaderException("The participant "+participant.getId()+" has an experimental interactor which refers to the interactor " + ref.getRef() + " but this interactor cannot be found by the parser." +
                                " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the interactors are described at the beginning of the file and" +
                                " all the interactions and participants then use references to pre-declared interactors or all the interactors are only described at the level of the participant and no interactor reference can be used.");
                    }
                    ei.setInteractorRef( null );
                    ei.setInteractor( interactor );
                }
            }
        }

        if ( participant.hasHostOrganisms() ) {
            for ( HostOrganism ho : participant.getHostOrganisms() ) {
                if ( ho.hasExperimentRefs() ) {
                    if( hasInteractionExperimentDescription  && !parentInteraction.getExperiments().isEmpty() ) {
                        for ( ExperimentRef eref : ho.getExperimentRefs()) {
                            ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, parentInteraction);

                            if( ed != null ) {
                                ho.getExperiments().add( ed );
                            }
                            else {
                                throw new PsimiXmlReaderException( "The participant ("+ participant.getId() +") defined in interaction (id="+
                                        parentInteraction.getId()+") has an organism which refers to experiment ref "+ eref.getRef() +"," +
                                        "however, this experiment isn't defined in this interaction." +
                                        " This is not a supported use of the PSI-MI XML format." );
                            }
                        }
                        ho.getExperimentRefs().clear();
                    }
                    else {
                        for ( ExperimentRef eref : ho.getExperimentRefs()) {

                            ExperimentDescription ed = getExperimentById( fis, eref.getRef() );

                            if (ed == null){
                                throw new PsimiXmlReaderException("The participant "+participant.getId()+" has a host organism which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                        " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                        " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                            }

                            ho.getExperiments().add( ed );
                        }
                        ho.getExperimentRefs().clear();
                    }
                }
            }
        }

        if ( participant.hasConfidences() ) {
            for ( Confidence c : participant.getConfidenceList() ) {
                if ( c.hasExperimentRefs() ) {
                    if( hasInteractionExperimentDescription  && !parentInteraction.getExperiments().isEmpty() ) {
                        for ( ExperimentRef eref : c.getExperimentRefs()) {
                            ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, parentInteraction);

                            if( ed != null ) {
                                c.getExperiments().add( ed );
                            }
                            else {
                                throw new PsimiXmlReaderException( "The participant ("+ participant.getId() +") defined in interaction (id="+
                                        parentInteraction.getId()+") has a confidence which refers to experiment ref "+ eref.getRef() +"," +
                                        "however, this experiment isn't defined in this interaction." +
                                        " This is not a supported use of the PSI-MI XML format." );
                            }
                        }
                        c.getExperimentRefs().clear();
                    }
                    else {
                        for ( ExperimentRef eref : c.getExperimentRefs()) {

                            ExperimentDescription ed = getExperimentById( fis, eref.getRef() );

                            if (ed == null){
                                throw new PsimiXmlReaderException("The participant "+participant.getId()+" has a confidence score which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                        " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                        " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                            }
                            c.getExperiments().add( ed );
                        }
                        c.getExperimentRefs().clear();
                    }
                }
            }
        }

        if ( participant.hasParameters() ) {
            for ( Parameter pm : participant.getParameters() ) {
                if ( pm.hasExperimentRef() ) {
                    ExperimentRef eref = pm.getExperimentRef();
                    if( hasInteractionExperimentDescription  && !parentInteraction.getExperiments().isEmpty() ) {
                        ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, parentInteraction);

                        if( ed != null ) {
                            pm.setExperiment(ed);
                            pm.setExperimentRef(null);
                        }
                        else {
                            throw new PsimiXmlReaderException( "The participant ("+ participant.getId() +") defined in interaction (id="+
                                    parentInteraction.getId()+") has a parameter which refers to experiment ref "+ eref.getRef() +"," +
                                    "however, this experiment isn't defined in this interaction." +
                                    " This is not a supported use of the PSI-MI XML format." );
                        }
                    }
                    else {
                        ExperimentDescription ed = getExperimentById( fis, eref.getRef() );
                        pm.setExperiment( ed );

                        if (ed == null){
                            throw new PsimiXmlReaderException("The participant "+participant.getId()+" has a parameter which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                    " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                    " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                        }

                        pm.setExperimentRef(null);
                    }
                }
            }
        }

        if ( participant.hasFeatures() ) {
            for ( Feature feature : participant.getFeatures() ) {
                if ( feature.hasExperimentRefs() ) {
                    if( hasInteractionExperimentDescription  && !parentInteraction.getExperiments().isEmpty() ) {
                        for ( ExperimentRef eref : feature.getExperimentRefs()) {
                            ExperimentDescription ed = findExperimentDescriptionInInteraction(eref, parentInteraction);

                            if( ed != null ) {
                                feature.getExperiments().add( ed );
                            }
                            else {
                                throw new PsimiXmlReaderException( "The feature ("+ feature.getId() +") defined in the participant (id="+ participant.getId() +") of the interaction (id="+
                                        parentInteraction.getId()+") refers to experiment ref "+ eref.getRef() +"," +
                                        "however, this experiment isn't defined in this interaction." +
                                        " This is not a supported use of the PSI-MI XML format." );
                            }
                        }
                        feature.getExperimentRefs().clear();
                    }
                    else {
                        for ( ExperimentRef eref : feature.getExperimentRefs()) {

                            ExperimentDescription ed = getExperimentById( fis, eref.getRef() );

                            if (ed == null){
                                throw new PsimiXmlReaderException("The participant "+participant.getId()+" has a feature which refers to the experiment " + eref.getRef() + " but this experiment cannot be found by the parser." +
                                        " Please check that you are not mixing the expanded PSI xml schema and the compact PSI xml schema together. Either all the experiments are described at the beginning of the file and" +
                                        " all the interactions and participants then use references to pre-declared experiments or all the experiments are only described at the level of the interactions and no experiment reference can be used.");
                            }

                            feature.getExperiments().add( ed );
                        }
                        feature.getExperimentRefs().clear();
                    }
                }
            }
        }
    }

/////////////////////
// Stream extractor

    /**
     * Extract the range of chars describe by the given range on the stream and build an other one solely returning
     * that content.
     *
     * @param file   the file we are reading the snippet from
     * @param range the byte range to extract using random access
     * @return an input stream representing the requested byte range on fis.
     * @throws psidev.psi.mi.xml.PsimiXmlReaderException
     *
     */
    public static InputStream extractXmlSnippet( File file, InputStreamRange range) throws IOException {

        if( range == null ) {
            throw new IllegalArgumentException( "You must give a non null InputStreamRange." );
        }

        StandardXmlElementExtractor xee = new StandardXmlElementExtractor();
        byte[] bytes = xee.readBytes(range.getFromPosition(), range.getToPosition(), file);
        return new ByteArrayInputStream( bytes );
    }


//    public static InputStream extractXmlSnippet( FileInputStream fis,
//                                                 InputStreamRange range ) throws PsimiXmlReaderException {
//
//        if( range == null ) {
//            throw new IllegalArgumentException( "You must give a non null InputStreamRange." );
//        }
//
//        try {
//            // Calculate how many char do we need to read.
//            int charCount = ( int ) ( range.getToPosition() - range.getFromPosition() );
//
//            // Position the stream
//            fis.getChannel().position( range.getFromPosition() );
//            InputStreamReader isr = new InputStreamReader( fis );
//            char[] buf = new char[charCount];
//            int read = isr.read( buf, 0, charCount );
//
//            // Build an InputStream with this
//            StringBuilder sb = new StringBuilder( charCount );
//            for ( int i = 0; i < buf.length; i++ ) {
//                char c = buf[i];
//                sb.append( c );
//            }
//
//            System.out.println("XML DATA:\n"+sb.toString());
//
//            return new ByteArrayInputStream( sb.toString().getBytes() );
//        } catch ( IOException e ) {
//            throw new PsimiXmlReaderException( "An error occured while extracting XML element in " + range, e );
//        }
//    }
}