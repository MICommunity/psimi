package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for DefaultExperiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class DefaultExperimentTest {

    @Test
    public void test_create_new_experiment(){

        Experiment exp = new DefaultExperiment(new DefaultPublication());
        Assert.assertNotNull(exp.getPublication());
        Assert.assertNotNull(exp.getVariableParameters());
        Assert.assertNotNull(exp.getAnnotations());
        Assert.assertNotNull(exp.getConfidences());
        Assert.assertNotNull(exp.getInteractionEvidences());
        Assert.assertEquals(CvTermUtils.createUnspecifiedMethod(), exp.getInteractionDetectionMethod());
    }

    @Test
    public void test_create_new_experiment_detection_method(){

        Experiment exp = new DefaultExperiment(new DefaultPublication(), CvTermUtils.createMICvTerm("test", "MI:xxxx"));
        Assert.assertEquals(CvTermUtils.createMICvTerm("test", "MI:xxxx"), exp.getInteractionDetectionMethod());

        exp.setInteractionDetectionMethod(null);
        Assert.assertEquals(CvTermUtils.createUnspecifiedMethod(), exp.getInteractionDetectionMethod());
    }

    @Test
    public void test_create_new_experiment_host_organism(){

        Experiment exp = new DefaultExperiment(new DefaultPublication(), null, new DefaultOrganism(9606));
        Assert.assertEquals(CvTermUtils.createUnspecifiedMethod(), exp.getInteractionDetectionMethod());
        Assert.assertEquals(new DefaultOrganism(9606), exp.getHostOrganism());
    }

    @Test
    public void test_set_unset_publication(){
        Publication pub = new DefaultPublication("12345");
        Experiment exp = new DefaultExperiment(null);
        Assert.assertNull(exp.getPublication());

        exp.setPublicationAndAddExperiment(pub);
        Assert.assertEquals(pub, exp.getPublication());
        Assert.assertEquals(1, pub.getExperiments().size());

        exp.setPublicationAndAddExperiment(null);
        Assert.assertNull(exp.getPublication());
        Assert.assertEquals(0, pub.getExperiments().size());
    }

    @Test
    public void test_add_remove_variable_parameters(){
        Experiment exp = new DefaultExperiment(null);
        VariableParameter param = new DefaultVariableParameter("cell cycle");

        exp.addVariableParameter(param);
        Assert.assertEquals(1, exp.getVariableParameters().size());
        Assert.assertEquals(exp, param.getExperiment());

        exp.removeVariableParameter(param);
        Assert.assertEquals(0, exp.getVariableParameters().size());
        Assert.assertNull(param.getExperiment());
    }

    @Test
    public void test_add_remove_interaction_evidence(){
        Experiment exp = new DefaultExperiment(null);
        InteractionEvidence ev = new DefaultInteractionEvidence("test interaction");

        exp.addInteractionEvidence(ev);
        Assert.assertEquals(1, exp.getInteractionEvidences().size());
        Assert.assertEquals(exp, ev.getExperiment());

        exp.removeInteractionEvidence(ev);
        Assert.assertEquals(0, exp.getInteractionEvidences().size());
        Assert.assertNull(ev.getExperiment());
    }
}
