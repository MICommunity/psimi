package psidev.psi.mi.jami.enricher.enricherimplementation.protein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.event.AdditionReport;
import psidev.psi.mi.jami.enricher.event.EnricherEvent;
import psidev.psi.mi.jami.enricher.event.MismatchReport;
import psidev.psi.mi.jami.enricher.event.OverwriteReport;
import psidev.psi.mi.jami.enricher.exception.ConflictException;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.exception.FetchingException;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessorImp;
import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.util.CollectionUtilsExtra;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;
import uk.ac.ebi.intact.irefindex.seguid.RogidGenerator;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public abstract class AbstractProteinEnricher
        extends EnricherEventProcessorImp
        implements ProteinEnricher {

    protected final Logger log = LoggerFactory.getLogger(AbstractProteinEnricher.class.getName());
    private ProteinFetcher fetcher = null;
    private static final String TYPE = "Protein";
    private OrganismEnricher organismEnricher;

    public AbstractProteinEnricher(){
        enricherEvent = new EnricherEvent(TYPE);
    }

    public AbstractProteinEnricher(ProteinFetcher fetcher){
        this();
        setFetcher(fetcher);
    }

    public void setFetcher(ProteinFetcher fetcher){
        this.fetcher = fetcher;
    }
    public ProteinFetcher getFetcher(){
        return this.fetcher;
    }

    public void setOrganismEnricher(OrganismEnricher organismEnricher){
        this.organismEnricher = organismEnricher;
        //MinimumOrganismEnricher minimumOrganismEnricher = new MinimumOrganismEnricher();

        organismEnricher.addEnricherListener(new EnricherListener() {
            public void onEnricherEvent(EnricherEvent e) {
                addEchoSubEnricherEvent(e);
            }
        });


    }

    protected Protein getFullyEnrichedForm(Protein ProteinToEnrich)
            throws EnrichmentException {

        if(fetcher == null) throw new FetchingException("ProteinFetcher is null.");

        Protein enriched = null;
        try{
            enriched = fetcher.getProteinByID(ProteinToEnrich.getUniprotkb());
            enricherEvent.clear();
            enricherEvent.setQueryDetails(ProteinToEnrich.getUniprotkb(),"UniprotKB AC");
        }catch (FetcherException e) {
            throw new FetchingException(e);
        }

        if(enriched==null) throw new FetchingException("Null protein");

        return enriched;
    }

    protected void runProteinAdditionEnrichment(Protein proteinToEnrich, Protein proteinEnriched)
            throws EnrichmentException{

        //ShortName - is never null

        //FullName
        if(proteinToEnrich.getFullName() == null
                && proteinEnriched.getFullName() != null){
            proteinToEnrich.setFullName( proteinEnriched.getFullName() );
            addAdditionReport(new AdditionReport(
                    "Full name", proteinEnriched.getFullName()));
        }


        //PRIMARY Uniprot AC
        if(proteinToEnrich.getUniprotkb() == null
                && proteinEnriched.getUniprotkb() != null) {
            proteinToEnrich.setUniprotkb(proteinEnriched.getUniprotkb());
            addAdditionReport(new AdditionReport("uniprotKb AC", proteinEnriched.getUniprotkb()));
        }


        //Sequence
        if(proteinToEnrich.getSequence() == null
                && proteinEnriched.getSequence() != null){
            proteinToEnrich.setSequence(proteinEnriched.getSequence());
            addAdditionReport(new AdditionReport("Sequence", proteinToEnrich.getSequence()));
        }

        //TODO - is this correct? Is there a scenario where 2 primary ACs are created
        //Add identifiers
        Collection<Xref> subtractedIdentifiers = CollectionUtilsExtra.comparatorSubtract(
                proteinEnriched.getIdentifiers(),
                proteinToEnrich.getIdentifiers(),
                new DefaultXrefComparator());

        for(Xref x: subtractedIdentifiers){
            proteinToEnrich.getIdentifiers().add(x);
            addAdditionReport(new AdditionReport("Identifier", x.getId()));
        }

        //Aliases
        Collection<Alias> subtractedAliases = CollectionUtilsExtra.comparatorSubtract(
                proteinEnriched.getAliases(),
                proteinToEnrich.getAliases(),
                new DefaultAliasComparator());
        for(Alias x: subtractedAliases){
            proteinToEnrich.getAliases().add(x);
            addAdditionReport(new AdditionReport("Alias", x.getName()+" in "+x.getType()));
        }

        //Xref
        Collection<Xref> subtractedXrefs = CollectionUtilsExtra.comparatorSubtract(
                proteinEnriched.getXrefs(),
                proteinToEnrich.getXrefs(),
                new DefaultXrefComparator());
        for(Xref x: subtractedXrefs){
            proteinToEnrich.getXrefs().add(x);
            addAdditionReport(new AdditionReport("Xref", x.getId()));
        }


        //TODO confirm that this is the proper way to handle this scenario in all situations
        //CHECKSUM - CRC64
        Checksum crc64checksum = null;
        for(Checksum c :proteinEnriched.getChecksums()){
            if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                if(crc64checksum != null) throw new EnrichmentException(
                        "Multiple CRC64 checksums found in the fetched protein");
                crc64checksum = c;
            }
        }
        if( crc64checksum != null){
            boolean exists = false;
            for(Checksum c :proteinEnriched.getChecksums()){
                if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                    if(!c.getValue().equals(crc64checksum.getValue())) throw new ConflictException(
                            "CRC64 checksum in the protein being enriched " +
                            "conflicts with the fetched protein.");
                    else exists = true;
                }
            }
            if(!exists){
                proteinEnriched.getChecksums().add(crc64checksum);
                addAdditionReport(new AdditionReport("CRC64", crc64checksum.getValue()));
            }
        }

        //CHECKSUM - ROGID - will be calculated at mismatch/overwrite stage

        //Organism - Create a new empty organism and pass the problem over to the organismEnricher
        if(proteinEnriched.getOrganism() != null){
            if(organismEnricher == null) throw new EnrichmentException(
                    "OrganismEnricher was not provided for proteinEnricher");

            if(proteinToEnrich.getOrganism() == null) proteinToEnrich.setOrganism(new DefaultOrganism(-3));

            MockOrganismFetcher fetcher = new MockOrganismFetcher();
            fetcher.addNewOrganism(""+proteinToEnrich.getOrganism().getTaxId(), proteinEnriched.getOrganism());
            organismEnricher.setFetcher(fetcher);
            organismEnricher.enrichOrganism(proteinToEnrich.getOrganism());
        }
    }

    public void runProteinMismatchComparison(Protein proteinToEnrich, Protein proteinEnriched){

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

        //Organism - is done at addition

        //Checksum -RogID
        if(proteinToEnrich.getOrganism() != null
                && proteinToEnrich.getOrganism().getTaxId() > 0
                && proteinToEnrich.getSequence() != null){

            RogidGenerator rogidGenerator = new RogidGenerator();

            try {
                String rogid = rogidGenerator.calculateRogid(
                        proteinToEnrich.getSequence(),""+proteinToEnrich.getOrganism().getTaxId());
                if(proteinToEnrich.getRogid() == null){
                    proteinToEnrich.setRogid(rogid);
                    addAdditionReport(new AdditionReport("RogID", rogid));
                }
                else if(!proteinToEnrich.getRogid().equals(rogid)){
                    //TODO HOW TO DEAL WITH A WRONG ROGID
                    addMismatchReport(new MismatchReport("RogID", proteinToEnrich.getRogid(), rogid));
                }
            } catch (SeguidException e) {
                addMismatchReport(new MismatchReport("RogID", proteinToEnrich.getRogid(),"[NULL ROGID]"));
                log.debug("caught exception from a failed rogid");
                //e.printStackTrace();
            }
        }
    }

    protected void runProteinOverwriteUpdate(Protein proteinToEnrich, Protein proteinEnriched){

        //ShortName - is never null
        if (!proteinToEnrich.getShortName().equalsIgnoreCase(
                proteinEnriched.getShortName() )) {
            String oldValue = proteinToEnrich.getShortName();
            proteinToEnrich.setShortName(proteinEnriched.getShortName());
            addOverwriteReport(new OverwriteReport(
                    "ShortName", oldValue, proteinToEnrich.getShortName()));
        }

        //Full name
        if(proteinEnriched.getFullName() != null){
            if (!proteinToEnrich.getFullName().equalsIgnoreCase(
                    proteinEnriched.getFullName() )) {
                String oldValue = proteinToEnrich.getFullName();
                proteinToEnrich.setFullName(proteinEnriched.getFullName());
                addOverwriteReport(new OverwriteReport(
                        "FullName", oldValue, proteinToEnrich.getFullName()));
            }
        }

        //PRIMARY Uniprot AC
        if(proteinEnriched.getUniprotkb() != null){
            if(! proteinToEnrich.getUniprotkb().equalsIgnoreCase(
                    proteinEnriched.getUniprotkb() )){
                String oldValue = proteinToEnrich.getUniprotkb() ;
                proteinToEnrich.setUniprotkb(proteinEnriched.getUniprotkb());
                addOverwriteReport(new OverwriteReport(
                        "UniprotKB AC", oldValue, proteinToEnrich.getUniprotkb()));
            }
        }

        //Sequence
        if(proteinEnriched.getSequence() != null){
            if(! proteinToEnrich.getSequence().equalsIgnoreCase(proteinEnriched.getSequence())){
                String oldValue = proteinToEnrich.getSequence();
                proteinToEnrich.setSequence(proteinEnriched.getSequence());
                addOverwriteReport(new OverwriteReport(
                        "Sequence", oldValue, proteinEnriched.getSequence()));
            }
        }

        //Organism is done at addition

        //RogID
        if(proteinToEnrich.getOrganism() != null
                && proteinToEnrich.getOrganism().getTaxId() > 0
                && proteinToEnrich.getSequence() != null){

            RogidGenerator rogidGenerator = new RogidGenerator();

            try {
                String rogid = rogidGenerator.calculateRogid(
                        proteinToEnrich.getSequence(),""+proteinToEnrich.getOrganism().getTaxId());
                if(proteinToEnrich.getRogid() == null){
                    proteinToEnrich.setRogid(rogid);
                    addAdditionReport(new AdditionReport("RogID", rogid));
                }
                else if(!proteinToEnrich.getRogid().equals(rogid)){
                    //TODO HOW TO DEAL WITH A WRONG ROGID
                    String oldValue =  proteinToEnrich.getRogid();
                    proteinToEnrich.setRogid(rogid);
                    addOverwriteReport(new OverwriteReport("RogID", oldValue, proteinToEnrich.getRogid()));
                }
            } catch (SeguidException e) {
                addMismatchReport(new MismatchReport("RogID", proteinToEnrich.getRogid(),"[NULL ROGID]"));
                log.debug("Caught exception from a failed RogID");
                //e.printStackTrace();
            }
        }
    }
}
