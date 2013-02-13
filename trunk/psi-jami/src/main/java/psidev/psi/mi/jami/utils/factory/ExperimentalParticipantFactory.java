package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;

/**
 * Factory for participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class ExperimentalParticipantFactory {

     public static ParticipantEvidence createUnknownBasicParticipant(){
         return new DefaultParticipantEvidence(ExperimentalInteractionFactory.createEmptyBasicExperimentalInteraction(), InteractorFactory.createUnknownBasicInteractor(), new DefaultCvTerm("unspecified method"));
     }
}
