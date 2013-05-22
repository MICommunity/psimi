package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorSetComparator;

import java.util.*;

/**
 * TDefault implementation for interactor set
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultInteractorSet extends DefaultInteractor implements InteractorSet {

    private Set<Molecule> interactors;

    public DefaultInteractorSet(String name, CvTerm type) {
        super(name, type);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorSet(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public DefaultInteractorSet(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public DefaultInteractorSet(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    protected void initialiseInteractorCandidatesSet(){
        this.interactors = new HashSet<Molecule>();
    }

    protected void initialiseInteractorCandidatesSetWith(Set<Molecule> interactorCandidates){
        if (interactorCandidates == null){
            this.interactors = Collections.EMPTY_SET;
        }
        else {
            this.interactors = interactorCandidates;
        }
    }

    public int size() {
        return interactors.size();
    }

    public boolean isEmpty() {
        return interactors.isEmpty();
    }

    public boolean contains(Object o) {
        return interactors.contains(o);
    }

    public Iterator<Molecule> iterator() {
        return interactors.iterator();
    }

    public Object[] toArray() {
        return interactors.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return interactors.toArray(ts);
    }

    public boolean add(Molecule interactor) {
        return interactors.add(interactor);
    }

    public boolean remove(Object o) {
        return interactors.remove(o);
    }

    public boolean containsAll(Collection<?> objects) {
        return interactors.containsAll(objects);
    }

    public boolean addAll(Collection<? extends Molecule> interactors) {
        return this.interactors.addAll(interactors);
    }

    public boolean retainAll(Collection<?> objects) {
        return interactors.retainAll(objects);
    }

    public boolean removeAll(Collection<?> objects) {
        return interactors.removeAll(objects);
    }

    public void clear() {
        interactors.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof InteractorSet)){
            return false;
        }

        // use UnambiguousExactInteractorCandidates comparator for equals
        return UnambiguousExactInteractorSetComparator.areEquals(this, (InteractorSet) o);
    }
}
