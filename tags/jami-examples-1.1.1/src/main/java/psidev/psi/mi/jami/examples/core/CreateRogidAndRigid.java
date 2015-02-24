package psidev.psi.mi.jami.examples.core;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.checksum.RigidGenerator;
import psidev.psi.mi.jami.utils.checksum.RogidGenerator;

/**
 * This code example shows how to compute RIGID/ROGID checksums for proteins and interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/02/15</pre>
 */

public class CreateRogidAndRigid {

    public static void main(String[] args) throws Exception{
        // Create test environment
        Interaction i1 = createInteraction();

        // create rogid generator
        RogidGenerator rogidGenerator = new RogidGenerator();
        // create rigid generator
        RigidGenerator rigidGenerator = new RigidGenerator();

        // generate a rogid for each protein in interaction
        for (Object p : i1.getParticipants()){
            Participant participant = (Participant)p;

            // check if protein
            if (participant.getInteractor() instanceof Protein){
                Protein prot = (Protein) participant.getInteractor();
                // check sequence and organism
                if (prot.getSequence() != null && prot.getOrganism() != null){
                    String rogid = rogidGenerator.computeRogidFrom(prot.getSequence(),
                            Integer.toString(prot.getOrganism().getTaxId()));
                    prot.setRogid(rogid);

                    // add rogid to rigid generator
                    rigidGenerator.getRogids().add(rogid);
                }
            }
        }

        // computes rigid interaction
        String rigid = rigidGenerator.calculateRigid();
        i1.setRigid(rigid);

        // clear all rogids from rigid generator
        rigidGenerator.getRogids().clear();
    }

    protected static Interaction createInteraction() {
        // first create an interaction

        InteractionEvidence nary = new DefaultInteractionEvidence();
        Protein prot1 = new DefaultProtein("p1");
        prot1.setSequence("NDKSDTYSAG");
        ParticipantEvidence p1 = new DefaultParticipantEvidence(prot1);
        p1.setExperimentalRole(CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI));
        Protein prot2 = new DefaultProtein("p2");
        prot2.setSequence("KCINRASDPV");
        ParticipantEvidence p2 = new DefaultParticipantEvidence(prot2);
        Protein prot3 = new DefaultProtein("p3");
        prot3.setSequence("ADVGELKMGT");
        ParticipantEvidence p3 = new DefaultParticipantEvidence(prot3);
        nary.addParticipant(p1);
        nary.addParticipant(p2);
        nary.addParticipant(p3);

        return nary;
    }
}
