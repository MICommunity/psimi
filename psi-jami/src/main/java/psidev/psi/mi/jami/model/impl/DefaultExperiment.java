package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.experiment.UnambiguousExperimentComparator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation for Experiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultExperiment implements Experiment {

    protected Publication publication;
    protected String shortLabel;
    protected Collection<Xref> xrefs;
    protected Collection<Annotation> annotations;
    protected CvTerm interactionDetectionMethod;
    protected Organism hostOrganism;
    protected Collection<InteractionEvidence> interactions;

    public DefaultExperiment(Publication publication, CvTerm interactionDetectionMethod){

        this.publication = publication;
        if (interactionDetectionMethod == null){
            throw new IllegalArgumentException("The interaction detection method is required and cannot be null");
        }
        this.interactionDetectionMethod = interactionDetectionMethod;

        initializeAnnotations();
        initializeXrefs();
        initializeInteractions();
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

    protected void initializeXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initializeAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initializeInteractions(){
        this.interactions = new ArrayList<InteractionEvidence>();
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
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
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

    public Collection<InteractionEvidence> getInteractions() {
        return this.interactions;
    }

    public boolean addInteractionEvidence(InteractionEvidence evidence) {
        if (evidence == null){
            return false;
        }

        if (interactions.add(evidence)){
            evidence.setExperiment(this);
            return true;
        }
        return false;
    }

    public boolean removeInteractionEvidence(InteractionEvidence evidence) {
        if (evidence == null){
            return false;
        }

        if (interactions.remove(evidence)){
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
