package psidev.psi.mi.tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabWriterUtils;
import psidev.psi.mi.tab.model.builder.PsimiTabVersion;

import java.io.*;
import java.util.Collection;


/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 22/06/2012
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class PsimiTabWriter implements psidev.psi.mi.tab.io.PsimiTabWriter {

    /**
     * Sets up a logger for that class.
     */
    Log log = LogFactory.getLog(PsimiTabWriter.class);

    private PsimiTabVersion version = PsimiTabVersion.v2_5;

    ///////////////////////////////
    // Constructors
    public PsimiTabWriter(PsimiTabVersion version) {
        this.version = version;
    }

    public PsimiTabWriter() {
        this(PsimiTabVersion.v2_5);
        log.warn("MITAB version was not provided. The default version MITAB 2.5 has been assigned by default.");
    }

    public void write(Collection<BinaryInteraction> interactions, Writer writer) throws IOException {
        for (BinaryInteraction interaction : interactions) {
            write(interaction, writer);
        }
    }

    public void write(Collection<BinaryInteraction> interactions, OutputStream os) throws IOException {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
            write(interactions, bufferedWriter);
        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void write(Collection<BinaryInteraction> interactions, PrintStream ps) throws IOException {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(ps));
            write(interactions, bufferedWriter);
        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void write(Collection<BinaryInteraction> interactions, File file) throws IOException {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new BufferedWriter(new FileWriter(file, true)));
            write(interactions, bufferedWriter);
        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }


    public void write(BinaryInteraction binaryInteraction, Writer writer) throws IOException {
        String line = MitabWriterUtils.buildLine(binaryInteraction, version);
        writer.write(line);
        writer.flush();
    }

    public void write(BinaryInteraction interaction, OutputStream os) throws IOException {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
            write(interaction, bufferedWriter);
        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void write(BinaryInteraction interaction, PrintStream ps) throws IOException {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(ps));
            write(interaction, bufferedWriter);
        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void write(BinaryInteraction interaction, File file) throws IOException {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            write(interaction, bufferedWriter);
        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void writeMitabHeader(Writer writer) throws IOException {
        String line = MitabWriterUtils.buildHeader(version);
        writer.write(line);
        writer.flush();
    }

    public void writeMitabHeader(OutputStream os) throws IOException {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
            writeMitabHeader(bufferedWriter);
        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void writeMitabHeader(PrintStream ps) throws IOException {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(ps));
            writeMitabHeader(bufferedWriter);
        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void writeMitabHeader(File file) throws IOException {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            writeMitabHeader(bufferedWriter);
        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void handleError(String message, Throwable e) throws PsimiTabException {
        //TODO
    }


}
