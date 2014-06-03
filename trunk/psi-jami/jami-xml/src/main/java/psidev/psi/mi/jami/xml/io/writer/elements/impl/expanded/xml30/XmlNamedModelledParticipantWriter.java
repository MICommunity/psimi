package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.NamedParticipant;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer for a named modelled participant having a fullname and a shortname.
 * It ignores experimental details.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlNamedModelledParticipantWriter extends XmlModelledParticipantWriter {
    public XmlNamedModelledParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeNames(ModelledParticipant object) throws XMLStreamException {
        if (object instanceof NamedParticipant){
            NamedParticipant xmlParticipant = (NamedParticipant) object;
            // write names
            PsiXmlUtils.writeCompleteNamesElement(xmlParticipant.getShortName(),
                    xmlParticipant.getFullName(), xmlParticipant.getAliases(), getStreamWriter(),
                    getAliasWriter());
        }
        else{
            super.writeNames(object);
        }
    }
}
