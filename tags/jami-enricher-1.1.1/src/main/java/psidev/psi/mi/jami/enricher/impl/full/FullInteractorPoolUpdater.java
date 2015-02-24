package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.CompositeInteractorEnricher;
import psidev.psi.mi.jami.listener.InteractorPoolChangeListener;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

import java.util.*;

/**
 * A full updater for interactor pools.
 *
 * See description of full update in AbstractInteractorUpdater.
 * - update all interactors in existing pool using a composite interactor enricher if it is not null.
 * It will add missing interactors in the pool and remove any interactors that are not in the fetched interactor pool
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorPoolUpdater extends FullInteractorBaseUpdater<InteractorPool> {
    private CompositeInteractorEnricher interactorEnricher;
    private Comparator<Interactor> interactorComparator;

    public FullInteractorPoolUpdater() {
        super();
    }

    public FullInteractorPoolUpdater(InteractorFetcher<InteractorPool> fetcher) {
        super(fetcher);
    }

    public CompositeInteractorEnricher getInteractorMemberEnricher() {
        return interactorEnricher;
    }

    public void setInteractorEnricher(CompositeInteractorEnricher interactorEnricher) {
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

    protected boolean isFullEnrichment() {
        return true;
    }

    @Override
    protected void processOtherProperties(InteractorPool poolToEnrich, InteractorPool fetched) throws EnricherException {
        processMembers(poolToEnrich, fetched);
    }

    protected void processMembers(InteractorPool poolToEnrich, InteractorPool fetched) throws EnricherException {
        if (fetched != null){
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
                    poolToEnrich.remove(i1);
                    if (getListener() instanceof InteractorPoolChangeListener){
                        ((InteractorPoolChangeListener)getListener()).onRemovedInteractor(poolToEnrich, i1);
                    }
                    i1 = iterator1.hasNext() ? iterator1.next() : null;
                }
                // i1 is after i2. It means that i2 is not in i1
                // we can add the interactor to the object to enrich
                else if (comp > 0){
                    interactorsToAdd.add(i2);
                    if (getListener() instanceof InteractorPoolChangeListener){
                        ((InteractorPoolChangeListener)getListener()).onAddedInteractor(poolToEnrich, i2);
                    }
                    // we enrich i2
                    if (getInteractorMemberEnricher() != null){
                        getInteractorMemberEnricher().enrich(i2);
                    }
                    i2 = iterator2.hasNext() ? iterator2.next() : null;
                }
                // i1 is the same as i2.
                else{
                    // we enrich i1 with properties of i2 in case we have small differences
                    if (getInteractorMemberEnricher() != null){
                        getInteractorMemberEnricher().enrich(i1, i2);
                        // then enrich i1
                        getInteractorMemberEnricher().enrich(i1);
                    }
                    i1 = iterator1.hasNext() ? iterator1.next() : null;
                    i2 = iterator2.hasNext() ? iterator2.next() : null;
                }
            }

            // It means that i1 is not in i2
            // we can delete the interactor from the object to enrich if allowed
            if (i1 != null ){
                iterator1.remove();
                while(iterator1.hasNext()){
                    Interactor interactorToRemove = iterator1.next();
                    poolToEnrich.remove(interactorToRemove);
                    if (getListener() instanceof InteractorPoolChangeListener){
                        ((InteractorPoolChangeListener)getListener()).onRemovedInteractor(poolToEnrich, interactorToRemove);
                    }
                }
            }
            // It means that i2 is not in i1
            // we can add the interactor to the object to enrich
            else if (i2 != null){
                interactorsToAdd.add(i2);
                while(iterator2.hasNext()){
                    Interactor interactorToAdd = iterator2.next();
                    interactorsToAdd.add(interactorToAdd);
                    if (getListener() instanceof InteractorPoolChangeListener){
                        ((InteractorPoolChangeListener)getListener()).onAddedInteractor(poolToEnrich, interactorToAdd);
                    }
                }
            }

            poolToEnrich.addAll(interactorsToAdd);
        }
        else if (getInteractorMemberEnricher() != null){
            for (Interactor i : poolToEnrich){
                getInteractorMemberEnricher().enrich(i);
            }
        }
    }
}
