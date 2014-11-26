package psidev.psi.mi.jami.json.nary;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.json.nary.elements.SimpleJsonInteractionEvidenceWriter;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.InteractionEvidence;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

/**
 * Abstract JSON writer for interactions (n-ary json format)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIJsonEvidenceWriter extends AbstractMIJsonWriter<InteractionEvidence> {

    private MIJsonModelledWriter complexWriter;

    public MIJsonEvidenceWriter(){
        super();
        this.complexWriter = new MIJsonModelledWriter(getProcessedInteractors(), getProcessedFeatures(), getIdGenerator());
    }

    public MIJsonEvidenceWriter(File file, OntologyTermFetcher fetcher) throws IOException {

        super(file, fetcher);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), fetcher,
                getProcessedInteractors(), getProcessedFeatures(), getIdGenerator());
    }

    public MIJsonEvidenceWriter(OutputStream output, OntologyTermFetcher fetcher) {

        super(output, fetcher);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), fetcher,
                getProcessedInteractors(), getProcessedFeatures(), getIdGenerator());
    }

    public MIJsonEvidenceWriter(Writer writer, OntologyTermFetcher fetcher) {

        super(writer, fetcher);
        this.complexWriter = new MIJsonModelledWriter(writer, fetcher,
                getProcessedInteractors(), getProcessedFeatures(), getIdGenerator());
    }

    public void flush() throws MIIOException {
        if (complexWriter != null){
            complexWriter.flush();
        }
    }

    public void close() throws MIIOException {
        try{
            if (complexWriter != null){
                complexWriter.close();
            }
        }
        finally {
            complexWriter = null;
        }
    }

    public void reset() throws MIIOException {
        try{
            if (complexWriter != null){
                complexWriter.reset();
            }
        }
        finally {
            complexWriter = null;
        }
    }

    @Override
    public void initialiseContext(Map<String, Object> options) {
        super.initialiseContext(options);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), getFetcher(), getProcessedInteractors(), getProcessedFeatures(), getIdGenerator());
    }

    @Override
    protected void writeComplex(Complex complex) {
        this.complexWriter.write(complex);
    }

    @Override
    protected void initialiseInteractionWriter() {
        super.setInteractionWriter(new SimpleJsonInteractionEvidenceWriter(getWriter(), getProcessedFeatures(),
                getProcessedInteractors(), getProcessedParticipants(), getIdGenerator()));
        ((SimpleJsonInteractionEvidenceWriter)getInteractionWriter()).setFetcher(getFetcher());
    }
}
