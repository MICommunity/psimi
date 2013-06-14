package psidev.psi.mi.jami.enricher.impl.protein;

import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.organism.MaximumOrganismUpdater;
import psidev.psi.mi.jami.enricher.impl.organism.MinimumOrganismUpdater;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 20/05/13
 * Time: 09:42
 */
public class MaximumProteinUpdater
        extends MinimumProteinUpdater
        implements ProteinEnricher {



    @Override
    protected void processProtein(Protein proteinToEnrich) {
        super.processProtein(proteinToEnrich);




    }


    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new MaximumOrganismUpdater();
            organismEnricher.setFetcher(new MockOrganismFetcher());
        }

        return organismEnricher;
    }

    /*public MaximumProteinUpdater(ProteinFetcher fetcher){
        super(fetcher);
    }

    public void enrichProtein(Protein proteinToEnrich)throws BridgeFailedException,
            MissingServiceException,
            BadToEnrichFormException,
            BadSearchTermException,
            BadResultException,
            SeguidException {

        Collection<Protein> proteinsEnriched = getFullyEnrichedForms(proteinToEnrich);
        Protein proteinEnriched = chooseProteinEnriched(proteinToEnrich, proteinsEnriched);

        if(proteinEnriched != null){
            super.setOrganismEnricher(new MaximumOrganismUpdaterOLD());
            runAdditionOnCore(proteinToEnrich, proteinEnriched);
            runUpdateOnCore(proteinToEnrich, proteinEnriched);
            runUpdateOnChecksums(proteinToEnrich, proteinEnriched);
            listener.onProteinEnriched(proteinToEnrich, "Success");
        }
    } */
}
