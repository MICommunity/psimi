package psidev.psi.mi.jami.json.nary;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

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
        this.complexWriter = new MIJsonModelledWriter();
    }

    public MIJsonEvidenceWriter(File file, OntologyTermFetcher fetcher) throws IOException {

        super(file, fetcher);
        this.complexWriter = new MIJsonModelledWriter(getWriter(), fetcher);
    }

    public MIJsonEvidenceWriter(OutputStream output, OntologyTermFetcher fetcher) {

        super(output, fetcher);
        this.complexWriter = new MIJsonModelledWriter(output, fetcher);
    }

    public MIJsonEvidenceWriter(Writer writer, OntologyTermFetcher fetcher) {

        super(writer, fetcher);
        this.complexWriter = new MIJsonModelledWriter(writer, fetcher);
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

    protected boolean writeExperiment(InteractionEvidence interaction) throws IOException {
        Experiment experiment = interaction.getExperiment();
        Collection<Annotation> figures = AnnotationUtils.collectAllAnnotationsHavingTopic(interaction.getAnnotations(), Annotation.FIGURE_LEGEND_MI, Annotation.FIGURE_LEGEND);

        if (experiment != null){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writeStartObject("experiment");
            getWriter().write(MIJsonUtils.OPEN);
            writeNextPropertySeparatorAndIndent();
            getWriter().write(MIJsonUtils.INDENT);

            // write detection method
            writeStartObject("detmethod");
            writeCvTerm(experiment.getInteractionDetectionMethod());

            if (experiment.getHostOrganism() != null){
                getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                getWriter().write(MIJsonUtils.INDENT);
                writeStartObject("host");
                writeOrganism(experiment.getHostOrganism());
            }

            // write publication
            if (experiment.getPublication() != null){
                getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                getWriter().write(MIJsonUtils.INDENT);
                writePublication(experiment.getPublication());
            }

            Collection<Annotation> expModifications = AnnotationUtils.collectAllAnnotationsHavingTopic(experiment.getAnnotations(), Annotation.EXP_MODIFICATION_MI, Annotation.EXP_MODIFICATION);
            if (!expModifications.isEmpty()){
                getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                getWriter().write(MIJsonUtils.INDENT);
                writeStartObject("experimentModifications");
                writeAllAnnotations(expModifications);
            }

            if (!figures.isEmpty()){
                getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                writeNextPropertySeparatorAndIndent();
                getWriter().write(MIJsonUtils.INDENT);
                writeStartObject("figures");
                writeAllAnnotations(figures);
            }

            writeNextPropertySeparatorAndIndent();
            getWriter().write(MIJsonUtils.CLOSE);
            return true;
        }
        else if (!figures.isEmpty()){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writeStartObject("experiment");
            getWriter().write(MIJsonUtils.OPEN);
            writeNextPropertySeparatorAndIndent();
            getWriter().write(MIJsonUtils.INDENT);

            // write figures
            writeStartObject("figures");
            writeAllAnnotations(figures);

            writeNextPropertySeparatorAndIndent();
            getWriter().write(MIJsonUtils.CLOSE);
            return true;
        }
        return false;
    }

    @Override
    protected void writeFeatureProperties(Feature object) throws IOException {
        FeatureEvidence feature = (FeatureEvidence)object;
        // detection methods
        if (!feature.getDetectionMethods().isEmpty()){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            getWriter().write(MIJsonUtils.INDENT);
            getWriter().write(MIJsonUtils.INDENT);
            writeStartObject("detmethods");
            getWriter().write(MIJsonUtils.OPEN_ARRAY);

            Iterator<CvTerm> methodIterator = feature.getDetectionMethods().iterator();
            while (methodIterator.hasNext()){
                CvTerm method = methodIterator.next();
                writeCvTerm(method);
                if (methodIterator.hasNext()){
                    getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                }
            }

            getWriter().write(MIJsonUtils.CLOSE_ARRAY);
        }
    }

    @Override
    protected void writeParticipantProperties(Participant object) throws IOException {
        ParticipantEvidence participant = (ParticipantEvidence)object;

        // write expRole
        getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
        writeNextPropertySeparatorAndIndent();
        getWriter().write(MIJsonUtils.INDENT);
        writeStartObject("expRole");
        writeCvTerm(participant.getExperimentalRole());

        // identification methods
        if (!participant.getIdentificationMethods().isEmpty()){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            getWriter().write(MIJsonUtils.INDENT);
            writeStartObject("identificationMethods");
            getWriter().write(MIJsonUtils.OPEN_ARRAY);

            Iterator<CvTerm> methodIterator = participant.getIdentificationMethods().iterator();
            while (methodIterator.hasNext()){
                CvTerm method = methodIterator.next();
                writeCvTerm(method);
                if (methodIterator.hasNext()){
                    getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
                }
            }

            getWriter().write(MIJsonUtils.CLOSE_ARRAY);
        }

        // expressed in
        if (participant.getExpressedInOrganism() != null){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            getWriter().write(MIJsonUtils.INDENT);
            writeStartObject("expressedIn");
            writeOrganism(participant.getExpressedInOrganism());
        }
    }

    @Override
    protected boolean writeInteractionProperties(InteractionEvidence interaction) throws IOException {
        return writeExperiment(interaction);
    }

    @Override
    protected void writeComplex(Complex complex) {
        this.complexWriter.write(complex);
    }


    @Override
    protected void writeParameters(InteractionEvidence binary) throws IOException {
        boolean hasParameters = !binary.getParameters().isEmpty();
        if (hasParameters){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writeStartObject("parameters");
            writeAllParameters(binary.getParameters());
        }
    }

    @Override
    protected void writeConfidences(InteractionEvidence binary) throws IOException {
        boolean hasConfidences = !binary.getConfidences().isEmpty();
        if (hasConfidences){
            getWriter().write(MIJsonUtils.ELEMENT_SEPARATOR);
            writeNextPropertySeparatorAndIndent();
            writeStartObject("confidences");
            writeAllConfidences(binary.getConfidences());
        }
    }

    @Override
    protected String extractImexIdFrom(InteractionEvidence binary) {
        return binary.getImexId();
    }
}
