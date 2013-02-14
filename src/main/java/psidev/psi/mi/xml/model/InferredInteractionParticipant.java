/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="participant" maxOccurs="unbounded" minOccurs="2">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="participantRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="participantFeatureRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class InferredInteractionParticipant {

    private ParticipantRef participantRef;

    private Participant participant;

    private FeatureRef featureRef;

    private Feature feature;

    ///////////////////////////
    // Constructors

    public InferredInteractionParticipant() {
    }

    public InferredInteractionParticipant( Participant participant ) {
        this.participant = participant;
    }

    public InferredInteractionParticipant( ParticipantRef participantRef ) {
        this.participantRef = participantRef;
    }

    public InferredInteractionParticipant( Feature feature ) {
        this.feature = feature;
    }

    public InferredInteractionParticipant( FeatureRef featureRef ) {
        this.featureRef = featureRef;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Check if the optional participant is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasParticipant() {
        return participant != null;
    }

    /**
     * Check if the optional participantRef is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasParticipantRef() {
        return participantRef != null;
    }

    public void setFeature( Feature feature ) {
        this.feature = feature;
    }

    /**
     * Gets a participant.
     */
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant( Participant participant ) {
        this.participant = participant;
    }

    public ParticipantRef getParticipantRef() {
        return participantRef;
    }

    public void setParticipantRef( ParticipantRef participantRef ) {
        this.participantRef = participantRef;
    }

    /**
     * Check if the optional feature is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeature() {
        return feature != null;
    }

    /**
     * Check if the optional featureRef is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatureRef() {
        return featureRef != null;
    }

    /**
     * Gets a feature.
     *
     * @return possible object is {@link psidev.psi.mi.xml.model.Feature }
     */
    public Feature getFeature() {
        return feature;
    }

    public FeatureRef getFeatureRef() {
        return featureRef;
    }

    public void setFeatureRef( FeatureRef featureRef ) {
        this.featureRef = featureRef;
    }

    //////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "InferredInteractionParticipant" );
        sb.append( "{participant=" ).append( participant );
        sb.append( ", feature=" ).append( feature );
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

        final InferredInteractionParticipant that = ( InferredInteractionParticipant ) o;

        if ( feature != null ? !feature.equals( that.feature ) : that.feature != null ) {
            return false;
        }
        if ( participant != null ? !participant.equals( that.participant ) : that.participant != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( participant != null ? participant.hashCode() : 0 );
        result = 29 * result + ( feature != null ? feature.hashCode() : 0 );
        return result;
    }
}