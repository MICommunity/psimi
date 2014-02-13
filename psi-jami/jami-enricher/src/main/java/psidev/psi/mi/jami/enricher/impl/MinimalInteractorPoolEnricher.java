package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.InteractorEnricher;
import psidev.psi.mi.jami.enricher.InteractorPoolEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalInteractorBaseEnricher;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

import java.util.*;

/**
 * A basic minimal enricher for interactor pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalInteractorPoolEnricher extends MinimalInteractorBaseEnricher<InteractorPool> implements InteractorPoolEnricher{

    private InteractorEnricher<Interactor> interactorEnricher;
    private Comparator<Interactor> interactorComparator;

    public MinimalInteractorPoolEnricher(){
        super();
    }

    public MinimalInteractorPoolEnricher(InteractorFetcher<InteractorPool> fetcher){
        super(fetcher);
    }

    public InteractorEnricher<Interactor> getInteractorEnricher() {
        return interactorEnricher;
    }

    public void setInteractorEnricher(InteractorEnricher<Interactor> interactorEnricher) {
        this.interactorEnricher = interactorEnricher;
    }

    public Comparator<Interactor> getInteractorComparator() {
        if (interactorComparator == null){
            interactorComparator = new UnambiguousExactInteractorComparator();
        }
        return interactorComparator;
    }

    public void setInteractorComparator(Comparator<Interactor> interactorComparator) {
        this.interactorComparator = interactorComparator;
    }

    @Override
    protected void processOtherProperties(InteractorPool poolToEnrich, InteractorPool fetched) throws EnricherException {

        TreeSet<Interactor> set1 = new TreeSet<Interactor>(getInteractorComparator());
        set1.addAll(poolToEnrich);
        TreeSet<Interactor> set2 = new TreeSet<Interactor>(getInteractorComparator());
        set2.addAll(fetched);

        Iterator<Interactor> iterator1 = set1.iterator();
        Iterator<Interactor> iterator2 = set2.iterator();

        Collection<Interactor> interactorsToAdd = new ArrayList<Interactor>(fetched.size());
        Interactor i1 = iterator1.hasNext() ? iterator1.next() : null;
        Interactor i2 = iterator2.hasNext() ? iterator2.next() : null;
        while(i1 != null && i2 != null){

            int comp = getInteractorComparator().compare(i1, i2);
            // i1 is before i2. It means that i1 is not in i2
            // we can delete the interactor from the object to enrich if allowed
            if (comp < 0){
                if (removeInteractorsFromPool()){
                    poolToEnrich.remove(i1);
                }
                else{
                    // we enrich i2
                    if (getInteractorEnricher() != null){
                        getInteractorEnricher().enrich(i1);
                    }
                }
                i1 = iterator1.hasNext() ? iterator1.next() : null;
            }
             // i1 is after i2. It means that i2 is not in i1
             // we can add the interactor to the object to enrich
            else if (comp > 0){
                interactorsToAdd.add(i2);
                // we enrich i2
                if (getInteractorEnricher() != null){
                    getInteractorEnricher().enrich(i2);
                }
                i2 = iterator2.hasNext() ? iterator2.next() : null;
            }
            // i1 is the same as i2.
            else{
                // we enrich i1 with properties of i2 in case we have small differences
                if (getInteractorEnricher() != null){
                    getInteractorEnricher().enrich(i1, i2);
                    // then enrich i1
                    getInteractorEnricher().enrich(i1);
                }
                i1 = iterator1.hasNext() ? iterator1.next() : null;
                i2 = iterator2.hasNext() ? iterator2.next() : null;
            }
        }

        // It means that i1 is not in i2
        // we can delete the interactor from the object to enrich if allowed
        if (i1 != null && removeInteractorsFromPool()){
            iterator1.remove();
            while(iterator1.hasNext()){
                poolToEnrich.remove(iterator1.next());
            }
        }
        // It means that i2 is not in i1
        // we can add the interactor to the object to enrich
        else if (i2 != null){
            interactorsToAdd.add(i2);
            while(iterator2.hasNext()){
                interactorsToAdd.add(iterator2.next());
            }
        }

        poolToEnrich.addAll(interactorsToAdd);
    }

    protected boolean removeInteractorsFromPool(){
        return false;
    }
}
