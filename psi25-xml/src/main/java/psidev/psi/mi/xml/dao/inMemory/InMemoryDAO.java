/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.dao.inMemory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.HasId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * In memory access to object implementing IndexedById.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19-Jun-2006</pre>
 */
public class InMemoryDAO<T extends HasId> implements PsiDAO<T> {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( InMemoryDAO.class );

    /////////////////////////////
    // Instance variable

    /**
     * Where we store the object, indexed by id.
     */
    private Map<Integer, T> map = new HashMap<Integer, T>();

    /////////////////////////////
    // Constructor

    /**
     * The user should not be allowed to instanciate the class directly.
     * <p/>
     * DAOFactory should be used instead.
     */
    InMemoryDAO() {
    }

    /////////////////////////////////
    // Implements PsiDAO

    public Collection<T> getAll() {
        return map.values();
    }

    public T retreive( int id ) {
        return map.get( id );
    }

    public void store( T object ) {

        if ( object == null ) {
            throw new IllegalArgumentException( "The Object to store must not be null." );
        }

        // type of the object we are handling
        String name = object.getClass().getSimpleName();

        int id = object.getId();

        if ( map.containsKey( id ) ) {
            log.warn( "Attempt to overwrite an existing " + name + " with id: " + id );
            T original = map.get( id );

            if ( !object.equals( original ) ) {
                log.warn( "The new " + name + " was different" );
                log.warn( "Original : " + original );
                log.warn( "New : " + object );
                // Crash is too strict, so just ignore duplicate entry.
                return;
            } else {
                log.warn( "The " + name + " to store was the same." );
            }
        }

        // store it
        log.debug( "Storing " + name + " under key( " + id + " )" );
        map.put( id, object );
    }

    public T remove( int id ) {
        return map.remove( id );
    }

    public void reset() {
        if ( log.isDebugEnabled() ) log.debug( "Clearing inMemory DAO map: " + getClass().getSimpleName() );
        map.clear();
    }
}