/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.dao;

import psidev.psi.mi.xml.model.HasId;

import java.util.Collection;

/**
 * describes how to access objects.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23-Jun-2006</pre>
 */
public interface PsiDAO<T extends HasId> {

    ////////////////////////////
    // DAO methods

    /**
     * Retreives all objects.
     *
     * @return a collection containing all objects.
     */
    public Collection<T> getAll();

    /**
     * Retreive an object by id.
     *
     * @param id the id of the object
     * @return the object or null if not found.
     */
    public T retreive( int id );

    /**
     * Store an object.
     *
     * @param object the object to store.
     */
    public void store( T object );

    /**
     * Remove an object.
     *
     * @param id the object to store.
     */
    public T remove( int id );

    /**
     * Reset internal data.
     */
    public void reset();
}