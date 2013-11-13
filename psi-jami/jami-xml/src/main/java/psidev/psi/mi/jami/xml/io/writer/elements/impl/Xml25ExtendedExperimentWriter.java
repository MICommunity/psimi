package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Experiment;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25PublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.List;

/**
 * XML 2.5 writer for extended experiments having participant identification method,
 * feature detection method and a list of host organisms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25ExtendedExperimentWriter extends Xml25NamedExperimentWriter{
    private PsiXml25ElementWriter<CvTerm> participantIdentificationMethodWriter;
    private PsiXml25ElementWriter<CvTerm> featureDetectionMethodWriter;

    public Xml25ExtendedExperimentWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex) {
        super(writer, objectIndex);
        this.participantIdentificationMethodWriter = new Xml25ParticipantIdentificationMethodWriter(writer);
        this.featureDetectionMethodWriter = new Xml25FeatureDetectionMethodWriter(writer);
    }

    public Xml25ExtendedExperimentWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25PublicationWriter publicationWriter, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<Organism> hostOrganismWriter, PsiXml25ElementWriter<CvTerm> detectionMethodWriter, PsiXml25ElementWriter<CvTerm> participantIdentificationMethodWriter, PsiXml25ElementWriter<CvTerm> featureDetectionMethodWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Confidence> confidenceWriter) {
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
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write cv
            this.participantIdentificationMethodWriter.write(identificationMethod);
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        // write feature detection method
        CvTerm featureMethod = xmlExperiment.getFeatureDetectionMethod();
        if (featureMethod != null){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write cv
            this.featureDetectionMethodWriter.write(featureMethod);
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeHostOrganism(Experiment object) throws XMLStreamException {
        ExtendedPsi25Experiment xmlExperiment = (ExtendedPsi25Experiment)object;
        // write hostOrganismList
        List<Organism> hostList = xmlExperiment.getHostOrganisms();
        if (!hostList.isEmpty()){
            // write start host organism list
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("hostOrganismList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write host
            for (Organism host : hostList){
                if (host != null){
                    getHostOrganismWriter().write(host);
                    getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
                }
            }
            // write end host organism list
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }
}
