package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.publication.DefaultPublicationComparator;

/**
 * Default experiment comparator.
 * It will look first at the publications using a DefaultPublicationComparator. If the publications are the same, it will look at the
 * interaction detection methods using DefaultCvTermComparator. If the interaction detection methods are the same, it will look at
 * the host organisms using DefaultOrganismComparator.
 *
 * This comparator will ignore all the other properties of an experiment.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class DefaultExperimentComparator extends ExperimentComparator {

    private static DefaultExperimentComparator defaultExperimentComparator;

    /**
     * Creates a new DefaultExperimentComparator. It will use DefaultPublicationComparator to compare publications, DefaultCvTermComparator
     * to compare interaction detection methods and it will use DefaultOrganismComparator to compare host organisms.
     */
    public DefaultExperimentComparator() {
        super(new DefaultPublicationComparator(), new DefaultOrganismComparator(), new DefaultCvTermComparator());
    }

    @Override
    /**
     * It will look first at the publications using a DefaultPublicationComparator. If the publications are the same, it will look at the
     * interaction detection methods using DefaultCvTermComparator. If the interaction detection methods are the same, it will look at
     * the host organisms using DefaultOrganismComparator.
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
     * Use DefaultExperimentComparator to know if two experiment are equals.
     * @param experiment1
     * @param experiment2
     * @return true if the two experiment are equal
     */
    public static boolean areEquals(Experiment experiment1, Experiment experiment2){
        if (defaultExperimentComparator == null){
            defaultExperimentComparator = new DefaultExperimentComparator();
        }

        return defaultExperimentComparator.compare(experiment1, experiment2) == 0;
    }
}
