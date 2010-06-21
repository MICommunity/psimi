package psidev.psi.mi;

import org.apache.commons.io.FileUtils;
import psidev.psi.mi.validator.ValidatorReport;
import psidev.psi.mi.validator.extension.Mi25Validator;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.preferences.UserPreferences;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A command line client for the PSI-MI Schema Validator.
 */
public class ValidatorClient {

    /**
     * The mimix scope
     */
    public final static String mimix = "mimix";
    /**
     * the imex scope
     */
    public final static String imex = "imex";
    /**
     * the xml syntax scope
     */
    public final static String xml = "xml";
    /**
     * the CV mapping scope
     */
    public final static String cv = "cv";

    /**
     *
     * @param validationScope : the scope
     * @return a Mi25Validator instance set up with the appropriate rules depending on the validation scope
     * @throws ValidatorException
     * @throws OntologyLoaderException
     * @throws IOException
     */
    public static Mi25Validator buildValidator(String validationScope) throws ValidatorException, OntologyLoaderException, IOException {

        Mi25Validator validator = null;
        // the ontology
        InputStream ontologyConfig = Mi25Validator.class.getResource( "/config/ontologies.xml" ).openStream();
        // the cv mapping file
        InputStream cvMappingConfig = Mi25Validator.class.getResource( "/config/cv-mapping.xml" ).openStream();
        // the object rules
        InputStream objectRuleConfig = null;

        // xml scope
        if (validationScope.equalsIgnoreCase(xml)){
            validator = new Mi25Validator( ontologyConfig, cvMappingConfig, null );
        }
        // cv scope
        else if (validationScope.equalsIgnoreCase(cv)) {

            validator = new Mi25Validator(ontologyConfig, cvMappingConfig, null);
        }
        // mimix scope
        else if (validationScope.equalsIgnoreCase(mimix)) {
            objectRuleConfig = Mi25Validator.class.getResource( "/config/mimix-rules.xml" ).openStream();

            validator = new Mi25Validator(ontologyConfig, cvMappingConfig, objectRuleConfig);
        }
        // imex scope
        else if (validationScope.equalsIgnoreCase(imex)) {
            objectRuleConfig = Mi25Validator.class.getResource( "/config/imex-rules.xml" ).openStream();

            validator = new Mi25Validator(ontologyConfig, cvMappingConfig, objectRuleConfig);
        }
        else {
            System.err.println( validationScope + " is not a valid scope. You can only choose xml, cv, mimix or imex." );
            System.exit( 1 );
        }

        // validator successfully created
        if (validator != null){
            // set user preferences
            UserPreferences preferences = new UserPreferences();
            preferences.setKeepDownloadedOntologiesOnDisk( true );
            preferences.setSaxValidationEnabled( true );
            validator.setUserPreferences( preferences );
        }

        return validator;
    }

    /**
     * Remove from the collection of validator messages the messages with a lower message level than the threshold level chosen by the user
     * @param messages : a collextion of ValidatorMessages
     * @param thresholdLevel : the level chosen by the user
     */
    public static void removeLowerMessagesFrom(Collection<ValidatorMessage> messages, MessageLevel thresholdLevel){
        // list of messages to remove
        Collection<ValidatorMessage> messagesToRemove = new ArrayList<ValidatorMessage>();

        for (ValidatorMessage message : messages){
            // if the level is lower than the threshold value, remove the message
            if (message.getLevel().isLower(thresholdLevel)){
                messagesToRemove.add(message);
            }
        }

        messages.removeAll(messagesToRemove);
    }

    /**
     * remove the messages of the validatorReport with a lower level than the threshold value. If the semantic messages are not enabled in the scope,
     * will remove them as well.
     * @param report : the ValidatorReport
     * @param levelThreshold : the level chosen by the user
     * @param scope : the scope of the validator
     */
    public static void cleanLowerLevelMessages(ValidatorReport report, String levelThreshold, String scope ){
        if (levelThreshold == null){
            throw new IllegalArgumentException("The level should not be null : can be either DEBUG, INFO, WARN, ERROR, FATAL.");
        }

        // get the thershold level as MessageLevel
        MessageLevel level = MessageLevel.forName(levelThreshold);

        if (report != null){
            Collection<ValidatorMessage> syntaxMessages = report.getSyntaxMessages();
            Collection<ValidatorMessage> semanticMessages = report.getSemanticMessages();

            removeLowerMessagesFrom(syntaxMessages, level);
            if (isSemanticValidationEnabled(scope)){
                removeLowerMessagesFrom(semanticMessages, level);
            }
            else {
                semanticMessages.clear();
            }
        }
    }

