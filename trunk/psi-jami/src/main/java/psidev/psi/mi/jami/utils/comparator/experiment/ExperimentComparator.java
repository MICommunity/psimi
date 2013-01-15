package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismComparator;

import java.util.Comparator;

/**
 * Basic experiment comparator.
 * It will look first at the publications using a Comparator<Publication>. If the publications are the same, it will look at the
 * interaction detection methods using AbstractCvTermComparator. If the interaction detection methods are the same, it will look at
 * the host organisms using OrganismComparator.
 *
 * This comparator will ignore all the other properties of an experiment.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class ExperimentComparator implements Comparator<Experiment>{

    protected Comparator<Publication> publicationComparator;
    protected OrganismComparator organismComparator;
    protected AbstractCvTermComparator cvTermComparator;

    /**
     * Creates a new ExperimentComparator. It needs a Comparator<Publication> to compare publications, a OrganismComparator to compare host organisms
     * and a AbstractCvTermComparator to compare interaction detection methods.
     * @param publicationComparator : comparator for the publication which is required
     * @param organismComparator : comparator for the host organism which is required
     * @param cvTermComparator : cv term comparator for the interaction detection method which is required
     */
    public ExperimentComparator(Comparator<Publication> publicationComparator, OrganismComparator organismComparator, AbstractCvTermComparator cvTermComparator){
        if (publicationComparator == null){
            throw new IllegalArgumentException("The publication comparator is required to compare the publications where the experiments have been published. It cannot be null");
        }
        this.publicationComparator = publicationComparator;
        if (organismComparator == null){
            throw new IllegalArgumentException("The organism comparator is required to compare the host organisms where the experiments took place. It cannot be null");
        }
        this.organismComparator = organismComparator;
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare interaction detection methods. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
    }

    /**
     * It will look first at the publications using a PublicationComparator. If the publications are the same, it will look at the
     * interaction detection methods using AbstractCvTermComparator. If the interaction detection methods are the same, it will look at
     * the host organisms using OrganismComparator.
     *
     * This comparator will ignore all the other properties of an experiment.
     * @param experiment1
     * @param experiment2
     * @return
     */
    public int compare(Experiment experiment1, Experiment experiment2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (experiment1 == null && experiment2 == null){
            return EQUAL;
        }
        else if (experiment1 == null){
            return AFTER;
        }
        else if (experiment2 == null){
            return BEFORE;
        }
        else {
            // first compares publications
            Publication pub1 = experiment1.getPublication();
            Publication pub2 = experiment2.getPublication();

            int comp = publicationComparator.compare(pub1, pub2);
            if (comp != 0){
               return comp;
            }

            // then compares interaction detection method
            CvTerm detMethod1 = experiment1.getInteractionDetectionMethod();
            CvTerm detMethod2 = experiment2.getInteractionDetectionMethod();

            comp = cvTermComparator.compare(detMethod1, detMethod2);
            if (comp != 0){
                return comp;
            }

            // then compares host organisms
            Organism organism1 = experiment1.getHostOrganism();
            Organism organism2 = experiment2.getHostOrganism();

            return organismComparator.compare(organism1, organism2);
        }
    }

    public Comparator<Publication> getPublicationComparator() {
        return publicationComparator;
    }

    public OrganismComparator getOrganismComparator() {
        return organismComparator;
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }
}
