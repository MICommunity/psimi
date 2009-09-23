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

import java.util.HashMap;
import java.util.Map;

/**
 * Creates a InteractionDetectionMethod and (if option enabled) pools them so save memory.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-rc1
 */
public class InteractionDetectionMethodFactory implements isCache {

    public static final Log log = LogFactory.getLog( InteractionDetectionMethodFactory.class );

    private static InteractionDetectionMethodFactory ourInstance = new InteractionDetectionMethodFactory();

    private boolean cacheEnabled = false;

    //////////////////////
    // Singleton

    public static InteractionDetectionMethodFactory getInstance() {
        return ourInstance;
    }

    private InteractionDetectionMethodFactory() {
    }

    final Map<String, InteractionDetectionMethod> cache = new HashMap<String, InteractionDetectionMethod>();

    /////////////////////
    // Builder methods

    public InteractionDetectionMethod build() {
        return new InteractionDetectionMethodImpl();
    }

    public InteractionDetectionMethod build( String database, String identifier ) {

        if ( database == null ) {
            throw new IllegalArgumentException( "You must give a non null database" );
        }
        database = database.trim();

        if ( identifier == null ) {
            throw new IllegalArgumentException( "You must give a non null identifier" );
        }
        identifier = identifier.trim();

        InteractionDetectionMethod idm = null;

        if ( cacheEnabled ) {
            final String key = database + identifier;
            idm = cache.get( key );
            if ( idm == null ) {
                // A problem will arise if someone updates a shared InteractionDetectionMethod after the BinaryInteractionImpl
                // has been built as all shares object get updated at once. Solution: we make idm a read-only object.
                idm = new UnmodifiableInteractionDetectionMethod(
                        new InteractionDetectionMethodImpl( database, identifier ) );
                cache.put( key, idm );
            }
        } else {
            idm = new InteractionDetectionMethodImpl( database, identifier );
        }

        return idm;
    }

    public InteractionDetectionMethod build( String database, String identifier, String text ) {

        if ( database == null ) {
            throw new IllegalArgumentException( "You must give a non null database" );
        }
        database = database.trim();

        if ( identifier == null ) {
            throw new IllegalArgumentException( "You must give a non null identifier" );
        }
        identifier = identifier.trim();

        if ( text == null || text.trim().length() == 0 ) {
            return build( database, identifier );
        }
        text = text.trim();

        InteractionDetectionMethod it = null;

        if ( cacheEnabled ) {
            final String key = database + identifier + text;
            it = cache.get( key );
            if ( it == null ) {
                // A problem will arise if someone updates a shared InteractionDetectionMethod after the BinaryInteractionImpl
                // has been built as all shares object get updated at once. Solution: we make it a read-only object.
                it = new UnmodifiableInteractionDetectionMethod(
                        new InteractionDetectionMethodImpl( database, identifier, text ) );
                cache.put( key, it );
            }
        } else {
            it = new InteractionDetectionMethodImpl( database, identifier, text );
        }

        return it;
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