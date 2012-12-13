/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ExperimentRule;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.Rule;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.*;

/**
 * Utilities for the MI25 Rules.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 2.0.0
 */
public final class RuleUtils {

    //////////////////////
    // Xref qualifier

    public static final String IDENTITY_MI_REF = "MI:0356";
    public static final String IDENTITY = "identity";

    public static final String INTERACTION_TYPE = "MI:0190";
    public static final String INTERACTOR_TYPE = "MI:0313";
    public static final String FEATURE_TYPE = "MI:0116";
    public static final String BINDING_SITE = "MI:0117";
    public static final String BIOLOGICAL_ROLE= "MI:0500";
    public static final String EXPERIMENTAL_ROLE = "MI:0495";

    /////////////////////////
    // Interactor types

    public static final String NUCLEIC_ACID_MI_REF = "MI:0318";
    public static final String DNA_MI_REF = "MI:0319";
    public static final String RNA_MI_REF = "MI:0320";
    public static final String PROTEIN_MI_REF = "MI:0326";
    public static final String PEPTIDE_MI_REF = "MI:0327";
    public static final String SMALL_MOLECULE_MI_REF = "MI:0328";
    public static final String BIOPOLYMER_MI_REF = "MI:0383";
    public static final String POLYSACCHARIDE_MI_REF = "MI:0904";
    public static final String RIBONUCLEIC_MI_REF = "MI:0320";
    public static final String RIBONUCLEIC = "ribonucleic acid";


    /////////////////////////
    // Databases

    public static final String INTERACTION_DATABASE_MI_REF = "MI:0461";
    public static final String SEQUENCE_DATABASE_MI_REF = "MI:0683";
    public static final String BIOACTIVE_ENTITY_DATABASE_MI_REF = "MI:2054";
    public static final String PSI_MI_REF = "MI:0488";
    public static final String PSI_MI = "psi-mi";
    public static final String UNIPROTKB = "uniprotkb";
    public static final String UNIPROTKB_MI_REF = "MI:0486";
    public static final String REFSEQ_MI_REF = "MI:0481";
    public static final String CHEBI = "chebi";
    public static final String CHEBI_MI_REF = "MI:0474";
    public static final String RESID = "resid";
    public static final String RESID_MI_REF = "MI:0248";
    public static final String CYGD_MI_REF = "MI:0464";
    public static final String INTACT = "intact";
    public static final String INTACT_MI_REF = "MI:0469";
    public static final String IMEX = "imex";
    public static final String IMEX_MI_REF = "MI:0670";
    public static final String EMBL = "ddbj/embl/genbank";
    public static final String EMBL_MI_REF = "MI:0475";
    public static final String BRENDA = "brenda";
    public static final String BRENDA_MI_REF = "MI:0864";
    public static final String CABRI = "cabri";
    public static final String CABRI_MI_REF = "MI:0246";
    public static final String CELL_ONTOLOGY = "cell ontology";
    public static final String CELL_ONTOLOGY_MI_REF = "MI:0831";
    public static final String TISSUE_LIST = "tissue list";
    public static final String TISSUE_LIST_MI_REF = "MI:0830";

    /////////////////////////
    // attribute type

