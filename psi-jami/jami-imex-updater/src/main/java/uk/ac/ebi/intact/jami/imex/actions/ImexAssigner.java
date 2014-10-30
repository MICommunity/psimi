package uk.ac.ebi.intact.jami.imex.actions;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Publication;
import uk.ac.ebi.intact.jami.bridges.imex.ImexCentralClient;

/**
 * Interface for assigning IMEx id to a publication and updating intact publications, experiments and interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/03/12</pre>
 */

public interface ImexAssigner {

    /**
     * Assign a new IMEx id to a publication that has not been assigned yet and update/clean up the annotations of the publication (full coverage and imex curation). It adds an IMEx primary reference
     * to the intact publication. It is only possible to assign a new IMEx id to publications having valid pubmed ids (no unassigned and no DOI number)
     * @param publication : the publication in IntAct
     * @param imexPublication : the publication in IMEx
     * @return the imex id if the IMEx assigner was successful, null otherwise
     * @throws psidev.psi.mi.jami.bridges.exception.BridgeFailedException if no record found or no IMEx id
     */
    public void assignImexIdentifier(Publication publication, Publication imexPublication) throws BridgeFailedException;

    /**
     * Add a IMEx primary reference to the experiment
     * @param experiment
     * @param imexId
     * @return true if the IMEx assigner did an update of the experiment, false otherwise
     */
    public boolean updateImexIdentifierForExperiment(Experiment experiment, String imexId) throws EnricherException;

    /**
     * Add a IMEx primary reference to the interaction
     * @param interaction
     * @param imexId
     * @return true if the IMEx assigner did an update of the interaction, false otherwise
     */
    public boolean updateImexIdentifierForInteraction(InteractionEvidence interaction, String imexId) throws EnricherException;

    /**
     *
     * @param interaction
     * @return true if the interaction can have an IMEx id, false otherwise
     */
    public boolean isEntitledToImex( InteractionEvidence interaction );

    public void clearInteractionImexContext();

    public int getNextImexChunkNumberAndFilterValidImexIdsFrom(Publication publication);


    public ImexCentralClient getImexCentralClient();
}
