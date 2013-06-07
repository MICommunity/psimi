package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

import java.util.Collection;

/**
 * The method by which complex n-ary data is expanded into binary data. This may be performed manually on data input, or computationally on data export.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public interface ComplexExpansionMethod {

    public static final String BIPARTITE_EXPANSION_MI = "MI:1062";
    public static final String BIPARTITE_EXPANSION = "bipartite expansion";
    public static final String SPOKE_EXPANSION_MI = "MI:1060";
    public static final String SPOKE_EXPANSION = "spoke expansion";
    public static final String MATRIX_EXPANSION_MI = "MI:1061";
    public static final String MATRIX_EXPANSION = "matrix expansion";

    /**
     * The method represented by the ComplexExpansionMethod object.
     * It is a controlled vocabulary term and cannot be null.
     * @return the complex expansion method
     */
    public CvTerm getMethod();

    /**
     * Method to know if this ComplexExpansionMethod can expandInteraction the given Interaction
     * @param interaction
     * @return true if this interaction can be expanded with this ComplexExpansionMethod
     */
    public boolean isInteractionExpandable(Interaction interaction);

    /**
     * Method to know if this ComplexExpansionMethod can expandInteraction the given InteractionEvidence
     * @param interaction
     * @return true if this interactionEvidence can be expanded with this ComplexExpansionMethod
     */
    public boolean isInteractionEvidenceExpandable(InteractionEvidence interaction);

    /**
     * Method to know if this ComplexExpansionMethod can expandInteraction the given ModelledInteraction
     * @param interaction
     * @return true if this modelledInteraction can be expanded with this ComplexExpansionMethod
     */
    public boolean isModelledInteractionExpandable(ModelledInteraction interaction);

    /**
     * Expand the interaction in a collection of BinaryInteraction<Participant>.
     * It will ignore if the interaction is interactionEvidence/modelledInteraction.
     * It will create new DefaultBinaryInteractions
     * The collection cannot be null.
     * @param interaction
     * @return
     * @throws IllegalArgumentException if the interaction cannot be expanded with this method
     */
    public Collection<BinaryInteraction> expandInteraction(Interaction interaction);

    /**
     * Expand the interactionEvidence in a collection of BinaryInteractionEvidence.
     * The collection cannot be null.
     * @param interaction
     * @return
     * @throws IllegalArgumentException if the interaction cannot be expanded with this method
     */
    public Collection<BinaryInteractionEvidence> expandInteractionEvidence(InteractionEvidence interaction);

    /**
     * Expand the modelledInteraction in a collection of ModelledBinaryInteraction.
     * The collection cannot be null.
     * @param interaction
     * @return
     * @throws IllegalArgumentException if the interaction cannot be expanded with this method
     */
    public Collection<ModelledBinaryInteraction> expandModelledInteraction(ModelledInteraction interaction);
}
