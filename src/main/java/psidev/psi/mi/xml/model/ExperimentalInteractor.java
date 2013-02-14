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
 *         &lt;choice>
 *           &lt;element name="interactorRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="interactor" type="{net:sf:psidev:mi}interactorElementType"/>
 *         &lt;/choice>
 *         &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class ExperimentalInteractor {

    private InteractorRef interactorRef;
    private Interactor interactor;

    private Collection<ExperimentDescription> experiments;
    private Collection<ExperimentRef> experimentRefs;

    ///////////////////////////
    // Constructors

    public ExperimentalInteractor() {
    }

    public ExperimentalInteractor( InteractorRef interactorRef ) {
        this.interactorRef = interactorRef;
    }

    public ExperimentalInteractor( InteractorRef interactorRef, Collection<ExperimentDescription> experiments ) {
        this.interactorRef = interactorRef;
        this.experiments = experiments;
    }

    public ExperimentalInteractor( Interactor interactor ) {
        setInteractor( interactor );
    }

    public ExperimentalInteractor( Interactor interactor, Collection<ExperimentDescription> experiments ) {
        setInteractor( interactor );
        this.experiments = experiments;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the interactorRef property.
     *
     * @return possible object is {@link InteractorRef }
     */
    public InteractorRef getInteractorRef() {
        return interactorRef;
    }

    /**
     * Sets the value of the interactorRef property.
     *
     * @param interactorRef allowed object is {@link InteractorRef }
     */
    public void setInteractorRef( InteractorRef interactorRef ) {
        this.interactorRef = interactorRef;
    }

    public boolean hasInteractorRef() {
        return interactorRef != null;
    }

    /**
     * Gets the value of the interactor property.
     *
     * @return possible object is {@link Interactor }
     */
    public Interactor getInteractor() {
        return interactor;
    }

    /**
     * Sets the value of the interactor property.
     *
     * @param interactor allowed object is {@link Interactor }
     */
    public void setInteractor( Interactor interactor ) {
        this.interactor = interactor;
    }

    public boolean hasInteractor() {
        return interactor != null;
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

    /////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ExperimentalInteractor" );
        sb.append( "{interactor=" ).append( interactor );
        sb.append( ", interactorRef=" ).append( interactorRef );
        sb.append( ", experiments=" ).append( experiments );
        sb.append( ", experimentRefs=" ).append( experimentRefs );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        ExperimentalInteractor that = ( ExperimentalInteractor ) o;

        if ( experimentRefs != null ? !experimentRefs.equals( that.experimentRefs ) : that.experimentRefs != null )
            return false;
        if ( experiments != null ? !experiments.equals( that.experiments ) : that.experiments != null ) return false;
        if ( !interactor.equals( that.interactor ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = interactor.hashCode();
        result = 31 * result + ( experiments != null ? experiments.hashCode() : 0 );
        result = 31 * result + ( experimentRefs != null ? experimentRefs.hashCode() : 0 );
        return result;
    }
}