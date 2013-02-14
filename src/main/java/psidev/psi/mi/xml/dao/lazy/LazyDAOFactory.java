/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.dao.lazy;

import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.DAOType;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.*;

/**
 * Build all in memory DAO types.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24-Jun-2006</pre>
 */
public class LazyDAOFactory extends DAOFactory {

    PsiDAO<ExperimentDescription> experimentDao = new LazyDAO<ExperimentDescription>();
    PsiDAO<Interaction> interactionDao = new LazyDAO<Interaction>();
    PsiDAO<Participant> participantDao = new LazyDAO<Participant>();
    PsiDAO<Interactor> interactorDao = new LazyDAO<Interactor>();
    PsiDAO<Feature> featureDao = new LazyDAO<Feature>();

    /////////////////////////////
    // Constructor

    public LazyDAOFactory() {
        super( DAOType.LAZY );
    }

    /////////////////////////////
    // Implements of DAOFactory

    public PsiDAO<ExperimentDescription> getExperimentDAO() {
        return experimentDao;
    }

    public PsiDAO<Interaction> getInteractionDAO() {
        return interactionDao;
    }

    public PsiDAO<Participant> getParticipantDAO() {
        return participantDao;
    }

    public PsiDAO<Interactor> getInteractorDAO() {
        return interactorDao;
    }

    public PsiDAO<Feature> getFeatureDAO() {
        return featureDao;
    }

    public void reset() {
        experimentDao.reset();
        interactionDao.reset();
        participantDao.reset();
        interactorDao.reset();
        featureDao.reset();
    }
}