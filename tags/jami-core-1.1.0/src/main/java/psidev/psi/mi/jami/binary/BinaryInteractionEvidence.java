package psidev.psi.mi.jami.binary;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * A BinaryInteractionEvidence is an InteractionEvidence with only two participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public interface BinaryInteractionEvidence extends BinaryInteraction<ParticipantEvidence>,InteractionEvidence{
}
