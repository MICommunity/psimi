package psidev.psi.mi.jami.enricher.enricherimplementation.protein;

import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.enricherimplementation.organism.MaximumOrganismUpdater;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

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

    public void enrichProtein(Protein proteinToEnrich)throws BridgeFailedException,
            MissingServiceException,
            BadToEnrichFormException,
            BadSearchTermException,
            BadResultException,
            SeguidException {

        Collection<Protein> proteinsEnriched = getFullyEnrichedForms(proteinToEnrich);
        Protein proteinEnriched = chooseProteinEnriched(proteinToEnrich, proteinsEnriched);

        if(proteinEnriched != null){
            super.setOrganismEnricher(new MaximumOrganismUpdater());
            runProteinAddition(proteinToEnrich, proteinEnriched);
            runProteinUpdateOnCore(proteinToEnrich, proteinEnriched);
            runProteinUpdateOnChecksums(proteinToEnrich, proteinEnriched);
            proteinEnricherListener.onProteinEnriched(proteinToEnrich, "Success");
        }
    }
}
