package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.Interactor;

/**
 * Interface for interactor enricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public interface InteractorEnricher<T extends Interactor> extends MIEnricher<T>{

    /**
     * Sets the listener to use when the bioactiveEntity has been changed
     * @param listener  The new listener. Can be null.
     */
    public void setListener(InteractorEnricherListener<T> listener);

    /**
     * The current listener of changes to the bioactiveEntities.
     * @return  The current listener. Can be null.
     */
    public InteractorEnricherListener<T> getListener();

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);

    public CvTermEnricher getCvTermEnricher();

    public void setOrganismEnricher(OrganismEnricher organismEnricher);

    public OrganismEnricher getOrganismEnricher();
}
