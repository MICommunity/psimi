package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantComparator;

/**
 * Unit tester for DefaultModelledFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class DefaultModelledFeatureTest {

    @Test
    public void test_create_empty_modelledFeature(){

        ModelledFeature feature = new DefaultModelledFeature();

        Assert.assertNotNull(feature.getLinkedFeatures());
        Assert.assertNull(feature.getParticipant());
        Assert.assertEquals(CvTermUtils.createBiologicalFeatureType(), feature.getType());
    }

    @Test
    public void test_set_unset_modelledParticipant(){

        ModelledFeature feature = new DefaultModelledFeature();
        ModelledParticipant p = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        feature.setParticipant(p);

        Assert.assertTrue(DefaultModelledParticipantComparator.areEquals(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()), feature.getParticipant(), true));
        Assert.assertEquals(0, p.getFeatures().size());

        feature.setParticipantAndAddFeature(p);
        Assert.assertNotNull(feature.getParticipant());
        Assert.assertEquals(1, p.getFeatures().size());
        Assert.assertEquals(feature, p.getFeatures().iterator().next());

        feature.setParticipantAndAddFeature(null);
        Assert.assertNull(feature.getParticipant());
        Assert.assertEquals(0, p.getFeatures().size());
    }
}
