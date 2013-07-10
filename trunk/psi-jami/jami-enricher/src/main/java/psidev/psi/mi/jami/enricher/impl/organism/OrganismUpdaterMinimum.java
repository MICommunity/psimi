package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 17:05
 */
public class OrganismUpdaterMinimum
        extends AbstractOrganismEnricher
        implements OrganismEnricher {


    @Override
    protected void processOrganism(Organism organismToEnrich) {
        // Only enrich if an organism was fetched
        if(organismFetched.getTaxId() != -3){

            // TaxID
            if(organismToEnrich.getTaxId() != organismFetched.getTaxId() ){

                String oldValue = ""+organismToEnrich.getTaxId();
                organismToEnrich.setTaxId(organismFetched.getTaxId());
                if (listener != null)
                    listener.onTaxidUpdate(organismToEnrich, oldValue );
            }

            // Scientific name
            if(organismFetched.getScientificName() != null
                    && ! organismFetched.getScientificName().equalsIgnoreCase(
                    organismToEnrich.getScientificName())){

                String oldValue = organismToEnrich.getScientificName();
                organismToEnrich.setScientificName(organismFetched.getScientificName());
                if (listener != null)
                    listener.onScientificNameUpdate(organismToEnrich , oldValue);
            }

            // Common name
            if(organismFetched.getCommonName() != null
                    && ! organismFetched.getCommonName().equalsIgnoreCase(
                    organismToEnrich.getCommonName())){

                String oldValue = organismToEnrich.getCommonName();
                organismToEnrich.setCommonName(organismFetched.getCommonName());
                if (listener != null)
                    listener.onCommonNameUpdate(organismToEnrich , oldValue);
            }
        }

    }

}
