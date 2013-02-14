/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


/**
 * Describes the biological source of an object, in simple form only the NCBI taxid.
 * <p/>
 * <p>Java class for bioSourceType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="bioSourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *         &lt;element name="cellType" type="{net:sf:psidev:mi}openCvType" minOccurs="0"/>
 *         &lt;element name="compartment" type="{net:sf:psidev:mi}openCvType" minOccurs="0"/>
 *         &lt;element name="tissue" type="{net:sf:psidev:mi}openCvType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ncbiTaxId" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Organism implements NamesContainer {

    private Names names;

    private CellType cellType;

    private Compartment compartment;

    private Tissue tissue;

    private int ncbiTaxId;

    ///////////////////////////
    // Constructors

    //TODO Constructors

    public Organism() {
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Check if the optional names is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasNames() {
        return names != null;
    }

    /**
     * Gets the value of the names property.
     *
     * @return possible object is {@link Names }
     */
    public Names getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     *
     * @param value allowed object is {@link Names }
     */
    public void setNames( Names value ) {
        this.names = value;
    }

    /**
     * Check if the optional cellType is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasCellType() {
        return cellType != null;
    }

    /**
     * Gets the value of the cellType property.
     *
     * @return possible object is {@link OpenCvType }
     */
    public CellType getCellType() {
        return cellType;
    }

    /**
     * Sets the value of the cellType property.
     *
     * @param value allowed object is {@link OpenCvType }
     */
    public void setCellType( CellType value ) {
        this.cellType = value;
    }

    /**
     * Check if the optional compartment is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasCompartment() {
        return compartment != null;
    }

    /**
     * Gets the value of the compartment property.
     *
     * @return possible object is {@link OpenCvType }
     */
    public Compartment getCompartment() {
        return compartment;
    }

    /**
     * Sets the value of the compartment property.
     *
     * @param value allowed object is {@link OpenCvType }
     */
    public void setCompartment( Compartment value ) {
        this.compartment = value;
    }

    /**
     * Check if the optional tissue is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasTissue() {
        return tissue != null;
    }

    /**
     * Gets the value of the tissue property.
     *
     * @return possible object is {@link OpenCvType }
     */
    public Tissue getTissue() {
        return tissue;
    }

    /**
     * Sets the value of the tissue property.
     *
     * @param value allowed object is {@link OpenCvType }
     */
    public void setTissue( Tissue value ) {
        this.tissue = value;
    }

    /**
     * Gets the value of the ncbiTaxId property.
     */
    public int getNcbiTaxId() {
        return ncbiTaxId;
    }

    /**
     * Sets the value of the ncbiTaxId property.
     */
    public void setNcbiTaxId( int value ) {
        this.ncbiTaxId = value;
    }

    //////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Organism" );
        sb.append( "{names=" ).append( names );
        sb.append( ", cellType=" ).append( cellType );
        sb.append( ", compartment=" ).append( compartment );
        sb.append( ", tissue=" ).append( tissue );
        sb.append( ", ncbiTaxId=" ).append( ncbiTaxId );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Organism organism = ( Organism ) o;

        if ( ncbiTaxId != organism.ncbiTaxId ) return false;
        //if (cellType != null ? !cellType.equals(organism.cellType) : organism.cellType != null) return false;
        //if (compartment != null ? !compartment.equals(organism.compartment) : organism.compartment != null)
        //    return false;
        //if (names != null ? !names.equals(organism.names) : organism.names != null) return false;
        //if (tissue != null ? !tissue.equals(organism.tissue) : organism.tissue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31;
        //result = (names != null ? names.hashCode() : 0);
        //result = 31 * result + (cellType != null ? cellType.hashCode() : 0);
        //result = 31 * result + (compartment != null ? compartment.hashCode() : 0);
        //result = 31 * result + (tissue != null ? tissue.hashCode() : 0);
        result = 31 * result + ncbiTaxId;
        return result;
    }
}