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

public class MinimalPolymerUpdater<T extends Polymer> extends MinimalInteractorUpdater<T> implements PolymerEnricher<T>{

    public MinimalPolymerUpdater(){
        super();
    }

    public MinimalPolymerUpdater(InteractorFetcher<T> fetcher){
        super(fetcher);
    }

    @Override
    protected void processOtherProperties(T polymerToUpdate, T fetched) {

        // sequence
        if ((fetched.getSequence() != null && !fetched.getSequence().equalsIgnoreCase(polymerToUpdate.getSequence())
                || (fetched.getSequence() == null && polymerToUpdate.getSequence() != null))){
            String oldSequence = polymerToUpdate.getSequence();
            polymerToUpdate.setSequence(fetched.getSequence());
            if (getListener() instanceof PolymerEnricherListener){
                ((PolymerChangeListener)getListener()).onSequenceUpdate(polymerToUpdate, oldSequence);
            }
        }
    }
}
