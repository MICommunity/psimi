package psidev.psi.mi.jami.xml.extension.binary;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.extension.XmlBasicInteraction;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

/**
 * Xml implementation of binary interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlBinaryInteraction extends XmlBasicInteraction implements BinaryInteraction<Participant>{

    private BinaryInteraction<Participant> binaryWrapper;

    public XmlBinaryInteraction(){
        super();
        this.binaryWrapper = new DefaultBinaryInteraction();
    }

    public XmlBinaryInteraction(String shortName){
        super(shortName);
        this.binaryWrapper = new DefaultBinaryInteraction();
    }

    public XmlBinaryInteraction(String shortName, CvTerm type){
        super(shortName, type);
        this.binaryWrapper = new DefaultBinaryInteraction();
    }

    public XmlBinaryInteraction(Participant participantA, Participant participantB){
        super();
        this.binaryWrapper = new DefaultBinaryInteraction(participantA, participantB);

    }

    public XmlBinaryInteraction(String shortName, Participant participantA, Participant participantB){
        super(shortName);
        this.binaryWrapper = new DefaultBinaryInteraction(participantA, participantB);
    }

    public XmlBinaryInteraction(String shortName, CvTerm type, Participant participantA, Participant participantB){
        super(shortName, type);
        this.binaryWrapper = new DefaultBinaryInteraction(participantA, participantB);
    }

    public XmlBinaryInteraction(CvTerm complexExpansion){
        super();
        this.binaryWrapper = new DefaultBinaryInteraction(complexExpansion);
    }

    public XmlBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion){
        super(shortName, type);
        this.binaryWrapper = new DefaultBinaryInteraction(complexExpansion);

    }

    public XmlBinaryInteraction(Participant participantA, Participant participantB, CvTerm complexExpansion){
        super();
        this.binaryWrapper = new DefaultBinaryInteraction(participantA, participantB, complexExpansion);

    }

    public XmlBinaryInteraction(String shortName, Participant participantA, Participant participantB, CvTerm complexExpansion){
        super(shortName);
        this.binaryWrapper = new DefaultBinaryInteraction(participantA, participantB, complexExpansion);
    }

    public XmlBinaryInteraction(String shortName, CvTerm type, Participant participantA, Participant participantB, CvTerm complexExpansion){
        super(shortName, type);
        this.binaryWrapper = new DefaultBinaryInteraction(participantA, participantB, complexExpansion);
    }

    public Participant getParticipantA() {
        return this.binaryWrapper.getParticipantA();
    }

    public Participant getParticipantB() {
        return this.binaryWrapper.getParticipantB();
    }

    public void setParticipantA(Participant participantA) {
        this.binaryWrapper.setParticipantA(participantA);
    }

    public void setParticipantB(Participant participantB) {
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
    public Collection<Participant> getParticipants() {
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
    public boolean addParticipant(Participant part) {
        return this.binaryWrapper.addParticipant(part);
    }

    /**
     * Removes the Participant from this binary interaction
     * @param part
     * @return
     */
    @Override
    public boolean removeParticipant(Participant part) {
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
    public boolean addAllParticipants(Collection<? extends Participant> participants) {
        return this.binaryWrapper.addAllParticipants(participants);
    }

    @Override
    public boolean removeAllParticipants(Collection<? extends Participant> participants) {
        return this.binaryWrapper.removeAllParticipants(participants);
    }
}
