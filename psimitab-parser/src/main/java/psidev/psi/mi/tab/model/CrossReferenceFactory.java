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
public class CrossReferenceFactory implements isCache {

    public static final Log log = LogFactory.getLog( CrossReferenceFactory.class );

    private static CrossReferenceFactory ourInstance = new CrossReferenceFactory();

    private boolean cacheEnabled = false;

    //////////////////////
    // Singleton

    public static CrossReferenceFactory getInstance() {
        return ourInstance;
    }

    private CrossReferenceFactory() {
    }

    final Map<String, CrossReference> cache = new HashMap<String, CrossReference>();

    /////////////////////
    // Builder methods

    public CrossReference build() {
        return new CrossReferenceImpl();
    }

    public CrossReference build( String database, String identifier ) {

        if ( database == null ) {
            throw new IllegalArgumentException( "You must give a non null database" );
        }
        database = database.trim();

        if ( identifier == null ) {
            throw new IllegalArgumentException( "You must give a non null identifier" );
        }
        identifier = identifier.trim();

        if ( identifier.length() == 0 ) {
            //throw new IllegalArgumentException( "You must give a non empty identifier for database: "+database );
            log.error("You must give a non empty identifier for database: "+database);
            identifier = "-";
        }

        CrossReference cr = null;

        if ( cacheEnabled ) {
            final String key = database + identifier;
            cr = cache.get( key );
            if ( cr == null ) {
                // A problem will arise if someone updates a shared CrossReferenceImpl after the BinaryInteractionImpl
                // has been built as all shares object get updated at once. Solution: we make cr a read-only object.
                cr = new UnmodifiableCrossReference( new CrossReferenceImpl( database, identifier ) );
                cache.put( key, cr );
            }
        } else {
            cr = new CrossReferenceImpl( database, identifier );
        }

        return cr;
    }

    public CrossReference build( String database, String identifier, String text ) {

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

        CrossReference cr = null;

        if ( cacheEnabled ) {
            final String key = database + identifier + text;
            cr = cache.get( key );
            if ( cr == null ) {
                // A problem will arise if someone updates a shared CrossReferenceImpl after the BinaryInteractionImpl
                // has been built as all shares object get updated at once. Solution: we make cr a read-only object.
                cr = new UnmodifiableCrossReference( new CrossReferenceImpl( database, identifier, text ) );
                cache.put( key, cr );
            }
        } else {
            cr = new CrossReferenceImpl( database, identifier, text );
        }

        return cr;
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
            log.debug( "Setting CrossReference pooling: " + (enabled ? "on" : "off" ) );
        }
        this.cacheEnabled = enabled;
    }
}