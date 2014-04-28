package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.NamedParticipant;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for a named participant XML 2.5 writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml25NamedParticipantWriter<P extends Participant, F extends Feature> extends AbstractXml25ParticipantWriter<P,F> {
    public AbstractXml25NamedParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeNames(P object) throws XMLStreamException {
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
