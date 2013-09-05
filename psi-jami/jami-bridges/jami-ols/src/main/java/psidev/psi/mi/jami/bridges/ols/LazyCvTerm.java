package psidev.psi.mi.jami.bridges.ols;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.ols.utils.OlsUtils;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.ols.soap.Query;

import java.rmi.RemoteException;
import java.util.*;

/**
 * A lazy cvTerm which will only fetch metadata when required.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 14/08/13
 */
public class LazyCvTerm extends DefaultCvTerm {

    protected final Logger log = LoggerFactory.getLogger(LazyCvTerm.class.getName());

    private Query queryService;

    private boolean hasShortName = false;
    private boolean hasSynonyms = false;
    private boolean hasLoadedMetadata = false;
    private boolean hasLoadedXrefs = false;

    private String ontologyName;
    private Xref originalXref;

    public LazyCvTerm(Query queryService, String fullName, Xref identityRef, String ontologyName) {
        super("");
        if (queryService == null){
            throw new IllegalArgumentException("The lazy cv term needs the Ols query service which cannot be null.");
        }
        this.queryService = queryService;

        setFullName(fullName);
        originalXref = identityRef;
        if (identityRef != null){
            getIdentifiers().add(identityRef);
        }

        this.ontologyName = ontologyName;
    }

    /**
     * If the shortName is not yet known, a query will be made to OLS.
     * If no shortName is found, the fullName is used instead.
     * @return  The shortName of the cvTerm.
     */
    @Override
    public String getShortName() {
        if (!hasShortName){
            initialiseMetaData(originalXref);
        }
        return super.getShortName();
    }

    public void setShortName(String name) {
        if (name != null){
            hasShortName = true;
        }
        super.setShortName(name);
    }

    public Collection<Xref> getXrefs() {
        if (!hasLoadedXrefs){
            initialiseOlsXrefs(originalXref);
        }
        return super.getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        if (!hasLoadedXrefs){
            initialiseOlsXrefs(originalXref);
        }
        if (!hasLoadedMetadata){
            initialiseMetaData(originalXref);
        }
        return super.getAnnotations();
    }

    public Collection<Alias> getSynonyms() {
        if (!hasSynonyms) {
            initialiseMetaData(originalXref);
        }

        return super.getSynonyms();
    }

    @Override
    public String toString() {
        return (getMIIdentifier() != null ? getMIIdentifier() : (getMODIdentifier() != null ? getMODIdentifier() : (getPARIdentifier() != null ? getPARIdentifier() : "-"))) + " ("+getFullName()+")";
    }

    protected Xref getOriginalXref() {
        return originalXref;
    }

    protected String getOntologyName() {
        return ontologyName;
    }

    protected Query getQueryService() {
        return queryService;
    }

    // == QUERY METHODS =======================================================================

    /**
     * Retrieve the metadata for an entry.
     * <p>
     * The identifier is used to find the metadata
     * which can be used to find the identifier phrases for short labels and synonyms.
     *
     * @param identifier    The identifier that is being used.
     */
    protected void initialiseMetaData(Xref identifier){
        Map<String,String> metaDataMap = null;
        try {
            metaDataMap = queryService.getTermMetadata(identifier.getId(), ontologyName);

            if (metaDataMap != null){
                if (!hasSynonyms){
                    for (Object key : metaDataMap.keySet()){
                        String keyName = (String) key;

                        // definition
                        if (OlsUtils.DEFINITION_KEY.equalsIgnoreCase(keyName)){
                            String description = (String) metaDataMap.get(keyName);
                            Annotation url = processDefinition(description);
                            if (url != null){
                                description = description.replaceAll(url.getValue(),"");
                            }
                            initialiseDefinition(description);
                        }
                        // comment
                        else if (OlsUtils.DEFINITION_KEY.equalsIgnoreCase(keyName) || keyName.startsWith(OlsUtils.COMMENT_KEY + OlsUtils.META_DATA_SEPARATOR)){
                            String comment = (String) metaDataMap.get(keyName);
                            initialiseDefinition(comment);
                        }
                        else {
                            String synonym = (String) metaDataMap.get(keyName);
                            processSynonym(keyName, synonym);
                        }
                    }
                }
            }

            hasLoadedMetadata = true;
            hasSynonyms = true;

            if (!hasShortName){
                super.setShortName(getFullName());
            }
        } catch (RemoteException e) {
            throw new LazyTermLoadingException("Impossible to load OLS metada for " + identifier.getId(),e);
        }
    }

