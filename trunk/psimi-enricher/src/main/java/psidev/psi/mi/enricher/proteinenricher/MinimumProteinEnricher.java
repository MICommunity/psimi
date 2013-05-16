package psidev.psi.mi.enricher.proteinenricher;

import psidev.psi.mi.enricher.exception.EnrichmentException;
import psidev.psi.mi.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricherlistener.event.MismatchEvent;
import psidev.psi.mi.fetcher.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import uk.ac.ebi.intact.irefindex.seguid.RogidGenerator;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public class MinimumProteinEnricher
        extends AbstractProteinEnricher
        implements ProteinEnricher{

    public MinimumProteinEnricher()  throws BridgeFailedException {
        super();
        log.debug("Starting the protein enricher");

    }

    public void enrichProtein(Protein proteinMaster)
            throws BridgeFailedException {

        Protein proteinEnriched = getEnrichedForm(proteinMaster);
        if(proteinEnriched == null){
            log.debug("The enriched protein was null");
        }
        enrichProtein(proteinMaster, proteinEnriched);//, report);
    }


    public void enrichProtein(Protein proteinMaster, Protein proteinEnriched){
        //proteinEnriched.setShortName("test");
        //log.debug("shortname is "+proteinEnriched.getShortName());
                //Shortname
        if (!proteinMaster.getShortName().equalsIgnoreCase(
                proteinEnriched.getShortName())) {
            addMismatchEvent(new MismatchEvent(
                    "ShortName", proteinMaster.getShortName(),proteinEnriched.getShortName()));
        }

        //Fullname
        if(proteinEnriched.getFullName() != null){
            if(proteinMaster.getFullName() == null){
                proteinMaster.setFullName( proteinEnriched.getFullName() );
                addAdditionEvent(new AdditionEvent("Full name",proteinEnriched.getFullName()));
            } else if (!proteinMaster.getFullName().equalsIgnoreCase(
                    proteinEnriched.getFullName())) {
                addMismatchEvent(new MismatchEvent(
                        "FullName",proteinMaster.getFullName(),proteinEnriched.getFullName()));
            }
        }

        //PRIMARY ACCESSION
        if(proteinEnriched.getUniprotkb() != null) {
            if( proteinMaster.getUniprotkb() == null){
                proteinMaster.setUniprotkb(proteinEnriched.getUniprotkb());
                addAdditionEvent(new AdditionEvent("uniprotKb AC",proteinEnriched.getUniprotkb()));
            }
            else if(! proteinMaster.getUniprotkb().equalsIgnoreCase(
                    proteinEnriched.getUniprotkb() )){
                addMismatchEvent(new MismatchEvent(
                        "UniprotKB AC", proteinMaster.getUniprotkb(),proteinEnriched.getUniprotkb()));
            }
        }

        //SEQUENCE
        if(proteinEnriched.getSequence() != null){
            if( proteinMaster.getSequence() == null){
                proteinMaster.setSequence(proteinEnriched.getSequence());
                addAdditionEvent(new AdditionEvent("Sequence",proteinMaster.getSequence()));
            }
            else if(! proteinMaster.getSequence().equalsIgnoreCase(proteinEnriched.getSequence())){
                addMismatchEvent(new MismatchEvent(
                        "Sequence",proteinEnriched.getSequence(),proteinMaster.getSequence()));
            }
        }

        //Orgnaism, // ROGID
        try{
            if(proteinEnriched.getOrganism() != null){
                if(proteinMaster.getOrganism() == null && proteinEnriched.getOrganism() != null){
                    proteinMaster.setOrganism(new DefaultOrganism(-3));
                }
                enrichOrganism(proteinMaster.getOrganism(), proteinEnriched.getOrganism());

                if(proteinMaster.getOrganism().getTaxId() > 0
                        && proteinMaster.getSequence() != null){
                    RogidGenerator rogidGenerator = new RogidGenerator();
                   // String rogid = null;

                    try {
                        String rogid = rogidGenerator.calculateRogid(
                                proteinMaster.getSequence(),""+proteinMaster.getOrganism().getTaxId());
                        if(proteinMaster.getRogid() == null){
                            proteinMaster.setRogid(rogid);
                            addAdditionEvent(new AdditionEvent("RogID",rogid));
                        }
                        else if(!proteinMaster.getRogid().equals(rogid)){
                            addMismatchEvent(new MismatchEvent("RogID",proteinMaster.getRogid(),rogid));
                        }
                    } catch (SeguidException e) {
                        log.debug("caught exception from a failed rogid");
                        e.printStackTrace();
                    }
                }
            }
        }catch(EnrichmentException e){
            log.debug("Caught Enrichment exception fired by organism conflict");
        }


        fireEnricherEvent(enricherEvent);
    }




    public void enrichOrganism(Organism masterOrganism, Organism enrichedOrganism)
            throws EnrichmentException {

        if(masterOrganism == null && enrichedOrganism == null) return;
        if(masterOrganism == null && enrichedOrganism != null) masterOrganism = new DefaultOrganism(-3);

        if(enrichedOrganism.getTaxId() >= 0){

            //TaxID
            if(masterOrganism.getTaxId() >= 0){
                if(masterOrganism.getTaxId() != enrichedOrganism.getTaxId()) {
                    //Todo organism conflict
                    addMismatchEvent(new MismatchEvent(
                            "TaxID",""+masterOrganism.getTaxId(),""+enrichedOrganism.getTaxId()));
                    throw new EnrichmentException();
                }
            }
            if(masterOrganism.getTaxId() == -3){
                masterOrganism.setTaxId(enrichedOrganism.getTaxId());
                addAdditionEvent(new AdditionEvent("TaxId",""+masterOrganism.getTaxId()));
            }

            //Scientific name
            if(enrichedOrganism.getScientificName() != null){
                if(masterOrganism.getScientificName() == null){
                    masterOrganism.setScientificName(enrichedOrganism.getScientificName());
                    addAdditionEvent(new AdditionEvent("Scientific name",enrichedOrganism.getScientificName()));
                } else if (!enrichedOrganism.getScientificName().equalsIgnoreCase(
                        masterOrganism.getScientificName())){
                    addMismatchEvent(new MismatchEvent("Scientific name",masterOrganism.getScientificName(),enrichedOrganism.getScientificName()));
                }
            }

            //Commonname
            if(enrichedOrganism.getCommonName() != null){
                if(masterOrganism.getCommonName() == null){
                    masterOrganism.setCommonName(enrichedOrganism.getCommonName());
                    addAdditionEvent(new AdditionEvent("Common name", masterOrganism.getCommonName()));
                } else if (!enrichedOrganism.getCommonName().equalsIgnoreCase(
                        masterOrganism.getCommonName())){
                    addMismatchEvent(new MismatchEvent("Common name",masterOrganism.getCommonName(),enrichedOrganism.getCommonName()));
                }
            }

        }
    }
}
