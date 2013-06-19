package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 17:05
 */
public class MinimumOrganismUpdater
        extends AbstractOrganismEnricher
        implements OrganismEnricher {


    @Override
    protected void processOrganism(Organism organismToEnrich) throws BadEnrichedFormException {
        if(organismFetched.getTaxId() < -4){//TODO check this  is a valid assertion
            throw new BadEnrichedFormException( "The organism had an invalid TaxID of "+organismFetched.getTaxId());
        }

        // Override TaxID but obviously not possible if organism is unknown
        if(organismFetched.getTaxId() != -3){

            // TaxID
            if(organismToEnrich.getTaxId() != organismFetched.getTaxId() ){
                if (listener != null) listener.onTaxidUpdate(
                        organismToEnrich, ""+organismToEnrich.getTaxId() );
                organismToEnrich.setTaxId(organismFetched.getTaxId());
            }

            // Scientific name
            if(organismFetched.getScientificName() != null
                    && ! organismFetched.getScientificName().equalsIgnoreCase(
                    organismToEnrich.getScientificName())){

                if (listener != null) listener.onScientificNameUpdate(
                        organismToEnrich , organismToEnrich.getScientificName());
                organismToEnrich.setScientificName(organismFetched.getScientificName());
            }

            // Common name
            if(organismFetched.getCommonName() != null
                    && ! organismFetched.getCommonName().equalsIgnoreCase(
                    organismToEnrich.getCommonName())){

                if (listener != null) listener.onCommonNameUpdate(
                        organismToEnrich , organismToEnrich.getCommonName());
                organismToEnrich.setCommonName(organismFetched.getCommonName());
            }
        }

    }

}
