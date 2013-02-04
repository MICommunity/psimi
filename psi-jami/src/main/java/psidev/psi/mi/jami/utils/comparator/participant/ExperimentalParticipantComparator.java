package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.cv.CvTermsCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterComparator;

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

public class ExperimentalParticipantComparator implements Comparator<ExperimentalParticipant> {

    protected ParticipantInteractorComparator participantComparator;
    protected CvTermsCollectionComparator cvTermCollectionComparator;
    protected OrganismComparator organismComparator;
    protected ParameterCollectionComparator parameterCollectionComparator;

    /**
     * Creates a new ExperimentalParticipantComparator
     * @param participantComparator : the participant comparator required to compare basic properties of a participant
     * @param cvTermComparator : the CvTerm comparator required to compare experimental roles, experimental preparations and identification methods
     * @param organismComparator : the organism comparator required to compare expressed in organisms
     * @param parameterComparator: ParameterComparator required for comparing participant features
     */
    public ExperimentalParticipantComparator(ParticipantInteractorComparator participantComparator,
                                             AbstractCvTermComparator cvTermComparator, OrganismComparator organismComparator,
            ParameterComparator parameterComparator){
        if (participantComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare basic participant properties. It cannot be null");
        }
        this.participantComparator = participantComparator;
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare experimental roles, identification methods and preparations. It cannot be null");
        }
        this.cvTermCollectionComparator = new CvTermsCollectionComparator(cvTermComparator);
        if (organismComparator == null){
            throw new IllegalArgumentException("The Organism comparator is required to compare expressed in organisms. It cannot be null");
        }
        this.organismComparator = organismComparator;
        if (parameterComparator == null){
            throw new IllegalArgumentException("The parameter comparator is required to compare participant parameters. It cannot be null");
        }
        this.parameterCollectionComparator = new ParameterCollectionComparator(parameterComparator);
    }

    public ParameterCollectionComparator getParameterCollectionComparator() {
        return parameterCollectionComparator;
    }

    public ParticipantInteractorComparator getParticipantComparator() {
        return participantComparator;
    }

    public CvTermsCollectionComparator getCvTermCollectionComparator() {
        return cvTermCollectionComparator;
    }

    public OrganismComparator getOrganismComparator() {
        return organismComparator;
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
    public int compare(ExperimentalParticipant experimentalParticipant1, ExperimentalParticipant experimentalParticipant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (experimentalParticipant1 == null && experimentalParticipant1 == null){
            return EQUAL;
        }
        else if (experimentalParticipant1 == null){
            return AFTER;
        }
        else if (experimentalParticipant2 == null){
            return BEFORE;
        }
        else {

            // first compares basic participant properties
            int comp = participantComparator.compare(experimentalParticipant1, experimentalParticipant2);
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

            // then compares the participant identification methods
            Collection<CvTerm> methods1 = experimentalParticipant1.getIdentificationMethods();
            Collection<CvTerm> methods2 = experimentalParticipant2.getIdentificationMethods();

            comp = cvTermCollectionComparator.compare(methods1, methods2);
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

            if (comp != 0){
                return comp;
            }

            // then compares the parameters
            Collection<Parameter> param1 = experimentalParticipant1.getParameters();
            Collection<Parameter> param2 = experimentalParticipant2.getParameters();

            return parameterCollectionComparator.compare(param1, param2);
        }
    }
}
