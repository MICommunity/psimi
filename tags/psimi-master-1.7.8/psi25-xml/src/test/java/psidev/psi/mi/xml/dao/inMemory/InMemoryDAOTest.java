/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.dao.inMemory;

import static junit.framework.Assert.*;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.dao.DAOFactory;
import psidev.psi.mi.xml.dao.DAOType;
import psidev.psi.mi.xml.dao.PsiDAO;
import psidev.psi.mi.xml.model.Bibref;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.Xref;

import java.util.Collection;

/**
 * InMemoryExperimentDAO Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/24/2006</pre>
 */
public class InMemoryDAOTest {

    @Test
    public void inMemoryDAO() {

        DAOFactory factory = new InMemoryDAOFactory();
        assertEquals( DAOType.IN_MEMORY, factory.getType() );
        buildFactory( factory );

        getAll( new InMemoryDAOFactory().getExperimentDAO() );
        retreive( new InMemoryDAOFactory().getExperimentDAO() );
        remove( new InMemoryDAOFactory().getExperimentDAO() );
        store( new InMemoryDAOFactory().getExperimentDAO() );
    }

    ///////////////////////////////////////////////////////////
    // Helper methods - each of which checks on a contract

    /**
     * Checks that none of the DAO returned is null and also that it consistently returns the same DAO.
     */
    private void buildFactory( DAOFactory factory ) {

        assertNotNull( factory.getExperimentDAO() );
        assertTrue( factory.getExperimentDAO() == factory.getExperimentDAO() );

        assertNotNull( factory.getFeatureDAO() );
        assertTrue( factory.getFeatureDAO() == factory.getFeatureDAO() );

        assertNotNull( factory.getInteractionDAO() );
        assertTrue( factory.getInteractionDAO() == factory.getInteractionDAO() );

        assertNotNull( factory.getInteractorDAO() );
        assertTrue( factory.getInteractorDAO() == factory.getInteractorDAO() );

        assertNotNull( factory.getParticipantDAO() );
        assertTrue( factory.getParticipantDAO() == factory.getParticipantDAO() );
    }

    public void getAll( PsiDAO<ExperimentDescription> dao ) {

        ExperimentDescription e1 = new ExperimentDescription();
        e1.setId( 1 );
        dao.store( e1 );

        ExperimentDescription e2 = new ExperimentDescription();
        e2.setId( 2 );
        dao.store( e2 );

        ExperimentDescription e3 = new ExperimentDescription();
        e3.setId( 3 );
        dao.store( e3 );

        Collection<ExperimentDescription> experiments = dao.getAll();
        assertNotNull( experiments );
        assertEquals( 3, experiments.size() );
        assertTrue( experiments.contains( e1 ) );
        assertTrue( experiments.contains( e2 ) );
        assertTrue( experiments.contains( e3 ) );
    }

    public void retreive( PsiDAO<ExperimentDescription> dao ) {

        ExperimentDescription e1 = new ExperimentDescription();
        e1.setId( 1 );
        dao.store( e1 );

        ExperimentDescription e2 = new ExperimentDescription();
        e2.setId( 2 );
        dao.store( e2 );

        ExperimentDescription e3 = new ExperimentDescription();
        e3.setId( 3 );
        dao.store( e3 );

        ExperimentDescription e = dao.retreive( 2 );

        assertEquals( e2, e );
    }

    public void store( PsiDAO<ExperimentDescription> dao ) {

        assertTrue( dao.getAll().isEmpty() );

        try {
            dao.store( null );
            fail();
        } catch ( Exception e ) {
            // ok
            assertTrue( dao.getAll().isEmpty() );
        }

        ExperimentDescription e1 = new ExperimentDescription();
        e1.setId( 1 );
        dao.store( e1 );
        assertEquals( 1, dao.getAll().size() );

        ExperimentDescription e2 = new ExperimentDescription();
        e2.setId( 2 );
        dao.store( e2 );
        assertEquals( 2, dao.getAll().size() );

        // store the same experiment again
        dao.store( e2 );
        assertEquals( 2, dao.getAll().size() );

        ExperimentDescription e3 = new ExperimentDescription();
        e3.setId( 3 );
        dao.store( e3 );
        assertEquals( 3, dao.getAll().size() );

        // store an experiment havving different information though the same id than an other one
        ExperimentDescription e4 = new ExperimentDescription();
        e4.setId( 2 );
        Bibref bibref = new Bibref();
        Xref xref = new Xref();
        DbReference dbReference = new DbReference();
        dbReference.setDb( "pubmed" );
        dbReference.setId( "12345678" );
        xref.setPrimaryRef( dbReference );
        bibref.setXref( xref );
        e4.setBibref( bibref );

        try {
            dao.store( e4 ); // e4 and e2 are equals !!
            fail();
        } catch ( Exception e ) {
            // ok
            assertEquals( 3, dao.getAll().size() );
        }
    }

    public void remove( PsiDAO<ExperimentDescription> dao ) {

        ExperimentDescription e1 = new ExperimentDescription();
        e1.setId( 1 );
        dao.store( e1 );
        assertEquals( 1, dao.getAll().size() );

        ExperimentDescription e2 = new ExperimentDescription();
        e2.setId( 2 );
        dao.store( e2 );
        assertEquals( 2, dao.getAll().size() );

        // store the same experiment again
        dao.store( e2 );
        assertEquals( 2, dao.getAll().size() );

        ExperimentDescription e3 = new ExperimentDescription();
        e3.setId( 3 );
        dao.store( e3 );
        assertEquals( 3, dao.getAll().size() );

        // remove experiment 2
        ExperimentDescription _e2 = dao.remove( 2 );
        assertEquals( 2, dao.getAll().size() );
        assertEquals( e2, _e2 );
        assertTrue( dao.getAll().contains( e1 ) );
        assertTrue( dao.getAll().contains( e3 ) );

        // remove an experiment that doesn't exist in the DAO.
        ExperimentDescription _e = dao.remove( 99 );
        assertNull( _e );
        assertEquals( 2, dao.getAll().size() );

        // remove experiment 3
        ExperimentDescription _e3 = dao.remove( 3 );
        assertEquals( 1, dao.getAll().size() );
        assertEquals( e3, _e3 );
        assertTrue( dao.getAll().contains( e1 ) );

        // remove experiment 1
        ExperimentDescription _e1 = dao.remove( 1 );
        assertEquals( 0, dao.getAll().size() );
        assertEquals( e1, _e1 );
    }
}
