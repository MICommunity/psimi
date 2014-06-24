package psidev.psi.mi.jami.xml.model.extension.binary.xml30;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlCausalRelationship;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Xml implementation of BinaryInteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlBinaryInteractionEvidence extends psidev.psi.mi.jami.xml.model.extension.binary.xml25.XmlBinaryInteractionEvidence
        implements psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlInteractionEvidence, Serializable {

    private List<ExtendedPsiXmlCausalRelationship> causalRelationships;

    public XmlBinaryInteractionEvidence() {
    }

    public XmlBinaryInteractionEvidence(String shortName) {
        super(shortName);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public XmlBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(participantA, participantB);
    }

    public XmlBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, participantA, participantB);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB) {
        super(shortName, type, participantA, participantB);
    }

    public XmlBinaryInteractionEvidence(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public XmlBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public XmlBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }

    @Override
    public List<ExtendedPsiXmlCausalRelationship> getCausalRelationships() {
        if (this.causalRelationships == null){
            this.causalRelationships = new ArrayList<ExtendedPsiXmlCausalRelationship>();
        }
        return this.causalRelationships;
    }
}
