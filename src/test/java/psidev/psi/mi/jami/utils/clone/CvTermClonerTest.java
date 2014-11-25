package psidev.psi.mi.jami.utils.clone;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;

/**
 * Unit tester for CvTermCloner
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class CvTermClonerTest {
    
    @Test
    public void test_copy_cvTerm_properties(){

        CvTerm sourceCvTerm = new DefaultCvTerm("source term");
        sourceCvTerm.setFullName("source cv term");
        sourceCvTerm.setMIIdentifier("MI:xxxx");
        sourceCvTerm.setMODIdentifier("MOD:xxxx");
        sourceCvTerm.setPARIdentifier("PAR:xxxx");
        sourceCvTerm.getSynonyms().add(new DefaultAlias("source alias"));
        sourceCvTerm.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceCvTerm.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        
        CvTerm targetCvTerm = new DefaultCvTerm("target term");
        targetCvTerm.setMIIdentifier("MI:xxx1");
        
        CvTermCloner.copyAndOverrideCvTermProperties(sourceCvTerm, targetCvTerm);

        Assert.assertEquals("source term", targetCvTerm.getShortName());
        Assert.assertEquals("source cv term", targetCvTerm.getFullName());
        Assert.assertEquals("MI:xxxx", targetCvTerm.getMIIdentifier());
        Assert.assertEquals("MOD:xxxx", targetCvTerm.getMODIdentifier());
        Assert.assertEquals("PAR:xxxx", targetCvTerm.getPARIdentifier());
        Assert.assertEquals(3, targetCvTerm.getIdentifiers().size());
        Assert.assertEquals(1, targetCvTerm.getXrefs().size());
        Assert.assertEquals(1, targetCvTerm.getSynonyms().size());
        Assert.assertEquals(1, targetCvTerm.getAnnotations().size());
        Assert.assertTrue(sourceCvTerm.getXrefs().iterator().next() == targetCvTerm.getXrefs().iterator().next());
        Assert.assertTrue(sourceCvTerm.getSynonyms().iterator().next() == targetCvTerm.getSynonyms().iterator().next());
        Assert.assertTrue(sourceCvTerm.getAnnotations().iterator().next() == targetCvTerm.getAnnotations().iterator().next());
    }
}
