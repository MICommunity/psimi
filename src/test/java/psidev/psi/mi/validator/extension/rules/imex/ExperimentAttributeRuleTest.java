package psidev.psi.mi.validator.extension.rules.imex;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.Attribute;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/01/11</pre>
 */

public class ExperimentAttributeRuleTest extends AbstractRuleTest {

    public ExperimentAttributeRuleTest() throws OntologyLoaderException {
        super(InteractorTypeRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ));
    }

    @Test
    public void test_experiment_no_attributes() throws ValidatorException {
        ExperimentDescription experiment = buildExperiment(9606);

        ExperimentAttributeRule rule = new ExperimentAttributeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(experiment);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_experiment_contact_email() throws ValidatorException {
        ExperimentDescription experiment = buildExperiment(9606);
        experiment.getAttributes().add(new Attribute(RuleUtils.CONTACT_EMAIL_MI_REF, RuleUtils.CONTACT_EMAIL, "bla@ebi.ac.uk"));

        Assert.assertEquals(1, experiment.getAttributes().size());
        ExperimentAttributeRule rule = new ExperimentAttributeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(experiment);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_experiment_copyright() throws ValidatorException {
        ExperimentDescription experiment = buildExperiment(9606);
        experiment.getAttributes().add(new Attribute("copyright", "bla"));

        Assert.assertEquals(1, experiment.getAttributes().size());
        ExperimentAttributeRule rule = new ExperimentAttributeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(experiment);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_experiment_attribute_without_nameAc() throws ValidatorException {
        ExperimentDescription experiment = buildExperiment(9606);
        experiment.getAttributes().add(new Attribute("contact-email", "bla@ebi.ac.uk"));

        Assert.assertEquals(1, experiment.getAttributes().size());
        ExperimentAttributeRule rule = new ExperimentAttributeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(experiment);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
