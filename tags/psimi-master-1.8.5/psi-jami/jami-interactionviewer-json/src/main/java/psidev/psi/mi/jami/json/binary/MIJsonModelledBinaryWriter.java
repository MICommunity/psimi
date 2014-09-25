package psidev.psi.mi.jami.json.binary;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * JSON writer for ModelledInteractions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/07/13</pre>
 */

public class MIJsonModelledBinaryWriter extends AbstractMIJsonBinaryWriter<ModelledBinaryInteraction> {

    public MIJsonModelledBinaryWriter(){
        super();
    }

    public MIJsonModelledBinaryWriter(File file, OntologyTermFetcher fetcher) throws IOException {

        super(file, fetcher);
    }

    public MIJsonModelledBinaryWriter(OutputStream output, OntologyTermFetcher fetcher) {

        super(output, fetcher);
    }

    public MIJsonModelledBinaryWriter(Writer writer, OntologyTermFetcher fetcher) {

        super(writer, fetcher);
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
    protected boolean writeInteractionProperties(ModelledBinaryInteraction interaction) throws IOException {
        if (interaction instanceof Complex){
            Complex complex = (Complex)interaction;
            // then interactor type
            boolean hasType = complex.getInteractorType() != null;
            if (hasType){
                getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writeStartObject("complexType");
                writeCvTerm(complex.getInteractorType());
            }
            // then evidence type
            hasType = complex.getEvidenceType() != null;
            if (hasType){
                getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writeStartObject("evidenceType");
                writeCvTerm(complex.getEvidenceType());
            }
            // then write organism
            hasType = complex.getOrganism() != null;
            if (hasType){
                getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                writeStartObject("organism");
                writeOrganism(complex.getOrganism());
            }
        }
        return false;
    }


    @Override
    protected void writeParameters(ModelledBinaryInteraction binary) throws IOException {
        boolean hasParameters = !binary.getModelledParameters().isEmpty();
        if (hasParameters){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writeStartObject("parameters");
            writeAllParameters(binary.getModelledParameters());
        }
    }

    @Override
    protected void writeConfidences(ModelledBinaryInteraction binary) throws IOException {
        boolean hasConfidences = !binary.getModelledConfidences().isEmpty();
        if (hasConfidences){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writeStartObject("confidences");
            writeAllConfidences(binary.getModelledConfidences());
        }
    }

    @Override
    protected String extractImexIdFrom(ModelledBinaryInteraction binary) {
        return null;
    }
}
