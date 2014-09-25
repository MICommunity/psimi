package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.exception.ComplexExpansionException;
import psidev.psi.mi.jami.factory.BinaryInteractionFactory;
import psidev.psi.mi.jami.factory.DefaultBinaryInteractionFactory;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.InteractionUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * Abstract class for ComplexExpansionMethod.
 *
 * It needs a BinaryInteractionFactory to be able to create the proper binary interaction instance.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public abstract class AbstractComplexExpansionMethod<T extends Interaction, B extends BinaryInteraction> implements ComplexExpansionMethod<T,B> {

    private CvTerm method;
    private BinaryInteractionFactory factory;

    /**
     *
     * @param method : the CvTerm that describe the method used to expand
     */
    public AbstractComplexExpansionMethod(CvTerm method){
        if (method == null){
           throw new IllegalArgumentException("The method is mandatory to define a new ComplexExpansionMethod");
        }
        this.method = method;
    }

    /**
     * The CvTerm that describe the method used to expand.
     * It cannot be null
     * @return CvTerm that describe the method used to expand
     */
    public CvTerm getMethod() {
        return this.method;
    }

    /**
     * By default, a complex expansion can only expand interactions having at least one participant and where the provided interaction is
     * not null.
     * @param interaction : the interaction we want to expand
     * @return true if this complex expansion method can expand such an interaction, false otherwise.
     */
    public boolean isInteractionExpandable(T interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    /**
     *
     * @param interaction : the interaction to expand
     * @return  the collection of binary interactions generated from the given interaction.
     * @throws psidev.psi.mi.jami.exception.ComplexExpansionException : when the interaction is not expandable by this method
     */
    public Collection<B> expand(T interaction) throws ComplexExpansionException {

        if (!isInteractionExpandable(interaction)){
            throw new ComplexExpansionException("Cannot expand the interaction: "+interaction.toString() + " with the expansion: "+method.toString());
        }

        ComplexType category = findInteractionCategory(interaction);

        switch (category){
            case binary:
                return createBinaryInteractionWrappersFrom(interaction);
            case self_intra_molecular:
                return createBinaryInteractionWrappersFrom(interaction);
            case self_inter_molecular:
                return createNewSelfBinaryInteractionsFrom(interaction);
            case n_ary:
                return collectBinaryInteractionsFromNary(interaction);
            default:
                throw new ComplexExpansionException("Cannot expand the interaction: "+interaction.toString() + " because does not recognize the complex type : "+category.toString());
        }
    }

    /**
     *
     * @return the factory used by the complex epxansion to create new binaryInteraction instances
     */
    public BinaryInteractionFactory getBinaryInteractionFactory() {
        if (this.factory == null){
            this.factory = new DefaultBinaryInteractionFactory();
        }
        return this.factory;
    }

    /**
     *
     * @param factory : the factory used by the complex epxansion to create new binaryInteraction instances
     */
    public void setBinaryInteractionFactory(BinaryInteractionFactory factory) {
        this.factory = factory;
    }

    /**
     *
     * @param interaction : the self interaction to expand
     * @return the collection of binary interaction generated from this self interaction
     */
    protected Collection<B> createNewSelfBinaryInteractionsFrom(T interaction) {
        return Collections.singletonList((B) getBinaryInteractionFactory().createSelfBinaryInteractionFrom(interaction));
    }

    /**
     *
     * @param interaction : the interaction with only two participants
     * @return the collection of binary interaction wrappers generated from this interaction with two partcipants
     */
    protected Collection<B> createBinaryInteractionWrappersFrom(T interaction) {
        return Collections.singletonList((B) getBinaryInteractionFactory().createBinaryInteractionWrapperFrom(interaction));
    }

    /**
     *
     * @param interaction : the interaction to expand
     * @return the complexType that match this interaction and which will be used to expand
     */
    protected ComplexType findInteractionCategory(T interaction) {
        return InteractionUtils.findInteractionCategoryOf(interaction, true);
    }

    /**
     *
     * @param interaction : the interaction to expand
     * @return the collection of binary interaction generated from this n-ary interaction
     */
    protected abstract Collection<B> collectBinaryInteractionsFromNary(T interaction);
}