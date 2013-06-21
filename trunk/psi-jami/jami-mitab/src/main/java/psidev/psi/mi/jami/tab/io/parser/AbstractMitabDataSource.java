package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.datasource.*;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * abstract class for Mitab datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public abstract class AbstractMitabDataSource<T extends Interaction, B extends BinaryInteraction, P extends Participant> implements MIFileDataSource, StreamingInteractionSource, MitabParserListener{

    private MitabLineParser<B,P> lineParser;
    private boolean isInitialised = false;
    private Collection<FileSourceError> errors;

    private File originalFile;
    private InputStream originalStream;
    private Reader originalReader;

    private boolean isConsumed = false;

    /**
     * Empty constructor for the factory
     */
    public AbstractMitabDataSource(){
        errors = new ArrayList<FileSourceError>();
    }

    public AbstractMitabDataSource(File file) throws IOException {

        initialiseFile(file);
        isInitialised = true;
        errors = new ArrayList<FileSourceError>();
    }

    public AbstractMitabDataSource(InputStream input) {

        initialiseInputStream(input);
        isInitialised = true;
        errors = new ArrayList<FileSourceError>();
    }

    public AbstractMitabDataSource(Reader reader) {

        initialiseReader(reader);
        isInitialised = true;
        errors = new ArrayList<FileSourceError>();
    }

    public void initialiseContext(Map<String, Object> options) {
        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the MitabDataSource should contain at least "+ MIDataSourceFactory.INPUT_FILE_OPTION_KEY
                    + " or " + MIDataSourceFactory.INPUT_STREAM_OPTION_KEY + " or " + MIDataSourceFactory.READER_OPTION_KEY+ " to know where to load the interactions from.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(MIDataSourceFactory.INPUT_FILE_OPTION_KEY)){
            initialiseFile((File) options.get(MIDataSourceFactory.INPUT_FILE_OPTION_KEY));
        }
        else if (options.containsKey(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY)){
            initialiseInputStream((InputStream) options.get(MIDataSourceFactory.INPUT_STREAM_OPTION_KEY));
        }
        else if (options.containsKey(MIDataSourceFactory.READER_OPTION_KEY)){
            initialiseReader((Reader) options.get(MIDataSourceFactory.READER_OPTION_KEY));
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the Mitab25Writer should contain at least "+InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }

        isInitialised = true;
    }

    public Collection<FileSourceError> getDataSourceErrors() {
        return errors;
    }

    public void close() {
        if (isInitialised){
            if (this.originalStream != null){
                try {
                    this.originalStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Impossible to close the original stream", e);
                }
            }
            if (this.originalReader != null){
                try {
                    this.originalReader.close();
                } catch (IOException e) {
                    throw new RuntimeException("Impossible to close the original reader", e);
                }
            }
            this.originalFile = null;
            this.lineParser = null;
            this.errors.clear();
            isConsumed = false;
            isInitialised = false;
        }
    }

    public boolean validateSyntax() {
        if (isConsumed){
            return errors.isEmpty();
        }
        else{
            // read the datasource
            Iterator<T> interactionIterator = getInteractionsIterator();
            while(interactionIterator.hasNext()){
                interactionIterator.next();
            }
            isConsumed = true;
            return errors.isEmpty();
        }
    }

    public void onTextFoundInIdentifier(MitabXref xref, int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: The syntax for Unique identifiers and alternative identifiers should be db:id and not db:id(text).", xref));
        }
    }

    public void onMissingCvTermName(CvTerm term, int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: The syntax for the cv term column "+mitabColumn+" should be db:id(name) and not db:id.", new DefaultFileSourceContext(new MitabSourceLocator(line, column, mitabColumn))));
        }
    }

    public void onTextFoundInConfidence(MitabConfidence conf, int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: The syntax for the interaction confidences column should be confidence_type:value and not confidence_type:value(text).", conf));
        }
    }

    public void onMissingExpansionId(MitabCvTerm expansion, int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: The syntax for the complex expansion column should be db:id(name) and not just a name", expansion));
        }
    }

    public void onInvalidSyntax(int line, int column, int mitabColumn, Exception e) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.invalid_syntax.toString(), "ERROR: We have an invalid syntax in mitab column "+mitabColumn+"("+e.getClass().toString()+"). The invalid element will be ignored.", new DefaultFileSourceContext(new MitabSourceLocator(line, column, mitabColumn))));
        }
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: We expect only one unique identifiers and we found " +ids.size(), ids.iterator().next()));
        }
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.invalid_syntax.toString(), "ERROR: The unique identifier column should not be empty when describing an interactor.", new DefaultFileSourceContext(new MitabSourceLocator(line, column, mitabColumn))));
        }
    }

    public void onEmptyAliases(int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: The alias column should not be empty when describing an interactor.", new DefaultFileSourceContext(new MitabSourceLocator(line, column, mitabColumn))));
        }
    }

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.invalid_syntax.toString(), "ERROR: The unique identifier, alternative identifiers and aliases columns were empty. We expect at least one identifiers and recommend at least one alias.", new DefaultFileSourceContext(new MitabSourceLocator(line, column, mitabColumn))));
        }
    }

    public void onAliasFoundInAlternativeIds(MitabXref ref, int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: the alternative identifier ("+ref.toString()+") should be moved to the aliases column and will be loaded as an alias.", ref));
        }
    }

    public void onChecksumFoundInAlternativeIds(MitabXref ref, int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: the alternative identifier ("+ref.toString()+") should be moved to the checksum column and will be loaded as a checksum.", ref));
        }
    }

    public void onChecksumFoundInAliases(MitabAlias alias, int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: the alias ("+alias.toString()+") should be moved to the checksum column and will be loaded as a checksum.", alias));
        }
    }

    public void onSeveralCvTermFound(Collection<MitabCvTerm> terms) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: We expect only one cv term and we found " +terms.size()+". Only the first term will be taken into account", terms.iterator().next()));
        }
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: We expect only one organism for each interactor and we found " +organisms.size()+". Only the first organism will be taken into account", organisms.iterator().next()));
        }
    }

    public void onParticipantWithoutInteractorDetails(int line, int column, int mitabColumn) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.invalid_syntax.toString(), "ERROR: the participant does not have any interactor details and is loaded with an unknown interactor", new DefaultFileSourceContext(new MitabSourceLocator(line, column, mitabColumn))));
        }
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: We expect only one stoichiometry for each participant and we found " +stoichiometry.size()+". Only the first stoichiometry will be taken into account", stoichiometry.iterator().next()));
        }
    }

    public void onInteractionWithoutParticipants(int line) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.invalid_syntax.toString(), "ERROR: the interaction does not have any participants. We expect at least one participant.", new DefaultFileSourceContext(new MitabSourceLocator(line, 0, 0))));
        }
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: We expect only one author and we found " +authors.size()+". Only the first author will be taken into account", authors.iterator().next()));
        }
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: We expect only one source and we found " +sources.size()+". Only the first source will be taken into account", sources.iterator().next()));
        }
    }

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: We expect only one host organism and we found " +organisms.size()+". Only the first host organism will be taken into account", organisms.iterator().next()));
        }
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: We expect only one created date and we found " +dates.size()+". Only the first created date will be taken into account", dates.iterator().next()));
        }
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        if (!isConsumed){
            this.errors.add(new FileSourceError(FileParsingErrorType.syntax_warning.toString(), "WARN: We expect only one update date and we found " +dates.size()+". Only the first update date will be taken into account", dates.iterator().next()));
        }
    }

    public void onEndOfFile() {
        isConsumed = true;
    }

    public Iterator<T> getInteractionsIterator() {
        // reset parser if possible
        if (isConsumed){
           reInit();
        }
        return createMitabIterator();
    }

    protected MitabLineParser<B,P> getLineParser() {
        return lineParser;
    }

    protected void setLineParser(MitabLineParser<B,P> lineParser) {
        this.lineParser = lineParser;
    }

    protected abstract void initialiseMitabLineParser(Reader reader);

    protected abstract void initialiseMitabLineParser(File file);

    protected abstract void initialiseMitabLineParser(InputStream input);

    protected abstract Iterator<T> createMitabIterator();

    protected void reInit(){
        if (isInitialised){
            if (this.originalFile != null){
                // close the previous stream
                if (this.originalStream != null){
                    try {
                        this.originalStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // reinitialise mitab parser
                try {
                    this.originalStream = new BufferedInputStream(new FileInputStream(this.originalFile));
                    this.lineParser.ReInit(this.originalStream);

                } catch (FileNotFoundException e) {
                    throw new RuntimeException("We cannot open the file " + this.originalFile.getName(), e);
                }
            }
            else if (this.originalStream != null){
                // reinit line parser if inputStream can be reset
                if (this.originalStream.markSupported()){
                    try {
                        this.originalStream.reset();
                        this.lineParser.ReInit(this.originalStream);
                    } catch (IOException e) {
                        throw new RuntimeException("The inputStream has been consumed and cannot be reset", e);
                    }
                }
                else {
                    throw new RuntimeException("The inputStream has been consumed and cannot be reset");
                }
            }
            else if (this.originalReader != null){
                // reinit line parser if reader can be reset
                if (this.originalReader.markSupported()){
                    try {
                        this.originalReader.reset();
                        this.lineParser.ReInit(this.originalReader);
                    } catch (IOException e) {
                        throw new RuntimeException("The reader has been consumed and cannot be reset", e);
                    }
                }
                else {
                    throw new RuntimeException("The reader has been consumed and cannot be reset");
                }
            }
            isConsumed = false;
        }
    }

    private void initialiseReader(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("The reader cannot be null.");
        }
        this.originalFile = null;
        this.originalReader = reader;
        this.originalStream = null;

        initialiseMitabLineParser(reader);
    }

    private void initialiseInputStream(InputStream input) {
        if (input == null){
            throw new IllegalArgumentException("The input stream cannot be null.");
        }
        this.originalFile = null;
        this.originalReader = null;
        this.originalStream = input;

        initialiseMitabLineParser(input);
    }

    private void initialiseFile(File file)  {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canRead()){
            throw new IllegalArgumentException("Does not have the permissions to read the file "+file.getAbsolutePath());
        }
        this.originalFile = file;
        this.originalReader = null;
        this.originalStream = null;

        initialiseMitabLineParser(file);
    }
}
