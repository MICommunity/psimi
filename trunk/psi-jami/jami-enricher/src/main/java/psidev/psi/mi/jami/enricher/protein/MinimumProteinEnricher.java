package psidev.psi.mi.jami.enricher.protein;

//import psidev.psi.mi.jami.bridges.fetcher.echoservice.EchoOrganism;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.organism.MinimumOrganismEnricher;
import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.event.MismatchReport;
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
 * Date: 14/05/13
 * Time: 14:27
 */
public class MinimumProteinEnricher
        extends AbstractProteinEnricher
        implements ProteinEnricher {

    public MinimumProteinEnricher(){
        super();
    }

    public MinimumProteinEnricher(ProteinFetcher fetcher){
        super(fetcher);
    }

    public void enrichProtein(Protein proteinToEnrich)
            throws EnrichmentException {

        Protein proteinEnriched = getEnrichedForm(proteinToEnrich);
        enrichProtein(proteinToEnrich, proteinEnriched);
        compareProteinMismatches(proteinToEnrich, proteinEnriched);
        fireEnricherEvent(enricherEvent);
    }


    protected void enrichProtein(Protein proteinToEnrich, Protein proteinEnriched)
            throws EnrichmentException{

        //Fullname
        if(proteinToEnrich.getFullName() == null
                && proteinEnriched.getFullName() != null){
            proteinToEnrich.setFullName( proteinEnriched.getFullName() );
            addAdditionReport(new AdditionReport(
                    "Full name", proteinEnriched.getFullName()));
        }


        //PRIMARY ACCESSION
        /**
         * Currently the AC always matches.
         * This is included against a scenario where a fetcher uses a filed other than AC
         */
        if(proteinToEnrich.getUniprotkb() == null
                && proteinEnriched.getUniprotkb() != null) {
            proteinToEnrich.setUniprotkb(proteinEnriched.getUniprotkb());
            addAdditionReport(new AdditionReport("uniprotKb AC", proteinEnriched.getUniprotkb()));
        }

        //SEQUENCE
        if(proteinToEnrich.getSequence() == null
                && proteinEnriched.getSequence() != null){
            proteinToEnrich.setSequence(proteinEnriched.getSequence());
            addAdditionReport(new AdditionReport("Sequence", proteinToEnrich.getSequence()));
        }
    }

    public void compareProteinMismatches(Protein proteinToEnrich, Protein proteinEnriched){

        //Short name
        if (!proteinToEnrich.getShortName().equalsIgnoreCase(
                proteinEnriched.getShortName() )) {
            addMismatchReport(new MismatchReport(
                    "ShortName", proteinToEnrich.getShortName(), proteinEnriched.getShortName()));
        }

        //Full name
        if(proteinEnriched.getFullName() != null){
            if (!proteinToEnrich.getFullName().equalsIgnoreCase(
                    proteinEnriched.getFullName() )) {
                addMismatchReport(new MismatchReport(
                        "FullName", proteinToEnrich.getFullName(), proteinEnriched.getFullName()));
            }
        }

        //Uniprot AC
        if(proteinEnriched.getUniprotkb() != null){
            if(! proteinToEnrich.getUniprotkb().equalsIgnoreCase(
                    proteinEnriched.getUniprotkb() )){
                addMismatchReport(new MismatchReport(
                        "UniprotKB AC", proteinToEnrich.getUniprotkb(), proteinEnriched.getUniprotkb()));
            }
        }

        //Sequence
        if(proteinEnriched.getSequence() != null){
            if(! proteinToEnrich.getSequence().equalsIgnoreCase(proteinEnriched.getSequence())){
                addMismatchReport(new MismatchReport(
                        "Sequence", proteinEnriched.getSequence(), proteinToEnrich.getSequence()));
            }
        }

        /*
        //Orgnaism, // ROGID
        try{
            if(proteinEnriched.getOrganism() != null){
                MinimumOrganismEnricher minimumOrganismEnricher= new MinimumOrganismEnricher();
                minimumOrganismEnricher.setFetcher(new EchoOrganism(proteinEnriched.getOrganism()));
                minimumOrganismEnricher.addEnricherListener(new EnricherListener() {
                    public void onEnricherEvent(EnricherEvent e) {
                        addEchoSubEnricherEvent(e);
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
                            addMismatchReport(new MismatchReport("RogID", proteinToEnrich.getRogid(), rogid));
                        }
                    } catch (SeguidException e) {
                        log.debug("caught exception from a failed rogid");
                        e.printStackTrace();
                    }
                }
            }
        }catch(EnrichmentException e){
            log.warn("Caught Enrichment exception fired by organism");
        }*/
    }
}
