/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

/**
 * Simple description of confidence score.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class ConfidenceImpl implements Confidence {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = 7009701156529411485L;

    /////////////////////
    // Instance variables


    /**
     * Type of the confidence value.
     */
    private String type;

    /**
     * ConfidenceImpl value.
     */
    private String value;
    
    /**
     * Optional text explaining the confidence value.
     */
    private String text;

    //////////////////////
    // Constructor

    public ConfidenceImpl() {
    }

    public ConfidenceImpl( String type, String value ) {
        this( type, value, null );
    }

    public ConfidenceImpl( String type, String value, String text ) {
        this.type = type;
        setValue( value );
        this.text = text;
    }

    //////////////////////
    // Getters and Setters

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        if ( value == null ) {
            throw new IllegalArgumentException( "You must give a non null value." );
        }
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        if ( text != null ) {
            // ignore empty string
            text = text.trim();
            if ( text.length() == 0 ) {
                text = null;
            }
        }
        this.text = text;
    }

    /////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ConfidenceImpl" );
        sb.append( "{type='" ).append( type ).append( '\'' );
        sb.append( ", value='" ).append( value ).append( '\'' );
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

        final ConfidenceImpl that = ( ConfidenceImpl ) o;

        if ( type != null ? !type.equals( that.type ) : that.type != null ) {
            return false;
        }
        if ( !value.equals( that.value ) ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( type != null ? type.hashCode() : 0 );
        result = 29 * result + value.hashCode();
        return result;
    }
}