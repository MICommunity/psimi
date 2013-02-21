/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.Collection;

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

public class Organism extends DefaultOrganism implements NamesContainer {

    private Names names;

    ///////////////////////////
    // Constructors

    //TODO Constructors

    public Organism() {
        super(-3);
    }

    @Override
    protected void initializeAliases() {
        this.aliases = new OrganismAliasList();
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
        if (value != null){
            this.names.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            this.names.getAliases().addAll(value.getAliases());
        }
        else {
            aliases.clear();
            this.commonName = null;
            this.scientificName = null;
            this.names = null;
        }
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
        return (CellType) cellType;
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
        return (Compartment) compartment;
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
        return (Tissue)tissue;
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
        return taxId;
    }

    /**
     * Sets the value of the ncbiTaxId property.
     */
    public void setNcbiTaxId( int value ) {
        this.taxId = value;
    }

    //////////////////////////
    // Object override

    @Override
    public void setCommonName(String name) {
        if (names != null){
            names.setShortLabel(name);
        }
        else {
            names = new OrganismNames();
            names.setShortLabel(name);
        }
    }

    @Override
    public void setScientificName(String name) {
        if (names != null){
            if (names.getShortLabel() == null){
                names.setShortLabel(name);
            }
            names.setFullName(name);
        }
        else {
            names = new OrganismNames();
            names.setShortLabel(name);
            names.setFullName(name);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Organism" );
        sb.append( "{names=" ).append( names );
        sb.append( ", cellType=" ).append( cellType );
        sb.append( ", compartment=" ).append( compartment );
        sb.append( ", tissue=" ).append( tissue );
        sb.append( ", ncbiTaxId=" ).append( taxId );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Organism organism = ( Organism ) o;

        if ( taxId != organism.taxId ) return false;
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
        result = 31 * result + taxId;
        return result;
    }

    protected class OrganismNames extends Names{

        protected AliasList extendedAliases = new AliasList();

        public String getShortLabel() {
            return commonName;
        }

        public boolean hasShortLabel() {
            return commonName != null;
        }

        public void setShortLabel( String value ) {
            if (value != null){
                commonName = value;
            }
            else {
                commonName = null;
            }
        }

        public String getFullName() {
            return scientificName;
        }

        public boolean hasFullName() {
            return scientificName != null;
        }

        public void setFullName( String value ) {
            scientificName = value;
        }

        public Collection<Alias> getAliases() {
            return this.extendedAliases;
        }

        public boolean hasAliases() {
            return ( extendedAliases != null ) && ( !extendedAliases.isEmpty() );
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Names" );
            sb.append( "{shortLabel='" ).append( commonName ).append( '\'' );
            sb.append( ", fullName='" ).append( scientificName ).append( '\'' );
            sb.append( ", aliases=" ).append( extendedAliases );
            sb.append( '}' );
            return sb.toString();
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            Names names = ( Names ) o;

            if ( extendedAliases != null ? !extendedAliases.equals( names.getAliases() ) : names.getAliases() != null ) return false;
            if ( scientificName != null ? !scientificName.equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( commonName != null ? !commonName.equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( commonName != null ? commonName.hashCode() : 0 );
            result = 31 * result + ( scientificName != null ? scientificName.hashCode() : 0 );
            result = 31 * result + ( extendedAliases != null ? extendedAliases.hashCode() : 0 );
            return result;
        }

        protected class AliasList extends AbstractListHavingPoperties<Alias> {

            @Override
            protected void processAddedObjectEvent(Alias added) {
                ((OrganismAliasList) aliases).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((OrganismAliasList)aliases).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((OrganismAliasList)aliases).clearOnly();
            }
        }
    }

    private class OrganismAliasList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Alias> {
        public OrganismAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Alias added) {

            if (names != null){
                OrganismNames name = (OrganismNames) names;

                if (added instanceof Alias){
                    ((OrganismNames.AliasList) name.getAliases()).addOnly((Alias) added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    ((OrganismNames.AliasList) name.getAliases()).addOnly(fixedAlias);
                }
            }
            else {
                if (added instanceof Alias){
                    names = new OrganismNames();
                    ((OrganismNames.AliasList) names.getAliases()).addOnly((Alias) added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    names = new OrganismNames();
                    ((OrganismNames.AliasList) names.getAliases()).addOnly((Alias) fixedAlias);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Alias removed) {

            if (names != null){
                OrganismNames name = (OrganismNames) names;

                if (removed instanceof Alias){
                    name.extendedAliases.removeOnly((Alias) removed);

                }
                else {
                    Alias fixedAlias = new Alias(removed.getName(), removed.getType() != null ? removed.getType().getShortName() : null, removed.getType() != null ? removed.getType().getMIIdentifier() : null);

                    name.extendedAliases.removeOnly(fixedAlias);
                }
            }
        }

        @Override
        protected void clearProperties() {

            if (names != null){
                OrganismNames name = (OrganismNames) names;
                name.extendedAliases.clearOnly();
            }
        }
    }
}