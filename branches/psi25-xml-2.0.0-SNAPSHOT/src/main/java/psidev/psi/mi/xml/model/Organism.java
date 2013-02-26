/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.clone.CvTermCloner;
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
    protected void initialiseAliases() {
        initialiseAliasesWith(new OrganismAliasList());
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
            if (this.names == null){
                this.names = new OrganismNames();
            }
            else {
                getAliases().clear();
            }
            super.setCommonName(value.getShortLabel());
            super.setScientificName(value.getFullName());
            getAliases().addAll(value.getAliases());
        }
        else if (this.names != null){
            getAliases().clear();
            super.setCommonName(null);
            super.setScientificName(null);
            this.names = null;
        }
    }

    /**
     * Check if the optional cellType is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasCellType() {
        return getCellType() != null;
    }

    /**
     * Gets the value of the cellType property.
     *
     * @return possible object is {@link OpenCvType }
     */
    public CellType getCellType() {
        return (CellType) super.getCellType();
    }

    /**
     * Sets the value of the cellType property.
     *
     * @param value allowed object is {@link OpenCvType }
     */
    public void setCellType( CellType value ) {
        super.setCellType(value);
    }

    /**
     * Check if the optional compartment is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasCompartment() {
        return getCompartment() != null;
    }

    /**
     * Gets the value of the compartment property.
     *
     * @return possible object is {@link OpenCvType }
     */
    public Compartment getCompartment() {
        return (Compartment) super.getCompartment();
    }

    /**
     * Sets the value of the compartment property.
     *
     * @param value allowed object is {@link OpenCvType }
     */
    public void setCompartment( Compartment value ) {
        super.setCompartment(value);
    }

    /**
     * Check if the optional tissue is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasTissue() {
        return getTissue() != null;
    }

    /**
     * Gets the value of the tissue property.
     *
     * @return possible object is {@link OpenCvType }
     */
    public Tissue getTissue() {
        return (Tissue)super.getTissue();
    }

    /**
     * Sets the value of the tissue property.
     *
     * @param value allowed object is {@link OpenCvType }
     */
    public void setTissue( Tissue value ) {
        super.setTissue(value);
    }

    /**
     * Gets the value of the ncbiTaxId property.
     */
    public int getNcbiTaxId() {
        return super.getTaxId();
    }

    /**
     * Sets the value of the ncbiTaxId property.
     */
    public void setNcbiTaxId( int value ) {
        super.setTaxId(value);
    }

    //////////////////////////
    // Object override

    @Override
    public void setCommonName(String name) {
        if (names != null){
            super.setCommonName(name);
        }
        else if (name != null) {
            names = new OrganismNames();
            super.setCommonName(name);
        }
    }

    @Override
    public void setScientificName(String name) {
        if (names != null){
            super.setCommonName(name);
            super.setScientificName(name);
        }
        else if (name != null) {
            names = new OrganismNames();
            super.setCommonName(name);
            super.setScientificName(name);
        }
    }

    protected void setCommonNameOnly(String name) {
        super.setCommonName(name);
    }

    protected void setScientificNameOnly(String name) {
        super.setScientificName(name);
    }

    protected Collection<psidev.psi.mi.jami.model.Alias> getOrganismAliases(){
        return super.getAliases();
    }

    @Override
    public void setTissue(CvTerm tissue) {
        if (tissue == null){
            super.setTissue(null);
        }
        else if (tissue instanceof Tissue){
            super.setTissue(tissue);
        }
        else {
            Tissue t = new Tissue();
            CvTermCloner.copyAndOverrideCvTermProperties(tissue, t);
            super.setTissue(t);
        }
    }

    @Override
    public void setCompartment(CvTerm compartment) {
        if (compartment == null){
            super.setCompartment(null);
        }
        else if (compartment instanceof Compartment){
            super.setCompartment(compartment);
        }
        else {
            Compartment t = new Compartment();
            CvTermCloner.copyAndOverrideCvTermProperties(compartment, t);
            super.setCompartment(t);
        }
    }

    @Override
    public void setCellType(CvTerm cellType) {
        if (cellType == null){
            super.setCellType(null);
        }
        else if (cellType instanceof CellType){
            super.setCellType(cellType);
        }
        else {
            CellType t = new CellType();
            CvTermCloner.copyAndOverrideCvTermProperties(cellType, t);
            super.setCellType(t);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Organism" );
        sb.append( "{names=" ).append( names );
        sb.append( ", cellType=" ).append( getCellType() );
        sb.append( ", compartment=" ).append( getCompartment() );
        sb.append( ", tissue=" ).append( getTissue() );
        sb.append( ", ncbiTaxId=" ).append( getTaxId() );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Organism organism = ( Organism ) o;

        if ( getTaxId() != organism.getTaxId() ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31;
        //result = (names != null ? names.hashCode() : 0);
        //result = 31 * result + (cellType != null ? cellType.hashCode() : 0);
        //result = 31 * result + (compartment != null ? compartment.hashCode() : 0);
        //result = 31 * result + (tissue != null ? tissue.hashCode() : 0);
        result = 31 * result + getTaxId();
        return result;
    }

    protected class OrganismNames extends Names{

        protected AliasList extendedAliases = new AliasList();

        public String getShortLabel() {
            return getCommonName();
        }

        public boolean hasShortLabel() {
            return getCommonName() != null;
        }

        public void setShortLabel( String value ) {
            setCommonNameOnly(value);
        }

        public String getFullName() {
            return getScientificName();
        }

        public boolean hasFullName() {
            return getScientificName() != null;
        }

        public void setFullName( String value ) {
            setScientificNameOnly(value);
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
            sb.append( "{shortLabel='" ).append(getCommonName()).append( '\'' );
            sb.append( ", fullName='" ).append( getScientificName() ).append( '\'' );
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
            if ( getScientificName() != null ? !getScientificName().equals(names.getFullName()) : names.getFullName() != null ) return false;
            if ( getCommonName() != null ? !getCommonName().equals(names.getShortLabel()) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( getCommonName() != null ? getCommonName().hashCode() : 0 );
            result = 31 * result + ( getScientificName() != null ? getScientificName().hashCode() : 0 );
            result = 31 * result + ( extendedAliases != null ? extendedAliases.hashCode() : 0 );
            return result;
        }

        protected class AliasList extends AbstractListHavingPoperties<Alias> {

            @Override
            protected void processAddedObjectEvent(Alias added) {
                ((OrganismAliasList) getOrganismAliases()).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((OrganismAliasList)getOrganismAliases()).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((OrganismAliasList)getOrganismAliases()).clearOnly();
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