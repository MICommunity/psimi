package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInferredInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlParameterWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for interaction evidence writers in PSI-MI XML 3.0
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlInteractionEvidenceWriter<I extends InteractionEvidence>
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionEvidenceWriter<I> {

    public AbstractXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseXrefWriter(){
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseExperimentWriter(){
        super.setExperimentWriter(new XmlExperimentWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseConfidenceWriter(){
        super.setConfidenceWriter(new XmlConfidenceWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseParameterWriter(){
        super.setParameterWriter(new XmlParameterWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseInferredInteractionWriter() {
        super.setInferredInteractionWriter(new XmlInferredInteractionWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseInteractionTypeWriter() {
        super.setInteractionTypeWriter(new XmlCvTermWriter(getStreamWriter()));
    }

    @Override
    protected void writeOtherProperties(I object) {
        // nothing to do
    }
}
