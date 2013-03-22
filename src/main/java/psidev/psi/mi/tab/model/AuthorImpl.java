/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

/**
 * Representation of an Author name.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03-Oct-2006</pre>
 */
public class AuthorImpl implements Author {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = -4763395239997399170L;

    /////////////////////////
    // Instance variables

    /**
     * Name of the first author.
     */
    private String name;

    private MitabSourceLocator locator;

    /////////////////////////
    // Constructor
    public AuthorImpl( String name ) {
        if( name == null ) {
            throw new IllegalArgumentException( "The author name cannot be null." );
        }
        this.name = name;
    }

    ////////////////////////////
    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public MitabSourceLocator getSourceLocator() {
        return locator;
    }

    public void setLocator(MitabSourceLocator locator) {
        this.locator = locator;
    }

    ////////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "AuthorImpl" );
        sb.append( "{name='" ).append( name ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final AuthorImpl author = ( AuthorImpl ) o;

        if ( !name.equals( author.name ) ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = name.hashCode();
        return result;
    }
}