/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceImpl;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.utils.PsiCollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Processor that reorganises the interactions by collapsing all that have the same couple of interactor.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12-Oct-2006</pre>
 */
public class ClusterInteractorPairProcessor<T extends BinaryInteraction> implements PostProcessorStrategy<T> {

    public static final Log log = LogFactory.getLog( ClusterInteractorPairProcessor.class );

    ///////////////////////
    // Constructor

    public ClusterInteractorPairProcessor() {
    }

    /////////////////////
    // Inner classes

    /**
     * The simple interactor comparison is based on the comparison of their identifier coupled with taxonomy id.
     */
    protected static class SimpleInteractor {

        Collection<CrossReference> identifiers;
        Integer taxid;

        public SimpleInteractor( Interactor interactor ) {
            if ( interactor == null ) {
                throw new IllegalArgumentException( "Interactor should not be null." );
            }
            this.identifiers = interactor.getIdentifiers();
            if ( interactor.hasOrganism() ) {
                for ( CrossReference cr : interactor.getOrganism().getIdentifiers() ) {
                    if ( cr.getDatabase().equals( "taxid" ) ) {
                        taxid = new Integer( cr.getIdentifier() );
                    }
                }
            }
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) {
                return true;
            }
            if ( o == null || getClass() != o.getClass() ) {
                return false;
            }

            SimpleInteractor that = ( SimpleInteractor ) o;

            if ( taxid != null ? !taxid.equals( that.taxid ) : that.taxid != null ) {
                return false;
            }
            // Equals if at least one of the identifier is the same
            if ( PsiCollectionUtils.intersection( identifiers, that.identifiers ).isEmpty() ) {
                return false;
            }


            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = identifiers.hashCode();
            result = 31 * result + ( taxid != null ? taxid.hashCode() : 0 );
            return result;
        }
    }

    /**
     * Simple object modeling a couple of SimpleInteractor and giving comparison feature.
     */
    protected static class TwoInteractor {
        private SimpleInteractor interactorA;
        private SimpleInteractor interactorB;

        public TwoInteractor( SimpleInteractor interactorA, SimpleInteractor interactorB ) {
            if ( interactorA == null ) {
                throw new IllegalArgumentException();
            }
            if ( interactorB == null ) {
                throw new IllegalArgumentException();
            }
            this.interactorA = interactorA;
            this.interactorB = interactorB;
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) {
                return true;
            }
            if ( o == null || getClass() != o.getClass() ) {
                return false;
            }

            TwoInteractor that = ( TwoInteractor ) o;

            // A = A' and B = B'     OR     A = B' and B = A'
            return ( ( interactorA.equals( that.interactorA ) && interactorB.equals( that.interactorB ) )
                     ||
                     ( interactorA.equals( that.interactorB ) && interactorB.equals( that.interactorA ) ) );
        }

        @Override
        public int hashCode() {
            // new implementation of the intersection in PsiCollectionUtils is faster
            //Collection<CrossReferenceImpl> inter = CollectionUtils.intersection(interactorA.identifiers, interactorB.identifiers);
            Collection<CrossReferenceImpl> inter = PsiCollectionUtils.intersection( interactorA.identifiers, interactorB.identifiers );
            int res = 1;
            for ( CrossReference reference : inter ) {
                res = 31 * res * reference.hashCode();
            }
            res *= ( interactorA.taxid != null ? interactorA.taxid.hashCode() : 1 );
            res *= ( interactorB.taxid != null ? interactorB.taxid.hashCode() : 1 );

            return res;
            //return 31 * interactorA.hashCode() * interactorB.hashCode();
        }
    }

    ////////////////////
    // Utility

    /**
     * Add all element of source into collection target only if it is not there yet.
     *
     * @param source
     * @param target
     */
    protected void mergeCollection( Collection source, Collection target ) {

        if ( source == null ) {
            throw new IllegalArgumentException( "Source collection must not be null." );
        }

        if ( target == null ) {
            throw new IllegalArgumentException( "Target collection must not be null." );
        }

        if ( source == target ) throw new IllegalStateException();

        for ( Object o : source ) {
            // Repeat of objects are a necessity (eg. for detection method accros multiple publications)
            target.add( o );
        }
    }

    /////////////////////////////
    // PostProcessorStrategy

    /**
     * Process the list of interactions in order to makes sure only one interaction features a couple of specific
     * interactor.
     *
     * @param interactions
     * @return
     * @post interactions.size() <= return.size()
     */
    public Collection<T> process( Collection<T> interactions ) {
        boolean verbose = true;

        Map<TwoInteractor, T> map = new HashMap<TwoInteractor, T>( interactions.size() );

        int count = 0, global = 0;
        long start = System.currentTimeMillis();

        for ( Iterator<T> iterator = interactions.iterator(); iterator.hasNext(); ) {
            T interaction = iterator.next();

            count++;
            global++;

            if ( log.isDebugEnabled() ) {
                if ( count != 0 && count % 1000 == 0 ) {
                    float elapsedTimeMin = ( System.currentTimeMillis() - start ) / ( 60 * 1000F );
                    int nbRemainInteractions = interactions.size() - global;
                    double timeByInteraction = ( double ) elapsedTimeMin / 1000D;
                    double etaMin = timeByInteraction * nbRemainInteractions;

                    log.debug( global + " interactions processed (" + elapsedTimeMin
                               + " min for these last 1000) - ETA = " + etaMin + " min" );

                    count = 0;
                    start = System.currentTimeMillis();
                }
            }

            TwoInteractor ti = new TwoInteractor( new SimpleInteractor( interaction.getInteractorA() ),
                                                  new SimpleInteractor( interaction.getInteractorB() ) );

            // enables to get the object and test its presence in the same time
            T target = map.get( ti );

            if ( target != null ) {
                if ( target == interaction ) {
                    log.warn( "The given collection cluster contained twice the same interaction. one was removed: " + interaction );
                    iterator.remove();
                } else {
                    // interaction already present with the same interactors collapse interaction details onto
                    // existing one interactors remain the same, but collapse all other fields (redundantly)
                    mergeCollections(interaction, target);
                }
            } else {
                // add interaction
                map.put( ti, interaction );
            }
        }

        return map.values();
    }

    protected void mergeCollections(T interaction, T target) {
        mergeCollection( interaction.getAuthors(), target.getAuthors() );
        mergeCollection( interaction.getPublications(), target.getPublications() );
        mergeCollection( interaction.getConfidenceValues(), target.getConfidenceValues() );
        mergeCollection( interaction.getDetectionMethods(), target.getDetectionMethods() );
        mergeCollection( interaction.getInteractionTypes(), target.getInteractionTypes() );
        mergeCollection( interaction.getSourceDatabases(), target.getSourceDatabases() );
        mergeCollection( interaction.getInteractionAcs(), target.getInteractionAcs() );
    }
}