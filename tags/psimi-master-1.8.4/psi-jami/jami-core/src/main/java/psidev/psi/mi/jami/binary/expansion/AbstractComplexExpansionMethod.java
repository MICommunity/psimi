package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.InteractionUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * Abstract class for ComplexExpansionMethod
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public abstract class AbstractComplexExpansionMethod<T extends Interaction<? extends Participant>, B extends BinaryInteraction> implements ComplexExpansionMethod<T,B> {

    private CvTerm method;

    public AbstractComplexExpansionMethod(CvTerm method){
        if (method == null){
           throw new IllegalArgumentException("The method is mandatory to define a new ComplexExpansionMethod");
        }
        this.method = method;
    }

    public CvTerm getMethod() {
        return this.method;
    }

    public boolean isInteractionExpandable(T interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public Collection<B> expand(T interaction) {

        InteractionCategory category = findInteractionCategory(interaction);

        switch (category){
            case binary:
                return createBinaryInteractionsFrom(interaction);
            case self_intra_molecular:
                return createBinaryInteractionsFrom(interaction);
            case self_inter_molecular:
                return createNewSelfBinaryInteractionsFrom(interaction);
            case n_ary:
                return collectBinaryInteractionsFrom(interaction);
            default:
                break;
        }

        return Collections.EMPTY_LIST;
    }

    protected Collection<B> createNewSelfBinaryInteractionsFrom(T interaction) {
        return Collections.singletonList((B) InteractionUtils.createNewSelfBinaryInteractionFrom(interaction));
    }

    protected Collection<B> createBinaryInteractionsFrom(T interaction) {
        return Collections.singletonList((B) InteractionUtils.createBinaryInteractionFrom(interaction));
    }

    protected InteractionCategory findInteractionCategory(T interaction) {
        return InteractionUtils.findInteractionCategoryOf(interaction, true);
    }

    protected abstract Collection<B> collectBinaryInteractionsFrom(T interaction);
}