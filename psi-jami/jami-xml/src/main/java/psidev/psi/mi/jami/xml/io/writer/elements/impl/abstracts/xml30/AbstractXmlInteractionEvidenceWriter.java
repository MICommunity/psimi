package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.VariableParameterValueSet;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInferredInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlVariableParameterValueSetWriter;

import javax.xml.stream.XMLStreamException;
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

    private PsiXmlElementWriter<VariableParameterValueSet> variableParameterValueSetWriter;

    public AbstractXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public PsiXmlElementWriter<VariableParameterValueSet> getVariableParameterValueSetWriter() {
        if (this.variableParameterValueSetWriter == null){
            this.variableParameterValueSetWriter = new XmlVariableParameterValueSetWriter(getStreamWriter(), getObjectIndex());
        }
        return variableParameterValueSetWriter;
    }

    public void setVariableParameterValueSetWriter(PsiXmlElementWriter<VariableParameterValueSet> variableParameterValueSetWriter) {
        this.variableParameterValueSetWriter = variableParameterValueSetWriter;
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
    protected void writeOtherProperties(I object) throws XMLStreamException {
        // experimental variable values
        writeExperimentalVariableValues(object);
    }

    protected void writeExperimentalVariableValues(I object) throws XMLStreamException {
        if (!object.getVariableParameterValues().isEmpty()){
            getStreamWriter().writeStartElement("experimentalVariableValueList");

            for (VariableParameterValueSet set : object.getVariableParameterValues()){
                 getVariableParameterValueSetWriter().write(set);
            }

            // end list
            getStreamWriter().writeEndElement();
        }
    }
}
