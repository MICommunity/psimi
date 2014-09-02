package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Wrapper for Xml participants pools
 *
 * Addeding new modelled feature to this participant will not add new feature evidences to the wrapped participant evidence as they are incompatibles.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
@XmlTransient
public class XmlExperimentalParticipantPoolWrapper extends XmlParticipantEvidenceWrapper implements ModelledParticipantPool {

    private Collection<ModelledParticipantCandidate> candidateList;

    public XmlExperimentalParticipantPoolWrapper(ExperimentalParticipantPool part, XmlInteractionEvidenceComplexWrapper wrapper){
        super((ExtendedPsiXmlParticipantEvidence)part, wrapper);
        initialiseCandidates();
    }

    protected Collection<ModelledParticipantCandidate> getCandidates(){
        return this.candidateList;
    }

    protected void initialiseCandidates(){
        candidateList = new ArrayList<ModelledParticipantCandidate>();
        for (ExperimentalParticipantCandidate candidate : (ExperimentalParticipantPool)getWrappedParticipant()){
            this.candidateList.add(new XmlExperimentalParticipantCandidateWrapper(candidate, this));
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
}
