package psidev.psi.mi.jami.enricher.enricherimplementation.protein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.event.*;
import psidev.psi.mi.jami.enricher.exception.ConflictException;
import psidev.psi.mi.jami.enricher.exception.DemergeException;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.exception.FetchingException;
import psidev.psi.mi.jami.enricher.listener.EnricherEventProcessorImp;
import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.util.CollectionUtilsExtra;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.CvTermUtils;
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
        organismEnricher.addEnricherListener(new EnricherListener() {
            public void onEnricherEvent(EnricherEvent e) {
                addSubEnricherEvent(e);
            }
        });
    }

    protected Collection<Protein> getFullyEnrichedForms(Protein ProteinToEnrich)
            throws EnrichmentException {

        if(fetcher == null) throw new FetchingException("ProteinFetcher is null.");
        if(ProteinToEnrich == null) throw new FetchingException("Attempted to enrich a null protein.");

        Collection<Protein> enriched = null;
        try{
            enriched = fetcher.getProteinsByID(ProteinToEnrich.getUniprotkb());
            enricherEvent.clear();
            enricherEvent.setQueryDetails(ProteinToEnrich.getUniprotkb(),"UniprotKB AC", fetcher.getService());
        }catch (FetcherException e) {
            throw new FetchingException(e);
        }

        if(enriched==null) throw new FetchingException("Null protein");

        return enriched;
    }

    protected Protein chooseProteinEnriched(Protein proteinToEnrich, Collection<Protein> proteinsEnriched) throws EnrichmentException {
        //TODO implement a real choice!

       // log.debug("There are "+proteinsEnriched.size()+" proteins for consideration of enrichment.");
        //Only 1 entry, return it
        if(proteinsEnriched.size() == 1) {
            for(Protein protein : proteinsEnriched){return protein;}
        }

        Collection<Protein> choice = new ArrayList<Protein>();

        //log.debug("The original is "+proteinToEnrich.getFullName()+" "+proteinToEnrich.getUniprotkb());

        if(proteinToEnrich.getOrganism() == null){
           // log.debug("Protein to enrich has no organism");
        }

        if(proteinsEnriched.size() > 1) {
            for(Protein protein : proteinsEnriched){
                if(protein.getOrganism() != null){
                   // log.debug("Protein "+protein.getFullName()+" "+protein.getUniprotkb()+" has an organism "+protein.getOrganism().getTaxId());

                    if(proteinToEnrich.getOrganism() != null
                            && protein.getOrganism().getTaxId() == proteinToEnrich.getOrganism().getTaxId()){

                        choice.add(protein);
                       // log.debug("Could use use "+protein.getFullName()+" "+protein.getUniprotkb());
                    }
                    else {
                       // log.debug("Did not use "+protein.getFullName()+" "+protein.getUniprotkb());
                    }
                }
                else {
                   // log.debug("Protein "+protein.getFullName()+" "+protein.getUniprotkb()+" does not have an organism.");
                }
            }
        }

       // log.debug("There are "+choice.size()+" proteins which match the organism of the proteinToEnrich.");

        int organism = -3;
        if(proteinToEnrich.getOrganism() != null) organism = proteinToEnrich.getOrganism().getTaxId();

        if(choice.size() == 1) {
            for(Protein protein : choice){
                enricherEvent.addRemapReport(new RemapReport("There are "+proteinsEnriched.size()+
                        " proteins returned by "+proteinToEnrich.getUniprotkb()+
                        " of which only "+protein.getUniprotkb()+" matches the organism "+organism+"."));
                return protein;
            }
        }



        throw new DemergeException("There are "+proteinsEnriched.size()+
                " proteins returned by "+proteinToEnrich.getUniprotkb()+
                " of which "+choice.size()+" match the organism "+organism+".");
    }

    protected void runProteinAdditionEnrichment(Protein proteinToEnrich, Protein proteinEnriched)
            throws EnrichmentException{

        //InteractorType
        if(proteinToEnrich.getInteractorType().getMIIdentifier() != Protein.PROTEIN_MI){
            if(proteinToEnrich.getInteractorType().getMIIdentifier() == Interactor.UNKNOWN_INTERACTOR_MI){
                proteinToEnrich.setInteractorType(CvTermUtils.createProteinInteractorType());
                addAdditionReport(new AdditionReport("InteractorType", Protein.PROTEIN));
            }
            //TODO consider if the conflict should be thrown if a polymer is provided rather than protein
            else throw new ConflictException("Expected InteractorType [Protein] "+
                    "but found ["+proteinToEnrich.getInteractorType().getShortName()+"] "+
                    "with the psi-mi id ["+proteinToEnrich.getInteractorType().getMIIdentifier()+"]");
        }

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
            addAdditionReport(new AdditionReport("Identifier", x.getId()+" in "+x.getDatabase()));
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
            addAdditionReport(new AdditionReport("Xref", x.getId()+" in "+x.getDatabase()));
        }

        //Organism -
        if(proteinEnriched.getOrganism() != null){
            if(organismEnricher == null) throw new EnrichmentException(
                    "OrganismEnricher was not provided for proteinEnricher");

            //Create a new empty organism and pass the problem over to the organismEnricher
            if(proteinToEnrich.getOrganism() == null) proteinToEnrich.setOrganism(new DefaultOrganism(-3));

            MockOrganismFetcher fetcher = new MockOrganismFetcher();
            fetcher.addNewOrganism(""+proteinToEnrich.getOrganism().getTaxId(), proteinEnriched.getOrganism());
            organismEnricher.setFetcher(fetcher);
            organismEnricher.enrichOrganism(proteinToEnrich.getOrganism());
        }


        //TODO confirm that this is the proper way to handle this scenario in all situations
        //Check that in all situations a mismatching checksum is a failure
        //CHECKSUM - CRC64
        Checksum crc64checksum = null;
        //Is there a checksum in the fetched protein
        for(Checksum c :proteinEnriched.getChecksums()){
            if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                if(crc64checksum != null) throw new FetchingException(
                        "Multiple CRC64 checksums found in the fetched protein");
                //Multiple checksums suggests an error on the fetchers part.
                crc64checksum = c;
            }
        }
        if( crc64checksum != null){
            boolean exists = false;
            for(Checksum c :proteinToEnrich.getChecksums()){
                if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                    if(!c.getValue().equals(crc64checksum.getValue())) throw new ConflictException(
                            "CRC64 checksum in the protein being enriched " +
                            "conflicts with the fetched protein CRC64.");
                    else exists = true;
                }
            }

            if(!exists){
                proteinToEnrich.getChecksums().add(crc64checksum);
                addAdditionReport(new AdditionReport(
                        "CRC64", crc64checksum.getValue()));
            }
        }

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
                    throw new ConflictException(
                            "ROGID checksum in the protein being enriched " +
                            "conflicts with the fetched protein ROGID."
                    );
                    //TODO HOW TO DEAL WITH A WRONG ROGID
                    //addMismatchReport(new MismatchReport("RogID", proteinToEnrich.getRogid(), rogid));
                }
            } catch (SeguidException e) {
                addMismatchReport(new MismatchReport(
                        "RogID", proteinToEnrich.getRogid(),"[NULL ROGID CAUSED BY SEGUID EXCEPTION]"));
                log.debug("caught exception from a failed rogid due to SeguidException");
            }
        }else if(proteinToEnrich.getRogid() != null){
            if(proteinToEnrich.getOrganism() == null) throw new ConflictException(
                    "ROGID checksum in the protein being enriched " +
                            "but unable to confirm as organism is null.");

            else if(proteinToEnrich.getOrganism().getTaxId() <= 0) throw new ConflictException(
                    "ROGID checksum in the protein being enriched " +
                            "but unable to confirm as organism taxid is not legal "+
                            "(value is ["+proteinToEnrich.getOrganism().getTaxId()+"]).");

            else if(proteinToEnrich.getSequence() == null) throw new ConflictException(
                    "ROGID checksum in the protein being enriched " +
                            "but unable to confirm it as no sequence is provided.");
        }
    }

    protected void runProteinMismatchComparison(Protein proteinToEnrich, Protein proteinEnriched){

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
    }
}
