package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.organism.UnambiguousOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.publication.DefaultPublicationComparator;
import psidev.psi.mi.jami.utils.comparator.publication.UnambiguousPublicationComparator;

/**
 * Unambiguous experiments comparator
 * It will look first at the publications using a UnambiguousPublicationComparator. If the publications are the same, it will look at the
 * interaction detection methods using UnambiguousCvTermComparator. If the interaction detection methods are the same, it will look at
 * the host organisms using UnambiguousOrganismComparator.
 *
 * This comparator will ignore all the other properties of an experiment.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class UnambiguousExperimentComparator extends ExperimentComparator {

    private static UnambiguousExperimentComparator unambiguousExperimentComparator;

    /**
     * Creates a new UnambiguousExperimentComparator. It will use UnambiguousPublicationComparator to compare publications, UnambiguousCvTermComparator
     * to compare interaction detection methods and it will use UnambiguousOrganismComparator to compare host organisms.
     */
    public UnambiguousExperimentComparator() {
        super(new UnambiguousPublicationComparator(), new UnambiguousOrganismComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    /**
     * It will look first at the publications using a UnambiguousPublicationComparator. If the publications are the same, it will look at the
     * interaction detection methods using UnambiguousCvTermComparator. If the interaction detection methods are the same, it will look at
     * the host organisms using UnambiguousOrganismComparator.
     *
     * This comparator will ignore all the other properties of an experiment.
     */
    public int compare(Experiment experiment1, Experiment experiment2) {
        return super.compare(experiment1, experiment2);
    }

    @Override
    public DefaultPublicationComparator getPublicationComparator() {
        return (DefaultPublicationComparator) this.publicationComparator;
    }

    @Override
    public DefaultOrganismComparator getOrganismComparator() {
        return (DefaultOrganismComparator) this.organismComparator;
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    /**
     * Use UnambiguousExperimentComparator to know if two experiment are equals.
     * @param experiment1
     * @param experiment2
     * @return true if the two experiment are equal
     */
    public static boolean areEquals(Experiment experiment1, Experiment experiment2){
        if (unambiguousExperimentComparator == null){
            unambiguousExperimentComparator = new UnambiguousExperimentComparator();
        }

        return unambiguousExperimentComparator.compare(experiment1, experiment2) == 0;
    }
}
