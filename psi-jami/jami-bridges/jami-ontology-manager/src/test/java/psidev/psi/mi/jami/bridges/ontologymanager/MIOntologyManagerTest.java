package psidev.psi.mi.jami.bridges.ontologymanager;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.local.MILocalOntology;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.ols.MIOlsOntology;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccessTemplate;

import java.io.IOException;
import java.io.InputStream;

/**
 * Unit tester of the MIOntologyManager
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/11/11</pre>
 */

public class MIOntologyManagerTest {

    @Test
    public void test_simulation() throws IOException, OntologyLoaderException {

        InputStream ontology = MIOntologyManagerTest.class.getResource("/ontologies.xml").openStream();
        MIOntologyManager om = new MIOntologyManager(ontology);
        ontology.close();

        Assert.assertEquals(3, om.getOntologyIDs().size());

        OntologyAccessTemplate<MIOntologyTermI> accessMI = om.getOntologyAccess("MI");
        Assert.assertNotNull(accessMI);
        Assert.assertTrue(accessMI instanceof MILocalOntology);
        Assert.assertNotNull(accessMI.getTermForAccession("MI:0018"));
        Assert.assertNull(accessMI.getTermForAccession("MOD:01161"));


        OntologyAccessTemplate<MIOntologyTermI> accessMOD = om.getOntologyAccess("MOD");
        Assert.assertNotNull(accessMOD);
        Assert.assertTrue(accessMOD instanceof MIOlsOntology);
        Assert.assertNotNull(accessMOD.getTermForAccession("MOD:01161"));
        Assert.assertNull(accessMOD.getTermForAccession("MI:0018"));

        OntologyAccessTemplate<MIOntologyTermI> accessECO = om.getOntologyAccess("ECO");
        Assert.assertNotNull(accessECO);
        Assert.assertTrue(accessECO instanceof MIOlsOntology);
        Assert.assertNotNull(accessECO.getTermForAccession("ECO:0000003"));
        Assert.assertNull(accessECO.getTermForAccession("MI:0018"));
    }
}
