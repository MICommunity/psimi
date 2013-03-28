package psidev.psi.mi.validator.extension.rules.imex;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.validator.extension.rules.AbstractRuleTest;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.DbReference;
import psidev.psi.mi.xml.model.Organism;
import psidev.psi.mi.xml.model.Tissue;
import psidev.psi.mi.xml.model.Xref;
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
        Tissue tissue = new Tissue();
        Xref brenda = new Xref();
        brenda.setPrimaryRef( new DbReference(RuleUtils.BRENDA, RuleUtils.BRENDA_MI_REF, "BTO:0001863", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF ) );

        tissue.setXref(brenda);

        TissueXRefRule rule = new TissueXRefRule(ontologyMaganer);

        Organism org = new Organism();
        org.setTissue(tissue);
        final Collection<ValidatorMessage> messages = rule.check( org );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_tissue_has_TISSUE_LIST() throws ValidatorException {
        Tissue tissue = new Tissue();
        Xref brenda = new Xref();
        brenda.setPrimaryRef( new DbReference(RuleUtils.TISSUE_LIST, RuleUtils.TISSUE_LIST_MI_REF, "TS-0006", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF ) );

        tissue.setXref(brenda);

        TissueXRefRule rule = new TissueXRefRule(ontologyMaganer);

        Organism org = new Organism();
        org.setTissue(tissue);
        final Collection<ValidatorMessage> messages = rule.check( org );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 0, messages.size() );
    }

    @Test
    public void test_tissue_no_xRef() throws ValidatorException {
        Tissue tissue = new Tissue();

        TissueXRefRule rule = new TissueXRefRule(ontologyMaganer);

        Organism org = new Organism();
        org.setTissue(tissue);
        final Collection<ValidatorMessage> messages = rule.check( org );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }

    @Test
    public void test_tissue_has_other_xref() throws ValidatorException {
        Tissue tissue = new Tissue();
        Xref ref = new Xref();
        ref.setPrimaryRef(new DbReference(RuleUtils.CABRI, RuleUtils.CABRI_MI_REF, "xxx", RuleUtils.IDENTITY, RuleUtils.IDENTITY_MI_REF));

        tissue.setXref(ref);

        TissueXRefRule rule = new TissueXRefRule(ontologyMaganer);

        Organism org = new Organism();
        org.setTissue(tissue);
        final Collection<ValidatorMessage> messages = rule.check( org );
        Assert.assertNotNull(messages);
        Assert.assertEquals( 1, messages.size() );
    }
}
