package psidev.psi.mi.enricher.batch.processor;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.util.Assert;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

/**
 * Spring batch processor that enriches a mix of interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */

public class InteractionCompositeEnricherProcessor implements ItemProcessor<Interaction, Interaction>, ItemStream{

    private InteractionEnricherProcessor interactionDelegate;
    private InteractionEnricherProcessor interactionEvidenceDelegate;
    private InteractionEnricherProcessor modelledInteractionDelegate;

    public Interaction process(Interaction item) throws Exception {
        if (this.interactionDelegate == null){
            throw new IllegalStateException("The InteractionEnricherProcessor needs a non null InteractionEnricher.");
        }
        if (item == null){
            return null;
        }

        // enrich interaction
        if (this.interactionEvidenceDelegate == null && this.modelledInteractionDelegate == null){
            return this.interactionDelegate.process(item);
        }
        else if (this.interactionEvidenceDelegate != null && item instanceof InteractionEvidence){
            return this.interactionEvidenceDelegate.process(item);
        }
        else if (this.modelledInteractionDelegate != null && item instanceof ModelledInteraction){
            return this.modelledInteractionDelegate.process(item);
        }
        else{
            return this.interactionDelegate.process(item);
        }
    }

    public void setInteractionDelegate(InteractionEnricherProcessor interactionDelegate) {
        this.interactionDelegate = interactionDelegate;
    }

    public void setInteractionEvidenceDelegate(InteractionEnricherProcessor interactionEvidenceDelegate) {
        this.interactionEvidenceDelegate = interactionEvidenceDelegate;
    }

    public void setModelledInteractionDelegate(InteractionEnricherProcessor modelledInteractionDelegate) {
        this.modelledInteractionDelegate = modelledInteractionDelegate;
    }

    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Assert.notNull(executionContext, "ExecutionContext must not be null");

        if (interactionDelegate != null){
            try {
                interactionDelegate.open(executionContext);
            } catch (ItemStreamException e) {
                throw new ItemStreamException("Error resource cannot be closed: ", e);
            }
        }
        if (interactionEvidenceDelegate != null){
            try {
                interactionEvidenceDelegate.open(executionContext);
            } catch (ItemStreamException e) {
                throw new ItemStreamException("Error resource cannot be closed: ", e);
            }
        }
        if (modelledInteractionDelegate != null){
            try {
                modelledInteractionDelegate.open(executionContext);
            } catch (ItemStreamException e) {
                throw new ItemStreamException("Error resource cannot be closed: ", e);
            }
        }
    }

    public void update(ExecutionContext executionContext) throws ItemStreamException {
        if (interactionDelegate != null){
            try {
                interactionDelegate.update(executionContext);
            } catch (ItemStreamException e) {
                throw new ItemStreamException("Error resource cannot be closed: ", e);
            }
        }
        if (interactionEvidenceDelegate != null){
            try {
                interactionEvidenceDelegate.update(executionContext);
            } catch (ItemStreamException e) {
                throw new ItemStreamException("Error resource cannot be closed: ", e);
            }
        }
        if (modelledInteractionDelegate != null){
            try {
                modelledInteractionDelegate.update(executionContext);
            } catch (ItemStreamException e) {
                throw new ItemStreamException("Error resource cannot be closed: ", e);
            }
        }
    }

    public void close() throws ItemStreamException {
        if (interactionDelegate != null){
            try {
                interactionDelegate.close();
            } catch (ItemStreamException e) {
                throw new ItemStreamException("Error resource cannot be closed: ", e);
            }
        }
        if (interactionEvidenceDelegate != null){
            try {
                interactionEvidenceDelegate.close();
            } catch (ItemStreamException e) {
                throw new ItemStreamException("Error resource cannot be closed: ", e);
            }
        }
        if (modelledInteractionDelegate != null){
            try {
                modelledInteractionDelegate.close();
            } catch (ItemStreamException e) {
                throw new ItemStreamException("Error resource cannot be closed: ", e);
            }
        }
    }
}
