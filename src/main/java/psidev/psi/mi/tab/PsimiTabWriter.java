package psidev.psi.mi.tab;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabWriterUtils;
import psidev.psi.mi.tab.model.builder.DocumentDefinition;
import psidev.psi.mi.tab.model.builder.PsimiTab;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.*;
import java.util.Collection;

import static au.com.bytecode.opencsv.CSVWriter.*;


/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 22/06/2012
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class PsimiTabWriter {

    /**
     * Sets up a logger for that class.
     */
    Log log = LogFactory.getLog(PsimiTabWriter.class);

    private boolean headerAlreadyWritten = false;
    private boolean headerEnabled = true;
    private int version = PsimiTab.VERSION_2_5;

    ///////////////////////////////
    // Constructors

    public PsimiTabWriter(int version) {
        this(version, true);
    }

    public PsimiTabWriter(int version, boolean headerEnabled) {
        this.version = version;
        this.headerEnabled = headerEnabled;
    }

    public PsimiTabWriter() {
        this(PsimiTab.VERSION_2_5,true);
        log.warn("MITAB version was not provided. The default version MITAB 2.5 has been assigned by default.");
    }

    public PsimiTabWriter(boolean headerEnabled) {
        this.headerEnabled = headerEnabled;
        this.version = PsimiTab.VERSION_2_5;
        log.warn("MITAB version was not provided. The default version MITAB 2.5 has been assigned by default.");
    }

    @Deprecated
    /**
     * @deprecated Replaced by {@link #PsimiTabWriter(boolean headerEnabled)}
     */
    public PsimiTabWriter(DocumentDefinition documentDefinition, boolean headerEnabled) {
        //This constructor does not use documentDefinition
        this.headerEnabled = headerEnabled;
    }

    ///////////////////////////////
    // Handling of the filter

    public boolean hasHeaderEnabled() {
        return headerEnabled;
    }

    public void setHeaderEnabled(boolean headerEnabled) {
        this.headerEnabled = headerEnabled;
    }

    /**
     * Write a interacton in a MITAB file format.
     *
     * @param binaryInteraction BinaryInteraction to write. This interaction must be non null.
     * @param file              a File object to write to.
     * @param createFile        When this parameter is true, if the file exists will be
     *                          overwritten. When it is false, if the file exists,
     *                          the interactions will be append to the end of the file.
     *                          In both cases, if the file doesn't exist will be created.
     * @throws IOException
     */
    public void writeOrAppend(BinaryInteraction binaryInteraction, File file, boolean createFile) throws IOException {
        CSVWriter csvWriter =
                new CSVWriter(new FileWriter(file, !createFile), '\t', NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER, DEFAULT_LINE_END);

        if (createFile && headerEnabled) {
            csvWriter.writeNext(MitabWriterUtils.buildHeader(version));
        }

        String[] line = MitabWriterUtils.buildLine(binaryInteraction, version);
        csvWriter.writeNext(line);


        csvWriter.close();
    }

    /**
     * Write a collection of interactions in a MITAB file format.
     *
     * @param interactions BinaryInteractions to write. These interactions must be non null.
     * @param file         a File object to write to.
     * @param createFile   When this parameter is true, if the file exists will be
     *                     overwritten. When it is false, if the file exists,
     *                     the interactions will be append to the end of the file.
     *                     In both cases, if the file doesn't exist will be created.
     * @throws IOException
     */
    public void writeOrAppend(Collection<BinaryInteraction> interactions, File file, boolean createFile) throws IOException {
        CSVWriter csvWriter =
                new CSVWriter(new FileWriter(file, !createFile), '\t', NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER, DEFAULT_LINE_END);

        if (createFile && headerEnabled) {
            csvWriter.writeNext(MitabWriterUtils.buildHeader(version));
        }

        for (BinaryInteraction interaction : interactions) {
            String[] line = MitabWriterUtils.buildLine(interaction, version);
            csvWriter.writeNext(line);
        }

        csvWriter.close();
    }

    public void write(Collection<BinaryInteraction> interactions, Writer writer) throws IOException, ConverterException {
        CSVWriter csvWriter =
                new CSVWriter(writer, '\t', NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER, DEFAULT_LINE_END);

        if (headerEnabled && !headerAlreadyWritten) {
            csvWriter.writeNext(MitabWriterUtils.buildHeader(version));
            headerAlreadyWritten = true;
        }

        for (BinaryInteraction interaction : interactions) {
            String[] line = MitabWriterUtils.buildLine(interaction, version);
            csvWriter.writeNext(line);
        }

        csvWriter.close();
    }

    public void write(Collection<BinaryInteraction> interactions, File file) throws IOException, ConverterException {
        write(interactions, new FileWriter(file));

    }

    public void write(Collection<BinaryInteraction> interactions, OutputStream os) throws IOException, ConverterException {
        write(interactions, new OutputStreamWriter(os));
    }


    public void write(Collection<BinaryInteraction> interactions, PrintStream ps) throws IOException, ConverterException {
        write(interactions, new OutputStreamWriter(ps));
    }

    public void write(BinaryInteraction binaryInteraction, Writer writer) throws IOException {
        CSVWriter csvWriter =
                new CSVWriter(writer, '\t', NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER, DEFAULT_LINE_END);

        if (headerEnabled && !headerAlreadyWritten) {
            csvWriter.writeNext(MitabWriterUtils.buildHeader(version));
            headerAlreadyWritten = true;
        }

        String[] line = MitabWriterUtils.buildLine(binaryInteraction, version);
        csvWriter.writeNext(line);

    }

    public void write(BinaryInteraction binaryInteraction, OutputStream os) throws IOException {
        write(binaryInteraction, new OutputStreamWriter(os));

    }


}
