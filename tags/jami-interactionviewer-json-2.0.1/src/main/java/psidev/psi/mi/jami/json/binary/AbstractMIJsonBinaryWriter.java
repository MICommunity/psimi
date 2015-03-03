package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.json.IncrementalIdGenerator;
import psidev.psi.mi.jami.json.nary.MIJsonModelledWriter;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * Abstract JSON writer for binary interactions (binary json format)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public abstract class AbstractMIJsonBinaryWriter<I extends BinaryInteraction> extends psidev.psi.mi.jami.json.nary.AbstractMIJsonWriter<I> {

    private Integer expansionId;
    private psidev.psi.mi.jami.json.nary.MIJsonModelledWriter complexWriter;

    public AbstractMIJsonBinaryWriter() {
    }

    public AbstractMIJsonBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {
        super(file, fetcher);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), fetcher, getProcessedInteractors(), getProcessedFeatures(), getProcessedParticipants(),
                getIdGenerator());
    }

    public AbstractMIJsonBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {
        super(output, fetcher);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), fetcher, getProcessedInteractors(), getProcessedFeatures(), getProcessedParticipants(),
                getIdGenerator());
    }

    public AbstractMIJsonBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {
        super(writer, fetcher);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), fetcher, getProcessedInteractors(), getProcessedFeatures(), getProcessedParticipants(),
                getIdGenerator());
    }

    protected AbstractMIJsonBinaryWriter(Writer writer, OntologyTermFetcher fetcher, Map<String, String> processedInteractors,
                                         Map<Feature, Integer> processedFeatures, Map<Entity, Integer> processedParticipants,
                                         IncrementalIdGenerator idGenerator) {
        super(writer, fetcher, processedInteractors, processedFeatures, processedParticipants, idGenerator);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), fetcher, getProcessedInteractors(), getProcessedFeatures(), getProcessedParticipants(),
                getIdGenerator());
    }

    protected AbstractMIJsonBinaryWriter(Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures,
                                         Map<Entity, Integer> processedParticipants, IncrementalIdGenerator idGenerator) {
        super(processedInteractors, processedFeatures, processedParticipants, idGenerator);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), getFetcher(), getProcessedInteractors(), getProcessedFeatures(),
                getProcessedParticipants(),
                getIdGenerator());
    }

    public void close() throws MIIOException{
        expansionId = null;
        if(this.complexWriter != null){
           this.complexWriter.clear();
        }
        super.close();
    }

    public void reset() throws MIIOException {
        expansionId = null;
        if(this.complexWriter != null){
            this.complexWriter.clear();
        }
        super.reset();
    }

    @Override
    public void initialiseContext(Map<String, Object> options) {
        super.initialiseContext(options);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), getFetcher(), getProcessedInteractors(), getProcessedFeatures(),
                getProcessedParticipants(),
                getIdGenerator());
    }

    @Override
    public void flush() throws MIIOException {
        super.flush();
    }

    public void setExpansionId(Integer expansionId) {
        this.expansionId = expansionId;
        initExpansionMethodInteractionWriter(expansionId);
    }

    protected abstract void initExpansionMethodInteractionWriter(Integer expansionId);

    protected Integer getExpansionId() {
        return expansionId;
    }

    @Override
    protected void writeComplex(Complex complex) {
        this.complexWriter.write(complex);
    }
}
