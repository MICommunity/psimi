package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CooperativeInteraction;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic CooperativeInteractionComparator.
 *
 * It will first compare the basic interaction properties using ModelledInteractionComparator.
 * It will then compare the cooperative mechanism using AbstractCvTermComparator. If the cooperative mechanisms are the same,
 * it will compare the effect outcome using AbstractCvTermComparator. If the effect outcomes are the same, it will compare the responses using
 * AbstractCvTermComparator. If the responses are the same, it will compare the affected interactions using a ModelledInteractionComparator that does not compare
 * cooperative interaction properties (avoiding internal loop).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class CooperativeInteractionComparator implements Comparator<CooperativeInteraction> {

    protected ModelledInteractionComparator interactionComparator;
    protected AbstractCvTermComparator cvTermComparator;
    protected ModelledInteractionCollectionComparator modelledInteractionCollectionComparator;

    /**
     * Creates a new CooperativeInteractionComparator.
     * @param interactionComparator : required to compare basic properties of a modelled interaction and to compare affected interactions
     * @param cvTermComparator : required to compare response, mechanism and effect outcome
     */
    public CooperativeInteractionComparator(ModelledInteractionComparator interactionComparator, AbstractCvTermComparator cvTermComparator){
        if (interactionComparator == null){
            throw new IllegalArgumentException("The Interaction comparator is required to compare basic interaction properties. It cannot be null");
        }
        this.interactionComparator = interactionComparator;

        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare cooperative mechanism, effect outcome and response. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;
        this.modelledInteractionCollectionComparator = new ModelledInteractionCollectionComparator(interactionComparator);
    }

    public ModelledInteractionComparator getInteractionComparator() {
        return interactionComparator;
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public ModelledInteractionCollectionComparator getModelledInteractionCollectionComparator() {
        return modelledInteractionCollectionComparator;
    }

    /**
     * It will first compare the basic interaction properties using ModelledInteractionComparator.
     * It will then compare the cooperative mechanism using AbstractCvTermComparator. If the cooperative mechanisms are the same,
     * it will compare the effect outcome using AbstractCvTermComparator. If the effect outcomes are the same, it will compare the responses using
     * AbstractCvTermComparator. If the responses are the same, it will compare the affected interactions using a ModelledInteractionComparator that does not compare
     * cooperative interaction properties (avoiding internal loop).
     * @param cooperativeInteraction1
     * @param cooperativeInteraction2
     * @return
     */
    public int compare(CooperativeInteraction cooperativeInteraction1, CooperativeInteraction cooperativeInteraction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (cooperativeInteraction1 == null && cooperativeInteraction2 == null){
            return EQUAL;
        }
        else if (cooperativeInteraction1 == null){
            return AFTER;
        }
        else if (cooperativeInteraction2 == null){
            return BEFORE;
        }
        else {
            // first compares basic properties of a modelled interaction
            int comp = interactionComparator.compare(cooperativeInteraction1, cooperativeInteraction2);
            if (comp != 0){
                return comp;
            }

            // first check cooperative mechanism
            CvTerm mechanism1 = cooperativeInteraction1.getCooperativeMechanism();
            CvTerm mechanism2 = cooperativeInteraction2.getCooperativeMechanism();

            comp = cvTermComparator.compare(mechanism1, mechanism2);
            if (comp != 0){
               return comp;
            }

            // then compare effect outcome
            CvTerm outcome1 = cooperativeInteraction1.getEffectOutCome();
            CvTerm outcome2 = cooperativeInteraction2.getEffectOutCome();

            comp = cvTermComparator.compare(outcome1, outcome2);
            if (comp != 0){
                return comp;
            }

            // then compare response
            CvTerm response1 = cooperativeInteraction1.getResponse();
            CvTerm response2 = cooperativeInteraction2.getResponse();

            comp = cvTermComparator.compare(response1, response2);
            if (comp != 0){
                return comp;
            }

            // then compares affected interactions
            Collection<ModelledInteraction> affected1 = cooperativeInteraction1.getAffectedInteractions();
            Collection<ModelledInteraction> affected2 = cooperativeInteraction2.getAffectedInteractions();

            return modelledInteractionCollectionComparator.compare(affected1, affected2);
        }
    }
}
