package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml25;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlModelledFeatureWriter;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for XML 2.5 writer of modelled participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractXmlModelledParticipantWriter
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlModelledParticipantWriter {

    public AbstractXmlModelledParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseFeatureWriter() {
        super.setFeatureWriter(new XmlModelledFeatureWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseBiologicalRoleWriter() {
        super.setBiologicalRoleWriter(new XmlCvTermWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseInteractorWriter() {
        super.setInteractorWriter(new XmlInteractorWriter(getStreamWriter(), getObjectIndex()));
    }

    protected void writeStoichiometry(ModelledParticipant object){
        // nothing to write
    }

    protected void writeOtherAttributes(ModelledParticipant object, boolean writeAttributeList) throws XMLStreamException {
        if (object.getStoichiometry() != null){
            if (writeAttributeList){
                // write start attribute list
                getStreamWriter().writeStartElement("attributeList");
            }
            writeStoichiometryAttribute(object.getStoichiometry());
            if (writeAttributeList){
                // write end attribute list
                getStreamWriter().writeEndElement();
            }
        }
    }

    protected void writeStoichiometryAttribute(Stoichiometry stc) throws XMLStreamException {
        // write stoichiometry

        // write start
        getStreamWriter().writeStartElement("attribute");
        // write topic
        getStreamWriter().writeAttribute("name", Annotation.COMMENT);
        getStreamWriter().writeAttribute("nameAc", Annotation.COMMENT_MI);
        // write description
        getStreamWriter().writeCharacters(PsiXmlUtils.STOICHIOMETRY_PREFIX);
        getStreamWriter().writeCharacters(Long.toString(stc.getMinValue()));
        // stoichiometry range
        if (stc.getMaxValue() != stc.getMinValue()){
            getStreamWriter().writeCharacters(" - ");
            getStreamWriter().writeCharacters(Long.toString(stc.getMaxValue()));
        }
        // write end attribute
        getStreamWriter().writeEndElement();
    }
}
