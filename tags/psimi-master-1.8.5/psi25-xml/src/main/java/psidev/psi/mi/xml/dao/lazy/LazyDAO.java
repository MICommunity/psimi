/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.dao.lazy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.dao.inMemory.InMemoryDAO;
import psidev.psi.mi.xml.model.HasId;

import java.util.Collection;
import java.util.Collections;

/**
 * In memory access to object implementing IndexedById.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19-Jun-2006</pre>
 */
public class LazyDAO<T extends HasId> implements PsiDAO<T> {

    public static final Log log = LogFactory.getLog( InMemoryDAO.class );

    /////////////////////////////
    // Constructor

    /**
     * The user should not be allowed to instanciate the class directly.
     * <p/>
     * DAOFactory should be used instead.
     */
    LazyDAO() {
    }

    /////////////////////////////////
    // Implements PsiDAO

    public Collection<T> getAll() {
        return Collections.EMPTY_LIST;
    }

    public T retreive( int id ) {
        return null;
    }

    public void store( T object ) {

        // store it
        log.info( "A lazy DAO does nothing, as says its name ;)" );
    }

    public T remove( int id ) {
        return null;
    }

    public void reset() {
    }
}