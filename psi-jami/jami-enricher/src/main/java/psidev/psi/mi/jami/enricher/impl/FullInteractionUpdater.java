package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

/**
 * Full updater of interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullInteractionUpdater<I extends Interaction<P>, P extends Participant<I,F>, F extends Feature<P,F>>
        extends FullInteractionEnricher<I , P , F>  {

    @Override
    protected void processRigid(I interactionToEnrich) throws EnricherException {
        String rigid = generateRigid(interactionToEnrich);
        String oldRigid = interactionToEnrich.getRigid();

        if ((rigid != null && !rigid.equals(oldRigid)) || (rigid == null && oldRigid != null)){
            interactionToEnrich.setRigid(rigid);
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onUpdatedRigid(interactionToEnrich, oldRigid);
            }
        }

        getRigidGenerator().getRogids().clear();
    }
}
