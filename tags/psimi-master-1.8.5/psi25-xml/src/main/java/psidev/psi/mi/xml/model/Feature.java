/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;

/**
 * A feature, e.g. domain, on a sequence.
 * <p/>
 * <p>Java class for featureElementType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="featureElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="featureType" type="{net:sf:psidev:mi}cvType" minOccurs="0"/>
 *         &lt;element name="featureDetectionMethod" type="{net:sf:psidev:mi}cvType" minOccurs="0"/>
 *         &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType" minOccurs="0"/>
 *         &lt;element name="featureRangeList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="featureRange" type="{net:sf:psidev:mi}baseLocationType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Feature implements HasId, NamesContainer, XrefContainer, AttributeContainer {

    private int id;

    private Names names;

    private Xref xref;

    private FeatureType featureType;

    private FeatureDetectionMethod featureDetectionMethod;

    private Collection<ExperimentDescription> experiments;

    private Collection<ExperimentRef> experimentRefs;

    private Collection<Range> ranges;

    private Collection<Attribute> attributes;

    ///////////////////////////
    // Constructors

    public Feature() {
    }

    public Feature( int id, Collection<Range> ranges ) {
        this.ranges = ranges;
        setId( id );
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId( int value ) {
        this.id = value;
    }

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
     * Check if the optional xref is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasXref() {
        return xref != null;
    }

    /**
     * Gets the value of the xref property.
     *
     * @return possible object is {@link Xref }
     */
    public Xref getXref() {
        return xref;
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value allowed object is {@link Xref }
     */
    public void setXref( Xref value ) {
        this.xref = value;
    }

    /**
     * Check if the optional featureType is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatureType() {
        return featureType != null;
    }

    /**
     * Gets the value of the featureType property.
     *
     * @return possible object is {@link CvType }
     */
    public FeatureType getFeatureType() {
        return featureType;
    }

    /**
     * Sets the value of the featureType property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setFeatureType( FeatureType value ) {
        this.featureType = value;
    }

    /**
     * Check if the optional featureDetectionMethod is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatureDetectionMethod() {
        return featureDetectionMethod != null;
    }

    /**
     * Gets the value of the featureDetectionMethod property.
     *
     * @return possible object is {@link CvType }
     */
    public FeatureDetectionMethod getFeatureDetectionMethod() {
        return featureDetectionMethod;
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setFeatureDetectionMethod( FeatureDetectionMethod value ) {
        this.featureDetectionMethod = value;
    }

    /**
     * Check if the optional experiments is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperiments() {
        return ( experiments != null ) && ( !experiments.isEmpty() );
    }

    /**
     * Gets the value of the experimentRefList property.
     *
     * @return possible object is {@link ExperimentDescription }
     */
    public Collection<ExperimentDescription> getExperiments() {
        if ( experiments == null ) {
            experiments = new ArrayList<ExperimentDescription>();
        }
        return experiments;
    }

    /**
     * Check if the optional experimentRefs is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperimentRefs() {
        return experimentRefs != null && !experimentRefs.isEmpty();
    }

    /**
     * Gets the value of the experimentRef property.
     *
     * @return possible object is {@link ExperimentRef }
     */
    public Collection<ExperimentRef> getExperimentRefs() {
        if ( experimentRefs == null ) {
            experimentRefs = new ArrayList<ExperimentRef>();
        }
        return experimentRefs;
    }

    /**
     * Gets the value of the featureRangeList property.
     *
     * @return possible object is {@link Range }
     */
    public Collection<Range> getRanges() {
        if ( ranges == null ) {
            ranges = new ArrayList<Range>();
        }
        return ranges;
    }

    /**
     * Check if the optional attributes is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasAttributes() {
        return ( attributes != null ) && ( !attributes.isEmpty() );
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return possible object is {@link Attribute }
     */
    public Collection<Attribute> getAttributes() {
        if ( attributes == null ) {
            attributes = new ArrayList<Attribute>();
        }
        return attributes;
    }

    /////////////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Feature" );
        sb.append( "{id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", featureType=" ).append( featureType );
        sb.append( ", featureDetectionMethod=" ).append( featureDetectionMethod );
        sb.append( ", experiments=" ).append( experiments );
        sb.append( ", experimentRefs=" ).append( experimentRefs );
        sb.append( ", ranges=" ).append( ranges );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Feature feature = ( Feature ) o;

        if ( id != feature.id ) return false;
        if ( attributes != null ? !attributes.equals( feature.attributes ) : feature.attributes != null ) return false;
        if ( experimentRefs != null ? !experimentRefs.equals( feature.experimentRefs ) : feature.experimentRefs != null )
            return false;
        if ( experiments != null ? !experiments.equals( feature.experiments ) : feature.experiments != null )
            return false;
        if ( featureDetectionMethod != null ? !featureDetectionMethod.equals( feature.featureDetectionMethod ) : feature.featureDetectionMethod != null )
            return false;
        if ( featureType != null ? !featureType.equals( feature.featureType ) : feature.featureType != null )
            return false;
        if ( names != null ? !names.equals( feature.names ) : feature.names != null ) return false;
        if ( ranges != null ? !ranges.equals( feature.ranges ) : feature.ranges != null ) return false;
        if ( xref != null ? !xref.equals( feature.xref ) : feature.xref != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = id;
        result = 31 * result + ( names != null ? names.hashCode() : 0 );
        result = 31 * result + ( xref != null ? xref.hashCode() : 0 );
        result = 31 * result + ( featureType != null ? featureType.hashCode() : 0 );
        result = 31 * result + ( featureDetectionMethod != null ? featureDetectionMethod.hashCode() : 0 );
        result = 31 * result + ( experiments != null ? experiments.hashCode() : 0 );
        result = 31 * result + ( experimentRefs != null ? experimentRefs.hashCode() : 0 );
        result = 31 * result + ( ranges != null ? ranges.hashCode() : 0 );
        result = 31 * result + ( attributes != null ? attributes.hashCode() : 0 );
        return result;
    }
}