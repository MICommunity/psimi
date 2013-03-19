package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.xml.model.*;
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
        Feature feature = buildCertainFeature(3, 10);

        BindingDomainSizeRule rule = new BindingDomainSizeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_binding_more_3_aa_total() throws ValidatorException {
        Feature feature = buildCertainFeature(3, 4);

        Xref refStart = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0335", "identity", "MI:0356"));
        Names namesStart = new Names();
        namesStart.setShortLabel("certain");
        RangeStatus start = new RangeStatus();
        start.setNames(namesStart);
        start.setXref(refStart);

        Xref refEnd = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0335", "identity", "MI:0356"));
        Names namesEnd = new Names();
        namesEnd.setShortLabel("certain");
        RangeStatus end = new RangeStatus();
        end.setNames(namesEnd);
        end.setXref(refEnd);

        Range certain = new Range (start, new Position(7), end, new Position(7));

        feature.getRanges().add(certain);

        BindingDomainSizeRule rule = new BindingDomainSizeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_binding_less_3_aa_adjacents() throws ValidatorException {
        Feature feature = buildCertainFeature(3, 4);

        BindingDomainSizeRule rule = new BindingDomainSizeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_binding_less_3_aa_non_adjacent() throws ValidatorException {
        Feature feature = buildCertainFeature(3, 3);

        Xref refStart = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0335", "identity", "MI:0356"));
        Names namesStart = new Names();
        namesStart.setShortLabel("certain");
        RangeStatus start = new RangeStatus();
        start.setNames(namesStart);
        start.setXref(refStart);

        Xref refEnd = new Xref(new DbReference("psi-mi", "MI:0488", "MI:0335", "identity", "MI:0356"));
        Names namesEnd = new Names();
        namesEnd.setShortLabel("certain");
        RangeStatus end = new RangeStatus();
        end.setNames(namesEnd);
        end.setXref(refEnd);

        Range certain = new Range (start, new Position(7), end, new Position(7));

        feature.getRanges().add(certain);

        BindingDomainSizeRule rule = new BindingDomainSizeRule(ontologyMaganer);

        final Collection<ValidatorMessage> messages = rule.check( feature );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
