package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 * Time: 10:16
 */
public class MinimumOrganismEnricher
        extends AbstractOrganismEnricher
        implements OrganismEnricher {


    @Override
    protected void processOrganism(Organism organismToEnrich) throws BadEnrichedFormException {
        if(organismFetched.getTaxId() < -4){//TODO check this  is a valid assertion
            throw new BadEnrichedFormException( "The organism had an invalid taxID of "+organismFetched.getTaxId());
        }

        //TaxID
        if(organismToEnrich.getTaxId() == -3){
            if (listener != null) listener.onTaxidUpdate(organismToEnrich, "-3");
            organismToEnrich.setTaxId(organismFetched.getTaxId());
        }

        //TODO - check that the organism details don't enrich if there is no match on taxid
        if(organismToEnrich.getTaxId() == organismFetched.getTaxId()){
            //Scientific name
            if(organismToEnrich.getScientificName() == null
                    && organismFetched.getScientificName() != null){
                if (listener != null) listener.onScientificNameUpdate(organismToEnrich , organismToEnrich.getScientificName());
                organismToEnrich.setScientificName(organismFetched.getScientificName());
            }

            //Commonname
            if(organismToEnrich.getCommonName() == null
                    && organismFetched.getCommonName() != null){
                if (listener != null) listener.onCommonNameUpdate(organismToEnrich , organismToEnrich.getCommonName());
                organismToEnrich.setCommonName(organismFetched.getCommonName());
            }
        }
    }
}

