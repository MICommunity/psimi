package uk.ac.ebi.intact.jami.imex.listener;

import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.util.Collection;

/**
 * An extension of the PublicationEnricherListener
 * with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the publication.
 */
public interface PublicationImexEnricherListener
        extends PublicationEnricherListener{


    public void onImexIdConflicts(Publication originalPublication, Collection<Xref> conflictingXrefs);
}
