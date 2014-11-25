package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.UnambiguousOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.publication.UnambiguousPublicationComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous experiments comparator
 * It will look first at the publications using a UnambiguousPublicationComparator. If the publications are the same, it will look at the
 * interaction detection methods using UnambiguousCvTermComparator. If the interaction detection methods are the same, it will look at
 * the host organisms using UnambiguousOrganismComparator.
 * If the host organisms are the same, it will look at the variableParameters using UnambiguousVariableParameterComparator.
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
        super(new UnambiguousPublicationComparator(), new UnambiguousOrganismComparator());
    }

    @Override
    /**
     * It will look first at the publications using a UnambiguousPublicationComparator. If the publications are the same, it will look at the
     * interaction detection methods using UnambiguousCvTermComparator. If the interaction detection methods are the same, it will look at
     * the host organisms using UnambiguousOrganismComparator.
     * If the host organisms are the same, it will look at the variableParameters using UnambiguousVariableParameterComparator.
     *
     * This comparator will ignore all the other properties of an experiment.
     */
    public int compare(Experiment experiment1, Experiment experiment2) {
        return super.compare(experiment1, experiment2);
    }

    @Override
    public UnambiguousPublicationComparator getPublicationComparator() {
        return (UnambiguousPublicationComparator) super.getPublicationComparator();
    }

    @Override
    public UnambiguousOrganismComparator getOrganismComparator() {
        return (UnambiguousOrganismComparator) super.getOrganismComparator();
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) super.getCvTermComparator();
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

    /**
     *
     * @param exp
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Experiment exp){
        if (unambiguousExperimentComparator == null){
            unambiguousExperimentComparator = new UnambiguousExperimentComparator();
        }

        if (exp == null){
            return 0;
        }

        int hashcode = 31;
        Publication pub = exp.getPublication();
        hashcode = 31*hashcode + UnambiguousPublicationComparator.hashCode(pub);

        CvTerm detMethod = exp.getInteractionDetectionMethod();
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(detMethod);

        Organism organism = exp.getHostOrganism();
        hashcode = 31*hashcode + UnambiguousOrganismComparator.hashCode(organism);

        List<VariableParameter> list1 = new ArrayList<VariableParameter>(exp.getVariableParameters());
        Collections.sort(list1, unambiguousExperimentComparator.getVariableParameterCollectionComparator().getObjectComparator());
        for (VariableParameter param : list1){
            hashcode = 31*hashcode + UnambiguousVariableParameterComparator.hashCode(param);
        }

        return hashcode;
    }
}
