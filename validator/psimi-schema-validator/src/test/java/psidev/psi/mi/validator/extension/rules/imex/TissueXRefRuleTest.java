package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.Collection;

/**
 * Tester of TissueXRefRule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/01/11</pre>
 */

public class TissueXRefRuleTest extends AbstractRuleTest {

    @Test
    public void test_tissue_has_BRENDA() throws ValidatorException {
        CvTerm tissue = new DefaultCvTerm("test tissue");
        Xref brenda = XrefUtils.createXrefWithQualifier(RuleUtils.BRENDA, RuleUtils.BRENDA_MI_REF, "BTO:0001863", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF);
        tissue.getXrefs().add(brenda);

        TissueXRefRule rule = new TissueXRefRule(ontologyMaganer);

        Organism org = new DefaultOrganism(-1);
        org.setTissue(tissue);

        final Collection<ValidatorMessage> messages = rule.check( org );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_tissue_has_TISSUE_LIST() throws ValidatorException {
        CvTerm tissue = new DefaultCvTerm("test tissue");
        Xref brenda = XrefUtils.createXrefWithQualifier(RuleUtils.TISSUE_LIST, RuleUtils.TISSUE_LIST_MI_REF, "TS-0006", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF );

        tissue.getXrefs().add(brenda);

        TissueXRefRule rule = new TissueXRefRule(ontologyMaganer);

        Organism org = new DefaultOrganism(-1);
        org.setTissue(tissue);

        final Collection<ValidatorMessage> messages = rule.check( org );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_tissue_no_xRef() throws ValidatorException {
        CvTerm tissue = new DefaultCvTerm("test tissue");

        TissueXRefRule rule = new TissueXRefRule(ontologyMaganer);

        Organism org = new DefaultOrganism(-1);
        org.setTissue(tissue);

        final Collection<ValidatorMessage> messages = rule.check( org );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_tissue_has_other_xref() throws ValidatorException {
        CvTerm tissue = new DefaultCvTerm("test tissue");
        Xref ref = XrefUtils.createXrefWithQualifier(RuleUtils.CABRI, RuleUtils.CABRI_MI_REF, "xxx", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF);

        tissue.getXrefs().add(ref);

        TissueXRefRule rule = new TissueXRefRule(ontologyMaganer);

        Organism org = new DefaultOrganism(-1);
        org.setTissue(tissue);

        final Collection<ValidatorMessage> messages = rule.check( org );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
