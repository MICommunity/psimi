package psidev.psi.mi.jami.json.nary;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Participant;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

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

    @Override
    protected void writeFeatureProperties(Feature feature) throws IOException {
        // nothing to write
    }

    @Override
    protected void writeParticipantProperties(Participant participant) throws IOException {
         // nothing to write
    }

    @Override
    protected boolean writeInteractionProperties(ModelledInteraction interaction) throws IOException {
        return false;
    }

    @Override
    protected void writeComplex(Complex complex) {
        write(complex);
    }

    @Override
    protected void writeParameters(ModelledInteraction binary) throws IOException {
        boolean hasParameters = !binary.getModelledParameters().isEmpty();
        if (hasParameters){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writeStartObject("parameters");
            writeAllParameters(binary.getModelledParameters());
        }
    }

    @Override
    protected void writeConfidences(ModelledInteraction binary) throws IOException {
        boolean hasConfidences = !binary.getModelledConfidences().isEmpty();
        if (hasConfidences){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writeStartObject("confidences");
            writeAllConfidences(binary.getModelledConfidences());
        }
    }

    @Override
    protected String extractImexIdFrom(ModelledInteraction binary) {
        return null;
    }


}
