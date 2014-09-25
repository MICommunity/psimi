package psidev.psi.mi;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import psidev.psi.mi.validator.ValidatorReport;
import psidev.psi.mi.validator.extension.MiValidator;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.preferences.UserPreferences;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A command line client for the PSI-MI Schema Validator.
 */
public class ValidatorClient {

    /**
     * The MIMIX scope
     */
    public final static String MIMIX = "MIMIX";
    /**
     * the IMEX scope
     */
    public final static String IMEX = "IMEX";
    /**
     * the SYNTAX syntax scope
     */
    public final static String SYNTAX = "SYNTAX";
    /**
     * the CV mapping scope
     */
    public final static String CV = "cv";

    public final static String XML_EXTENSION = ".xml";

    public final static String TEXT_EXTENSION = ".txt";
    public final static String TSV_EXTENSION = ".tsv";
    public final static String CSV_EXTENSION = ".csv";

    public final static String ZIP_EXTENSION = ".zip";

    /**
     *
     * @param validationScope : the scope
     * @return a Mi25Validator instance set up with the appropriate rules depending on the validation scope
     * @throws ValidatorException
     * @throws OntologyLoaderException
     * @throws IOException
     */
    public static MiValidator buildValidator(String validationScope) throws ValidatorException, OntologyLoaderException, IOException {

        // the ontology
        InputStream ontologyConfig = null;
        // the CV mapping file
        InputStream cvMappingConfig = null;
        // the object rules
        InputStream objectRuleConfig = null;

        MiValidator validator = null;
        try{
            // the ontology
            ontologyConfig = MiValidator.class.getResource( "/config/psi_mi/ontologies.xml" ).openStream();
            // the CV mapping file
            cvMappingConfig = MiValidator.class.getResource( "/config/psi_mi/cv-mapping.xml" ).openStream();
            // the object rules
            objectRuleConfig = null;

            // SYNTAX scope
            if (validationScope.equalsIgnoreCase(SYNTAX)){
                validator = new MiValidator( ontologyConfig, cvMappingConfig, null );
            }
            // CV scope
            else if (validationScope.equalsIgnoreCase(CV)) {

                validator = new MiValidator(ontologyConfig, cvMappingConfig, null);
            }
            // MIMIX scope
            else if (validationScope.equalsIgnoreCase(MIMIX)) {
                objectRuleConfig = MiValidator.class.getResource( "/config/psi_mi/mimix-rules.xml" ).openStream();

                validator = new MiValidator(ontologyConfig, cvMappingConfig, objectRuleConfig);
            }
            // IMEX scope
            else if (validationScope.equalsIgnoreCase(IMEX)) {
                objectRuleConfig = MiValidator.class.getResource( "/config/psi_mi/imex-rules.xml" ).openStream();

                validator = new MiValidator(ontologyConfig, cvMappingConfig, objectRuleConfig);
            }
            else {
                System.err.println( validationScope + " is not a valid scope. You can only choose SYNTAX, CV, MIMIX or IMEX." );
                System.exit( 1 );
            }
        }
        finally {
            if (ontologyConfig != null){
                try{
                    ontologyConfig.close();
                }
                catch (IOException e){
                    System.out.println(ExceptionUtils.getFullStackTrace(e));
                }
            }
            if (cvMappingConfig != null){
                try{
                    cvMappingConfig.close();
                }
                catch (IOException e){
                    System.out.println(ExceptionUtils.getFullStackTrace(e));
                }
            }
            if (objectRuleConfig != null){
                try{
                    objectRuleConfig.close();
                }
                catch (IOException e){
                    System.out.println(ExceptionUtils.getFullStackTrace(e));
                }
            }
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
     * @return  true if the semantic messages are enabled (scope is not SYNTAX)
     */
    public static boolean isSemanticValidationEnabled(String scope){
        if (SYNTAX.equalsIgnoreCase(scope)){
            return true;
        }
        else if (CV.equalsIgnoreCase(scope)){
            return true;
        }
        else if (MIMIX.equalsIgnoreCase(scope)){
            return true;
        }
        else if (IMEX.equalsIgnoreCase(scope)){
            return true;
        }
        return false;
    }

    public static boolean isFATALMessage(ValidatorMessage message){

        return MessageLevel.FATAL.equals(message.getLevel());
    }

    public static boolean isERRORMessage(ValidatorMessage message){

        return MessageLevel.ERROR.equals(message.getLevel());
    }

    public static boolean isWARNINGMessage(ValidatorMessage validatorMessage){

        return MessageLevel.WARN.equals(validatorMessage.getLevel());
    }

    public static boolean isINFOMessage(ValidatorMessage validatorMessage){

        return MessageLevel.INFO.equals(validatorMessage.getLevel());
    }

    public static MessageLevel getHigherMessageLevelFrom(Collection<ValidatorMessage> validatorMessages){

        boolean hasFatal = false;
        boolean hasError = false;
        boolean hasWarning = false;
        boolean hasInfo = false;

        for (ValidatorMessage message : validatorMessages){

            if (isFATALMessage(message)){
                hasFatal = true;
            }
            else if (isERRORMessage(message)){
                hasError = true;
            }
            else if (isWARNINGMessage(message)){
                hasWarning = true;
            }
            else if (isINFOMessage(message)){
                hasInfo = true;
            }
        }


        if (hasFatal){
            return MessageLevel.FATAL;
        }
        else if (hasError){
            return MessageLevel.ERROR;
        }
        else if (hasWarning){
            return MessageLevel.WARN;
        }
        else if (hasInfo){
            return MessageLevel.INFO;
        }

        return null;
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
    public static void validateFile(File file, MiValidator validator, String levelThreshold, String scope) throws ValidatorException, IOException {

        if (file == null){
            throw new ValidatorException("We can't validate a file which is null.");
        }
        if (validator == null){
            throw new ValidatorException("We can't validate the file " + file.getName() + " if the validator is null.");
        }

        // validate the file
        ValidatorReport validatorReport = validator.validate( file );

        // clean the messages with a level lower than the threshold value
        cleanLowerLevelMessages(validatorReport, levelThreshold, scope);

        // the name of the file
        String exactFileName = file.getName();
        int indexExtension = exactFileName.lastIndexOf(".");
        String fileName = exactFileName;

        if (indexExtension != -1){
            if (validatorReport.hasSyntaxMessages() || validatorReport.hasSemanticMessages()){

                Collection<ValidatorMessage> totalMessages = validatorReport.getSemanticMessages();
                totalMessages.addAll(validatorReport.getSyntaxMessages());

                MessageLevel extension = getHigherMessageLevelFrom(totalMessages);

                if (extension != null){
                    // the name of the file without its extension
                    fileName = exactFileName.substring(0, indexExtension) + "_"+extension.toString()+".log";
                }
                else {
                    // the name of the file without its extension
                    fileName = exactFileName.substring(0, indexExtension) + "_invalid.log";
                }
            }
            else {
                // the name of the file without its extension
                fileName = exactFileName.substring(0, indexExtension) + "_valid.log";
            }
        }

        int indexName = file.getPath().indexOf(exactFileName);
        // the path of the directory where the file will be
        String path = file.getPath().substring(0, indexName);
        // the outout file
        File output = new File(path+"report-"+fileName);
        Writer writer = new BufferedWriter(new FileWriter(output));

        try{
            // finally, we set the messages obtained (if any) to the report
            if (validatorReport.hasSyntaxMessages() || validatorReport.hasSemanticMessages()) {
                writer.write("Document contained " +validatorReport.getInteractionCount()+ " interactions.");
                writer.write("Validator reported " + validatorReport.getSyntaxMessages().size() + " syntax messages \n" );
                writer.write("Validator reported " + validatorReport.getSemanticMessages().size() + " semantic messages \n" );
                writer.flush();

                if (validatorReport.hasSyntaxMessages()){
                    for (ValidatorMessage message : validatorReport.getSyntaxMessages()){

                        writer.write(message.getLevel().toString() + " : " + message.getMessage() + "\n");
                        if (message.getContext() != null){
                            writer.write(message.getContext() + "\n");
                        }
                        writer.flush();
                    }
                    writer.write("\n");
                }
                if (validatorReport.isSyntaxValid()) {
                    writer.write("File syntax valid. \n \n");
                    writer.flush();
                }
                else if (validatorReport.hasSyntaxWarnings()) {
                    writer.write("File syntax valid but with warnings. \n \n");
                    writer.flush();
                }
                else{
                    writer.write("File syntax invalid. \n");
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
                        if (message.getRule() != null){
                            writer.write(message.getRule().getName() + " : ");
                        }
                        writer.write(message.getLevel().toString() + "\n");
                        writer.write(message.getMessage() + "\n");
                        if (message.getContext() != null){
                            writer.write(message.getContext() + "\n");
                        }
                        writer.write("\n");
                        writer.flush();
                    }
                    writer.write("\n");
                    writer.write(status + "\n");

                }
            }
            else {

                writer.write("Document is valid" + "\n");
                writer.write("Document contained " +validatorReport.getInteractionCount()+ " interactions.");
                writer.flush();
            }
        }
        finally {
            writer.close();
        }
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
            MiValidator validator = buildValidator(validationScope);

            // validate SYNTAX and ZIP xmlFiles
            String [] xmlExtension = {"xml"};
            String [] zipExtension = {"zip"};
            boolean recursive = true;

            Collection<File> miFiles = new ArrayList<File>();
            Collection<File> zipFiles = new ArrayList<File>();

            if (dir.isDirectory()){
                miFiles = FileUtils.listFiles(dir, xmlExtension, recursive);
                zipFiles = FileUtils.listFiles(dir, zipExtension, recursive);
            }
            else if (dir.getName().endsWith(XML_EXTENSION)
                    || dir.getName().endsWith(TEXT_EXTENSION)
                    || dir.getName().endsWith(TSV_EXTENSION)
                    || dir.getName().endsWith(CSV_EXTENSION)) {
                miFiles.add(dir);
            }
            else if (dir.getName().endsWith(ZIP_EXTENSION)) {
                zipFiles.add(dir);
            }

            for (File file : miFiles) {

                System.out.println("Validate File : " + file.getAbsolutePath());
                validateFile(file, validator, levelThreshold, validationScope);
            }

            final int BUFFER = 2048;

            for (File zip : zipFiles){
                System.out.println("Unzip File : " + zip.getAbsolutePath());

                File parentDirectory = zip.getParentFile();

                ZipFile zis = new ZipFile(zip);

                Enumeration<ZipEntry> zipEntries = (Enumeration<ZipEntry>) zis.entries();

                while (zipEntries.hasMoreElements())
                {
                    ZipEntry entry = zipEntries.nextElement();

                    if (entry.isDirectory()){
                        System.out.println("Creating directory : " +entry.getName());

                        File directory = new File( parentDirectory, entry.getName() );
                        if ( !directory.exists() ) {
                            if ( !directory.mkdirs() ) {
                                throw new IOException( "Cannot create temp directory: " + directory.getAbsolutePath() );
                            }
                        }
                    }
                    else{

                        if (entry.getName().endsWith(XML_EXTENSION)
                                || entry.getName().endsWith(TEXT_EXTENSION)
                                || entry.getName().endsWith(TSV_EXTENSION)
                                || entry.getName().endsWith(CSV_EXTENSION)){
                            System.out.println("Extracting: " +entry.getName());

                            String entryName = parentDirectory.getAbsolutePath() + File.separator + entry.getName();
                            // write the files to the disk
                            FileOutputStream fos = new
                                    FileOutputStream(entryName);
                            BufferedOutputStream dest = new
                                    BufferedOutputStream(fos, BUFFER);

                            try{
                                InputStream inputStream = zis.getInputStream(entry);
                                try{
                                    IOUtils.copy(inputStream, dest);
                                }
                                finally {
                                    inputStream.close();
                                }

                                dest.flush();
                            }
                            finally {
                                dest.close();
                            }

                            File fileToValidate = new File(entry.getName());
                            System.out.println("Validate File : " + fileToValidate.getAbsolutePath());
                            validateFile(fileToValidate, validator, levelThreshold, validationScope);
                        }
                    }

                }
                zis.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
