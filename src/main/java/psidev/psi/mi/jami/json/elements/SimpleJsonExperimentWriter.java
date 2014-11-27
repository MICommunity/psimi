package psidev.psi.mi.jami.json.elements;

import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

/**
 * Json writer which will only write experimental details of an interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonExperimentWriter implements JsonElementWriter<InteractionEvidence>{

    private Writer writer;
    private JsonElementWriter<CvTerm> cvWriter;
    private JsonElementWriter<Publication> publicationWriter;
    private JsonElementWriter<Organism> hostOrganismWriter;
    private JsonElementWriter<Annotation> annotationWriter;

    public SimpleJsonExperimentWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The json experiment writer needs a non null Writer");
        }
        this.writer = writer;
    }

    public void write(InteractionEvidence object) throws IOException {
        Collection<Annotation> figures = AnnotationUtils.collectAllAnnotationsHavingTopic(object.getAnnotations(),
                Annotation.FIGURE_LEGEND_MI, Annotation.FIGURE_LEGEND);
        Experiment experiment = object.getExperiment();
        if (experiment != null){
            MIJsonUtils.writeSeparator(this.writer);
            MIJsonUtils.writePropertyKey("experiment", this.writer);
            MIJsonUtils.writeStartObject(this.writer);

            // write detection method
            MIJsonUtils.writePropertyKey("detmethod", writer);
            getCvWriter().write(experiment.getInteractionDetectionMethod());

            if (experiment.getHostOrganism() != null){
                MIJsonUtils.writeSeparator(this.writer);
                MIJsonUtils.writePropertyKey("host", writer);
                getHostOrganismWriter().write(experiment.getHostOrganism());
            }

            // write publication
            if (experiment.getPublication() != null){
                MIJsonUtils.writeSeparator(this.writer);
                getPublicationWriter().write(experiment.getPublication());
            }

            Collection<Annotation> expModifications = AnnotationUtils.collectAllAnnotationsHavingTopic(experiment.getAnnotations(),
                    Annotation.EXP_MODIFICATION_MI, Annotation.EXP_MODIFICATION);
            if (!expModifications.isEmpty()){
                MIJsonUtils.writeSeparator(this.writer);
                MIJsonUtils.writePropertyKey("experimentModifications", writer);
                writeAnnotations(expModifications);
            }

            if (!figures.isEmpty()){
                MIJsonUtils.writeSeparator(this.writer);
                MIJsonUtils.writePropertyKey("figures", writer);
                writeAnnotations(figures);
            }

            MIJsonUtils.writeEndObject(this.writer);
        }
        else if (!figures.isEmpty()){
            MIJsonUtils.writeSeparator(this.writer);
            MIJsonUtils.writePropertyKey("experiment", this.writer);
            MIJsonUtils.writeStartObject(this.writer);

            // write figures
            MIJsonUtils.writePropertyKey("figures", writer);
            writeAnnotations(figures);

            MIJsonUtils.writeEndObject(this.writer);
        }
    }

    protected void writeAnnotations(Collection<Annotation> expModifications) throws IOException {
        MIJsonUtils.writeOpenArray(this.writer);

        Iterator<Annotation> expIterator = expModifications.iterator();
        while (expIterator.hasNext()){
            getAnnotationWriter().write(expIterator.next());
            if (expIterator.hasNext()){
                MIJsonUtils.writeSeparator(this.writer);
            }
        }
        MIJsonUtils.writeEndArray(this.writer);
    }

    public JsonElementWriter<CvTerm> getCvWriter() {
        if (this.cvWriter == null){
            this.cvWriter = new SimpleJsonCvTermWriter(writer);
        }
        return cvWriter;
    }

    public void setCvWriter(JsonElementWriter<CvTerm> cvWriter) {
        this.cvWriter = cvWriter;
    }

    public JsonElementWriter<Publication> getPublicationWriter() {
        if (this.publicationWriter == null){
            this.publicationWriter = new SimpleJsonPublicationWriter(this.writer);
            ((SimpleJsonPublicationWriter)this.publicationWriter).setCvWriter(getCvWriter());
        }
        return publicationWriter;
    }

    public void setPublicationWriter(JsonElementWriter<Publication> publicationWriter) {
        this.publicationWriter = publicationWriter;
    }

    public JsonElementWriter<Organism> getHostOrganismWriter() {
        if (this.hostOrganismWriter == null){
            this.hostOrganismWriter = new SimpleJsonHostOrganismWriter(this.writer);
            ((SimpleJsonHostOrganismWriter)this.hostOrganismWriter).setCvWriter(getCvWriter());
        }
        return hostOrganismWriter;
    }

    public void setHostOrganismWriter(JsonElementWriter<Organism> hostOrganismWriter) {
        this.hostOrganismWriter = hostOrganismWriter;
    }

    public JsonElementWriter<Annotation> getAnnotationWriter() {
        if (this.annotationWriter == null){
            this.annotationWriter = new SimpleJsonAnnotationWriter(this.writer);
        }
        return annotationWriter;
    }

    public void setAnnotationWriter(JsonElementWriter<Annotation> annotationWriter) {
        this.annotationWriter = annotationWriter;
    }
}
