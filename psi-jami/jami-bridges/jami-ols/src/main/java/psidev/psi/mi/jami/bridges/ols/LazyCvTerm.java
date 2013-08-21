package psidev.psi.mi.jami.bridges.ols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 14/08/13
 */
public class LazyCvTerm implements CvTerm {

    protected final Logger log = LoggerFactory.getLogger(LazyCvTerm.class.getName());

    private Query queryService;

    private String shortName = null;
    public String fullName = null;
    private Collection<Xref> xrefs  = null;
    private Collection<Xref> identifiers = null;
    private Collection<Annotation> annotations = null;
    private Collection<Alias> synonyms = null;

    private Xref primaryIdentifier;
    private Xref miIdentifier;
    private Xref modIdentifier;
    private Xref parIdentifier;

    public static final String MI_ONTOLOGY = "MI";
    public static final String MOD_ONTOLOGY = "MOD";
    public static final String PAR_ONTOLOGY = "PAR";
    public static final String GO_ONTOLOGY = "GO";

    /**
     * Initiates the query service.
     * @throws BridgeFailedException
     */
    private void startQueryService() throws BridgeFailedException {
        try{
            queryService = new QueryServiceLocator().getOntologyQuery();
        }catch (ServiceException e) {
            queryService = null;
            throw new BridgeFailedException(e);
        }
    }

    /**
     * Will create a lazy CvTerm using a known termName to find the identifier.
     *
     * The term must be an MI term and only the MI ontology will be searched.
     * Will search for the exact name. If the exact flag is false, and no exact terms were found,
     * the search will be repeated on inexact names.
     * @param miTermName        The name of the MI term to search for.
     * @param exact             True if only exact names should be searched.
     *                          False if inexact names should also be tried.
     * @throws BridgeFailedException
     */
    public LazyCvTerm(String miTermName , boolean exact) throws BridgeFailedException {
        this(miTermName, PSI_MI , exact);
    }

    /**
     * Will create a lazy CvTerm using a known termName to find the identifier.
     *
     * The search is limited to the ontology of the given name if that name is recognised, otherwise all of OLS is used.
     * Will search for the exact name. If the exact flag is false, and no exact terms were found,
     * the search will be repeated on inexact names.
     * @param termName          The name of the term to search for.
     * @param ontologyName      The MI name used for the ontology. (EG, 'PSI-MI' for the ontology 'MI')
     * @param exact             True if only exact names should be searched.
     *                          False if inexact names should also be tried.
     * @throws BridgeFailedException
     */
    public LazyCvTerm(String termName , String ontologyName, boolean exact) throws BridgeFailedException {
        if (termName == null)
            throw new IllegalArgumentException("The short name is required and cannot be null");
        if (ontologyName == null)
            throw new IllegalArgumentException("The ontology name is required and cannot be null");

        startQueryService();

        String olsOntologyName = null;
        if(PSI_MI.equalsIgnoreCase(ontologyName))
            olsOntologyName = MI_ONTOLOGY;
        else if(PSI_MOD.equalsIgnoreCase(ontologyName))
            olsOntologyName = MOD_ONTOLOGY;
        else if(PSI_PAR.equalsIgnoreCase(ontologyName))
            olsOntologyName = PAR_ONTOLOGY;
        else if("GO".equalsIgnoreCase(ontologyName))
            olsOntologyName = GO_ONTOLOGY;

        HashMap<String,String> termNamesMap;
        try{
            termNamesMap = queryService.getTermsByExactName(termName, olsOntologyName);
            if(!exact && termNamesMap.isEmpty())
                termNamesMap = queryService.getTermsByName(termName, olsOntologyName , false);
        }catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }

        log.info("entries: "+termNamesMap.size());


