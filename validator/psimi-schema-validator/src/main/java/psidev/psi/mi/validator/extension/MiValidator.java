package psidev.psi.mi.validator.extension;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.commons.*;
import psidev.psi.mi.jami.datasource.InteractionStream;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.validator.ValidatorReport;
import psidev.psi.mi.validator.ValidatorUtils;
import psidev.psi.mi.validator.extension.rules.*;
import psidev.psi.mi.validator.extension.rules.cvmapping.MICvRuleManager;
import psidev.psi.mi.validator.extension.rules.psimi.DatabaseAccessionRule;
import psidev.psi.tools.cvrReader.mapping.jaxb.CvMapping;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.OntologyManagerContext;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.*;
import psidev.psi.tools.validator.preferences.UserPreferences;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <b> PSI-MI 2.5.2 Specific Validator </b>.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class MiValidator extends Validator {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( MiValidator.class );

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

    private AliasRuleWrapper aliasRuleWrapper;
    private AnnotationRuleWrapper annotationRuleWrapper;
    private ConfidenceRuleWrapper confidenceRuleWrapper;
    private ExperimentRuleWrapper experimentRuleWrapper;
    private FeatureEvidenceRuleWrapper featureRuleWrapper;
    private InteractionEvidenceRuleWrapper interactionEvidenceRuleWrapper;
    private InteractorRuleWrapper interactorRuleWrapper;
    private OrganismRuleWrapper organismRuleWrapper;
    private ParameterRuleWrapper parameterRuleWrapper;
    private ParticipantEvidenceRuleWrapper participantRuleWrapper;
    private PublicationRuleWrapper publicationRuleWrapper;
    private XrefRuleWrapper xrefRuleWrapper;
    private CvRuleWrapper cvRuleWrapper;
    private ChecksumRuleWrapper checksumRuleWrapper;
    private MIFileSyntaxListenerRule syntaxRule;
    private MIFileListenerRuleWrapper listenerRule;

    private boolean validateObjectRule = true;

    private MIFileAnalyzer fileAnalyser = new MIFileAnalyzer();

    private Set<Object> processObjects;

    /**
     * Creates a MI 25 validator with the default user userPreferences.
     *
     * @param ontologyconfig   configuration for the ontologies.
     * @param cvMappingConfig  configuration for the cv mapping rules.
     * @param objectRuleConfig configuration for the object rules.
     * @throws ValidatorException
     */
    public MiValidator(InputStream ontologyconfig,
                       InputStream cvMappingConfig,
                       InputStream objectRuleConfig) throws ValidatorException, OntologyLoaderException {

        super( ontologyconfig, cvMappingConfig, objectRuleConfig );
        validatorReport = new ValidatorReport();
        this.syntaxRule = new MIFileSyntaxListenerRule(this.ontologyMngr);
        this.processObjects = Collections.newSetFromMap(new IdentityHashMap<Object, Boolean>());

        // refilter object rules
        setObjectRules(new ArrayList<ObjectRule>(getObjectRules()));
    }

    public MiValidator(OntologyManager ontologyManager,
                       CvMapping cvMapping,
                       Collection<ObjectRule> objectRules) {
        super( ontologyManager, cvMapping, objectRules);
        validatorReport = new ValidatorReport();
        this.syntaxRule = new MIFileSyntaxListenerRule(this.ontologyMngr);
        this.processObjects = Collections.newSetFromMap(new IdentityHashMap<Object, Boolean>());
    }

    @Override
    public void setObjectRules(Collection<ObjectRule> objectRules){

        if (objectRules != null){
            getObjectRules().clear();

            // initialise wrappers first
            this.aliasRuleWrapper = new AliasRuleWrapper(this.ontologyMngr);
            this.annotationRuleWrapper = new AnnotationRuleWrapper(this.ontologyMngr);
            this.confidenceRuleWrapper = new ConfidenceRuleWrapper(this.ontologyMngr);
            this.experimentRuleWrapper = new ExperimentRuleWrapper(this.ontologyMngr);
            this.featureRuleWrapper = new FeatureEvidenceRuleWrapper(this.ontologyMngr);
            this.interactionEvidenceRuleWrapper = new InteractionEvidenceRuleWrapper(this.ontologyMngr);
            this.interactorRuleWrapper = new InteractorRuleWrapper(this.ontologyMngr);
            this.organismRuleWrapper = new OrganismRuleWrapper(this.ontologyMngr);
            this.parameterRuleWrapper = new ParameterRuleWrapper(this.ontologyMngr);
            this.participantRuleWrapper = new ParticipantEvidenceRuleWrapper(this.ontologyMngr);
            this.publicationRuleWrapper = new PublicationRuleWrapper(this.ontologyMngr);
            this.xrefRuleWrapper = new XrefRuleWrapper(this.ontologyMngr);
            this.cvRuleWrapper = new CvRuleWrapper(this.ontologyMngr);
            this.checksumRuleWrapper = new ChecksumRuleWrapper(this.ontologyMngr);
            this.listenerRule = new MIFileListenerRuleWrapper(this.ontologyMngr);

            for (ObjectRule rule : objectRules){
                // if we have special rules
                if (rule instanceof AbstractMIRule){
                    AbstractMIRule miRule = (AbstractMIRule)rule;
                    if (miRule.getType().isAssignableFrom(Alias.class)){
                        this.aliasRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(Annotation.class)){
                        this.annotationRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(Confidence.class)){
                        this.confidenceRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(Experiment.class)){
                        this.experimentRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(FeatureEvidence.class)){
                        this.featureRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(InteractionEvidence.class)){
                        this.interactionEvidenceRuleWrapper.addRule(miRule);
                    }
                    else if (Interactor.class.isAssignableFrom(miRule.getType())){
                        this.interactorRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(Organism.class)){
                        this.organismRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(Parameter.class)){
                        this.parameterRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(ParticipantEvidence.class)){
                        this.participantRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(Publication.class)){
                        this.publicationRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(Xref.class)){
                        this.xrefRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(CvTerm.class)){
                        this.cvRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(Checksum.class)){
                        this.checksumRuleWrapper.addRule(miRule);
                    }
                    else if (miRule.getType().isAssignableFrom(MIFileDataSource.class)
                            && miRule instanceof MitabParserListener &&
                            miRule instanceof PsiXmlParserListener){
                        this.listenerRule.addRule(miRule);
                    }
                    else{
                        getObjectRules().add(rule);
                    }
                }
                // collect all other rules as basic object rules
                else{
                    getObjectRules().add(rule);
                }
            }
        }
        else {
            log.info("No object rule has been loaded.");
        }

        if (objectRules.isEmpty()){
            log.info("The list of object rules is empty.");
            this.validateObjectRule = false;
        }
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

    /////////////////////////////
    // Validator

    /**
     * Validate a file and use the user preferences to decide if run semantic and syntax validation or not
     * @param file
     * @return
     * @throws ValidatorException
     */
    public ValidatorReport validate( File file ) throws ValidatorException {
        this.validatorReport.clear();
        this.processObjects.clear();

        try {

            // first check if can parse dataSource and register datasources
            InteractionStream<InteractionEvidence> interactionSource = parseDataSource(file);

            try{
                // 1. Validate file input using Schema (MIF) for xml, normal listeners for mitab
                if ( userPreferences.isSaxValidationEnabled() ) {
                    if (false == validateSyntax((MIFileDataSource)interactionSource)) {
                        return this.validatorReport;
                    }
                }

                validateSemantic(interactionSource);
            }
            finally {
                if (interactionSource != null){
                    interactionSource.close();
                }
            }

            return this.validatorReport;

        } catch ( Exception e ) {
            throw new ValidatorException( "Unable to process input file:" + file.getAbsolutePath(), e );
        }
        finally {
            clearThreadLocals();
        }
    }

    /**
     * Validate the syntax of a file.
     * @param interactionSource
     * @return false if syntax not valid
     */
    public boolean validateSyntax(MIFileDataSource interactionSource){
        this.validatorReport.getSyntaxMessages().clear();

        return runSyntaxValidation(this.validatorReport, interactionSource);
    }

    /**
     * validate the semantic of a file and write the results in the report of this validator
     * @param interactionSource
     * @throws ValidatorException
     */
    public void validateSemantic(InteractionStream<InteractionEvidence> interactionSource) throws ValidatorException {
        this.validatorReport.getSemanticMessages().clear();
        this.validatorReport.setInteractionCount(0);

        runSemanticValidation(this.validatorReport, interactionSource);
    }

    /**
     * Validates a PSI-MI 2.5 InputStream.
     *
     * @param is the input stream to validate.
     * @return a collection of messages
     * @throws ValidatorException
     */
    public ValidatorReport validate( InputStream is ) throws ValidatorException {

        OpenedInputStream openedStream = null;
        try {
            openedStream = this.fileAnalyser.extractMIFileTypeFrom(is);
            if (openedStream == null || openedStream.getSource().equals(MIFileType.other)){
                throw new ValidatorException("Cannot parse the MI input source because the format is not recognized");
            }
            long id = getUniqueId();

            File tempFile = MIFileUtils.storeAsTemporaryFile(openedStream.getReader(), "validator."+id, openedStream.getSource().equals(MIFileType.psi25_xml) ? ".xml" : ".txt");

            // 1. Validate XML input using Schema (MIF)
            validate(tempFile);

            tempFile.delete();

            return this.validatorReport;

        } catch ( Exception e ) {
            throw new ValidatorException( "Unable to process input stream", e );
        } finally{
            clearThreadLocals();
            if (openedStream != null){
                // close the original inputStream as it has been successfully read
                try {
                    openedStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Validates an interaction evidence source.
     *
     * @param es the entrySet to validate.
     * @return a collection of messages
     * @throws ValidatorException
     */
    public ValidatorReport validate( InteractionStream<InteractionEvidence> es ) throws ValidatorException {

        this.validatorReport.clear();
        this.processObjects.clear();

        try{
            if ( userPreferences.isSaxValidationEnabled() && es instanceof MIFileDataSource) {
                if (false == validateSyntax((MIFileDataSource)es)) {
                    return this.validatorReport;
                }
            }

            validateSemantic(es);
        }
        finally{
            clearThreadLocals();
        }

        return this.validatorReport;
    }

    public ValidatorReport getMIValidatorReport() {
        return validatorReport;
    }

    public AliasRuleWrapper getAliasRuleWrapper() {
        return aliasRuleWrapper;
    }

    public ConfidenceRuleWrapper getConfidenceRuleWrapper() {
        return confidenceRuleWrapper;
    }

    public AnnotationRuleWrapper getAnnotationRuleWrapper() {
        return annotationRuleWrapper;
    }

    public ExperimentRuleWrapper getExperimentRuleWrapper() {
        return experimentRuleWrapper;
    }

    public FeatureEvidenceRuleWrapper getFeatureRuleWrapper() {
        return featureRuleWrapper;
    }

    public InteractionEvidenceRuleWrapper getInteractionEvidenceRuleWrapper() {
        return interactionEvidenceRuleWrapper;
    }

    public InteractorRuleWrapper getInteractorRuleWrapper() {
        return interactorRuleWrapper;
    }

    public OrganismRuleWrapper getOrganismRuleWrapper() {
        return organismRuleWrapper;
    }

    public ParameterRuleWrapper getParameterRuleWrapper() {
        return parameterRuleWrapper;
    }

    public ParticipantEvidenceRuleWrapper getParticipantRuleWrapper() {
        return participantRuleWrapper;
    }

    public PublicationRuleWrapper getPublicationRuleWrapper() {
        return publicationRuleWrapper;
    }

    public XrefRuleWrapper getXrefRuleWrapper() {
        return xrefRuleWrapper;
    }

    public MIFileSyntaxListenerRule getSyntaxRule() {
        return syntaxRule;
    }

    public CvRuleWrapper getCvRuleWrapper() {
        return cvRuleWrapper;
    }

    public ChecksumRuleWrapper getChecksumRuleWrapper() {
        return checksumRuleWrapper;
    }

    public Collection<ObjectRule> getAllRules(){
        if (!validateObjectRule){
            return Collections.EMPTY_LIST;
        }
        Collection<ObjectRule> allRules = new ArrayList<ObjectRule>(60);
        allRules.addAll(getObjectRules());
        allRules.addAll(this.aliasRuleWrapper.getRules());
        allRules.addAll(this.confidenceRuleWrapper.getRules());
        allRules.addAll(this.experimentRuleWrapper.getRules());
        allRules.addAll(this.featureRuleWrapper.getRules());
        allRules.addAll(this.interactionEvidenceRuleWrapper.getRules());
        allRules.addAll(this.interactorRuleWrapper.getRules());
        allRules.addAll(this.organismRuleWrapper.getRules());
        allRules.addAll(this.parameterRuleWrapper.getRules());
        allRules.addAll(this.participantRuleWrapper.getRules());
        allRules.addAll(this.publicationRuleWrapper.getRules());
        allRules.addAll(this.xrefRuleWrapper.getRules());
        allRules.addAll(this.aliasRuleWrapper.getRules());
        allRules.addAll(this.cvRuleWrapper.getRules());
        allRules.addAll(this.checksumRuleWrapper.getRules());
        allRules.addAll(this.listenerRule.getRules());
        return allRules;
    }

    @Override
    protected void instantiateCvRuleManager(OntologyManager manager, CvMapping cvMappingRules) {
        super.setCvRuleManager(new MICvRuleManager(manager, cvMappingRules));
    }


    //////////////////////////////
    // Private

    /**
     * Runs the XML syntax validation and append messages to the report if any.
     *
     * @param report report in which errors should be reported.
     * @param interactionSource MI file source to be validated.
     * @return true is the validation passed, false otherwise.
     */
    private boolean runSyntaxValidation(ValidatorReport report, MIFileDataSource interactionSource) {

        if (log.isDebugEnabled()) log.debug("[File Syntax Validation] enabled via user preferences" );
        try {
            Collection<ValidatorMessage> messages = this.syntaxRule.check(interactionSource);
            return processSyntaxValidation(report, messages);

        } catch (Exception e) {
            log.error("Parsing error while running file syntax validation", e);
            String msg = "An unexpected error occured while running the syntax validation";
            if( e.getMessage() != null ) {
                msg = msg + ": " + e.getMessage();
            }
            final ValidatorMessage message = new ValidatorMessage(msg, MessageLevel.FATAL );
            report.getSyntaxMessages().add(message);

            return false; // abort
        }
    }

    private boolean processSyntaxValidation(ValidatorReport report, Collection<ValidatorMessage> messages) {
        if ( !messages.isEmpty() ) {

            if (log.isDebugEnabled()) log.debug( "[Syntax Validation] File with syntax errors/warnings." );
            report.getSyntaxMessages().addAll( messages );

            // check if we have FATAL message level
            for (ValidatorMessage message : messages){
                // we have one syntax fatal error. We don't need to do semantic validation
                if (message.getLevel().equals(MessageLevel.FATAL)){
                    report.setSyntaxValid(false);
                }
                else {
                    report.setSyntaxWarnings(true);
                }
            }

            // show an error message
            if (log.isDebugEnabled()) {
                log.debug( messages.size() + " message" + ( messages.size() > 1 ? "s" : "" ) + "" );
            }
            if (log.isDebugEnabled() && !report.isSyntaxValid()) log.debug( "Abort semantic validation." );

            // cluster all messages!!
            Collection<ValidatorMessage> clusteredValidatorMessages = ValidatorUtils.clusterByMessagesAndRules(report.getSyntaxMessages());
            report.setSyntaxMessages(clusteredValidatorMessages);
            return report.isSyntaxValid();
        } else {
            if (log.isDebugEnabled()) log.debug( "[Syntax Validation] File is valid." );
        }

        return true;
    }

    /**
     * Runs the semantic validation and append messages to the given report.
     *
     * @param report the report to be completed.
     * @param interactionSource the interaction source to be validated.
     * @throws ValidatorException
     */
    private void runSemanticValidation(ValidatorReport report, InteractionStream<InteractionEvidence> interactionSource) throws ValidatorException {

        // Build the collection of messages in which we will accumulate the output of the validator
        Collection<ValidatorMessage> messages = report.getSemanticMessages();

        try {
            // report grammar rules
            if (interactionSource instanceof MIFileDataSource){
                ((MIFileDataSource)interactionSource).setFileParserListener(this.listenerRule);
            }

            // then run the object rules (if any)
            if ( this.validateObjectRule || getCvRuleManager() != null) {
                processSemanticValidation(report, messages, interactionSource);
            }

            // report grammar rules
            if (interactionSource instanceof MIFileDataSource && this.listenerRule != null){
                messages.addAll(this.listenerRule.check((MIFileDataSource) interactionSource));
            }

            // cluster all messages!!
            Collection<ValidatorMessage> clusteredValidatorMessages = ValidatorUtils.clusterByMessagesAndRules(report.getSemanticMessages());
            report.setSemanticMessages(clusteredValidatorMessages);

        } catch(Exception e){
            StringBuffer messageBuffer = new StringBuffer();
            messageBuffer.append("\n The validator reported at least " + messages.size() + " semantic messages but the error while parsing the MI file need " +
                    "to be able to finish the semantic validation. Error message thrown : ");
            messageBuffer.append(ExceptionUtils.getMessage(e));

            MiContext context = new MiContext();

            messages.clear();
            messages.add( new ValidatorMessage( messageBuffer.toString(),
                    MessageLevel.FATAL,
                    context,
                    this.syntaxRule ) );
        }
    }

    private void processSemanticValidation(ValidatorReport report, Collection<ValidatorMessage> messages, InteractionStream<InteractionEvidence> interactionSource) throws ValidatorException {
        // now process interactions
        Iterator<InteractionEvidence> interactionIterator = interactionSource.getInteractionsIterator();
        int number = 0;
        while ( interactionIterator.hasNext() ) {
            InteractionEvidence interaction = interactionIterator.next();

            // check using cv mapping rules
            messages.addAll(super.checkCvMapping(interaction, "/interactionEvidence/"));

            // check object rules
            if (validateObjectRule){
                checkInteraction(messages, interaction);
            }
            number++;
        }

        // add count of interactions
        report.setInteractionCount(number);
    }

    private void checkInteraction(Collection<ValidatorMessage> messages, InteractionEvidence interaction) throws ValidatorException {
        // run the interaction specialized rules
        messages.addAll(this.interactionEvidenceRuleWrapper.check(interaction));
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(interaction));
        }

        // validate experiment if not done yet
        Experiment exp = interaction.getExperiment();
        if (exp != null && this.processObjects.add(exp)){
            checkExperiment(messages, exp);
        }

        // validate interaction type
        if (interaction.getInteractionType() != null){
            checkCv(messages, interaction.getInteractionType(), interaction, "interaction");
        }

        // validate participants
        for (ParticipantEvidence p : interaction.getParticipants()){
            checkParticipant(messages, p, interaction);
        }

        // validate confidences
        for (Confidence c : interaction.getConfidences()){
            checkConfidence(messages, c, interaction, "interaction");
        }

        // validate parameters
        for (Parameter p : interaction.getParameters()){
            checkParameter(messages, p, interaction, "interaction");
        }

        // validate identifier
        for (Xref ref : interaction.getIdentifiers()){
            checkXref(messages, ref, interaction, "interaction");
        }

        // validate xref
        for (Xref ref : interaction.getXrefs()){
            checkXref(messages, ref, interaction, "interaction");
        }

        // validate annotations
        for (Annotation a : interaction.getAnnotations()){
            checkAnnotation(messages, a, interaction, "interaction");
        }

        // validate checksums
        for (Checksum a : interaction.getChecksums()){
            checkChecksum(messages, a, interaction, "interaction");
        }
    }

    private void checkAnnotation(Collection<ValidatorMessage> messages, Annotation a, Object parent, String label) throws ValidatorException {
        Collection<ValidatorMessage> annotationMessages = this.annotationRuleWrapper.check(a);
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(a));
        }
        // add context
        for (ValidatorMessage message : annotationMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, label));
            messages.add(message);
        }
    }

    private void checkXref(Collection<ValidatorMessage> messages, Xref ref, Object parent, String label) throws ValidatorException {
        Collection<ValidatorMessage> refMessages = this.xrefRuleWrapper.check(ref);
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(ref));
        }
        // add context
        for (ValidatorMessage message : refMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, label));
            messages.add(message);
        }
    }

    private void checkParameter(Collection<ValidatorMessage> messages, Parameter c, Object parent, String label) throws ValidatorException {
        Collection<ValidatorMessage> paramMessages = this.parameterRuleWrapper.check(c);
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(c));
        }
        // add context
        for (ValidatorMessage message : paramMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, label));
            messages.add(message);
        }
    }

    private void checkConfidence(Collection<ValidatorMessage> messages, Confidence c , Object parent, String label) throws ValidatorException {
        Collection<ValidatorMessage> confMessages = this.confidenceRuleWrapper.check(c);
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(c));
        }
        // add context
        for (ValidatorMessage message : confMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, label));
            messages.add(message);
        }
    }

    private void checkCv(Collection<ValidatorMessage> messages, CvTerm c , Object parent, String label) throws ValidatorException {
        Collection<ValidatorMessage> confMessages = this.cvRuleWrapper.check(c);
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(c));
        }
        // add context
        for (ValidatorMessage message : confMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, label));
            messages.add(message);
        }
    }

    private void checkChecksum(Collection<ValidatorMessage> messages, Checksum c , Object parent, String label) throws ValidatorException {
        Collection<ValidatorMessage> confMessages = this.checksumRuleWrapper.check(c);
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(c));
        }
        // add context
        for (ValidatorMessage message : confMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, label));
            messages.add(message);
        }
    }

    private void checkExperiment(Collection<ValidatorMessage> messages, Experiment experiment) throws ValidatorException {
        // run the experiment specialized rules
        messages.addAll(this.experimentRuleWrapper.check(experiment));
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(experiment));
        }

        // run the publication specialized rules
        Publication publication = experiment.getPublication();
        if (publication != null){
            checkPublication(messages, publication, experiment);
        }

        // validate interaction detection method
        checkCv(messages, experiment.getInteractionDetectionMethod(), experiment, "experiment");

        // validate host organism
        if (experiment.getHostOrganism() != null){
            checkOrganism(messages, experiment.getHostOrganism(), experiment, "experiment");
        }

        // validate confidences
        for (Confidence c : experiment.getConfidences()){
            checkConfidence(messages, c, experiment, "experiment");
        }

        // validate xref
        for (Xref ref : experiment.getXrefs()){
            checkXref(messages, ref, experiment, "experiment");
        }

        // validate annotations
        for (Annotation a : experiment.getAnnotations()){
            checkAnnotation(messages, a, experiment, "experiment");
        }
    }

    private void checkPublication(Collection<ValidatorMessage> messages, Publication p, Experiment parent) throws ValidatorException {
        Collection<ValidatorMessage> confMessages = this.publicationRuleWrapper.check(p);
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(p));
        }
        // add context
        for (ValidatorMessage message : confMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, "experiment"));
            messages.add(message);
        }

        // validate identifier
        for (Xref ref : p.getIdentifiers()){
            checkXref(messages, ref, p, "publication");
        }

        // validate xref
        for (Xref ref : p.getXrefs()){
            checkXref(messages, ref, p, "publication");
        }

        // validate annotations
        for (Annotation a : p.getAnnotations()){
            checkAnnotation(messages, a, p, "publication");
        }
    }

    private void checkParticipant(Collection<ValidatorMessage> messages, ParticipantEvidence p, InteractionEvidence parent) throws ValidatorException {
        // run the participant specialized rules
        Collection<ValidatorMessage> partMessages = (this.participantRuleWrapper.check(p));
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(p));
        }
        // add context
        for (ValidatorMessage message : partMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, "interaction"));
            messages.add(message);
        }

        // validate interactor
        if (p.getInteractor() != null && this.processObjects.add(p.getInteractor())){
            checkInteractor(messages, p.getInteractor());
        }

        // validate features
        for (FeatureEvidence f : p.getFeatures()){
            checkFeature(messages, f, p, parent);
        }

        // validate biological role
        checkCv(messages, p.getBiologicalRole(), p, "participant");

        // validate experimental role
        checkCv(messages, p.getExperimentalRole(), p, "participant");

        // validate identification methods
        for (CvTerm term : p.getIdentificationMethods()){
            checkCv(messages, term, p, "participant");
        }

        // validate experimental preparations
        for (CvTerm term : p.getExperimentalPreparations()){
            checkCv(messages, term, p, "participant");
        }

        // validate confidences
        for (Confidence c : p.getConfidences()){
            checkConfidence(messages, c, p, "participant");
        }

        // validate parameters
        for (Parameter param : p.getParameters()){
            checkParameter(messages, param, p, "participant");
        }

        // validate alias
        for (Alias alias : p.getAliases()){
            checkAlias(messages, alias, p, "participant");
        }

        // validate xref
        for (Xref ref : p.getXrefs()){
            checkXref(messages, ref, p, "participant");
        }

        // validate annotations
        for (Annotation a : p.getAnnotations()){
            checkAnnotation(messages, a, p, "participant");
        }

        // validate expressed in organism
        if (p.getExpressedInOrganism() != null){
            checkOrganism(messages, p.getExpressedInOrganism(), p, "participant");
        }
    }

    private void checkOrganism(Collection<ValidatorMessage> messages, Organism o, Object parent, String label ) throws ValidatorException {

        // run the organism specialized rules
        Collection<ValidatorMessage> organismMessages = (this.organismRuleWrapper.check(o));
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(o));
        }
        // add context
        for (ValidatorMessage message : organismMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, label));
            messages.add(message);
        }
        // validate aliases
        for (Alias alias : o.getAliases()){
            checkAlias(messages, alias, o, "organism");
        }
    }

    private void checkAlias(Collection<ValidatorMessage> messages, Alias alias, Object parent, String label) throws ValidatorException {
        Collection<ValidatorMessage> aliasMessages = this.aliasRuleWrapper.check(alias);
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(alias));
        }
        // add context
        for (ValidatorMessage message : aliasMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, label));
            messages.add(message);
        }
    }

    private void checkInteractor(Collection<ValidatorMessage> messages, Interactor interactor) throws ValidatorException {
        // run the interactor specialized rules
        messages.addAll(this.interactorRuleWrapper.check(interactor));
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(interactor));
        }

        // validate organism
        if (interactor.getOrganism() != null){
            checkOrganism(messages, interactor.getOrganism(), interactor, "interactor");
        }

        // validate interactor type
        checkCv(messages, interactor.getInteractorType(), interactor, "interactor");

        // validate aliases
        for (Alias alias : interactor.getAliases()){
            checkAlias(messages, alias, interactor, "interactor");
        }

        // validate identifier
        for (Xref ref : interactor.getIdentifiers()){
            checkXref(messages, ref, interactor, "interactor");
        }

        // validate xref
        for (Xref ref : interactor.getXrefs()){
            checkXref(messages, ref, interactor, "interactor");
        }

        // validate annotations
        for (Annotation a : interactor.getAnnotations()){
            checkAnnotation(messages, a, interactor, "interactor");
        }

        // validate checksums
        for (Checksum a : interactor.getChecksums()){
            checkChecksum(messages, a, interactor, "interactor");
        }
    }

    private void checkFeature(Collection<ValidatorMessage> messages, FeatureEvidence f, ParticipantEvidence parent, InteractionEvidence parent2 ) throws ValidatorException {
        // run the feature specialized rules
        Collection<ValidatorMessage> partMessages = (this.featureRuleWrapper.check(f));
        // validate with other rules if any
        if (!getObjectRules().isEmpty()){
            messages.addAll(super.validate(f));
        }
        // add context
        for (ValidatorMessage message : partMessages){
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent, "participant"));
            ((MiContext)message.getContext()).addAssociatedContext(RuleUtils.buildContext(parent2, "interaction"));
            messages.add(message);
        }

        // validate feature type
        if (f.getType() != null){
            checkCv(messages, f.getType(), f, "feature");
        }

        // validate feature detection method
        for (CvTerm term : f.getDetectionMethods()){
            checkCv(messages, term, f, "feature");
        }

        // validate identifier
        for (Xref ref : f.getIdentifiers()){
            checkXref(messages, ref, f, "feature");
        }

        // validate xref
        for (Xref ref : f.getXrefs()){
            checkXref(messages, ref, f, "feature");
        }

        // validate annotations
        for (Annotation a : f.getAnnotations()){
            checkAnnotation(messages, a, f, "feature");
        }

        // validate feature range status
        for (Range range : f.getRanges()){
            checkCv(messages, range.getStart().getStatus(), f, "feature");
            checkCv(messages, range.getEnd().getStatus(), f, "feature");
        }
    }

    private void clearThreadLocals(){
        // remove validator CvContext
        ValidatorCvContext.removeInstance();
        // close GeneralCacheAdministrator for DatabaseAccessionRule
        DatabaseAccessionRule.closeGeneralCacheAdministrator();
        // close GeneralCacheAdministrator for OntologyManagerContext
        OntologyManagerContext.removeInstance();
    }

    private InteractionStream<InteractionEvidence> parseDataSource(File file) throws IOException {
        PsiJami.initialiseInteractionEvidenceSources();
        MIDataSourceFactory dataSourceFactory = MIDataSourceFactory.getInstance();
        MIDataSourceOptionFactory optionsFactory = MIDataSourceOptionFactory.getInstance();

        // by default we always have interaction evidences
        InteractionStream<InteractionEvidence> interactionSource = dataSourceFactory.getInteractionSourceWith(optionsFactory.getDefaultOptions(file));

        if (interactionSource == null){
            throw new IOException("Cannot parse the file "+file.getName());
        }
        return interactionSource;
    }
}