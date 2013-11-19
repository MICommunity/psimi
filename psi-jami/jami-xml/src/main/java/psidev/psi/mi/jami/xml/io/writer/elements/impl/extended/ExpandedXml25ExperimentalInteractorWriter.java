package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25InteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ExperimentalInteractorWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Expanded XML 2.5 writer of experimental interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class ExpandedXml25ExperimentalInteractorWriter extends AbstractXml25ExperimentalInteractorWriter implements ExpandedPsiXml25ElementWriter<ExperimentalInteractor> {
    private PsiXml25ElementWriter<Interactor> interactorWriter;

    public ExpandedXml25ExperimentalInteractorWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
        this.interactorWriter = new Xml25InteractorWriter(writer, objectIndex);
    }

    public ExpandedXml25ExperimentalInteractorWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex, PsiXml25ElementWriter<Interactor> interactorWriter) {
        super(writer, objectIndex);
        this.interactorWriter = interactorWriter != null ? interactorWriter : new Xml25InteractorWriter(writer, objectIndex);
    }

    @Override
    protected void writeInteractor(Interactor interactor) throws XMLStreamException {
        if (interactor != null){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.interactorWriter.write(interactor);
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }
}