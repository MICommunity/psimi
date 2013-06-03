package psidev.psi.mi.jami.utils;

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

public class ParticipantUtils {

     public static ParticipantEvidence createUnknownBasicExperimentalParticipant(){
         return new DefaultParticipantEvidence(InteractionUtils.createEmptyBasicExperimentalInteraction(), InteractorUtils.createUnknownBasicInteractor(), new DefaultCvTerm("unspecified method"));
     }
}