    protected void initialiseDefinition(String description) {
        super.getAnnotations().add(AnnotationUtils.createComment(description));
    }

    protected boolean hasLoadedMetadata() {
        return hasLoadedMetadata;
    }

    protected boolean hasLoadedXrefs() {
        return hasLoadedXrefs;
    }

    private void processSynonym(String synonymName, String synonym) {

        if (synonymName.startsWith(OlsUtils.MI_SHORTLABEL_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR)
                || synonymName.startsWith(OlsUtils.MOD_SHORTLABEL_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR)){
            if (!hasShortName){
                super.setShortName(synonym.toLowerCase());
                hasShortName = true;
            }
        }
        else if (synonymName.startsWith(OlsUtils.EXACT_SYNONYM_KEY + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.EXACT_SYNONYM_KEY.equalsIgnoreCase(synonymName)){
            super.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym));
        }
        else if (synonymName.startsWith(OlsUtils.MI_ALIAS_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.MI_ALIAS_IDENTIFIER.equalsIgnoreCase(synonymName)){
            super.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym));
        }
        else if (synonymName.startsWith(OlsUtils.MOD_ALIAS_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.MOD_ALIAS_IDENTIFIER.equalsIgnoreCase(synonymName)){
            super.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym));
        }
        else if (synonymName.startsWith(OlsUtils.RESID_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.RESID_IDENTIFIER.equalsIgnoreCase(synonymName)){
            super.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym));
        }
        else if (synonymName.startsWith(OlsUtils.RESID_MISNOMER_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.RESID_MISNOMER_IDENTIFIER.equalsIgnoreCase(synonymName)){
            super.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym));
        }
        else if (synonymName.startsWith(OlsUtils.RESID_NAME_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.RESID_NAME_IDENTIFIER.equalsIgnoreCase(synonymName)){
            super.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym));
        }
        else if (synonymName.startsWith(OlsUtils.RESID_SYSTEMATIC_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.RESID_SYSTEMATIC_IDENTIFIER.equalsIgnoreCase(synonymName)){
            super.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym));
        }
        else if (synonymName.startsWith(OlsUtils.UNIPROT_FEATURE_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.UNIPROT_FEATURE_IDENTIFIER.equalsIgnoreCase(synonymName)){
            super.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym));
        }
    }

    /**
     * Process the definition String
     * @param definition
     * @return
     */
    private Annotation processDefinition(String definition) {
        if ( definition.contains( OlsUtils.LINE_BREAK ) ) {
            String[] defArray = definition.split( OlsUtils.LINE_BREAK );

            String otherInfoString = null;
            if ( defArray.length == 2 ) {
                otherInfoString = defArray[1];
                return processInfoInDescription(otherInfoString, defArray[0].length());
            } else if ( defArray.length > 2 ) {

                for (int i = 1; i < defArray.length; i++){
                    otherInfoString = defArray[i];
                    Annotation annot = processInfoInDescription(otherInfoString, defArray[0].length());
                    if (annot != null){
                         return annot;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Process the other information in the description
     * @param otherInfoString
     * @return true if an obsolete annotation has been added
     */
    private Annotation processInfoInDescription(String otherInfoString, int defLength) {

        // URL
        if ( otherInfoString.startsWith( OlsUtils.HTTP_DEF ) ) {
            Annotation annot = AnnotationUtils.createAnnotation(Annotation.URL, Annotation.URL_MI, otherInfoString);
            super.getAnnotations().add(annot);
            return annot;
        }
        else if (otherInfoString.contains( OlsUtils.HTTP_DEF )){
            String[] defArray = otherInfoString.split( OlsUtils.HTTP_DEF );

            if ( defArray.length == 2 ) {
                Annotation annot = AnnotationUtils.createAnnotation(Annotation.URL, Annotation.URL_MI, OlsUtils.HTTP_DEF + defArray[1]);
                        super.getAnnotations().add(annot);
                return annot;
            } else if ( defArray.length > 2 ) {
                Annotation annot = AnnotationUtils.createAnnotation(Annotation.URL, Annotation.URL_MI, otherInfoString.substring(defLength));
                        super.getAnnotations().add(annot);
                return annot;
            }

        }
        return null;
    }

    /**
     * This method will initialize the xrefs of this object from the map of xrefs
     */
    private void initialiseOlsXrefs(Xref identifier) {
        Map<String,String> metaDataRefs = null;
        try {
            metaDataRefs = queryService.getTermXrefs(identifier.getId(), ontologyName);

            if (metaDataRefs != null){
                for (Object key : metaDataRefs.keySet()){
                    String keyName = (String) key;

                    // xref definitions
                    if (OlsUtils.XREF_DEFINITION_KEY.equalsIgnoreCase(keyName) || keyName.startsWith(OlsUtils.XREF_DEFINITION_KEY)) {
                        String xref = (String) metaDataRefs.get(keyName);

                        if (xref.contains(OlsUtils.META_XREF_SEPARATOR)){
                            String [] xrefDef = xref.split(OlsUtils.META_XREF_SEPARATOR);
                            String database = null;
                            String accession = null;
                            String pubmedPrimary = null;

                            if (xrefDef.length == 2){
                                database = xrefDef[0];
                                accession = xrefDef[1].trim();
                            }
                            else if (xrefDef.length > 2){
                                database = xrefDef[0];
                                accession = xref.substring(database.length() + 1).trim();
                            }

                            if (database != null && accession != null){
                                pubmedPrimary = processXrefDefinition(xref, database, accession, pubmedPrimary);
                            }
                        }
                    }
                    else {
                        String xref = (String) metaDataRefs.get(keyName);

                        if (xref.contains(OlsUtils.META_XREF_SEPARATOR)){
                            String [] xrefDef = xref.split(OlsUtils.META_XREF_SEPARATOR);
                            String database = null;
                            String accession = null;

                            if (xrefDef.length == 2){
                                database = xrefDef[0];
                                accession = xrefDef[1].trim();
                            }
                            else if (xrefDef.length > 2){
                                database = xrefDef[0];
                                accession = xref.substring(database.length() + 1).trim();
                            }

                            if (database != null && accession != null){
                                processXref(database, accession);
                            }
                        }
                        else {
                            processXref(null, xref);
                        }
                    }
                }
            }

            hasLoadedXrefs = true;
        } catch (RemoteException e) {
            throw new LazyTermLoadingException("Impossible to load OLS metada for " + identifier.getId(),e);
        }
    }

    private void processXref(String db, String accession) {
        // xref validation regexp
        if (Annotation.VALIDATION_REGEXP.equalsIgnoreCase(db)){

            String annotationText = accession.trim();

            if (annotationText.startsWith(OlsUtils.QUOTE)){
                annotationText = annotationText.substring(OlsUtils.QUOTE.length());
            }
            if (annotationText.endsWith(OlsUtils.QUOTE)){
                annotationText = annotationText.substring(0, annotationText.indexOf(OlsUtils.QUOTE));
            }

            Annotation validation = AnnotationUtils.createAnnotation(Annotation.VALIDATION_REGEXP, Annotation.VALIDATION_REGEXP_MI, annotationText);  // MI xref
            super.getAnnotations().add(validation);
        }
        // search url
        else if (db == null && accession.startsWith(Annotation.SEARCH_URL)){
            String url = accession.substring(Annotation.SEARCH_URL.length());

            if (url.startsWith("\\")){
                url = url.substring(1);
            }
            if (url.endsWith("\\")){
                url = url.substring(0, url.length() - 1);
            }

            Annotation validation = AnnotationUtils.createAnnotation(Annotation.SEARCH_URL, Annotation.SEARCH_URL_MI, url);  // MI xref
            super.getAnnotations().add(validation);
        }
        else if (db.equalsIgnoreCase(Annotation.SEARCH_URL)){
            String url = accession.trim();

            Annotation validation = AnnotationUtils.createAnnotation(Annotation.SEARCH_URL, Annotation.SEARCH_URL_MI, url);  // MI xref
            super.getAnnotations().add(validation);
        }
        else if (db.startsWith(Annotation.SEARCH_URL)){
            String prefix = db.substring(Annotation.SEARCH_URL.length());
            String url = prefix + OlsUtils.META_XREF_SEPARATOR + accession;

            if (url.startsWith("\"")){
                url = url.substring(1);
            }
            if (url.endsWith("\"")){
                url = url.substring(0, url.length() - 1);
            }

            Annotation validation = AnnotationUtils.createAnnotation(Annotation.SEARCH_URL, Annotation.SEARCH_URL_MI, url);  // MI xref
            super.getAnnotations().add(validation);
        }
    }

    private String processXrefDefinition(String xref, String database, String accession, String pubmedPrimary) {

        if ( OlsUtils.PMID.equalsIgnoreCase(database) ) {
            if (pubmedPrimary == null){
                pubmedPrimary = xref;

                Xref primaryPubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, Xref.PRIMARY, Xref.PRIMARY_MI);
                super.getXrefs().add(primaryPubmedRef);
            }
            else {
                Xref pubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, Xref.METHOD_REFERENCE, Xref.METHOD_REFERENCE_MI);
                super.getXrefs().add(pubmedRef);
            }
        }
        else if ( OlsUtils.PMID_APPLICATION.equalsIgnoreCase(database) ) {
            Xref pubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            super.getXrefs().add(pubmedRef); // MI not MOD
        } else if ( Xref.GO.equalsIgnoreCase(database) ) {
            Xref goRef = XrefUtils.createXrefWithQualifier(Xref.GO, Xref.GO_MI, database + ":" + accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            super.getXrefs().add(goRef); // MI not MOD
        } else if ( Xref.RESID.equalsIgnoreCase(database) ) {
            Xref resXref = XrefUtils.createXrefWithQualifier(Xref.RESID, Xref.RESID_MI, accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            super.getXrefs().add(resXref);
        } else if ( Xref.SO.equalsIgnoreCase(database) ) {
            Xref soRef = XrefUtils.createXrefWithQualifier(Xref.SO, Xref.SO_MI, database + ":" + accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            super.getXrefs().add(soRef);  // MI not MOD
        }
        else if ( Xref.PUBMED.equalsIgnoreCase(database) ) {
            if (pubmedPrimary == null){
                pubmedPrimary = xref;

                Xref primaryPubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, Xref.PRIMARY, Xref.PRIMARY_MI);
                super.getXrefs().add(primaryPubmedRef);
            }
            else {
                Xref pubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, Xref.METHOD_REFERENCE, Xref.METHOD_REFERENCE_MI);
                super.getXrefs().add(pubmedRef);
            }
        }
        else if ( Xref.CHEBI.equalsIgnoreCase(database) ) {
            Xref chebiRef = XrefUtils.createXrefWithQualifier(Xref.CHEBI, Xref.CHEBI_MI, accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            super.getXrefs().add(chebiRef);  // MOD xref
        } else if ( Annotation.URL.equalsIgnoreCase(database) ) {
            super.getAnnotations().add(AnnotationUtils.createAnnotation(Annotation.URL, Annotation.URL_MI, accession));
        }

        return pubmedPrimary;
    }
}
