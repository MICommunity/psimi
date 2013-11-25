package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.HostOrganism;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for an extended host organism having experiment references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class Xml25HostOrganismWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25HostOrganismWriter {
    private PsiXml25ObjectCache objectIndex;

    public Xml25HostOrganismWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer);
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25HostOrganismWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    public Xml25HostOrganismWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                   PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25ElementWriter<CvTerm> cellTypeWriter,
                                   PsiXml25ElementWriter<CvTerm> compartmentWriter, PsiXml25ElementWriter<CvTerm> tissueWriter) {
        super(writer, aliasWriter, cellTypeWriter, compartmentWriter, tissueWriter);
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25HostOrganismWriter. It is necessary for generating an id to an experimentDescription");
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
