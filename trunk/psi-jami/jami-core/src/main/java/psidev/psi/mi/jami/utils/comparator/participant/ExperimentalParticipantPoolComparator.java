package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.cv.CvTermsCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureEvidenceComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic Experimental participant pool comparator.
 *
 * It will first compares experimental roles using AbstractCvTermComparator. If both experimental roles are equals, it
 * will look at the identification methods using AbstractCvTermComparator. If both identification methods are equals, it will
 * look at the experimental preparations using AbstractCvTermComparator. If both experimental preparations are equals, it will
 * look at the expressed in organisms using OrganismComparator. If both organisms are the same, it will compare parameters using ParameterComparator
 *
 * All participant candidates will be compared with ExperimentalEntityComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class ExperimentalParticipantPoolComparator implements Comparator<ExperimentalParticipantPool> {

    private ParticipantBaseComparator participantBaseComparator;
    private CollectionComparator<CvTerm> cvTermCollectionComparator;
    private OrganismComparator organismComparator;
    private CollectionComparator<Parameter> parameterCollectionComparator;
    private CollectionComparator<FeatureEvidence> featureCollectionComparator;
    private CollectionComparator<ExperimentalEntity> experimentalEntityCollectionComparator;
    private Comparator<ExperimentalEntity> experimentalEntityComparator;

    /**
     * Creates a new ExperimentalParticipantPoolComparator
     * @param participantBaseComparator : the participant comparator required to compare basic properties of a participant
     * @param organismComparator : the organism comparator required to compare expressed in organisms
     * @param parameterComparator: ParameterComparator required for comparing participant features
     * @param entityComparator: comparator for participant candidates
     */
    public ExperimentalParticipantPoolComparator(ParticipantBaseComparator participantBaseComparator,
                                                 OrganismComparator organismComparator,
                                                 ParameterComparator parameterComparator,
                                                 FeatureEvidenceComparator featureComparator,
                                                 ExperimentalEntityComparator entityComparator){
        if (participantBaseComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare basic participant properties. It cannot be null");
        }
        this.participantBaseComparator = participantBaseComparator;
        this.cvTermCollectionComparator = new CvTermsCollectionComparator(this.participantBaseComparator.getCvTermComparator());
        if (organismComparator == null){
            throw new IllegalArgumentException("The Organism comparator is required to compare expressed in organisms. It cannot be null");
        }
        this.organismComparator = organismComparator;
        if (parameterComparator == null){
            throw new IllegalArgumentException("The parameter comparator is required to compare participant parameters. It cannot be null");
        }
        this.parameterCollectionComparator = new ParameterCollectionComparator(parameterComparator);
        if (featureComparator == null){
            throw new IllegalArgumentException("The feature comparator is required to compare features. It cannot be null");
        }
        this.featureCollectionComparator = new FeatureCollectionComparator(featureComparator);
        if (entityComparator == null){
            throw new IllegalArgumentException("The experimental entity comparator is required to compare participant candidates. It cannot be null");
        }
        this.experimentalEntityComparator = entityComparator;
        this.experimentalEntityCollectionComparator = new CollectionComparator<ExperimentalEntity>(this.experimentalEntityComparator);
    }

    /**
     * Creates a new ExperimentalParticipantPoolComparator
     * @param participantBaseComparator : the participant comparator required to compare basic properties of a participant
     * @param organismComparator : the organism comparator required to compare expressed in organisms
     * @param parameterComparator: ParameterComparator required for comparing participant features
     * @param entityComparator: comparator for participant candidates
     * @param cvTermComparator: comparator for collection of cvs
     */
    public ExperimentalParticipantPoolComparator(ParticipantBaseComparator participantBaseComparator,
                                                 OrganismComparator organismComparator,
                                                 CollectionComparator<Parameter> parameterComparator,
                                                 CollectionComparator<FeatureEvidence> featureComparator,
                                                 CollectionComparator<ExperimentalEntity> entityComparator,
                                                 CollectionComparator<CvTerm> cvTermComparator){
        if (participantBaseComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare basic participant properties. It cannot be null");
        }
        this.participantBaseComparator = participantBaseComparator;
        this.cvTermCollectionComparator = cvTermComparator != null ? cvTermComparator : new CvTermsCollectionComparator(this.participantBaseComparator.getCvTermComparator());
        if (organismComparator == null){
            throw new IllegalArgumentException("The Organism comparator is required to compare expressed in organisms. It cannot be null");
        }
        this.organismComparator = organismComparator;
        if (parameterComparator == null){
            throw new IllegalArgumentException("The parameter comparator is required to compare participant parameters. It cannot be null");
        }
        this.parameterCollectionComparator = parameterComparator;
        if (featureComparator == null){
            throw new IllegalArgumentException("The feature comparator is required to compare features. It cannot be null");
        }
        this.featureCollectionComparator = featureComparator;
        if (entityComparator == null){
            throw new IllegalArgumentException("The experimental entity comparator is required to compare participant candidates. It cannot be null");
        }
        this.experimentalEntityCollectionComparator = entityComparator;
        this.experimentalEntityComparator = this.experimentalEntityCollectionComparator.getObjectComparator();
    }

    public CollectionComparator<Parameter> getParameterCollectionComparator() {
        return parameterCollectionComparator;
    }

    public ParticipantBaseComparator getParticipantBaseComparator() {
        return participantBaseComparator;
    }

    public CollectionComparator<CvTerm> getCvTermCollectionComparator() {
        return cvTermCollectionComparator;
    }

    public OrganismComparator getOrganismComparator() {
        return organismComparator;
    }

    public CollectionComparator<FeatureEvidence> getFeatureCollectionComparator() {
        return featureCollectionComparator;
    }

    public Comparator<ExperimentalEntity> getExperimentalEntityComparator() {
        return experimentalEntityComparator;
    }

    /**
     * It will first compares experimental roles using AbstractCvTermComparator. If both experimental roles are equals, it
     * will look at the identification methods using AbstractCvTermComparator. If both identification methods are equals, it will
     * look at the experimental preparations using AbstractCvTermComparator. If both experimental preparations are equals, it will
     * look at the expressed in organisms using OrganismComparator.  If both organisms are the same, it will compare parameters using ParameterComparator
     *
     * All participant candidates will be compared with ExperimentalEntityComparator
     * @param experimentalParticipant1
     * @param experimentalParticipant2
     * @return
     */
    public int compare(ExperimentalParticipantPool experimentalParticipant1, ExperimentalParticipantPool experimentalParticipant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (experimentalParticipant1 == experimentalParticipant2){
            return EQUAL;
        }
        else if (experimentalParticipant1 == null){
            return AFTER;
        }
        else if (experimentalParticipant2 == null){
            return BEFORE;
        }
        else {
            participantBaseComparator.setIgnoreInteractors(false);

            // first compares basic participant properties
            int comp = participantBaseComparator.compare(experimentalParticipant1, experimentalParticipant2);
            if (comp != 0){
               return comp;
            }

            // first compares the experimental roles
            CvTerm expRoles1 = experimentalParticipant1.getExperimentalRole();
            CvTerm expRoles2 = experimentalParticipant2.getExperimentalRole();

            comp = cvTermCollectionComparator.getObjectComparator().compare(expRoles1, expRoles2);
            if (comp != 0){
                return comp;
            }

            // then compares the participant identification method
            Collection<CvTerm> method1 = experimentalParticipant1.getIdentificationMethods();
            Collection<CvTerm> method2 = experimentalParticipant2.getIdentificationMethods();

            comp = cvTermCollectionComparator.compare(method1, method2);
            if (comp != 0){
                return comp;
            }

            // then compares the participant experimental preparations
            Collection<CvTerm> prep1 = experimentalParticipant1.getExperimentalPreparations();
            Collection<CvTerm> prep2 = experimentalParticipant2.getExperimentalPreparations();

            comp = cvTermCollectionComparator.compare(prep1, prep2);
            if (comp != 0){
                return comp;
            }

            // then compares the expressed in organisms
            Organism organism1 = experimentalParticipant1.getExpressedInOrganism();
            Organism organism2 = experimentalParticipant2.getExpressedInOrganism();

            comp = organismComparator.compare(organism1, organism2);
            if (comp != 0){
                return comp;
            }

            // then compares the parameters
            Collection<Parameter> param1 = experimentalParticipant1.getParameters();
            Collection<Parameter> param2 = experimentalParticipant2.getParameters();

            comp = parameterCollectionComparator.compare(param1, param2);
            if (comp != 0){
               return comp;
            }

            // then compares the features
            Collection<? extends FeatureEvidence> features1 = experimentalParticipant1.getFeatures();
            Collection<? extends FeatureEvidence> features2 = experimentalParticipant2.getFeatures();

            comp = featureCollectionComparator.compare(features1, features2);
            if (comp != 0){
                return comp;
            }

            return this.experimentalEntityCollectionComparator.compare(experimentalParticipant1, experimentalParticipant2);
        }
    }
}
