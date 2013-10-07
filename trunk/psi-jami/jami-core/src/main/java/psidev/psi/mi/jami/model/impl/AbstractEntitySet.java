package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.*;

/**
 * Default implementation of EntitySet
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */

public abstract class AbstractEntitySet<I extends Interaction, F extends Feature, C extends Entity> extends AbstractParticipant<I,F> implements EntitySet<I,F,C> {

    private Set<C> components;
    private CvTerm type;

    public AbstractEntitySet(InteractorSet interactor) {
        super(interactor);
        initialiseComponentCandidatesSet();
    }

    public AbstractEntitySet(InteractorSet interactor, CvTerm bioRole) {
        super(interactor, bioRole);
        initialiseComponentCandidatesSet();
    }

    public AbstractEntitySet(InteractorSet interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
        initialiseComponentCandidatesSet();
    }

    public AbstractEntitySet(InteractorSet interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
        initialiseComponentCandidatesSet();
    }

    protected void initialiseComponentCandidatesSet(){
        this.components = new HashSet<C>();
    }

    protected void initialiseInteractorCandidatesSetWith(Set<C> interactorCandidates){
        if (interactorCandidates == null){
            this.components = Collections.EMPTY_SET;
        }
        else {
            this.components = interactorCandidates;
        }
    }

    @Override
    public InteractorSet getInteractor() {
        return (InteractorSet) super.getInteractor();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        throw new UnsupportedOperationException("Cannot set the interactor of an EntitySet as it is an interactorSet that is related to the interactors in the set of entities");
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
        return components.size();
    }

    public boolean isEmpty() {
        return components.isEmpty();
    }

    public boolean contains(Object o) {
        return components.contains(o);
    }

    public Iterator<C> iterator() {
        return components.iterator();
    }

    public Object[] toArray() {
        return components.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return components.toArray(ts);
    }

    public boolean add(C interactor) {
        if (components.add(interactor)){
            getInteractor().add(interactor.getInteractor());
            return true;
        }
        return false;
    }

    public boolean remove(Object o) {
        if (components.remove(o)){
            getInteractor().remove(((Entity)o).getInteractor());
            return true;
        }
        return false;
    }

    public boolean containsAll(Collection<?> objects) {
        return components.containsAll(objects);
    }

    public boolean addAll(Collection<? extends C> interactors) {
        boolean added = this.components.addAll(interactors);
        if (added){
            for (C entity : this){
                getInteractor().add(entity.getInteractor());
            }
        }
        return added;
    }

    public boolean retainAll(Collection<?> objects) {
        boolean retain = components.retainAll(objects);
        if (retain){
            Collection<Interactor> interactors = new ArrayList<Interactor>(objects.size());
            for (Object o : objects){
                interactors.add(((Entity)o).getInteractor());
            }
            getInteractor().retainAll(interactors);
        }
        return retain;
    }

    public boolean removeAll(Collection<?> objects) {
        boolean remove = components.removeAll(objects);
        if (remove){
            Collection<Interactor> interactors = new ArrayList<Interactor>(objects.size());
            for (Object o : objects){
                interactors.add(((Entity)o).getInteractor());
            }
            // check if an interactor is not in another entity that is kept.
            // remove any interactors that are kept with other entities
            for (C entity : this){
                interactors.remove(entity.getInteractor());
            }
            getInteractor().removeAll(interactors);
        }
        return remove;
    }

    public void clear() {
        components.clear();
        getInteractor().clear();
    }
}
