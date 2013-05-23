package psidev.psi.mi.jami.enricher.protein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
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
    private ProteinFetcher fetcher = null;
    private static final String TYPE = "Protein";

    public AbstractProteinEnricher(){
        enricherEvent = new EnricherEvent(TYPE);
    }

    public AbstractProteinEnricher(ProteinFetcher fetcher){
        this();
        setFetcher(fetcher);
    }


    public void setFetcher(ProteinFetcher fetcher){
        this.fetcher = fetcher;
    }
    public ProteinFetcher getFetcher(){
        return this.fetcher;
    }

    protected Protein getEnrichedForm(Protein MasterProtein)
            throws EnrichmentException {
        if(fetcher == null) throw new FetchingException("ProteinFetcher is null.");

        Protein enriched = null;
        try{
            enriched = fetcher.getProteinByID(MasterProtein.getUniprotkb());
            enricherEvent.clear();
            enricherEvent.setQueryDetails(MasterProtein.getUniprotkb(),"UniprotKb");
        }catch (FetcherException e) {
            new FetchingException(e);
        }

        if(enriched==null) throw new FetchingException("Null protein");

        return enriched;

    }
}
