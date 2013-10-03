package psidev.psi.mi.enricher.batch.processor;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Spring batch processor that enriches the interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */

public class InteractionEvidenceEnricherProcessor implements ItemProcessor<InteractionEvidence, InteractionEvidence>, ItemStream{

    private Resource errorResource;
    private Writer errorWriter;
    private InteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence> interactionEnricher;

    public InteractionEvidence process(InteractionEvidence item) throws Exception {
        if (this.interactionEnricher == null){
            throw new IllegalStateException("The InteractionEvidenceEnricherProcessor needs a non null InteractionEnricher.");
        }
        if (item == null){
            return null;
        }

        // enrich interaction
        try{
            interactionEnricher.enrich(item);
        }
        catch (EnricherException e){
            String source = item.toString();
            if (item instanceof FileSourceContext){
               FileSourceContext context = (FileSourceContext) item;
                if (context.getSourceLocator() != null){
                    source = context.getSourceLocator().toString();
                }
            }
            errorWriter.write("Cannot enrich interaction " + source);
        }

        return item;
    }

    public void setInteractionEnricher(InteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence> interactionEnricher) {
        this.interactionEnricher = interactionEnricher;
    }

    public Resource getErrorResource() {
        return errorResource;
    }

    public void setErrorResource(Resource errorResource) {
        this.errorResource = errorResource;
    }

    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Assert.notNull(executionContext, "ExecutionContext must not be null");

        if (errorResource == null){
            throw new IllegalStateException("Error resource must be provided. ");
        }

        File fileToRead = null;
        try {
            fileToRead = errorResource.getFile();

            this.errorWriter = new FileWriter(fileToRead);
        } catch (IOException e) {
            throw new ItemStreamException("Error resource must be writable: "
                    + errorResource, e);
        }
    }

    public void update(ExecutionContext executionContext) throws ItemStreamException {
        try {
            errorWriter.flush();
        } catch (IOException e) {
            throw new ItemStreamException("Cannot flush Error resource: "
                    + errorResource, e);
        }
    }

    public void close() throws ItemStreamException {
        if (errorWriter != null){
            try {
                errorWriter.close();
            } catch (IOException e) {
                throw new ItemStreamException("Error resource cannot be closed: "
                        + errorResource, e);
            }
        }
    }
}
