package psidev.psi.mi.jami.enricher.impl.organism;


import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.model.Organism;

/**
 * Enriches the organism to its minimum level.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/05/13
 */
public class MinimumOrganismEnricher
        extends AbstractOrganismEnricher
        implements OrganismEnricher {


    @Override
    protected void processOrganism(Organism organismToEnrich)  {
        if(organismFetched == null) throw new IllegalArgumentException(
                "The fetched organism is null, cannot enrich" );

        //TaxID
        if(organismToEnrich.getTaxId() == -3 && organismFetched.getTaxId() != -3){
            organismToEnrich.setTaxId(organismFetched.getTaxId());
            if (listener != null) listener.onTaxidUpdate(organismToEnrich, "-3");
        }

        //TODO - check that the organism details don't enrich if there is no match on taxID
        if(organismToEnrich.getTaxId() == organismFetched.getTaxId()){
            //Scientific name
            if(organismToEnrich.getScientificName() == null
                    && organismFetched.getScientificName() != null){
                organismToEnrich.setScientificName(organismFetched.getScientificName());
                if (listener != null)
                    listener.onScientificNameUpdate(organismToEnrich , null);
            }

            //Common name
            if(organismToEnrich.getCommonName() == null
                    && organismFetched.getCommonName() != null){
                organismToEnrich.setCommonName(organismFetched.getCommonName());
                if (listener != null)
                    listener.onCommonNameUpdate(organismToEnrich , null);
            }
        }
    }
}