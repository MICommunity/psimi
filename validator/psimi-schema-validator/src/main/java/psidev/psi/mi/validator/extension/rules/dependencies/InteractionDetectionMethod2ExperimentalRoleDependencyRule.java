package psidev.psi.mi.validator.extension.rules.dependencies;

import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2ExperimentRoleDependencyRule.java 2 2009-06-01 15:46:10Z brunoaranda $
 * @since 2.0.0
 */
public class InteractionDetectionMethod2ExperimentalRoleDependencyRule extends Mi25InteractionRule {

    public InteractionDetectionMethod2ExperimentalRoleDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager );

        // describe the rule.
        setName( "Dependency between interaction detection method and experimental role" );
//        addTip( "" );
    }

    // TODO Currently the cv dependency are hard coded in a local model (bottom of this class).
    // TODO It seems desirable to encode this into an Excel file that would be downloaded from the PSI web site.

    /**
     * For each experiment associated with this interaction, collect all respective participants' experimental role and
     * check if the dependencies are correct.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // build a context in case of error
        Mi25Context context = new Mi25Context();
        context.setInteractionId( interaction.getId() );


        for ( ExperimentDescription experiment : interaction.getExperiments() ) {

            context.setExperimentId( experiment.getId() );
            final InteractionDetectionMethod method = experiment.getInteractionDetectionMethod();

            final Collection<ExperimentalRole> participants = new ArrayList<ExperimentalRole>();
            for ( Participant participant : interaction.getParticipants() ) {
                context.setParticipantId( participant.getId() );
                for ( ExperimentalRole role : participant.getExperimentalRoles() ) {
                    if ( role.hasExperimentRefs() ) {

                        for ( ExperimentRef experimentRef : role.getExperimentRefs() ) {
                            if ( experimentRef.getRef() == experiment.getId() ) {
                                participants.add( role );
                            }
                        }
                    } else if ( role.hasExperiments() ) {
                        for ( ExperimentDescription e : role.getExperiments() ) {
                            if ( e.getId() == experiment.getId() ) {
                                participants.add( role );
                            }
                        }
                    } else {
                        participants.add( role );
                    }
                } // experimental roles
            } // participants

            // check dependency
            messages.addAll( check( method, participants, context ) );
        } // experiments

        return messages;
    }

    /**
     * Check if the given method and the list of correcponding roles are in agreement with the defined dependency mapping.
     *
     * @param method
     * @param roles
     * @param context
     * @return
     */
    private Collection<ValidatorMessage> check( InteractionDetectionMethod method, Collection<ExperimentalRole> roles, Mi25Context context ) {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        final InteractionDetectionMethod2ExperimentalRoleDependencyRule.DependencyMapping mapping = buildSampleMapping();

        for ( Dependency dependency : mapping.getDependencies() ) {
            // check if the 'from' of the dependency is in the list of defined mapping
            final String id = dependency.getFrom().getId();
            final String methodMi = getMiIdentifier( method );
            if ( methodMi != null ) {

                for ( Dependency dep : mapping.getDependencies() ) {
                    // TODO NPE check
                    if ( dep.getFrom().getId().equals( methodMi ) ) {
                        // do check on the associated list
                        for ( ExperimentalRole role : roles ) {
                            // for each role,
                            //     if in the inclusion list => ok
                            //     if in the exclusion list => error
                            //     if not in either inclusion or exclusion => info message

                            final String roleMi = getMiIdentifier( role );
                            if ( roleMi != null ) {

                                boolean included = false;
                                boolean excluded = false;


                                for ( Iterator<Term> it = dep.getInclusionList().iterator(); it.hasNext() && !included; ) {
                                    Term term = it.next();
                                    if ( term.getId().equals( roleMi ) ) {
                                        included = true;
                                    }
                                }

                                if ( !included ) {

                                    for ( Iterator<Term> it = dep.getExclusionList().iterator(); it.hasNext() && !excluded; ) {
                                        Term term = it.next();
                                        if ( term.getId().equals( roleMi ) ) {
                                            excluded = true;
                                        }
                                    }
                                }

                                if ( excluded ) {

                                    // TODO specify Context
                                    
                                    final String msg = "Invalid use of experimental role '" + role.getNames().getShortLabel() +
                                                       "' (" + roleMi + "). Given the interaction detection method '"+
                                                       dep.getFrom().getName()+"' ("+dep.getFrom().getId()+") one would" +
                                                       " expect the following experimental role(s): " +
                                                       printTerms( dep.getInclusionList() );
                                    messages.add( new ValidatorMessage( msg, MessageLevel.ERROR, context, this ) );

                                } else if ( !included ) {

                                    final String msg = "Found a role '" + role.getNames().getShortLabel() +
                                                       "' (" + roleMi + ") associated with the interaction detection " +
                                                       "method '"+ dep.getFrom().getName() + "' (" + dep.getFrom().getId() +
                                                       ")  that doesn't belong to either the inclusion list ("+
                                                       printTerms( dep.getInclusionList() )+") or the " +
                                                       "exclusion list ("+printTerms( dep.getExclusionList() )+"). ";
                                    messages.add( new ValidatorMessage( msg, MessageLevel.INFO, context, this ) );
                                }

                            } else {
                                // Error, a CV term should have an MI
                                final String msg = "Found a role without an MI: " + role.getNames().getShortLabel() ;
                                messages.add( new ValidatorMessage( msg, MessageLevel.ERROR ) );
                            }
                        }
                    }
                }

            } else {
                // Error, a CV term should have an MI
                final String msg = "Found an interaction detection method without an MI: " + method.getNames().getShortLabel() ;
                messages.add( new ValidatorMessage( msg, MessageLevel.ERROR ) );
            }
        }

        return messages;
    }

    private String printTerms( Collection<Term> terms ) {
        StringBuilder sb = new StringBuilder();
        for ( Iterator<Term> termIterator = terms.iterator(); termIterator.hasNext(); ) {
            Term term = termIterator.next();
            sb.append(term.getName()).append(" (").append(term.getId()).append(")");
            if( termIterator.hasNext() ) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private DependencyMapping buildSampleMapping() {

        Dependency dep1 = new Dependency( new Term( "MI:0018", "2hybrid" ),

                                          Arrays.asList( new Term( "MI:0496", "bait" ),
                                                         new Term( "MI:0498", "prey" ),
                                                         new Term( "MI:0499", "unspecified" ),
                                                         new Term( "MI:0684", "ancillary" ) ),

                                          Arrays.asList( new Term( "MI:0497", "neutral component" ),
                                                         new Term( "MI:0583", "fluorescence donor" ),
                                                         new Term( "MI:0584", "fluorescence acceptor" ) ) );

        // TODO what about the terms that are not specified ? ie. self, suppressor gene, suppressed gene, putative self 
        // for now, we generate a INFO message

        Dependency dep2 = new Dependency( new Term( "MI:0027", "cosedimentation" ),

                                          Arrays.asList( new Term( "MI:0497", "neutral component" ) ),

                                          Arrays.asList( new Term( "MI:0496", "bait" ),
                                                         new Term( "MI:0498", "prey" ),
                                                         new Term( "MI:0503", "self" ),
                                                         new Term( "MI:0684", "ancillary" ) ) );

        DependencyMapping mapping = new DependencyMapping( Arrays.asList( dep1 ) );

        return mapping;
    }

    public String getId() {
        return null;
    }

    //////////////////////
    // Inner classes

    public class Term {
        private String id;
        private String name;

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

        public void setId( String id ) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }
    }

    public class Dependency {

        private Term from;
        private Collection<Term> inclusionList;
        private Collection<Term> exclusionList;

        public Dependency( Term from, Collection<Term> inclusionList, Collection<Term> exclusionList ) {

            if ( from == null ) {
                throw new IllegalArgumentException( "You must give a non null starting point to your dependency" );
            }
            this.from = from;

            if ( inclusionList == null || inclusionList.isEmpty() ) {
                throw new IllegalArgumentException( "You must give a non empty inclusion list" );
            }

            this.inclusionList = inclusionList;
            this.exclusionList = exclusionList;
        }

        public Term getFrom() {
            return from;
        }

        public Collection<Term> getInclusionList() {
            return inclusionList;
        }

        public Collection<Term> getExclusionList() {
            return exclusionList;
        }
    }

    public class DependencyMapping {

        private Collection<Dependency> dependencies;

        public DependencyMapping( Collection<Dependency> dependencies ) {
            if ( dependencies == null || dependencies.isEmpty() ) {
                throw new IllegalArgumentException( "You must give a non empty list of dependencies" );
            }
            this.dependencies = dependencies;
        }

        public Collection<Dependency> getDependencies() {
            return dependencies;
        }
    }
}