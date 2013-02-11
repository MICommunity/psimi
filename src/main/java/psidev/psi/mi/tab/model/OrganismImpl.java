/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

/**
 * Represents a simple organism.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12-Jan-2007</pre>
 */
public class OrganismImpl implements Organism {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = 5647365864375422507L;

    List<CrossReference> identifiers = new ArrayList<CrossReference>();

    ///////////////////////
    // Cosntructor

    public OrganismImpl() {
    }

    public OrganismImpl( CrossReference ref ) {
        addIdentifier( ref );
    }

    public OrganismImpl( List<CrossReference> identifiers ) {
        setIdentifiers( identifiers );
    }

    public OrganismImpl( int taxid ) {
        addIdentifier( new CrossReferenceImpl( DEFAULT_DATABASE, String.valueOf( taxid ) ) );
    }

    public OrganismImpl( int taxid, String name ) {
        addIdentifier( new CrossReferenceImpl( DEFAULT_DATABASE, String.valueOf( taxid ), name ) );
    }

    ////////////////
    //

    public void addIdentifier( CrossReference ref ) {
        identifiers.add( ref );
    }

    ////////////////////////
    // Getters & Setters


    public List<CrossReference> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers( List<CrossReference> identifiers ) {
        if ( identifiers == null ) {
            throw new IllegalArgumentException( "Identifiers cannot be null." );
        }
        this.identifiers.addAll(identifiers);
    }

    ///////////////////
    // Utility

    public String getTaxid() {
        for ( CrossReference crossReference : identifiers ) {
            if(  DEFAULT_DATABASE.equals(crossReference.getDatabase() ) ) {
                return crossReference.getIdentifier();
            }
        }
        return null;
    }

    /////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Organism" );
        sb.append( "{identifiers=" ).append( identifiers );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        OrganismImpl organism = ( OrganismImpl ) o;

        if ( !identifiers.equals( organism.identifiers ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return identifiers.hashCode();
    }
}