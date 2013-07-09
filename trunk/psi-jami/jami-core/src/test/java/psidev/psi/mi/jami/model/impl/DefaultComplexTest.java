package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for DefaultComplex
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultComplexTest {

    @Test
    public void create_empty_complex(){

        Complex interaction = new DefaultComplex("test complex");

        Assert.assertEquals("test complex", interaction.getShortName());
        Assert.assertNull(interaction.getSource());
        Assert.assertNotNull(interaction.getParticipants());
        Assert.assertNotNull(interaction.getCooperativeEffects());
        Assert.assertNotNull(interaction.getInteractionEvidences());
        Assert.assertNotNull(interaction.getModelledConfidences());
        Assert.assertNotNull(interaction.getModelledParameters());
        Assert.assertEquals(CvTermUtils.createComplexInteractorType(), interaction.getInteractorType());

        interaction = new DefaultComplex("test", (String) null);
        Assert.assertEquals(CvTermUtils.createComplexInteractorType(), interaction.getInteractorType());
    }

    @Test
    public void create_complex_interactor_type_null(){

        Complex interactor = new DefaultComplex("test", CvTermUtils.createUnknownInteractorType());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(null);
        Assert.assertEquals(CvTermUtils.createComplexInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(CvTermUtils.createUnknownInteractorType());
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());
    }

    @Test
    public void test_add_remove_modelledParticipants(){
        Complex interaction = new DefaultComplex("test interaction");
        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertNull(participant.getInteraction());

        // add participant and set modelledInteraction
        interaction.addParticipant(participant);
        Assert.assertEquals(interaction, participant.getInteraction());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // remove modelled participant and set interaction to null
        interaction.removeParticipant(participant);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(0, interaction.getParticipants().size());

        // simply add modelled participant
        interaction.getParticipants().add(participant);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // simply remove modelled participant
        interaction.getParticipants().remove(participant);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(0, interaction.getParticipants().size());
    }

    @Test
    public void test_add_remove_rigid(){
        Complex interaction = new DefaultComplex("test interaction");
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

    @Test
    public void test_add_remove_physical_properties(){
        Complex interaction = new DefaultComplex("test interaction");
        Assert.assertNull(interaction.getPhysicalProperties());

        interaction.getAnnotations().add(AnnotationUtils.createAnnotation(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_MI, "molecular weight x"));
        Assert.assertEquals("molecular weight x", interaction.getPhysicalProperties());

        interaction.getAnnotations().remove(AnnotationUtils.createAnnotation(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_MI, "molecular weight x"));
        Assert.assertNull(interaction.getPhysicalProperties());

        interaction.getAnnotations().clear();
        Assert.assertNull(interaction.getPhysicalProperties());

        interaction.setPhysicalProperties("molecular weight x");
        Assert.assertEquals("molecular weight x", interaction.getPhysicalProperties());
        Assert.assertEquals(1, interaction.getAnnotations().size());
        Assert.assertEquals(AnnotationUtils.createAnnotation(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_MI, "molecular weight x"), interaction.getAnnotations().iterator().next());

        interaction.getAnnotations().add(AnnotationUtils.createAnnotation(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_MI, "molecular weight x2"));
        Assert.assertEquals("molecular weight x", interaction.getPhysicalProperties());
        Assert.assertEquals(2, interaction.getAnnotations().size());

        interaction.getAnnotations().clear();
        Assert.assertNull(interaction.getPhysicalProperties());
        Assert.assertEquals(0, interaction.getAnnotations().size());
    }
}
