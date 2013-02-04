package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorCandidates;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorCandidatesComparator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * TDefault implementation for interactor candidates
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultInteractorCandidates extends DefaultInteractor implements InteractorCandidates {

    private Set<Interactor> interactors;

    public DefaultInteractorCandidates(String name, CvTerm type) {
        super(name, type);
        this.interactors = new HashSet<Interactor>();
    }

    public DefaultInteractorCandidates(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
        this.interactors = new HashSet<Interactor>();
    }

    public DefaultInteractorCandidates(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
        this.interactors = new HashSet<Interactor>();
    }

    public DefaultInteractorCandidates(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
        this.interactors = new HashSet<Interactor>();
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

    public Iterator<Interactor> iterator() {
        return interactors.iterator();
    }

    public Object[] toArray() {
        return interactors.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return interactors.toArray(ts);
    }

    public boolean add(Interactor interactor) {
        return interactors.add(interactor);
    }

    public boolean remove(Object o) {
        return interactors.remove(o);
    }

    public boolean containsAll(Collection<?> objects) {
        return interactors.containsAll(objects);
    }

    public boolean addAll(Collection<? extends Interactor> interactors) {
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

        if (!(o instanceof InteractorCandidates)){
            return false;
        }

        // use UnambiguousExactInteractorCandidates comparator for equals
        return UnambiguousExactInteractorCandidatesComparator.areEquals(this, (InteractorCandidates) o);
    }
}
