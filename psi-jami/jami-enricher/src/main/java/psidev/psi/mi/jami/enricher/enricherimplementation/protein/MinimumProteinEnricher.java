package psidev.psi.mi.jami.enricher.enricherimplementation.protein;

//import psidev.psi.mi.jami.bridges.fetcher.echoservice.EchoOrganism;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.enricherimplementation.organism.MinimumOrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public class MinimumProteinEnricher
        extends AbstractProteinEnricher
        implements ProteinEnricher {

    public MinimumProteinEnricher(){
        super();
    }

    public MinimumProteinEnricher(ProteinFetcher fetcher){
        super(fetcher);
    }

    public void enrichProtein(Protein proteinToEnrich)
            throws EnrichmentException {

        Protein proteinEnriched = getFullyEnrichedForm(proteinToEnrich);
        super.setOrganismEnricher(new MinimumOrganismEnricher());
        runProteinAdditionEnrichment(proteinToEnrich, proteinEnriched);
        runProteinMismatchComparison(proteinToEnrich, proteinEnriched);
        fireEnricherEvent(enricherEvent);
    }
}
