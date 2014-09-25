/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;

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

public class InferredInteraction {

    //TODO How do we handle the constraint ? Accoring to the schema, cardinality must be 2 or more !!
    private Collection<InferredInteractionParticipant> participant;

    private Collection<ExperimentRef> experimentRefs;

    private Collection<ExperimentDescription> experiments;

    ///////////////////////////
    // Constructors

    public InferredInteraction() {
    }

    public InferredInteraction( Collection<InferredInteractionParticipant> participants ) {
        this.participant = participants;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the participant property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the participant property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipant().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link Participant }
     */
    public Collection<InferredInteractionParticipant> getParticipant() {
        if ( participant == null ) {
            participant = new ArrayList<InferredInteractionParticipant>();
        }
        return this.participant;
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
     * @return possible object is {@link ExperimentRef }
     */
    public Collection<ExperimentRef> getExperimentRefs() {
        if ( experimentRefs == null ) {
            experimentRefs = new ArrayList<ExperimentRef>();
        }
        return experimentRefs;
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return possible object is {@link ExperimentDescription }
     */
    public Collection<ExperimentDescription> getExperiments() {
        if ( experiments == null ) {
            experiments = new ArrayList<ExperimentDescription>();
        }
        return experiments;
    }

    //////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "InferredInteraction" );
        sb.append( "{participants=" ).append( participant );
        sb.append( ", experimentRefs=" ).append( experimentRefs );
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

        final InferredInteraction that = ( InferredInteraction ) o;

        if ( experimentRefs != null ? !experimentRefs.equals( that.experimentRefs ) : that.experimentRefs != null ) {
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
        result = 29 * result + ( experimentRefs != null ? experimentRefs.hashCode() : 0 );
        return result;
    }
}