/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Simple description of an Interactor.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class Interactor implements Serializable {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = -4549381843138930910L;

    ///////////////////////
    // Instance variables

    /**
     * Primary identifiers of the interactor.
     */
    private Collection<CrossReference> identifiers;

    /**
     * alternative identifiers of the interactor.
     */
    private Collection<CrossReference> alternativeIdentifiers;

    /**
     * Aliases of the interactor (ie. alternative names).
     */
    private Collection<Alias> aliases;

    /**
     * Organism the interactor belongs to.
     */
    private Organism organism;

    ///////////////////////////
    // Constructor

    public Interactor() {
    }

    public Interactor( Collection<CrossReference> identifiers ) {
        if ( identifiers == null ) {
            throw new IllegalArgumentException( "You must give a non null collection of identifiers." );
        }
        this.identifiers = identifiers;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Getter for property 'identifiers'.
     *
     * @return Value for property 'identifiers'.
     */
    public Collection<CrossReference> getIdentifiers() {
        return identifiers;
    }

    /**
     * Setter for property 'identifiers'.
     *
     * @param identifiers Value to set for property 'identifiers'.
     */
    public void setIdentifiers( Collection<CrossReference> identifiers ) {
        this.identifiers = identifiers;
    }

    /**
     * Getter for property 'alternativeIdentifiers'.
     *
     * @return Value for property 'alternativeIdentifiers'.
     */
    public Collection<CrossReference> getAlternativeIdentifiers() {
        if (alternativeIdentifiers == null) {
            alternativeIdentifiers = new ArrayList<CrossReference>(2);
        }
        return alternativeIdentifiers;
    }

    /**
     * Setter for property 'alternativeIdentifiers'.
     *
     * @param alternativeIdentifiers Value to set for property 'alternativeIdentifiers'.
     */
    public void setAlternativeIdentifiers( Collection<CrossReference> alternativeIdentifiers ) {
        this.alternativeIdentifiers = alternativeIdentifiers;
    }

    /**
     * Getter for property 'aliases'.
     *
     * @return Value for property 'aliases'.
     */
    public Collection<Alias> getAliases() {
        if (aliases == null) {
            aliases = new ArrayList<Alias>(2);
        }
        return aliases;
    }

    /**
     * Setter for property 'aliases'.
     *
     * @param aliases Value to set for property 'aliases'.
     */
    public void setAliases( Collection<Alias> aliases ) {
        this.aliases = aliases;
    }

    /**
     * Getter for property 'organism'.
     *
     * @return Value for property 'organisms'.
     */
    public Organism getOrganism() {
        return organism;
    }

    /**
     * Setter for property 'organism'.
     *
     * @param organism Value to set for property 'organisms'.
     */
    public void setOrganism( Organism organism ) {
        this.organism = organism;
    }

    public boolean hasOrganism() {
        return organism != null;
    }

    /////////////////////////////
    // Object's override

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Interactor{" +
               "identifiers=" + identifiers +
               ", alternativeIdentifiers=" + alternativeIdentifiers +
               ", aliases=" + aliases +
               ", organism=" + organism +
               '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Interactor that = ( Interactor ) o;

        if ( identifiers != null ? !identifiers.equals( that.identifiers ) : that.identifiers != null ) return false;
        if ( organism != null ? !organism.equals( that.organism ) : that.organism != null ) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;
        result = ( identifiers != null ? identifiers.hashCode() : 0 );
        result = 31 * result + ( organism != null ? organism.hashCode() : 0 );
        return result;
    }
}