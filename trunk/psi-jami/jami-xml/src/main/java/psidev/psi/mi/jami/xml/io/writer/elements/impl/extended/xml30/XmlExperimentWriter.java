package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml30NamedExperimentWriter;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlExperiment;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.List;

/**
 * XML 3.0 writer for extended experiments having participant identification method,
 * feature detection method and a list of host organisms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlExperimentWriter extends Xml30NamedExperimentWriter {

    public XmlExperimentWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeOtherProperties(Experiment object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlExperiment){
            ExtendedPsiXmlExperiment xmlExperiment = (ExtendedPsiXmlExperiment)object;
            // write participant identification method
            CvTerm identificationMethod = xmlExperiment.getParticipantIdentificationMethod();
            if (identificationMethod != null){
                // write cv
                getDetectionMethodWriter().write(identificationMethod,"participantIdentificationMethod");
            }
            // write feature detection method
            CvTerm featureMethod = xmlExperiment.getFeatureDetectionMethod();
            if (featureMethod != null){
                // write cv
                getDetectionMethodWriter().write(featureMethod,"featureDetectionMethod");
            }
        }
    }

    @Override
    protected void writeHostOrganism(Experiment object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlExperiment){
            ExtendedPsiXmlExperiment xmlExperiment = (ExtendedPsiXmlExperiment)object;
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
        else{
            super.writeHostOrganism(object);
        }
    }
}
