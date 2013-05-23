package psidev.psi.mi.jami.enricher.protein;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.fetcher.echoservice.EchoOrganism;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.organism.MinimumOrganismEnricher;
import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.event.OverwriteReport;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import uk.ac.ebi.intact.irefindex.seguid.RogidGenerator;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

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

        Protein proteinEnriched = getFullyEnrichedForm(proteinToEnrich);
        runProteinAdditionEnrichment(proteinToEnrich, proteinEnriched);
        runProteinOverwriteUpdate(proteinToEnrich, proteinEnriched);
        fireEnricherEvent(enricherEvent);
    }
}
