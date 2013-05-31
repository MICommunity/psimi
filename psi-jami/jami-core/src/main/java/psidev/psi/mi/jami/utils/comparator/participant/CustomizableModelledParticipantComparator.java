package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;

import java.util.Comparator;

/**
 * A ModelledParticipantComparator that can be customized so we can ignore interactors
 * when comparing modelled participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public interface CustomizableModelledParticipantComparator extends Comparator<ModelledParticipant> {

    public boolean isCheckComplexesAsInteractors();

    public void setCheckComplexesAsInteractors(boolean checkComplexesAsInteractors);
}
