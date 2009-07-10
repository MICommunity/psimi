package psidev.psi.mi.validator.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.validator.ValidatorReport;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.Validator;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;
import psidev.psi.tools.validator.preferences.UserPreferences;
import psidev.psi.tools.validator.schema.SaxMessage;
import psidev.psi.tools.validator.schema.SaxReport;
import psidev.psi.tools.validator.schema.SaxValidatorHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;

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

        // Run CV mapping Rules first (if any)
        if( getCvRuleManager() != null ) {
            if( entries == null ) {
                PsimiXmlLightweightReader psiReader = new PsimiXmlLightweightReader( nis );
                entries = psiReader.getIndexedEntries();
            }

            for ( IndexedEntry entry : entries ) {
                Iterator<Interaction> interactionIterator = entry.unmarshallInteractionIterator();
                while ( interactionIterator.hasNext() ) {
                    Interaction interaction = interactionIterator.next();

                    // check using cv mapping rules
                    Collection<ValidatorMessage> interactionMessages =
                            super.checkCvMapping( interaction, "/entrySet/entry/interactionList/interaction/" );

                    interactionMessages = convertToMi25Messages( interactionMessages, interaction );

                    // append messages to the global collection
                    messages.addAll( interactionMessages );
                }
            }
        }


        // then run the object rules (if any)
        final List<ObjectRule> rules = getObjectRules();
        if ( rules != null && !rules.isEmpty() ) {
            // now that we know that we have at least one rule, it is worth parsing the data.
            if( entries == null ) {
                PsimiXmlLightweightReader psiReader = new PsimiXmlLightweightReader( nis );
                entries = psiReader.getIndexedEntries();
            }
            
            for ( IndexedEntry entry : entries ) {

                final Iterator<ExperimentDescription> experimentIterator = entry.unmarshallExperimentIterator();
                while ( experimentIterator.hasNext() ) {
                    ExperimentDescription experiment = experimentIterator.next();

                    // run the experiment specialized rules
                    final Collection<ValidatorMessage> validatorMessages = super.validate( experiment );
                    if( !validatorMessages.isEmpty() ) {
                        long lineNumber = entry.getExperimentLineNumber( experiment.getId() );
                        updateLineNumber( validatorMessages, lineNumber );
                    }

                    messages.addAll( validatorMessages );
                }

                // now process interactions
                Iterator<Interaction> interactionIterator = entry.unmarshallInteractionIterator();
                while ( interactionIterator.hasNext() ) {
                    Interaction interaction = interactionIterator.next();

                    // run the interaction specialized rules
                    Collection<ValidatorMessage> interactionMessages = super.validate( interaction );

                    // add line number
                    if( !interactionMessages.isEmpty() ) {
                        long lineNumber = entry.getInteractionLineNumber( interaction.getId() );
                        updateLineNumber( interactionMessages, lineNumber );
                    }

                    // append messages to the global collection
                    messages.addAll( interactionMessages );
                }
            }
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
            final Mi25Context context = new Mi25Context();
            context.setInteractionId( interaction.getId() );
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
}