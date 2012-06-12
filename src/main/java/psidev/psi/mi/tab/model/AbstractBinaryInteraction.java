/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a binary interaction as in the MITAB25 format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public abstract class AbstractBinaryInteraction<T extends Interactor> implements BinaryInteraction<T> {

    ///////////////////////
    // Instance variable

    /**
     * Interactor A.
     */
    private T interactorA;

    /**
     * Interactor B.
     */
    private T interactorB;

    /**
     * Detection method for that interaction.
     */
    private List<InteractionDetectionMethod> detectionMethods;

    /**
     * Types of the interaction
     */
    private List<InteractionType> interactionTypes;

    /**
     * Associated publications of that interaction.
     */
    private List<CrossReference> publications;

    /**
     * Associated confidence value for that interaction.
     */
    private List<Confidence> confidenceValues;

    /**
     * Source databases.
     */
    private List<CrossReference> sourceDatabases;

    /**
     * Identifiers of the interaction that provided the data.
     */
    private List<CrossReference> interactionAcs;

    /**
     * First author surname(s) of the publication(s).
     */
    private List<Author> authors;

    /**
     *
     */
    private static int expectedColumnCount = 15;

    ///////////////////////
    // Constructors

    public AbstractBinaryInteraction( T interactorA, T interactorB ) {
        setInteractorA( interactorA );
        setInteractorB( interactorB );
    }

    public void flip() {
        T interactorA = getInteractorA();
        T interactorB = getInteractorB();

        setInteractorA(interactorB);
        setInteractorB(interactorA);
    }

    ///////////////////////////
    // Getters & Setters

    /**
     * Returns interactor A.
     *
     * @return interactor A.
     */
    public T getInteractorA() {
        return interactorA;
    }

    /**
     * Sets interactor A.
     *
     * @param interactorA interactor A.
     */
    public void setInteractorA( T interactorA ) {
        if ( interactorA == null ) {
            throw new IllegalArgumentException( "You must give a non null interactor A." );
        }
        this.interactorA = interactorA;
    }

    /**
     * Returns interactor B.
     *
     * @return interactor B.
     */
    public T getInteractorB() {
        return interactorB;
    }

    /**
     * Sets interactor B.
     *
     * @param interactorB interactor B.
     */
    public void setInteractorB( T interactorB ) {
        if ( interactorB == null ) {
            throw new IllegalArgumentException( "You must give a non null interactor B." );
        }
        this.interactorB = interactorB;
    }

    /**
     * Returns detection method for that interaction.
     *
     * @return detection method for that interaction.
     */
    public List<InteractionDetectionMethod> getDetectionMethods() {
        if ( detectionMethods == null ) {
            detectionMethods = new ArrayList<InteractionDetectionMethod>( 2 );
        }
        return detectionMethods;
    }

    /**
     * Sets detection method for that interaction.
     *
     * @param detectionMethods detection method for that interaction.
     */
    public void setDetectionMethods( List<InteractionDetectionMethod> detectionMethods ) {
        this.detectionMethods = detectionMethods;
    }

    /**
     * Returns types of the interaction
     *
     * @return types of the interaction
     */
    public List<InteractionType> getInteractionTypes() {
        if ( interactionTypes == null ) {
            interactionTypes = new ArrayList<InteractionType>( 2 );
        }
        return interactionTypes;
    }

    /**
     * Sets types of the interaction
     *
     * @param interactionTypes types of the interaction
     */
    public void setInteractionTypes( List<InteractionType> interactionTypes ) {
        this.interactionTypes = interactionTypes;
    }

    /**
     * Returns associated publications of that interaction.
     *
     * @return associated publications of that interaction.
     */
    public List<CrossReference> getPublications() {
        if ( publications == null ) {
            publications = new ArrayList<CrossReference>( 2 );
        }

        return publications;
    }

    /**
     * Sets associated publications of that interaction.
     *
     * @param publications associated publications of that interaction.
     */
    public void setPublications( List<CrossReference> publications ) {
        this.publications = publications;
    }

    /**
     * Returns associated confidence value for that interaction.
     *
     * @return associated confidence value for that interaction.
     */
    public List<Confidence> getConfidenceValues() {
        if ( confidenceValues == null ) {
            confidenceValues = new ArrayList<Confidence>( 2 );
        }
        return confidenceValues;
    }

    /**
     * Sets associated confidence value for that interaction.
     *
     * @param confidenceValues associated confidence value for that interaction.
     */
    public void setConfidenceValues( List<Confidence> confidenceValues ) {
        this.confidenceValues = confidenceValues;
    }

    /**
     * Getter for property 'authors'.
     *
     * @return Value for property 'authors'.
     */
    public List<Author> getAuthors() {
        if ( authors == null ) {
            authors = new ArrayList<Author>( 2 );
        }
        return authors;
    }

    /**
     * Setter for property 'authors'.
     *
     * @param authors Value to set for property 'authors'.
     */
    public void setAuthors( List<Author> authors ) {
        this.authors = authors;
    }

    /**
     * Getter for property 'sourceDatabases'.
     *
     * @return Value for property 'sourceDatabases'.
     */
    public List<CrossReference> getSourceDatabases() {
        if ( sourceDatabases == null ) {
            sourceDatabases = new ArrayList<CrossReference>( 2 );
        }
        return sourceDatabases;
    }

    /**
     * Setter for property 'sourceDatabases'.
     *
     * @param sourceDatabases Value to set for property 'sourceDatabases'.
     */
    public void setSourceDatabases( List<CrossReference> sourceDatabases ) {
        this.sourceDatabases = sourceDatabases;
    }

    /**
     * Getter for property 'interactionAcs'.
     *
     * @return Value for property 'interactionAcs'.
     */
    public List<CrossReference> getInteractionAcs() {
        if ( interactionAcs == null ) {
            interactionAcs = new ArrayList<CrossReference>( 2 );
        }
        return interactionAcs;
    }

    /**
     * Setter for property 'interactionAcs'.
     *
     * @param interactionAcs Value to set for property 'interactionAcs'.
     */
    public void setInteractionAcs( List<CrossReference> interactionAcs ) {
        this.interactionAcs = interactionAcs;
    }

    /**
     * Getter of the number of expected columns
     *
     * @return expected colmn count
     */
	public int getExpectedColumnCount() {
		return expectedColumnCount;
	}

	/**
	 * Setter of the number of expected columns
	 *
	 * @param expectedColumnCount of expected columns
	 */
	public void setExpectedColumnCount(int expectedColumnCount) {
		AbstractBinaryInteraction.expectedColumnCount = expectedColumnCount;
	}

    /////////////////////////
    // Object's override

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( getClass().getSimpleName() );
        sb.append( "{interactorA=" ).append( interactorA );
        sb.append( ", interactorB=" ).append( interactorB );
        sb.append( ", detectionMethods=" ).append( detectionMethods );
        sb.append( ", interactionTypes=" ).append( interactionTypes );
        sb.append( ", publications=" ).append( publications );
        sb.append( ", confidenceValues=" ).append( confidenceValues );
        sb.append( ", sourceDatabases=" ).append( sourceDatabases );
        sb.append( ", interactionAcs=" ).append( interactionAcs );
        sb.append( ", authors=" ).append( authors );
        sb.append( '}' );
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        AbstractBinaryInteraction that = (AbstractBinaryInteraction) o;

        if ( detectionMethods != null ? !detectionMethods.equals( that.detectionMethods ) : that.detectionMethods != null ) {
            return false;
        }
        if ( interactionTypes != null ? !interactionTypes.equals( that.interactionTypes ) : that.interactionTypes != null ) {
            return false;
        }

        // TODO update to (A == A' && B == B') || (B == A' && A == B')

        boolean part1 = ( interactorA.equals( that.interactorA ) && interactorB.equals( that.interactorB ) );
        boolean part2 = ( interactorB.equals( that.interactorA ) && interactorA.equals( that.interactorB ) );
        if ( !( part1 || part2 ) ) {
            return false;
        }

        if ( publications != null ? !publications.equals( that.publications ) : that.publications != null ) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;

        // Note: we want to reflect that (A == A' && B == B') || (B == A' && A == B')

        result = interactorA.hashCode() * interactorB.hashCode();
        result = 31 * result + ( detectionMethods != null ? detectionMethods.hashCode() : 0 );
        result = 31 * result + ( interactionTypes != null ? interactionTypes.hashCode() : 0 );
        result = 31 * result + ( publications != null ? publications.hashCode() : 0 );
        return result;
    }


}