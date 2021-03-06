package psidev.psi.mi.enricher.batch.reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.*;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import psidev.psi.mi.jami.commons.MIDataSourceOptionFactory;
import psidev.psi.mi.jami.commons.PsiJami;
import psidev.psi.mi.jami.datasource.InteractionStream;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.MIDataSourceFactory;
import psidev.psi.mi.jami.model.Interaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * The PsiJami file reader is a  spring batch reader that can read any PSI-MI files
 * containing interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */

public class PsiInteractionReader implements ItemReader<psidev.psi.mi.jami.model.Interaction>, ItemStream{

    private InteractionStream interactionDataSource;
    private int interactionCount = 0;
    private static final String COUNT_OPTION = "interaction_count";
    private Resource resource;
    private static final Log logger = LogFactory.getLog(PsiInteractionReader.class);
    private Iterator interactionIterator;

    public psidev.psi.mi.jami.model.Interaction read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (this.interactionIterator == null){
            throw new IllegalStateException("The reader must be opened before reading interactions.");
        }

        Interaction next = interactionIterator.hasNext() ? (psidev.psi.mi.jami.model.Interaction)interactionIterator.next() : null;
        this.interactionCount++;
        while (next == null && interactionIterator.hasNext()){
            next = (Interaction)interactionIterator.next();
            this.interactionCount++;
        }
        return next;
    }

    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Assert.notNull(executionContext, "ExecutionContext must not be null");

        PsiJami.initialiseAllMIDataSources();

        if (resource == null){
            throw new IllegalStateException("Input resource must be provided. ");
        }

        if (!resource.exists()) {
            logger.warn("Input resource does not exist " + resource.getDescription());
            throw new IllegalStateException("Input resource must exist: " + resource);
        }

        if (!resource.isReadable()) {
            logger.warn("Input resource is not readable " + resource.getDescription());
            throw new IllegalStateException("Input resource must be readable: "
                    + resource);
        }

        initialiseInputDataStream();

        if (this.interactionDataSource == null){
            throw new ItemStreamException("The resource " + resource.getDescription() + " is not recognized as a valid MI datasource. We expect MITAB or Psi-XML files.");
        }

        try{
            this.interactionIterator = this.interactionDataSource.getInteractionsIterator();

            // the job has been restarted, we update iterator
            if (executionContext.containsKey(COUNT_OPTION)){
                this.interactionCount = executionContext.getInt(COUNT_OPTION);

                int count = 0;
                while (count < this.interactionCount && this.interactionIterator.hasNext()){
                    this.interactionIterator.next();
                    count++;
                }
            }
        }
        catch (MIIOException e) {
            logger.error("Problem reading the input source: " + resource.getDescription(), e);
            throw new ItemStreamException("Problem reading the input source: " + resource.getDescription(), e);
        }
    }

    public void update(ExecutionContext executionContext) throws ItemStreamException {
        Assert.notNull(executionContext, "ExecutionContext must not be null");
        executionContext.put(COUNT_OPTION, interactionCount);
    }

    public void close() throws ItemStreamException {
        if (this.interactionDataSource != null){
            this.interactionDataSource.close();
        }
        this.interactionCount = 0;
        this.interactionDataSource = null;
        this.interactionIterator = null;
    }

    public void setResource(Resource source) {
        this.resource = source;
    }

    protected void initialiseInputDataStream() {
        InputStream inputStreamToAnalyse = null;
        try {
            inputStreamToAnalyse = resource.getInputStream();

            MIDataSourceFactory dataSourceFactory = MIDataSourceFactory.getInstance();
            MIDataSourceOptionFactory optionFactory = MIDataSourceOptionFactory.getInstance();

            this.interactionDataSource = dataSourceFactory.getInteractionSourceWith(optionFactory.getDefaultOptions(inputStreamToAnalyse));
        } catch (IOException e) {
            logger.warn("Input resource cannot be opened " + resource.getDescription());
            throw new ItemStreamException("Input resource must be readable: "
                    + resource, e);
        }
    }

    protected void setInteractionDataSource(InteractionStream interactionDataSource) {
        this.interactionDataSource = interactionDataSource;
    }
}
