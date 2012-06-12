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
 * Creates a CrossReference and (if option enabled) pools them so save memory.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0-rc1
 */
public class InteractionTypeFactory implements isCache {

    public static final Log log = LogFactory.getLog( InteractionTypeFactory.class );

    private static InteractionTypeFactory ourInstance = new InteractionTypeFactory();

    private boolean cacheEnabled = false;

    //////////////////////
    // Singleton

    public static InteractionTypeFactory getInstance() {
        return ourInstance;
    }

    private InteractionTypeFactory() {
    }

    final Map<String, InteractionType> cache = new HashMap<String, InteractionType>();

    /////////////////////
    // Builder methods

    public InteractionType build() {
        return new InteractionTypeImpl();
    }

    public InteractionType build( String database, String identifier ) {

        if ( database == null ) {
            throw new IllegalArgumentException( "You must give a non null database" );
        }
        database = database.trim();

        if ( identifier == null ) {
            throw new IllegalArgumentException( "You must give a non null identifier" );
        }
        identifier = identifier.trim();

        InteractionType it = null;

        if ( cacheEnabled ) {
            final String key = database + identifier;
            it = cache.get( key );
            if ( it == null ) {
                // A problem will arise if someone updates a shared InteractionTypeImpl after the BinaryInteractionImpl
                // has been built as all shares object get updated at once. Solution: we make it a read-only object.
                it = new UnmodifiableInteractionType( new InteractionTypeImpl( database, identifier ) );
                cache.put( key, it );
            }
        } else {
            it = new InteractionTypeImpl( database, identifier );
        }

        return it;
    }

    public InteractionType build( String database, String identifier, String text ) {

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

        InteractionType it = null;

        if ( cacheEnabled ) {
            final String key = database + identifier + text;
            it = cache.get( key );
            if ( it == null ) {
                // A problem will arise if someone updates a shared InteractionTypeImpl after the BinaryInteractionImpl
                // has been built as all shares object get updated at once. Solution: we make it a read-only object.
                it = new UnmodifiableInteractionType( new InteractionTypeImpl( database, identifier, text ) );
                cache.put( key, it );
            }
        } else {
            it = new InteractionTypeImpl( database, identifier, text );
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
            log.debug( "Setting InteractionTypes pooling: " + (enabled ? "on" : "off" ) );
        }
        this.cacheEnabled = enabled;
    }
}