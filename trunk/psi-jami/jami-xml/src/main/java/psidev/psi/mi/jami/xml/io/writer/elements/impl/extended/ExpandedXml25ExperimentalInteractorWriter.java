package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25InteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ExperimentalInteractorWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer of experimental interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class ExpandedXml25ExperimentalInteractorWriter extends AbstractXml25ExperimentalInteractorWriter implements ExpandedPsiXml25ElementWriter<ExperimentalInteractor> {
    private PsiXml25ElementWriter<Interactor> interactorWriter;

    public ExpandedXml25ExperimentalInteractorWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
        this.interactorWriter = new Xml25InteractorWriter(writer, objectIndex);
    }

    public ExpandedXml25ExperimentalInteractorWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                     PsiXml25ElementWriter<Interactor> interactorWriter) {
        super(writer, objectIndex);
        this.interactorWriter = interactorWriter != null ? interactorWriter : new Xml25InteractorWriter(writer, objectIndex);
    }

    @Override
    protected void writeInteractor(Interactor interactor) throws XMLStreamException {
        if (interactor != null){
            this.interactorWriter.write(interactor);
        }
    }
}