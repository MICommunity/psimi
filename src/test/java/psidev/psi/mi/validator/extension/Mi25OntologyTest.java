/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.validator.extension;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;

import java.io.InputStream;

/**
 * MiOntology Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/05/2006</pre>
 */
public class Mi25OntologyTest {

    MiOntology ontology;

    @Before
    public void setup() throws Exception {

        final String ontoConfig = "config/ontologies.xml";
        InputStream is = OntologyManager.class.getClassLoader().getResourceAsStream( ontoConfig );
        Assert.assertNotNull( "Could not read ontology configuration file: " + ontoConfig, is );
        OntologyManager om = new OntologyManager();
        om.loadOntologies(is);
        is.close();
        Assert.assertNotNull( om );

        OntologyAccess miAccess = om.getOntologyAccess( "MI" );
        Assert.assertNotNull( miAccess );

        ontology = new MiOntology( miAccess );
    }

    @After
    public void cleanup() {
        ontology = null;
    }

    @Test
    public void isChildOf() throws Exception {

        final OntologyTermI proteinCleavage = ontology.search( "MI:0570" );
        Assert.assertNotNull( proteinCleavage );

        final OntologyTermI interactionType = ontology.search( "MI:0190" );
        Assert.assertNotNull( interactionType );

        Assert.assertTrue( ontology.isChildOf( interactionType, proteinCleavage ) );
        Assert.assertFalse( ontology.isChildOf( proteinCleavage, interactionType ) );
    }
}
