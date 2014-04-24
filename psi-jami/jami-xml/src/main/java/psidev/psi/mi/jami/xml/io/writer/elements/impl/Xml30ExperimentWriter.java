package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlExperimentWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * PSI-XML 3.0.0 experiment writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml30ExperimentWriter extends AbstractXmlExperimentWriter {

    public Xml30ExperimentWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
    }

    @Override
    protected void writeOtherAttributes(Experiment object, boolean needToWriteAttributeList) throws XMLStreamException {
        // does not write publication attributes as everything should be in bibref attribute
    }

    @Override
    protected void writeExperimentXrefs(Experiment object, String imexId) throws XMLStreamException {
        // write xrefs
        if (!object.getXrefs().isEmpty()){
            // write start xref
            getStreamWriter().writeStartElement("xref");
            if (!object.getXrefs().isEmpty()){
                writeXrefFromExperimentXrefs(object, imexId);
            }
            // does not write IMEx id as it should be in bibref

            // write end xref
            getStreamWriter().writeEndElement();
        }
    }

    protected void initialisePublicationWriter() {
        super.setPublicationWriter(new Xml30PublicationWriter(getStreamWriter()));
    }
}
