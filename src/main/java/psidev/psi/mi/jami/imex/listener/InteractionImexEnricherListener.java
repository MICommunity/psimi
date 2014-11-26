package psidev.psi.mi.jami.imex.listener;

import psidev.psi.mi.jami.enricher.listener.InteractionEvidenceEnricherListener;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;

import java.util.Collection;

/**
 * An extension of the ExperimentEnricherListener
 * with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the publication.
 */
public interface InteractionImexEnricherListener
        extends InteractionEvidenceEnricherListener{


    public void onImexIdConflicts(InteractionEvidence originalInteraction, Collection<Xref> conflictingXrefs);

    public void onImexIdAssigned(InteractionEvidence interaction, String imex);


}
