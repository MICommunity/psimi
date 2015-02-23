package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic Experimental participant comparator.
 *
 * It will first compares experimental roles using AbstractCvTermComparator. If both experimental roles are equals, it
 * will look at the identification methods using AbstractCvTermComparator. If both identification methods are equals, it will
 * look at the experimental preparations using AbstractCvTermComparator. If both experimental preparations are equals, it will
 * look at the expressed in organisms using OrganismComparator. If both organisms are the same, it will compare parameters using ParameterComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class ParticipantEvidenceComparator implements Comparator<ParticipantEvidence> {

    private ExperimentalParticipantPoolComparator poolComparator;

    /**
     * Creates a new ParticipantEvidenceComparator
     * @param poolComparator : the participant pool comparator required to compare basic properties of a participant and participant candidates in case of a pool
     */
    public ParticipantEvidenceComparator(ExperimentalParticipantPoolComparator poolComparator){
        if (poolComparator == null){
            throw new IllegalArgumentException("The participant pool comparator is required to compare basic participant properties and participant candidates of a pool. It cannot be null");
        }
        this.poolComparator = poolComparator;
    }

    public ExperimentalParticipantPoolComparator getParticipantPoolComparator() {
        return poolComparator;
    }

    /**
     * It will first compares experimental roles using AbstractCvTermComparator. If both experimental roles are equals, it
     * will look at the identification methods using AbstractCvTermComparator. If both identification methods are equals, it will
     * look at the experimental preparations using AbstractCvTermComparator. If both experimental preparations are equals, it will
     * look at the expressed in organisms using OrganismComparator.  If both organisms are the same, it will compare parameters using ParameterComparator
     *
     * @param experimentalParticipant1
     * @param experimentalParticipant2
     * @return
     */
    public int compare(ParticipantEvidence experimentalParticipant1, ParticipantEvidence experimentalParticipant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;
        // both are biological participants
        boolean isPool1 = experimentalParticipant1 instanceof ExperimentalParticipantPool;
        boolean isPool2 = experimentalParticipant2 instanceof ExperimentalParticipantPool;
        if (isPool1 && isPool2) {
            return this.poolComparator.compare((ExperimentalParticipantPool) experimentalParticipant1,
                    (ExperimentalParticipantPool) experimentalParticipant2);
        }
        // the biological participant is before
        else if (isPool1) {
            return BEFORE;
        } else if (isPool2) {
            return AFTER;
        } else {

            if (experimentalParticipant1 == experimentalParticipant2) {
                return EQUAL;
            } else if (experimentalParticipant1 == null) {
                return AFTER;
            } else if (experimentalParticipant2 == null) {
                return BEFORE;
            } else {
                poolComparator.getParticipantBaseComparator().setIgnoreInteractors(false);

                // first compares basic participant properties
                int comp = poolComparator.getParticipantBaseComparator().compare(experimentalParticipant1, experimentalParticipant2);
                if (comp != 0) {
                    return comp;
                }

                // first compares the experimental roles
                CvTerm expRoles1 = experimentalParticipant1.getExperimentalRole();
                CvTerm expRoles2 = experimentalParticipant2.getExperimentalRole();

                comp = poolComparator.getCvTermCollectionComparator().getObjectComparator().compare(expRoles1, expRoles2);
                if (comp != 0) {
                    return comp;
                }

                // then compares the participant identification method
                Collection<CvTerm> method1 = experimentalParticipant1.getIdentificationMethods();
                Collection<CvTerm> method2 = experimentalParticipant2.getIdentificationMethods();

                comp = poolComparator.getCvTermCollectionComparator().compare(method1, method2);
                if (comp != 0) {
                    return comp;
                }

                // then compares the participant experimental preparations
                Collection<CvTerm> prep1 = experimentalParticipant1.getExperimentalPreparations();
                Collection<CvTerm> prep2 = experimentalParticipant2.getExperimentalPreparations();

                comp = poolComparator.getCvTermCollectionComparator().compare(prep1, prep2);
                if (comp != 0) {
                    return comp;
                }

                // then compares the expressed in organisms
                Organism organism1 = experimentalParticipant1.getExpressedInOrganism();
                Organism organism2 = experimentalParticipant2.getExpressedInOrganism();

                comp = poolComparator.getOrganismComparator().compare(organism1, organism2);
                if (comp != 0) {
                    return comp;
                }

                // then compares the parameters
                Collection<Parameter> param1 = experimentalParticipant1.getParameters();
                Collection<Parameter> param2 = experimentalParticipant2.getParameters();

                comp = poolComparator.getParameterCollectionComparator().compare(param1, param2);
                if (comp != 0) {
                    return comp;
                }

                // then compares the features
                Collection<? extends FeatureEvidence> features1 = experimentalParticipant1.getFeatures();
                Collection<? extends FeatureEvidence> features2 = experimentalParticipant2.getFeatures();

                return poolComparator.getFeatureCollectionComparator().compare(features1, features2);
            }
        }
    }
}
