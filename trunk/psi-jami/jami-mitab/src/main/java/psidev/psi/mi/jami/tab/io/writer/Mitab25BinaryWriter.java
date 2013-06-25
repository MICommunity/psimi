package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.writer.feeder.DefaultMitabColumnFeeder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * The basic Mitab 2.5 writer for BinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class Mitab25BinaryWriter extends AbstractMitab25BinaryWriter<BinaryInteraction, Participant>{

    private Mitab25ModelledBinaryWriter modelledBinaryWriter;
    private Mitab25BinaryEvidenceWriter binaryEvidenceWriter;

    public Mitab25BinaryWriter() {
        super();
    }

    public Mitab25BinaryWriter(File file) throws IOException {
        super(file);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab25BinaryWriter(OutputStream output) {
        super(output);
        initialiseSubWritersWith(getWriter());
    }

    public Mitab25BinaryWriter(Writer writer) {
        super(writer);
        initialiseSubWritersWith(writer);
    }

    @Override
    public void initialiseContext(Map<String, Object> options) throws DataSourceWriterException {
        super.initialiseContext(options);
        initialiseSubWritersWith(getWriter());
    }

    @Override
    public void flush() throws DataSourceWriterException {
        super.flush();
        this.binaryEvidenceWriter.flush();
        this.modelledBinaryWriter.flush();
    }

    @Override
    public void write(BinaryInteraction interaction) throws DataSourceWriterException {
        if (this.binaryEvidenceWriter == null || this.modelledBinaryWriter == null){
            throw new IllegalStateException("The Mitab25Writer has not been initialised with a map of options." +
                    "The options for the Mitab25Writer should contain at least "+ InteractionWriterFactory.OUTPUT_FILE_OPTION_KEY
                    + " or " + InteractionWriterFactory.OUTPUT_STREAM_OPTION_KEY + " or " + InteractionWriterFactory.WRITER_OPTION_KEY + " to know where to write the interactions.");
        }
        // did not start yet so need to write the header if required
        else if (!hasWrittenHeader()){
            try{
                writeHeaderIfNotDone();
                this.binaryEvidenceWriter.setHasWrittenHeader(false);
                this.modelledBinaryWriter.setHasWrittenHeader(false);
            }
            catch (IOException e) {
                throw new DataSourceWriterException("Impossible to write MITAB header ", e);
            }
        }
        // we already started to write binary
        else{
            this.binaryEvidenceWriter.setHasWrittenHeader(true);
            this.modelledBinaryWriter.setHasWrittenHeader(true);
        }

        if (interaction instanceof BinaryInteractionEvidence){
            this.binaryEvidenceWriter.write((BinaryInteractionEvidence) interaction);
        }
        else if (interaction instanceof ModelledBinaryInteraction){
            this.modelledBinaryWriter.write((ModelledBinaryInteraction) interaction);
        }
        else {
            super.write(interaction);
        }
    }

    protected void initialiseSubWritersWith(Writer writer) {

        this.modelledBinaryWriter = new Mitab25ModelledBinaryWriter(writer);
        this.modelledBinaryWriter.setWriteHeader(false);
        this.binaryEvidenceWriter = new Mitab25BinaryEvidenceWriter(writer);
        this.binaryEvidenceWriter.setWriteHeader(false);
    }

    @Override
    protected void initialiseColumnFeeder() {
        setColumnFeeder(new DefaultMitabColumnFeeder(getWriter()));
    }

    @Override
    public void setWriteHeader(boolean writeHeader) {
        super.setWriteHeader(writeHeader);
        if (this.modelledBinaryWriter != null){
            this.modelledBinaryWriter.setWriteHeader(false);
        }
        if (this.binaryEvidenceWriter != null){
            this.binaryEvidenceWriter.setWriteHeader(false);
        }
    }
}
