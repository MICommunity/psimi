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

        Assert.assertNotNull(feature.getLinkedModelledFeatures());
        Assert.assertNull(feature.getModelledParticipant());
        Assert.assertEquals(CvTermUtils.createBiologicalFeatureType(), feature.getType());
    }

    @Test
    public void test_set_unset_modelledParticipant(){

        ModelledFeature feature = new DefaultModelledFeature();
        ModelledParticipant p = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        feature.setModelledParticipant(p);

        Assert.assertTrue(DefaultModelledParticipantComparator.areEquals(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()), feature.getModelledParticipant()));
        Assert.assertEquals(0, p.getModelledFeatures().size());

        feature.setModelledParticipantAndAddFeature(p);
        Assert.assertNotNull(feature.getModelledParticipant());
        Assert.assertEquals(1, p.getModelledFeatures().size());
        Assert.assertEquals(feature, p.getModelledFeatures().iterator().next());

        feature.setModelledParticipantAndAddFeature(null);
        Assert.assertNull(feature.getModelledParticipant());
        Assert.assertEquals(0, p.getModelledFeatures().size());
    }
}
