package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.HostOrganism;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for an extended host organism having experiment references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlHostOrganismWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlHostOrganismWriter {
    private PsiXmlObjectCache objectIndex;

    public XmlHostOrganismWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer);
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlHostOrganismWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    public XmlHostOrganismWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                 PsiXmlElementWriter<Alias> aliasWriter, PsiXmlElementWriter<CvTerm> cellTypeWriter,
                                 PsiXmlElementWriter<CvTerm> compartmentWriter, PsiXmlElementWriter<CvTerm> tissueWriter) {
        super(writer, aliasWriter, cellTypeWriter, compartmentWriter, tissueWriter);
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlHostOrganismWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    @Override
    protected void writeOtherProperties(Organism object) throws XMLStreamException {
        HostOrganism extendedOrganism = (HostOrganism)object;
        // write experiment refs
        if (!extendedOrganism.getExperiments().isEmpty()){
            getStreamWriter().writeStartElement("experimentRefList");
            for (Experiment exp : extendedOrganism.getExperiments()){
                getStreamWriter().writeStartElement("experimentRef");
                getStreamWriter().writeCharacters(Integer.toString(this.objectIndex.extractIdForExperiment(exp)));
                getStreamWriter().writeEndElement();
            }
            getStreamWriter().writeEndElement();
        }
    }
}
