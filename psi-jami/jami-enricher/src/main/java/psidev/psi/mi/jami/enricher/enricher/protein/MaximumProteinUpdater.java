package psidev.psi.mi.jami.enricher.enricher.protein;

import psidev.psi.mi.jami.enricher.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.MismatchReport;
import psidev.psi.mi.jami.enricher.event.OverwriteReport;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.Organism;
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
        extends MinimumProteinEnricher
        implements ProteinEnricher {

    public MaximumProteinUpdater()  throws EnrichmentException {
        super();
        log.debug("Starting the maximum protein updater");

    }

    @Override
    public void enrichProtein(Protein proteinMaster)
            throws EnrichmentException {

        Protein proteinEnriched = getEnrichedForm(proteinMaster);
        if(proteinEnriched == null){
            log.debug("The enriched protein was null");
            return;
        }
        super.enrichProtein(proteinMaster, proteinEnriched);

        updateProteinMismatches(proteinMaster, proteinEnriched);

        fireEnricherEvent(enricherEvent);
    }


    public void updateProteinMismatches(Protein proteinMaster, Protein proteinEnriched){

        //Short name
        if (!proteinMaster.getShortName().equalsIgnoreCase(
                proteinEnriched.getShortName() )) {
            String oldValue = proteinMaster.getShortName();
            proteinMaster.setShortName(proteinEnriched.getShortName());
            addOverwriteReport(new OverwriteReport(
                    "ShortName", oldValue, proteinMaster.getShortName()));
        }

        //Full name
        if (!proteinMaster.getFullName().equalsIgnoreCase(
                proteinEnriched.getFullName() )) {
            String oldValue = proteinMaster.getFullName();
            proteinMaster.setFullName(proteinEnriched.getFullName());
            addOverwriteReport(new OverwriteReport(
                    "FullName", oldValue, proteinMaster.getFullName()));
        }

        //Uniprot AC
        if(! proteinMaster.getUniprotkb().equalsIgnoreCase(
                proteinEnriched.getUniprotkb() )){
            String oldValue = proteinMaster.getUniprotkb() ;
            proteinMaster.setUniprotkb(proteinEnriched.getUniprotkb());
            addOverwriteReport(new OverwriteReport(
                    "UniprotKB AC", oldValue, proteinMaster.getUniprotkb()));
        }

        //Sequence
        if(! proteinMaster.getSequence().equalsIgnoreCase(proteinEnriched.getSequence())){
            String oldValue = proteinMaster.getSequence();
            proteinMaster.setSequence(proteinEnriched.getSequence());
            addOverwriteReport(new OverwriteReport(
                    "Sequence", oldValue, proteinEnriched.getSequence()));
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
                            addAdditionReport(new AdditionReport("RogID",rogid));
                        }
                        else if(!proteinMaster.getRogid().equals(rogid)){
                            String oldValue = proteinMaster.getRogid();
                            proteinMaster.setRogid(rogid);
                            addOverwriteReport(new OverwriteReport(
                                    "RogID",oldValue, proteinMaster.getRogid()));
                        }
                    } catch (SeguidException e) {
                        log.debug("caught exception from a failed rogid");
                        e.printStackTrace();
                    }
                }
            }
        }catch(EnrichmentException e){
            log.warn("Caught Enrichment exception fired by organism conflict");
        }




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
                    addMismatchReport(new MismatchReport(
                            "TaxID", "" + masterOrganism.getTaxId(), "" + enrichedOrganism.getTaxId()));
                    throw new EnrichmentException();
                }
            }
            if(masterOrganism.getTaxId() == -3){
                masterOrganism.setTaxId(enrichedOrganism.getTaxId());
                addAdditionReport(new AdditionReport("TaxId", "" + masterOrganism.getTaxId()));
            }

            //Scientific name
            if(enrichedOrganism.getScientificName() != null){
                if(masterOrganism.getScientificName() == null){
                    masterOrganism.setScientificName(enrichedOrganism.getScientificName());
                    addAdditionReport(new AdditionReport("Scientific name", enrichedOrganism.getScientificName()));
                } else if (!enrichedOrganism.getScientificName().equalsIgnoreCase(
                        masterOrganism.getScientificName())){
                    addMismatchReport(new MismatchReport("Scientific name", masterOrganism.getScientificName(), enrichedOrganism.getScientificName()));
                }
            }

            //Commonname
            if(enrichedOrganism.getCommonName() != null){
                if(masterOrganism.getCommonName() == null){
                    masterOrganism.setCommonName(enrichedOrganism.getCommonName());
                    addAdditionReport(new AdditionReport("Common name", masterOrganism.getCommonName()));
                } else if (!enrichedOrganism.getCommonName().equalsIgnoreCase(
                        masterOrganism.getCommonName())){
                    addMismatchReport(new MismatchReport("Common name", masterOrganism.getCommonName(), enrichedOrganism.getCommonName()));
                }
            }

        }
    }
}
