package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.cooperativity.UnambiguousCooperativityEvidenceComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for CooperativityEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultCooperativityEvidence implements CooperativityEvidence {

    private Publication publication;
    private Collection<CvTerm> evidenceMethods;

    public DefaultCooperativityEvidence(Publication publication){
        if (publication == null){
            throw new IllegalArgumentException("The publication cannot be null in a CooperativityEvidence");
        }
        this.publication = publication;
    }

    protected void initialiseEvidenceMethods(){
        this.evidenceMethods = new ArrayList<CvTerm>();
    }

    protected void initialiseEvidenceMethodsWith(Collection<CvTerm> methods){
        if (methods == null){
            this.evidenceMethods = Collections.EMPTY_LIST;
        }
        else{
            this.evidenceMethods = methods;
        }
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        if (publication == null){
            throw new IllegalArgumentException("The publication cannot be null in a CooperativityEvidence");
        }
        this.publication = publication;
    }

    public Collection<CvTerm> getEvidenceMethods() {

        if (evidenceMethods == null){
            initialiseEvidenceMethods();
        }
        return evidenceMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof CooperativityEvidence)){
            return false;
        }

        return UnambiguousCooperativityEvidenceComparator.areEquals(this, (CooperativityEvidence) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousCooperativityEvidenceComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return publication != null ? publication.toString() : super.toString();
    }
}
