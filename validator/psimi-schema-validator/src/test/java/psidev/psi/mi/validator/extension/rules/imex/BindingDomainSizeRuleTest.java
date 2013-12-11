package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of BindingDomainSizeRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/01/11</pre>
 */

public class BindingDomainSizeRuleTest extends AbstractRuleTest {

    public BindingDomainSizeRuleTest() throws OntologyLoaderException {
        super(BindingDomainSizeRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ));
    }

    @Test
    public void test_binding_more_3_aa_adjacents() throws ValidatorException {
        FeatureEvidence feature = buildCertainFeature(3, 10);

        BindingDomainSizeRule rule = new BindingDomainSizeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_binding_more_3_aa_total() throws ValidatorException {
        FeatureEvidence feature = buildCertainFeature(3, 4);

        Range certain = RangeUtils.createCertainRange(7);

        feature.getRanges().add(certain);

        BindingDomainSizeRule rule = new BindingDomainSizeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_binding_less_3_aa_adjacents() throws ValidatorException {
        FeatureEvidence feature = buildCertainFeature(3, 4);

        BindingDomainSizeRule rule = new BindingDomainSizeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_binding_less_3_aa_non_adjacent() throws ValidatorException {
        FeatureEvidence feature = buildCertainFeature(3, 3);

        Range certain = RangeUtils.createCertainRange(7);

        feature.getRanges().add(certain);

        BindingDomainSizeRule rule = new BindingDomainSizeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
