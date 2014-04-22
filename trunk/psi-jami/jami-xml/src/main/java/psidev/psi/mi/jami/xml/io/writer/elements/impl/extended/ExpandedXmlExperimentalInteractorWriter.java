package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlExperimentalInteractorWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer of experimental interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class ExpandedXmlExperimentalInteractorWriter extends AbstractXmlExperimentalInteractorWriter implements ExpandedPsiXmlElementWriter<ExperimentalInteractor> {
    private PsiXmlElementWriter<Interactor> interactorWriter;

    public ExpandedXmlExperimentalInteractorWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
        this.interactorWriter = new XmlInteractorWriter(writer, objectIndex);
    }

    public ExpandedXmlExperimentalInteractorWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                   PsiXmlElementWriter<Interactor> interactorWriter) {
        super(writer, objectIndex);
        this.interactorWriter = interactorWriter != null ? interactorWriter : new XmlInteractorWriter(writer, objectIndex);
    }

    @Override
    protected void writeInteractor(Interactor interactor) throws XMLStreamException {
        if (interactor != null){
            this.interactorWriter.write(interactor);
        }
    }
}