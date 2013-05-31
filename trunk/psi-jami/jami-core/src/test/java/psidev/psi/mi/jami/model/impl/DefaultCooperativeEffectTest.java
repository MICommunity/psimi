package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CooperativeEffect;

/**
 * Unit tester for DefaultCooperativeEffect
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultCooperativeEffectTest {

    @Test
    public void create_empty_cooperative_effect(){

        CooperativeEffect effect = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"));

        Assert.assertEquals(new DefaultCvTerm("test outcome"), effect.getOutCome());
        Assert.assertNull(effect.getResponse());
        Assert.assertNotNull(effect.getAffectedInteractions());
        Assert.assertNotNull(effect.getCooperativityEvidences());
        Assert.assertNotNull(effect.getAnnotations());
    }

    @Test
    public void create_cooperative_effect_and_response(){

        CooperativeEffect effect = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"), new DefaultCvTerm("test response"));

        Assert.assertEquals(new DefaultCvTerm("test outcome"), effect.getOutCome());
        Assert.assertEquals(new DefaultCvTerm("test response"), effect.getResponse());
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_cooperative_effect_no_outcome(){

        CooperativeEffect effect = new DefaultCooperativeEffect(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_set_outcome_null(){

        CooperativeEffect effect = new DefaultCooperativeEffect(new DefaultCvTerm("test outcome"));

        effect.setOutCome(null);
    }
}
