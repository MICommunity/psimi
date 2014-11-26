package psidev.psi.mi.jami.json.nary;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.nary.elements.SimpleJsonModelledInteractionWriter;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ModelledInteraction;

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

public class MIJsonModelledWriter extends AbstractMIJsonWriter<ModelledInteraction> {

    public MIJsonModelledWriter(){
        super();
    }

    public MIJsonModelledWriter(File file, OntologyTermFetcher fetcher) throws IOException {

        super(file, fetcher);
    }

    public MIJsonModelledWriter(OutputStream output, OntologyTermFetcher fetcher) {

        super(output, fetcher);
    }

    public MIJsonModelledWriter(Writer writer, OntologyTermFetcher fetcher) {

        super(writer, fetcher);
    }

    public MIJsonModelledWriter(Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures, IncrementalIdGenerator idGenerator) {
        super(processedInteractors, processedFeatures, idGenerator);
    }

    public MIJsonModelledWriter(Writer writer, OntologyTermFetcher fetcher, Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures, IncrementalIdGenerator idGenerator) {
        super(writer, fetcher, processedInteractors, processedFeatures, idGenerator);
    }

    @Override
    protected void writeComplex(Complex complex) {
        write(complex);
    }

    @Override
    protected void initialiseInteractionWriter() {
        super.setInteractionWriter(new SimpleJsonModelledInteractionWriter(getWriter(), getProcessedFeatures(), getProcessedInteractors(),
                getProcessedParticipants(), getIdGenerator()));
        ((SimpleJsonModelledInteractionWriter)getInteractionWriter()).setFetcher(getFetcher());
    }

}
