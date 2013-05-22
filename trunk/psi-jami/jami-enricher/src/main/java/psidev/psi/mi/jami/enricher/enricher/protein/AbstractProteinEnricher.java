package psidev.psi.mi.jami.enricher.enricher.protein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.exception.FetchingException;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessorImp;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public abstract class AbstractProteinEnricher
        extends EnricherEventProcessorImp
        implements ProteinEnricher {

    protected final Logger log = LoggerFactory.getLogger(AbstractProteinEnricher.class.getName());
    private ProteinFetcher fetcher=null;

    public AbstractProteinEnricher() throws EnrichmentException {
        /*log.debug("Starting the protein fetcher");
        try{
            fetcher = new UniprotFetcher();
        } catch (BridgeFailedException e) {
            log.warn("Protein fetcher failed on initialise.");
            throw new EnrichmentException(e);
        }*/
    }

    public void setFetcher(ProteinFetcher fetcher){
        this.fetcher = fetcher;
    }
    public ProteinFetcher getFetcher(){
        return this.fetcher;
    }

    protected Protein getEnrichedForm(Protein MasterProtein)
            throws EnrichmentException {

        if(fetcher == null) throw new FetchingException("Fetcher is null.");

        enricherEvent.clear();
        enricherEvent.setQueryDetails(MasterProtein.getUniprotkb(),"UniprotKb");

        Protein p = null;
        try{
            p = fetcher.getProteinByID(MasterProtein.getUniprotkb());
        }catch (FetcherException e) {
            new FetchingException(e);
        }

        if(p==null) throw new FetchingException("Null protein");
        return p;

    }
}
