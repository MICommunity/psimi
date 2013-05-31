package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.publication.DefaultCuratedPublicationComparator;

/**
 * Default curated experiment comparator.
 * It will look first at the publications using a DefaultCuratedPublicationComparator. If the publications are the same, it will look at the
 * interaction detection methods using DefaultCvTermComparator. If the interaction detection methods are the same, it will look at
 * the host organisms using DefaultOrganismComparator.
 * If the host organisms are the same, it will look at the variableParameters using DefaultVariableParameterComparator.
 *
 * This comparator will ignore all the other properties of an experiment.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultCuratedExperimentComparator extends ExperimentComparator {

    private static DefaultCuratedExperimentComparator defaultCuratedExperimentComparator;

    /**
     * Creates a new DefaultCuratedExperimentComparator. It will use DefaultCuratedPublicationComparator to compare publications, DefaultCvTermComparator
     * to compare interaction detection methods and it will use DefaultOrganismComparator to compare host organisms.
     */
    public DefaultCuratedExperimentComparator() {
        super(new DefaultCuratedPublicationComparator(), new DefaultOrganismComparator());
    }

    @Override
    /**
     * It will look first at the publications using a DefaultCuratedPublicationComparator. If the publications are the same, it will look at the
     * interaction detection methods using DefaultCvTermComparator. If the interaction detection methods are the same, it will look at
     * the host organisms using DefaultOrganismComparator.
     * If the host organisms are the same, it will look at the variableParameters using DefaultVariableParameterComparator.
     *
     * This comparator will ignore all the other properties of an experiment.
     */
    public int compare(Experiment experiment1, Experiment experiment2) {
        return super.compare(experiment1, experiment2);
    }

    @Override
    public DefaultCuratedPublicationComparator getPublicationComparator() {
        return (DefaultCuratedPublicationComparator) this.publicationComparator;
    }

    @Override
    public DefaultOrganismComparator getOrganismComparator() {
        return (DefaultOrganismComparator) this.organismComparator;
    }

    /**
     * Use DefaultCuratedExperimentComparator to know if two experiment are equals.
     * @param experiment1
     * @param experiment2
     * @return true if the two experiment are equal
     */
    public static boolean areEquals(Experiment experiment1, Experiment experiment2){
        if (defaultCuratedExperimentComparator == null){
            defaultCuratedExperimentComparator = new DefaultCuratedExperimentComparator();
        }

        return defaultCuratedExperimentComparator.compare(experiment1, experiment2) == 0;
    }
}
