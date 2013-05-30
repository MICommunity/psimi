package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for DefaultFeatureEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class DefaultFeatureEvidenceTest {

    @Test
    public void test_create_empty_featureEvidence(){

        FeatureEvidence feature = new DefaultFeatureEvidence();

        Assert.assertNotNull(feature.getLinkedFeatureEvidences());
        Assert.assertNull(feature.getParticipantEvidence());
        Assert.assertNull(feature.getType());
        Assert.assertNotNull(feature.getLinkedFeatureEvidences());
    }

    @Test
    public void test_set_unset_participantEvidence(){

        FeatureEvidence feature = new DefaultFeatureEvidence();
        ParticipantEvidence p = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        feature.setParticipantEvidence(p);

        Assert.assertEquals(new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor()), feature.getParticipantEvidence());
        Assert.assertEquals(0, p.getFeatureEvidences().size());

        feature.setParticipantEvidenceAndAddFeature(p);
        Assert.assertNotNull(feature.getParticipantEvidence());
        Assert.assertEquals(1, p.getFeatureEvidences().size());
        Assert.assertEquals(feature, p.getFeatureEvidences().iterator().next());

        feature.setParticipantEvidenceAndAddFeature(null);
        Assert.assertNull(feature.getParticipantEvidence());
        Assert.assertEquals(0, p.getFeatureEvidences().size());
    }
}
