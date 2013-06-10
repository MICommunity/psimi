package psidev.psi.mi.jami.enricher.enricherimplementation.protein;

//import psidev.psi.mi.jami.bridges.fetcher.echoservice.EchoOrganism;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.enricherimplementation.organism.MinimumOrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

import java.util.Collection;

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
        try {
            Collection<Protein> proteinsEnriched = null;
            proteinsEnriched = getFullyEnrichedForms(proteinToEnrich);
            Protein proteinEnriched = chooseProteinEnriched(proteinToEnrich, proteinsEnriched);


            super.setOrganismEnricher(new MinimumOrganismEnricher());

            runProteinAddition(proteinToEnrich, proteinEnriched);
            runProteinMismatchOnCore(proteinToEnrich, proteinEnriched);
            runProteinMismatchOnChecksum(proteinToEnrich, proteinEnriched);
            fireAllReportEvents();
        } catch (FetcherException e) {
            e.printStackTrace();
        } catch (SeguidException e) {
            e.printStackTrace();
        }
    }
}
