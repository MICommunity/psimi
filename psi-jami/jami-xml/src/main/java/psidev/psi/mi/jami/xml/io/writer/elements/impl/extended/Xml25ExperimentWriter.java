package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Experiment;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25PublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25FeatureDetectionMethodWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25NamedExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ParticipantIdentificationMethodWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.List;

/**
 * XML 2.5 writer for extended experiments having participant identification method,
 * feature detection method and a list of host organisms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25ExperimentWriter extends Xml25NamedExperimentWriter {
    private PsiXml25ElementWriter<CvTerm> participantIdentificationMethodWriter;
    private PsiXml25ElementWriter<CvTerm> featureDetectionMethodWriter;

    public Xml25ExperimentWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
        this.participantIdentificationMethodWriter = new Xml25ParticipantIdentificationMethodWriter(writer);
        this.featureDetectionMethodWriter = new Xml25FeatureDetectionMethodWriter(writer);
    }

    public Xml25ExperimentWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25PublicationWriter publicationWriter, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<Organism> hostOrganismWriter, PsiXml25ElementWriter<CvTerm> detectionMethodWriter, PsiXml25ElementWriter<CvTerm> participantIdentificationMethodWriter, PsiXml25ElementWriter<CvTerm> featureDetectionMethodWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Confidence> confidenceWriter) {
        super(writer, objectIndex, aliasWriter, publicationWriter, primaryRefWriter, secondaryRefWriter, hostOrganismWriter, detectionMethodWriter, attributeWriter, confidenceWriter);
        this.participantIdentificationMethodWriter = participantIdentificationMethodWriter != null ? participantIdentificationMethodWriter : new Xml25ParticipantIdentificationMethodWriter(writer);
        this.featureDetectionMethodWriter = featureDetectionMethodWriter != null ? featureDetectionMethodWriter : new Xml25FeatureDetectionMethodWriter(writer);
    }

    @Override
    protected void writeOtherProperties(Experiment object) throws XMLStreamException {
        ExtendedPsi25Experiment xmlExperiment = (ExtendedPsi25Experiment)object;
        // write participant identification method
        CvTerm identificationMethod = xmlExperiment.getParticipantIdentificationMethod();
        if (identificationMethod != null){
            // write cv
            this.participantIdentificationMethodWriter.write(identificationMethod);
        }
        // write feature detection method
        CvTerm featureMethod = xmlExperiment.getFeatureDetectionMethod();
        if (featureMethod != null){
            // write cv
            this.featureDetectionMethodWriter.write(featureMethod);
        }
    }

    @Override
    protected void writeHostOrganism(Experiment object) throws XMLStreamException {
        ExtendedPsi25Experiment xmlExperiment = (ExtendedPsi25Experiment)object;
        // write hostOrganismList
        List<Organism> hostList = xmlExperiment.getHostOrganisms();
        if (!hostList.isEmpty()){
            // write start host organism list
            getStreamWriter().writeStartElement("hostOrganismList");
            // write host
            for (Organism host : hostList){
                if (host != null){
                    getHostOrganismWriter().write(host);
                }
            }
            // write end host organism list
            getStreamWriter().writeEndElement();
        }
    }
}
