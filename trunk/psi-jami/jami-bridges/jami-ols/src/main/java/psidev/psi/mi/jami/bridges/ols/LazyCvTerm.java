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

    public LazyCvTerm(Query queryService, String fullName, Xref identityRef, String ontologyName) {
        super("");
        if (queryService == null){
            throw new IllegalArgumentException("The lazy cv term needs the Ols query service which cannot be null.");
        }
        this.queryService = queryService;

        setFullName(fullName);

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
            initialiseMetaData(getIdentifiers().iterator().next());
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
        if (!hasXrefs){
            initialiseOlsXrefs();
        }
        return super.getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        if (!hasAnnotations){
            initialiseOlsAnnotations();
        }
        return super.getAnnotations();
    }

    public Collection<Alias> getSynonyms() {
        if (!hasSynonyms) {
            initialiseMetaData(getIdentifiers().iterator().next());
        }

        return super.getSynonyms();
    }

    @Override
    public String toString() {
        return (getMIIdentifier() != null ? getMIIdentifier() : (getMODIdentifier() != null ? getMODIdentifier() : (getPARIdentifier() != null ? getPARIdentifier() : "-"))) + " ("+getFullName()+")";
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
    private void initialiseMetaData(Xref identifier){
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
                            super.getAnnotations().add(AnnotationUtils.createComment(description));
                        }
                        // comment
                        else if (OlsUtils.DEFINITION_KEY.equalsIgnoreCase(keyName) || keyName.startsWith(OlsUtils.COMMENT_KEY + OlsUtils.META_DATA_SEPARATOR)){
                            String comment = (String) metaDataMap.get(keyName);
                            super.getAnnotations().add(AnnotationUtils.createComment(comment));
                        }
                        else {
                            String synonym = (String) metaDataMap.get(keyName);
                            processSynonym(keyName, synonym);
                        }
                    }
                }
            }

            hasLoadedMetadata = true;
        } catch (RemoteException e) {
            throw new LazyTermLoadingException("Impossible to load OLS metada for " + identifier.getId(),e);
        }
    }

    @Override
    protected void processSynonym(String synonymName, String synonym) {

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
        else if (synonymName.startsWith(OlsUtils.ALIAS_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.ALIAS_IDENTIFIER.equalsIgnoreCase(synonymName)){
            this.aliases.add(synonym);
        }
        if (synonymName.startsWith(OlsUtils.SHORTLABEL_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR)){
            this.shortLabel = synonym.toLowerCase();
        }
        else if (synonymName.startsWith(OlsUtils.EXACT_SYNONYM_KEY + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.EXACT_SYNONYM_KEY.equalsIgnoreCase(synonymName)){
            this.aliases.add(synonym);
        }
        else if (synonymName.startsWith(OlsUtils.MOD_ALIAS_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.MOD_ALIAS_IDENTIFIER.equalsIgnoreCase(synonymName)){
            this.aliases.add(synonym);
        }
        else if (synonymName.startsWith(OlsUtils.RESID_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.RESID_IDENTIFIER.equalsIgnoreCase(synonymName)){
            this.aliases.add(synonym);
        }
        else if (synonymName.startsWith(OlsUtils.RESID_MISNOMER_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.RESID_MISNOMER_IDENTIFIER.equalsIgnoreCase(synonymName)){
            this.aliases.add(synonym);
        }
        else if (synonymName.startsWith(OlsUtils.RESID_NAME_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.RESID_NAME_IDENTIFIER.equalsIgnoreCase(synonymName)){
            this.aliases.add(synonym);
        }
        else if (synonymName.startsWith(OlsUtils.RESID_SYSTEMATIC_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.RESID_SYSTEMATIC_IDENTIFIER.equalsIgnoreCase(synonymName)){
            this.aliases.add(synonym);
        }
        else if (synonymName.startsWith(OlsUtils.UNIPROT_FEATURE_IDENTIFIER + OlsUtils.META_DATA_SEPARATOR) || OlsUtils.UNIPROT_FEATURE_IDENTIFIER.equalsIgnoreCase(synonymName)){
            this.aliases.add(synonym);
        }
    }

    /**
     * Process the definition String
     * @param definition
     * @return
     */
    protected Annotation processDefinition(String definition) {
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
    }

    /**
     * Process the other information in the description
     * @param otherInfoString
     * @return true if an obsolete annotation has been added
     */
    protected Annotation processInfoInDescription(String otherInfoString, int defLength) {

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
                    synonyms.add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, (String) metaDataMap.get(key)));
                }else if (keyName.startsWith(EXACT_SYNONYM_KEY)){
                    synonyms.add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, (String) metaDataMap.get(key)));
                }
            }
            return synonyms;
        }else{
            return Collections.EMPTY_LIST;
        }
    }

    private void initialiseOlsXrefs(){
        super.getXrefs();
        hasXrefs = true;
    }

    private void initialiseOlsAnnotations(){
        super.getAnnotations();
        hasAnnotations = true;
    }
}
