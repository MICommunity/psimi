package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.ExperimentUtils;
import psidev.psi.mi.jami.utils.comparator.experiment.DefaultExperimentComparator;

/**
 * Unit tester for DefaultVariableParameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class DefaultVariableParameterTest {

    @Test
    public void test_create_variable_parameter() throws Exception {
        VariableParameter param = new DefaultVariableParameter("cell cycle");

        Assert.assertEquals("cell cycle", param.getDescription());
        Assert.assertNull(param.getUnit());
        Assert.assertNull(param.getExperiment());
        Assert.assertNotNull(param.getVariableValues());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_variable_parameter_no_description() throws Exception {
        VariableParameter param = new DefaultVariableParameter(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_variable_parameter_set_description_null() throws Exception {
        VariableParameter param = new DefaultVariableParameter("cell cycle");
        param.setDescription(null);
    }

    @Test
    public void test_create_variable_parameter_with_unit() throws Exception {
        VariableParameter param = new DefaultVariableParameter("PMA treatment", CvTermUtils.createMICvTerm("molar", "MI:xxxx"));

        Assert.assertEquals("PMA treatment", param.getDescription());
        Assert.assertEquals(CvTermUtils.createMICvTerm("molar", "MI:xxxx"), param.getUnit());
        Assert.assertNull(param.getExperiment());
        Assert.assertNotNull(param.getVariableValues());
    }

    @Test
    public void test_create_variable_parameter_with_experiment() throws Exception {
        VariableParameter param = new DefaultVariableParameter("PMA treatment", ExperimentUtils.createUnknownBasicExperiment());

        Assert.assertEquals("PMA treatment", param.getDescription());
        Assert.assertTrue(DefaultExperimentComparator.areEquals(ExperimentUtils.createUnknownBasicExperiment(), param.getExperiment()));
        Assert.assertNull(param.getUnit());
        Assert.assertNotNull(param.getVariableValues());
    }

    @Test
    public void test_set_unset_experiment(){
        VariableParameter param = new DefaultVariableParameter("PMA treatment");
        Experiment exp = ExperimentUtils.createUnknownBasicExperiment();

        param.setExperimentAndAddVariableParameter(exp);
        Assert.assertEquals(exp, param.getExperiment());
        Assert.assertEquals(1, exp.getVariableParameters().size());

        param.setExperimentAndAddVariableParameter(null);
        Assert.assertNull(param.getExperiment());
        Assert.assertEquals(0, exp.getVariableParameters().size());
    }
}
