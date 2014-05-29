package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlExperimentalInteractorWriter;
import psidev.psi.mi.jami.xml.model.extension.ExperimentalInteractor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer of experimental interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlExperimentalInteractorWriter extends AbstractXmlExperimentalInteractorWriter implements ExpandedPsiXmlElementWriter<ExperimentalInteractor> {
    private PsiXmlElementWriter<Interactor> interactorWriter;

    public XmlExperimentalInteractorWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlElementWriter<Interactor> getInteractorWriter() {
        if (this.interactorWriter == null){
            this.interactorWriter = new XmlInteractorWriter(getStreamWriter(), getObjectIndex());

        }
        return interactorWriter;
    }

    public void setInteractorWriter(PsiXmlElementWriter<Interactor> interactorWriter) {
        this.interactorWriter = interactorWriter;
    }

    @Override
    protected void writeInteractor(Interactor interactor) throws XMLStreamException {
        if (interactor != null){
            getInteractorWriter().write(interactor);
        }
    }
}