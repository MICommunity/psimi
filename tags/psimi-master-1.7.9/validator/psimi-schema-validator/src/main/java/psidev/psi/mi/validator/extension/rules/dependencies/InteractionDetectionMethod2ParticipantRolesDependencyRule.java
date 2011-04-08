package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.*;
import java.util.*;

/**
 * Rule that allows to check whether the interaction detection method specified matches the experimental
 * roles of the participants.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2ParticipantRolesDependencyRule.java 2 2009-06-01 15:46:10Z brunoaranda $
 * @since 2.0.0
 */
public class InteractionDetectionMethod2ParticipantRolesDependencyRule extends Mi25InteractionRule {

    // TODO add term cardinality in the configuration file to make the rules more meaningful.
    //      Currently I can have a yeast two hybrid with 6 preys and the rule is happy :(

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2ParticipantRolesDependencyRule.class );

    private static InteractionDetectionMethod2ParticipantRolesDependencyRule.DependencyMapping mapping;

    public InteractionDetectionMethod2ParticipantRolesDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager );
        // HACK the mapping is loaded every single time the rule is created :(

        // TODO : the resource should be a final private static or should be put as argument of the constructor
        try {

            OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );

            mapping = buildMappingFromFile( mi );
        } catch ( Exception e ) {
            throw new RuntimeException( "An error occured while loading the rule set.", e );
        }

        // describe the rule.
        setName( "Dependency between interaction detection method and participant's experimental role" );
    }

    /**
     * For each experiment associated with this interaction, collect all respective participants' experimental role and
     * check if the dependencies are correct.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // build a context in case of error
        Mi25Context context = new Mi25Context();
        context.setInteractionId( interaction.getId() );

        for ( ExperimentDescription experiment : interaction.getExperiments() ) {

            context.setExperimentId( experiment.getId() );

            final InteractionDetectionMethod method = experiment.getInteractionDetectionMethod();

            for ( Participant participant : interaction.getParticipants() ) {

                context.setParticipantId( participant.getId() );

                final BiologicalRole biologicalRole = participant.getBiologicalRole();

                for ( ExperimentalRole experimentalRole : participant.getExperimentalRoles() ) {
                    if ( experimentalRole.hasExperimentRefs() ) {

                        for ( ExperimentRef experimentRef : experimentalRole.getExperimentRefs() ) {
                            if ( experimentRef.getRef() == experiment.getId() ) {
                                // ok - even though the parser should have resolved that !!
                            } else {
                                throw new IllegalStateException( "Validator error - interaction "+ interaction.getId() +
                                        " has a participant "+ participant.getId() +" with " +
                                        "an ExperimentRef, this should have been resolved by " +
                                        "the PSI-MI XML parser" );
                            }
                        }
                    } else if ( experimentalRole.hasExperiments() ) {
                        for ( ExperimentDescription e : experimentalRole.getExperiments() ) {
                            if ( e.getId() == experiment.getId() ) {
                                // that's ok, keep our local experiment
                            } else {
                                // override local experiment and fire check
                                context.setExperimentId( e.getId() );
                                messages.addAll( check( method, biologicalRole, experimentalRole, context ) );
                            }
                        }
                    } else {
                        context.setExperimentId( experiment.getId() );
                        messages.addAll( check( method, biologicalRole, experimentalRole, context ) );
                    }

                } // experimental roles
            } // participants
        } // experiments

        return messages;
    }

    /**
     * Check if the given method and the list of correcponding roles are in agreement with the defined dependency mapping.
     *
     * @param method
     * @param biologicalRole
     * @param experimentalRole
     * @param context
     * @return a list of messages should any error be found.
     */
    private Collection<ValidatorMessage> check( InteractionDetectionMethod method,
                                                BiologicalRole biologicalRole,
                                                ExperimentalRole experimentalRole,
                                                Mi25Context context ) {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        final Map<Term, Set<RolePair>> dependencies = mapping.getDependencies();

        final InteractionDetectionMethod2ParticipantRolesDependencyRule.Term methodTerm = buildTerm( method );

        if( methodTerm != null) {

            if( dependencies.containsKey( methodTerm )) {
                final Set<RolePair> pairs = dependencies.get( methodTerm );

                final InteractionDetectionMethod2ParticipantRolesDependencyRule.Term brTerm = buildTerm( biologicalRole );
                if( brTerm == null ) {

                    // TODO here we could report eror given that no MI accession number was given for that method.

                } else {

                    final InteractionDetectionMethod2ParticipantRolesDependencyRule.Term erTerm = buildTerm( experimentalRole );
                    if( erTerm == null ) {

                        // TODO here we could report eror given that no MI accession number was given for that method.

                    } else {

                        RolePair myPair = new RolePair( brTerm, erTerm );
                        if( pairs.contains(myPair )) {

                            // check if there is a message to be generated for that pair
                            final MessageLevel level = getMessageLevel( pairs, myPair );
                            if( level != null ) {
                                // the mapping indicates that we should build a message at the spefified level
                                final String msg = "Unusual combination of interaction detection method ["+printTerm(methodTerm)+"] " +
                                        "and experimental role ["+printTerm(erTerm)+"] / " +
                                        "biological role ["+printTerm(brTerm)+"]. Roles combination allowed are: " +
                                        printTermPairs( pairs );

                                messages.add( new ValidatorMessage( msg, MessageLevel.WARN, context.copy(), this ) );
                            }

                        } else {
                            // the pair is not allowed, generate an error.
                            final String msg = "Invalid combination of interaction detection method ["+printTerm(methodTerm)+"] " +
                                    "and experimental role ["+printTerm(erTerm)+"] / " +
                                    "biological role ["+printTerm(brTerm)+"]. Roles combination allowed are: " +
                                    printTermPairs( pairs );

                            messages.add( new ValidatorMessage( msg, MessageLevel.ERROR, context.copy(), this ) );
                        }

                    } // experimetnal role available
                } // biological role available

            } // there are rule for the method

        } else {
            // TODO here we could report eror given that no MI accession number was given for that method.
        }

        // warning if CVs do not have MIs

        return messages;
    }

    private MessageLevel getMessageLevel( Set<RolePair> pairs, RolePair myPair ) {

        for ( RolePair pair : pairs ) {
            if( pair.equals( myPair ) ) {
                // found it
                return pair.getLevel();
            }
        }

        throw new IllegalStateException( "Could not find the pair: " + myPair );
    }

    private String printTermPairs( Set<RolePair> pairs ) {
        StringBuilder sb = new StringBuilder( pairs.size() * 64 );
        for ( Iterator<RolePair> iterator = pairs.iterator(); iterator.hasNext(); ) {
            RolePair pair = iterator.next();
            sb.append("[").append( printTerm(pair.getExperimentalRole()) ).append(" / ").append( printTerm(pair.getBiologicalRole()) ).append("]");
            if( iterator.hasNext() ) {
                sb.append("  ");
            }
        }
        return sb.toString();
    }

    private Term buildTerm( CvType cv ) {
        final String id = getMiIdentifier( cv );
        if( id == null ) {
            return null;
        }
        return new Term( id, cv.getNames().getShortLabel() );
    }

    private String printTerm( Term term ) {
        StringBuilder sb = new StringBuilder( 32 );
        sb.append(term.getName()).append(" (").append(term.getId()).append(")");
        return sb.toString();
    }

    private String printTerms( Collection<Term> terms ) {
        StringBuilder sb = new StringBuilder( terms.size() * 32 );
        for ( Iterator<Term> termIterator = terms.iterator(); termIterator.hasNext(); ) {
            Term term = termIterator.next();
            sb.append( printTerm(term) );
            if( termIterator.hasNext() ) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private static DependencyMapping buildMappingFromFile( OntologyAccess mi ) throws IOException, ValidatorException {

        DependencyMapping mapping = new DependencyMapping();

        String resource = InteractionDetectionMethod2ParticipantRolesDependencyRule.class
                .getResource( "/interactionDetection2roles.tsv" ).getFile();

        BufferedReader in = new BufferedReader( new FileReader( new File( resource ) ) );
        String str;
        int lineCount = 0;
        while ( ( str = in.readLine() ) != null ) {
            // we skip empty lines and those starting with the symbol '#'
            lineCount++;
            if ( log.isDebugEnabled() ) {
                log.debug( "L" + lineCount + ":\t" + str );
            }

            if (str.startsWith( "\"" )){
                str = str.substring(1);
            }
            if (str.endsWith("\"")){
                str = str.substring(0, str.length() - 1);
            }

            if( str.startsWith( "#" ) || str.trim().length() == 0) {
                continue; // skip
            }

            // 0. DETECTION MI	
            // 1. DETECTION NAME
            // 2. INCLUDE CHILDREN
            // 3. EXP ROLE MI
            // 4. EXP ROLE NAME
            // 5. BIOL ROLE MI
            // 6. BIOL ROLE NAME
            // 7. ERROR LEVEL

            if (str.contains("\t")){
                final String[] columns = str.split( "\t" );

                // Remove the possible " characters we can find after editing a tab file using excel.
                for (int i = 0; i < columns.length; i++){
                    String col = columns[i];
                    if (col != null){
                        if (col.startsWith( "\"" )){
                            columns[i] = columns[i].substring(1);
                        }
                        if (col.endsWith("\"")){
                            columns[i] = columns[i].substring(0, columns[i].length() - 1);
                        }
                    }
                }
                Term detection = new Term( columns[0], columns[1] );
                if( columns[2].length() > 0 ) {
                    detection.setIncludeChildren( Boolean.valueOf( columns[2] ) );
                }
                Set<RolePair> pairs = null;
                if( ! mapping.getDependencies().containsKey( detection ) ) {
                    pairs = new HashSet<RolePair>();
                    mapping.getDependencies().put( detection, pairs );
                } else {
                    pairs = mapping.getDependencies().get( detection );
                }

                RolePair roles = new RolePair(
                        new Term( columns[5], columns[6] ),
                        new Term( columns[3], columns[4] ),
                        ( columns.length > 7 && columns[7] != null && columns[7].length()> 0 ? MessageLevel.forName( columns[7] ) : null )
                );

                pairs.add( roles ); // will only add if not already in. (cf. RolePair equals/hashcode)
            }
        }
        in.close();

        if ( log.isInfoEnabled() ) {
            log.info( "Completed reading " + mapping.getDependencies().size() + " dependencies from mapping file" );
        }

        // Now that we have swallowed the whole file, we post process CV terms so that if we are supposed to include
        // all children, we include them into the mapping and attach to them the same instance of term pairs.

        // Create a new Map to avoid modifiying the one we are currently iterating over.
        final Map<Term, Set<RolePair>> childrenDependencies = new HashMap<Term, Set<RolePair>>( );

        for ( Term term : mapping.getDependencies().keySet() ) {
            if(term.includeChildren) {
                // fetch all children and append them to the local map
                final Collection<OntologyTermI> children = mi.getValidTerms( term.getId(), true, false );
                if( ! children.isEmpty() ) {
                    final Set<RolePair> pairs = mapping.getDependencies().get( term );
                    for ( OntologyTermI child : children ) {
                        final Term termChild = new Term( child.getTermAccession(), child.getPreferredName() );
                        if( mapping.getDependencies().containsKey( termChild )) {
                            log.warn( "Could not add child term(s) of '" + term.getName() + "' as it is already defined in the rule set:" + termChild.getName() );
                        } else {
                            log.info( "Adding children of term " + term.getName() + ":" + termChild.getName() );
                            childrenDependencies.put( termChild, pairs );
                        }
                    }
                }
            }
        }
        log.info( childrenDependencies.size() +" children dependencies added transitively  " );
        mapping.getDependencies().putAll( childrenDependencies );

        return mapping;
    }

    //////////////////////
    // Inner classes

    public static final class Term {
        private String id;
        private String name;
        private boolean includeChildren;

        public Term( String id, String name ) {
            if ( id == null || id.trim().length() == 0 ) {
                throw new IllegalArgumentException( "You must give a non empty id for a CV term." );
            }
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public boolean isIncludeChildren() {
            return includeChildren;
        }

        public void setIncludeChildren( boolean includeChildren ) {
            this.includeChildren = includeChildren;
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            Term term = ( Term ) o;

            if ( !id.equals( term.id ) ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public String toString() {
            return "Term{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", includeChildren=" + includeChildren +
                    '}';
        }
    }

    public static final class RolePair {

        private Term biologicalRole;
        private Term experimentalRole;
        private MessageLevel level;

        public RolePair( Term biologicalRole, Term experimentalRole, MessageLevel level ) {
            this( biologicalRole, experimentalRole );
            this.level = level;
        }

        public RolePair( Term biologicalRole, Term experimentalRole ) {
            if ( biologicalRole == null ) {
                throw new IllegalArgumentException( "You must give a non null biologicalRole" );
            }
            this.biologicalRole = biologicalRole;

            if ( experimentalRole == null ) {
                throw new IllegalArgumentException( "You must give a non null experimentalRole" );
            }
            this.experimentalRole = experimentalRole;
        }

        public Term getBiologicalRole() {
            return biologicalRole;
        }

        public Term getExperimentalRole() {
            return experimentalRole;
        }

        public MessageLevel getLevel() {
            return level;
        }

        @Override
        public String toString() {
            return "RolePair{" +
                    "biologicalRole=" + biologicalRole +
                    ", experimentalRole=" + experimentalRole +
                    ", level=" + level +
                    '}';
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            RolePair rolePair = ( RolePair ) o;

            if ( !biologicalRole.equals( rolePair.biologicalRole ) ) return false;
            if ( !experimentalRole.equals( rolePair.experimentalRole ) ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = biologicalRole.hashCode();
            result = 31 * result + experimentalRole.hashCode();
            return result;
        }
    }

    public static class DependencyMapping {

        private Map<Term, Set<RolePair>> dependencies;

        public DependencyMapping() {
            this.dependencies = new HashMap<Term, Set<RolePair>>( );
        }

        public Map<Term, Set<RolePair>> getDependencies() {
            return dependencies;
        }

        public synchronized DependencyMapping getInstance( OntologyAccess ontology ) {
            return this;
        }
    }
}