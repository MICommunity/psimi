package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.model.CooperativeEffect;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract writer for Xml25Feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public abstract class AbstractXml25FeatureWriter<F extends Feature> extends AbstractXmlFeatureWriter<F> {

    public AbstractXml25FeatureWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);

    }

    protected void writeFeatureRole(F object) throws XMLStreamException{
        // nothing to write
    }

    protected void writeOtherAttributes(F object, boolean writeAttributeList) throws XMLStreamException{
        // write interaction dependency
        if (object.getRole() != null){
            // write start attribute list
            if (writeAttributeList){
                getStreamWriter().writeStartElement("attributeList");
            }
            writeAttribute(object.getRole().getShortName(), object.getRole().getMIIdentifier(), null);
            // write participant ref
            if (!object.getRanges().isEmpty()){
                Set<Integer> participantSet = new HashSet<Integer>();
                for (Object obj : object.getRanges()){
                    Range range = (Range)obj;
                    if (range.getParticipant() != null){
                        Integer id = this.getObjectIndex().extractIdForParticipant(range.getParticipant());
                        if (!participantSet.contains(id)){
                            participantSet.add(id);
                            writeAttribute(CooperativeEffect.PARTICIPANT_REF, CooperativeEffect.PARTICIPANT_REF_ID, Integer.toString(id));
                        }
                    }
                }
            }
            // write end attributeList
            if (writeAttributeList){
                getStreamWriter().writeEndElement();
            }
        }
        // write participant ref
        else if (!object.getRanges().isEmpty()){

            Set<Integer> participantSet = new HashSet<Integer>();
            for (Object obj : object.getRanges()){
                Range range = (Range)obj;
                if (range.getParticipant() != null){
                    Integer id = getObjectIndex().extractIdForParticipant(range.getParticipant());
                    if (!participantSet.contains(id)){
                        participantSet.add(id);
                    }
                }
            }
            if (!participantSet.isEmpty()){
                // write start attribute list
                if (writeAttributeList){
                    getStreamWriter().writeStartElement("attributeList");
                }
                for (Integer id : participantSet){
                    writeAttribute(CooperativeEffect.PARTICIPANT_REF, CooperativeEffect.PARTICIPANT_REF_ID, Integer.toString(id));
                }
                // write end attributeList
                if (writeAttributeList){
                    getStreamWriter().writeEndElement();
                }
            }
        }
    }
}