    public static final String AUTHOR_CONFIDENCE = "author-confidence";
    public static final String AUTHOR_CONFIDENCE_MI_REF = "MI:0621";
    public static final String CONFIDENCE_MAPPING = "confidence-mapping";
    public static final String CONFIDENCE_MAPPING_MI_REF = "MI:0622";
    public static final String EXPERIMENT_ATTRIBUTE = "experiment attribute name";
    public static final String EXPERIMENT_ATTRIBUTE_MI_REF = "MI:0665";
    public static final String CONTACT_EMAIL = "contact-email";
    public static final String CONTACT_EMAIL_MI_REF = "MI:0634";
    public static final String URL = "MI:0614";
    public static final String EXP_MODIFICATION = "MI:0627";
    public static final String DATASET = "MI:0875";
    public static final String DATA_PROCESSING = "MI:0633";
    public static final String COMMENT = "MI:0612";
    public static final String CAUTION = "MI:0618";
    public static final String ANTIBODIES = "MI:0671";
    public static final String IMEX_CURATION = "MI:0959";
    public static final String FULL_COVERAGE = "MI:0957";
    public static final String CURATION_REQUEST = "MI:0873";
    public static final String AUTHOR_SUBMITTED = "MI:0878";
    public static final String PUBLICATION_YEAR = "MI:0886";
    public static final String JOURNAL = "MI:0885";
    public static final String AUTHOR_LIST = "MI:0636";
    public static final String LIBRARY_USED = "MI:0672";
    public static final String FIGURE_LEGEND = "figure-legend";
    public static final String FIGURE_LEGEND_MI_REF = "MI:0599";

    //////////////////////////
    // biological roles

    public static final String UNSPECIFIED_MI_REF = "MI:0499";
    public static final String ENZYME_MI_REF = "MI:0501";
    public static final String TARGET_MI_REF = "MI:0502";
    public static final String SELF_MI_REF = "MI:0503";


    //////////////////////////
    // experimental roles

    public static final String BAIT_MI_REF = "MI:0496";
    public static final String PREY_MI_REF = "MI:0498";
    public static final String ANCILARY_MI_REF = "MI:0684";
    public static final String NEUTRAL_MI_REF = "MI:0497";

    /////////////////////////////////
    // interaction detection method

    public static final String TWO_HYBRID_MI_REF = "MI:0018";
    public static final String TWO_HYBRID_ARRAY_MI_REF = "MI:0397";
    public static final String IMAGING_TECHNIQUE_MI_REF = "MI:0428";
    public static final String CROSS_LINKING_MI_REF = "MI:0030";
    public static final String PROTEIN_COMPLEMENTATION_MI_REF = "MI:0090";

    ///////////////////////////
    // Utility methods

