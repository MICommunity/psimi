package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
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

    private AbstractMitab25BinaryWriter<ModelledBinaryInteraction, ModelledParticipant> modelledBinaryWriter;
    private AbstractMitab25BinaryWriter<BinaryInteractionEvidence, ParticipantEvidence> binaryEvidenceWriter;

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
    public void initialiseContext(Map<String, Object> options) {
        super.initialiseContext(options);
        initialiseSubWritersWith(getWriter());
    }

    @Override
    public void start() throws MIIOException {
        super.start();
    }

    @Override
    public void write(BinaryInteraction interaction) throws MIIOException {
        if (this.binaryEvidenceWriter == null || this.modelledBinaryWriter == null){
            throw new IllegalStateException("The Mitab writer has not been initialised. The options for the Mitab writer should contain at least "+ InteractionWriterFactory.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        boolean hasJustStarted = !hasStarted();
        if (!hasStarted()){
            start();
        }

        if (interaction instanceof BinaryInteractionEvidence){
            this.binaryEvidenceWriter.write((BinaryInteractionEvidence) interaction);
            if (hasJustStarted){
                this.modelledBinaryWriter.start();
            }
        }
        else if (interaction instanceof ModelledBinaryInteraction){
            this.modelledBinaryWriter.write((ModelledBinaryInteraction) interaction);
            if (hasJustStarted){
                this.binaryEvidenceWriter.start();
            }
        }
        else {
            super.write(interaction);
        }
    }

    @Override
    public void close() throws MIIOException {
        try{
            super.close();
        }
        finally {
            this.modelledBinaryWriter = null;
            this.binaryEvidenceWriter = null;
        }
    }

    @Override
    public void reset() throws MIIOException {
        try{
            super.reset();
        }
        finally {
            this.modelledBinaryWriter = null;
            this.binaryEvidenceWriter = null;
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

    protected void setModelledBinaryWriter(AbstractMitab25BinaryWriter<ModelledBinaryInteraction, ModelledParticipant> modelledBinaryWriter) {
        this.modelledBinaryWriter = modelledBinaryWriter;
        this.modelledBinaryWriter.setWriteHeader(false);
    }

    protected void setBinaryEvidenceWriter(AbstractMitab25BinaryWriter<BinaryInteractionEvidence, ParticipantEvidence> binaryEvidenceWriter) {
        this.binaryEvidenceWriter = binaryEvidenceWriter;
        this.binaryEvidenceWriter.setWriteHeader(false);
    }
}
