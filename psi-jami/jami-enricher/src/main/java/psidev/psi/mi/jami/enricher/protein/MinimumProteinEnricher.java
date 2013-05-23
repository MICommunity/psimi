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

    public void enrichProtein(Protein proteinMaster)
            throws EnrichmentException {

        Protein proteinEnriched = getEnrichedForm(proteinMaster);
        enrichProtein(proteinMaster, proteinEnriched);
        compareProteinMismatches(proteinMaster, proteinEnriched);
        fireEnricherEvent(enricherEvent);
    }


    protected void enrichProtein(Protein proteinMaster, Protein proteinEnriched)
            throws EnrichmentException{

        //Fullname
        if(proteinMaster.getFullName() == null
                && proteinEnriched.getFullName() != null){
            proteinMaster.setFullName( proteinEnriched.getFullName() );
            addAdditionReport(new AdditionReport(
                    "Full name", proteinEnriched.getFullName()));
        }


        //PRIMARY ACCESSION
        /**
         * Currently the AC always matches.
         * This is included against a scenario where a fetcher uses a filed other than AC
         */
        if(proteinMaster.getUniprotkb() == null
                && proteinEnriched.getUniprotkb() != null) {
            proteinMaster.setUniprotkb(proteinEnriched.getUniprotkb());
            addAdditionReport(new AdditionReport("uniprotKb AC", proteinEnriched.getUniprotkb()));
        }

        //SEQUENCE
        if(proteinMaster.getSequence() == null
                && proteinEnriched.getSequence() != null){
            proteinMaster.setSequence(proteinEnriched.getSequence());
            addAdditionReport(new AdditionReport("Sequence", proteinMaster.getSequence()));
        }
    }

    public void compareProteinMismatches(Protein proteinMaster, Protein proteinEnriched){

        //Short name
        if (!proteinMaster.getShortName().equalsIgnoreCase(
                proteinEnriched.getShortName() )) {
            addMismatchReport(new MismatchReport(
                    "ShortName", proteinMaster.getShortName(), proteinEnriched.getShortName()));
        }

        //Full name
        if(proteinEnriched.getFullName() != null){
            if (!proteinMaster.getFullName().equalsIgnoreCase(
                    proteinEnriched.getFullName() )) {
                addMismatchReport(new MismatchReport(
                        "FullName", proteinMaster.getFullName(), proteinEnriched.getFullName()));
            }
        }

        //Uniprot AC
        if(proteinEnriched.getUniprotkb() != null){
            if(! proteinMaster.getUniprotkb().equalsIgnoreCase(
                    proteinEnriched.getUniprotkb() )){
                addMismatchReport(new MismatchReport(
                        "UniprotKB AC", proteinMaster.getUniprotkb(), proteinEnriched.getUniprotkb()));
            }
        }

        //Sequence
        if(proteinEnriched.getSequence() != null){
            if(! proteinMaster.getSequence().equalsIgnoreCase(proteinEnriched.getSequence())){
                addMismatchReport(new MismatchReport(
                        "Sequence", proteinEnriched.getSequence(), proteinMaster.getSequence()));
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

                if(proteinMaster.getOrganism() == null && proteinEnriched.getOrganism() != null){
                    proteinMaster.setOrganism(new DefaultOrganism(-3));
                }
                minimumOrganismEnricher.enrichOrganism(proteinMaster.getOrganism());

                if(proteinMaster.getOrganism().getTaxId() > 0
                        && proteinMaster.getSequence() != null){
                    RogidGenerator rogidGenerator = new RogidGenerator();
                    // String rogid = null;

                    try {
                        String rogid = rogidGenerator.calculateRogid(
                                proteinMaster.getSequence(),""+proteinMaster.getOrganism().getTaxId());
                        if(proteinMaster.getRogid() == null){
                            proteinMaster.setRogid(rogid);
                            addAdditionReport(new AdditionReport("RogID", rogid));
                        }
                        else if(!proteinMaster.getRogid().equals(rogid)){
                            addMismatchReport(new MismatchReport("RogID", proteinMaster.getRogid(), rogid));
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
