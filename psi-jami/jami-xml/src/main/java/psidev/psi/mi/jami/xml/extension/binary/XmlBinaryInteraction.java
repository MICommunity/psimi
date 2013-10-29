package psidev.psi.mi.jami.xml.extension.binary;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of binary interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlBinaryInteraction extends AbstractXmlBinaryInteraction<Participant>{

    public XmlBinaryInteraction() {
    }

    public XmlBinaryInteraction(String shortName) {
        super(shortName);
    }

    public XmlBinaryInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public XmlBinaryInteraction(Participant participantA, Participant participantB) {
        super(participantA, participantB);
    }

    public XmlBinaryInteraction(String shortName, Participant participantA, Participant participantB) {
        super(shortName, participantA, participantB);
    }

    public XmlBinaryInteraction(String shortName, CvTerm type, Participant participantA, Participant participantB) {
        super(shortName, type, participantA, participantB);
    }

    public XmlBinaryInteraction(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public XmlBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public XmlBinaryInteraction(Participant participantA, Participant participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public XmlBinaryInteraction(String shortName, Participant participantA, Participant participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public XmlBinaryInteraction(String shortName, CvTerm type, Participant participantA, Participant participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }
}
