package psidev.psi.mi.validator.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.ValidatorReport;
import psidev.psi.mi.validator.extension.rules.PsimiXmlSchemaRule;
import psidev.psi.mi.xml.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.model.*;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;
import psidev.psi.tools.cvrReader.mapping.jaxb.CvMapping;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.*;
import psidev.psi.tools.validator.preferences.UserPreferences;
import psidev.psi.tools.validator.rules.Rule;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;
import psidev.psi.tools.validator.schema.SaxMessage;
import psidev.psi.tools.validator.schema.SaxReport;
import psidev.psi.tools.validator.schema.SaxValidatorHandler;

import java.io.*;
import java.util.*;

/**
 * <b> PSI-MI 2.5.2 Specific Validator </b>.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class Mi25Validator extends Validator {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( Mi25Validator.class );

    /**
     * Plateform specific break line.
     */
    public static final String NEW_LINE = System.getProperty( "line.separator" );

    /**
     * counter used for creating unique IDs.
     * <p/>
     * These are used to create temp files.
     */
    private long uniqId = 0;

    private UserPreferences userPreferences;

    /**
     * Creates a MI 25 validator with the default user userPreferences.
     *
     * @param ontologyconfig   configuration for the ontologies.
     * @param cvMappingConfig  configuration for the cv mapping rules.
     * @param objectRuleConfig configuration for the object rules.
     * @throws ValidatorException
     */
    public Mi25Validator( InputStream ontologyconfig,
                          InputStream cvMappingConfig,
                          InputStream objectRuleConfig ) throws ValidatorException, OntologyLoaderException {

        super( ontologyconfig, cvMappingConfig, objectRuleConfig );
    }

    public Mi25Validator( OntologyManager ontologyManager,
                          CvMapping cvMapping,
                          Collection<ObjectRule> objectRules) {
        super( ontologyManager, cvMapping, objectRules);
    }

    ///////////////////////////
    // Getters and Setters

    public void setUserPreferences( UserPreferences userPreferences ) {
        this.userPreferences = userPreferences;
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    //////////////////////////
    // Utility

    /**
     * Return a unique ID.
     *
     * @return a unique id.
     */
    synchronized private long getUniqueId() {
        return ++uniqId;
    }

    /**
     * Store the content of the given input stream into a temporary file and return its descriptor.
     *
     * @param is the input stream to store.
     * @return a File descriptor describing a temporary file storing the content of the given input stream.
     * @throws IOException if an IO error occur.
     */
    private File storeAsTemporaryFile( InputStream is ) throws IOException {

        if ( is == null ) {
            throw new IllegalArgumentException( "You must give a non null InputStream" );
        }

        BufferedReader in = new BufferedReader( new InputStreamReader( is ) );

        // Create a temp file and write URL content in it.
        File tempDirectory = new File( System.getProperty( "java.io.tmpdir", "tmp" ) );
        if ( !tempDirectory.exists() ) {
            if ( !tempDirectory.mkdirs() ) {
                throw new IOException( "Cannot create temp directory: " + tempDirectory.getAbsolutePath() );
            }
        }

        long id = getUniqueId();

        File tempFile = File.createTempFile( "validator." + id, ".xml", tempDirectory );
        tempFile.deleteOnExit();

        log.info( "The file is temporary store as: " + tempFile.getAbsolutePath() );

        BufferedWriter out = new BufferedWriter( new FileWriter( tempFile ) );

        String line;
        while ( ( line = in.readLine() ) != null ) {
            out.write( line );
        }

        in.close();

        out.flush();
        out.close();


        return tempFile;
    }

    /////////////////////////////
    // Validator

    public ValidatorReport validate( File file ) throws ValidatorException {
        final ValidatorReport report = new ValidatorReport();

        try {
            // the given inputStream should not be used anymore.
            InputStream nis = new FileInputStream( file );

            // 1. Validate XML input using Schema (MIF)
            if ( userPreferences.isSaxValidationEnabled() ) {
                if (false == runSyntaxValidation(report, nis)) {
                    return report;
                }
            }

            nis = new FileInputStream( file );

            runSemanticValidation(report, nis);

            return report;

        } catch ( Exception e ) {
            throw new ValidatorException( "Unable to process input file:" + file.getAbsolutePath(), e );
        }
    }

    /**
     * Validates a PSI-MI 2.5 InputStream.
     *
     * @param is the input stream to validate.
     * @return a collection of messages
     * @throws ValidatorException
     */
    public ValidatorReport validate( InputStream is ) throws ValidatorException {

        final ValidatorReport report = new ValidatorReport();

        try {
            File tempFile = storeAsTemporaryFile( is );

            // the given inputStream should not be used anymore.
            InputStream nis = new FileInputStream( tempFile );

            // 1. Validate XML input using Schema (MIF)
            if ( userPreferences.isSaxValidationEnabled() ) {
                if (false == runSyntaxValidation(report, nis)) {
                    return report;
                }
            }

            nis = new FileInputStream( tempFile );

            runSemanticValidation(report, nis);

            return report;

        } catch ( Exception e ) {
            throw new ValidatorException( "Unable to process input stream", e );
        }
    }

    /**
     * Validates a PSI-MI 2.5 EntrySet.
     *
     * @param es the entrySet to validate.
     * @return a collection of messages
     * @throws ValidatorException
     */
    public ValidatorReport validate( EntrySet es ) throws ValidatorException {

        final ValidatorReport report = new ValidatorReport();

        for ( Entry entry : es.getEntries() ) {
            boolean hasExperimentList = false;
            boolean hasInteractorList = false;

            if (entry.hasExperiments()){
                hasExperimentList = true;
                for ( ExperimentDescription experiment : entry.getExperiments() ) {

                    // cv mapping
                    Collection<ValidatorMessage> messages = checkCvMapping( experiment, "/entrySet/entry/experimentList/experimentDescription/" );

                    if( ! messages.isEmpty() ){
                        checkExperiment(messages, experiment);
                    }
                    else{
                        checkExperiment(messages, experiment);
                    }

                    report.getSemanticMessages().addAll(messages);
                }
            }

            if (entry.hasInteractors()){
                hasInteractorList = true;
                for ( Interactor interactor : entry.getInteractors() ) {
                    // cv mapping
                    Collection<ValidatorMessage> messages = checkCvMapping( interactor, "/entrySet/entry/interactorList/interactor/" );

                    if( ! messages.isEmpty() ){
                        checkInteractor(messages, interactor);
                    }
                    else{
                        checkInteractor(messages, interactor);
                    }

                    report.getSemanticMessages().addAll(messages);
                }
            }

            for ( Interaction interaction : entry.getInteractions() ) {

                // cv mapping
                Collection<ValidatorMessage> messages = checkCvMapping( interaction, "/entrySet/entry/interactionList/interaction/" );
                if( ! messages.isEmpty() )
                    messages = convertToMi25Messages( messages, interaction );

                // object rule
                checkInteraction(messages, interaction, hasExperimentList, hasInteractorList);

                report.getSemanticMessages().addAll(messages);
            }
        }

        // cluster all messages!!
        Collection<ValidatorMessage> clusteredValidatorMessages = clusterByMessagesAndRules(report.getSemanticMessages());
        report.setSemanticMessages(clusteredValidatorMessages);

        return report;
    }

    //////////////////////////////
    // Private

    /**
     * Runs the semantic validation and append messages to the given report.
     *
     * @param report the report to be completed.
     * @param nis the data stream to be validated.
     * @throws PsimiXmlReaderException
     * @throws ValidatorException
     */
    private void runSemanticValidation(ValidatorReport report, InputStream nis) throws PsimiXmlReaderException, ValidatorException {

        // Build the collection of messages in which we will accumulate the output of the validator
        Collection<ValidatorMessage> messages = report.getSemanticMessages();

        List<IndexedEntry> entries = null;

        try {
            // then run the object rules (if any)
            final Set<ObjectRule> rules = getObjectRules();
            if ( (rules != null && !rules.isEmpty()) || getCvRuleManager() != null) {
                if( entries == null ) {
                    PsimiXmlLightweightReader psiReader = new PsimiXmlLightweightReader( nis );
                    entries = psiReader.getIndexedEntries();
                }

                for ( IndexedEntry entry : entries ) {
                    boolean hasExperimentList = false;
                    boolean hasInteractorList = false;

                    final Iterator<ExperimentDescription> experimentIterator = entry.unmarshallExperimentIterator();

                    if (experimentIterator.hasNext()){
                        hasExperimentList = true;

                        while ( experimentIterator.hasNext() ) {
                            ExperimentDescription experiment = experimentIterator.next();

                            // check using cv mapping rules
                            Collection<ValidatorMessage> validatorMessages =
                                    super.checkCvMapping( experiment, "/entrySet/entry/experimentList/experimentDescription/" );

                            if (validatorMessages != null){
                                validatorMessages = convertToMi25Messages( validatorMessages, experiment );

                                validatorMessages.addAll(checkExperiment(validatorMessages, experiment));
                            }
                            else {
                                validatorMessages = checkExperiment(validatorMessages, experiment);
                            }

                            if( !validatorMessages.isEmpty() ) {
                                long lineNumber = entry.getExperimentLineNumber( experiment.getId() );
                                updateLineNumber( validatorMessages, lineNumber );
                            }

                            // append messages to the global collection
                            messages.addAll( validatorMessages );
                        }
                    }

                    // now process interactors
                    final Iterator<Interactor> interactorIterator = entry.unmarshallInteractorIterator();

                    if (interactorIterator.hasNext()){
                        hasInteractorList = true;

                        while ( interactorIterator.hasNext() ) {
                            Interactor interactor = interactorIterator.next();

                            // check using cv mapping rules
                            Collection<ValidatorMessage> validatorMessages =
                                    super.checkCvMapping( interactor, "/entrySet/entry/interactorList/interactor/" );

                            if (validatorMessages != null){
                                validatorMessages = convertToMi25Messages( validatorMessages, interactor );

                                validatorMessages.addAll(checkInteractor(validatorMessages, interactor));
                            }
                            else {
                                validatorMessages = checkInteractor(validatorMessages, interactor);
                            }

                            if( !validatorMessages.isEmpty() ) {
                                long lineNumber = entry.getInteractorLineNumber( interactor.getId() );
                                updateLineNumber( validatorMessages, lineNumber );
                            }

                            // append messages to the global collection
                            messages.addAll( validatorMessages );
                        }
                    }

                    // now process interactions
                    Iterator<Interaction> interactionIterator = entry.unmarshallInteractionIterator();
                    while ( interactionIterator.hasNext() ) {
                        Interaction interaction = interactionIterator.next();

                        // check using cv mapping rules
                        Collection<ValidatorMessage> interactionMessages =
                                super.checkCvMapping( interaction, "/entrySet/entry/interactionList/interaction/" );

                        if (interactionMessages != null){
                            interactionMessages = convertToMi25Messages( interactionMessages, interaction );

                            interactionMessages.addAll(checkInteraction(interactionMessages, interaction, hasExperimentList, hasInteractorList));
                        }
                        else {
                            interactionMessages = checkInteraction(interactionMessages, interaction, hasExperimentList, hasInteractorList);
                        }

                        // add line number
                        if( !interactionMessages.isEmpty() ) {
                            long lineNumber = entry.getInteractionLineNumber( interaction.getId() );
                            updateLineNumber( interactionMessages, lineNumber );
                        }

                        // append messages to the global collection
                        messages.addAll( interactionMessages );
                    }
                }

                // cluster all messages!!
                Collection<ValidatorMessage> clusteredValidatorMessages = clusterByMessagesAndRules(report.getSemanticMessages());
                report.setSemanticMessages(clusteredValidatorMessages);
            }

        } catch(Exception e){
            PsimiXmlSchemaRule schemaRule = new PsimiXmlSchemaRule(this.ontologyMngr);

            StringBuffer messageBuffer = schemaRule.createMessageFromException(e);
            messageBuffer.append("\n The validator reported at least " + messages.size() + " messages but the error in the xml file need " +
                    "to be fixed before finishing the validation of the file.");

            Mi25Context context = new Mi25Context();

            messages.clear();
            messages.add( new ValidatorMessage( messageBuffer.toString(),
                    MessageLevel.FATAL,
                    context,
                    schemaRule ) );
        }
    }

    private Collection<ValidatorMessage> checkInteraction(Collection<ValidatorMessage> messages, Interaction interaction, boolean hasExperimentList, boolean hasInteractorList) throws ValidatorException {
        // run the interaction specialized rules
        Collection<ValidatorMessage> interactionMessages = super.validate( interaction );

        if (!hasExperimentList){
            for (ExperimentDescription experiment : interaction.getExperiments()){
                checkExperiment(interactionMessages, experiment);
            }
        }

        for (Confidence c : interaction.getConfidences()){
            checkConfidence(interactionMessages, c);
        }

        for (InteractionType it : interaction.getInteractionTypes()){
            // run the interaction type specialized rules
            interactionMessages.addAll(super.validate( it ));
        }

        for (Participant p : interaction.getParticipants()){
            checkParticipant(interactionMessages, p, hasInteractorList);

            for (Feature f : p.getFeatures()){
                checkFeature(interactionMessages, f);
            }
        }

        // append messages to the global collection
        if( ! interactionMessages.isEmpty() )
            messages.addAll( convertToMi25Messages( interactionMessages, interaction ) );
        return interactionMessages;
    }

    private Collection<ValidatorMessage> checkExperiment(Collection<ValidatorMessage> messages, ExperimentDescription experiment) throws ValidatorException {
        // run the experiment specialized rules
        Collection<ValidatorMessage> validatorMessages = super.validate( experiment );

        // run the bibref specialized rules
        validatorMessages.addAll(super.validate( experiment.getBibref() ));

        // run the feature detection method specialized rules
        validatorMessages.addAll(super.validate( experiment.getFeatureDetectionMethod() ));

        // run the interaction detection method specialized rules
        validatorMessages.addAll(super.validate( experiment.getInteractionDetectionMethod() ));

        // run the participant identification method specialized rules
        validatorMessages.addAll(super.validate( experiment.getParticipantIdentificationMethod() ));

        for (Confidence c : experiment.getConfidences()){
            checkConfidence(validatorMessages, c);
        }

        for (Organism o : experiment.getHostOrganisms()){
            checkOrganism(validatorMessages, o);
        }

        if( ! validatorMessages.isEmpty() )
            messages.addAll( convertToMi25Messages( validatorMessages, experiment ) );
        return validatorMessages;
    }

    private Collection<ValidatorMessage> checkInteractor(Collection<ValidatorMessage> messages, Interactor interactor) throws ValidatorException {
        // run the interactor specialized rules
        final Collection<ValidatorMessage> validatorMessages = super.validate( interactor );

        // run the interactor type specialized rules
        validatorMessages.addAll(super.validate( interactor.getInteractorType() ));

        if (interactor.getOrganism() != null){
            Organism o = interactor.getOrganism();

            checkOrganism(validatorMessages, o);
        }

        if( ! validatorMessages.isEmpty() )
            messages.addAll( convertToMi25Messages( validatorMessages, interactor ) );

        return validatorMessages;
    }

    private void checkParticipant(Collection<ValidatorMessage> validatorMessages, Participant p, boolean hasInteractorList) throws ValidatorException {
        // run the participant specialized rules
        validatorMessages.addAll(super.validate( p ));

        if (p.getInteractor() != null && !hasInteractorList){
            checkInteractor(validatorMessages, p.getInteractor());
        }

        // run the biological roles specialized rules
        validatorMessages.addAll(super.validate( p.getBiologicalRole() ));

        for (ParticipantIdentificationMethod m : p.getParticipantIdentificationMethods()){
            // run the participant identification specialized rules
            validatorMessages.addAll(super.validate( m ));
        }
        for (ExperimentalRole role : p.getExperimentalRoles()){
            // run the experimental role specialized rules
            validatorMessages.addAll(super.validate( role ));
        }
        for (Confidence c : p.getConfidenceList()){
            // run the confidence specialized rules
            validatorMessages.addAll(super.validate( c ));
        }

        for (ExperimentalPreparation ep : p.getExperimentalPreparations()){
            // run the experimental preparation specialized rules
            validatorMessages.addAll(super.validate( ep ));
        }

        for (ExperimentalInteractor ei : p.getExperimentalInteractors()){
            // run the experimental interactor specialized rules
            if (ei.getInteractor() != null && !hasInteractorList){
                checkInteractor(validatorMessages, ei.getInteractor());
            }
        }

        for (HostOrganism o : p.getHostOrganisms()){
            checkOrganism(validatorMessages, o.getOrganism());
        }
    }

    private void checkFeature(Collection<ValidatorMessage> validatorMessages, Feature f ) throws ValidatorException {
        // run the feature specialized rules
        validatorMessages.addAll(super.validate( f ));

        // run the feature detection method specialized rules
        validatorMessages.addAll(super.validate( f.getFeatureDetectionMethod() ));

        // run the feature type specialized rules
        validatorMessages.addAll(super.validate( f.getFeatureType() ));

        for (Range r : f.getRanges()){
            // run the start status specialized rules
            validatorMessages.addAll(super.validate( r.getStartStatus() ));

            // run the end status specialized rules
            validatorMessages.addAll(super.validate( r.getEndStatus() ));
        }
    }

    private void checkConfidence(Collection<ValidatorMessage> validatorMessages, Confidence c ) throws ValidatorException {
        if (c.getUnit() != null){
            // run the unit specialized rules
            validatorMessages.addAll(super.validate( c.getUnit() ));
        }
    }

    private void checkOrganism(Collection<ValidatorMessage> validatorMessages, Organism o ) throws ValidatorException {

        // run the organism specialized rules
        validatorMessages.addAll(super.validate( o ));

        if (o.getCellType() != null){
            // run the celltype specialized rules
            validatorMessages.addAll(super.validate( o.getCellType() ));
        }

        if (o.getTissue() != null){
            // run the tissue specialized rules
            validatorMessages.addAll(super.validate( o.getTissue() ));
        }

        if (o.getCompartment() != null){
            // run the compartment specialized rules
            validatorMessages.addAll(super.validate( o.getCompartment() ));
        }
    }

    /**
     * Runs the XML syntax validation and append messages to the report if any.
     *
     * @param report report in which errors should be reported.
     * @param nis stream of data to be validated.
     * @return true is the validation passed, false otherwise.
     */
    private boolean runSyntaxValidation(ValidatorReport report, InputStream nis) {

        if (log.isDebugEnabled()) log.debug("[SAX Validation] enabled via user preferences" );
        SaxReport saxReport = null;
        try {
            saxReport = SaxValidatorHandler.validate( nis );
        } catch (Exception e) {
            log.error("SAX error while running syntax validation", e);
            String msg = "An unexpected error occured while running the syntax validation";
            if( e.getMessage() != null ) {
                msg = msg + ": " + e.getMessage();
            }
            final ValidatorMessage message = new ValidatorMessage(msg, MessageLevel.FATAL );
            report.getSyntaxMessages().add(message);

            return false; // abort
        }

        if ( !saxReport.isValid() ) {

            if (log.isDebugEnabled()) log.debug( "[SAX Validation] File not PSI 2.5 valid." );

            // show an error message
            Collection<SaxMessage> messages = saxReport.getMessages();
            if (log.isDebugEnabled()) {
                log.debug( messages.size() + " message" + ( messages.size() > 1 ? "s" : "" ) + "" );
            }
            Collection<ValidatorMessage> validatorMessages = new ArrayList<ValidatorMessage>( messages.size() );
            for ( SaxMessage saxMessage : messages ) {
                // convert SaxMessage into a ValidatorMessage
                ValidatorMessage vm = new ValidatorMessage( saxMessage, MessageLevel.FATAL );
                validatorMessages.add( vm );
            }

            report.getSyntaxMessages().addAll( validatorMessages );

            if (log.isDebugEnabled()) log.debug( "Abort semantic validation." );
            return false;
        } else {
            if (log.isDebugEnabled()) log.debug( "[SAX Validation] File is valid." );
        }

        return true;
    }

    private Collection<ValidatorMessage> convertToMi25Messages( Collection<ValidatorMessage> messages, Interaction interaction ) {
        Collection<ValidatorMessage> convertedMessages = new ArrayList<ValidatorMessage>( messages.size() );

        for ( ValidatorMessage message : messages ) {
            Mi25Context context = null;

            if (message.getContext() instanceof Mi25Context){
                context = (Mi25Context) message.getContext();
            }
            else {
                context = new Mi25Context();
            }

            context.setInteractionId( interaction.getId() );
            convertedMessages.add( new ValidatorMessage( message.getMessage(), message.getLevel(), context, message.getRule() ) );
        }

        return convertedMessages;
    }

    private Collection<ValidatorMessage> convertToMi25Messages( Collection<ValidatorMessage> messages, ExperimentDescription experiment ) {
        Collection<ValidatorMessage> convertedMessages = new ArrayList<ValidatorMessage>( messages.size() );

        for ( ValidatorMessage message : messages ) {
            Mi25Context context = null;

            if (message.getContext() instanceof Mi25Context){
                context = (Mi25Context) message.getContext();
            }
            else {
                context = new Mi25Context();
            }

            context.setExperimentId( experiment.getId() );
            convertedMessages.add( new ValidatorMessage( message.getMessage(), message.getLevel(), context, message.getRule() ) );
        }

        return convertedMessages;
    }

    private Collection<ValidatorMessage> convertToMi25Messages( Collection<ValidatorMessage> messages, Interactor interactor ) {
        Collection<ValidatorMessage> convertedMessages = new ArrayList<ValidatorMessage>( messages.size() );

        for ( ValidatorMessage message : messages ) {
            Mi25Context context = null;

            if (message.getContext() instanceof Mi25Context){
                context = (Mi25Context) message.getContext();
            }
            else {
                context = new Mi25Context();
            }

            context.setInteractorId( interactor.getId() );
            convertedMessages.add( new ValidatorMessage( message.getMessage(), message.getLevel(), context, message.getRule() ) );
        }

        return convertedMessages;
    }

    private void updateLineNumber( Collection<ValidatorMessage> validatorMessages, long lineNumber ) {
        if( lineNumber > 0 ) {
            for ( ValidatorMessage msg : validatorMessages ) {
                if( msg.getContext() instanceof Mi25Context ) {
                    ((Mi25Context) msg.getContext() ).setLineNumber( lineNumber );
                }
            }
        }
    }

    public Collection<ValidatorMessage> clusterByMessagesAndRules (Collection<ValidatorMessage> messages){
        Collection<ValidatorMessage> clusteredMessages = new ArrayList<ValidatorMessage>( messages.size() );

        // build a first clustering by message and rule
        Map<String, Map<Rule, Set<ValidatorMessage>>> clustering = new HashMap<String, Map<Rule, Set<ValidatorMessage>>>();
        for (ValidatorMessage message : messages){

            if (clustering.containsKey(message.getMessage())){
                Map<Rule, Set<ValidatorMessage>> messagesCluster = clustering.get(message.getMessage());

                if (messagesCluster.containsKey(message.getRule())){
                    messagesCluster.get(message.getRule()).add(message);
                }
                else{
                    Set<ValidatorMessage> validatorContexts = new HashSet<ValidatorMessage>();
                    validatorContexts.add(message);
                    messagesCluster.put(message.getRule(), validatorContexts);
                }
            }
            else {
                Map<Rule, Set<ValidatorMessage>> messagesCluster = new HashMap<Rule, Set<ValidatorMessage>>();

                Set<ValidatorMessage> validatorContexts = new HashSet<ValidatorMessage>();
                validatorContexts.add(message);
                messagesCluster.put(message.getRule(), validatorContexts);

                clustering.put(message.getMessage(), messagesCluster);
            }
        }

        // build a second cluster by message level
        Map<MessageLevel, Mi25ClusteredContext> clusteringByMessageLevel = new HashMap<MessageLevel, Mi25ClusteredContext>();

        for (Map.Entry<String, Map<Rule, Set<ValidatorMessage>>> entry : clustering.entrySet()){

            String message = entry.getKey();
            Map<Rule, Set<ValidatorMessage>> ruleCluster = entry.getValue();

            // cluster by message level and create proper validatorMessage
            for (Map.Entry<Rule, Set<ValidatorMessage>> ruleEntry : ruleCluster.entrySet()){
                clusteringByMessageLevel.clear();

                Rule rule = ruleEntry.getKey();
                Set<ValidatorMessage> validatorMessages = ruleEntry.getValue();

                for (ValidatorMessage validatorMessage : validatorMessages){

                    if (clusteringByMessageLevel.containsKey(validatorMessage.getLevel())){
                        Mi25ClusteredContext clusteredContext = clusteringByMessageLevel.get(validatorMessage.getLevel());

                        clusteredContext.getContexts().add(validatorMessage.getContext());
                    }
                    else{
                        Mi25ClusteredContext clusteredContext = new Mi25ClusteredContext();

                        clusteredContext.getContexts().add(validatorMessage.getContext());

                        clusteringByMessageLevel.put(validatorMessage.getLevel(), clusteredContext);
                    }
                }

                for (Map.Entry<MessageLevel, Mi25ClusteredContext> levelEntry : clusteringByMessageLevel.entrySet()){

                    ValidatorMessage validatorMessage = new ValidatorMessage(message, levelEntry.getKey(), levelEntry.getValue(), rule);
                    clusteredMessages.add(validatorMessage);

                }
            }
        }

        return clusteredMessages;
    }
}