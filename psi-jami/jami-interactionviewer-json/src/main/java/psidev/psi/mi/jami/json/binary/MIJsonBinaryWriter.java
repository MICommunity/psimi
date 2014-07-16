package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

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

    @Override
    protected void writeFeatureProperties(Feature object) throws IOException {
        // nothing to do
    }

    @Override
    protected void writeParticipantProperties(Participant object) throws IOException {
        // nothing to do
    }

    @Override
    protected boolean writeInteractionProperties(BinaryInteraction interaction) throws IOException {
        return false;
    }


    @Override
    protected void writeParameters(BinaryInteraction binary) throws IOException {
        // nothing to do
    }

    @Override
    protected void writeConfidences(BinaryInteraction binary) throws IOException {
        // nothing to do
    }

    @Override
    protected String extractImexIdFrom(BinaryInteraction binary) {
        return null;
    }

    protected void initialiseSubWritersWith(Writer writer) {

        this.modelledBinaryWriter = new MIJsonModelledBinaryWriter(writer, getFetcher());
        this.binaryEvidenceWriter = new MIJsonBinaryEvidenceWriter(writer, getFetcher());
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

    @Override
    public void initialiseContext(Map<String, Object> options) {
        super.initialiseContext(options);
        initialiseSubWritersWith(getWriter());
    }
}
