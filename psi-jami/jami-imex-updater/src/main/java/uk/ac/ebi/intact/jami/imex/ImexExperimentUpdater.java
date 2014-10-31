package uk.ac.ebi.intact.jami.imex;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.full.FullExperimentEnricher;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.intact.jami.imex.actions.ImexAssigner;
import uk.ac.ebi.intact.jami.imex.listener.ExperimentImexEnricherListener;

/**
 * This enricher will update an experiment attached to a publication having an IMEx id
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/14</pre>
 */

public class ImexExperimentUpdater extends FullExperimentEnricher{

    private ImexAssigner imexAssigner;

    public ImexExperimentUpdater() {
        super();
    }

    @Override
    protected void processInteractionDetectionMethod(Experiment experimentToEnrich) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processOrganism(Experiment experimentToEnrich) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processConfidences(Experiment experimentToEnrich, Experiment objectSource) {
        // nothing to do
    }

    @Override
    protected void processAnnotations(Experiment experimentToEnrich, Experiment objectSource) {
        // nothing to do
    }

    @Override
    protected void processXrefs(Experiment experimentToEnrich, Experiment objectSource) {
        if (experimentToEnrich.getPublication() != null && experimentToEnrich.getPublication().getImexId() != null){

            try {
                getImexAssigner().updateImexIdentifierForExperiment(experimentToEnrich, experimentToEnrich.getPublication().getImexId());
                if (getExperimentEnricherListener() instanceof ExperimentImexEnricherListener){
                    ((ExperimentImexEnricherListener)getExperimentEnricherListener()).onImexIdAssigned(experimentToEnrich, experimentToEnrich.getPublication().getImexId());
                }

            } catch (EnricherException e) {
                if (getExperimentEnricherListener() instanceof ExperimentImexEnricherListener){
                    ((ExperimentImexEnricherListener)getExperimentEnricherListener()).onImexIdConflicts(experimentToEnrich,
                            XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(experimentToEnrich.getXrefs(), Xref.IMEX_MI, Xref.IMEX, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY));
                }
            }
        }
    }

    @Override
    protected void processInteractionDetectionMethod(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processOrganism(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException {
        // nothing to do
    }

    @Override
    protected void processVariableParameters(Experiment experimentToEnrich, Experiment objectSource) {
        // nothing to do
    }

    @Override
    protected void processOtherProperties(Experiment experimentToEnrich) throws EnricherException {
        super.processOtherProperties(experimentToEnrich);
        // now works with xrefs
        processXrefs(experimentToEnrich, null);
    }

    public ImexAssigner getImexAssigner() {
        return imexAssigner;
    }

    public void setImexAssigner(ImexAssigner imexAssigner) {
        this.imexAssigner = imexAssigner;
    }
}


