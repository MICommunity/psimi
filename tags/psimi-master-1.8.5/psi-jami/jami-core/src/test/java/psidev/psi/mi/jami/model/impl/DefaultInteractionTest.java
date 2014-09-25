package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.ChecksumUtils;

/**
 * Unit tester for DefaultInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultInteractionTest {

    @Test
    public void create_empty_interaction(){

        Interaction interaction = new DefaultInteraction();

        Assert.assertNull(interaction.getShortName());
        Assert.assertNull(interaction.getInteractionType());
        Assert.assertNull(interaction.getCreatedDate());
        Assert.assertNull(interaction.getUpdatedDate());
        Assert.assertNull(interaction.getRigid());
        Assert.assertNotNull(interaction.getAnnotations());
        Assert.assertNotNull(interaction.getXrefs());
        Assert.assertNotNull(interaction.getChecksums());
    }

    @Test
    public void create_interaction_shortname(){

        Interaction interaction = new DefaultInteraction("test interaction");

        Assert.assertEquals("test interaction", interaction.getShortName());
        Assert.assertNull(interaction.getInteractionType());
    }

    @Test
    public void create_interaction_shortname_type(){

        Interaction interaction = new DefaultInteraction("test interaction", new DefaultCvTerm("association"));

        Assert.assertEquals("test interaction", interaction.getShortName());
        Assert.assertEquals(new DefaultCvTerm("association"), interaction.getInteractionType());
    }

    @Test
    public void test_add_remove_rigid(){
        Interaction interaction = new DefaultInteraction("test interaction");
        Assert.assertNull(interaction.getRigid());

        interaction.getChecksums().add(ChecksumUtils.createRigid("11aa1"));
        Assert.assertEquals("11aa1", interaction.getRigid());

        interaction.getChecksums().remove(ChecksumUtils.createRigid("11aa1"));
        Assert.assertNull(interaction.getRigid());

        interaction.getChecksums().clear();
        Assert.assertNull(interaction.getRigid());

        interaction.setRigid("11aa1");
        Assert.assertEquals("11aa1", interaction.getRigid());
        Assert.assertEquals(1, interaction.getChecksums().size());
        Assert.assertEquals(ChecksumUtils.createRigid("11aa1"), interaction.getChecksums().iterator().next());

        interaction.getChecksums().add(ChecksumUtils.createRigid("11aa2"));
        Assert.assertEquals("11aa1", interaction.getRigid());
        Assert.assertEquals(2, interaction.getChecksums().size());

        interaction.getChecksums().clear();
        Assert.assertNull(interaction.getRigid());
        Assert.assertEquals(0, interaction.getChecksums().size());
    }
}
