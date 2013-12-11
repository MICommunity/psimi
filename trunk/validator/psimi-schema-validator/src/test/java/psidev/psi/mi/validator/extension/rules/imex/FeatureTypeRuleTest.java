package psidev.psi.mi.validator.extension.rules.imex;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of FeatureTypeRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/01/11</pre>
 */

public class FeatureTypeRuleTest extends AbstractRuleTest {

    public FeatureTypeRuleTest() throws OntologyLoaderException {
        super(FeatureTypeRuleTest.class.getResourceAsStream( "/config/ontologies.xml" ));
    }

    @Test
    public void test_feature_one_featureType_type() throws ValidatorException {
        FeatureEvidence feature = buildCertainFeature(3, 8);

        FeatureTypeRule rule =  new FeatureTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(feature);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_feature_one_feature_no_type() throws ValidatorException {
        FeatureEvidence feature = buildCertainFeature(3, 8);
        feature.setType(null);

        FeatureTypeRule rule =  new FeatureTypeRule(ontologyMaganer);

        Collection<ValidatorMessage> messages = rule.check(feature);

        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
