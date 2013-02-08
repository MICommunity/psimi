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

    private Publication publication;
    private String shortLabel;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private CvTerm interactionDetectionMethod;
    private Organism horstOrganism;
    private Collection<ExperimentalInteraction> interactions;

    public DefaultExperiment(Publication publication, CvTerm interactionDetectionMethod){
         if (publication == null){
             throw new IllegalArgumentException("The publication is required and cannot be null");
         }
        this.publication = publication;
        if (interactionDetectionMethod == null){
            throw new IllegalArgumentException("The interaction detection method is required and cannot be null");
        }
        this.interactionDetectionMethod = interactionDetectionMethod;

        this.xrefs = new ArrayList<Xref>();
        this.annotations = new ArrayList<Annotation>();
        this.interactions = new ArrayList<ExperimentalInteraction>();
    }

    public DefaultExperiment(String shortLabel, Publication publication, CvTerm interactionDetectionMethod){
        this(publication, interactionDetectionMethod);
        this.shortLabel = shortLabel;
    }

    public DefaultExperiment(Publication publication, CvTerm interactionDetectionMethod, Organism organism){
        this(publication, interactionDetectionMethod);
        this.horstOrganism = organism;
    }

    public DefaultExperiment(String shortLabel, Publication publication, CvTerm interactionDetectionMethod, Organism organism){
        this(shortLabel, publication, interactionDetectionMethod);
        this.horstOrganism = organism;
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        if (publication == null){
            throw new IllegalArgumentException("The publication cannot be null");
        }
        this.publication = publication;
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
        return this.horstOrganism;
    }

    public void setHostOrganism(Organism organism) {
        this.horstOrganism = organism;
    }

    public Collection<ExperimentalInteraction> getInteractions() {
        return this.interactions;
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
        return publication.toString() + "( " + interactionDetectionMethod.toString() + (horstOrganism != null ? ", " + horstOrganism.toString():"") + " )";
    }
}
