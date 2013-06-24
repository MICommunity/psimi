package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.experiment.DefaultCuratedExperimentComparator;
import psidev.psi.mi.jami.utils.comparator.experiment.VariableParameterValueSetComparator;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultParticipantEvidenceComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Default curated InteractionEvidenceComparator.
 *
 * It will first compare the basic interaction properties using DefaultCuratedInteractionBaseComparator.
 * It will then compares the IMEx identifiers if both IMEx ids are set. If at least one IMEx id is not set, it will compare the negative properties.
 * A negative interaction will come after a positive interaction. it will compare
 * the experiment using DefaultCuratedExperimentComparator. If the experiments are the same, it will compare the participants using DefaultParticipantEvidenceComparator. Then, it will compare the parameters using DefaultParameterComparator.
 * If the parameters are the same, it will first compare the experimental variableParameters using VariableParameterValueSetComparator and then it will compare the inferred boolean value (Inferred interactions will always come after).
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultCuratedInteractionEvidenceComparator {

    /**
     * Use DefaultCuratedInteractionEvidenceComparator to know if two experimental interactions are equals.
     * @param experimentalInteraction1
     * @param experimentalInteraction2
     * @return true if the two experimental interactions are equal
     */
    public static boolean areEquals(InteractionEvidence experimentalInteraction1, InteractionEvidence experimentalInteraction2){
        if (experimentalInteraction1 == null && experimentalInteraction2 == null){
            return true;
        }
        else if (experimentalInteraction1 == null || experimentalInteraction2 == null){
            return false;
        }
        else {
            if (!DefaultCuratedInteractionBaseComparator.areEquals(experimentalInteraction1, experimentalInteraction2)){
               return false;
            }

            // first compares the IMEx id
            String imex1 = experimentalInteraction1.getImexId();
            String imex2 = experimentalInteraction2.getImexId();

            if (imex1 != null && imex2 != null){
                return imex1.equals(imex2);
            }

            // then compares negative
            boolean isNegative1 = experimentalInteraction1.isNegative();
            boolean  isNegative2 = experimentalInteraction2.isNegative();

            if (isNegative1 != isNegative2){
                return false;
            }

            // If at least one IMEx id is not set, will compare the experiment
            Experiment exp1 = experimentalInteraction1.getExperiment();
            Experiment exp2 = experimentalInteraction2.getExperiment();

            if (!DefaultCuratedExperimentComparator.areEquals(exp1, exp2)){
                return false;
            }

            // first compares participants of an interaction
            Collection<ParticipantEvidence> participants1 = experimentalInteraction1.getParticipants();
            Collection<ParticipantEvidence> participants2 = experimentalInteraction2.getParticipants();
            if (!compareParticipants(participants1, participants2)){
                return false;
            }


            // If experiments are the same, compare parameters
            Collection<Parameter> parameters1 = experimentalInteraction1.getParameters();
            Collection<Parameter> parameters2 = experimentalInteraction2.getParameters();

            if (!ComparatorUtils.areParametersEqual(parameters1, parameters2)){
                return false;
            }

            // if parameters are the same, check experimental parameters
            Collection<VariableParameterValueSet> parameterSet1 = experimentalInteraction1.getVariableParameterValues();
            Collection<VariableParameterValueSet> parameterSet2 = experimentalInteraction2.getVariableParameterValues();

            if (parameterSet1.size() != parameterSet2.size()){
                return false;
            }
            else {
                Iterator<? extends VariableParameterValueSet> f1Iterator = new ArrayList<VariableParameterValueSet>(parameterSet1).iterator();
                Collection<? extends VariableParameterValueSet> f2List = new ArrayList<VariableParameterValueSet>(parameterSet2);

                while (f1Iterator.hasNext()){
                    VariableParameterValueSet f1 = f1Iterator.next();
                    VariableParameterValueSet f2ToRemove = null;
                    for (VariableParameterValueSet f2 : f2List){
                        if (VariableParameterValueSetComparator.areEquals(f1, f2)){
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
            }

            // check if inferred
            boolean inferred1 = experimentalInteraction1.isInferred();
            boolean inferred2 = experimentalInteraction2.isInferred();

            if (inferred1 != inferred2){
                return false;
            }
            else {
                return true;
            }
        }
    }

    private static boolean compareParticipants(Collection<ParticipantEvidence> participants1, Collection<ParticipantEvidence> participants2) {
        // compare collections
        Iterator<ParticipantEvidence> f1Iterator = new ArrayList<ParticipantEvidence>(participants1).iterator();
        Collection<ParticipantEvidence> f2List = new ArrayList<ParticipantEvidence>(participants2);

        while (f1Iterator.hasNext()){
            ParticipantEvidence f1 = f1Iterator.next();
            ParticipantEvidence f2ToRemove = null;
            for (ParticipantEvidence f2 : f2List){
                if (DefaultParticipantEvidenceComparator.areEquals(f1, f2)){
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
