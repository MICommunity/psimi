package psidev.psi.mi.validator.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.*;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.factory.MolecularInteractionDataSourceFactory;
import psidev.psi.mi.tab.listeners.SimpleMitabDataSource;
import psidev.psi.mi.validator.ValidatorReport;
import psidev.psi.mi.validator.extension.rules.FileSyntaxRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.validator.extension.rules.cvmapping.MICvRuleManager;
import psidev.psi.mi.validator.extension.rules.psimi.DatabaseAccessionRule;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.listeners.LightWeightSimplePsiXmlDataSource;
import psidev.psi.tools.cvrReader.mapping.jaxb.CvMapping;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.OntologyManagerContext;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.*;
import psidev.psi.tools.validator.preferences.UserPreferences;
import psidev.psi.tools.validator.rules.Rule;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.io.File;
import java.io.InputStream;
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

    ///**
    //* Plateform specific break line.
    //*/
    //public static final String NEW_LINE = System.getProperty( "line.separator" );

    /**
     * counter used for creating unique IDs.
     * <p/>
     * These are used to create temp files.
     */
    private long uniqId = 0;

    private UserPreferences userPreferences;

    private ValidatorReport validatorReport;

    private MolecularInteractionDataSource currentDataSource;

    private FileSourceAnalyzer fileSourceAnalyzer;

    public final static String SOURCE_FORMAT = "source_format";

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
        validatorReport = new ValidatorReport();
        fileSourceAnalyzer = new FileSourceAnalyzer();
    }

    public Mi25Validator( OntologyManager ontologyManager,
                          CvMapping cvMapping,
                          Collection<ObjectRule> objectRules) {
        super( ontologyManager, cvMapping, objectRules);
        validatorReport = new ValidatorReport();
        fileSourceAnalyzer = new FileSourceAnalyzer();
    }

    ///////////////////////////
    // Getters and Setters

    public void setUserPreferences( UserPreferences userPreferences ) {
        this.userPreferences = userPreferences;
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    @Override
    protected void instantiateCvRuleManager(OntologyManager manager, CvMapping cvMappingRules) {
        super.setCvRuleManager(new MICvRuleManager(manager, cvMappingRules));
    }

    /////////////////////////////
    // Validator

    public void registerMolecularInteractionDataSources(){
        Map<String, Object> mitabOptions = new HashMap<String, Object>();
        mitabOptions.put(SOURCE_FORMAT, MolecularInteractionSource.mitab.toString());
        Map<String, Object> xmlOptions = new HashMap<String, Object>();
        xmlOptions.put(SOURCE_FORMAT, MolecularInteractionSource.psi25_xml.toString());

        MolecularInteractionDataSourceFactory.registerDataSource(SimpleMitabDataSource.class, mitabOptions);
        MolecularInteractionDataSourceFactory.registerDataSource(LightWeightSimplePsiXmlDataSource.class, xmlOptions);
    }

    /**
     * Validate a file and use the user preferences to decide if run semantic and syntax validation or not
     * @param file
     * @return
     * @throws ValidatorException
     */
    public ValidatorReport validate( File file ) throws ValidatorException {
        this.validatorReport.clear();
        registerMolecularInteractionDataSources();
        try {
            MolecularInteractionSource sourceFormat = fileSourceAnalyzer.getMolecularInteractionSourceFor(file);
            Map<String, Object> requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(SOURCE_FORMAT, sourceFormat.toString());

            this.currentDataSource = MolecularInteractionDataSourceFactory.getMolecularInteractionDataSourceFrom(file, requiredOptions);
            // 1. Validate source syntax
            if ( userPreferences.isSaxValidationEnabled() ) {
                if (false == validateSyntax(currentDataSource)) {
                    return this.validatorReport;
                }
            }

            validateSemantic(currentDataSource);

            return this.validatorReport;

        } catch ( Exception e ) {
            throw new ValidatorException( "Unable to process input file:" + file.getAbsolutePath(), e );
        }
        finally {
            clearThreadLocals();
            if (this.currentDataSource instanceof MolecularInteractionFileDataSource){
                ((MolecularInteractionFileDataSource)this.currentDataSource).close();
            }
        }
    }

    /**
     * Validate the syntax of a file.
     * @param file
     * @return false if syntax not valid
     */
    public boolean validateSyntax(File file){
        this.validatorReport.getSyntaxMessages().clear();

        registerMolecularInteractionDataSources();
        try {
            MolecularInteractionSource sourceFormat = fileSourceAnalyzer.getMolecularInteractionSourceFor(file);
            Map<String, Object> requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(SOURCE_FORMAT, sourceFormat.toString());

            this.currentDataSource = MolecularInteractionDataSourceFactory.getMolecularInteractionDataSourceFrom(file, requiredOptions);

            boolean valid = runSyntaxValidation(validatorReport, this.currentDataSource);
            if (this.currentDataSource instanceof MolecularInteractionFileDataSource){
                ((MolecularInteractionFileDataSource)this.currentDataSource).close();
            }
            return valid;

        } catch ( Exception e ) {
            log.error("Error while running syntax validation", e);
            String msg = "An unexpected error occured while running the syntax validation: The file format is not recognized";
            if( e.getMessage() != null ) {
                msg = msg + ": " + e.getMessage();
            }
            final ValidatorMessage message = new ValidatorMessage(msg, MessageLevel.FATAL );
            validatorReport.getSyntaxMessages().add(message);

            return false;
        }
    }

    /**
     * Validate the syntax of a datasource.
     * @param dataSource
     * @return false if syntax not valid
     */
    public boolean validateSyntax(MolecularInteractionDataSource dataSource){
        this.validatorReport.getSyntaxMessages().clear();

        return runSyntaxValidation(this.validatorReport, dataSource);
    }

    /**
     * validate the syntax of an inputStream
     * @param streamToValidate
     * @return false if not valid
     */
    public boolean validateSyntax(InputStream streamToValidate){
        this.validatorReport.getSyntaxMessages().clear();

        registerMolecularInteractionDataSources();
        try {
            OpenedInputStream openedStream = fileSourceAnalyzer.getMolecularInteractionSourceFor(streamToValidate);
            Map<String, Object> requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(SOURCE_FORMAT, openedStream.getSource().toString());

            this.currentDataSource = MolecularInteractionDataSourceFactory.getMolecularInteractionDataSourceFrom(openedStream.getStream(), requiredOptions);

            boolean valid = runSyntaxValidation(validatorReport, this.currentDataSource);
            if (this.currentDataSource instanceof MolecularInteractionFileDataSource){
                ((MolecularInteractionFileDataSource)this.currentDataSource).close();
            }
            return valid;

        } catch ( Exception e ) {
            log.error("Error while running syntax validation", e);
            String msg = "An unexpected error occured while running the syntax validation: The file format is not recognized";
            if( e.getMessage() != null ) {
                msg = msg + ": " + e.getMessage();
            }
            final ValidatorMessage message = new ValidatorMessage(msg, MessageLevel.FATAL );
            validatorReport.getSyntaxMessages().add(message);

            return false;
        }
    }

    /**
     * validate the semantic of a file and write the results in the report of this validator
     * @param file
     * @throws ValidatorException
     * @throws PsimiXmlReaderException
     */
    public void validateSemantic(File file) throws ValidatorException {
        this.validatorReport.getSemanticMessages().clear();
        this.validatorReport.setInteractionCount(0);

        registerMolecularInteractionDataSources();
        try {
            MolecularInteractionSource sourceFormat = fileSourceAnalyzer.getMolecularInteractionSourceFor(file);
            Map<String, Object> requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(SOURCE_FORMAT, sourceFormat.toString());

            this.currentDataSource = MolecularInteractionDataSourceFactory.getMolecularInteractionDataSourceFrom(file, requiredOptions);

            validateSemantic(currentDataSource);

            if (this.currentDataSource instanceof MolecularInteractionFileDataSource){
                ((MolecularInteractionFileDataSource)this.currentDataSource).close();
            }

        } catch ( Exception e ) {
            throw new ValidatorException( "Unable to process input file:" + file.getAbsolutePath(), e );
        }
    }

    public void validateSemantic(MolecularInteractionDataSource dataSource) throws ValidatorException {
        this.validatorReport.getSemanticMessages().clear();
        this.validatorReport.setInteractionCount(0);

        runSemanticValidation(this.validatorReport, dataSource);
    }


    /**
     * validate the semantic of an inputstream and write the results in the report of this validator
     * @param streamToValidate
     * @throws ValidatorException
     * @throws PsimiXmlReaderException
     */
    public void validateSemantic(InputStream streamToValidate) throws ValidatorException, PsimiXmlReaderException {
        this.validatorReport.getSemanticMessages().clear();
        this.validatorReport.setInteractionCount(0);

        registerMolecularInteractionDataSources();
        try {
            OpenedInputStream openedStream = fileSourceAnalyzer.getMolecularInteractionSourceFor(streamToValidate);
            Map<String, Object> requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(SOURCE_FORMAT, openedStream.getSource().toString());

            this.currentDataSource = MolecularInteractionDataSourceFactory.getMolecularInteractionDataSourceFrom(openedStream.getStream(), requiredOptions);

            validateSemantic(currentDataSource);

            if (this.currentDataSource instanceof MolecularInteractionFileDataSource){
                ((MolecularInteractionFileDataSource)this.currentDataSource).close();
            }

            openedStream.close();

        } catch ( Exception e ) {
            throw new ValidatorException( "Unable to process input stream", e );
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

        this.validatorReport.clear();
        registerMolecularInteractionDataSources();
        try {
            OpenedInputStream openedStream = fileSourceAnalyzer.getMolecularInteractionSourceFor(is);
            Map<String, Object> requiredOptions = new HashMap<String, Object>();
            requiredOptions.put(SOURCE_FORMAT, openedStream.getSource().toString());

            this.currentDataSource = MolecularInteractionDataSourceFactory.getMolecularInteractionDataSourceFrom(openedStream.getStream(), requiredOptions);
            // 1. Validate source syntax
            if ( userPreferences.isSaxValidationEnabled() ) {
                if (false == validateSyntax(currentDataSource)) {
                    return this.validatorReport;
                }
            }

            validateSemantic(currentDataSource);

            openedStream.close();

            return this.validatorReport;

        } catch ( Exception e ) {
            throw new ValidatorException( "Unable to process input stream:", e );
        }
        finally {
            clearThreadLocals();
            if (this.currentDataSource instanceof MolecularInteractionFileDataSource){
                ((MolecularInteractionFileDataSource)this.currentDataSource).close();
            }
        }
    }

    /**
     * Validates a PSI-MI 2.5 EntrySet.
     *
     * @param es the entrySet to validate.
     * @return a collection of messages
     * @throws ValidatorException
     */
    public ValidatorReport validate( MolecularInteractionDataSource es ) throws ValidatorException {

        this.validatorReport.clear();
        this.currentDataSource = es;

        try {
            // 1. Validate source syntax
            if ( userPreferences.isSaxValidationEnabled() ) {
                if (false == validateSyntax(currentDataSource)) {
                    return this.validatorReport;
                }
            }

            validateSemantic(currentDataSource);

            return this.validatorReport;

        } catch ( Exception e ) {
            throw new ValidatorException( "Unable to process input stream:", e );
        }
        finally {
            clearThreadLocals();
            if (this.currentDataSource instanceof MolecularInteractionFileDataSource){
                ((MolecularInteractionFileDataSource)this.currentDataSource).close();
            }
        }
    }

    //////////////////////////////
    // Private

    /**
     * Runs the semantic validation and append messages to the given report.
     *
     * @param report the report to be completed.
     * @param dataSource the molecular interaction source to be validated.
     * @throws PsimiXmlReaderException
     * @throws ValidatorException
     */
    private void runSemanticValidation(ValidatorReport report, MolecularInteractionDataSource dataSource) throws ValidatorException {
        if (dataSource == null){
            throw new ValidatorException("The molecular interaction datasource could not be loaded because the format is not recognized.");
        }

        // Build the collection of messages in which we will accumulate the output of the validator
        Collection<ValidatorMessage> messages = report.getSemanticMessages();
        processSemanticValidation(report, messages, dataSource);
    }

    private void processSemanticValidation(ValidatorReport report, Collection<ValidatorMessage> messages, MolecularInteractionDataSource dataSource) throws ValidatorException {

        Collection<ValidatorMessage> interactionMessages = super.validate(dataSource);
        // only validate streaming interaction source for now
        if (dataSource instanceof StreamingInteractionSource){
            StreamingInteractionSource interactionSource = (StreamingInteractionSource) dataSource;

            Iterator<? extends InteractionEvidence> interactionIterator = interactionSource.getInteractionEvidencesIterator();

            // now process interactions
            while ( interactionIterator.hasNext() ) {
                InteractionEvidence interaction = interactionIterator.next();

                // check using cv mapping rules
                interactionMessages.addAll(super.checkCvMapping(interaction, "/interactionEvidence/"));

                checkInteraction(interactionMessages, interaction);

                // add line number
                if( !interactionMessages.isEmpty() ) {
                    interactionMessages = convertToMi25Messages( interactionMessages, interaction );
                }

                // append messages to the global collection
                messages.addAll( interactionMessages );
            }

            // cluster all messages!!
            Collection<ValidatorMessage> clusteredValidatorMessages = clusterByMessagesAndRules(report.getSemanticMessages());
            report.setSemanticMessages(clusteredValidatorMessages);
        }
        else {
            throw new ValidatorException("The molecular interaction datasource is not an InteractionStreaming data source and cannot be validated.");
        }
    }

    private void checkInteraction(Collection<ValidatorMessage> messages, InteractionEvidence interaction) throws ValidatorException {
        // run the interaction specialized rules
        messages.addAll(super.validate( interaction ));

        if (interaction.getExperiment() != null){
            checkExperiment(messages, interaction.getExperiment());
        }

        for (psidev.psi.mi.jami.model.Confidence c : interaction.getExperimentalConfidences()){
            checkConfidence(messages, c);
        }

        for (Parameter p : interaction.getExperimentalParameters()){
            checkParameter(messages, p);
        }

        if (interaction.getType() != null){
            // run the interaction type specialized rules
            checkCvTerm(messages, interaction.getType());
        }

        if (!interaction.getXrefs().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : interaction.getXrefs()){
                messages.addAll(super.validate( ref ));
            }
        }

        if (!interaction.getIdentifiers().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : interaction.getIdentifiers()){
                messages.addAll(super.validate( ref ));
            }
        }

        for (ParticipantEvidence p : interaction.getParticipantEvidences()){
            checkParticipant(messages, p);
        }
    }

    private void checkExperiment(Collection<ValidatorMessage> messages, Experiment experiment) throws ValidatorException {
        // run the experiment specialized rules
        messages.addAll(super.validate(experiment));

        // run the bibref specialized rules
        if (experiment.getPublication() != null){
            checkPublication(messages, experiment.getPublication());
        }

        // run the interaction detection method specialized rules
        checkCvTerm(messages, experiment.getInteractionDetectionMethod() );

        if (experiment.getHostOrganism() != null){
            checkOrganism(messages, experiment.getHostOrganism());
        }

        if (!experiment.getXrefs().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : experiment.getXrefs()){
                messages.addAll(super.validate( ref ));
            }
        }
    }

    private void checkPublication(Collection<ValidatorMessage> messages, Publication publication) throws ValidatorException {
        // run the experiment specialized rules
        messages.addAll(super.validate( publication ));

        // run the bibref specialized rules
        if (publication.getSource() != null){
            checkCvTerm(messages, publication.getSource());
        }

        if (!publication.getIdentifiers().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : publication.getIdentifiers()){
                messages.addAll(super.validate( ref ));
            }
        }
        if (!publication.getXrefs().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : publication.getXrefs()){
                messages.addAll(super.validate( ref ));
            }
        }
    }

    private void checkInteractor(Collection<ValidatorMessage> messages, psidev.psi.mi.jami.model.Interactor interactor) throws ValidatorException {
        // run the interactor specialized rules
        messages.addAll(super.validate(interactor));

        // run the interactor type specialized rules
        checkCvTerm(messages, interactor.getType() );

        if (!interactor.getIdentifiers().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : interactor.getIdentifiers()){
                messages.addAll(super.validate( ref ));
            }
        }
        if (!interactor.getXrefs().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : interactor.getXrefs()){
                messages.addAll(super.validate( ref ));
            }
        }
        if (!interactor.getAliases().isEmpty()){
            for (Alias ref : interactor.getAliases()){
                messages.addAll(super.validate( ref ));
            }
        }

        if (interactor.getOrganism() != null){
            checkOrganism(messages, interactor.getOrganism());
        }
    }

    private void checkParticipant(Collection<ValidatorMessage> validatorMessages, ParticipantEvidence p) throws ValidatorException {
        // run the participant specialized rules
        validatorMessages.addAll(super.validate( p ));

        if (p.getInteractor() != null){
            checkInteractor(validatorMessages, p.getInteractor());
        }

        // run the biological roles specialized rules
        checkCvTerm(validatorMessages, p.getBiologicalRole());

        if (p.getIdentificationMethod() != null){
            // run the participant identification specialized rules
            checkCvTerm(validatorMessages, p.getIdentificationMethod());
        }

        checkCvTerm(validatorMessages, p.getExperimentalRole());

        for (psidev.psi.mi.jami.model.Confidence c : p.getConfidences()){
            // run the confidence specialized rules
            checkConfidence(validatorMessages, c );
        }

        for (Parameter c : p.getParameters()){
            // run the confidence specialized rules
            checkParameter(validatorMessages, c);
        }

        for (CvTerm ep : p.getExperimentalPreparations()){
            // run the experimental preparation specialized rules
            checkCvTerm(validatorMessages, ep );
        }

        if (p.getExpressedInOrganism() != null){
            checkOrganism(validatorMessages, p.getExpressedInOrganism());
        }

        if (!p.getXrefs().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : p.getXrefs()){
                validatorMessages.addAll(super.validate( ref ));
            }
        }
        if (!p.getAliases().isEmpty()){
            for (Alias ref : p.getAliases()){
                validatorMessages.addAll(super.validate( ref ));
            }
        }

        for (FeatureEvidence f : p.getFeatureEvidences()){
            checkFeature(validatorMessages, f);
        }
    }

    private void checkFeature(Collection<ValidatorMessage> validatorMessages, FeatureEvidence f ) throws ValidatorException {
        // run the feature specialized rules
        validatorMessages.addAll(super.validate( f ));

        // run the feature detection method specialized rules
        if (f.getDetectionMethod() != null){
            checkCvTerm(validatorMessages, f.getDetectionMethod());
        }

        // run the feature type specialized rules
        if (f.getType() != null){
            checkCvTerm(validatorMessages, f.getType());
        }

        if (!f.getXrefs().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : f.getXrefs()){
                validatorMessages.addAll(super.validate( ref ));
            }
        }

        if (!f.getIdentifiers().isEmpty()){
            for (psidev.psi.mi.jami.model.Xref ref : f.getXrefs()){
                validatorMessages.addAll(super.validate( ref ));
            }
        }

        for (psidev.psi.mi.jami.model.Range r : f.getRanges()){
            // run the start status specialized rules
            checkCvTerm(validatorMessages, r.getStart().getStatus());

            // run the end status specialized rules
            checkCvTerm(validatorMessages, r.getEnd().getStatus());
        }
    }

    private void checkConfidence(Collection<ValidatorMessage> validatorMessages, psidev.psi.mi.jami.model.Confidence c ) throws ValidatorException {
        if (c.getType() != null){
            // run the unit specialized rules
            checkCvTerm(validatorMessages, c.getType());
        }

        if (c.getUnit() != null){
            // run the unit specialized rules
            checkCvTerm(validatorMessages, c.getUnit());
        }
    }

    private void checkParameter(Collection<ValidatorMessage> validatorMessages, Parameter c ) throws ValidatorException {
        if (c.getType() != null){
            // run the unit specialized rules
            checkCvTerm(validatorMessages, c.getType());
        }

        if (c.getUnit() != null){
            // run the unit specialized rules
            checkCvTerm(validatorMessages, c.getUnit());
        }
    }

    private void checkCvTerm(Collection<ValidatorMessage> validatorMessages, CvTerm c ) throws ValidatorException {
        validatorMessages.addAll(super.validate(c));

        if (!c.getSynonyms().isEmpty()){
            for (Alias alias : c.getSynonyms()){
                validatorMessages.addAll(super.validate( alias ));
            }
        }

        if (!c.getXrefs().isEmpty()){
            // run the unit specialized rules
            for (psidev.psi.mi.jami.model.Xref ref : c.getXrefs()){
                validatorMessages.addAll(super.validate( ref ));
            }
        }

        if (!c.getIdentifiers().isEmpty()){
            // run the unit specialized rules
            for (psidev.psi.mi.jami.model.Xref ref : c.getIdentifiers()){
                validatorMessages.addAll(super.validate( ref ));
            }
        }
    }

    private void checkOrganism(Collection<ValidatorMessage> validatorMessages, psidev.psi.mi.jami.model.Organism o ) throws ValidatorException {

        // run the organism specialized rules
        validatorMessages.addAll(super.validate( o ));

        if (o.getCellType() != null){
            // run the celltype specialized rules
            checkCvTerm(validatorMessages, o.getCellType());
        }

        if (o.getTissue() != null){
            // run the tissue specialized rules
            checkCvTerm(validatorMessages, o.getTissue());
        }

        if (o.getCompartment() != null){
            // run the compartment specialized rules
            checkCvTerm(validatorMessages, o.getCompartment());
        }

        if (!o.getAliases().isEmpty()){
            for (Alias alias : o.getAliases()){
                validatorMessages.addAll(super.validate( alias ));
            }
        }
    }

    private boolean runSyntaxValidation(ValidatorReport report, MolecularInteractionDataSource dataSource) {

        if (log.isDebugEnabled()) log.debug("[Syntax Validation] enabled via user preferences" );

        if (dataSource == null){
            String msg = "The molecular interaction datasource could not be loaded";
            final ValidatorMessage message = new ValidatorMessage(msg, MessageLevel.FATAL );
            report.getSyntaxMessages().add(message);
            return false;
        }
        else if (dataSource instanceof MolecularInteractionFileDataSource){
            MolecularInteractionFileDataSource fileSource = (MolecularInteractionFileDataSource) dataSource;

            FileSyntaxRule syntaxRule = new FileSyntaxRule(ontologyMngr);
            try {
                report.getSyntaxMessages().addAll(syntaxRule.check(fileSource));
                return report.getSyntaxMessages().isEmpty();
            } catch (ValidatorException e) {
                log.error("Error while running syntax validation", e);
                String msg = "An unexpected error occured while running the syntax validation";
                if( e.getMessage() != null ) {
                    msg = msg + ": " + e.getMessage();
                }
                final ValidatorMessage message = new ValidatorMessage(msg, MessageLevel.FATAL );
                report.getSyntaxMessages().add(message);

                return false;
            }
        }
        else {
            return true;
        }
    }

    private Collection<ValidatorMessage> convertToMi25Messages( Collection<ValidatorMessage> messages, InteractionEvidence interaction ) {
        Collection<ValidatorMessage> convertedMessages = new ArrayList<ValidatorMessage>( messages.size() );

        for ( ValidatorMessage message : messages ) {
            Mi25Context context = null;

            if (message.getContext() instanceof Mi25Context){
                context = (Mi25Context) message.getContext();
                if (!context.getObjectLabel().equals("interaction")){
                    context.addAssociatedContext(RuleUtils.buildContext(interaction, "interaction"));
                }
            }
            else {
                context = RuleUtils.buildContext(interaction, "interaction");
                context.setContext(message.getContext().getContext());
            }

            convertedMessages.add( new ValidatorMessage( message.getMessage(), message.getLevel(), context, message.getRule() ) );
        }

        return convertedMessages;
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

    public ValidatorReport getMIValidatorReport() {
        return validatorReport;
    }

    private void clearThreadLocals(){
        // remove validator CvContext
        ValidatorCvContext.removeInstance();
        // close GeneralCacheAdministrator for DatabaseAccessionRule
        DatabaseAccessionRule.closeGeneralCacheAdministrator();
        // close GeneralCacheAdministrator for OntologyManagerContext
        OntologyManagerContext.removeInstance();
        // close Mi25ValidatorContext
        Mi25ValidatorContext.removeInstance();
    }
}