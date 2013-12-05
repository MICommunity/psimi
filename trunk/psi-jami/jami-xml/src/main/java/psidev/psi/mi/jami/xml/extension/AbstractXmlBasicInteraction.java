package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Xml implementation of interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlBasicInteraction extends AbstractXmlInteraction<Participant>{

    public AbstractXmlBasicInteraction() {
        super();
    }

    public AbstractXmlBasicInteraction(String shortName) {
        super(shortName);
    }

    public AbstractXmlBasicInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public void setJAXBIntraMolecular(Boolean intra) {
        super.setIntraMolecular(intra);
    }

    public void setJAXBId(int value) {
        super.setId(value);
    }

    public void setJAXBParticipantWrapper(JAXBParticipantWrapper jaxbParticipantWrapper) {
        super.setParticipantWrapper(jaxbParticipantWrapper);
    }

    @Override
    protected void initialiseParticipantWrapper() {
        super.setParticipantWrapper(new JAXBParticipantWrapper());
    }

    ////////////////////////////////////////////////////// classes
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="basicParticipantWrapper")
    public static class JAXBParticipantWrapper extends AbstractXmlInteraction.JAXBParticipantWrapper<Participant> {

        public JAXBParticipantWrapper(){
            super();
        }

        @XmlElement(type=XmlParticipant.class, name="participant", required = true)
        public List<Participant> getJAXBParticipants() {
            return super.getJAXBParticipants();
        }
    }
}
