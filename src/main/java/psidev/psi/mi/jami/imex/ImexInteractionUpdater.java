package psidev.psi.mi.jami.imex;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.full.FullInteractionEnricher;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.imex.actions.ImexAssigner;
import psidev.psi.mi.jami.imex.listener.InteractionImexEnricherListener;

/**
 * This enricher will update a interaction evidence having a publication with IMEx id
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/14</pre>
 */

public class ImexInteractionUpdater extends FullInteractionEnricher{
    private ImexAssigner imexAssigner;

    public ImexInteractionUpdater() {
        super();
    }

    @Override
    protected void processOtherProperties(Interaction interactionToEnrich) throws EnricherException {
        super.processOtherProperties(interactionToEnrich);
        // process xrefs
        processXrefs(interactionToEnrich, null);
    }

    @Override
    protected void processChecksums(Interaction objectToEnrich, Interaction objectSource) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processXrefs(Interaction objectToEnrich, Interaction objectSource) throws EnricherException {
        if (objectToEnrich instanceof InteractionEvidence){
            InteractionEvidence evidence = (InteractionEvidence)objectToEnrich;
            if (evidence.getExperiment() != null &&
                    evidence.getExperiment().getPublication() != null
                    && evidence.getExperiment().getPublication().getImexId() != null
                    && getImexAssigner() != null){

                try {
                    getImexAssigner().clearInteractionImexContext();
                    getImexAssigner().updateImexIdentifierForInteraction(evidence, evidence.getExperiment().getPublication().getImexId());
                    if (getInteractionEnricherListener() instanceof InteractionImexEnricherListener){
                        ((InteractionImexEnricherListener)getInteractionEnricherListener()).onImexIdAssigned(evidence,
                                evidence.getExperiment().getPublication().getImexId());
                    }

                } catch (EnricherException e) {
                    if (getInteractionEnricherListener() instanceof InteractionImexEnricherListener){
                        ((InteractionImexEnricherListener)getInteractionEnricherListener()).onImexIdConflicts(evidence,
                                XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(evidence.getXrefs(), Xref.IMEX_MI, Xref.IMEX, Xref.IMEX_PRIMARY_MI,
                                        Xref.IMEX_PRIMARY));
                    }
                    else{
                        throw e;
                    }
                }
            }
        }
    }

    @Override
    protected void processAnnotations(Interaction objectToEnrich, Interaction objectSource) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processRigid(Interaction interactionToEnrich) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processCreatedDate(Interaction objectToEnrich, Interaction objectSource) {
        // nothing to do
    }

    @Override
    protected void processUpdateDate(Interaction objectToEnrich, Interaction objectSource) {
        // nothing to do
    }

    @Override
    protected void processShortName(Interaction objectToEnrich, Interaction objectSource) {
        // nothing to do
    }

    @Override
    protected void processIdentifiers(Interaction objectToEnrich, Interaction objectSource) {
        // nothing to do
    }

    @Override
    protected void processParticipants(Interaction objectToEnrich, Interaction objectSource) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processInteractionType(Interaction objectToEnrich, Interaction objectSource) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processInteractionType(Interaction interactionToEnrich) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processParticipants(Interaction interactionToEnrich) throws EnricherException {
        // nothing to do
    }


    public ImexAssigner getImexAssigner() {
        return imexAssigner;
    }

    public void setImexAssigner(ImexAssigner imexAssigner) {
        this.imexAssigner = imexAssigner;
    }
}


