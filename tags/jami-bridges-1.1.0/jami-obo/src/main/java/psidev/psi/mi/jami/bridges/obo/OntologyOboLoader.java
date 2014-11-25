package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultOntologyTerm;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.ols.model.interfaces.DbXref;
import uk.ac.ebi.ols.model.interfaces.Term;
import uk.ac.ebi.ols.model.interfaces.TermRelationship;
import uk.ac.ebi.ols.model.interfaces.TermSynonym;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * The ontologyOboLoader is a loader of OntologyTerm with parents and children
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class OntologyOboLoader extends AbstractOboLoader<OntologyTerm> {

    private static final String SHORTLABEL_IDENTIFIER = "Unique short label curated by PSI-MI";
    private static final String ALIAS_IDENTIFIER = "Alternate label curated by PSI-MI";
    private static final String MOD_SHORTLABEL_IDENTIFIER = "Short label curated by PSI-MOD";
    protected static final String EXACT_KEY = "exact";
    private static final String MOD_ALIAS_IDENTIFIER = "Alternate name curated by PSI-MOD"; //
    private static final String RESID_IDENTIFIER = "Alternate name from RESID"; //
    private static final String RESID_MISNOMER_IDENTIFIER = "Misnomer tagged alternate name from RESID";
    private static final String RESID_NAME_IDENTIFIER = "Name from RESID"; //
    private static final String RESID_SYSTEMATIC_IDENTIFIER = "Systematic name from RESID";   //
    private static final String UNIPROT_FEATURE_IDENTIFIER = "Protein feature description from UniProtKB";
    protected static final int XREF_TYPE = 3;
    private static final String PMID_APPLICATION = "PMID for application instance";
    private static final String SO = "so";
    private static final String SO_MI_REF = "MI:0601";
    private static final String HTTP_DEF = "http";
    private static final String XREF_VALIDATION_REGEXP = "id-validation-regexp";
    private static final String XREF_VALIDATION_REGEXP_MI_REF = "MI:0628";
    private static final String SEARCH_URL = "search-url";
    private static final String SEARCH_URL_MI_REF = "MI:0615";
    private static final String QUOTE = "&quot;";
    protected static final String META_XREF_SEPARATOR = ":";
    protected static final String LINE_BREAK = "\n";
    protected static final String COMMENT_KEY = "comment";
    protected static final String PMID = "PMID";
    protected static final String METHOD_REFERENCE = "method reference";
    protected static final String METHOD_REFERENCE_MI_REF = "MI:0357";
    private static final String GO = "go";
    private static final String GO_MI_REF = "MI:0448";
    protected static final String RESID = "resid";
    protected static final String RESID_MI_REF = "MI:0248";

    public OntologyOboLoader(CvTerm database) {
        super(database);
    }

    public OntologyOboLoader(String databaseName) {
        super(databaseName);
    }

    public void buildOntology(Map<String, OntologyTerm> id2Terms, Map<String, OntologyTerm> name2Terms) {

        super.buildOntology(id2Terms, name2Terms);
        buildTermRelationships(id2Terms);
    }

    @Override
    protected OntologyTerm instantiateNewTerm(String name, Xref identity) {
        OntologyTerm ontologyTerm = new DefaultOntologyTerm("", name, identity);
        return ontologyTerm;
    }

    @Override
    protected void createDefinitionFor(String def, OntologyTerm term) {
        if (term.getDefinition() != null){
            term.setDefinition(term.getDefinition() + LINE_BREAK + def);
        }
        else{
            term.setDefinition(def);
        }
    }

    /**
     * Process the annotations of a term
     * @param term
     */
    protected void processAnnotations(Term term, OntologyTerm ontologyTerm) {
        Collection<uk.ac.ebi.ols.model.interfaces.Annotation> annotations = term.getAnnotations();

        if (annotations != null){
            for (uk.ac.ebi.ols.model.interfaces.Annotation annot : annotations){
                // only one comment with type null
                if (annot.getAnnotationType() != null && COMMENT_KEY.equals(annot.getAnnotationType())){
                    ontologyTerm.getAnnotations().add(AnnotationUtils.createComment(annot.getAnnotationStringValue()));
                }
            }
        }
    }

    protected void processXrefs(Term term, OntologyTerm ontologyTerm) {
        Collection<DbXref> dbXrefs = term.getXrefs();

        if (dbXrefs != null){
            String pubmedPrimary = null;
            for (DbXref xref : dbXrefs){

                if (xref.getXrefType() == XREF_TYPE){
                    if (xref.getAccession() == null){
                        processXref(xref.getDbName(), xref.getDescription(), ontologyTerm);
                    }
                    else {
                        processXref(xref.getDbName(), xref.getAccession(), ontologyTerm);
                    }
                }
                else {
                    pubmedPrimary = processXrefDefinition(xref.toString(), xref.getDbName(), xref.getAccession(), pubmedPrimary, ontologyTerm);
                }
            }
        }
    }

    protected String processXrefDefinition(String xref, String database, String accession, String pubmedPrimary, OntologyTerm ontologyTerm) {

        if ( PMID.equalsIgnoreCase(database) ) {
            if (pubmedPrimary == null){
                pubmedPrimary = xref;

                Xref primaryPubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, Xref.PRIMARY, Xref.PRIMARY_MI);
                ontologyTerm.getXrefs().add(primaryPubmedRef);
            }
            else {
                Xref pubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, METHOD_REFERENCE, METHOD_REFERENCE_MI_REF);
                ontologyTerm.getXrefs().add(pubmedRef);
            }
        }
        else if ( PMID_APPLICATION.equalsIgnoreCase(database) ) {
            Xref pubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            ontologyTerm.getXrefs().add(pubmedRef); // MI not MOD
        } else if ( GO.equalsIgnoreCase(database) ) {
            Xref goRef = XrefUtils.createXrefWithQualifier(GO, GO_MI_REF, database + ":" + accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            ontologyTerm.getXrefs().add(goRef); // MI not MOD
        } else if ( RESID.equalsIgnoreCase(database) ) {
            Xref resXref = XrefUtils.createXrefWithQualifier(RESID, RESID_MI_REF, accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            ontologyTerm.getXrefs().add(resXref);
        } else if ( SO.equalsIgnoreCase(database) ) {
            Xref soRef = XrefUtils.createXrefWithQualifier(SO, SO_MI_REF, database + ":" + accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            ontologyTerm.getXrefs().add(soRef);  // MI not MOD
        }else if ( Xref.PUBMED.equalsIgnoreCase(database) ) {
            if (pubmedPrimary == null){
                pubmedPrimary = xref;

                Xref primaryPubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, Xref.PRIMARY, Xref.PRIMARY_MI);
                ontologyTerm.getXrefs().add(primaryPubmedRef);
            }
            else {
                Xref pubmedRef = XrefUtils.createXrefWithQualifier(Xref.PUBMED, Xref.PUBMED_MI, accession, METHOD_REFERENCE, METHOD_REFERENCE_MI_REF);
                ontologyTerm.getXrefs().add(pubmedRef);
            }
        }
        else if ( RESID.equalsIgnoreCase(database) ) {
            Xref resXref = XrefUtils.createXrefWithQualifier(RESID, RESID_MI_REF, accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            ontologyTerm.getXrefs().add(resXref);
        }else if ( Xref.CHEBI.equalsIgnoreCase(database) ) {
            Xref chebiRef = XrefUtils.createXrefWithQualifier(Xref.CHEBI, Xref.CHEBI_MI, accession, Xref.SEE_ALSO, Xref.SEE_ALSO_MI);
            ontologyTerm.getXrefs().add(chebiRef);  // MOD xref
        } else if ( Annotation.URL.equalsIgnoreCase(database) ) {
            ontologyTerm.getAnnotations().add(AnnotationUtils.createAnnotation(Annotation.URL, Annotation.URL_MI, accession));
        }

        return pubmedPrimary;
    }

    protected void processXref(String db, String accession, OntologyTerm ontologyTerm) {
        // xref validation regexp
        if (XREF_VALIDATION_REGEXP.equalsIgnoreCase(db)){

            String annotationText = accession.trim();

            if (annotationText.startsWith(QUOTE)){
                annotationText = annotationText.substring(QUOTE.length());
            }
            if (annotationText.endsWith(QUOTE)){
                annotationText = annotationText.substring(0, annotationText.indexOf(QUOTE));
            }

            Annotation validation = AnnotationUtils.createAnnotation(XREF_VALIDATION_REGEXP, XREF_VALIDATION_REGEXP_MI_REF, annotationText);  // MI xref
            ontologyTerm.getAnnotations().add(validation);
        }
        // search url
        else if (db == null && accession.startsWith(SEARCH_URL)){
            String url = accession.substring(SEARCH_URL.length());

            if (url.startsWith("\\")){
                url = url.substring(1);
            }
            if (url.endsWith("\\")){
                url = url.substring(0, url.length() - 1);
            }

            Annotation validation = AnnotationUtils.createAnnotation(SEARCH_URL, SEARCH_URL_MI_REF, url);  // MI xref
            ontologyTerm.getAnnotations().add(validation);
        }
        else if (db.equalsIgnoreCase(SEARCH_URL)){
            String url = accession.trim();

            Annotation validation = AnnotationUtils.createAnnotation(SEARCH_URL, SEARCH_URL_MI_REF, url);  // MI xref
            ontologyTerm.getAnnotations().add(validation);
        }
        else if (db.startsWith(SEARCH_URL)){
            String prefix = db.substring(SEARCH_URL.length());
            String url = prefix + META_XREF_SEPARATOR + accession;

            if (url.startsWith("\"")){
                url = url.substring(1);
            }
            if (url.endsWith("\"")){
                url = url.substring(0, url.length() - 1);
            }

            Annotation validation = AnnotationUtils.createAnnotation(SEARCH_URL, SEARCH_URL_MI_REF, url);  // MI xref
            ontologyTerm.getAnnotations().add(validation);
        }
    }

    /**
     * Process the definition of a term
     * @param term
     */
    protected void processDefinition(Term term, OntologyTerm ontologyTerm) {

        String definition = term.getDefinition();
        processDefinition(definition, ontologyTerm);
    }

    /**
     * Process the definition String
     * @param definition
     * @return
     */
    protected void processDefinition(String definition, OntologyTerm ontologyTerm) {
        if ( definition.contains( LINE_BREAK ) ) {
            String[] defArray = definition.split( LINE_BREAK );

            String otherInfoString = null;

            if ( defArray.length == 2 ) {
                ontologyTerm.setDefinition(defArray[0]);
                otherInfoString = defArray[1];
                processInfoInDescription(definition, otherInfoString, ontologyTerm);
            } else if ( defArray.length > 2 ) {
                ontologyTerm.setDefinition(defArray[0]);

                for (int i = 1; i < defArray.length; i++){
                    otherInfoString = defArray[i];
                    processInfoInDescription(definition, otherInfoString, ontologyTerm);
                }
            }
        }
        else {
            processInfoInDescription(definition, definition, ontologyTerm);
        }
    }

    /**
     * Process the other information in the description
     * @param definition
     * @param otherInfoString
     * @return true if an obsolete annotation has been added
     */
    protected void processInfoInDescription(String definition, String otherInfoString, OntologyTerm ontologyTerm) {

        // URL
        if ( otherInfoString.startsWith( HTTP_DEF ) ) {
            ontologyTerm.getAnnotations().add(AnnotationUtils.createAnnotation(Annotation.URL, Annotation.URL_MI, otherInfoString));
        }
        else if (otherInfoString.contains( HTTP_DEF )){
            String[] defArray = otherInfoString.split( HTTP_DEF );
            String def = null;

            if ( defArray.length == 2 ) {
                def = defArray[0];
                ontologyTerm.getAnnotations().add(AnnotationUtils.createAnnotation(Annotation.URL, Annotation.URL_MI, HTTP_DEF + defArray[1]));
            } else if ( defArray.length > 2 ) {
                def = defArray[0];
                ontologyTerm.getAnnotations().add(AnnotationUtils.createAnnotation(Annotation.URL, Annotation.URL_MI, otherInfoString.substring(def.length())));
            }

            if (ontologyTerm.getDefinition() == null){
                ontologyTerm.setDefinition(otherInfoString);
            }
            else {
                ontologyTerm.setDefinition(ontologyTerm.getDefinition() + LINE_BREAK + def);
            }
        }
        // simple definition
        else {
            if (definition.startsWith(otherInfoString)){
                ontologyTerm.setDefinition(otherInfoString);
            }
            else {
                ontologyTerm.setDefinition(ontologyTerm.getDefinition() + LINE_BREAK + otherInfoString);
            }
        }
    }

    protected void processSynonyms(Term term, OntologyTerm ontologyTerm) {
        Collection<TermSynonym> synonyms = term.getSynonyms();

        if (synonyms != null){
            for (TermSynonym synonym : synonyms){
                Term synonymType = synonym.getSynonymType();
                //PSI-MOD-label for MOD
                if (synonymType != null){
                    if (SHORTLABEL_IDENTIFIER.equalsIgnoreCase(synonymType.getName()) || MOD_SHORTLABEL_IDENTIFIER.equalsIgnoreCase(synonymType.getName())){
                        ontologyTerm.setShortName(synonym.getSynonym());
                    }

                    else if (ALIAS_IDENTIFIER.equalsIgnoreCase(synonymType.getName())
                            || MOD_ALIAS_IDENTIFIER.equalsIgnoreCase(synonymType.getName())
                            || RESID_IDENTIFIER.equalsIgnoreCase(synonymType.getName())
                            || RESID_MISNOMER_IDENTIFIER.equalsIgnoreCase(synonymType.getName())
                            || RESID_NAME_IDENTIFIER.equalsIgnoreCase(synonymType.getName())
                            || RESID_SYSTEMATIC_IDENTIFIER.equalsIgnoreCase(synonymType.getName())
                            || UNIPROT_FEATURE_IDENTIFIER.equalsIgnoreCase(synonymType.getName())
                            || EXACT_KEY.equalsIgnoreCase(synonymType.getName())){
                        ontologyTerm.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym.getSynonym()));
                    }
                }
            }
        }
    }

    protected void processShortLabel(Term term, OntologyTerm ontologyTerm) {
        if (ontologyTerm.getShortName().length() == 0){
            ontologyTerm.setShortName(term.getName());
        }
    }

    protected void buildTermRelationships(Map<String, OntologyTerm> id2Terms) {
        // 2. build hierarchy based on the relations of the Terms
        for ( Iterator iterator = ontBean.getTerms().iterator(); iterator.hasNext(); ) {
            Term term = ( Term ) iterator.next();

            if ( term.getRelationships() != null ) {
                for ( Iterator iterator1 = term.getRelationships().iterator(); iterator1.hasNext(); ) {
                    TermRelationship relation = ( TermRelationship ) iterator1.next();

                    addLinkToTerm( relation.getObjectTerm().getIdentifier(),
                            relation.getSubjectTerm().getIdentifier(), id2Terms);
                }
            }
        }
    }

    protected void addLinkToTerm(String parentId, String childId, Map<String, OntologyTerm> id2Terms){
        OntologyTerm child = id2Terms.get( childId );
        OntologyTerm parent = id2Terms.get( parentId );

        if ( child == null ) {
            throw new NullPointerException( "You must give a non null child" );
        }

        if ( parent == null ) {
            throw new NullPointerException( "You must give a non null parent" );
        }

        if ( !child.getParents().contains(parent) ) {
            child.getParents().add(parent);
        }

        if ( !parent.getChildren().contains(child) ) {
            parent.getChildren().add(child);
        }
    }
}
