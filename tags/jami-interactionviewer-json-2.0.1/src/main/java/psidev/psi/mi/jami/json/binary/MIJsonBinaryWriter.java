package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.json.binary.elements.SimpleJsonBinaryInteractionWriter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * JSON writer for mix of modelled and experimental interactions and
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIJsonBinaryWriter extends AbstractMIJsonBinaryWriter<BinaryInteraction> {

    private MIJsonBinaryEvidenceWriter binaryEvidenceWriter;
    private MIJsonModelledBinaryWriter modelledBinaryWriter;

    public MIJsonBinaryWriter(){
        super();
    }

    public MIJsonBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {

        super(file, fetcher);
        initialiseSubWritersWith(getWriter());
    }

    public MIJsonBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {

        super(output, fetcher);
        initialiseSubWritersWith(getWriter());
    }

    public MIJsonBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {

        super(writer, fetcher);
        initialiseSubWritersWith(getWriter());
    }

    @Override
    public void write(BinaryInteraction interaction) throws MIIOException {
        if (this.binaryEvidenceWriter == null || this.modelledBinaryWriter == null){
            throw new IllegalStateException("The Json writer has not been initialised. The options for the Json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
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

        this.modelledBinaryWriter = new MIJsonModelledBinaryWriter(writer, getFetcher(), getProcessedInteractors(), getProcessedFeatures(), getProcessedParticipants(),
                getIdGenerator());
        this.binaryEvidenceWriter = new MIJsonBinaryEvidenceWriter(writer, getFetcher(), getProcessedInteractors(), getProcessedFeatures(), getProcessedParticipants(),
                getIdGenerator());
    }

    @Override
    public void close() throws MIIOException {
        try{
            this.modelledBinaryWriter.clear();
            this.binaryEvidenceWriter.clear();
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
            this.modelledBinaryWriter.clear();
            this.binaryEvidenceWriter.clear();
            super.reset();
        }
        finally {
            this.modelledBinaryWriter = null;
            this.binaryEvidenceWriter = null;
        }
    }

    @Override
    protected void initialiseInteractionWriter() {
        super.setInteractionWriter(new SimpleJsonBinaryInteractionWriter<BinaryInteraction>(getWriter(), getProcessedFeatures(),
                getProcessedInteractors(), getProcessedParticipants(), getIdGenerator()));
        if (getExpansionId() != null){
            ((SimpleJsonBinaryInteractionWriter)getInteractionWriter()).setExpansionId(getExpansionId());
        }
    }

    @Override
    public void flush() throws MIIOException {
        super.flush();
    }

    @Override
    protected void initExpansionMethodInteractionWriter(Integer expansionId) {
        ((SimpleJsonBinaryInteractionWriter) getInteractionWriter()).setExpansionId(expansionId);
        this.modelledBinaryWriter.initExpansionMethodInteractionWriter(expansionId);
        this.binaryEvidenceWriter.initExpansionMethodInteractionWriter(expansionId);
    }

    @Override
    public void initialiseContext(Map<String, Object> options) {
        super.initialiseContext(options);
        initialiseSubWritersWith(getWriter());
    }
}
