package psidev.psi.mi.jami.enricher.enricherimplementation.protein;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.enricherimplementation.organism.MaximumOrganismUpdater;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 20/05/13
 * Time: 09:42
 */
public class MaximumProteinUpdater
        extends AbstractProteinEnricher
        implements ProteinEnricher {

    public MaximumProteinUpdater(){
        super();
    }

    public MaximumProteinUpdater(ProteinFetcher fetcher){
        super(fetcher);
    }

    public void enrichProtein(Protein proteinToEnrich)
            throws EnrichmentException {

        Collection<Protein> proteinsEnriched = getFullyEnrichedForms(proteinToEnrich);
        Protein proteinEnriched = chooseProteinEnriched(proteinsEnriched);

        super.setOrganismEnricher(new MaximumOrganismUpdater());
        runProteinAdditionEnrichment(proteinToEnrich, proteinEnriched);
        runProteinOverwriteUpdate(proteinToEnrich, proteinEnriched);
        fireEnricherEvent(enricherEvent);
    }
}
