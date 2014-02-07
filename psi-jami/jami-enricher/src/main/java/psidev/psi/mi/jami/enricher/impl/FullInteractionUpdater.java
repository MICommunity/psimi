package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.ChecksumUtils;

/**
 * Full updater of interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullInteractionUpdater<I extends Interaction, P extends Participant, F extends Feature>
        extends FullInteractionEnricher<I , P , F>  {

    private InteractionEnricher<I,P,F> delegate;

    public FullInteractionUpdater(){
         super();
        this.delegate = new FullInteractionEnricher<I, P, F>();
    }

    protected FullInteractionUpdater(InteractionEnricher<I,P,F> delegate){
        super();
        if (delegate == null){
            throw new IllegalArgumentException("Interaction enricher delegate is required");
        }
        this.delegate = delegate;
    }

    @Override
    protected void processMinimalUpdates(I objectToEnrich, I objectSource) throws EnricherException {
        this.delegate.enrich(objectToEnrich, objectSource);
    }

    @Override
    protected void processRigid(I interactionToEnrich) throws EnricherException {
        String rigid = generateRigid(interactionToEnrich);
        String oldRigid = interactionToEnrich.getRigid();

        if ((rigid != null && !rigid.equals(oldRigid)) || (rigid == null && oldRigid != null)){
            Checksum oldChecksum = ChecksumUtils.collectFirstChecksumWithMethodAndValue(interactionToEnrich.getChecksums(), Checksum.RIGID_MI, Checksum.RIGID, oldRigid);
            interactionToEnrich.setRigid(rigid);
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onRemovedChecksum(interactionToEnrich,oldChecksum);
                getInteractionEnricherListener().onAddedChecksum(interactionToEnrich,
                        ChecksumUtils.collectFirstChecksumWithMethodAndValue(interactionToEnrich.getChecksums(), Checksum.RIGID_MI, Checksum.RIGID, rigid));
            }
        }

        getRigidGenerator().getRogids().clear();
    }

    @Override
    protected void processChecksums(I objectToEnrich, I objectSource) throws EnricherException {
        EnricherUtils.mergeChecksums(objectToEnrich, objectToEnrich.getChecksums(), objectSource.getChecksums(), true, getInteractionEnricherListener());
        // process RIGID
        processRigid(objectToEnrich);
    }

    @Override
    protected void processXrefs(I objectToEnrich, I objectSource) throws EnricherException {
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getXrefs(), objectSource.getXrefs(), true, false, getInteractionEnricherListener(),
                getInteractionEnricherListener());
    }

    @Override
    protected void processAnnotations(I objectToEnrich, I objectSource) throws EnricherException {
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), objectSource.getAnnotations(), true, getInteractionEnricherListener());
    }
}
