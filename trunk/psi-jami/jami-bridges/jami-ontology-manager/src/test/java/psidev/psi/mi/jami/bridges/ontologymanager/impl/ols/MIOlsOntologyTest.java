package psidev.psi.mi.jami.bridges.ontologymanager.impl.ols;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.ontologymanager.MIOntologyTermI;
import psidev.psi.mi.jami.bridges.ontologymanager.impl.OntologyTermWrapper;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;

/**
 * Unit tester for MIOlsOntology
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/11/11</pre>
 */

public class MIOlsOntologyTest {

    @Test
    public void test_synonyms() throws OntologyLoaderException {

        MIOlsOntology olsOntology = new MIOlsOntology("psi-mi", "MI:0488", OntologyTermWrapper.MI_REGEXP, null);

        MIOntologyTermI term = olsOntology.getTermForAccession("MI:0018");

        Assert.assertFalse(term.getNameSynonyms().isEmpty());
    }
}
