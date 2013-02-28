package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousExperimentComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for Experiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultExperiment implements Experiment {

    private Publication publication;
    private String shortLabel;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private CvTerm interactionDetectionMethod;
    private Organism hostOrganism;
    private Collection<InteractionEvidence> interactions;

    public DefaultExperiment(Publication publication, CvTerm interactionDetectionMethod){

        this.publication = publication;
        if (interactionDetectionMethod == null){
            throw new IllegalArgumentException("The interaction detection method is required and cannot be null");
        }
        this.interactionDetectionMethod = interactionDetectionMethod;
    }

    public DefaultExperiment(String shortLabel, Publication publication, CvTerm interactionDetectionMethod){
        this(publication, interactionDetectionMethod);
        this.shortLabel = shortLabel;
    }

    public DefaultExperiment(Publication publication, CvTerm interactionDetectionMethod, Organism organism){
        this(publication, interactionDetectionMethod);
        this.hostOrganism = organism;
    }

    public DefaultExperiment(String shortLabel, Publication publication, CvTerm interactionDetectionMethod, Organism organism){
        this(shortLabel, publication, interactionDetectionMethod);
        this.hostOrganism = organism;
    }

    protected void initialiseXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseInteractions(){
        this.interactions = new ArrayList<InteractionEvidence>();
    }

    protected void initialiseXrefsWith(Collection<Xref> xrefs){
        if (xrefs == null){
            this.xrefs = Collections.EMPTY_LIST;
        }
        else{
            this.xrefs = xrefs;
        }
    }

    protected void initialiseAnnotationsWith(Collection<Annotation> annotations){
        if (annotations == null){
            this.annotations = Collections.EMPTY_LIST;
        }
        else{
            this.annotations = annotations;
        }
    }

    protected void initialiseInteractionsWith(Collection<InteractionEvidence> interactionEvidences){
        if (interactionEvidences == null){
            this.interactions = Collections.EMPTY_LIST;
        }
        else{
            this.interactions = interactionEvidences;
        }
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public void setPublicationAndAddExperiment(Publication publication) {
        this.publication = publication;

        if (publication != null){
            this.publication.getExperiments().add(this);
        }
    }

    public String getShortLabel() {
        return this.shortLabel;
    }

    public void setShortLabel(String name) {
        this.shortLabel = name;
    }

    public Collection<Xref> getXrefs() {
        if (xrefs == null){
            initialiseXrefs();
        }
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
           initialiseAnnotations();
        }
        return this.annotations;
    }

    public CvTerm getInteractionDetectionMethod() {
        return this.interactionDetectionMethod;
    }

    public void setInteractionDetectionMethod(CvTerm term) {
        if (term == null){
            throw new IllegalArgumentException("The interaction detection method cannot be null");
        }
        this.interactionDetectionMethod = term;
    }

    public Organism getHostOrganism() {
        return this.hostOrganism;
    }

    public void setHostOrganism(Organism organism) {
        this.hostOrganism = organism;
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        if (interactions == null){
            initialiseInteractions();
        }
        return this.interactions;
    }

    public boolean addInteractionEvidence(InteractionEvidence evidence) {
        if (evidence == null){
            return false;
        }

        if (getInteractionEvidences().add(evidence)){
            evidence.setExperiment(this);
            return true;
        }
        return false;
    }

    public boolean removeInteractionEvidence(InteractionEvidence evidence) {
        if (evidence == null){
            return false;
        }

        if (getInteractionEvidences().remove(evidence)){
            evidence.setExperiment(null);
            return true;
        }
        return false;
    }

    public boolean addAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        if (evidences == null){
            return false;
        }

        boolean added = false;
        for (InteractionEvidence ev : evidences){
            if (addInteractionEvidence(ev)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        if (evidences == null){
            return false;
        }

        boolean removed = false;
        for (InteractionEvidence ev : evidences){
            if (removeInteractionEvidence(ev)){
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Experiment)){
            return false;
        }

        return UnambiguousExperimentComparator.areEquals(this, (Experiment) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousExperimentComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return publication.toString() + "( " + interactionDetectionMethod.toString() + (hostOrganism != null ? ", " + hostOrganism.toString():"") + " )";
    }
}
