package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.PolymerEnricher;
import psidev.psi.mi.jami.enricher.listener.PolymerEnricherListener;
import psidev.psi.mi.jami.listener.PolymerChangeListener;
import psidev.psi.mi.jami.model.Polymer;

/**
 * A basic minimal enricher for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalPolymerEnricher<T extends Polymer> extends MinimalInteractorBaseEnricher<T> implements PolymerEnricher<T>{

    public MinimalPolymerEnricher(){
        super();
    }

    public MinimalPolymerEnricher(InteractorFetcher<T> fetcher){
        super(fetcher);
    }


    @Override
    protected void processOtherProperties(T polymerToEnrich, T fetched) {

        // sequence
        if (polymerToEnrich.getSequence() == null && fetched.getSequence() != null){
            polymerToEnrich.setSequence(fetched.getSequence());
            if (getListener() instanceof PolymerEnricherListener){
                ((PolymerChangeListener)getListener()).onSequenceUpdate(polymerToEnrich, null);
            }
        }
    }
}
