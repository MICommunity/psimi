/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.Collection;

/**
 * Allows to reference an external controlled vocabulary, or to directly include a value if no suitable external
 * definition is available.
 * <p/>
 * <p>Java class for openCvType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="openCvType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
public abstract class OpenCvType extends CvType {

    private Collection<Attribute> attributes=new CvTermXmlAnnotationList();

    ///////////////////////////
    // Constructors

    protected OpenCvType() {
    }

    protected OpenCvType( Collection<Attribute> attributes ) {
        if (attributes != null){
            this.attributes.addAll(attributes);
        }
    }

    protected OpenCvType( Names names, Xref xref, Collection<Attribute> attributes ) {
        super( names, xref );
        if (attributes != null){
            this.attributes.addAll(attributes);
        }
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new CvTermAnnotationList();
    }

    ///////////////////////////
    // Getters and Setters

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
        return attributes;
    }

    //////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder( 128 );
        sb.append( "OpenCvType" );
        sb.append( "{attributes=" ).append( attributes );
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

        final OpenCvType that = ( OpenCvType ) o;

        if ( attributes != null ? !attributes.equals( that.attributes ) : that.attributes != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return ( attributes != null ? attributes.hashCode() : 0 );
    }

    protected class CvTermAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public CvTermAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((CvTermXmlAnnotationList)attributes).addOnly((Attribute) added);
            }
            else {
                ((CvTermXmlAnnotationList)attributes).addOnly(new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue()));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((CvTermXmlAnnotationList)attributes).removeOnly(removed);
            }
            else {
                ((CvTermXmlAnnotationList)attributes).removeOnly(new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue()));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((CvTermXmlAnnotationList)attributes).clearOnly();
        }
    }

    protected class CvTermXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public CvTermXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {

            // we added a annotation, needs to add it in annotations
            ((CvTermAnnotationList)annotations).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            // we removed a annotation, needs to remove it in annotations
            ((CvTermAnnotationList)annotations).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((CvTermAnnotationList)annotations).clearOnly();
        }
    }
}