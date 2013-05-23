package psidev.psi.mi.jami.enricher.organism;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.exception.FetchingException;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessorImp;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 * Time: 10:05
 */
public abstract class AbstractOrganismEnricher
        extends EnricherEventProcessorImp
        implements OrganismEnricher {


    protected final Logger log = LoggerFactory.getLogger(AbstractOrganismEnricher.class.getName());
    private OrganismFetcher fetcher=null;
    private static final String TYPE = "Organism";

    public AbstractOrganismEnricher(){
        enricherEvent = new EnricherEvent(TYPE);
    }

    public AbstractOrganismEnricher(OrganismFetcher fetcher){
        this();
        setFetcher(fetcher);
    }

    public void setFetcher(OrganismFetcher fetcher){
        this.fetcher = fetcher;
    }
    public OrganismFetcher getFetcher(){
        return this.fetcher;
    }

    protected Organism getEnrichedForm(Organism MasterOrganism)
            throws EnrichmentException {
        if(fetcher == null) throw new FetchingException("Fetcher is null.");

        Organism enriched;
        try{
            enriched = fetcher.getOrganismByTaxID(MasterOrganism.getTaxId());
            enricherEvent.clear();
            enricherEvent.setQueryDetails(""+MasterOrganism.getTaxId(),"TaxId");
        }catch(FetcherException e){
            throw new FetchingException(e);
        }

        if(enriched==null) throw new FetchingException("Null organism");

        return enriched;
    }

}
