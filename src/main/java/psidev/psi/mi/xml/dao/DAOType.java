/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.dao;

/**
 * Types of DAO available from the framework.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24-Jun-2006</pre>
 */
public enum DAOType {

    /**
     * DAO that keeps objects in memory.
     */
    IN_MEMORY,

    /**
     * DAO that does nothing.
     */
    LAZY,

    /**
     * DAO making use of Sleepy Cat as a backend.
     */
    SLEEPY_CAT;
}