    public static void checkOrganism( OntologyManager ontologyManager,
                                      Organism organism,
                                      Mi25Context context,
                                      Collection<ValidatorMessage> messages,
                                      Rule rule,
                                      String objectType,
                                      String organismType ) {

        int taxId = organism.getNcbiTaxId();
        switch ( taxId ) {

            // special cases in PSI-MI that do not exist in NEWT
            case -5:
                break;
            case -4:
                break;
            case -3:
                break;
            case -2:
                break;
            case -1:
                break;

            case 1:
                // this is the root of newt, which users are not suppose to use to define their host organism
                messages.add( new ValidatorMessage( objectType + " with an invalid " + organismType +
                        " for which the taxid was: '" + taxId +
                        "'. Please choose a child term of the NEWT root.",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                break;

            case 0:
                // this is the root of newt, which users are not suppose to use to define their host organism
                messages.add( new ValidatorMessage( objectType + " with an invalid " + organismType +
                        " for which the taxid was: '" + taxId +
                        "'. Please choose a valid NEWT term.",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                break;

            default:
                // check in NEWT if the taxid exists
                if ( taxId < 0 ) {
                    // break here
                    messages.add( new ValidatorMessage( objectType + " with an invalid " + organismType +
                            " for which the taxid was: '" + taxId + "'.",
                            MessageLevel.ERROR,
                            context,
                            rule ) );
                } else {
                    // TODO optimize via caching of valid taxid for a period of time (in a same file it is very likely that we will see only a handful of taxids.)
                    final OntologyAccess newt = ontologyManager.getOntologyAccess( "NEWT" );
                    final OntologyTermI newtTerm = newt.getTermForAccession( String.valueOf( taxId ) );
                    if ( newtTerm == null ) {
                        // could not find it
                        final String msg = objectType + " with an invalid " + organismType + ", the taxid given was '" +
                                taxId + "' which cannot be found in NEWT.";
                        messages.add( new ValidatorMessage( msg,
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
        }
    }

    public static void checkImexOrganism( OntologyManager ontologyManager,
                                          Organism organism,
                                          Mi25Context context,
                                          Collection<ValidatorMessage> messages,
                                          Rule rule,
                                          String objectType,
                                          String organismType ) {

        int taxId = organism.getNcbiTaxId();
        switch ( taxId ) {

            // special cases for Imex
            case -5:
                // IMEX doesn't allow to use this term to define their host organism
                messages.add( new ValidatorMessage( objectType + " with a " + organismType +
                        " for which the taxid was: '" + taxId +
                        "' is not valid. IMEx does not allow to choose " + taxId + " for the taxId of an organism. You can choose any NCBI taxID, -1 or -2",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                break;
            case -4:
                // IMEX doesn't allow to use this term to define their host organism
                messages.add( new ValidatorMessage( objectType + " with a " + organismType +
                        " for which the taxid was: '" + taxId +
                        "' is not valid. IMEx does not allow to choose " + taxId + " for the taxId of an organism. You can choose any NCBI taxID, -1 or -2.",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                break;
            case -3:
                // IMEX doesn't allow to use this term to define their host organism
                messages.add( new ValidatorMessage( objectType + " with a " + organismType +
                        " for which the taxid was: '" + taxId +
                        "' is not valid. IMEx does not allow to choose " + taxId + " for the taxId of an organism. You can choose any NCBI taxID, -1 or -2.",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
                break;
            default:
                checkOrganism(ontologyManager, organism, context, messages, rule, objectType, organismType);
        }
    }

    public static boolean isOfType( OntologyManager ontologyManager, final InteractorType type, final String miRef, final boolean includeChildren ) {

        if ( type == null ) {
            throw new IllegalArgumentException( "You must give a non null type" );
        }

        if ( miRef == null ) {
            throw new IllegalArgumentException( "You must give a non null miRef" );
        }

        String typeId = null;

        if( type.getXref() != null ) {
            typeId = type.getXref().getPrimaryRef().getId();
        }

        if( typeId != null ) {
            if ( includeChildren ) {
                final OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
                final Set<OntologyTermI> terms = mi.getValidTerms( miRef, true, true );

                for ( OntologyTermI term : terms ) {
                    if ( typeId.equals( term.getTermAccession() ) ) {
                        return true;
                    }
                }
            } else {
                if ( miRef.equals( typeId ) ) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isOfType( OntologyManager ontologyManager, final FeatureType type, final String miRef, final boolean includeChildren ) {

        if ( type == null ) {
            throw new IllegalArgumentException( "You must give a non null type" );
        }

        if ( miRef == null ) {
            throw new IllegalArgumentException( "You must give a non null miRef" );
        }

        String typeId = null;

        if( type.getXref() != null ) {
            typeId = type.getXref().getPrimaryRef().getId();
        }

        if( typeId != null ) {
            if ( includeChildren ) {
                final OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
                final Set<OntologyTermI> terms = mi.getValidTerms( miRef, true, true );

                for ( OntologyTermI term : terms ) {
                    if ( typeId.equals( term.getTermAccession() ) ) {
                        return true;
                    }
                }
            } else {
                if ( miRef.equals( typeId ) ) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isBiopolymer( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.BIOPOLYMER_MI_REF, true );
    }

    public static boolean isPolysaccharide( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.POLYSACCHARIDE_MI_REF, true );
    }

    public static boolean isDNA( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.DNA_MI_REF, true );
    }

    public static boolean isRNA( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.RNA_MI_REF, true );
    }

    public static boolean isSmallMolecule( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.SMALL_MOLECULE_MI_REF, false );
    }

    public static boolean isNucleicAcid( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.NUCLEIC_ACID_MI_REF, true );
    }

    public static boolean isProtein( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.PROTEIN_MI_REF, false );
    }

    public static boolean isPeptide( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.PEPTIDE_MI_REF, false );
    }

    public static boolean isBindingSite( OntologyManager ontologyManager, Feature feature ) {
        return isOfType( ontologyManager, feature.getFeatureType(), RuleUtils.BINDING_SITE, true );
    }

    /**
     * Builds a new colelction of references filtered according to the given parameters. If both filters are null, we
     * get a new collection containing all references given.
     *
     * @param xrefs      the collection of reference to build upon.
     * @param typeMiRef a Type Ref MI identifier, can be null.
     * @param dbMiRef   a database MI identifier, can be null.
     * @return a non null collection of references.
     */
    public static Collection<DbReference> searchReferences( Collection<DbReference> xrefs,
                                                            String typeMiRef,
                                                            String dbMiRef,
                                                            String accession ) {

        Collection<DbReference> refs = new ArrayList<DbReference>();

        if( xrefs != null ) {
            for ( DbReference ref : xrefs ) {
                if ( dbMiRef != null && !dbMiRef.equals( ref.getDbAc() ) ) {
                    continue;
                }
                if ( typeMiRef != null && !typeMiRef.equals( ref.getRefTypeAc() ) ) {
                    continue;
                }
                if ( accession != null && !accession.equals( ref.getId() ) ) {
                    continue;
                }
                refs.add( ref );
            }
        }

        return refs;
    }

    /**
     * Builds a new collection of references filtered according to the given parameters. If both filters are null, we
     * get a new collection containing all references given.
     *
     * @param xrefs      the collection of reference to build upon.
     * @param typeMiRefs a collection of  type reference MI identifier, can be null.
     * @param dbMiRefs   a collection of database MI identifier, can be null.
     * @param accessions   a collection of accession number supposed to match DbReference.id, can be null.
     * @return a non null collection of references.
     */
    public static Collection<DbReference> searchReferences( Collection<DbReference> xrefs,
                                                            Collection<String> typeMiRefs,
                                                            Collection<String> dbMiRefs,
                                                            Collection<String> accessions ) {

        Collection<DbReference> refs = new ArrayList<DbReference>();

        if( xrefs != null ) {
            for ( DbReference ref : xrefs ) {
                if ( dbMiRefs != null && !dbMiRefs.contains( ref.getDbAc() ) ) {
                    continue;
                }
                if ( typeMiRefs != null && !typeMiRefs.contains( ref.getRefTypeAc() ) ) {
                    continue;
                }
                if ( accessions != null && !accessions.contains( ref.getId() ) ) {
                    continue;
                }
                refs.add( ref );
            }
        }

        return refs;
    }

    public static Set<String> collectIds( Collection<DbReference> refs ) {
        Set<String> ids = new HashSet<String>( refs.size() );
        for ( DbReference ref : refs ) {
            ids.add( ref.getId() );
        }
        return ids;
    }

    public static Set<String> collectAccessions( Collection<OntologyTermI> refs ) {
        Set<String> ids = new HashSet<String>( refs.size() );
        for ( OntologyTermI termI : refs ) {
            ids.add( termI.getTermAccession() );
        }
        return ids;
    }

    /**
     * Builds a new collection of attributes filtered according to the given parameters. If both filters are null, we
     * get a new collection containing all attributess given.
     *
     * @param attributes the collection of attributes to build upon.
     * @param name       a name filter, can be null.
     * @param nameAc     accession number of the name attribute, can be null.
     * @return a non null collection of attributes.
     */
    public static Collection<Attribute> searchAttributes( Collection<Attribute> attributes, String name, String nameAc, Collection<ValidatorMessage> messages, Mi25Context context, ObjectRule rule) {

        Collection<Attribute> foundAttributes = new ArrayList<Attribute>( attributes.size() );

        for ( Attribute attribute : attributes ) {
            if( nameAc != null){
                if (!nameAc.equals( attribute.getNameAc() )) {

                    if (name != null){
                        if (name.equalsIgnoreCase( attribute.getName() )){
                            messages.add( new ValidatorMessage( "The attribute " + name + " (name attribute)  is associated with a nameAc which points to a different PSI-MI term. The nameAc always overwrites the name attribute in the attribute elements so the current attribute is not identified as " + name + " and it may not be what you wanted.",
                                    MessageLevel.WARN,
                                    context,
                                    rule ) );
                        }
                    }

                    continue;

                }
            }
            if( name != null) {
                if (!name.equalsIgnoreCase( attribute.getName() )){
                    continue;
                }
            }
            foundAttributes.add( attribute );
        }

        return foundAttributes;
    }

    public static Collection<DbReference> findByReferenceType( Collection<DbReference> dbReferences, String mi, String name, Collection<ValidatorMessage> messages, Mi25Context context, ObjectRule rule ) {
        Collection<DbReference> selectedReferences = new ArrayList<DbReference>( dbReferences.size() );
        for ( DbReference reference : dbReferences ) {
            if (mi != null){
                if (reference.hasRefTypeAc()){
                    if (mi.equals( reference.getRefTypeAc() )){
                        selectedReferences.add( reference );
                    }
                    else if (name != null){
                        if (name.equalsIgnoreCase( reference.getDb() )){
                            messages.add( new ValidatorMessage( "The reference Type " + name + " (refType attribute)  is associated with a refTypeAc which points to a different PSI-MI term. The refTypeAc always overwrites the refType attribute in the cross references so the current reference type is not identified as " + name + " and it may not be what you wanted.",
                                    MessageLevel.WARN,
                                    context,
                                    rule ) );
                        }
                    }
                }
            }
            else if (name != null && reference.hasRefType()){
                if (name.equalsIgnoreCase( reference.getRefType() )){
                    selectedReferences.add( reference );
                }
            }
        }
        return selectedReferences;
    }

    public static  Collection<DbReference> findByDatabaseAndReferenceType( Collection<DbReference> dbReferences, String dbmi, String dbname, String qmi, String qname, Collection<ValidatorMessage> messages, Mi25Context context, ObjectRule rule ) {
        Collection<DbReference> selectedDatabases = findByDatabase(dbReferences, dbmi, dbname, messages, context, rule);
        Collection<DbReference> selectedReferenceTypes = findByReferenceType(selectedDatabases, qmi, qname, messages, context, rule);

        return selectedReferenceTypes;
    }

    public static Collection<DbReference> findByDatabase( Collection<DbReference> dbReferences, String mi, String name, Collection<ValidatorMessage> messages, Mi25Context context, ObjectRule rule ) {
        Collection<DbReference> selectedReferences = new ArrayList<DbReference>( dbReferences.size() );
        for ( DbReference reference : dbReferences ) {

            if (mi != null && reference.hasDbAc()){
                if (mi.equals( reference.getDbAc() )){
                    selectedReferences.add( reference );
                }
                else if (name != null){
                    if (name.equalsIgnoreCase( reference.getDb() )){
                        messages.add( new ValidatorMessage( "The database " + name + " (db attribute)  is associated with a dbAc which points to a different PSI-MI term. The dbAc always overwrites the db attribute in the cross references so the current database is not identified as " + name + " and it may not be what you wanted.",
                                MessageLevel.WARN,
                                context,
                                rule ) );
                    }
                }
            }
            else if(name != null && reference.getDb() != null){
                if (name.equals( reference.getDb() )){
                    selectedReferences.add( reference );
                }
            }
        }
        return selectedReferences;
    }

    public static Collection<Attribute> findByAttributeName( Collection<Attribute> attributes, String mi, String name ) {
        Collection<Attribute> selectedAttribute = new ArrayList<Attribute>( attributes.size() );
        for ( Attribute attribute : attributes ) {
            if (mi != null && attribute.hasNameAc()){
                if (mi.equals( attribute.getNameAc() )){
                    selectedAttribute.add( attribute );
                }
            }
            else if(name != null && attribute.getName() != null){
                if (name.equalsIgnoreCase( attribute.getName() )){
                    selectedAttribute.add( attribute );
                }
            }
        }
        return selectedAttribute;
    }

    /**
     * Checks that a psi mi cross reference is present and well formatted. The controlled vocabulary rules will already check if the controlled vocabulary is valid
     * @param container
     * @param messages
     * @param context
     * @param rule
     * @param mi
     */
    public static void checkPsiMIXRef(XrefContainer container, List<ValidatorMessage> messages, Mi25Context context, Rule rule, String mi){
        Xref xref = container.getXref();
        String containerName = container.getClass().getSimpleName();

        if (xref != null){
            Collection<DbReference> allDbRef = xref.getAllDbReferences();

            if (!allDbRef.isEmpty()){
                // search for database : db="psi-mi" dbAc="MI:0488"
                Collection<DbReference> psiMiReferences = RuleUtils.findByDatabaseAndReferenceType( allDbRef,"MI:0488", "psi-mi","MI:0356",  "identical object", messages, context, (ObjectRule) rule );

                if (!psiMiReferences.isEmpty()){
                    // There is only one psi-mi database reference for an InteractionDetectionMethod
                    if (psiMiReferences.size() != 1){

                        messages.add( new ValidatorMessage( "The "+ containerName + " has "+ psiMiReferences.size() +" psi-mi cross references with type 'identity' and only one is allowed.",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
                else {
                    messages.add( new ValidatorMessage( "The "+ containerName + " does not have a psi-mi cross reference (db = 'psi-mi' dbAc='MI:0488') with type 'identity' (refType = 'identity' refTypeAc='MI:0356').",
                            MessageLevel.ERROR,
                            context,
                            rule ) );
                }
            }
            else {
                messages.add( new ValidatorMessage( "The "+ containerName + " does not have a psi-mi cross reference (db = 'psi-mi' dbAc='MI:0488') with type 'identity' (refType = 'identity' refTypeAc='MI:0356'). One psi-mi cross reference is mandatory ( must be any child of "+ mi +").",
                        MessageLevel.ERROR,
                        context,
                        rule ) );

            }
        }
        else {
            messages.add( new ValidatorMessage( "The "+ containerName + " does not have a psi-mi cross reference (db = 'psi-mi' dbAc='MI:0488' in the XRef/primaryRef element) with type 'identity'(refType = 'identity' refTypeAc='MI:0356'). One psi-mi cross reference is mandatory ( must be any child of \"+ mi +\").",
                    MessageLevel.ERROR,
                    context,
                    rule ) );

        }
    }

    /**
     * Checks that a psi mi cross reference is present and well formatted. The controlled vocabulary rules will already check if the controlled vocabulary is valid
     * @param container
     * @param messages
     * @param context
     * @param rule
     * @param mi
     */
    public static void checkPsiMIOrModXRef(XrefContainer container, List<ValidatorMessage> messages, Mi25Context context, Rule rule, String mi){
        Xref xref = container.getXref();
        String containerName = container.getClass().getSimpleName();

        if (xref != null){
            Collection<DbReference> allDbRef = xref.getAllDbReferences();

            if (!allDbRef.isEmpty()){
                // search for database : db="psi-mi" dbAc="MI:0488"
                Collection<DbReference> psiMiReferences = RuleUtils.findByDatabaseAndReferenceType( allDbRef,"MI:0488", "psi-mi","MI:0356",  "identical object", messages, context, (ObjectRule) rule );
                Collection<DbReference> psiModReferences = RuleUtils.findByDatabaseAndReferenceType( allDbRef,"MI:0897", "psi-mod","MI:0356",  "identical object", messages, context, (ObjectRule) rule );

                if (!psiModReferences.isEmpty() && !psiMiReferences.isEmpty()){
                    messages.add( new ValidatorMessage( "The "+ containerName + " has "+ psiModReferences.size() +" psi-mod cross references with type 'identity' and "+ psiMiReferences.size() +" psi-mi cross references with type 'identity'. As it is confusing, it is better to give only one identity cross reference (psi-mi or psi-mod)",
                            MessageLevel.WARN,
                            context,
                            rule ) );
                }
                else if (!psiModReferences.isEmpty()){
                    if (psiModReferences.size() != 1){
                        messages.add( new ValidatorMessage( "The "+ containerName + " has "+ psiModReferences.size() +" psi-mod cross references with type 'identity' and only one is allowed.",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }

                }
                else if (!psiMiReferences.isEmpty()){
                    // There is only one psi-mi database reference for an InteractionDetectionMethod
                    if (psiMiReferences.size() != 1){

                        messages.add( new ValidatorMessage( "The "+ containerName + " has "+ psiMiReferences.size() +" psi-mi cross references with type 'identity' and only one is allowed.",
                                MessageLevel.ERROR,
                                context,
                                rule ) );
                    }
                }
                else {
                    messages.add( new ValidatorMessage( "The "+ containerName + " does not have a psi-mi or psi-mod cross reference (db = 'psi-mi' dbAc='MI:0488' or db = 'psi-mod' dbAc='MI:0897') with type 'identity' (refType = 'identity' refTypeAc='MI:0356').",
                            MessageLevel.ERROR,
                            context,
                            rule ) );
                }
            }
            else {
                messages.add( new ValidatorMessage( "The "+ containerName + " does not have a psi-mi or psi-mod cross reference (db = 'psi-mi' dbAc='MI:0488' or db = 'psi-mod' dbAc='MI:0897') with type 'identity' (refType = 'identity' refTypeAc='MI:0356'). One psi-mi cross reference is mandatory ( must be any child of "+ mi +").",
                        MessageLevel.ERROR,
                        context,
                        rule ) );

            }
        }
        else {
            messages.add( new ValidatorMessage( "The "+ containerName + " does not have a psi-mi or psi-mod cross reference (db = 'psi-mi' dbAc='MI:0488' or db = 'psi-mod' dbAc='MI:0897' in the XRef/primaryRef element) with type 'identity'(refType = 'identity' refTypeAc='MI:0356'). One psi-mi cross reference is mandatory ( must be any child of \"+ mi +\").",
                    MessageLevel.ERROR,
                    context,
                    rule ) );

        }
    }

    public static void checkPresenceOfAttributeInExperiment(ExperimentDescription experiment, List<ValidatorMessage> messages, Mi25Context context, Mi25ExperimentRule experimentRule, String attMi, String attname){
        // An experiment must have at least one attribute 'imex-curation' and one attribute 'full coverage'
        if (experiment.hasAttributes()){
            // The attributes of the experiment
            Collection<Attribute> attributes = experiment.getAttributes();
            // The attributes with a name/MI attName/attMI
            Collection<Attribute> attributeName = RuleUtils.findByAttributeName(attributes, attMi, attname);

            if (attributeName.isEmpty()){
                messages.add( new ValidatorMessage( "The experiment does not have an attribute '"+attname+"' ("+attMi+") and it is required for IMEx. ",
                        MessageLevel.ERROR,
                        context,
                        experimentRule ) );
            }
        }
        else {
            messages.add( new ValidatorMessage( "The experiment does not have any attributes. At least one attribute '"+attname+"' ("+attMi+") is required for IMEx. ",
                    MessageLevel.ERROR,
                    context,
                    experimentRule ) );
        }

    }

    /**
     * Find the experiments of the interaction that the experimentRef referred to
     * @param interaction
     * @param experimentRefs
     * @return
     */
    public static Collection<ExperimentDescription> collectExperiment(Interaction interaction, Collection<ExperimentRef> experimentRefs) {
        ArrayList<ExperimentDescription> collectedExps = new ArrayList<ExperimentDescription>();

        if( experimentRefs != null && !experimentRefs.isEmpty() ) {
            for (ExperimentRef ref : experimentRefs) {
                for (ExperimentDescription ed : interaction.getExperiments()) {
                    if( ed.getId() == ref.getRef() ) {
                        collectedExps.add( ed );
                    }
                }
            }
        } else {
            collectedExps.addAll( interaction.getExperiments() );
        }

        return collectedExps;
    }
}
