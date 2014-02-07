package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.checksum.RigidGenerator;
import psidev.psi.mi.jami.utils.checksum.SeguidException;

/**
 * Full enricher for interactions
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class FullInteractionEnricher<I extends Interaction, P extends Participant, F extends Feature>
        extends MinimalInteractionEnricher<I , P , F> {

    private RigidGenerator rigidGenerator;

    public FullInteractionEnricher() {
        super();
        this.rigidGenerator = new RigidGenerator();
    }

    @Override
    protected void processOtherProperties(I interactionToEnrich) throws EnricherException {

        // process RIGID
        processRigid(interactionToEnrich);
    }

    @Override
    protected void processOtherProperties(I objectToEnrich, I objectSource) throws EnricherException {

        // process checksums
        processChecksums(objectToEnrich, objectSource);
        // process xrefs
        processXrefs(objectToEnrich, objectSource);
        // process annotations
        processAnnotations(objectToEnrich, objectSource);
    }

    protected void processChecksums(I objectToEnrich, I objectSource) throws EnricherException{

        EnricherUtils.mergeChecksums(objectToEnrich, objectToEnrich.getChecksums(), objectSource.getChecksums(), false, getInteractionEnricherListener());
        // process RIGID
        processRigid(objectToEnrich);
    }

    protected void processXrefs(I objectToEnrich, I objectSource) throws EnricherException{
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getXrefs(), objectSource.getXrefs(), false, false, getInteractionEnricherListener(),                getInteractionEnricherListener());

    }

    protected void processAnnotations(I objectToEnrich, I objectSource) throws EnricherException{
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), objectSource.getAnnotations(), false, getInteractionEnricherListener());
    }

    protected void processRigid(I interactionToEnrich) throws EnricherException {

        if (interactionToEnrich.getRigid() == null){
            String rigid = generateRigid(interactionToEnrich);

            if (rigid != null){
                Checksum checksum = ChecksumUtils.createRigid(rigid);
                interactionToEnrich.getChecksums().add(checksum);
                if (getInteractionEnricherListener() != null){
                    getInteractionEnricherListener().onAddedChecksum(interactionToEnrich, checksum);
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
        for (Object o : interactionToEnrich.getParticipants()){
            P participant = (P)o;
            Interactor interactor = participant.getInteractor();

            Checksum rogid = ChecksumUtils.collectFirstChecksumWithMethod(interactor.getChecksums(), Checksum.ROGID_MI, Checksum.ROGID);
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
