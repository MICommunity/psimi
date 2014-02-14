package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.EntityPoolEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.EntityPool;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantComparator;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class MinimalEntityPoolEnricher<P extends EntityPool, F extends Feature> extends MinimalParticipantEnricher<P , F>
implements EntityPoolEnricher<P,F>{

    private ParticipantEnricher participantEnricher;
    private Comparator<Entity> participantComparator;

    public MinimalEntityPoolEnricher(){
        super();
    }

    public ParticipantEnricher getParticipantEnricher() {
        return participantEnricher;
    }

    public void setParticipantEnricher(ParticipantEnricher participantEnricher) {
        this.participantEnricher = participantEnricher;
    }

    public Comparator<Entity> getParticipantComparator() {
        if (participantComparator == null){
            participantComparator = new UnambiguousExactParticipantComparator();
        }
        return participantComparator;
    }

    public void setParticipantComparator(Comparator<Entity> participantComparator) {
        this.participantComparator = participantComparator;
    }

    @Override
    public void processInteractor(P objectToEnrich, P objectSource) throws EnricherException {
        // nothing to do here
    }

    @Override
    public void processOtherProperties(P poolToEnrich, P fetched) throws EnricherException {

        TreeSet<Entity> set1 = new TreeSet<Entity>(getParticipantComparator());
        set1.addAll(poolToEnrich);
        TreeSet<Entity> set2 = new TreeSet<Entity>(getParticipantComparator());
        set2.addAll(fetched);

        Iterator<Entity> iterator1 = set1.iterator();
        Iterator<Entity> iterator2 = set2.iterator();

        Collection<Entity> entitiesToAdd = new ArrayList<Entity>(fetched.size());
        Entity i1 = iterator1.hasNext() ? iterator1.next() : null;
        Entity i2 = iterator2.hasNext() ? iterator2.next() : null;
        while(i1 != null && i2 != null){

            int comp = getParticipantComparator().compare(i1, i2);
            // i1 is before i2. It means that i1 is not in i2
            // we can delete the interactor from the object to enrich if allowed
            if (comp < 0){
                if (removeEntitiesFromPool()){
                    poolToEnrich.remove(i1);
                }
                else{
                    // we enrich i2
                    if (getParticipantEnricher() != null){
                        getParticipantEnricher().enrich(i1);
                    }
                }
                i1 = iterator1.hasNext() ? iterator1.next() : null;
            }
            // i1 is after i2. It means that i2 is not in i1
            // we can add the interactor to the object to enrich
            else if (comp > 0){
                entitiesToAdd.add(i2);
                // we enrich i2
                if (getParticipantEnricher() != null){
                    getParticipantEnricher().enrich(i2);
                }
                i2 = iterator2.hasNext() ? iterator2.next() : null;
            }
            // i1 is the same as i2.
            else{
                // we enrich i1 with properties of i2 in case we have small differences
                if (getParticipantEnricher() != null){
                    getParticipantEnricher().enrich(i1, i2);
                    // then enrich i1
                    getParticipantEnricher().enrich(i1);
                }
                i1 = iterator1.hasNext() ? iterator1.next() : null;
                i2 = iterator2.hasNext() ? iterator2.next() : null;
            }
        }

        // It means that i1 is not in i2
        // we can delete the interactor from the object to enrich if allowed
        if (i1 != null && removeEntitiesFromPool()){
            iterator1.remove();
            while(iterator1.hasNext()){
                poolToEnrich.remove(iterator1.next());
            }
        }
        // It means that i2 is not in i1
        // we can add the interactor to the object to enrich
        else if (i2 != null){
            entitiesToAdd.add(i2);
            while(iterator2.hasNext()){
                entitiesToAdd.add(iterator2.next());
            }
        }

        poolToEnrich.addAll(entitiesToAdd);
    }

    protected boolean removeEntitiesFromPool(){
        return false;
    }
}
