package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultComplex;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;
import psidev.psi.mi.jami.utils.clone.InteractorCloner;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The bipartite expansion.
 *
 * Complex n-ary data has been expanded to binary using the bipartite model.
 * This assumes that all molecules in the complex interact with a single externally designated entity.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class BipartiteExpansion extends AbstractComplexExpansionMethod {

    public BipartiteExpansion(){
        super(CvTermUtils.createMICvTerm(ComplexExpansionMethod.BIPARTITE_EXPANSION, ComplexExpansionMethod.BIPARTITE_EXPANSION_MI));
    }

    @Override
    protected Collection<BinaryInteractionEvidence> collectBinaryInteractionEvidencesFrom(InteractionEvidence interaction){
        Complex complexEntity = createComplexEntity(interaction);
        InteractorCloner.copyAndOverrideBasicComplexPropertiesWithInteractionProperties(interaction, complexEntity);
        ParticipantEvidence externalEntity =  new DefaultParticipantEvidence(complexEntity);

        Collection<BinaryInteractionEvidence> binaryInteractions = new ArrayList<BinaryInteractionEvidence>(interaction.getParticipants().size());
        for ( Participant p : interaction.getParticipants() ) {

            // build a new interaction
            BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence(getMethod());
            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, binary, false, true);

            // set participants
            initialiseBinaryInteractionParticipantsWith(externalEntity, p, binary);

            binaryInteractions.add(binary);
        }

        return binaryInteractions;
    }

    @Override
    protected Collection<ModelledBinaryInteraction> collectModelledBinaryInteractionsFrom(ModelledInteraction interaction){
        Complex complexEntity = createComplexEntity(interaction);
        InteractorCloner.copyAndOverrideBasicComplexPropertiesWithModelledInteractionProperties(interaction, complexEntity);
        ModelledParticipant externalEntity =  new DefaultModelledParticipant(complexEntity);

        Collection<ModelledBinaryInteraction> binaryInteractions = new ArrayList<ModelledBinaryInteraction>(interaction.getParticipants().size());
        for ( Participant p : interaction.getParticipants() ) {

            // build a new interaction
            ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction(getMethod());
            InteractionCloner.copyAndOverrideModelledInteractionProperties(interaction, binary, false, true);

            // set participants
            initialiseBinaryInteractionParticipantsWith(externalEntity, p, binary);

            binaryInteractions.add(binary);
        }

        return binaryInteractions;
    }

    @Override
    protected Collection<BinaryInteraction> collectDefaultBinaryInteractionsFrom(Interaction interaction){
        Complex complexEntity = createComplexEntity(interaction);
        InteractorCloner.copyAndOverrideBasicComplexPropertiesWithInteractionProperties(interaction, complexEntity);
        Participant externalEntity =  new DefaultParticipant(complexEntity);

        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>(interaction.getParticipants().size());
        for ( Participant p : interaction.getParticipants() ) {

            // build a new interaction
            BinaryInteraction binary = new DefaultBinaryInteraction(getMethod());
            InteractionCloner.copyAndOverrideBasicInteractionProperties(interaction, binary, false, true);

            // set participants
            initialiseBinaryInteractionParticipantsWith(externalEntity, p, binary);

            binaryInteractions.add(binary);
        }

        return binaryInteractions;
    }

    private Complex createComplexEntity(Interaction interaction) {
        String complexName = generateComplexName(interaction);
        Complex interactionAsComplex = new DefaultComplex(complexName);
        return interactionAsComplex;
    }

    protected String generateComplexName(Interaction interaction) {
        String complexName = interaction.getShortName() != null ? interaction.getShortName() : interaction.toString();
        if (complexName == null || complexName.length() == 0){
            complexName = Integer.toString(interaction.hashCode());
        }
        return complexName;
    }
}