        if(termNamesMap.size() == 1){
            Map.Entry<String, String> entry = termNamesMap.entrySet().iterator().next();
            log.info(entry.getValue()+","+entry.getKey());
            fullName = entry.getValue();

            initialiseIdentifiers();
            if (PSI_MI.equalsIgnoreCase(ontologyName)){
                setMIIdentifier(entry.getKey());
                primaryIdentifier = miIdentifier;
            } else if(PSI_MOD.equalsIgnoreCase(ontologyName)) {
                setMODIdentifier(entry.getKey());
                primaryIdentifier = modIdentifier;
            } else if(PSI_PAR.equalsIgnoreCase(ontologyName)) {
                setPARIdentifier(entry.getKey());
                primaryIdentifier = parIdentifier;
            } else if(GO_ONTOLOGY.equalsIgnoreCase(ontologyName)) {
                primaryIdentifier = XrefUtils.createIdentityXref("GO" , entry.getKey());
                identifiers.add(primaryIdentifier);
            } else {
                primaryIdentifier = XrefUtils.createIdentityXref(ontologyName , entry.getKey());
                identifiers.add(primaryIdentifier);
            }
            log.info(fullName);

        } else {
            if(termNamesMap.size() > 1){
                log.info("A choice could not be made on the following:");
                log.info(termNamesMap.entrySet().toString());
            }
            fullName = null;
            identifiers = null;
        }
    }

    /**
     * Create a new LazyCvTerm using an MiIdentifier
     * @param miIdentifier          The MiIdentifier of the cvTerm.
     * @throws BridgeFailedException
     */
    public LazyCvTerm(String miIdentifier) throws BridgeFailedException {
        this( miIdentifier , PSI_MI );
    }

    /**
     * Creates a new CvTerm using the known identifier. The full name will also be queried for.
     * @param identifier        The identifier of the term.
     * @param ontologyName      The Mi name of the ontology from which the identifier was taken.
     * @throws BridgeFailedException
     */
    public LazyCvTerm(String identifier , String ontologyName) throws BridgeFailedException {
        if (identifier == null)
            throw new IllegalArgumentException("The short name is required and cannot be null");
        if (ontologyName == null)
            throw new IllegalArgumentException("The ontology name is required and cannot be null");

        startQueryService();

        initialiseIdentifiers();
        if (PSI_MI.equalsIgnoreCase(ontologyName)){
            setMIIdentifier(identifier);
            primaryIdentifier = miIdentifier;
        } else if(PSI_MOD.equalsIgnoreCase(ontologyName)) {
            setMODIdentifier(identifier);
            primaryIdentifier = modIdentifier;
        } else if(PSI_PAR.equalsIgnoreCase(ontologyName)) {
            setPARIdentifier(identifier);
            primaryIdentifier = parIdentifier;
        } else if(GO_ONTOLOGY.equalsIgnoreCase(ontologyName)) {
            primaryIdentifier = XrefUtils.createIdentityXref("GO" , identifier);
            identifiers.add(primaryIdentifier);
        } else {
            primaryIdentifier = XrefUtils.createIdentityXref(ontologyName , identifier);
            identifiers.add(primaryIdentifier);
        }

        try {
            String fullName = queryService.getTermById(identifier, null);
            if(fullName.equals(identifier)){
                // The OLS service echoes back the original query term if it can not be found
                log.warn("identifier "+identifier+" could not be found.");
                initialiseIdentifiers();
                clearPropertiesLinkedToIdentifiers();
            } else {
                this.fullName = fullName;
            }
        } catch (RemoteException e) {
            throw new BridgeFailedException(e);
        }
    }

    /**
     * If the shortName is not yet known, a query will be made to OLS.
     * If no shortName is found, the fullName is used instead.
     * @return  The shortName of the cvTerm.
     */
    public String getShortName() {
        if(shortName == null)
            fetchMetaDataByIdentifier(getMIIdentifier());
        if (shortName == null)
            shortName = fullName;
        return shortName;
    }

    public void setShortName(String name) {
        this.shortName = name;
    }

    /**
     * Returns the full name, found at initialisation.
     * @return      The full name. If this CvTerm is for an unreachable term, it will be null.
     */
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    protected void initialiseXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseSynonyms(){
        this.synonyms = new ArrayList<Alias>();
    }

    protected void initialiseIdentifiers(){
        this.identifiers = new CvTermIdentifierList();
    }

    public String getMIIdentifier() {
        return this.miIdentifier != null ? this.miIdentifier.getId() : null;
    }

    public String getMODIdentifier() {
        return this.modIdentifier != null ? this.modIdentifier.getId() : null;
    }

    public String getPARIdentifier() {
        return this.parIdentifier != null ? this.parIdentifier.getId() : null;
    }

    public void setMIIdentifier(String mi) {
        Collection<Xref> cvTermIdentifiers = getIdentifiers();

        // add new mi if not null
        if (mi != null){
            CvTerm psiMiDatabase = CvTermUtils.createPsiMiDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old psi mi if not null
            if (this.miIdentifier != null){
                cvTermIdentifiers.remove(this.miIdentifier);
            }
            this.miIdentifier = new DefaultXref(psiMiDatabase, mi, identityQualifier);
            cvTermIdentifiers.add(this.miIdentifier);
        }
        // remove all mi if the collection is not empty
        else if (!getIdentifiers().isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(getIdentifiers(), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
            this.miIdentifier = null;
        }
    }

    public void setMODIdentifier(String mod) {
        Collection<Xref> cvTermIdentifiers = getIdentifiers();

        // add new mod if not null
        if (mod != null){

            CvTerm psiModDatabase = CvTermUtils.createPsiModDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old psi mod if not null
            if (this.modIdentifier != null){
                cvTermIdentifiers.remove(this.modIdentifier);
            }
            this.modIdentifier = new DefaultXref(psiModDatabase, mod, identityQualifier);
            cvTermIdentifiers.add(this.modIdentifier);
        }
        // remove all mod if the collection is not empty
        else if (!getIdentifiers().isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(getIdentifiers(), CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
            this.modIdentifier = null;
        }
    }

    public void setPARIdentifier(String par) {
        Collection<Xref> cvTermIdentifiers = getIdentifiers();

        // add new mod if not null
        if (par != null){

            CvTerm psiModDatabase = CvTermUtils.createPsiParDatabase();
            CvTerm identityQualifier = CvTermUtils.createIdentityQualifier();
            // first remove old psi mod if not null
            if (this.parIdentifier != null){
                cvTermIdentifiers.remove(this.parIdentifier);
            }
            this.parIdentifier = new DefaultXref(psiModDatabase, par, identityQualifier);
            cvTermIdentifiers.add(this.parIdentifier);
        }
        // remove all mod if the collection is not empty
        else if (!getIdentifiers().isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(getIdentifiers(), null, CvTerm.PSI_PAR);
            this.parIdentifier = null;
        }
    }

    public Collection<Xref> getIdentifiers() {
        if(identifiers == null)
            initialiseIdentifiers();
        return identifiers;
    }

    public Collection<Xref> getXrefs() {
        if (xrefs == null){
            initialiseXrefs();
        }
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    public Collection<Alias> getSynonyms() {
        if (synonyms == null)
            fetchMetaDataByIdentifier(getMIIdentifier());

        return this.synonyms;
    }

    protected void processAddedIdentifierEvent(Xref added) {

        // the added identifier is psi-mi and it is not the current mi identifier
        if (miIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MI_MI, CvTerm.PSI_MI)){
            // the current psi-mi identifier is not identity, we may want to set miIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the miidentifier is not set, we can set the miidentifier
                if (miIdentifier == null){
                    miIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    miIdentifier = added;
                }
                // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    miIdentifier = added;
                }
            }
        }
        // the added identifier is psi-mod and it is not the current mod identifier
        else if (modIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD)){
            // the current psi-mod identifier is not identity, we may want to set modIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the modIdentifier is not set, we can set the modIdentifier
                if (modIdentifier == null){
                    modIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    modIdentifier = added;
                }
                // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    modIdentifier = added;
                }
            }
        }
        // the added identifier is psi-par and it is not the current par identifier
        else if (parIdentifier != added && XrefUtils.isXrefFromDatabase(added, null, CvTerm.PSI_PAR)){
            // the current psi-par identifier is not identity, we may want to set parIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(parIdentifier, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the parIdentifier is not set, we can set the parIdentifier
                if (parIdentifier == null){
                    parIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    parIdentifier = added;
                }
                // the added xref is secondary object and the current par is not a secondary object, we reset paridentifier
                else if (!XrefUtils.doesXrefHaveQualifier(parIdentifier, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    parIdentifier = added;
                }
            }
        }
    }

    protected void processRemovedIdentifierEvent(Xref removed) {
        // the removed identifier is psi-mi
        if (miIdentifier != null && miIdentifier.equals(removed)){
            miIdentifier = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
        }
        // the removed identifier is psi-mod
        else if (modIdentifier != null && modIdentifier.equals(removed)){
            modIdentifier = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
        }
        // the removed identifier is psi-par
        else if (parIdentifier != null && parIdentifier.equals(removed)){
            parIdentifier = XrefUtils.collectFirstIdentifierWithDatabase(getIdentifiers(), null, CvTerm.PSI_PAR);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers() {
        primaryIdentifier = null;
        miIdentifier = null;
        modIdentifier = null;
        parIdentifier = null;
    }


    @Override
    public int hashCode() {
        return UnambiguousCvTermComparator.hashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof CvTerm)){
            return false;
        }

        return UnambiguousCvTermComparator.areEquals(this, (CvTerm) o);
    }

    @Override
    public String toString() {
        return (miIdentifier != null ? miIdentifier.getId() : (modIdentifier != null ? modIdentifier.getId() : (parIdentifier != null ? parIdentifier.getId() : "-"))) + " ("+(shortName != null ? shortName : fullName)+")";
    }

    private class CvTermIdentifierList extends AbstractListHavingProperties<Xref> {
        public CvTermIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {

            processAddedIdentifierEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            processRemovedIdentifierEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToIdentifiers();
        }
    }



    // == QUERY METHODS =======================================================================

    /**
     * Retrieve the metadata for an entry.
     * <p>
     * The identifier is used to find the metadata
     * which can be used to find the identifier phrases for short labels and synonyms.
     *
     * @param identifier    The identifier that is being used.
     * @throws BridgeFailedException
     */
    private void fetchMetaDataByIdentifier(String identifier){
        try{
            Map<String,String> metaDataMap = queryService.getTermMetadata(identifier, null);
            if(primaryIdentifier == null)
                log.info("null primary");
            if(primaryIdentifier.getDatabase() == null)
                log.info("null database");
            log.info("using "+primaryIdentifier.getDatabase().getShortName());
            shortName = extractShortNameFromMetaData(metaDataMap , primaryIdentifier.getDatabase().getShortName());
            synonyms = extractSynonymsFromMetaData(metaDataMap , primaryIdentifier.getDatabase().getShortName());
            if(synonyms.isEmpty())
                initialiseSynonyms();
        }catch (RemoteException e) {
            log.warn("LazyCvTerm "+toString()+" failed whilst attempting to access metaData.",e);
        }
    }

    /**
     * Scans the meta data to find the short name if one is present.
     * @param metaDataMap           The map of data to search in.
     * @param miTermOntologyName    The name used for the ontology in MI.
     * @return                      The short name if one is found.
     */
    private String extractShortNameFromMetaData(Map metaDataMap, String miTermOntologyName){

        String META_DATA_SEPARATOR = "_";
        String SHORTLABEL_IDENTIFIER;
        if(miTermOntologyName == null) return null;
        else if(miTermOntologyName.equals(PSI_MI)) SHORTLABEL_IDENTIFIER = "Unique short label curated by PSI-MI";
        else if(miTermOntologyName.equals(PSI_MOD)) SHORTLABEL_IDENTIFIER = "Short label curated by PSI-MOD";
        else return null;

        if (metaDataMap != null) {
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (keyName.startsWith(SHORTLABEL_IDENTIFIER + META_DATA_SEPARATOR)){
                    return (String)metaDataMap.get(key);
                }
            }
        }
        return null;
    }

    /**
     * Scans the meta data to find the description if one is present.
     * @param metaDataMap   The map of metaData to scrape for a definition
     * @return              The definition for the term if it is known.
     */
    private String extractDescriptionFromMetaData(Map metaDataMap){
        String DEFINITION_KEY = "definition";
        if (metaDataMap != null) {
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (DEFINITION_KEY.equalsIgnoreCase(keyName)){
                    return (String) metaDataMap.get(key);
                }
            }
        }
        return null;
    }

    /**
     * Scans the meta data to find the synonyms if any are present.
     * @param metaDataMap           The map of meatData to scrape for synonyms.
     * @param miTermOntologyName    The name of the ontology used in psi-mi
     * @return                      The synonyms. Or an empty collection if none are found.
     */
    private Collection<Alias> extractSynonymsFromMetaData(Map metaDataMap, String miTermOntologyName){

        String META_DATA_SEPARATOR = "_";
        String SYNONYM_IDENTIFIER;
        String EXACT_SYNONYM_KEY = "exact_synonym";
        if(miTermOntologyName == null) return Collections.EMPTY_LIST;
        else if(miTermOntologyName.equalsIgnoreCase(PSI_MI)) SYNONYM_IDENTIFIER = "Alternate label curated by PSI-MI";
        else if(miTermOntologyName.equalsIgnoreCase(PSI_MOD)) SYNONYM_IDENTIFIER = "Alternate name curated by PSI-MOD";
        else return Collections.EMPTY_LIST;

        if (metaDataMap != null) {
            Collection<Alias> synonyms = new ArrayList<Alias>();
            for (Object key : metaDataMap.keySet()){
                String keyName = (String)key;
                if (keyName.startsWith(SYNONYM_IDENTIFIER + META_DATA_SEPARATOR)){
                    synonyms.add(AliasUtils.createAlias(
                            "synonym", "MI:1041", (String) metaDataMap.get(key)));
                }else if (keyName.startsWith(EXACT_SYNONYM_KEY)){
                    synonyms.add(AliasUtils.createAlias(
                            "synonym", "MI:1041", (String) metaDataMap.get(key)));
                }
            }
            return synonyms;
        }else{
            return Collections.EMPTY_LIST;
        }
    }
}
