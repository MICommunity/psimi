/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Crossreference to an external database. Crossreferences to literature databases, e.g. PubMed, should not be put into
 * this structure, but into the bibRef element where possible.
 * <p/>
 * <p>Java class for xrefType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="xrefType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="primaryRef" type="{net:sf:psidev:mi}dbReferenceType"/>
 *         &lt;element name="secondaryRef" type="{net:sf:psidev:mi}dbReferenceType" maxOccurs="unbounded"
 * minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Xref {

    private DbReference primaryRef;

    private Collection<DbReference> secondaryRef;

    ///////////////////////////
    // Constructors

    public Xref() {
    }

    public Xref( DbReference primaryRef ) {
        setPrimaryRef( primaryRef );
    }

    public Xref( DbReference primaryRef, Collection<DbReference> secondaryRef ) {
        setPrimaryRef( primaryRef );
        this.secondaryRef = secondaryRef;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the primaryRef property.
     *
     * @return possible object is {@link DbReference }
     */
    public DbReference getPrimaryRef() {
        return primaryRef;
    }

    /**
     * Sets the value of the primaryRef property.
     *
     * @param value allowed object is {@link DbReference }
     */
    public void setPrimaryRef( DbReference value ) {
        this.primaryRef = value;
    }

    /**
     * Check if the optional secondaryRef is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasSecondaryRef() {
        return ( secondaryRef != null ) && ( !secondaryRef.isEmpty() );
    }

    /**
     * Gets the value of the secondaryRef property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the secondaryRef property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecondaryRef().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link DbReference }
     */
    public Collection<DbReference> getSecondaryRef() {
        if ( secondaryRef == null ) {
            secondaryRef = new ArrayList<DbReference>();
        }
        return this.secondaryRef;
    }

    public Collection<DbReference> getAllDbReferences() {
        Collection<DbReference> refs = new ArrayList<DbReference>();
        if ( primaryRef != null ) {
            refs.add( primaryRef );
        }
        if ( secondaryRef != null ) {
            refs.addAll( secondaryRef );
        }
        return refs;
    }

    //////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Xref" );
        sb.append( "{primaryRef=" ).append( primaryRef );
        sb.append( ", secondaryRef=" ).append( secondaryRef );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        final Xref xref = ( Xref ) o;

        if ( !primaryRef.equals( xref.primaryRef ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return primaryRef.hashCode();
    }
}