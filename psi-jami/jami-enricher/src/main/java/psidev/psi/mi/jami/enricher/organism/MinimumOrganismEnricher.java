package psidev.psi.mi.jami.enricher.organism;

import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.MismatchReport;
import psidev.psi.mi.jami.enricher.exception.ConflictException;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.exception.FetchingException;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 22/05/13
 * Time: 10:16
 */
public class MinimumOrganismEnricher
        extends AbstractOrganismEnricher
        implements OrganismEnricher {

    public MinimumOrganismEnricher()
            throws EnrichmentException{
        super();
    }

    public void enrichOrganism(Organism organismMaster)
            throws EnrichmentException {

        Organism organismEnriched = getEnrichedForm(organismMaster);

        enrichOrganism(organismMaster, organismEnriched);
        compareOrganismMismatches(organismMaster, organismEnriched);
        fireEnricherEvent(enricherEvent);
    }

    protected void enrichOrganism(Organism organismMaster, Organism organismEnriched)
            throws EnrichmentException {

        //if(organismMaster == null && organismEnriched == null) return; //Todo
        //if(organismMaster == null && organismEnriched != null) organismMaster = new DefaultOrganism(-3);

        if(organismEnriched.getTaxId() < -4){//TODO check this  is a valid assertion
            throw new FetchingException(
                    "The organism had an invalid taxID of "+organismEnriched.getTaxId());
        }else{
            //TaxID
            if(organismMaster.getTaxId() > 0){
                if(organismMaster.getTaxId() != organismEnriched.getTaxId()) {

                    throw new ConflictException("Organism Conflict on TaxId. " +
                            "Master had ["+organismMaster.getTaxId()+"] " +
                            "but fetcher found ["+organismEnriched.getTaxId());
                }
            }else if(organismMaster.getTaxId() == -3){
                organismMaster.setTaxId(organismEnriched.getTaxId());
                addAdditionReport(new AdditionReport("TaxId",""+organismMaster.getTaxId()));
            }
            //negative taxids will continue through at this point

            //Scientific name
            if(organismMaster.getScientificName() == null
                    && organismEnriched.getScientificName() != null){
                organismMaster.setScientificName(organismEnriched.getScientificName());
                addAdditionReport(new AdditionReport(
                        "Scientific name",organismEnriched.getScientificName()));
            }

            //Commonname
            if(organismMaster.getCommonName() == null
                    &&organismEnriched.getCommonName() != null){
                organismMaster.setCommonName(organismEnriched.getCommonName());
                addAdditionReport(new AdditionReport(
                        "Common name", organismMaster.getCommonName()));
            }
        }
    }

    protected void compareOrganismMismatches(Organism organismMaster, Organism organismEnriched){

        if(organismEnriched.getTaxId() != organismMaster.getTaxId()){
            addMismatchReport(new MismatchReport(
                    "TaxID",
                    ""+organismMaster.getTaxId(),
                    ""+organismEnriched.getTaxId()));
        }

        if (!organismEnriched.getScientificName().equalsIgnoreCase(
                organismMaster.getScientificName())){
            addMismatchReport(new MismatchReport(
                    "Scientific name",
                    organismMaster.getScientificName(),
                    organismEnriched.getScientificName()));
        }

        if (!organismEnriched.getCommonName().equalsIgnoreCase(
                organismMaster.getCommonName())){
            addMismatchReport(new MismatchReport(
                    "Common name",
                    organismMaster.getCommonName(),
                    organismEnriched.getCommonName()));
        }
    }
}

