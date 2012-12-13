/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.filter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

import psidev.psi.mi.tab.PsimiTabReaderTest;
import psidev.psi.mi.tab.TestHelper;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.tab.utils.PsimiTabFileMerger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;


/**
 * ConjonctiveFilterSet Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0-beta4
 */
public abstract class AbstractFilterTest {

    private Collection<BinaryInteraction> interactions;

    @Before
    public void setUp() throws Exception {

        Collection<File> files = new ArrayList<File>();

        files.add( TestHelper.getFileByResources( "/mitab-samples/12167173.txt", PsimiTabReaderTest.class ));
        files.add( TestHelper.getFileByResources( "/mitab-samples/14726512.txt", PsimiTabReaderTest.class ));

        try {
            interactions = PsimiTabFileMerger.merge( files );
            assertNotNull( interactions );
        } catch ( Exception e ) {
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void tearDown() throws Exception {
        interactions = null;
    }

    public Collection<BinaryInteraction> getInteractions() {
        return interactions;
    }

    ////////////////////
    // Tests

    protected BinaryInteractionFilter buildFilterOrganism( int taxid ) {

        final String myTaxid = String.valueOf( taxid );

        return new BinaryInteractionFilter() {
            public boolean evaluate( BinaryInteraction<?> interaction ) {
                Organism oa = interaction.getInteractorA().getOrganism();
                Organism ob = interaction.getInteractorB().getOrganism();

                return myTaxid.equals( oa.getTaxid() ) || myTaxid.equals( ob.getTaxid() );
            }
        };
    }

    protected BinaryInteractionFilter buildFilterInteractionType( final String miRef ) {

        return new BinaryInteractionFilter() {
            public boolean evaluate( BinaryInteraction<?> interaction ) {
                for ( CrossReference type : interaction.getInteractionTypes() ) {
                    String mi = type.getDatabase() + ":" + type.getIdentifier();
                    if ( mi.equals( miRef ) || type.getIdentifier().equals( miRef ) ) {
                        return true;
                    }
                }

                return false;
            }
        };
    }

    protected BinaryInteractionFilter buildFilterInteractionDetection( final String miRef ) {

        return new BinaryInteractionFilter() {
            public boolean evaluate( BinaryInteraction<?> interaction ) {
                for ( CrossReference method : interaction.getDetectionMethods() ) {
                    String mi = method.getDatabase() + ":" + method.getIdentifier();
                    if ( mi.equals( miRef ) || method.getIdentifier().equals( miRef ) ) {
                        return true;
                    }
                }

                return false;
            }
        };
    }
}