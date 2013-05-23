package psidev.psi.mi.jami.enricher.protein;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.fetcher.echoservice.EchoOrganism;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.organism.MinimumOrganismEnricher;
import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.event.OverwriteReport;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.listener.EnricherListener;
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

    public MaximumProteinUpdater(){
        super();
    }

    public MaximumProteinUpdater(ProteinFetcher fetcher){
        super(fetcher);
    }

    @Override
    public void enrichProtein(Protein proteinToEnrich)
            throws EnrichmentException {

        Protein proteinEnriched = getEnrichedForm(proteinToEnrich);
        if(proteinEnriched == null){
            log.debug("The enriched protein was null");
            return;
        }
        super.enrichProtein(proteinToEnrich, proteinEnriched);

        updateProteinMismatches(proteinToEnrich, proteinEnriched);

        fireEnricherEvent(enricherEvent);
    }


    protected void updateProteinMismatches(Protein proteinToEnrich, Protein proteinEnriched){

        //Short name
        if (!proteinToEnrich.getShortName().equalsIgnoreCase(
                proteinEnriched.getShortName() )) {
            String oldValue = proteinToEnrich.getShortName();
            proteinToEnrich.setShortName(proteinEnriched.getShortName());
            addOverwriteReport(new OverwriteReport(
                    "ShortName", oldValue, proteinToEnrich.getShortName()));
        }

        //Full name
        if (!proteinToEnrich.getFullName().equalsIgnoreCase(
                proteinEnriched.getFullName() )) {
            String oldValue = proteinToEnrich.getFullName();
            proteinToEnrich.setFullName(proteinEnriched.getFullName());
            addOverwriteReport(new OverwriteReport(
                    "FullName", oldValue, proteinToEnrich.getFullName()));
        }

        //Uniprot AC
        if(! proteinToEnrich.getUniprotkb().equalsIgnoreCase(
                proteinEnriched.getUniprotkb() )){
            String oldValue = proteinToEnrich.getUniprotkb() ;
            proteinToEnrich.setUniprotkb(proteinEnriched.getUniprotkb());
            addOverwriteReport(new OverwriteReport(
                    "UniprotKB AC", oldValue, proteinToEnrich.getUniprotkb()));
        }

        //Sequence
        if(! proteinToEnrich.getSequence().equalsIgnoreCase(proteinEnriched.getSequence())){
            String oldValue = proteinToEnrich.getSequence();
            proteinToEnrich.setSequence(proteinEnriched.getSequence());
            addOverwriteReport(new OverwriteReport(
                    "Sequence", oldValue, proteinEnriched.getSequence()));
        }

        /*
        //Orgnaism, // ROGID
        try{
            if(proteinEnriched.getOrganism() != null){

                MinimumOrganismEnricher minimumOrganismEnricher= new MinimumOrganismEnricher();
                minimumOrganismEnricher.setFetcher(new EchoOrganism(proteinEnriched.getOrganism()));
                minimumOrganismEnricher.addEnricherListener(new EnricherListener() {
                    public void onEnricherEvent(EnricherEvent e) {
                        addSubEnricherEvent(e);
                    }
                });

                if(proteinToEnrich.getOrganism() == null && proteinEnriched.getOrganism() != null){
                    proteinToEnrich.setOrganism(new DefaultOrganism(-3));
                }

                minimumOrganismEnricher.enrichOrganism(proteinToEnrich.getOrganism());

                if(proteinToEnrich.getOrganism().getTaxId() > 0
                        && proteinToEnrich.getSequence() != null){
                    RogidGenerator rogidGenerator = new RogidGenerator();
                    // String rogid = null;

                    try {
                        String rogid = rogidGenerator.calculateRogid(
                                proteinToEnrich.getSequence(),""+proteinToEnrich.getOrganism().getTaxId());
                        if(proteinToEnrich.getRogid() == null){
                            proteinToEnrich.setRogid(rogid);
                            addAdditionReport(new AdditionReport("RogID", rogid));
                        }
                        else if(!proteinToEnrich.getRogid().equals(rogid)){
                            String oldValue = proteinToEnrich.getRogid();
                            proteinToEnrich.setRogid(rogid);
                            addOverwriteReport(new OverwriteReport(
                                    "RogID", oldValue, proteinToEnrich.getRogid()));
                        }
                    } catch (SeguidException e) {
                        log.debug("caught exception from a failed rogid");
                        e.printStackTrace();
                    }
                }
            }
        }catch(EnrichmentException e){
            log.warn("Caught Enrichment exception fired by organism conflict");
        }  */
    }
}
