package psidev.psi.mi.validator.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b> PSI-MI 2.5.1 Specific Ontology </b>.
 * <p/>
 *
 * @author Matthias Oesterheld
 * @version $Id$
 * @since 04.01.2006; 15:37:07
 */
public class Mi25Ontology {

    public static final Log log = LogFactory.getLog( Mi25Ontology.class );

    //////////////////////////////
    // Cosntants

    public static final String PUBMED = "pubmed";
    public static final String DOI = "digital object identifier";
    public static final String PUBMED_MI = "MI:0446";
    public static final String DOI_MI = "MI:0574";
    public static final String BAIT_MI = "MI:0496";
    public static final String UNIPROT_MI = "MI:0486";

    ////////////////////////////
    // Mapping name -> MI

    public static Map<String, String> shortlabel2mi = new HashMap<String, String>();

    static {

        // TODO load that list (name, synonym...) using the OBO ontology.

        // Please put only loweracse labels !!!
        shortlabel2mi.put( "pubmed", "MI:0446" );
        shortlabel2mi.put( "pmid", "MI:0446" );
        shortlabel2mi.put( "digital object identifier", "MI:0574" );
        shortlabel2mi.put( "doi", "MI:0574" );
        shortlabel2mi.put( "cabri", "MI:0246" );
        shortlabel2mi.put( "chebi", "MI:0474" );
        shortlabel2mi.put( "cygd", "MI:0464" );
        shortlabel2mi.put( "ddbj/embl/genbank", "MI:0475" );
        shortlabel2mi.put( "ensembl", "MI:0476" );
        shortlabel2mi.put( "entrez gene/locuslink", "MI:0477" );
        shortlabel2mi.put( "flybase", "MI:0478" );
        shortlabel2mi.put( "huge", "MI:0249" );
        shortlabel2mi.put( "international protein index", "MI:0675" );
        shortlabel2mi.put( "ipi", "MI:0675" );
        shortlabel2mi.put( "kegg", "MI:0470" );
        shortlabel2mi.put( "mgd/mgi", "MI:0479" );
        shortlabel2mi.put( "newt", "MI:0247" );
        shortlabel2mi.put( "omim", "MI:0480" );
        shortlabel2mi.put( "refseq", "MI:0481" );
        shortlabel2mi.put( "rfam", "MI:0482" );
        shortlabel2mi.put( "rgd", "MI:0483" );
        shortlabel2mi.put( "sgd", "MI:0484" );
        shortlabel2mi.put( "uniparc", "MI:0485" );
        shortlabel2mi.put( "uniprot knowledge base", "MI:0486" );
        shortlabel2mi.put( "uniprotkb", "MI:0486" );
        shortlabel2mi.put( "uniprot", "MI:0486" );
        shortlabel2mi.put( "swissprot", "MI:0486" );
        shortlabel2mi.put( "trembl", "MI:0486" );
        shortlabel2mi.put( "wormbase", "MI:0487" );
    }

    ///////////////////////////
    // PSI MI Root terms

    public static final String InteractionDetectionTypeRoot = "MI:0001";
    public static final String ParticipantIdentificationTypeRoot = "MI:0002";
    public static final String FeatureDetectionTypeRoot = "MI:0003";
    public static final String FeatureTypeRoot = "MI:0116";
    public static final String InteractionTypeRoot = "MI:0190";
    public static final String AliasTypeRoot = "MI:0300";
    public static final String InteractorTypeRoot = "MI:0313";
    public static final String ExperimentalPreparationRoot = "MI:0346";
    public static final String XrefTypeRoot = "MI:0353";
    public static final String DatabaseCitationRoot = "MI:0444";
    public static final String ExperimentalRoleRoot = "MI:0495";
    public static final String BiologicalRoleRoot = "MI:0500";
    public static final String AttributeNameRoot = "MI:0590";

    /**
     * Pattern of an MI identifier as a regular expression
     */
    private static final String MI_IDENTIFIER_REGEX = "MI:[0-9]{4}";

    /**
     * Precompile the Pattern Matcher for obvious efficiency reason.
     */
    public static final Pattern MI_IDENTIFIER_PATTERN = Pattern.compile( MI_IDENTIFIER_REGEX );

    private final OntologyAccess ontology;

    /////////////////////////////
    // Constructor

    public Mi25Ontology( OntologyAccess ontology ) {
        if ( ontology == null ) {
            throw new IllegalArgumentException( "You must give a non null ontology" );
        }
        this.ontology = ontology;
    }

