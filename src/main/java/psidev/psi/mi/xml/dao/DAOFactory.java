/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.dao;

import psidev.psi.mi.xml.model.*;

/**
 * Gives access to all PsiDAO.
 * <p/>
 * Modeled after the Abstract Factory Pattern.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24-Jun-2006</pre>
 */
public abstract class DAOFactory {

    /////////////////////////////
    // Instance variable

    private DAOType type;

    ////////////////////////////
    // Constructor

    private DAOFactory() {
    }

    protected DAOFactory( DAOType type ) {
        setType( type );
    }

    ///////////////////////////
    // Getters and Setters

    public DAOType getType() {
        return type;
    }

    private void setType( DAOType type ) {
        if ( type == null ) {
            throw new IllegalArgumentException( "You must give a non null type." );
        }
        this.type = type;
    }

    ////////////////////////////
    // Abstracts

    /**
     * Returns an experiment DAO.
     *
     * @return a non null experiment DAO.
     */
    public abstract PsiDAO<ExperimentDescription> getExperimentDAO();

    /**
     * Returns an Interaction DAO.
     *
     * @return a non null Interaction DAO.
     */
    public abstract PsiDAO<Interaction> getInteractionDAO();

    /**
     * Returns an Participant DAO.
     *
     * @return a non null Participant DAO.
     */
    public abstract PsiDAO<Participant> getParticipantDAO();

    /**
     * Returns an Interactor DAO.
     *
     * @return a non null Interactor DAO.
     */
    public abstract PsiDAO<Interactor> getInteractorDAO();

    /**
     * Returns an Feature DAO.
     *
     * @return a non null Feature DAO.
     */
    public abstract PsiDAO<Feature> getFeatureDAO();

    /**
     * Some implementations may require to flush cached objects and this can be implemented
     * in this method.
     */
    public abstract void reset();
}