package psidev.psi.mi.enricher.proteinenricher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        extends EnricherEventProcessorImp {

    protected final Logger log = LoggerFactory.getLogger(AbstractProteinEnricher.class.getName());
    private ProteinFetcher fetcher=null;

    public AbstractProteinEnricher() throws BridgeFailedException {
        log.debug("Starting the fetcher");
        fetcher = new UniprotFetcher();
    }

    protected Protein getEnrichedForm(Protein MasterProtein) throws BridgeFailedException {
        if(fetcher == null){
            log.debug("The fetcher was really null.");
            return null;
        } else {
            enricherEvent = new EnricherEvent(MasterProtein.getUniprotkb(),"UniprotKb");
            return fetcher.getProteinByID(MasterProtein.getUniprotkb());
        }
    }
}
