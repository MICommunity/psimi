/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;

import java.util.ArrayList;
import java.util.Collection;


public class Interaction extends DefaultInteractionEvidence implements Complex, HasId, NamesContainer, XrefContainer, AttributeContainer {

    private int id;

    private Names names;

    private Xref xref;

    private Availability availability;

    private Collection<ExperimentDescription> experiments;

    private Collection<ExperimentRef> experimentRefs;

    private Collection<Participant> participants;

    private Collection<InferredInteraction> inferredInteractions;

    private Collection<InteractionType> interactionTypes;

    private Boolean modelled;

    private Boolean intraMolecular;

    private Collection<Confidence> confidences;

    private Collection<Parameter> parameters;

    private Collection<Attribute> attributeList;

    ///////////////////////////
    // Constructors

    public Interaction() {
        super();
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
     * Check if the optional imexId is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasImexId() {
        return imexId != null;
    }

    /**
     * Gets the value of the imexId property.
     *
     * @return possible object is {@link String }
     */
    public String getImexId() {
        return imexId;
    }

    /**
     * Sets the value of the imexId property.
     *
     * @param value allowed object is {@link String }
     */
    public void setImexId( String value ) {
        this.imexId = value;
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
     * Check if the optional availability is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasAvailability() {
        return availability != null;
    }

    /**
     * Gets the value of the availability property.
     *
     * @return possible object is {@link Availability }
     */
    public Availability getAvailability() {
        return availability;
    }

    /**
     * Sets the value of the availability property.
     *
     * @param value allowed object is {@link Availability }
     */
    public void setAvailability( Availability value ) {
        this.availability = value;
    }

    public boolean hasExperiments() {
        return experiments != null && !experiments.isEmpty();
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

    public Collection<Component> getComponents() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getPhysicalProperties() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPhysicalProperties(String properties) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean addComponent(Component part) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean removeComponent(Component part) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean addAllComponents(Collection<? extends Component> part) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean removeAllComponents(Collection<? extends Component> part) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasExperimentRefs() {
        return experimentRefs != null && !experimentRefs.isEmpty();
    }

    /**
     * Gets the value of the experimentList property.
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
     * Gets the value of the participantList property.
     *
     * @return possible object is {@link Participant }
     */
    public Collection<Participant> getParticipants() {
        if ( participants == null ) {
            participants = new ArrayList<Participant>();
        }
        return participants;
    }

    /**
     * Check if the optional inferredInteractions is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInferredInteractions() {
        return ( inferredInteractions != null ) && ( !inferredInteractions.isEmpty() );
    }

    /**
     * Gets the value of the inferredInteractionList property.
     *
     * @return possible object is {@link InferredInteraction }
     */
    public Collection<InferredInteraction> getInferredInteractions() {
        if ( inferredInteractions == null ) {
            inferredInteractions = new ArrayList<InferredInteraction>();
        }
        return inferredInteractions;
    }

    /**
     * Check if the optional interactionTypes is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInteractionTypes() {
        return ( interactionTypes != null ) && ( !interactionTypes.isEmpty() );
    }

    /**
     * Gets the value of the interactionType property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the interactionType property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInteractionType().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link CvType }
     */
    public Collection<InteractionType> getInteractionTypes() {
        if ( interactionTypes == null ) {
            interactionTypes = new ArrayList<InteractionType>();
        }
        return interactionTypes;
    }

    /**
     * Check if the optional modelled is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasModelled() {
        return modelled != null;
    }

    /**
     * Gets the value of the modelled property.
     *
     * @return possible object is {@link Boolean }
     */
    public boolean isModelled() {
        if ( modelled == null ) {
            return false;
        }
        return modelled;
    }

    /**
     * Sets the value of the modelled property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setModelled( boolean value ) {
        this.modelled = value;
    }

    /**
     * Gets the value of the intraMolecular property.
     *
     * @return possible object is {@link Boolean }
     */
    public boolean isIntraMolecular() {
        if ( intraMolecular == null ) {
            return false;
        }
        return intraMolecular;
    }

    /**
     * Sets the value of the intraMolecular property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setIntraMolecular( boolean value ) {
        this.intraMolecular = value;
    }

    /**
     * Gets the value of the negative property.
     *
     * @return possible object is {@link Boolean }
     */
    public boolean isNegative() {
        if ( negative == null ) {
            return false;
        }
        return negative;
    }

    /**
     * Sets the value of the negative property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setNegative( boolean value ) {
        this.negative = value;
    }

    /**
     * Check if the optional confidences is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasConfidences() {
        return ( confidences != null ) && ( !confidences.isEmpty() );
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return possible object is {@link Confidence }
     */
    public Collection<Confidence> getConfidences() {
        if ( confidences == null ) {
            confidences = new ArrayList<Confidence>();
        }
        return confidences;
    }

    /**
     * Check if the optional parameters is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasParameters() {
        return ( parameters != null ) && ( !parameters.isEmpty() );
    }

    /**
     * Gets the value of the parameterList property.
     *
     * @return possible object is {@link Parameter }
     */
    public Collection<Parameter> getParameters() {
        if ( parameters == null ) {
            parameters = new ArrayList<Parameter>();
        }
        return parameters;
    }

    /**
     * Check if the optional attributes is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasAttributes() {
        return ( attributeList != null ) && ( !attributeList.isEmpty() );
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return possible object is {@link Attribute }
     */
    public Collection<Attribute> getAttributes() {
        if ( attributeList == null ) {
            attributeList = new ArrayList<Attribute>();
        }
        return attributeList;
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return possible object is {@link Attribute }
     */
    @Deprecated
    public Collection<Attribute> getAttributeList() {
        return getAttributes();
    }

    ///////////////////////////
    // Object override


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Interaction" );
        sb.append( "{id=" ).append( id );
        sb.append( ", imexId='" ).append( imexId ).append( '\'' );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", availability=" ).append( availability );
        sb.append( ", experiments=" ).append( experiments );
        sb.append( ", participants=" ).append( participants );
        sb.append( ", inferredInteractions=" ).append( inferredInteractions );
        sb.append( ", interactionTypes=" ).append( interactionTypes );
        sb.append( ", modelled=" ).append( modelled );
        sb.append( ", intraMolecular=" ).append( intraMolecular );
        sb.append( ", negative=" ).append( negative );
        sb.append( ", confidences=" ).append( confidences );
        sb.append( ", parameters=" ).append( parameters );
        sb.append( ", attributes=" ).append( attributeList );
        sb.append( '}' );
        return sb.toString();
    }

    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Interaction that = ( Interaction ) o;

        if ( id != that.id ) return false;
        if ( attributeList != null ? !attributeList.equals( that.attributeList ) : that.attributeList != null )
            return false;
        if ( availability != null ? !availability.equals( that.availability ) : that.availability != null )
            return false;
        if ( confidences != null ? !confidences.equals( that.confidences ) : that.confidences != null ) return false;
        if ( experiments != null ? !experiments.equals( that.experiments ) : that.experiments != null ) return false;
        if ( imexId != null ? !imexId.equals( that.imexId ) : that.imexId != null ) return false;
        if ( inferredInteractions != null ? !inferredInteractions.equals( that.inferredInteractions ) : that.inferredInteractions != null )
            return false;
        if ( interactionTypes != null ? !interactionTypes.equals( that.interactionTypes ) : that.interactionTypes != null )
            return false;
        if ( intraMolecular != null ? !intraMolecular.equals( that.intraMolecular ) : that.intraMolecular != null )
            return false;
        if ( modelled != null ? !modelled.equals( that.modelled ) : that.modelled != null ) return false;
        if ( names != null ? !names.equals( that.names ) : that.names != null ) return false;
        if ( negative != null ? !negative.equals( that.negative ) : that.negative != null ) return false;
        if ( parameters != null ? !parameters.equals( that.parameters ) : that.parameters != null ) return false;
        if ( participants != null ? !participants.equals( that.participants ) : that.participants != null )
            return false;
        if ( xref != null ? !xref.equals( that.xref ) : that.xref != null ) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = id;
        result = 31 * result + ( imexId != null ? imexId.hashCode() : 0 );
        result = 31 * result + ( names != null ? names.hashCode() : 0 );
        result = 31 * result + ( xref != null ? xref.hashCode() : 0 );
        result = 31 * result + ( availability != null ? availability.hashCode() : 0 );
        result = 31 * result + ( experiments != null ? experiments.hashCode() : 0 );
        result = 31 * result + ( participants != null ? participants.hashCode() : 0 );
        result = 31 * result + ( inferredInteractions != null ? inferredInteractions.hashCode() : 0 );
        result = 31 * result + ( interactionTypes != null ? interactionTypes.hashCode() : 0 );
        result = 31 * result + ( modelled != null ? modelled.hashCode() : 0 );
        result = 31 * result + ( intraMolecular != null ? intraMolecular.hashCode() : 0 );
        result = 31 * result + ( negative != null ? negative.hashCode() : 0 );
        result = 31 * result + ( confidences != null ? confidences.hashCode() : 0 );
        result = 31 * result + ( parameters != null ? parameters.hashCode() : 0 );
        result = 31 * result + ( attributeList != null ? attributeList.hashCode() : 0 );
        return result;
    }

    public String getFullName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFullName(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Alias> getAliases() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Organism getOrganism() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setOrganism(Organism organism) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}