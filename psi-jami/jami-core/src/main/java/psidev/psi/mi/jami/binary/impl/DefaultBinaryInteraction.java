package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;

/**
 * Default implementation for BinaryInteraction
 *
 * Note: the methods equals and hashcode have not been overriden. Use the same comparators as for DefaultInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class DefaultBinaryInteraction extends AbstractBinaryInteraction<Participant> {

    public DefaultBinaryInteraction(){
        super();
    }

    public DefaultBinaryInteraction(String shortName){
        super(shortName);
    }

    public DefaultBinaryInteraction(String shortName, CvTerm type){
        super(shortName, type);
    }

    public DefaultBinaryInteraction(Participant participantA, Participant participantB) {
        super(participantA, participantB);
    }

    public DefaultBinaryInteraction(String shortName, Participant participantA, Participant participantB) {
        super(shortName, participantA, participantB);
    }

    public DefaultBinaryInteraction(String shortName, CvTerm type, Participant participantA, Participant participantB) {
        super(shortName, type, participantA, participantB);
    }

    public DefaultBinaryInteraction(CvTerm complexExpansion) {
        super(complexExpansion);
    }

    public DefaultBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion) {
        super(shortName, type, complexExpansion);
    }

    public DefaultBinaryInteraction(Participant participantA, Participant participantB, CvTerm complexExpansion) {
        super(participantA, participantB, complexExpansion);
    }

    public DefaultBinaryInteraction(String shortName, Participant participantA, Participant participantB, CvTerm complexExpansion) {
        super(shortName, participantA, participantB, complexExpansion);
    }

    public DefaultBinaryInteraction(String shortName, CvTerm type, Participant participantA, Participant participantB, CvTerm complexExpansion) {
        super(shortName, type, participantA, participantB, complexExpansion);
    }
}
