package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.publication.DefaultCuratedPublicationComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

public class DefaultCuratedExperimentComparator {

    /**
     * Use DefaultCuratedExperimentComparator to know if two experiment are equals.
     * @param experiment1
     * @param experiment2
     * @return true if the two experiment are equal
     */
    public static boolean areEquals(Experiment experiment1, Experiment experiment2){
        if (experiment1 == null && experiment2 == null){
            return true;
        }
        else if (experiment1 == null || experiment2 == null){
            return false;
        }
        else {
            // first compares publications
            Publication pub1 = experiment1.getPublication();
            Publication pub2 = experiment2.getPublication();

            if (!DefaultCuratedPublicationComparator.areEquals(pub1, pub2)){
                return false;
            }

            // then compares interaction detection method
            CvTerm detMethod1 = experiment1.getInteractionDetectionMethod();
            CvTerm detMethod2 = experiment2.getInteractionDetectionMethod();

            if (!DefaultCvTermComparator.areEquals(detMethod1, detMethod2)){
                return false;
            }

            // then compares host organisms
            Organism organism1 = experiment1.getHostOrganism();
            Organism organism2 = experiment2.getHostOrganism();

            if (!DefaultOrganismComparator.areEquals(organism1, organism2)){
                return false;
            }

            // then compares variable parameters
            Collection<VariableParameter> variableParameters1 = experiment1.getVariableParameters();
            Collection<VariableParameter> variableParameters2 = experiment2.getVariableParameters();

            // compare collections
            Iterator<VariableParameter> f1Iterator = new ArrayList<VariableParameter>(variableParameters1).iterator();
            Collection<VariableParameter> f2List = new ArrayList<VariableParameter>(variableParameters2);

            while (f1Iterator.hasNext()){
                VariableParameter f1 = f1Iterator.next();
                VariableParameter f2ToRemove = null;
                for (VariableParameter f2 : f2List){
                    if (DefaultVariableParameterComparator.areEquals(f1, f2)){
                        f2ToRemove = f2;
                        break;
                    }
                }
                if (f2ToRemove != null){
                    f2List.remove(f2ToRemove);
                    f1Iterator.remove();
                }
                else {
                    return false;
                }
            }

            if (f1Iterator.hasNext() || !f2List.isEmpty()){
                return false;
            }
            else{
                return true;
            }
        }
    }
}
