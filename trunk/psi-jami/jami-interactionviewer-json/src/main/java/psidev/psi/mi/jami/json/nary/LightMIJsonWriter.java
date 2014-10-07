package psidev.psi.mi.jami.json.nary;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.nary.elements.SimpleJsonInteractionWriter;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;

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

public class LightMIJsonWriter extends AbstractMIJsonWriter<Interaction> {

    public LightMIJsonWriter(){
        super();
    }

    public LightMIJsonWriter(File file, OntologyTermFetcher fetcher) throws IOException {

        super(file, fetcher);
    }

    public LightMIJsonWriter(OutputStream output, OntologyTermFetcher fetcher) {

        super(output, fetcher);
    }

    public LightMIJsonWriter(Writer writer, OntologyTermFetcher fetcher) {

        super(writer, fetcher);
    }

    public LightMIJsonWriter(Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures, IncrementalIdGenerator idGenerator) {
        super(processedInteractors, processedFeatures, idGenerator);
    }

    public LightMIJsonWriter(Writer writer, OntologyTermFetcher fetcher, Map<String, String> processedInteractors, Map<Feature, Integer> processedFeatures, IncrementalIdGenerator idGenerator) {
        super(writer, fetcher, processedInteractors, processedFeatures, idGenerator);
    }

    @Override
    protected void writeComplex(Complex complex) {
        write(complex);
    }

    @Override
    protected void initialiseInteractionWriter() {
        super.setInteractionWriter(new SimpleJsonInteractionWriter<Interaction>(getWriter(), getProcessedFeatures(), getProcessedInteractors(), getIdGenerator()));
        ((SimpleJsonInteractionWriter<Interaction>)getInteractionWriter()).setFetcher(getFetcher());
    }

}
