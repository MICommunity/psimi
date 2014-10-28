package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultProtein;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tester for ParticipantUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class ParticipantUtilsTest {

    @Test
    public void test_is_putative_self_participant_evidence(){

        ParticipantEvidence participant_exp_role_putative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createPutativeSelf(), null);
        ParticipantEvidence participant_exp_role_self = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createSelf(), null);
        ParticipantEvidence participant_bio_role_putative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createPutativeSelf(), CvTermUtils.createUnspecifiedRole(), null);
        ParticipantEvidence participant_bio_role_self = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createSelf(), CvTermUtils.createUnspecifiedRole(), null);
        ParticipantEvidence participant_different_exp_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI), null);
        ParticipantEvidence participant_different_bio_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI), CvTermUtils.createUnspecifiedRole(), null);

        Assert.assertTrue(ParticipantUtils.isPutativeSelfParticipantEvidence(participant_exp_role_putative));
        Assert.assertFalse(ParticipantUtils.isPutativeSelfParticipantEvidence(participant_exp_role_self));
        Assert.assertTrue(ParticipantUtils.isPutativeSelfParticipantEvidence(participant_bio_role_putative));
        Assert.assertFalse(ParticipantUtils.isPutativeSelfParticipantEvidence(participant_bio_role_self));
        Assert.assertFalse(ParticipantUtils.isPutativeSelfParticipantEvidence(participant_different_exp_role));
        Assert.assertFalse(ParticipantUtils.isPutativeSelfParticipantEvidence(participant_different_bio_role));
    }

    @Test
    public void test_is_self_participant_evidence(){

        ParticipantEvidence participant_exp_role_putative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createPutativeSelf(), null);
        ParticipantEvidence participant_exp_role_self = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createSelf(), null);
        ParticipantEvidence participant_bio_role_putative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createPutativeSelf(), CvTermUtils.createUnspecifiedRole(), null);
        ParticipantEvidence participant_bio_role_self = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createSelf(), CvTermUtils.createUnspecifiedRole(), null);
        ParticipantEvidence participant_different_exp_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI), null);
        ParticipantEvidence participant_different_bio_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI), CvTermUtils.createUnspecifiedRole(), null);

        Assert.assertFalse(ParticipantUtils.isSelfParticipantEvidence(participant_exp_role_putative));
        Assert.assertTrue(ParticipantUtils.isSelfParticipantEvidence(participant_exp_role_self));
        Assert.assertFalse(ParticipantUtils.isSelfParticipantEvidence(participant_bio_role_putative));
        Assert.assertTrue(ParticipantUtils.isSelfParticipantEvidence(participant_bio_role_self));
        Assert.assertFalse(ParticipantUtils.isSelfParticipantEvidence(participant_different_exp_role));
        Assert.assertFalse(ParticipantUtils.isSelfParticipantEvidence(participant_different_bio_role));
    }

    @Test
    public void test_is_putative_self_participant_ignore_participant_evidence(){

        ParticipantEvidence participant_exp_role_putative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createPutativeSelf(), null);
        ParticipantEvidence participant_exp_role_self = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createSelf(), null);
        ModelledParticipant participant_bio_role_putative = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createPutativeSelf());
        ModelledParticipant participant_bio_role_self = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createSelf());
        ParticipantEvidence participant_different_exp_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI), null);
        ParticipantEvidence participant_different_bio_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI), CvTermUtils.createUnspecifiedRole(), null);

        Assert.assertFalse(ParticipantUtils.isPutativeSelfParticipant(participant_exp_role_putative, false));
        Assert.assertFalse(ParticipantUtils.isPutativeSelfParticipant(participant_exp_role_self, false));
        Assert.assertTrue(ParticipantUtils.isPutativeSelfParticipant(participant_bio_role_putative, false));
        Assert.assertFalse(ParticipantUtils.isPutativeSelfParticipant(participant_bio_role_self, false));
        Assert.assertFalse(ParticipantUtils.isPutativeSelfParticipant(participant_different_exp_role, false));
        Assert.assertFalse(ParticipantUtils.isPutativeSelfParticipant(participant_different_bio_role, false));
    }

    @Test
    public void test_is_self_participant_ignore_participant_evidence(){

        ParticipantEvidence participant_exp_role_putative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createPutativeSelf(), null);
        ParticipantEvidence participant_exp_role_self = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createSelf(), null);
        ModelledParticipant participant_bio_role_putative = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createPutativeSelf());
        ModelledParticipant participant_bio_role_self = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createSelf());
        ParticipantEvidence participant_different_exp_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createUnspecifiedRole(), CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI), null);
        ParticipantEvidence participant_different_bio_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(),
                CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI), CvTermUtils.createUnspecifiedRole(), null);

        Assert.assertFalse(ParticipantUtils.isSelfParticipant(participant_exp_role_putative, true));
        Assert.assertTrue(ParticipantUtils.isSelfParticipant(participant_exp_role_self, true));
        Assert.assertFalse(ParticipantUtils.isSelfParticipant(participant_bio_role_putative, true));
        Assert.assertTrue(ParticipantUtils.isSelfParticipant(participant_bio_role_self, true));
        Assert.assertFalse(ParticipantUtils.isSelfParticipant(participant_different_exp_role, true));
        Assert.assertFalse(ParticipantUtils.isSelfParticipant(participant_different_bio_role, true));
    }

    @Test
    public void test_identify_biological_role_participant_null(){

        Participant p = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI));

        Assert.assertFalse(ParticipantUtils.doesParticipantHaveBiologicalRole(null, Participant.ENZYME_ROLE_MI, Participant.ENZYME_ROLE));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveBiologicalRole(p, null, null));
    }

    @Test
    public void test_identify_biological_role_participant(){

        Participant p = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI));
        Participant p2_name_only = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, null));
        Participant p3_identifier_only = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm("test name", Participant.ENZYME_ROLE_MI));
        Participant p4_different_role = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.ELECTRON_DONOR_ROLE, Participant.ELECTRON_DONOR_ROLE_MI));

        Assert.assertTrue(ParticipantUtils.doesParticipantHaveBiologicalRole(p, Participant.ENZYME_ROLE_MI, Participant.ENZYME_ROLE));
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveBiologicalRole(p2_name_only, Participant.ENZYME_ROLE_MI, Participant.ENZYME_ROLE));
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveBiologicalRole(p3_identifier_only, Participant.ENZYME_ROLE_MI, Participant.ENZYME_ROLE));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveBiologicalRole(p4_different_role, Participant.ENZYME_ROLE_MI, Participant.ENZYME_ROLE));

        // check name only
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveBiologicalRole(p, null, Participant.ENZYME_ROLE));
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveBiologicalRole(p2_name_only, null, Participant.ENZYME_ROLE));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveBiologicalRole(p3_identifier_only, null, Participant.ENZYME_ROLE));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveBiologicalRole(p4_different_role, null, Participant.ENZYME_ROLE));

        // check identifier only
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveBiologicalRole(p, Participant.ENZYME_ROLE_MI, null));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveBiologicalRole(p2_name_only, Participant.ENZYME_ROLE_MI, null));
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveBiologicalRole(p3_identifier_only, Participant.ENZYME_ROLE_MI, null));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveBiologicalRole(p4_different_role, Participant.ENZYME_ROLE_MI, null));
    }

    @Test
    public void test_identify_experimental_role_participant(){

        ParticipantEvidence p = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI), null);
        ParticipantEvidence p2_name_only = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, null), null);
        ParticipantEvidence p3_identifier_only = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm("test name", Participant.BAIT_ROLE_MI), null);
        ParticipantEvidence p4_different_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.FLUORESCENCE_DONOR_ROLE, Participant.FLUORESCENCE_DONOR_ROLE_MI), null);

        Assert.assertTrue(ParticipantUtils.doesParticipantHaveExperimentalRole(p, Participant.BAIT_ROLE_MI, Participant.BAIT_ROLE));
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveExperimentalRole(p2_name_only, Participant.BAIT_ROLE_MI, Participant.BAIT_ROLE));
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveExperimentalRole(p3_identifier_only, Participant.BAIT_ROLE_MI, Participant.BAIT_ROLE));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveExperimentalRole(p4_different_role, Participant.BAIT_ROLE_MI, Participant.BAIT_ROLE));

        // check name only
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveExperimentalRole(p, null, Participant.BAIT_ROLE));
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveExperimentalRole(p2_name_only, null, Participant.BAIT_ROLE));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveExperimentalRole(p3_identifier_only, null, Participant.BAIT_ROLE));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveExperimentalRole(p4_different_role, null, Participant.BAIT_ROLE));

        // check identifier only
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveExperimentalRole(p, Participant.BAIT_ROLE_MI, null));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveExperimentalRole(p2_name_only, Participant.BAIT_ROLE_MI, null));
        Assert.assertTrue(ParticipantUtils.doesParticipantHaveExperimentalRole(p3_identifier_only, Participant.BAIT_ROLE_MI, null));
        Assert.assertFalse(ParticipantUtils.doesParticipantHaveExperimentalRole(p4_different_role, Participant.BAIT_ROLE_MI, null));
    }

    @Test
    public void test_alternative_bait_for_spoke_expansion_based_on_experimental_role(){

        ParticipantEvidence p_bait_not_alternative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI), null);
        ParticipantEvidence p2_fluorescence_donor= new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.FLUORESCENCE_DONOR_ROLE, Participant.FLUORESCENCE_DONOR_ROLE_MI), null);
        ParticipantEvidence p3_gene_repressor = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.SUPPRESSOR_GENE_ROLE, Participant.SUPPRESSOR_GENE_ROLE_MI), null);
        ParticipantEvidence p4_different_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.SELF_ROLE, Participant.SELF_ROLE_MI), null);

        Assert.assertFalse(ParticipantUtils.isParticipantEvidenceAnAlternativeBaitForSpokeExpansion(p_bait_not_alternative));
        Assert.assertTrue(ParticipantUtils.isParticipantEvidenceAnAlternativeBaitForSpokeExpansion(p2_fluorescence_donor));
        Assert.assertTrue(ParticipantUtils.isParticipantEvidenceAnAlternativeBaitForSpokeExpansion(p3_gene_repressor));
        Assert.assertFalse(ParticipantUtils.isParticipantEvidenceAnAlternativeBaitForSpokeExpansion(p4_different_role));
    }

    @Test
    public void test_alternative_bait_for_spoke_expansion_based_on_biological_role(){

        ParticipantEvidence p_bait_not_alternative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI), null);
        Participant p2_enzyme= new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI));
        Participant p3_donor = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.DONOR_ROLE, Participant.DONOR_ROLE_MI));
        Participant p4_electron_donor = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.ELECTRON_DONOR_ROLE, Participant.ELECTRON_DONOR_ROLE_MI));
        Participant p5_phosphate_donor = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.PHOSPHATE_DONOR_ROLE, Participant.PHOSPHATE_DONOR_ROLE_MI));
        Participant p6_photon_donor = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.PHOTON_DONOR_ROLE, Participant.PHOTON_DONOR_ROLE_MI));

        Assert.assertFalse(ParticipantUtils.isParticipantAnAlternativeBaitForSpokeExpansion(p_bait_not_alternative));
        Assert.assertTrue(ParticipantUtils.isParticipantAnAlternativeBaitForSpokeExpansion(p2_enzyme));
        Assert.assertTrue(ParticipantUtils.isParticipantAnAlternativeBaitForSpokeExpansion(p3_donor));
        Assert.assertTrue(ParticipantUtils.isParticipantAnAlternativeBaitForSpokeExpansion(p4_electron_donor));
        Assert.assertTrue(ParticipantUtils.isParticipantAnAlternativeBaitForSpokeExpansion(p5_phosphate_donor));
        Assert.assertTrue(ParticipantUtils.isParticipantAnAlternativeBaitForSpokeExpansion(p6_photon_donor));
    }

    @Test
    public void collect_best_bait_participant_evidence_for_spoke_expansion(){

        ParticipantEvidence p_bait_not_alternative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI), null);
        ParticipantEvidence p2_fluorescence_donor= new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.FLUORESCENCE_DONOR_ROLE, Participant.FLUORESCENCE_DONOR_ROLE_MI), null);
        ParticipantEvidence p3_gene_repressor = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.SUPPRESSOR_GENE_ROLE, Participant.SUPPRESSOR_GENE_ROLE_MI), null);
        ParticipantEvidence p4_different_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.SELF_ROLE, Participant.SELF_ROLE_MI), null);
        ParticipantEvidence p5_enzyme_role = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI), CvTermUtils.createMICvTerm(Participant.SELF_ROLE, Participant.SELF_ROLE_MI), null);
        ParticipantEvidence p6_first_alphabetical = new DefaultParticipantEvidence(new DefaultProtein("a1"), null, CvTermUtils.createMICvTerm(Participant.SELF_ROLE, Participant.SELF_ROLE_MI), null);

        // choose bait
        List<ParticipantEvidence> evidences = Arrays.asList(p2_fluorescence_donor, p_bait_not_alternative, p3_gene_repressor, p4_different_role, p5_enzyme_role, p6_first_alphabetical);

        ParticipantEvidence bestBait = ParticipantUtils.collectBestParticipantEvidenceAsBaitForSpokeExpansion(evidences);
        Assert.assertTrue(bestBait == p_bait_not_alternative);

        // choose best alternative experimental role
        List<ParticipantEvidence> evidences2 = Arrays.asList(p5_enzyme_role, p2_fluorescence_donor, p3_gene_repressor, p4_different_role, p6_first_alphabetical);

        bestBait = ParticipantUtils.collectBestParticipantEvidenceAsBaitForSpokeExpansion(evidences2);
        Assert.assertTrue(bestBait == p2_fluorescence_donor);

        // choose best alternative bio role
        List<ParticipantEvidence> evidences3 = Arrays.asList(p4_different_role, p5_enzyme_role, p6_first_alphabetical);

        bestBait = ParticipantUtils.collectBestParticipantEvidenceAsBaitForSpokeExpansion(evidences3);
        Assert.assertTrue(bestBait == p5_enzyme_role);

        // choose best alternative alphabetical bait
        List<ParticipantEvidence> evidences4 = Arrays.asList(p4_different_role, p6_first_alphabetical);

        bestBait = ParticipantUtils.collectBestParticipantEvidenceAsBaitForSpokeExpansion(evidences4);
        Assert.assertTrue(bestBait == p6_first_alphabetical);
    }

    @Test
    public void collect_best_bait_participant_for_spoke_expansion(){

        ParticipantEvidence p_bait_not_alternative = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor(), null, CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI), null);
        Participant p2_enzyme= new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI));
        Participant p3_donor = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.DONOR_ROLE, Participant.DONOR_ROLE_MI));
        Participant p4_electron_donor = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.ELECTRON_DONOR_ROLE, Participant.ELECTRON_DONOR_ROLE_MI));
        Participant p5_phosphate_donor = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.PHOSPHATE_DONOR_ROLE, Participant.PHOSPHATE_DONOR_ROLE_MI));
        Participant p7_photon_donor = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor(), CvTermUtils.createMICvTerm(Participant.PHOTON_DONOR_ROLE, Participant.PHOTON_DONOR_ROLE_MI));
        ParticipantEvidence p6_first_alphabetical = new DefaultParticipantEvidence(new DefaultProtein("a1"), null, CvTermUtils.createMICvTerm(Participant.SELF_ROLE, Participant.SELF_ROLE_MI), null);

        // choose best alternative bio role
        List<Participant> participants = Arrays.asList(p_bait_not_alternative, p2_enzyme, p3_donor, p4_electron_donor, p5_phosphate_donor, p7_photon_donor, p6_first_alphabetical);

        Participant bestBait = ParticipantUtils.collectBestBaitParticipantForSpokeExpansion(participants);
        Assert.assertTrue(bestBait == p2_enzyme);

        // choose best alternative alphabetical bait
        List<ParticipantEvidence> participants2 = Arrays.asList(p_bait_not_alternative, p6_first_alphabetical);

        bestBait = ParticipantUtils.collectBestBaitParticipantForSpokeExpansion(participants2);
        Assert.assertTrue(bestBait == p6_first_alphabetical);
    }
}
