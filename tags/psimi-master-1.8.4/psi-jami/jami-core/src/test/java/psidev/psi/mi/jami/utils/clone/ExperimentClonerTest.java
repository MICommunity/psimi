package psidev.psi.mi.jami.utils.clone;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for ExperimentCloner
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class ExperimentClonerTest {

    @Test
    public void test_copy_experiment_properties(){

        Publication pub = new DefaultPublication("12345");
        Experiment sourceExperiment = new DefaultExperiment(pub);
        pub.addExperiment(sourceExperiment);
        sourceExperiment.setHostOrganism(new DefaultOrganism(-1, "in vitro"));
        sourceExperiment.setInteractionDetectionMethod(CvTermUtils.createUnspecifiedMethod());

        sourceExperiment.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceExperiment.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceExperiment.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceExperiment.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceExperiment.getVariableParameters().add(new DefaultVariableParameter("cell cycle", sourceExperiment));

        Publication pub2 = new DefaultPublication("12345");
        Experiment targetExperiment = new DefaultExperiment(pub2);
        targetExperiment.setHostOrganism(new DefaultOrganism(9606));

        ExperimentCloner.copyAndOverrideExperimentProperties(sourceExperiment, targetExperiment);

        Assert.assertFalse(pub2 == targetExperiment.getPublication());
        Assert.assertTrue(pub == targetExperiment.getPublication());
        Assert.assertTrue(pub.getExperiments().size() == 1);
        Assert.assertTrue(sourceExperiment.getHostOrganism() == targetExperiment.getHostOrganism());
        Assert.assertTrue(sourceExperiment.getInteractionDetectionMethod() == targetExperiment.getInteractionDetectionMethod());
        Assert.assertEquals(sourceExperiment.getAnnotations().size(), targetExperiment.getAnnotations().size());
        Assert.assertEquals(sourceExperiment.getXrefs().size(), targetExperiment.getXrefs().size());
        Assert.assertEquals(sourceExperiment.getVariableParameters().size(), targetExperiment.getVariableParameters().size());
        Assert.assertTrue(sourceExperiment.getAnnotations().iterator().next() == targetExperiment.getAnnotations().iterator().next());
        Assert.assertTrue(targetExperiment.getInteractionEvidences().isEmpty());
        Assert.assertTrue(sourceExperiment.getXrefs().iterator().next() == targetExperiment.getXrefs().iterator().next());
        Assert.assertTrue(sourceExperiment.getConfidences().iterator().next() == targetExperiment.getConfidences().iterator().next());
        Assert.assertFalse(sourceExperiment.getVariableParameters().iterator().next() == targetExperiment.getVariableParameters().iterator().next());
        Assert.assertEquals(sourceExperiment.getVariableParameters().iterator().next(), targetExperiment.getVariableParameters().iterator().next());
    }
}
