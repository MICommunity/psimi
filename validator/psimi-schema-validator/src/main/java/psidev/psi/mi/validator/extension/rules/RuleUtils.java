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

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.validator.extension.MiContext;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.Rule;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    public static final String FIGURE_LEGEND = "figure legend";
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

    public static Collection<ValidatorMessage> checkOrganism( OntologyManager ontologyManager,
                                      Organism organism,
                                      Rule rule,
                                      String objectType,
                                      String organismType ) {

        int taxId = organism.getTaxId();
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
                MiContext context = RuleUtils.buildContext(organism, "organism");
                // this is the root of newt, which users are not suppose to use to define their host organism
                return Collections.singleton( new ValidatorMessage( objectType + " with an invalid " + organismType +
                        " for which the taxid was: '" + taxId +
                        "'. Please choose a child term of the NEWT root.",
                        MessageLevel.ERROR,
                        context,
                        rule ) );

            case 0:
                context = RuleUtils.buildContext(organism, "organism");
                // this is the root of newt, which users are not suppose to use to define their host organism
                return Collections.singleton(( new ValidatorMessage( objectType + " with an invalid " + organismType +
                        " for which the taxid was: '" + taxId +
                        "'. Please choose a valid NEWT term.",
                        MessageLevel.ERROR,
                        context,
                        rule ) ));

            default:
                // check in NEWT if the taxid exists
                if ( taxId < 0 ) {
                    context = RuleUtils.buildContext(organism, "organism");
                    // break here
                    return Collections.singleton(( new ValidatorMessage( objectType + " with an invalid " + organismType +
                            " for which the taxid was: '" + taxId + "'.",
                            MessageLevel.ERROR,
                            context,
                            rule ) ));
                } else {
                    // TODO optimize via caching of valid taxid for a period of time (in a same file it is very likely that we will see only a handful of taxids.)
                    final OntologyAccess newt = ontologyManager.getOntologyAccess( "NEWT" );
                    final OntologyTermI newtTerm = newt.getTermForAccession( String.valueOf( taxId ) );
                    if ( newtTerm == null ) {
                        // could not find it
                        context = RuleUtils.buildContext(organism, "organism");
                        final String msg = objectType + " with an invalid " + organismType + ", the taxid given was '" +
                                taxId + "' which cannot be found in NEWT.";
                        return Collections.singleton(( new ValidatorMessage( msg,
                                MessageLevel.ERROR,
                                context,
                                rule ) ));
                    }
                }
        }
        return Collections.EMPTY_LIST;
    }

    public static Collection<ValidatorMessage> checkImexOrganism( OntologyManager ontologyManager,
                                          Organism organism,
                                          Rule rule,
                                          String objectType,
                                          String organismType ) {

        int taxId = organism.getTaxId();
        switch ( taxId ) {

            // special cases for Imex
            case -5:
                MiContext context = RuleUtils.buildContext(organism, "organism");
                // IMEX doesn't allow to use this term to define their host organism
                return Collections.singletonList(new ValidatorMessage(objectType + " with a " + organismType +
                        " for which the taxid was: '" + taxId +
                        "' is not valid. IMEx does not allow to choose " + taxId + " for the taxId of an organism. You can choose any NCBI taxID, -1 or -2",
                        MessageLevel.ERROR,
                        context,
                        rule));
            case -4:
                context = RuleUtils.buildContext(organism, "organism");
                // IMEX doesn't allow to use this term to define their host organism
                return Collections.singletonList( new ValidatorMessage( objectType + " with a " + organismType +
                        " for which the taxid was: '" + taxId +
                        "' is not valid. IMEx does not allow to choose " + taxId + " for the taxId of an organism. You can choose any NCBI taxID, -1 or -2.",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
            case -3:
                context = RuleUtils.buildContext(organism, "organism");
                // IMEX doesn't allow to use this term to define their host organism
                return Collections.singletonList( new ValidatorMessage( objectType + " with a " + organismType +
                        " for which the taxid was: '" + taxId +
                        "' is not valid. IMEx does not allow to choose " + taxId + " for the taxId of an organism. You can choose any NCBI taxID, -1 or -2.",
                        MessageLevel.ERROR,
                        context,
                        rule ) );
            default:
                return checkOrganism(ontologyManager, organism, rule, objectType, organismType);
        }
    }

    public static boolean isOfType( OntologyManager ontologyManager, final CvTerm type, final String miRef, final boolean includeChildren ) {

        if ( type == null ) {
            throw new IllegalArgumentException( "You must give a non null type" );
        }

        if ( miRef == null ) {
            throw new IllegalArgumentException( "You must give a non null miRef" );
        }

        String typeId = type.getMIIdentifier();

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
        return isOfType( ontologyManager, interactor.getInteractorType(), BioactiveEntity.BIOACTIVE_ENTITY_MI, false );
    }

    public static boolean isNucleicAcid( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.NUCLEIC_ACID_MI_REF, true );
    }

    public static boolean isProtein( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), Protein.PROTEIN_MI, false );
    }

    public static boolean isPeptide( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), Protein.PEPTIDE_MI, false );
    }

    public static boolean isBindingSite( OntologyManager ontologyManager, Feature feature ) {
        return isOfType( ontologyManager, feature.getType(), RuleUtils.BINDING_SITE, true );
    }

    public static Set<String> collectNames( Collection<OntologyTermI> refs ) {
        Set<String> ids = new HashSet<String>( refs.size() );
        for ( OntologyTermI termI : refs ) {
            ids.add( termI.getPreferredName().toLowerCase().trim());
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

    public static String extractObjectLabelFromXPath(String xpath){
        if (xpath == null){
            return null;
        }
        else if (xpath.contains("/features/")){
            return "feature";
        }
        else if (xpath.contains("/interactor/")){
            return "interactor";
        }
        else if (xpath.contains("/participants/")){
            return "participant";
        }
        else if (xpath.contains("/experiment/")){
            return "experiment";
        }
        else if (xpath.contains("interaction")){
            return "interaction";
        }
        else{
            return null;
        }
    }

    public static MiContext buildContext( Object object ) {
        MiContext context = new MiContext();
        if (object instanceof FileSourceContext){
            context.setLocator(((FileSourceContext) object).getSourceLocator());
        }

        return context;
    }

    public static MiContext buildContext( Object object, String objectLabel ) {
        MiContext context;
        context = new MiContext();
        if (object instanceof FileSourceContext){
            context.setLocator(((FileSourceContext) object).getSourceLocator());
        }

        context.setObjectLabel(objectLabel);
        return context;
    }
}
