package psidev.psi.mi.tab.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabWriterUtils;
import psidev.psi.mi.tab.model.builder.PsimiTab;

import java.io.*;
import java.util.Collection;


/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 22/06/2012
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class PsimiTabWriter implements psidev.psi.mi.tab.PsimiTabWriter {

    /**
     * Sets up a logger for that class.
     */
    Log log = LogFactory.getLog(PsimiTabWriter.class);

    private int version = PsimiTab.VERSION_2_5;

    ///////////////////////////////
    // Constructors
    public PsimiTabWriter(int version) {
        this.version = version;
    }

    public PsimiTabWriter() {
        this(PsimiTab.VERSION_2_5);
        log.warn("MITAB version was not provided. The default version MITAB 2.5 has been assigned by default.");
    }

    public void write(Collection<BinaryInteraction> interactions, Writer writer) throws IOException {
        for (BinaryInteraction interaction : interactions) {
            write(interaction, writer);
        }
    }

    public void write(Collection<BinaryInteraction> interactions, OutputStream os) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(os);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        write(interactions, bufferedWriter);
        bufferedWriter.close();
        writer.close();
    }

    public void write(Collection<BinaryInteraction> interactions, PrintStream ps) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(ps);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        write(interactions, bufferedWriter);
        bufferedWriter.close();
        writer.close();
    }

    public void write(Collection<BinaryInteraction> interactions, File file) throws IOException {
        final FileWriter writer = new FileWriter(file, true);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        write(interactions, bufferedWriter);
        bufferedWriter.close();
        writer.close();
    }


    public void write(BinaryInteraction binaryInteraction, Writer writer) throws IOException {
        String line = MitabWriterUtils.buildLine(binaryInteraction, version);
        writer.write(line);
        writer.flush();
    }

    public void write(BinaryInteraction interaction, OutputStream os) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(os);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        write(interaction, bufferedWriter);
        bufferedWriter.close();
        writer.close();
    }

    public void write(BinaryInteraction interaction, PrintStream ps) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(ps);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        write(interaction, bufferedWriter);
        bufferedWriter.close();
        writer.close();
    }

    public void write(BinaryInteraction interaction, File file) throws IOException {
        final FileWriter writer = new FileWriter(file, true);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        write(interaction, bufferedWriter);
        bufferedWriter.close();
        writer.close();
    }

    public void writeMitabHeader(Writer writer) throws IOException {
        String line = MitabWriterUtils.buildHeader(version);
        writer.write(line);
        writer.flush();
    }

    public void writeMitabHeader(OutputStream os) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(os);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        writeMitabHeader(bufferedWriter);
        bufferedWriter.close();
        writer.close();
    }

    public void writeMitabHeader(PrintStream ps) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(ps);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        writeMitabHeader(bufferedWriter);
        bufferedWriter.close();
        writer.close();
    }

    public void writeMitabHeader(File file) throws IOException {
        final FileWriter writer = new FileWriter(file, true);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);
        writeMitabHeader(bufferedWriter);
        bufferedWriter.close();
        writer.close();
    }

    public void handleError() {
        //TODO
    }

    //TODO Checking format and handle exceptions
}
