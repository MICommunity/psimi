package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlExperimentalInteractorWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML writer of experimental interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlExperimentalInteractorWriter extends AbstractXmlExperimentalInteractorWriter implements CompactPsiXmlElementWriter<ExperimentalInteractor> {
    public XmlExperimentalInteractorWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeInteractor(Interactor interactor) throws XMLStreamException {
        if (interactor != null){
            getStreamWriter().writeStartElement("interactorRef");
            getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForInteractor(interactor)));
            getStreamWriter().writeEndElement();
        }
    }
}
