package psidev.psi.mi.jami.xml.extension.binary;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultModelledBinaryInteraction;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.xml.extension.XmlModelledInteraction;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

/**
 * Xml implementation of ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlModelledBinaryInteraction extends XmlModelledInteraction implements ModelledBinaryInteraction{
    private ModelledBinaryInteraction binaryWrapper;

    public XmlModelledBinaryInteraction(){
        super();
        this.binaryWrapper = new DefaultModelledBinaryInteraction();
    }

    public XmlModelledBinaryInteraction(String shortName){
        super(shortName);
        this.binaryWrapper = new DefaultModelledBinaryInteraction();
    }

    public XmlModelledBinaryInteraction(String shortName, CvTerm type){
        super(shortName, type);
        this.binaryWrapper = new DefaultModelledBinaryInteraction();
    }

    public XmlModelledBinaryInteraction(ModelledParticipant participantA, ModelledParticipant participantB){
        super();
        this.binaryWrapper = new DefaultModelledBinaryInteraction(participantA, participantB);

    }

    public XmlModelledBinaryInteraction(String shortName, ModelledParticipant participantA, ModelledParticipant participantB){
        super(shortName);
        this.binaryWrapper = new DefaultModelledBinaryInteraction(participantA, participantB);
    }

    public XmlModelledBinaryInteraction(String shortName, CvTerm type, ModelledParticipant participantA, ModelledParticipant participantB){
        super(shortName, type);
        this.binaryWrapper = new DefaultModelledBinaryInteraction(participantA, participantB);
    }

    public XmlModelledBinaryInteraction(CvTerm complexExpansion){
        super();
        this.binaryWrapper = new DefaultModelledBinaryInteraction(complexExpansion);
    }

    public XmlModelledBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion){
        super(shortName, type);
        this.binaryWrapper = new DefaultModelledBinaryInteraction(complexExpansion);

    }

    public XmlModelledBinaryInteraction(ModelledParticipant participantA, ModelledParticipant participantB, CvTerm complexExpansion){
        super();
        this.binaryWrapper = new DefaultModelledBinaryInteraction(participantA, participantB, complexExpansion);

    }

    public XmlModelledBinaryInteraction(String shortName, ModelledParticipant participantA, ModelledParticipant participantB, CvTerm complexExpansion){
        super(shortName);
        this.binaryWrapper = new DefaultModelledBinaryInteraction(participantA, participantB, complexExpansion);
    }

    public XmlModelledBinaryInteraction(String shortName, CvTerm type, ModelledParticipant participantA, ModelledParticipant participantB, CvTerm complexExpansion){
        super(shortName, type);
        this.binaryWrapper = new DefaultModelledBinaryInteraction(participantA, participantB, complexExpansion);
    }

    public ModelledParticipant getParticipantA() {
        return this.binaryWrapper.getParticipantA();
    }

    public ModelledParticipant getParticipantB() {
        return this.binaryWrapper.getParticipantB();
    }

    public void setParticipantA(ModelledParticipant participantA) {
        this.binaryWrapper.setParticipantA(participantA);
    }

    public void setParticipantB(ModelledParticipant participantB) {
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
    public Collection<ModelledParticipant> getParticipants() {
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
    public boolean addParticipant(ModelledParticipant part) {
        return this.binaryWrapper.addParticipant(part);
    }

    /**
     * Removes the Participant from this binary interaction
     * @param part
     * @return
     */
    @Override
    public boolean removeParticipant(ModelledParticipant part) {
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
    public boolean addAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.binaryWrapper.addAllParticipants(participants);
    }

    @Override
    public boolean removeAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.binaryWrapper.removeAllParticipants(participants);
    }

    @Override
    public void setJAXBId(int value) {
        setJAXBIdOnly(value);
    }
}
