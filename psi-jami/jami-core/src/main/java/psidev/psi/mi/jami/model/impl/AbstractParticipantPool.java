package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Abstract class for Participant pool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public abstract class AbstractParticipantPool<I extends Interaction, F extends Feature, P extends ParticipantCandidate>
        extends AbstractParticipant<I,F> implements ParticipantPool<I,F,P>, EntityInteractorChangeListener {
    private Collection<P> candidates;
    private CvTerm type;

    public AbstractParticipantPool(Interactor interactor){
        super(interactor);
        initialiseComponentCandidatesSet();
    }

    public AbstractParticipantPool(Interactor interactor, CvTerm bioRole){
        super(interactor);
        initialiseComponentCandidatesSet();
    }

    public AbstractParticipantPool(Interactor interactor, Stoichiometry stoichiometry){
        super(interactor, stoichiometry);
        initialiseComponentCandidatesSet();
    }

    public AbstractParticipantPool(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry){
        super(interactor, stoichiometry);
        initialiseComponentCandidatesSet();
    }

    protected void initialiseComponentCandidatesSet() {
        this.candidates = new ArrayList<P>();
    }

    protected void initialiseComponentCandidatesSetWith(Collection<P> candidates) {
        if (candidates == null){
            this.candidates = Collections.EMPTY_LIST;
        }
        else {
            this.candidates = candidates;
        }
    }

    @Override
    public InteractorPool getInteractor() {
        return (InteractorPool) super.getInteractor();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        throw new UnsupportedOperationException("Cannot set the interactor of an ParticipantPool as it is an interactorSet that is related to the interactors in the set of entities");
    }

    public CvTerm getType() {
        return type;
    }

    /**
     * Sets the component set type.
     * Sets the type to molecule set (MI:1304) if the given type is null
     */
    public void setType(CvTerm type) {
        if (type == null){
            this.type = CvTermUtils.createMoleculeSetType();
        }
        else {
            this.type = type;
        }
        getInteractor().setInteractorType(this.type);
    }

    public int size() {
        return candidates.size();
    }

    public boolean isEmpty() {
        return candidates.isEmpty();
    }

    public boolean contains(Object o) {
        return candidates.contains(o);
    }

    public Iterator<P> iterator() {
        return candidates.iterator();
    }

    public Object[] toArray() {
        return candidates.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return candidates.toArray(ts);
    }

    public boolean add(P interactor) {
        if (candidates.add(interactor)){
            interactor.setChangeListener(this);
            getInteractor().add(interactor.getInteractor());
            interactor.setParentPool(this);
            return true;
        }
        return false;
    }

    public boolean remove(Object o) {
        if (candidates.remove(o)){
            ParticipantCandidate entity = (ParticipantCandidate)o;
            entity.setChangeListener(null);
            getInteractor().remove(entity.getInteractor());
            entity.setParentPool(null);
            return true;
        }
        return false;
    }

    public boolean containsAll(Collection<?> objects) {
        return candidates.containsAll(objects);
    }

    public boolean addAll(Collection<? extends P> interactors) {
        boolean added = this.candidates.addAll(interactors);
        if (added){
            for (P entity : this){
                entity.setChangeListener(this);
                getInteractor().add(entity.getInteractor());
                entity.setParentPool(this);
            }
        }
        return added;
    }

    public boolean retainAll(Collection<?> objects) {
        boolean retain = candidates.retainAll(objects);
        if (retain){
            Collection<Interactor> interactors = new ArrayList<Interactor>(objects.size());
            for (Object o : objects){
                interactors.add(((Participant)o).getInteractor());
            }
            getInteractor().retainAll(interactors);
        }
        return retain;
    }

    public boolean removeAll(Collection<?> objects) {
        boolean remove = candidates.removeAll(objects);
        if (remove){
            Collection<Interactor> interactors = new ArrayList<Interactor>(objects.size());
            for (Object o : objects){
                Participant entity = (Participant)o;
                entity.setChangeListener(null);
                interactors.add(entity.getInteractor());
                entity.setInteraction(null);
            }
            // check if an interactor is not in another entity that is kept.
            // remove any interactors that are kept with other entities
            for (P entity : this){
                interactors.remove(entity.getInteractor());
            }
            getInteractor().removeAll(interactors);
        }
        return remove;
    }

    public void clear() {
        for (P entity : this){
            entity.setChangeListener(null);
            entity.setParentPool(null);
        }
        candidates.clear();
        getInteractor().clear();
    }

    public void onInteractorUpdate(Entity entity, Interactor oldInteractor) {
        // check that the listener still makes sensr
        if (contains(entity)){
            boolean needsToRemoveOldInteractor = true;
            // check if an interactor is not in another entity that is kept.
            // remove any interactors that are kept with other entities
            for (P e : this){
                // we want to check if an interactor is the same as old interactor in another entry
                if (e != entity){
                    if (oldInteractor.equals(e.getInteractor())){
                        needsToRemoveOldInteractor = false;
                    }
                }
            }
            if (!needsToRemoveOldInteractor){
                getInteractor().remove(oldInteractor);
            }
            getInteractor().add(entity.getInteractor());
        }
    }

}
