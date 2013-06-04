package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Factory for participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class ParticipantUtils {

     public static ParticipantEvidence createUnknownBasicExperimentalParticipant(){
         return new DefaultParticipantEvidence(InteractionUtils.createEmptyBasicExperimentalInteraction(), InteractorUtils.createUnknownBasicInteractor(), new DefaultCvTerm("unspecified method"));
     }

    /**
     * Method to know if a participant evidence has a putative self experimental or biological role
     * @param p
     * @return
     */
    public static boolean isPutativeSelfParticipant(ParticipantEvidence p){
        if (p == null){
            return false;
        }

        CvTerm experimentalRole = p.getExperimentalRole();
        CvTerm biologicalRole = p.getBiologicalRole();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getPutativeSelf(), experimentalRole)
                || DefaultCvTermComparator.areEquals(CvTermUtils.getPutativeSelf(), biologicalRole) ;
    }

    /**
     * Method to know if a participant evidence has a self experimental or biological role
     * @param p
     * @return
     */
    public static boolean isSelfParticipant(ParticipantEvidence p){
        if (p == null){
            return false;
        }

        CvTerm experimentalRole = p.getExperimentalRole();
        CvTerm biologicalRole = p.getBiologicalRole();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getSelf(), experimentalRole)
                || DefaultCvTermComparator.areEquals(CvTermUtils.getSelf(), biologicalRole) ;
    }

    /**
     * Method to know if a modelled participant has a putative self biological role
     * @param p
     * @return
     */
    public static boolean isPutativeSelfParticipant(ModelledParticipant p){
        if (p == null){
            return false;
        }

        CvTerm biologicalRole = p.getBiologicalRole();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getPutativeSelf(), biologicalRole) ;
    }

    /**
     * Method to know if a modelled participant has a self biological role
     * @param p
     * @return
     */
    public static boolean isSelfParticipant(ModelledParticipant p){
        if (p == null){
            return false;
        }

        CvTerm biologicalRole = p.getBiologicalRole();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getSelf(), biologicalRole) ;
    }

    /**
     * Method to know if a participant has a putative self biological role.
     * If it is a ParticipantEvidence, also look at the experimental role
     * @param p
     * @return
     */
    public static boolean isPutativeSelfParticipant(Participant p){
        if (p == null){
            return false;
        }

        CvTerm biologicalRole = p.getBiologicalRole();

        if (DefaultCvTermComparator.areEquals(CvTermUtils.getPutativeSelf(), biologicalRole)){
            return true;
        }

        if (p instanceof ParticipantEvidence){
            CvTerm expRole = ((ParticipantEvidence)p).getExperimentalRole();
            return DefaultCvTermComparator.areEquals(CvTermUtils.getPutativeSelf(), expRole) ;
        }

        return false;
    }

    /**
     * Method to know if a participant has a self biological role
     * If it is a ParticipantEvidence, also look at the experimental role
     * @param p
     * @return
     */
    public static boolean isSelfParticipant(Participant p){
        if (p == null){
            return false;
        }

        CvTerm biologicalRole = p.getBiologicalRole();

        if (DefaultCvTermComparator.areEquals(CvTermUtils.getSelf(), biologicalRole)){
            return true;
        }

        if (p instanceof ParticipantEvidence){
            CvTerm expRole = ((ParticipantEvidence)p).getExperimentalRole();
            return DefaultCvTermComparator.areEquals(CvTermUtils.getSelf(), expRole) ;
        }

        return false;
    }
}
