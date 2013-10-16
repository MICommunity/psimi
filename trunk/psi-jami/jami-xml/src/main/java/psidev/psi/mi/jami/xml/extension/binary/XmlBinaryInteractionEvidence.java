package psidev.psi.mi.jami.xml.extension.binary;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.xml.extension.XmlInteractionEvidence;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

/**
 * Xml implementation of BinaryInteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlBinaryInteractionEvidence extends XmlInteractionEvidence implements BinaryInteractionEvidence{

    private BinaryInteractionEvidence binaryWrapper;

    public XmlBinaryInteractionEvidence(){
        super();
        this.binaryWrapper = new DefaultBinaryInteractionEvidence();
    }

    public XmlBinaryInteractionEvidence(String shortName){
        super(shortName);
        this.binaryWrapper = new DefaultBinaryInteractionEvidence();
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type){
        super(shortName, type);
        this.binaryWrapper = new DefaultBinaryInteractionEvidence();
    }

    public XmlBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB){
        super();
        this.binaryWrapper = new DefaultBinaryInteractionEvidence(participantA, participantB);

    }

    public XmlBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB){
        super(shortName);
        this.binaryWrapper = new DefaultBinaryInteractionEvidence(participantA, participantB);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB){
        super(shortName, type);
        this.binaryWrapper = new DefaultBinaryInteractionEvidence(participantA, participantB);
    }

    public XmlBinaryInteractionEvidence(CvTerm complexExpansion){
        super();
        this.binaryWrapper = new DefaultBinaryInteractionEvidence(complexExpansion);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type, CvTerm complexExpansion){
        super(shortName, type);
        this.binaryWrapper = new DefaultBinaryInteractionEvidence(complexExpansion);

    }

    public XmlBinaryInteractionEvidence(ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion){
        super();
        this.binaryWrapper = new DefaultBinaryInteractionEvidence(participantA, participantB, complexExpansion);

    }

    public XmlBinaryInteractionEvidence(String shortName, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion){
        super(shortName);
        this.binaryWrapper = new DefaultBinaryInteractionEvidence(participantA, participantB, complexExpansion);
    }

    public XmlBinaryInteractionEvidence(String shortName, CvTerm type, ParticipantEvidence participantA, ParticipantEvidence participantB, CvTerm complexExpansion){
        super(shortName, type);
        this.binaryWrapper = new DefaultBinaryInteractionEvidence(participantA, participantB, complexExpansion);
    }

    public ParticipantEvidence getParticipantA() {
        return this.binaryWrapper.getParticipantA();
    }

    public ParticipantEvidence getParticipantB() {
        return this.binaryWrapper.getParticipantB();
    }

    public void setParticipantA(ParticipantEvidence participantA) {
        this.binaryWrapper.setParticipantA(participantA);
    }

    public void setParticipantB(ParticipantEvidence participantB) {
        this.binaryWrapper.setParticipantB(participantB);
    }

    public CvTerm getComplexExpansion() {
        return this.binaryWrapper.getComplexExpansion();
    }

    public void setComplexExpansion(CvTerm expansion) {
        this.binaryWrapper.setComplexExpansion(expansion);
    }

    /**
     * The collection of participants for this binary interaction.
     * It cannot be changed.
     * @return
     */
    @Override
    public Collection<ParticipantEvidence> getParticipants() {
        return this.binaryWrapper.getParticipants();
    }

    /**
     * Adds a new Participant and set the Interaction of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param part
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants
     */
    @Override
    public boolean addParticipant(ParticipantEvidence part) {
        return this.binaryWrapper.addParticipant(part);
    }

    /**
     * Removes the Participant from this binary interaction
     * @param part
     * @return
     */
    @Override
    public boolean removeParticipant(ParticipantEvidence part) {
        return this.binaryWrapper.removeParticipant(part);
    }

    /**
     * Adds the participants and set the Interaction of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param participants
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants or the given participants contain more than two participants
     */
    @Override
    public boolean addAllParticipants(Collection<? extends ParticipantEvidence> participants) {
        return this.binaryWrapper.addAllParticipants(participants);
    }

    @Override
    public boolean removeAllParticipants(Collection<? extends ParticipantEvidence> participants) {
        return this.binaryWrapper.removeAllParticipants(participants);
    }

    @Override
    public void setJAXBId(int value) {
        setJAXBIdOnly(value);
    }
}
