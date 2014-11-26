package psidev.psi.mi.jami.json.nary;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.json.nary.elements.SimpleJsonInteractionWriter;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * The jsonWriter which writes the all interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/07/13</pre>
 */

public class MIJsonWriter extends AbstractMIJsonWriter<Interaction> {
    private MIJsonEvidenceWriter evidenceWriter;
    private MIJsonModelledWriter modelledWriter;

    public MIJsonWriter(){
        super();
    }

    public MIJsonWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super(file, fetcher);
        initialiseSubWritersWith(getWriter());
    }

    public MIJsonWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super(output, fetcher);
        initialiseSubWritersWith(getWriter());

    }

    public MIJsonWriter(Writer writer, OntologyTermFetcher fetcher) {
        super(writer, fetcher);
        initialiseSubWritersWith(getWriter());
    }

    protected void initialiseSubWritersWith(Writer writer) {

        this.modelledWriter = new MIJsonModelledWriter(writer, getFetcher());
        this.evidenceWriter = new MIJsonEvidenceWriter(writer, getFetcher());
    }

    @Override
    public void close() throws MIIOException {
        try{
            super.close();
        }
        finally {
            this.modelledWriter = null;
            this.evidenceWriter = null;
        }
    }

    @Override
    public void reset() throws MIIOException {
        try{
            super.reset();
        }
        finally {
            this.modelledWriter = null;
            this.evidenceWriter = null;
        }
    }

    @Override
    protected void writeComplex(Complex complex) {
        write(complex);
    }

    @Override
    protected void initialiseInteractionWriter() {
        super.setInteractionWriter(new SimpleJsonInteractionWriter<Interaction>(getWriter(), getProcessedFeatures(), getProcessedInteractors(),
                getProcessedParticipants(), getIdGenerator()));
        ((SimpleJsonInteractionWriter<Interaction>)getInteractionWriter()).setFetcher(getFetcher());
    }

    @Override
    public void write(Interaction interaction) throws MIIOException {
        if (this.evidenceWriter == null || this.modelledWriter == null){
            throw new IllegalStateException("The Json writer has not been initialised. The options for the Json writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        if (interaction instanceof InteractionEvidence){
            this.evidenceWriter.write((InteractionEvidence) interaction);
        }
        else if (interaction instanceof ModelledInteraction){
            this.modelledWriter.write((ModelledInteraction) interaction);
        }
        else {
            super.write(interaction);
        }
    }

    @Override
    public void initialiseContext(Map<String, Object> options) {
        super.initialiseContext(options);
        initialiseSubWritersWith(getWriter());
    }
}

