package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import java.util.Collection;
import java.util.Iterator;

/**
 * Wrapper for XmlParticipant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public class XmlParticipantPoolWrapper extends XmlParticipantWrapper implements ModelledParticipantPool{

    private SynchronizedParticipantCandidateList candidateList;

    public XmlParticipantPoolWrapper(ParticipantPool part, ModelledInteraction wrapper){
        super((ExtendedPsiXmlParticipant)part, wrapper);
        initialiseCandidates();
    }

    protected SynchronizedParticipantCandidateList getCandidates(){
        return this.candidateList;
    }

    protected void initialiseCandidates(){
        candidateList = new SynchronizedParticipantCandidateList();
        for (Object candidate : (ParticipantPool)getWrappedParticipant()){
            this.candidateList.addOnly(new XmlParticipantCandidateWrapper((ParticipantCandidate)candidate, this));
        }
    }

    @Override
    public CvTerm getType() {
        return ((ParticipantPool)getWrappedParticipant()).getType();
    }

    @Override
    public void setType(CvTerm type) {
        ((ParticipantPool)getWrappedParticipant()).setType(type);
    }

    @Override
    public int size() {
        return getCandidates().size();
    }

    @Override
    public boolean isEmpty() {
        return getCandidates().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getCandidates().contains(o);
    }

    @Override
    public Iterator<ModelledParticipantCandidate> iterator() {
        return getCandidates().iterator();
    }

    @Override
    public Object[] toArray() {
        return getCandidates().toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return getCandidates().toArray(ts);
    }

    @Override
    public boolean add(ModelledParticipantCandidate modelledParticipantCandidate) {
        return getCandidates().add(modelledParticipantCandidate);
    }

    @Override
    public boolean remove(Object o) {
        return getCandidates().remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return getCandidates().containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends ModelledParticipantCandidate> modelledParticipantCandidates) {
        return getCandidates().addAll(modelledParticipantCandidates);
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        return getCandidates().removeAll(objects);
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        return getCandidates().retainAll(objects);
    }

    @Override
    public void clear() {
         getCandidates().clear();
    }


    ////////////////////////////////////// classes
    private class SynchronizedParticipantCandidateList extends AbstractListHavingProperties<ModelledParticipantCandidate> {

        private SynchronizedParticipantCandidateList() {
        }

        @Override
        protected void processAddedObjectEvent(ModelledParticipantCandidate added) {
            ((ParticipantPool)getWrappedParticipant()).add(added);
        }

        @Override
        protected void processRemovedObjectEvent(ModelledParticipantCandidate removed) {
            ((ParticipantPool)getWrappedParticipant()).remove(removed);
        }

        @Override
        protected void clearProperties() {
            ((ParticipantPool)getWrappedParticipant()).clear();
        }
    }
}