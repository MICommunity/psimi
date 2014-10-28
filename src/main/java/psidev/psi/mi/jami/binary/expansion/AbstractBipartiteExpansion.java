package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.factory.DefaultInteractorFactory;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.clone.InteractorCloner;
import psidev.psi.mi.jami.factory.InteractorFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract class for BipartiteExpansion.
 *
 * An InteractorFactory can be provided if we want to override the complex created by this expansion method
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public abstract class AbstractBipartiteExpansion<T extends Interaction, B extends BinaryInteraction>
        extends AbstractComplexExpansionMethod<T,B> {

    private InteractorFactory interactorFactory;

    public AbstractBipartiteExpansion(){
        super(CvTermUtils.createMICvTerm(ComplexExpansionMethod.BIPARTITE_EXPANSION, ComplexExpansionMethod.BIPARTITE_EXPANSION_MI));
    }

    /**
     *
     * @return  the interactor factory we want to use to create the complex for bipartite expansion
     */
    public InteractorFactory getInteractorFactory() {
        if (this.interactorFactory == null){
            this.interactorFactory = new DefaultInteractorFactory();
        }
        return interactorFactory;
    }

    /**
     *
     * @param interactorFactory : the interactor factory we want to use to create the complex for bipartite expansion
     */
    public void setInteractorFactory(InteractorFactory interactorFactory) {
        this.interactorFactory = interactorFactory;
    }

    @Override
    protected Collection<B> collectBinaryInteractionsFromNary(T interaction){
        Participant externalEntity =  createParticipantForComplexEntity(createComplexEntity(interaction));

        Collection<B> binaryInteractions = new ArrayList<B>(interaction.getParticipants().size());
        for ( Object p : interaction.getParticipants() ) {

            // build a new interaction
            B binary = createBinaryInteraction(interaction, externalEntity, (Participant)p);

            binaryInteractions.add(binary);
        }

        return binaryInteractions;
    }

    /**
     *
     * @param interaction : the interaction to expand
     * @param c1 : the participant with the created complex
     * @param c2 : the original participant
     * @param <P> : type of participant
     * @return the binary interaction
     */
    protected abstract <P extends Participant> B createBinaryInteraction(T interaction, P c1, P c2);

    /**
     *
     * @param complexEntity : the generated complex
     * @param <P> : type of participant
     * @return the participant generated for the generated complex
     */
    protected abstract <P extends Participant> P createParticipantForComplexEntity(Complex complexEntity);

    /**
     *
     * @param interaction : the interaction to expand
     * @return  the complex generated from this interaction instance
     */
    protected Complex createComplexEntity(T interaction) {
        String complexName = generateComplexName(interaction);
        Complex interactionAsComplex = getInteractorFactory().createComplex(complexName, null);
        InteractorCloner.copyAndOverrideBasicComplexPropertiesWithInteractionProperties(interaction, interactionAsComplex);
        return interactionAsComplex;
    }

    /**
     *
     * @param interaction : the interaction to expand
     * @return the generated name for this interaction
     */
    protected String generateComplexName(Interaction interaction) {
        String complexName = interaction.getShortName() != null ? interaction.getShortName() : interaction.toString();
        if (complexName == null || complexName.length() == 0){
            complexName = Integer.toString(interaction.hashCode());
        }
        return complexName;
    }
}