    /////////////////////////////
    // Convenience methods

    /**
     * checks that an MI identifier format is valid against a regular expression.
     *
     * @param id
     * @return true is the identifier is valid, false otherwise.
     */
    public boolean isValidIdentifier( String id ) {

        if ( id == null ) {
            return false;
        }

        // validate the Id against that regular expression
        Matcher matcher = MI_IDENTIFIER_PATTERN.matcher( id );
        return matcher.matches();
    }

    public OntologyTermI getInteractionDetectionTypeRoot() {
        return ontology.getTermForAccession( InteractionDetectionTypeRoot );
    }

    public OntologyTermI getParticipantIdentificationTypeRoot() {
        return ontology.getTermForAccession( ParticipantIdentificationTypeRoot );
    }

    public OntologyTermI getFeatureDetectionTypeRoot() {
        return ontology.getTermForAccession( FeatureDetectionTypeRoot );
    }

    public OntologyTermI getFeatureTypeRoot() {
        return ontology.getTermForAccession( FeatureTypeRoot );
    }

    public OntologyTermI getInteractionTypeRoot() {
        return ontology.getTermForAccession( InteractionTypeRoot );
    }

    public OntologyTermI getAliasTypeRoot() {
        return ontology.getTermForAccession( AliasTypeRoot );
    }

    public OntologyTermI getInteractorTypeRoot() {
        return ontology.getTermForAccession( InteractorTypeRoot );
    }

    public OntologyTermI getExperimentalPreparationRoot() {
        return ontology.getTermForAccession( ExperimentalPreparationRoot );
    }

    public OntologyTermI getXrefTypeRoot() {
        return ontology.getTermForAccession( XrefTypeRoot );
    }

    public OntologyTermI getDatabaseCitationRoot() {
        return ontology.getTermForAccession( DatabaseCitationRoot );
    }

    public OntologyTermI getExperimentalRoleRoot() {
        return ontology.getTermForAccession( ExperimentalRoleRoot );
    }

    public OntologyTermI getBiologicalRoleRoot() {
        return ontology.getTermForAccession( BiologicalRoleRoot );
    }

    public OntologyTermI getAttributeNameRoot() {
        return ontology.getTermForAccession( AttributeNameRoot );
    }


    /**
     * Give an MI reference by Controlled Vocabulary name.
     *
     * @param name the cv name
     * @return an MI reference or null if none found.
     */
    public String getMiByCvName( final String name ) {
        if ( name == null ) {
            throw new IllegalArgumentException( "Please give a non null name." );
        }
        return shortlabel2mi.get( name.toLowerCase() );
    }

    ///////////////////////////////////
    // Deletated methods from Ontology

    public OntologyTermI search( String id ) {
        return ontology.getTermForAccession( id );
    }

    public boolean isChildOf( OntologyTermI parent, OntologyTermI child ) {

        if ( parent == null ) {
            throw new IllegalArgumentException( "You must give a non null parent" );
        }
        if ( child == null ) {
            throw new IllegalArgumentException( "You must give a non null child" );
        }

        log.trace( "parent: " + parent.getTermAccession() + " (" + parent.getPreferredName() + ")" );
        log.trace( "child: " + child.getTermAccession() + " (" + child.getPreferredName() + ")" );

        // traverse all parents terms of the given child until we either meet the given parent or a root term.
        final Set<OntologyTermI> parents = ontology.getDirectParents( child );
        if( log.isTraceEnabled() ) {
            log.trace( "Parents of " + child.getTermAccession() + " (" + child.getPreferredName() + ")" );
            for ( OntologyTermI p : parents ) {
                log.trace( "\t - " + p.getTermAccession() + " (" + p.getPreferredName() + ")" );
            }
        }

        // we go breadth first to limit the queries to the ontology
        for ( OntologyTermI aParent : parents ) {
            if( log.isTraceEnabled() ) log.trace( "comparing " + aParent.getPreferredName() + " to " + parent.getPreferredName() );
            if ( aParent.equals( parent ) ) {
                log.trace( "SAME" );
                return true;
            } else {
                log.trace( "NOT SAME" );
            }
        }

        System.out.println( "Going to upper level" );
        for ( OntologyTermI aParent : parents ) {
            log.trace( "CHecking parents of " + aParent.getPreferredName() );
            if ( isChildOf( parent, aParent ) ) {
                return true;
            }
        }

        return false;
    }
}