    /**
     *
     * @param scope : the scope of validation
     * @return  true if the semantic messages are enabled (scope is not xml)
     */
    public static boolean isSemanticValidationEnabled(String scope){
        if (xml.equalsIgnoreCase(scope)){
            return true;
        }
        else if (cv.equalsIgnoreCase(scope)){
            return true;
        }
        else if (mimix.equalsIgnoreCase(scope)){
            return true;
        }
        else if (imex.equalsIgnoreCase(scope)){
            return true;
        }
        return false;
    }

    /**
     * Validate the file and write the results in the same directory with a name report-filename.txt
     * @param file : the file to validate
     * @param validator : the validator
     * @param levelThreshold : the threshold level
     * @param scope : the scope of validation
     * @throws ValidatorException
     * @throws IOException
     */
    public static void validateFile(File file, Mi25Validator validator, String levelThreshold, String scope) throws ValidatorException, IOException {

        if (file == null){
            throw new ValidatorException("We can't validate a file which is null.");
        }
        if (validator == null){
            throw new ValidatorException("We can't validate the file " + file.getName() + " if the validator is null.");
        }

        // the name of the file
        String exactFileName = file.getName();
        int indexExtension = exactFileName.indexOf("xml");
        String fileName = exactFileName;

        if (indexExtension != -1){
            // the name of the file without its extension
            fileName = exactFileName.substring(0, indexExtension) + "txt";
        }

        int indexName = file.getPath().indexOf(exactFileName);
        // the path of the directory where the file will be
        String path = file.getPath().substring(0, indexName);

        // the outout file
        File output = new File(path+"report-"+fileName);
        FileWriter writer = new FileWriter(output);

        // validate the file
        ValidatorReport validatorReport = validator.validate( file );

        // clean the messages with a level lower than the threshold value
        cleanLowerLevelMessages(validatorReport, levelThreshold, scope);

        writer.write("Validator reported " + validatorReport.getSyntaxMessages().size() + " syntax messages \n" );
        writer.write("Validator reported " + validatorReport.getSemanticMessages().size() + " semantic messages \n" );
        writer.flush();

        // finally, we set the messages obtained (if any) to the report
        if (validatorReport.hasSyntaxMessages()) {
            writer.write("XML syntax validation failed. \n");

            for (ValidatorMessage message : validatorReport.getSyntaxMessages()){
                writer.write(message.getLevel().toString() + ": " + message.getMessage() + "\n");
                writer.write(message.getContext() + "\n");
                writer.flush();
            }
        } else {
            writer.write("XML syntax valid. \n \n");
            writer.flush();
        }

        if ( validatorReport.hasSemanticMessages()) {

            // we need to determine the status of the semantics validation.
            // If there are no validatorMessages, the status is "valid" (already set).
            // If there are messages, but all of them are warnings, the status will be "warnings".
            // If there are error or fatal messages, the status, the status will be failed
            String status = null;

            for (ValidatorMessage message : validatorReport.getSemanticMessages()){
                if (message.getLevel() == MessageLevel.WARN && status == null){
                    status = "Validated with warnings";
                }
                else if (message.getLevel() == MessageLevel.ERROR && status == null){
                    status = "Validation failed";
                }
                writer.write(message.getLevel().toString() + ": " + message.getMessage() + "\n");
                writer.write(message.getContext() + "\n");
                writer.flush();
            }

            writer.write(status + "\n");

        } else {

            writer.write("Document is valid" + "\n");
            writer.flush();
        }
        writer.close();
    }

    /**
     * Run the ValidatorClient
     * @param args
     */
    public static void main( String[] args ) {

        // three possible arguments
        if( args.length != 3 ) {
            System.err.println( "Usage: ValidatorClient <file> <scope> <level.threshold>" );
            System.exit( 1 );
        }
        final String filename = args[0];
        final String validationScope = args[1];
        final String levelThreshold = args[2];

        System.out.println( "filename = " + filename );
        System.out.println( "validationScope = " + validationScope );
        System.out.println( "levelThreshold = " + levelThreshold );

        File dir = new File(filename);

        try {
            Mi25Validator validator = buildValidator(validationScope);

            String [] xmlExtension = {"xml"};
            boolean recursive = true;

            Collection<File> files = new ArrayList<File>();
            if (dir.isDirectory()){
                files = FileUtils.listFiles(dir, xmlExtension, recursive);
            }
            else {
                files.add(dir);
            }

            for (File file : files) {

                System.out.println("Validate File : " + file.getAbsolutePath());
                validateFile(file, validator, levelThreshold, validationScope);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
