package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.listener.PolymerEnricherListener;
import psidev.psi.mi.jami.listener.PolymerChangeListener;
import psidev.psi.mi.jami.model.Polymer;

/**
 * Full updater of polymer
 *
 * - update all properties of interactor as described in FullInteractorBaseUpdater
 * - update sequence of polymer. If the sequence of the polymer to enrich is different from the one of the fetched polymer, it will enrich it with the
 * sequence of the fetched polymer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullPolymerUpdater<T extends Polymer> extends FullInteractorBaseUpdater<T>{
    public FullPolymerUpdater() {
        super(new FullPolymerEnricher<T>());
    }

    public FullPolymerUpdater(InteractorFetcher<T> fetcher) {
        super(new FullPolymerEnricher<T>(fetcher));
    }

    @Override
    protected void processOtherProperties(T polymerToUpdate, T fetched) {

        // sequence
        if (fetched != null &&
                (fetched.getSequence() != null && !fetched.getSequence().equalsIgnoreCase(polymerToUpdate.getSequence())
                || (fetched.getSequence() == null && polymerToUpdate.getSequence() != null))){
            String oldSequence = polymerToUpdate.getSequence();
            polymerToUpdate.setSequence(fetched.getSequence());
            if (getListener() instanceof PolymerEnricherListener){
                ((PolymerChangeListener)getListener()).onSequenceUpdate(polymerToUpdate, oldSequence);
            }
        }
    }
}
