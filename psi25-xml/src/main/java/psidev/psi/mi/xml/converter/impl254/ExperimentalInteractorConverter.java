package psidev.psi.mi.xml.converter.impl254;

import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.ExperimentRef;
import psidev.psi.mi.xml.model.Interactor;
import psidev.psi.mi.xml254.jaxb.ExperimentRefList;

/**
 * Experimental Interactor converter.
 *
 * @author Michael Mueller
 * @version $Id$
 * @since 1.0
 */
public class ExperimentalInteractorConverter {

    ///////////////////////////
    // Instance variable

    private InteractorConverter interactorConverter;
    private ExperimentDescriptionConverter experimentDescriptionConverter;

    /**
     * Handles DAOs.
     */
    private DAOFactory factory;

    ///////////////////////
    // Constructor

    public ExperimentalInteractorConverter() {
        interactorConverter = new InteractorConverter();
        experimentDescriptionConverter = new ExperimentDescriptionConverter();
    }

    ///////////////////////////////
    // DAO factory stategy

    /**
     * Set the DAO Factory that holds required DAOs for resolving ids.
     *
     * @param factory the DAO factory
     */
    public void setDAOFactory( DAOFactory factory ) {
        this.factory = factory;
    }

    /**
     * Checks that the dependencies of that object are fulfilled.
     *
     * @throws ConverterException
     */
    private void checkDependencies() throws ConverterException {
        if ( factory == null ) {
            throw new ConverterException( "Please set a DAO factory in order to resolve interactor and experiment id." );
        }
    }

    /////////////////////////
    // Converter methods

    public psidev.psi.mi.xml.model.ExperimentalInteractor fromJaxb( psidev.psi.mi.xml254.jaxb.ExperimentalInteractor jExperimentalInteractor ) throws ConverterException {

        if ( jExperimentalInteractor == null ) {
            throw new IllegalArgumentException( "You must give a non null JAXB ExperimentalInteractor." );
        }

        checkDependencies();

        psidev.psi.mi.xml.model.ExperimentalInteractor mExperimentalInteractor = new psidev.psi.mi.xml.model.ExperimentalInteractor();

        // Initialise the model reading the Jaxb object

        // 1. set encapsulated objects

        // experiments

        if (jExperimentalInteractor.getExperimentRefList() != null){
            PsiDAO<ExperimentDescription> experimentDAO = factory.getExperimentDAO();
            
            for ( Integer jExperimentId : jExperimentalInteractor.getExperimentRefList().getExperimentReves() ) {
                ExperimentDescription experiment = experimentDAO.retreive( jExperimentId );
                if ( experiment == null ) {
                    mExperimentalInteractor.getExperimentRefs().add( new ExperimentRef( jExperimentId ) );
                } else {
                    mExperimentalInteractor.getExperiments().add( experiment );
                }
            }
        }

        // interactor
        Interactor interactor = null;
        if ( jExperimentalInteractor.getInteractorRef() != null ) {

            PsiDAO<Interactor> interactorDAO = factory.getInteractorDAO();
            interactor = interactorDAO.retreive( jExperimentalInteractor.getInteractorRef() );

        } else if ( jExperimentalInteractor.getInteractor() != null ) {

            // TODO should this be store via DAO ? maybe we should check via DAO if we have it already, if so, reuse it.
            //      on the other hand, if we reuse something coming from DAO, we may lose the id of that interactor.
            interactor = interactorConverter.fromJaxb( jExperimentalInteractor.getInteractor() );

        } else {
            throw new ConverterException( "neither an interactor ref or an interactor were provided." );
        }

        mExperimentalInteractor.setInteractor( interactor );

        return mExperimentalInteractor;
    }

    public psidev.psi.mi.xml254.jaxb.ExperimentalInteractor toJaxb( psidev.psi.mi.xml.model.ExperimentalInteractor mExperimentalInteractor ) throws ConverterException {

        if ( mExperimentalInteractor == null ) {
            throw new IllegalArgumentException( "You must give a non null model ExperimentalInteractor." );
        }

        checkDependencies();

        psidev.psi.mi.xml254.jaxb.ExperimentalInteractor jExperimentalInteractor = new psidev.psi.mi.xml254.jaxb.ExperimentalInteractor();

        // Initialise the JAXB object reading the model

        // 1. set encapsulated objects

        // experiments
        if ( mExperimentalInteractor.hasExperiments() ) {
            if ( jExperimentalInteractor.getExperimentRefList() == null ) {
                jExperimentalInteractor.setExperimentRefList( new ExperimentRefList() );
            }
            for ( ExperimentDescription experimentDescription : mExperimentalInteractor.getExperiments() ) {
                jExperimentalInteractor.getExperimentRefList().getExperimentReves().add( experimentDescription.getId() );
            }
        } else if ( mExperimentalInteractor.hasExperimentRefs() ) {
            if ( jExperimentalInteractor.getExperimentRefList() == null ) {
                jExperimentalInteractor.setExperimentRefList( new ExperimentRefList() );
            }
            for ( ExperimentRef experimentDescription : mExperimentalInteractor.getExperimentRefs() ) {
                jExperimentalInteractor.getExperimentRefList().getExperimentReves().add( experimentDescription.getRef() );
            }
        }

        // interactor
        // TODO do we export compact or expanded form ?! We need a configuration object.
        // for now we do it compact.

        jExperimentalInteractor.setInteractorRef( mExperimentalInteractor.getInteractor().getId() );

        return jExperimentalInteractor;
    }
}