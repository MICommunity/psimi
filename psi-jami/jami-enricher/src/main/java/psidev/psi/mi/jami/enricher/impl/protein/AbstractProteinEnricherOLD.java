package psidev.psi.mi.jami.enricher.impl.protein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;


/**
 * Created with IntelliJ IDEA.
 *
 * Author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
@Deprecated
public abstract class AbstractProteinEnricherOLD
        implements ProteinEnricher {

    protected final Logger log = LoggerFactory.getLogger(AbstractProteinEnricherOLD.class.getName());

    //TODO will throw currently throw null pointer if there is no listener applied
    protected ProteinEnricherListener listener;

    private ProteinFetcher fetcher = null;


    private MockOrganismFetcher mockOrganismFetcher = new MockOrganismFetcher();
    private OrganismEnricher organismEnricher;
    public static final String TYPE = "Protein";



    public AbstractProteinEnricherOLD(){
    }

    public AbstractProteinEnricherOLD(ProteinFetcher fetcher){
        this();
        setFetcher(fetcher);
    }

    /**
     * Returns the listener which hears events from the protein as it is enriched and the status of the enrichment.
     * @return
     */
    public ProteinEnricherListener getProteinEnricherListener(){
        return listener;
    }

    /**
     * Sets the listener which hears events from the protein as it is enriched and the status of the enrichment.
     * Only one listener can be set.
     * @param proteinEnricherListener
     */
    public void setProteinEnricherListener(ProteinEnricherListener proteinEnricherListener){
        this.listener = proteinEnricherListener;
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
     * @param ProteinToEnrich   The protein which is being enriched.
     * @return
     * @throws
     */
    /*protected Collection<Protein> getFullyEnrichedForms(Protein ProteinToEnrich)
            throws BadToEnrichFormException, MissingServiceException,
            BadSearchTermException, BadResultException,
            BridgeFailedException {

        if(fetcher == null) throw new MissingServiceException("ProteinFetcher has not been provided.");
        if(ProteinToEnrich == null) throw new BadToEnrichFormException("Attempted to enrich a null protein.");

        if(ProteinToEnrich.getUniprotkb() == null) {
            if(listener != null) listener.onProteinEnriched(ProteinToEnrich,
                    "Failed. No Uniprot AC was found to search upon.");
            return null;
        }

        Collection<Protein> enriched;
        enriched = fetcher.getProteinsByIdentifier(ProteinToEnrich.getUniprotkb());
        //queryDetails = new QueryDetails(TYPE, ProteinToEnrich.getUniprotkb(),FIELD_UNIPROTKBID, fetcher.getService(), ProteinToEnrich);

        return enriched;
    }*/

    /**
     * Takes results from a query and chooses the best match for enrichment (if there is one.
     * Will return null if no proteins are available or there is no clear match.
     * Will also fire onProteinEnriched if the returned protein is null
     * to give further information about why no protein was chosen.
     * @param proteinToEnrich   The protein which is being enriched.
     * @param proteinsEnriched  Collection of fully enriched proteins to choose the best match from
     * @return  A fully enriched protein to compare and extract fields from.
     */
    /*protected Protein chooseProteinEnriched(Protein proteinToEnrich, Collection<Protein> proteinsEnriched){

        // No proteins, fail
        if(proteinsEnriched == null
                || proteinsEnriched.isEmpty()){
            if(listener != null) listener.onProteinEnriched(proteinToEnrich,
                    "Failed. Protein is dead. " +
                            "Found no entries.");
            return null;
        }

        // 1 protein, use it
        if(proteinsEnriched.size() == 1) {
            return proteinsEnriched.iterator().next();
        }

        // Examples:
        // - one single entry : P12345
        // - uniprot demerge (different uniprot entries with different organisms) : P77681
        // - uniprot demerge (different uniprot entries with same organisms) : P11163
        // In your enricher, if you have several entries,
        // try to look for the one with the same organism as the protein you try to enrich.
        // If you don't find one or several entries have the same organism,
        // fire a specific event because we want to track these proteins.

        // Many proteins, try and choose
        if(proteinToEnrich.getOrganism() == null
                || proteinToEnrich.getOrganism().getTaxId() == -3){
            if(listener != null) listener.onProteinEnriched(proteinToEnrich,
                    "Failed. Protein is demerged. " +
                            "Found " + proteinsEnriched.size() + " entries. " +
                            "Cannot choose as no organism in proteinToEnrich.");
            return null;
        }

        Protein proteinFetched = null;
        for(Protein protein : proteinsEnriched){
            if(protein.getOrganism() != null
                    && protein.getOrganism().getTaxId() != -3){
                if(protein.getOrganism().getTaxId() == proteinToEnrich.getOrganism().getTaxId()){
                    if(proteinFetched == null) proteinFetched = protein;
                    else{
                        if(listener != null) listener.onProteinEnriched(proteinToEnrich,
                                "Failed. Protein is demerged. " +
                                        "Found " + proteinsEnriched.size() + " entries. " +
                                        "Cannot choose as multiple entries match organism in proteinToEnrich.");
                        return null;
                    }
                }
            }
        }

        if(proteinFetched == null){
            if(listener != null) listener.onProteinEnriched(proteinToEnrich,
                    "Failed. Protein is demerged. " +
                            "Found " + proteinsEnriched.size() + " entries. " +
                            "Cannot choose as no entries match organism in proteinToEnrich.");
            return null;
        }

        if(log.isInfoEnabled()) log.info("Chose a demerged protein from a choice of "+proteinsEnriched.size());
        return proteinFetched;
    }  */



    /**
     * Does a minimum enrichment of all empty or appendable fields up to the level of mi-tab 2.5
     *
     *
     * @param proteinToEnrich   The protein which is being enriched.
     * @param proteinFetched   A fully enriched interpretation to compare and extract fields from.
     * @throws SeguidException
     */
    /*protected void runAdditionOnCore(Protein proteinToEnrich, Protein proteinFetched)
            throws SeguidException {


        //InteractorType
        if(!proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Protein.PROTEIN_MI)){
            if(proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR_MI)){
                //TODO listener.onAdded
                //        onInteractorUpdate();
                proteinToEnrich.setInteractorType(CvTermUtils.createProteinInteractorType());
            }
        }

        //ShortName - is never null

        //FullName
        if(proteinToEnrich.getFullName() == null
                && proteinFetched.getFullName() != null){
            if(listener != null) listener.onFullNameUpdate(proteinFetched, null);
            proteinToEnrich.setFullName( proteinFetched.getFullName() );
        }

        //PRIMARY Uniprot AC
        if(proteinToEnrich.getUniprotkb() == null
                && proteinFetched.getUniprotkb() != null) {
            if(listener != null) listener.onUniprotKbUpdate(proteinFetched, null);
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
        }

        //Sequence
        if(proteinToEnrich.getSequence() == null
                && proteinFetched.getSequence() != null){
            if(listener != null) listener.onSequenceUpdate(proteinFetched, null);
            proteinToEnrich.setSequence(proteinFetched.getSequence());
        }

        //TODO - is this correct? Is there a scenario where 2 primary ACs are created?
        Collection<Xref> subtractedIdentifiers = CollectionUtilsExtra.comparatorSubtract(
                proteinFetched.getIdentifiers(),
                proteinToEnrich.getIdentifiers(),
                new DefaultXrefComparator());
        for(Xref xref: subtractedIdentifiers){
            if(listener != null) listener.onAddedIdentifier(proteinFetched, xref);
            proteinToEnrich.getIdentifiers().add(xref);
        }

        //TODO some introduced aliases may enter a form of conflict - need to do a further comparison.
        //Aliases
        Collection<Alias> subtractedAliases = CollectionUtilsExtra.comparatorSubtract(
                proteinFetched.getAliases(),
                proteinToEnrich.getAliases(),
                new DefaultAliasComparator());
        for(Alias alias: subtractedAliases){
            if(listener != null) listener.onAddedAlias(proteinFetched, alias);
            proteinToEnrich.getAliases().add(alias);
        }

        //Xref
        Collection<Xref> subtractedXrefs = CollectionUtilsExtra.comparatorSubtract(
                proteinFetched.getXrefs(),
                proteinToEnrich.getXrefs(),
                new DefaultXrefComparator());
        for(Xref xref: subtractedXrefs){
            if(listener != null) listener.onAddedXref(proteinFetched, xref);
            proteinToEnrich.getXrefs().add(xref);
        }*/

        //Organism -
        /*if(proteinEnriched.getOrganism() != null){
            if(organismEnricher == null) throw new EnrichmentException(
                    "OrganismEnricher was not provided for proteinEnricher");

            //Create a new empty organism and pass the problem over to the organismEnricher
            if(proteinToEnrich.getOrganism() == null) proteinToEnrich.setOrganism(new DefaultOrganism(-3));


            mockOrganismFetcher.clearOrganisms();
            mockOrganismFetcher.addNewOrganism(
                    ""+proteinToEnrich.getOrganism().getTaxId(), proteinEnriched.getOrganism());
            organismEnricher.enrichOrganism(proteinToEnrich.getOrganism());
        }
    }*/

    /**
     * Update and overwrites any of the central fields which differ from the enriched form.
     *
     * @param proteinToEnrich   The protein which is being enriched.
     * @param proteinFetched   A fully enriched interpretation to compare and extract fields from.
     */
    /*protected void runUpdateOnCore(Protein proteinToEnrich, Protein proteinFetched){
        //ShortName - is never null
        if (! proteinToEnrich.getShortName().equalsIgnoreCase(proteinFetched.getShortName() )) {
            if(listener != null) listener.onShortNameUpdate(proteinToEnrich, proteinToEnrich.getShortName());
            proteinToEnrich.setShortName(proteinFetched.getShortName());
        }

        //Full name
        if(proteinFetched.getFullName() != null
                && (proteinToEnrich.getFullName() == null
                    || ! proteinToEnrich.getFullName().equalsIgnoreCase(proteinFetched.getFullName()) )) {
            if(listener != null) listener.onFullNameUpdate(proteinToEnrich, proteinToEnrich.getFullName());
            proteinToEnrich.setFullName(proteinFetched.getFullName());
        }

        //PRIMARY Uniprot AC
        if(proteinFetched.getUniprotkb() != null
                && ( proteinToEnrich.getUniprotkb() == null
                    || ! proteinToEnrich.getUniprotkb().equalsIgnoreCase(proteinFetched.getUniprotkb()) )){
            if(listener != null) listener.onUniprotKbUpdate(proteinToEnrich, proteinToEnrich.getUniprotkb());
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
        }

        //Sequence
        if(proteinFetched.getSequence() != null
                && ( proteinToEnrich.getSequence() == null
                    || ! proteinToEnrich.getSequence().equalsIgnoreCase(proteinFetched.getSequence()) )){
            if(listener != null) listener.onSequenceUpdate(proteinToEnrich, proteinToEnrich.getSequence());
            proteinToEnrich.setSequence(proteinFetched.getSequence());
        }
    }*/


    /**
     * Checks the Checksums (CRC64 and ROGID) and fires a conflict if one is present, otherwise the checksum is added.
     *
     * For each checksum type, it is confirmed that only one is present in the Enriched form,
     * if this is so, the checksum is compared to the proteinToEnrich.
     * If there is already a checksum of a different value, a conflict is fired.
     * Else, the checksum will be added and the details added to the additionEvent.
     *
     * @param proteinToEnrich   The protein which is being enriched.
     * @param proteinFetched   A fully enriched interpretation to compare and extract fields from.
     * @throws SeguidException
     */
   /* protected void runAdditionOnChecksum(Protein proteinToEnrich, Protein proteinFetched) throws SeguidException {

        //CHECKSUM - CRC64
        Checksum crc64checksum = null;
        //Is there a checksum in the fetched protein
        for(Checksum c :proteinFetched.getChecksums()){
            if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                if(crc64checksum != null){
                    if(! crc64checksum.getValue().equals(c.getValue())){
                        // If multiple, non identical CRC64 checksums have been found,
                        // Fire an event, tell the logger if it's debugging
                        // then clear the place holder and break.
                        //fireErrorEvent(new ErrorEvent(
                        //        ErrorEvent.ERROR_CONFLICT, FIELD_CHECKSUM,
                        //        "Multiple CRC64 checksums found in the fetched protein."));
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
                        //fireConflictEvent(new ConflictEvent(
                        //    FIELD_CHECKSUM, crc64checksum, proteinToEnrich));
                    }
                }
            }
            if(!exists){
                proteinToEnrich.getChecksums().add(crc64checksum);
                if(listener != null) listener.onAddedChecksum(proteinToEnrich, crc64checksum);
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
                //todo if(listener != null) listener.onAddedChecksum(proteinToEnrich, rogid);
            }
            else if(!proteinToEnrich.getRogid().equals(rogid)){
                //fireConflictEvent(new ConflictEvent(
                //    FIELD_CHECKSUM, rogid, proteinToEnrich));
            }
        }
    }  */


    /**
     * Checks the Checksums (CRC64 and ROGID), fires an update if required,ands adds the checksum.
     *
     * For each checksum type, it is confirmed that only one is present in the Enriched form,
     * if this is so, the checksum is compared to the proteinToEnrich.
     * If there is already a checksum of a different value, an update event is fired and the old value removed.
     * The checksum will be added and the details added to the additionEvent.
     *
     * @param proteinToEnrich   The protein which is being enriched.
     * @param proteinFetched   A fully enriched interpretation to compare and extract fields from.
     * @throws SeguidException
     */
     /*protected void runUpdateOnChecksums(Protein proteinToEnrich, Protein proteinFetched)
            throws SeguidException {

        //CHECKSUM - CRC64
        Checksum crc64checksum = null;
        //Is there a checksum in the fetched protein
        for(Checksum c :proteinFetched.getChecksums()){
            if(c.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                if(crc64checksum != null){
                    if(! crc64checksum.getValue().equals(c.getValue())){
                        // If multiple, non identical CRC64 checksums have been found,
                        // Fire an event, tell the logger if it's debugging
                        // then clear the place holder and break as the enricher cannot choose.
                        //fireErrorEvent(new ErrorEvent(ErrorEvent.ERROR_CONFLICT, FIELD_CHECKSUM,
                        //        "Multiple CRC64 checksums found in the fetched protein."));
                        if(log.isDebugEnabled()) log.debug(
                                "Multiple different CRC64 checksums were found in fetched protein, please check code.");
                        crc64checksum = null;
                        break;
                    }else if(log.isDebugEnabled()) log.debug(
                            "Multiple identical CRC64 checksums were found fetched protein, please check code.");
                } else {
                    crc64checksum = c;
                }
            }
        }
        if( crc64checksum != null){
            // Remove all old CRC64 checksums
            Collection<Checksum> oldChecksums = new ArrayList<Checksum>();
            for(Checksum checksum :proteinToEnrich.getChecksums()){
                if(checksum.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                    if(!checksum.getValue().equals(crc64checksum.getValue())){
                        oldChecksums.add(checksum);
                        if(listener != null) listener.onRemovedChecksum(proteinToEnrich, checksum);
                    }
                }
            }
            proteinToEnrich.getChecksums().removeAll(oldChecksums);
            proteinToEnrich.getChecksums().add(crc64checksum);
            if(listener != null) listener.onAddedChecksum(proteinToEnrich, crc64checksum);
        }

        // Checksum -RogID
        if(proteinToEnrich.getOrganism() != null
                && proteinToEnrich.getOrganism().getTaxId() != -3
                && proteinToEnrich.getSequence() != null){

            RogidGenerator rogidGenerator = new RogidGenerator();
            String rogid = rogidGenerator.calculateRogid(
                    proteinToEnrich.getSequence(),""+proteinToEnrich.getOrganism().getTaxId());

            if(proteinToEnrich.getRogid() == null
                    || !proteinToEnrich.getRogid().equals(rogid)){
                proteinToEnrich.setRogid(rogid);
                //todo if(listener != null) listener.onAddedChecksum(proteinToEnrich, );
            }
        }
    }*/
}
