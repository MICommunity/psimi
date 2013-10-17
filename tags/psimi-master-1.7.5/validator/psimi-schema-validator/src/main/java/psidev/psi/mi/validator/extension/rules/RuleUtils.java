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
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.Rule;

import java.util.Collection;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

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
    public static final String CHEBI_MI_REF = "MI:0474";
    public static final String RESID = "resid";
    public static final String RESID_MI_REF = "MI:0248";
    public static final String CYGD_MI_REF = "MI:0464";
    public static final String INTACT = "intact";
    public static final String INTACT_MI_REF = "MI:0469";
    public static final String IMEX = "imex";
    public static final String IMEX_MI_REF = "MI:0670";

    /////////////////////////
    // attribute type

    public static final String AUTHOR_CONFIDENCE = "author-confidence";
    public static final String AUTHOR_CONFIDENCE_MI_REF = "MI:0621";
    public static final String CONFIDENCE_MAPPING = "confidence-mapping";
    public static final String CONFIDENCE_MAPPING_MI_REF = "MI:0622";

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

    /////////////////////////////////
    // interaction detection method

    public static final String TWO_HYBRID_MI_REF = "MI:0018";
    public static final String TWO_HYBRID_ARRAY_MI_REF = "MI:0397";
    public static final String IMAGING_TECHNIQUE_MI_REF = "MI:0428";

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
                                                    MessageLevel.WARN,
                                                    context,
                                                    rule ) );
                break;

            case 0:
                // this is the root of newt, which users are not suppose to use to define their host organism
                messages.add( new ValidatorMessage( objectType + " with an invalid " + organismType +
                                                    " for which the taxid was: '" + taxId +
                                                    "'. Please choose a valid NEWT term.",
                                                    MessageLevel.WARN,
                                                    context,
                                                    rule ) );
                break;

            default:
                // check in NEWT if the taxid exists
                if ( taxId < 0 ) {
                    // break here
                    messages.add( new ValidatorMessage( objectType + " with an invalid " + organismType +
                                                        " for which the taxid was: '" + taxId + "'.",
                                                        MessageLevel.WARN,
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
                                                            MessageLevel.WARN,
                                                            context,
                                                            rule ) );
                    }
                }
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

    public static boolean isBiopolymer( OntologyManager ontologyManager, Interactor interactor ) {
        return isOfType( ontologyManager, interactor.getInteractorType(), RuleUtils.BIOPOLYMER_MI_REF, true );
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
    public static Collection<Attribute> searchAttributes( Collection<Attribute> attributes, String name, String nameAc ) {

        Collection<Attribute> foundAttributes = new ArrayList<Attribute>( attributes.size() );

        if ( attributes != null ) {
            for ( Attribute attribute : attributes ) {
                if( nameAc != null && !nameAc.equals( attribute.getNameAc() ) ) {
                    continue;
                }
                if( name != null && !name.equals( attribute.getName() ) ) {
                    continue;
                }
                foundAttributes.add( attribute );
            }
        }

        return foundAttributes;
    }
}