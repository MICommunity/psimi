package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.utils.comparator.publication.DefaultPublicationComparator;

/**
 * Unit tester for DefaultCooperativityEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultCooperativityEvidenceTest {

    @Test
    public void test_create_cooperativity_evidence(){

        CooperativityEvidence evidence = new DefaultCooperativityEvidence(new DefaultPublication("12345"));

        Assert.assertTrue(DefaultPublicationComparator.areEquals(new DefaultPublication("12345"), evidence.getPublication()));
        Assert.assertNotNull(evidence.getEvidenceMethods());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_cooperativity_evidence_no_publication() throws Exception {
        CooperativityEvidence evidence = new DefaultCooperativityEvidence(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_set_null_publication() throws Exception {
        CooperativityEvidence evidence = new DefaultCooperativityEvidence(new DefaultPublication("12345"));

        evidence.setPublication(null);
    }
}
