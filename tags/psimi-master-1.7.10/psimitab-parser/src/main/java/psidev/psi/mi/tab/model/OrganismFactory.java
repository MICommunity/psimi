/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
package psidev.psi.mi.tab.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Creates a InteractionDetectionMethod and (if option enabled) pools them so save memory.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-rc1
 */
public class OrganismFactory implements isCache {

    public static final Log log = LogFactory.getLog( OrganismFactory.class );

    private static OrganismFactory ourInstance = new OrganismFactory();

    private boolean cacheEnabled = false;

    //////////////////////
    // Singleton

    public static OrganismFactory getInstance() {
        return ourInstance;
    }

    private OrganismFactory() {
    }

    final Map<String, Organism> cache = new HashMap<String, Organism>();

    /////////////////////
    // Builder methods

    public Organism build( int taxid, String name ) {

        if ( name == null ) {
            throw new IllegalArgumentException( "You must give a non null name" );
        }
        name = name.trim();

        Organism o = null;

        if ( cacheEnabled ) {
            final String key = taxid + name;
            o = cache.get( key );
            if ( o == null ) {
                // A problem will arise if someone updates a shared Organism after the BinaryInteractionImpl
                // has been built as all shares object get updated at once. Solution: we make o a read-only object.
                o = new UnmodifiableOrganism( new OrganismImpl( taxid, name ) );
                cache.put( key, o );
            }
        } else {
            o = new OrganismImpl( taxid, name );
        }

        return o;
    }

    public Organism build( int taxid ) {

        Organism o = null;

        if ( cacheEnabled ) {
            final String key = String.valueOf( taxid );
            o = cache.get( key );
            if ( o == null ) {
                // A problem will arise if someone updates a shared Organism after the BinaryInteractionImpl
                // has been built as all shares object get updated at once. Solution: we make o a read-only object.
                o = new UnmodifiableOrganism( new OrganismImpl( taxid ) );
                cache.put( key, o );
            }
        } else {
            o = new OrganismImpl( taxid );
        }

        return o;
    }

    public Organism build( Collection<CrossReference> refs ) {

        if( refs == null ) {
            throw new IllegalArgumentException( "You must give a non null CrossReference collection" );
        }

        Organism o = null;

        if ( cacheEnabled ) {
            // sort the identifiers to build the cache key
            List<CrossReference> orderedRefs = new ArrayList<CrossReference>( refs );
            Collections.sort( orderedRefs, new Comparator<CrossReference>() {
                public int compare( CrossReference o1, CrossReference o2 ) {
                    return o1.getIdentifier().compareTo( o2.getIdentifier() );
                }
            } );

            StringBuilder sb = new StringBuilder();
            for ( CrossReference cr : orderedRefs ) {
                sb.append( cr.getIdentifier() ).append('.');
            }

            final String key = sb.toString();
            o = cache.get( key );
            if ( o == null ) {
                // A problem will arise if someone updates a shared Organism after the BinaryInteractionImpl
                // has been built as all shares object get updated at once. Solution: we make o a read-only object.
                o = new UnmodifiableOrganism( new OrganismImpl( refs ) );
                cache.put( key, o );
            }
        } else {
            o = new OrganismImpl( refs );
        }

        return o;
    }

    ///////////
    // Cache

    public void resetCache() {
        cache.clear();
    }

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled( boolean enabled ) {
        if ( log.isDebugEnabled() ) {
            log.debug( "Setting InteractionDetectionMethod pooling: " + (enabled ? "on" : "off" ) );
        }
        this.cacheEnabled = enabled;
    }
}