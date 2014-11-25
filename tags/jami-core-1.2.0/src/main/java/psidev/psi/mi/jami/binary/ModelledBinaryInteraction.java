package psidev.psi.mi.jami.binary;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;

/**
 * A ModelledBinary interaction is a ModelledInteraction with only two participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public interface ModelledBinaryInteraction extends BinaryInteraction<ModelledParticipant>, ModelledInteraction {
}
