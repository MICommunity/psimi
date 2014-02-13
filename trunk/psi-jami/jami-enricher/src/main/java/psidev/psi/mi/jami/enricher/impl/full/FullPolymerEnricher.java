package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.listener.PolymerEnricherListener;
import psidev.psi.mi.jami.listener.PolymerChangeListener;
import psidev.psi.mi.jami.model.Polymer;

/**
 * Full polymer enricher.
 *
 * - enrich all properties of interactor as described in FullInteractorBaseEnricher
 * - enrich sequence of polymer. If the sequence of the polymer to enrich is null, it will enrich it with the
 * sequence of the fetched polymer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullPolymerEnricher<T extends Polymer> extends FullInteractorBaseEnricher<T> {

    public FullPolymerEnricher() {
        super();
    }

    public FullPolymerEnricher(InteractorFetcher<T> fetcher) {
        super(fetcher);
    }

    @Override
    protected void processOtherProperties(T polymerToEnrich, T fetched) {

        // sequence
        if (fetched != null && polymerToEnrich.getSequence() == null && fetched.getSequence() != null){
            polymerToEnrich.setSequence(fetched.getSequence());
            if (getListener() instanceof PolymerEnricherListener){
                ((PolymerChangeListener)getListener()).onSequenceUpdate(polymerToEnrich, null);
            }
        }
    }
}
