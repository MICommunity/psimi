package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultComplex;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;
import psidev.psi.mi.jami.utils.clone.InteractorCloner;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract class for BipartiteExpansion
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class AbstractBipartiteExpansion<T extends Interaction> extends AbstractComplexExpansionMethod<T> {

    public AbstractBipartiteExpansion(){
        super(CvTermUtils.createMICvTerm(ComplexExpansionMethod.BIPARTITE_EXPANSION, ComplexExpansionMethod.BIPARTITE_EXPANSION_MI));
    }

    @Override
    protected Collection<? extends BinaryInteraction> collectBinaryInteractionsFrom(T interaction){
        Participant externalEntity =  createParticipantForComplexEntity(createComplexEntity(interaction));

        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>(interaction.getParticipants().size());
        for ( Participant p : interaction.getParticipants() ) {

            // build a new interaction
            BinaryInteraction binary = createBinaryInteraction(interaction, externalEntity, p);

            binaryInteractions.add(binary);
        }

        return binaryInteractions;
    }

    protected BinaryInteraction createBinaryInteraction(T interaction, Participant c1, Participant c2){
        BinaryInteraction binary = new DefaultBinaryInteraction(getMethod());
        InteractionCloner.copyAndOverrideBasicInteractionProperties(interaction, binary, false, true);
        binary.setParticipantA(c1);
        binary.setParticipantB(c2);
        return binary;
    }

    protected Participant createParticipantForComplexEntity(Complex complexEntity){
        return new DefaultParticipant(complexEntity);
    }

    protected Complex createComplexEntity(T interaction) {
        String complexName = generateComplexName(interaction);
        Complex interactionAsComplex = new DefaultComplex(complexName);
        InteractorCloner.copyAndOverrideBasicComplexPropertiesWithInteractionProperties(interaction, interactionAsComplex);
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
