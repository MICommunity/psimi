/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

/**
 * Simple description of an interaction type.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class InteractionTypeImpl extends CrossReferenceImpl implements InteractionType {

    ////////////////////////
    // Constructors

    public InteractionTypeImpl() {
        super();
    }

    public InteractionTypeImpl( String databaseName, String identifier ) {
        super( databaseName, identifier );
    }

    public InteractionTypeImpl( String database, String identifier, String text ) {
        super( database, identifier, text );
    }
}