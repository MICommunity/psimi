package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.util.Collection;

/**
 * The InteractionDataSourceWriter can write interactions in a datasource (files, database)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public interface InteractionDataSourceWriter {

    /**
     * Writes an interaction
     * @param interaction
     */
    public void write(Interaction interaction);

    /**
     * Writes an interactionEvidence
     * @param interaction
     */
    public void write(InteractionEvidence interaction);

    /**
     * Writes a modelled interaction
     * @param interaction
     */
    public void write(ModelledInteraction interaction);

    /**
     * Writes a collection of Interaction objects
     * @param interactions
     */
    public void writeInteractions(Collection<Interaction> interactions);

    /**
     * Writes a collection of InteractionEvidence objects
     * @param interactions
     */
    public void writeInteractionEvidences(Collection<InteractionEvidence> interactions);

    /**
     * Writes a Collection of ModelledInteraction objects
     * @param interactions
     */
    public void writeModelledInteractions(Collection<ModelledInteraction> interactions);

    /**
     * Flushes the writer (commit or write on disk)
     */
    public void flush();

    /**
     * Closes the InteractionDataSourceWriter. It will flushes before closing.
     */
    public void close();
}
