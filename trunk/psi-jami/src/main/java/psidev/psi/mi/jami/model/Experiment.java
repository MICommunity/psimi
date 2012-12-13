package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Set;

/**
 * Experiment in which some interactions has been determined.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Experiment {

    /**
     * The publication where the experiment has been described.
     * It cannot be null.
     * @return the publication
     */
    public Publication getPublication();

    /**
     * Set the publication where the experiment has been described.
     * @param publication : the publication
     * @throws IllegalArgumentException if the publication is null
     */
    public void setPublication(Publication publication);

    /**
     * The short label of experiment.
     * It can be null.
     * Ex: brehme-2009-1
     * @return the shortName
     */
    public String getShortLabel();

    /**
     * Set the short label of an experiment
     * @param name : short name
     */
    public void setShortLabel(String name);

    /**
     * Set of cross references for an experiment which can give more information about the experiment.
     * It cannot be null and if the experiment does not have any xrefs, the method should return an empty set.
     * Ex: PRIDE experiment/project xrefs
     * @return the xrefs
     */
    public Set<Xref> getXrefs();

    /**
     * Set of annotations for an experiment.
     * It cannot be null. If the experiment does not have any annotations, the method should return an empty set.
     * Ex: data-processing, comments, cautions, confidence-mapping annotations
     * @return the annotations
     */
    public Set<Annotation> getAnnotations();

    /**
     * The experimental method to determine the interaction. It is a controlled vocabulary term and cannot not be null.
     * Ex: pull down, coip, ...
     * @return the interaction detection method
     */
    public CvTerm getInteractionDetectionMethod();

    /**
     * Set the interaction detection method for this experiment
     * @param term : the detction method
     * @throws IllegalArgumentException if the interaction detection method is null
     */
    public void setInteractionDetectionMethod(CvTerm term);

    /**
     * The host organism where the interaction took place in this experiment.
     * It can be null.
     * Ex: in vitro, human-hela cells
     * @return the host organism
     */
    public Organism getHostOrganism();

    /**
     * Sets the host organism of an experiment
     * @param organism : host organism
     */
    public void setHostOrganism(Organism organism);

    /**
     * The interactions determined in this experiment.
     * The collection cannot be null. If the experiment did not show any interactions, the method should return an empty collection
     * @return the interactions
     */
    public Collection<ExperimentalInteraction> getInteractions();
}
