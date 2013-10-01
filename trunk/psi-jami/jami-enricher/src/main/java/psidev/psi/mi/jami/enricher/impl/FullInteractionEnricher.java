package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.checksum.RigidGenerator;
import psidev.psi.mi.jami.utils.checksum.SeguidException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullInteractionEnricher<I extends Interaction<P>, P extends Participant<I,F>, F extends Feature<P,F>>
        extends MinimalInteractionEnricher<I , P , F> {

    private RigidGenerator rigidGenerator;

    public FullInteractionEnricher() {
        super();
        this.rigidGenerator = new RigidGenerator();
    }

    @Override
    protected void processInteraction(I interactionToEnrich) throws EnricherException {

        // process RIGID
        processRigid(interactionToEnrich);
    }

    protected void processRigid(I interactionToEnrich) throws EnricherException {

        if (interactionToEnrich.getRigid() == null){
            String rigid = generateRigid(interactionToEnrich);

            if (rigid != null){
                interactionToEnrich.setRigid(rigid);
                if (getInteractionEnricherListener() != null){
                    getInteractionEnricherListener().onUpdatedRigid(interactionToEnrich, null);
                }
            }
        }

        this.rigidGenerator.getRogids().clear();
    }

    protected RigidGenerator getRigidGenerator() {
        return rigidGenerator;
    }

    protected String generateRigid(I interactionToEnrich) throws EnricherException {
        boolean canComputeRigid = true;

        // first collect all rogids
        for (P participant : interactionToEnrich.getParticipants()){
            Interactor interactor = participant.getInteractor();

            Checksum rogid = ChecksumUtils.collectFirstChecksumWithMethod(interactor.getChecksums(), null, Checksum.ROGID);
            if (rogid == null){
                canComputeRigid = false;
                break;
            }
            else{
                this.rigidGenerator.getRogids().add(rogid.getValue());
            }
        }
        if (canComputeRigid){
            try {
                return this.rigidGenerator.calculateRigid();
            } catch (SeguidException e) {
                throw new EnricherException("Cannot compute the rigid.", e);
            }
        }
        return null;
    }
}
