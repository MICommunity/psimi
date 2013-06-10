package psidev.psi.mi.jami.enricher.enricherimplementation.protein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.FetcherException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.event.ConflictEvent;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.event.ErrorEvent;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.event.QueryDetails;
import psidev.psi.mi.jami.enricher.enricherimplementation.protein.eventprocessor.ProteinEventProcessor;
import psidev.psi.mi.jami.enricher.exception.DemergeException;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.enricher.exception.FetchingException;
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
 * Author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public abstract class AbstractProteinEnricher
        //extends EnricherEventProcessorImp
        extends ProteinEventProcessor
        implements ProteinEnricher {

    protected final Logger log = LoggerFactory.getLogger(AbstractProteinEnricher.class.getName());
    private ProteinFetcher fetcher = null;
    private OrganismEnricher organismEnricher;
    public static final String TYPE = "Protein";

    public static final String FIELD_INTERACTORTYPE = "InteractorType";
    public static final String FIELD_FULLNAME = "FullName";
    public static final String FIELD_SHORTNAME = "ShortName";
    public static final String FIELD_UNIPROTKBID = "UniportKbID";
    public static final String FIELD_SEQUENCE = "Sequence";
    public static final String FIELD_IDENTIFIER = "Identifier";
    public static final String FIELD_ALIAS = "Alias";
    public static final String FIELD_XREF = "Xref";
    public static final String FIELD_CHECKSUM = "Checksum";

    private MockOrganismFetcher mockOrganismFetcher = new MockOrganismFetcher();

    public AbstractProteinEnricher(){
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

    /**
     *
     * If the provided enricher has no fetcher, a mock fetcher will be provided.
     * @param organismEnricher
     */
    public void setOrganismEnricher(OrganismEnricher organismEnricher){
        this.organismEnricher = organismEnricher;
        if(organismEnricher.getFetcher() == null) organismEnricher.setFetcher(mockOrganismFetcher);

        /*organismEnricher.addEnricherListener(new EnricherListener() {
            public void onEnricherEvent(EnricherEvent e) {
                addSubEnricherEvent(e);
            }
        }); */
    }


    /**
     * The first step in all enrichment is to find appropriate proteins to enrich from.
     *
     * @param ProteinToEnrich
     * @return
     * @throws EnrichmentException
     */
    protected Collection<Protein> getFullyEnrichedForms(Protein ProteinToEnrich)
            throws EnrichmentException, FetcherException {

        if(fetcher == null) throw new FetchingException("ProteinFetcher is null.");
        //TODO - is this an exception?
        if(ProteinToEnrich == null) throw new FetchingException("Attempted to enrich a null protein.");
        //if(ProteinToEnrich == null) return null;

        Collection<Protein> enriched = null;
        enriched = fetcher.getProteinsByID(ProteinToEnrich.getUniprotkb());
        queryDetails = new QueryDetails(
                TYPE, ProteinToEnrich.getUniprotkb(),
                FIELD_UNIPROTKBID, fetcher.getService(), ProteinToEnrich);

        return enriched;
    }

    protected Protein chooseProteinEnriched(Protein proteinToEnrich, Collection<Protein> proteinsEnriched)
            throws EnrichmentException {
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
                /*enricherEvent.addRemapReport(new RemapReport("There are "+proteinsEnriched.size()+
                        " proteins returned by "+proteinToEnrich.getUniprotkb()+
                        " of which only "+protein.getUniprotkb()+" matches the organism "+organism+"."));
                return protein;   */
            }
        }

        throw new DemergeException("There are "+proteinsEnriched.size()+
                " proteins returned by "+proteinToEnrich.getUniprotkb()+
                " of which "+choice.size()+" match the organism "+organism+".");
    }

    /**
     * Adds all single-entry fields which are empty and appends values for any multi-entry fields.
     *
     * The following are added if empty: InteractorType, FullName, UniprotID, Sequence.
     * The following are appended to: Identifiers, Aliases, Xrefs.
     *
     * In both cases, the addition is added to the addedEvent which is then fired at the end of the method.
     *
     * The organismEnricher is also called in this method.
     *
     * The interactorType will fire a Conflict event if it is an unexpected type.
     *
     * @param proteinToEnrich
     * @param proteinEnriched
     * @throws EnrichmentException
     * @throws SeguidException
     */
    protected void runProteinAddition(Protein proteinToEnrich, Protein proteinEnriched)
            throws EnrichmentException, SeguidException {

        //InteractorType
        if(!proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Protein.PROTEIN_MI)){
            if(proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR_MI)){
                proteinToEnrich.setInteractorType(CvTermUtils.createProteinInteractorType());
                addedEvent.addElement(FIELD_INTERACTORTYPE, Protein.PROTEIN);
            }
            else fireConflictEvent(new ConflictEvent(FIELD_INTERACTORTYPE, Protein.PROTEIN, proteinToEnrich, "Expected InteractorType [Protein] " +
                    "but found [" + proteinToEnrich.getInteractorType().getShortName() + "] " +
                    "with the psi-mi id [" + proteinToEnrich.getInteractorType().getMIIdentifier() + "]"));
        }

        //ShortName - is never null

        //FullName
        if(proteinToEnrich.getFullName() == null
                && proteinEnriched.getFullName() != null){
            proteinToEnrich.setFullName( proteinEnriched.getFullName() );
            addedEvent.addElement(FIELD_FULLNAME, proteinEnriched.getFullName());
        }


        //PRIMARY Uniprot AC
        if(proteinToEnrich.getUniprotkb() == null
                && proteinEnriched.getUniprotkb() != null) {
            proteinToEnrich.setUniprotkb(proteinEnriched.getUniprotkb());
            addedEvent.addElement(FIELD_UNIPROTKBID, proteinEnriched.getUniprotkb());
        }


        //Sequence
        if(proteinToEnrich.getSequence() == null
                && proteinEnriched.getSequence() != null){
            proteinToEnrich.setSequence(proteinEnriched.getSequence());
            addedEvent.addElement(FIELD_SEQUENCE, proteinToEnrich.getSequence());
        }

        //TODO - is this correct? Is there a scenario where 2 primary ACs are created
        //Add identifiers
        Collection<Xref> subtractedIdentifiers = CollectionUtilsExtra.comparatorSubtract(
                proteinEnriched.getIdentifiers(),
                proteinToEnrich.getIdentifiers(),
                new DefaultXrefComparator());
        for(Xref xref: subtractedIdentifiers){
            proteinToEnrich.getIdentifiers().add(xref);
            addedEvent.addElement(FIELD_IDENTIFIER, xref);
        }

        //Aliases
        Collection<Alias> subtractedAliases = CollectionUtilsExtra.comparatorSubtract(
                proteinEnriched.getAliases(),
                proteinToEnrich.getAliases(),
                new DefaultAliasComparator());
        for(Alias alias: subtractedAliases){
            proteinToEnrich.getAliases().add(alias);
            addedEvent.addElement(FIELD_ALIAS, alias);
        }

        //Xref
        Collection<Xref> subtractedXrefs = CollectionUtilsExtra.comparatorSubtract(
                proteinEnriched.getXrefs(),
                proteinToEnrich.getXrefs(),
                new DefaultXrefComparator());
        for(Xref xref: subtractedXrefs){
            proteinToEnrich.getXrefs().add(xref);
            addedEvent.addElement(FIELD_XREF, xref);
        }

        //Organism -
        if(proteinEnriched.getOrganism() != null){
            if(organismEnricher == null) throw new EnrichmentException(
                    "OrganismEnricher was not provided for proteinEnricher");

            //Create a new empty organism and pass the problem over to the organismEnricher
            if(proteinToEnrich.getOrganism() == null) proteinToEnrich.setOrganism(new DefaultOrganism(-3));


            mockOrganismFetcher.clearOrganisms();
            mockOrganismFetcher.addNewOrganism(
                    ""+proteinToEnrich.getOrganism().getTaxId(), proteinEnriched.getOrganism());
            organismEnricher.enrichOrganism(proteinToEnrich.getOrganism());
        }
    }


    /**
     * Check for conflicts on all fields which take a single entry with the 'Enriched' value.
     *
     * This method will check for conflicts in: shortName, fullName, UniprotKbId and sequence.
     * In each case a check will be made to ensure that the value is not null,
     * and that the values are different.
     *
     * For each conflict found, a conflictEvent object will be fired immediately.
     * @param proteinToEnrich
     * @param proteinEnriched
     */
    protected void runProteinMismatchOnCore(Protein proteinToEnrich, Protein proteinEnriched){

        //Short name
        if (!proteinToEnrich.getShortName().equalsIgnoreCase(
                proteinEnriched.getShortName() )) {
            fireConflictEvent( new ConflictEvent(
                    FIELD_SHORTNAME, proteinEnriched.getShortName(), proteinToEnrich));
        }

        //Full name
        if(proteinEnriched.getFullName() != null){
            if (!proteinToEnrich.getFullName().equalsIgnoreCase(
                    proteinEnriched.getFullName() )) {
                fireConflictEvent(new ConflictEvent(
                        FIELD_FULLNAME, proteinEnriched.getFullName(), proteinToEnrich));
            }
        }

        //Uniprot AC
        if(proteinEnriched.getUniprotkb() != null){
            if(! proteinToEnrich.getUniprotkb().equalsIgnoreCase(
                    proteinEnriched.getUniprotkb() )){
                fireConflictEvent(new ConflictEvent(
                        FIELD_UNIPROTKBID, proteinEnriched.getUniprotkb(), proteinToEnrich));
            }
        }

        //Sequence
        if(proteinEnriched.getSequence() != null){
            if(! proteinToEnrich.getSequence().equalsIgnoreCase(proteinEnriched.getSequence())){
                fireConflictEvent(new ConflictEvent(
                        FIELD_SEQUENCE, proteinToEnrich.getSequence(), proteinEnriched));
            }
        }
    }

    /**
     * Checks the Checksums (CRC64 and ROGID) and fires a conflict if one is present, otherwise the checksum is added.
     *
     * For each checksum type, it is confirmed that only one is present in the Enriched form,
     * if this is so, the checksum is compared to the proteinToEnrich.
     * If there is already a checksum of a different value, a conflict is fired.
     * Else, the checksum will be added and the details added to the additionEvent.
     *
     * @param proteinToEnrich
     * @param proteinEnriched
     * @throws SeguidException
     */
    protected void runProteinMismatchOnChecksum(Protein proteinToEnrich, Protein proteinEnriched) throws SeguidException {

        //CHECKSUM - CRC64
        Checksum crc64checksum = null;
        //Is there a checksum in the fetched protein
        for(Checksum c :proteinEnriched.getChecksums()){
            if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                if(crc64checksum != null){
                    if(! crc64checksum.getValue().equals(c.getValue())){
                        // If multiple, non identical CRC64 checksums have been found,
                        // Fire an event, tell the logger if it's debugging
                        // then clear the place holder and break.
                        fireErrorEvent(new ErrorEvent(
                                ErrorEvent.ERROR_CONFLICT, FIELD_CHECKSUM,
                                "Multiple CRC64 checksums found in the fetched protein."));
                        if(log.isDebugEnabled()) log.debug(
                                "Multiple different CRC64 checksums were found in fetched protein, " +
                                        "please check code.");
                        crc64checksum = null;
                        break;
                    }else if(log.isDebugEnabled()) log.debug(
                            "Multiple identical CRC64 checksums were found fetched protein, " +
                                    "please check code.");
                } else {
                    crc64checksum = c;
                }
            }
        }
        if( crc64checksum != null){
            boolean exists = false;
            for(Checksum c :proteinToEnrich.getChecksums()){
                if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                    // If there is already a CRC64 in a conflict check,
                    // there is either a conflict or it is already there,
                    // so exists is true and no addition will be performed.
                    exists = true;
                    if(!c.getValue().equals(crc64checksum.getValue())){
                        fireConflictEvent(new ConflictEvent(
                            FIELD_CHECKSUM, crc64checksum, proteinToEnrich));
                    }
                }
            }
            if(!exists){
                proteinToEnrich.getChecksums().add(crc64checksum);
                addedEvent.addElement(FIELD_CHECKSUM, crc64checksum);
            }
        }

        //Checksum -RogID
        if(proteinToEnrich.getOrganism() != null
                && proteinToEnrich.getOrganism().getTaxId() != -3
                && proteinToEnrich.getSequence() != null){

            RogidGenerator rogidGenerator = new RogidGenerator();
            String rogid = rogidGenerator.calculateRogid(
                    proteinToEnrich.getSequence(),""+proteinToEnrich.getOrganism().getTaxId());
            if(proteinToEnrich.getRogid() == null){
                proteinToEnrich.setRogid(rogid);
                addedEvent.addElement(FIELD_CHECKSUM,proteinToEnrich.getRogid());
            }
            else if(!proteinToEnrich.getRogid().equals(rogid)){
                fireConflictEvent(new ConflictEvent(
                    FIELD_CHECKSUM, rogid, proteinToEnrich));
            }
            //TODO - should any kind of warning be raised if the ROGID cannot be calculated?
            //Does the enricher provide any degree of error catching?
        }
    }



    /**
     * Update all fields which take a single entry with the 'Enriched' value.
     *
     * This method will update: shortName, fullName, UniprotKbId and sequence.
     * In each case a check will be made to ensure that there is a value to update too,
     * and that the values are different.
     *
     * If an update is done, it will be recorded in an updateEvent object.
     * This method does not fire any UpdateEvents.
     *
     * This method assumes that the proteinToEnrich has no null fields
     * (as they should have been filled by addition)
     * @param proteinToEnrich
     * @param proteinEnriched
     */
    protected void runProteinUpdateOnCore(Protein proteinToEnrich, Protein proteinEnriched){
        //ShortName - is never null
        if (! proteinToEnrich.getShortName().equalsIgnoreCase(
                proteinEnriched.getShortName() )) {
            updatedEvent.addElement(FIELD_SHORTNAME,proteinToEnrich.getShortName());
            proteinToEnrich.setShortName(proteinEnriched.getShortName());
        }

        //Full name
        if(proteinEnriched.getFullName() != null
                && ! proteinToEnrich.getFullName().equalsIgnoreCase(
                proteinEnriched.getFullName() )) {
            updatedEvent.addElement(FIELD_FULLNAME, proteinToEnrich.getFullName());
            proteinToEnrich.setFullName(proteinEnriched.getFullName());
        }

        //PRIMARY Uniprot AC
        if(proteinEnriched.getUniprotkb() != null
                && ! proteinToEnrich.getUniprotkb().equalsIgnoreCase(
                proteinEnriched.getUniprotkb() )){
            updatedEvent.addElement(FIELD_UNIPROTKBID, proteinToEnrich.getUniprotkb());
            proteinToEnrich.setUniprotkb(proteinEnriched.getUniprotkb());
        }

        //Sequence
        if(proteinEnriched.getSequence() != null
                && ! proteinToEnrich.getSequence().equalsIgnoreCase(
                proteinEnriched.getSequence())){
            updatedEvent.addElement(FIELD_SEQUENCE, proteinToEnrich.getSequence());
            proteinToEnrich.setSequence(proteinEnriched.getSequence());
        }
    }

    /**
     * Checks the Checksums (CRC64 and ROGID), fires an update if required,ands adds the checksum.
     *
     * For each checksum type, it is confirmed that only one is present in the Enriched form,
     * if this is so, the checksum is compared to the proteinToEnrich.
     * If there is already a checksum of a different value, an update event is fired and the old value removed.
     * The checksum will be added and the details added to the additionEvent.
     *
     * @param proteinToEnrich
     * @param proteinEnriched
     * @throws SeguidException
     */
     protected void runProteinUpdateOnChecksums(Protein proteinToEnrich, Protein proteinEnriched)
            throws SeguidException {

        //CHECKSUM - CRC64
        Checksum crc64checksum = null;
        //Is there a checksum in the fetched protein
        for(Checksum c :proteinEnriched.getChecksums()){
            if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                if(crc64checksum != null){
                    if(! crc64checksum.getValue().equals(c.getValue())){
                        // If multiple, non identical CRC64 checksums have been found,
                        // Fire an event, tell the logger if it's debugging
                        // then clear the place holder and break as the enricher cannot choose.
                        fireErrorEvent(new ErrorEvent(ErrorEvent.ERROR_CONFLICT, FIELD_CHECKSUM,
                                "Multiple CRC64 checksums found in the fetched protein."));
                        if(log.isDebugEnabled()) log.debug(
                                "Multiple different CRC64 checksums were found in fetched protein, please check code.");
                        crc64checksum = null;
                        break;
                    }else if(log.isDebugEnabled()) log.debug(
                            "Multiple identical CRC64 checksums were found fetched protein, " +
                                    "please check code.");
                } else {
                    crc64checksum = c;
                }
            }
        }
        if( crc64checksum != null){
            // Remove all old CRC64 checksums
            Collection<Checksum> oldChecksums = new ArrayList<Checksum>();
            for(Checksum c :proteinToEnrich.getChecksums()){
                if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                    if(!c.getValue().equals(crc64checksum.getValue())){
                        updatedEvent.addElement(FIELD_CHECKSUM, c);
                        oldChecksums.add(c);
                    }
                }
            }
            proteinToEnrich.getChecksums().removeAll(oldChecksums);

            proteinToEnrich.getChecksums().add(crc64checksum);
            addedEvent.addElement(FIELD_CHECKSUM, crc64checksum);
        }

        //Checksum -RogID
        if(proteinToEnrich.getOrganism() != null
                && proteinToEnrich.getOrganism().getTaxId() != -3
                && proteinToEnrich.getSequence() != null){

            RogidGenerator rogidGenerator = new RogidGenerator();
            String rogid = rogidGenerator.calculateRogid(
                    proteinToEnrich.getSequence(),""+proteinToEnrich.getOrganism().getTaxId());
            if(proteinToEnrich.getRogid() == null){
                proteinToEnrich.setRogid(rogid);
                addedEvent.addElement(FIELD_CHECKSUM,proteinToEnrich.getRogid());
            }
            else if(!proteinToEnrich.getRogid().equals(rogid)){
                updatedEvent.addElement(FIELD_CHECKSUM, rogid);
            }
            //TODO - should any kind of warning be raised if the ROGID cannot be calculated?
            //Does the enricher provide any degree of error catching?
        }

        /*
        else if(proteinToEnrich.getRogid() != null){
            if(proteinToEnrich.getOrganism() == null) throw new ConflictException(
                    "ROGID checksum in the protein being enriched " +
                            "but unable to confirm as organism is null.");

            else if(proteinToEnrich.getOrganism().getTaxId() == -3) throw new ConflictException(
                    "ROGID checksum in the protein being enriched " +
                            "but unable to confirm as organism taxid is not legal "+
                            "(value is ["+proteinToEnrich.getOrganism().getTaxId()+"]).");

            else if(proteinToEnrich.getSequence() == null) throw new ConflictException(
                    "ROGID checksum in the protein being enriched " +
                            "but unable to confirm it as no sequence is provided.");
        }
         */
    }

}
