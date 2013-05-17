package psidev.psi.mi.enricher.proteinenricher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricher.exception.EnrichmentException;
import psidev.psi.mi.enricherlistener.EnricherEventProcessorImp;
import psidev.psi.mi.enricherlistener.event.EnricherEvent;
import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.fetcher.uniprot.UniprotFetcher;
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
        implements ProteinEnricher{

    protected final Logger log = LoggerFactory.getLogger(AbstractProteinEnricher.class.getName());
    private ProteinFetcher fetcher=null;

    public AbstractProteinEnricher() throws EnrichmentException {
        log.debug("Starting the protein fetcher");
        try{
            fetcher = new UniprotFetcher();
        } catch (BridgeFailedException e) {
            log.warn("Protein fetcher failed on initialise.");
            throw new EnrichmentException(e);
        }
    }

    protected Protein getEnrichedForm(Protein MasterProtein) throws EnrichmentException {
        if(fetcher == null){
            log.debug("The fetcher was really null.");
            return null;
        } else {
            enricherEvent = new EnricherEvent(MasterProtein.getUniprotkb(),"UniprotKb");
            Protein p = null;
            try{
                p = fetcher.getProteinByID(MasterProtein.getUniprotkb());
            }catch (BridgeFailedException e) {
                new EnrichmentException(e);
            }

            if(p==null) throw new EnrichmentException("Null protein");
            return p;
        }
    }
}
